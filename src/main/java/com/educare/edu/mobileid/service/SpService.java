package com.educare.edu.mobileid.service;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.educare.edu.mobileid.config.ConfigBean;
import com.educare.edu.mobileid.mapper.QrTableMapper;
import com.educare.edu.mobileid.model.QrTable;
import com.raonsecure.omnione.core.data.did.DIDs;
import com.raonsecure.omnione.core.data.iw.Privacy;
import com.raonsecure.omnione.core.data.iw.Unprotected;
import com.raonsecure.omnione.core.data.iw.profile.EncryptTypeEnum;
import com.raonsecure.omnione.core.data.iw.profile.result.VCVerifyProfileResult;
import com.raonsecure.omnione.core.data.rest.QrDataJson;
import com.raonsecure.omnione.core.data.rest.ResultJson;
import com.raonsecure.omnione.core.key.IWKeyManager;
import com.raonsecure.omnione.core.key.IWKeyManagerInterface.OnUnLockListener;
import com.raonsecure.omnione.core.key.store.IWDIDFile;
import com.raonsecure.omnione.core.util.GDPLogger;
import com.raonsecure.omnione.core.util.http.HttpException;
import com.raonsecure.omnione.sdk_server_core.OmniOption;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.BlockChainException;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.ServerInfo;
import com.raonsecure.omnione.sdk_server_core.data.VcResult;
import com.raonsecure.omnione.sdk_verifier.VerifyApi;
import com.raonsecure.omnione.sdk_verifier.api.data.VcVerifyProfileParam;

@Service
public class SpService {

	private Logger logger = LoggerFactory.getLogger(SpService.class);

	@Autowired
	private ConfigBean configBean;

	@Autowired
	private QrTableMapper qrTableMapper;

	/**
	 * SP Wallet KeyManager
	 */
	private IWKeyManager keyManager;

	/**
	 * SP DID Object
	 */
	private DIDs didDoc;

	/**
	 * SP DID File Path;
	 */
	private String didFilePath;

	/**
	 * BlockChain Node Server Info
	 */
	private ServerInfo blockChainServerInfo;

	@PostConstruct
	public void init() {
		try {
			// logging setting
			GDPLogger.FLAG = true;
			GDPLogger.SYSOUT_PRINT = true;

			// SDK Detail Log
			OmniOption.setSdkDetailLog(configBean.isSdkDetailLog());
			
			// SDK Use Cache
			OmniOption.setUseCache(configBean.isSdkUseCache());
			
			// KeyManager Load
			File keymanagerFile = ResourceUtils.getFile(configBean.getSpKeymanagerPath());
			String keyManagerPath = keymanagerFile.getAbsolutePath();
			logger.info("[OMN] SP keyManagerPath:" + keyManagerPath);

			keyManager = new IWKeyManager(keyManagerPath, configBean.getSpKeymanagerPassword().toCharArray());
			keyManager.unLock(configBean.getSpKeymanagerPassword().toCharArray(), new OnUnLockListener() {
				@Override
				public void onSuccess() {
					logger.info("[OMN] SP keyManager: onSuccess");
				}

				@Override
				public void onFail(int errCode) {
					logger.info("[OMN] SP keyManager: onFail");
				}

				@Override
				public void onCancel() {
					logger.info("[OMN] SP keyManager: onCancel");
				}
			});

			// DID File Load
			File didFile = ResourceUtils.getFile(configBean.getSpDidPath());
			didFilePath = didFile.getAbsolutePath();
			logger.info("[OMN] SP didDocuemntPath:" + didFilePath);

			IWDIDFile iwdidFile = new IWDIDFile(didFilePath);
			didDoc = iwdidFile.getData();
			logger.info("[OMN] SP didDocuemnt:" + didDoc.toJson());

			// BlockChain Node Setting
			blockChainServerInfo = new ServerInfo(configBean.getBlockchainServerDomain());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[OMN] SP Init Error - Check Log");
		}
	}	

	/**
	 * Profile JSON을 서버에서 내려주지 않고 블록체인 ReadNode를 통해 Profile을 획득하는 방식 
	 * 
	 * @param nonce
	 * @return
	 * @throws BlockChainException
	 * @throws HttpException
	 */
	public QrDataJson noneProfileQRData(String nonce, String contextPath){
		logger.debug("profile nonce:" + nonce);


		String serviceCode = configBean.getSpServiceCode();
		logger.debug("profile serviceCode:" + serviceCode);
		
		
		String callBackUrl = configBean.getBlockchainCallback();

		QrDataJson qrData = new QrDataJson();
		qrData.setSpDid(didDoc.getId());
		qrData.setServiceCode(serviceCode);
		qrData.setEncryptType(EncryptTypeEnum.AES_256);
		qrData.setNonce(nonce);
		qrData.setCallBackUrl(callBackUrl);
		
		return qrData;
	}

	/**
	 * 검증
	 * 
	 * @param vcVerifyProfileResult
	 * @return
	 * @throws BlockChainException
	 * @throws HttpException
	 */
	public ResultJson verify(VCVerifyProfileResult vcVerifyProfileResult) throws BlockChainException, HttpException {
		// logging setting
		GDPLogger.FLAG = true;
		GDPLogger.SYSOUT_PRINT = true;
				
		ResultJson resultJson = new ResultJson();

		String nonce = vcVerifyProfileResult.getNonce();
		QrTable qrTable = qrTableMapper.selectQrTableByNonce(nonce);

		if (qrTable != null && qrTable.getStep() == 1) {
			VcVerifyProfileParam vcVerifyParam = new VcVerifyProfileParam(
					blockChainServerInfo, 
					keyManager, 
					configBean.getSpMainKeyId(), 
					configBean.getSpAccount(), 
					vcVerifyProfileResult, 
					didFilePath);

			// 유효기간이 지난 증명서 검증 가능(2021.07.05), 반드시 false로 설정!!
			vcVerifyParam.setCheckVCExpirationDate(false);	//true : 유효기간 이내 검증, false : 유효기간 제한 없이 검증

			VcResult vcResult = VerifyApi.verify(vcVerifyParam, false);

			String vcCompleteJson = "";
			if (vcResult.isW3CType()) {
				vcCompleteJson = vcResult.getVpComplete().toJson();
				logger.debug("VP: " + vcCompleteJson);
			} else {
				vcCompleteJson = vcResult.getVcComplete().toJson();
				logger.debug("VC_COPY: " + vcCompleteJson);
			}
			
			List<Unprotected> privacyList = vcResult.getPrivacyList();
			Privacy privacy = new Privacy();
			privacy.setUnprotected(privacyList);

			qrTable.setStep(2);
			qrTable.setVcJson(privacy.toJson());
			qrTableMapper.updateQrTable(qrTable);
			resultJson.setResult(vcResult.getStatus().equals("1"));
			return resultJson;

		} else {
			qrTable.setStep(10);
			qrTableMapper.updateQrTable(qrTable);

			resultJson.setResult(false);
			return resultJson;
		}
	}

}

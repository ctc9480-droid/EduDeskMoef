package com.educare.edu.mobileid.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.edu.mobileid.mapper.QrTableMapper;
import com.educare.edu.mobileid.model.QrTable;
import com.educare.edu.mobileid.model.ResultQr;
import com.educare.edu.mobileid.service.SpService;
import com.educare.edu.mobileid.util.NonceUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.raonsecure.omnione.core.data.iw.profile.result.VCVerifyProfileResult;
import com.raonsecure.omnione.core.data.rest.ErrorEnum;
import com.raonsecure.omnione.core.data.rest.QrDataJson;
import com.raonsecure.omnione.core.data.rest.ResultJson;
import com.raonsecure.omnione.core.exception.IWException;
import com.raonsecure.omnione.core.util.GDPBase64;
import com.raonsecure.omnione.core.util.http.HttpException;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.BlockChainException;

@Controller("SpNoneProfile")
@RequestMapping("/spnoneprofile")
public class SpNoneProfileRest {

	private Logger logger = LoggerFactory.getLogger(SpNoneProfileRest.class);

	@Autowired
	private QrTableMapper qrTableMapper;
	
	@Autowired
	private SpService spService;
	

	/**
	 * QR 이미지 생성 
	 * @param msg
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	private String createQrImage(String msg, int width, int height) throws Exception {
		QRCodeWriter q = new QRCodeWriter();
		Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 0); /* default = 4 */

		BitMatrix bitMatrix = q.encode(msg, BarcodeFormat.QR_CODE, width, height, hints);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
		byte[] pngData = baos.toByteArray();
		baos.close();
		String data = "data:" + "image/png" + ";base64,";
		data = data + Base64.getEncoder().encodeToString(pngData);
		return data;
	}
	
	/**
	 * 검증 QR 이미지 요청 Controller
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "qrVerifyReq.do", method = RequestMethod.POST,
            produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResultQr qrVerifyReq(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		ResultQr resultQr = new ResultQr();

		session.removeAttribute("nonce");
		session.removeAttribute("csrfToken");

		resultQr.setResult(true);
		String nonce = NonceUtil.createNonce();
		String csrfToken = NonceUtil.createNonce();
		session.setAttribute("nonce", nonce);
		session.setAttribute("csrfToken", csrfToken);

		QrTable qrTable = new QrTable();
		qrTable.setNonce(nonce);
		qrTable.setStep(1);
		// 2분
		Long time = new Date().getTime() + 120000;
		qrTable.setExpireDate(new Date(time));
		qrTableMapper.insertQrTable(qrTable);

		logger.debug("SP QR Data: " + qrTable);

		try {
			String contextPath = request.getContextPath();
			QrDataJson qrData = spService.noneProfileQRData(nonce, contextPath);
			logger.debug("qrString: " + qrData.toJson());

			String qrString = GDPBase64.encodeUrlString(qrData.toJson().getBytes("utf-8"));
			
			resultQr.setCsrfToken(csrfToken);
			resultQr.setQrData(createQrImage(qrString, 500, 500));

		} catch (Exception e) {
			e.printStackTrace();
			resultQr.setErrorCode(ErrorEnum.FAIL);
		}

		response.setContentType("application/json");
		return resultQr;
	}
	
	/**
	 * 검증 APP to APP 요청 Controller
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "apptoapp", method = { RequestMethod.POST })
	@ResponseBody
	public String apptoapp(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		ResultQr resultQr = new ResultQr();

		session.removeAttribute("nonce");
		session.removeAttribute("csrfToken");

		resultQr.setResult(true);
		String nonce = NonceUtil.createNonce();
		String csrfToken = NonceUtil.createNonce();
		session.setAttribute("nonce", nonce);
		session.setAttribute("csrfToken", csrfToken);

		QrTable qrTable = new QrTable();
		qrTable.setNonce(nonce);
		qrTable.setStep(0);
		// 2분
		Long time = new Date().getTime() + 120000;
		qrTable.setExpireDate(new Date(time));
		qrTableMapper.insertQrTable(qrTable);

		logger.debug("SP QR Data: " + qrTable);

		String contextPath = request.getContextPath();
		QrDataJson qrData = spService.noneProfileQRData(nonce, contextPath);

		response.setContentType("application/json");
		return qrData.toJson();
	}	

	@RequestMapping(value = "txCheck.do", method = RequestMethod.POST,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResultQr txCheck(String csrfToken, HttpSession session, HttpServletResponse response) {
		ResultQr demoResultBean = new ResultQr();

		String sessionNonce = (String) session.getAttribute("nonce");
		String sessionCsrfToken = (String) session.getAttribute("csrfToken");

		if (sessionNonce != null && csrfToken.equals(sessionCsrfToken)) {
			QrTable qrTable = qrTableMapper.selectQrTableByNonce(sessionNonce);
			if (qrTable.getExpireDate().getTime() >= new Date().getTime()) {
				demoResultBean.setResult(true);
				if (qrTable.getStep() == 2) {
					demoResultBean.setTxCompleteCode(ResultQr.TxCode.COMPLETE);
				} else if (qrTable.getStep() == 10) {
					demoResultBean.setTxCompleteCode(ResultQr.TxCode.ERROR);
					demoResultBean.setResult(false);

				} else {
					demoResultBean.setTxCompleteCode(ResultQr.TxCode.WAITING);
				}
			} else {
				demoResultBean.setResult(false);
				demoResultBean.setTxCompleteCode(ResultQr.TxCode.TIMEOUT);
			}
		} else {
			demoResultBean.setResult(false);
			demoResultBean.setTxCompleteCode(ResultQr.TxCode.ERROR);
		}
		response.setContentType("application/json");
		return demoResultBean;
	}


	/**
	 * 사본 제출 및 검증
	 * 
	 * @param vcVerifyRestParam
	 * @param request
	 * @return
	 * @throws HttpException
	 * @throws IWException
	 * @throws BlockChainException
	 * @throws IOException 
	 */
	@RequestMapping(value = "verify.do", method = RequestMethod.POST,
			produces = "application/json; charset=UTF-8")
		@ResponseBody
	public ResultJson verify(HttpServletRequest request, HttpServletResponse response) 
			throws BlockChainException, HttpException, IWException, IOException {
		String requestBody = this.getRawRequestBody(request);
		VCVerifyProfileResult vcVerifyProfileResult = new VCVerifyProfileResult();
		vcVerifyProfileResult.fromJson(requestBody);
		logger.debug("verify: " + vcVerifyProfileResult.toJson());

		ResultJson resultVc = spService.verify(vcVerifyProfileResult);
		response.setContentType("application/json");
		return resultVc;
	}

	private String getRawRequestBody(HttpServletRequest request) throws IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		
		String line;
		while((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		
		return buffer.toString();
	}
}

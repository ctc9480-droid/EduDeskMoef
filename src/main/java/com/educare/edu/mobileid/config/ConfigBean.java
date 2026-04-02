package com.educare.edu.mobileid.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigBean {
	/**
	 * 블록체인 RCP HOST
	 */
	@Value("${app.blockchain-server-domain}")
	private String blockchainServerDomain;

	/**
	 * Wallet 파일 세팅
	 */
	@Value("${app.sp-keymanager-path}")
	private String spKeymanagerPath;

	/**
	 * Wallet 패스워드
	 */
	@Value("${app.sp-keymanager-password}")
	private String spKeymanagerPassword;

	/**
	 * Wallet 메인 키 아이디
	 */
	@Value("${app.sp-main-key-id}")
	private String spMainKeyId;
	
	/**
	 * SP 블록체인 계정
	 */
	@Value("${app.sp-account}")
	private String spAccount;

	/**
	 * SP DID 파일 경로
	 */
	@Value("${app.sp-did-path}")
	private String spDidPath;

	/**
	 * SP Service Code
	 */
	@Value("${app.sp-service-code}")
	private String spServiceCode;

	/**
	 * 블록체인을 통한 검증 여부, true = 스마트컨트랙트를 통한 검증, false = 로컬 서버에 검증
	 */
	@Value("#{new Boolean('${app.verify-check-blockchain}')}")
	private boolean isVerifyCheckBlockchain;

	/**
	 * 상세 로그 사용 여부
	 */
	@Value("#{new Boolean('${app.sdk-detail-log}')}")
	private boolean sdkDetailLog;

	/**
	 * 블록체인 조회 캐싱 사용 여부
	 */
	@Value("#{new Boolean('${app.sdk-use-cache}')}")
	private boolean sdkUseCache;

	/**
	 * 검증(verify) Callback URL
	 */
	@Value("${app.blockchain-callback}")
	private String blockchainCallback;


	public String getBlockchainServerDomain() {
		return blockchainServerDomain;
	}

	public String getSpKeymanagerPath() {
		return spKeymanagerPath;
	}

	public String getSpKeymanagerPassword() {
		return spKeymanagerPassword;
	}

	public String getSpAccount() {
		return spAccount;
	}

	public String getSpDidPath() {
		return spDidPath;
	}

	public String getSpServiceCode() {
		return spServiceCode;
	}

	public boolean isVerifyCheckBlockchain() {
		return isVerifyCheckBlockchain;
	}

	public boolean isSdkDetailLog() {
		return sdkDetailLog;
	}

	public boolean isSdkUseCache() {
		return sdkUseCache;
	}

	public String getBlockchainCallback() {
		return blockchainCallback;
	}

	public String getSpMainKeyId() {
		return spMainKeyId;
	}
}

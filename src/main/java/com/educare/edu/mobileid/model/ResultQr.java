package com.educare.edu.mobileid.model;

import com.raonsecure.omnione.core.data.rest.ResultJson;

/**
 * QR 요청 정보
 * @author tykim
 *
 */
public class ResultQr extends ResultJson {
	
	public enum TxCode{
		WAITING, COMPLETE, TIMEOUT, ERROR
	}
	
	/**
	 * TX 상태 정보 
	 */
	private TxCode txCompleteCode = TxCode.WAITING;

	/**
	 * QR Page csrfToken
	 */
	private String csrfToken;

	/**
	 * QR Image Data
	 */
	private String qrData;

	public String getCsrfToken() {
		return csrfToken;
	}

	public void setCsrfToken(String csrfToken) {
		this.csrfToken = csrfToken;
	}

	public String getQrData() {
		return qrData;
	}

	public void setQrData(String qrData) {
		this.qrData = qrData;
	}

	public TxCode getTxCompleteCode() {
		return txCompleteCode;
	}

	public void setTxCompleteCode(TxCode txCompleteCode) {
		this.txCompleteCode = txCompleteCode;
	}

}

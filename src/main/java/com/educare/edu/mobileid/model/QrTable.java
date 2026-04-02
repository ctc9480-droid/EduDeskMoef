package com.educare.edu.mobileid.model;

import java.util.Date;

public class QrTable {

	private Long qrKey;

	private int step = 0;

	private String nonce;

	private String vcJson;

	private Date expireDate;


	public Long getQrKey() {
		return qrKey;
	}

	public void setQrKey(Long qrKey) {
		this.qrKey = qrKey;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getVcJson() {
		return vcJson;
	}

	public void setVcJson(String vcJson) {
		this.vcJson = vcJson;
	}

}

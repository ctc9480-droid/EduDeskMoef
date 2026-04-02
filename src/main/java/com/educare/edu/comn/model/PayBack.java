package com.educare.edu.comn.model;

import java.util.Date;

public class PayBack{
	private int idx;
	private String bankNm;
	private String accountNm;
	private String accountNo;
	private String userId;
	
	
	public PayBack() {
		super();
	}
	
	public PayBack(String bankNm, String accountNm, String accountNo, String userId) {
		super();
		this.bankNm = bankNm;
		this.accountNm = accountNm;
		this.accountNo = accountNo;
		this.userId = userId;
	}


	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getBankNm() {
		return bankNm;
	}
	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}
	public String getAccountNm() {
		return accountNm;
	}
	public void setAccountNm(String accountNm) {
		this.accountNm = accountNm;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}

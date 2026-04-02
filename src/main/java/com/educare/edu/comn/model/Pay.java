package com.educare.edu.comn.model;

import java.util.Date;

import com.educare.util.EhCacheUtil;


/**
 * 기관 
 * @author koonsdev01
 *
 */
public class Pay{
	private String payNo;
	private int rceptSeq;
	private String userId;
	private String userNm;
	private String email;
	private Date regDt;
	private int state; //0:취소,1:승인
	private int amount;
	private int payType;//1:카드,2:계좌이체,3:가상계좌,4:휴대폰
	private String tel;
	private String mobile;
	private String pgPayNo;
	private String pgNm;
	private String pgResultData;
	private String goodNm;
	private Date updDt;
	
	private String vtBank;
	private String vtNm;
	private String vtNo;
	private String vtCloseDtime;
	
	private String transactionId;
	private String ktlBillNo;
	
	public Pay() {
		super();
	}
	public Pay(String payNo, int rceptSeq, String userId, String userNm, String email, int state, int amount, int payType, String tel, String mobile, String pgPayNo, String pgNm,String goodNm
			,String vtBank,String vtNm, String vtNo, String vtCloseDtime) {
		super();
		this.payNo = payNo;
		this.rceptSeq = rceptSeq;
		this.userId = userId;
		this.userNm = userNm;
		this.email = email;
		this.state = state;
		this.amount = amount;
		this.payType = payType;
		this.tel = tel;
		this.mobile = mobile;
		this.pgPayNo = pgPayNo;
		this.pgNm = pgNm;
		this.goodNm = goodNm;
		this.vtBank = vtBank;
		this.vtNm = vtNm;
		this.vtNo = vtNo;
		this.vtCloseDtime = vtCloseDtime;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public int getRceptSeq() {
		return rceptSeq;
	}
	public void setRceptSeq(int rceptSeq) {
		this.rceptSeq = rceptSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getEmail() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getEmail();
			//return CryptoSeedUtil.decrypt(this.email);
		} catch (NullPointerException e) {
			return this.email;
		}
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getMobile();
			//return CryptoSeedUtil.decrypt(this.mobile);
		} catch (NullPointerException e) {
			return this.mobile;
		}
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPgPayNo() {
		return pgPayNo;
	}
	public void setPgPayNo(String pgPayNo) {
		this.pgPayNo = pgPayNo;
	}
	public String getPgNm() {
		return pgNm;
	}
	public void setPgNm(String pgNm) {
		this.pgNm = pgNm;
	}
	public String getGoodNm() {
		return goodNm;
	}
	public void setGoodNm(String goodNm) {
		this.goodNm = goodNm;
	}
	public Date getUpdDt() {
		return updDt;
	}
	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}
	public String getVtBank() {
		return vtBank;
	}
	public void setVtBank(String vtBank) {
		this.vtBank = vtBank;
	}
	public String getVtNm() {
		return vtNm;
	}
	public void setVtNm(String vtNm) {
		this.vtNm = vtNm;
	}
	public String getVtNo() {
		return vtNo;
	}
	public void setVtNo(String vtNo) {
		this.vtNo = vtNo;
	}
	public String getVtCloseDtime() {
		return vtCloseDtime;
	}
	public void setVtCloseDtime(String vtCloseDtime) {
		this.vtCloseDtime = vtCloseDtime;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getPgResultData() {
		return pgResultData;
	}
	public void setPgResultData(String pgResultData) {
		this.pgResultData = pgResultData;
	}
	public String getKtlBillNo() {
		return ktlBillNo;
	}
	public void setKtlBillNo(String ktlBillNo) {
		this.ktlBillNo = ktlBillNo;
	}

}

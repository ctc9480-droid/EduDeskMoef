package com.educare.edu.comn.model;

import java.util.Date;


/**
 * 기관 
 * @author koonsdev01
 *
 */
public class UserMemo{
	private String userId;
	private int eduSeq;
	private String memoPay;
	
	public UserMemo(String userId, int eduSeq) {
		super();
		this.userId = userId;
		this.eduSeq = eduSeq;
	}
	public UserMemo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public String getMemoPay() {
		return memoPay;
	}
	public void setMemoPay(String memoPay) {
		this.memoPay = memoPay;
	}

}

package com.educare.edu.comn.model;

import java.util.Date;


/**
 * 기관 
 * @author koonsdev01
 *
 */
public class OnlineHistory{
	private int idx;
	private int eduSeq;
	private int timeSeq;
	private String userId;
	private String userNm;
	private Date regTime;
	private String email;
	public OnlineHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OnlineHistory(int eduSeq, int timeSeq, String userId, String userNm, Date regTime,String email) {
		super();
		this.eduSeq = eduSeq;
		this.timeSeq = timeSeq;
		this.userId = userId;
		this.userNm = userNm;
		this.regTime = regTime;
		this.email = email;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public int getTimeSeq() {
		return timeSeq;
	}
	public void setTimeSeq(int timeSeq) {
		this.timeSeq = timeSeq;
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
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

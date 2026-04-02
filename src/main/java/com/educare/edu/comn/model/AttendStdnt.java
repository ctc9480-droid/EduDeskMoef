package com.educare.edu.comn.model;

import java.util.Date;

public class AttendStdnt{
	private int eduSeq;
	private int timeSeq;
	private String userId;
	private String attCd;
	private Date regDate;
	
	public AttendStdnt() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AttendStdnt(int eduSeq, int timeSeq, String userId, String attCd, Date regDate) {
		super();
		this.eduSeq = eduSeq;
		this.timeSeq = timeSeq;
		this.userId = userId;
		this.attCd = attCd;
		this.regDate = regDate;
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
	public String getAttCd() {
		return attCd;
	}
	public void setAttCd(String attCd) {
		this.attCd = attCd;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}

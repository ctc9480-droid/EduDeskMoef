package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.educare.component.VarComponent;

public class LectureLike{
	private int eduSeq;
	private String userId;
	private int state;
	
	private String regId;
	private String updId;
	private Date regDe;
	private Date updDe;
	public LectureLike() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LectureLike(int eduSeq, String userId, int state, String regId, String updId, Date regDe, Date updDe) {
		super();
		this.eduSeq = eduSeq;
		this.userId = userId;
		this.state = state;
		this.regId = regId;
		this.updId = updId;
		this.regDe = regDe;
		this.updDe = updDe;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public Date getRegDe() {
		return regDe;
	}
	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}
	public Date getUpdDe() {
		return updDe;
	}
	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}
	
	
}

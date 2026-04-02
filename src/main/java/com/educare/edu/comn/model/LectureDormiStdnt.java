package com.educare.edu.comn.model;

import java.util.Date;

/**
 */
public class LectureDormiStdnt {
	
	private int dormiSeq; //방번호
	private String userId; //사용자번호
	private String startDt; //배정시작일시
	private String endDt;	//배정종료일시
	private int eduSeq;
	
	private String updId;
	private Date updDe;
	
	
	public int getDormiSeq() {
		return dormiSeq;
	}
	public void setDormiSeq(int dormiSeq) {
		this.dormiSeq = dormiSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public Date getUpdDe() {
		return updDe;
	}
	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}
	
	
	
}

package com.educare.edu.comn.model;

import org.apache.commons.lang3.StringUtils;

public class LectureTimeStdnt{
	private int eduSeq;
	private int timeSeq;
	private String userId;
	private String movTime;
	private String movAllTime;
	private int movRe;
	
	public LectureTimeStdnt() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LectureTimeStdnt(int eduSeq, int timeSeq, String userId) {
		super();
		this.eduSeq = eduSeq;
		this.timeSeq = timeSeq;
		this.userId = userId;
	}
	public LectureTimeStdnt(int eduSeq, int timeSeq, String userId, String movTime, String movAllTime,int movRe) {
		super();
		this.eduSeq = eduSeq;
		this.timeSeq = timeSeq;
		this.userId = userId;
		this.movTime = movTime;
		this.movAllTime=movAllTime;
		this.setMovRe(movRe);
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
	public String getMovTime() {
		return movTime;
	}
	public void setMovTime(String movTime) {
		this.movTime = movTime;
	}
	public String getMovAllTime() {
		return movAllTime;
	}
	public void setMovAllTime(String movAllTime) {
		this.movAllTime = movAllTime;
	}
	public int getMovRe() {
		return movRe;
	}
	public void setMovRe(int movRe) {
		this.movRe = movRe;
	}
}

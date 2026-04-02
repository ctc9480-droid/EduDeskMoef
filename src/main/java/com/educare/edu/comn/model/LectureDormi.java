package com.educare.edu.comn.model;

import java.util.Date;

/**
 */
public class LectureDormi {
	
	protected int dormiSeq;		//방번호
	protected String dormiNm;	//방이름
	private String useYn;		//사용여부
	private String place;		//장소명
	private String memo;		//메모
	private int capaCnt;		//수용인원
	private String accessYn;
	private String regId;
	private Date regDe;
	private String updId;
	private Date updDe;
	
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getCapaCnt() {
		return capaCnt;
	}
	public void setCapaCnt(int capaCnt) {
		this.capaCnt = capaCnt;
	}
	public int getDormiSeq() {
		return dormiSeq;
	}
	public void setDormiSeq(int dormiSeq) {
		this.dormiSeq = dormiSeq;
	}
	public String getDormiNm() {
		return dormiNm;
	}
	public void setDormiNm(String dormiNm) {
		this.dormiNm = dormiNm;
	}
	public Date getRegDe() {
		return regDe;
	}
	public void setRegDe(Date regDe) {
		this.regDe = regDe;
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
	public String getAccessYn() {
		return accessYn;
	}
	public void setAccessYn(String accessYn) {
		this.accessYn = accessYn;
	}
	
}

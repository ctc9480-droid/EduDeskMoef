package com.educare.edu.comn.model;

import java.util.Date;

/**
 */
public class LectureRoom {
	
	protected int roomSeq;
	protected String roomNm;
	private String useYn;
	private String place;
	private String memo;
	
	private String regId;
	private Date regDt;
	private String modId;
	private Date modDt;
	
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public int getRoomSeq() {
		return roomSeq;
	}
	public void setRoomSeq(int roomSeq) {
		this.roomSeq = roomSeq;
	}
	public String getRoomNm() {
		return roomNm;
	}
	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public Date getModDt() {
		return modDt;
	}
	public void setModDt(Date modDt) {
		this.modDt = modDt;
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
	
}

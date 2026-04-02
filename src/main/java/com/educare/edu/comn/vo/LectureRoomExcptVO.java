package com.educare.edu.comn.vo;

import com.educare.edu.comn.model.LectureRoom;

/**
 */
public class LectureRoomExcptVO {
	
	//model
	private int roomSeq;
	private String startDt;
	private String endDt;
	private String memo;
	
	private String roomNm;
	
	//add
	private String srchEduDt;
	
	public LectureRoomExcptVO(int roomSeq, String startDt, String endDt,String memo) {
		this.roomSeq = roomSeq;
		this.startDt = startDt;
		this.endDt = endDt;
		this.memo = memo;
	}
	public LectureRoomExcptVO() {
	}
	public int getRoomSeq() {
		return roomSeq;
	}
	public void setRoomSeq(int roomSeq) {
		this.roomSeq = roomSeq;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getSrchEduDt() {
		return srchEduDt;
	}
	public void setSrchEduDt(String srchEduDt) {
		this.srchEduDt = srchEduDt;
	}
	public String getRoomNm() {
		return roomNm;
	}
	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}
	
}

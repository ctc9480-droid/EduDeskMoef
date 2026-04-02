package com.educare.edu.comn.model;

import java.util.Date;

/**
 */
public class LectureTmTimetable {
	
	private int tmSeq;
	private int tmPlanSeq;
	private int tmSubSeq;
	private String tmEduDt;
	private String resvUserId;
	private Date resvDt;
	private int resvRoomSeq;
	
	
	
	public LectureTmTimetable(int tmSeq, int tmPlanSeq, int tmSubSeq, String tmEduDt) {
		this.tmSeq = tmSeq;
		this.tmPlanSeq = tmPlanSeq;
		this.tmSubSeq = tmSubSeq;
		this.tmEduDt = tmEduDt;
	}
	public LectureTmTimetable() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTmSeq() {
		return tmSeq;
	}
	public void setTmSeq(int tmSeq) {
		this.tmSeq = tmSeq;
	}
	public int getTmPlanSeq() {
		return tmPlanSeq;
	}
	public void setTmPlanSeq(int tmPlanSeq) {
		this.tmPlanSeq = tmPlanSeq;
	}
	public int getTmSubSeq() {
		return tmSubSeq;
	}
	public void setTmSubSeq(int tmSubSeq) {
		this.tmSubSeq = tmSubSeq;
	}
	public String getTmEduDt() {
		return tmEduDt;
	}
	public void setTmEduDt(String tmEduDt) {
		this.tmEduDt = tmEduDt;
	}
	public String getResvUserId() {
		return resvUserId;
	}
	public void setResvUserId(String resvUserId) {
		this.resvUserId = resvUserId;
	}
	public Date getResvDt() {
		return resvDt;
	}
	public void setResvDt(Date resvDt) {
		this.resvDt = resvDt;
	}
	public int getResvRoomSeq() {
		return resvRoomSeq;
	}
	public void setResvRoomSeq(int resvRoomSeq) {
		this.resvRoomSeq = resvRoomSeq;
	}
	
}

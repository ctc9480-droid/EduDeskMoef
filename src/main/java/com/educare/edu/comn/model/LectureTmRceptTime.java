package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class LectureTmRceptTime {
	private int tmRceptSeq;
	private int tmSeq;
	private int tmPlanSeq;
	private int tmSubSeq;
	private String tmEduDt;
	private String startTm;
	private String endTm;
	
	public LectureTmRceptTime() {
		super();
	}

	public LectureTmRceptTime(int tmRceptSeq, int tmSeq, int tmPlanSeq, int tmSubSeq, String tmEduDt, String startTm, String endTm) {
		this.tmRceptSeq = tmRceptSeq;
		this.tmSeq = tmSeq;
		this.tmPlanSeq = tmPlanSeq;
		this.tmSubSeq = tmSubSeq;
		this.tmEduDt = tmEduDt;
		this.startTm = startTm;
		this.endTm = endTm;
	}

	public int getTmRceptSeq() {
		return tmRceptSeq;
	}

	public void setTmRceptSeq(int tmRceptSeq) {
		this.tmRceptSeq = tmRceptSeq;
	}

	public int getTmPlanSeq() {
		return tmPlanSeq;
	}

	public void setTmPlanSeq(int tmPlanSeq) {
		this.tmPlanSeq = tmPlanSeq;
	}

	public String getTmEduDt() {
		return tmEduDt;
	}

	public void setTmEduDt(String tmEduDt) {
		this.tmEduDt = tmEduDt;
	}

	public String getStartTm() {
		return startTm;
	}

	public void setStartTm(String startTm) {
		this.startTm = startTm;
	}

	public String getEndTm() {
		return endTm;
	}

	public void setEndTm(String endTm) {
		this.endTm = endTm;
	}

	public int getTmSubSeq() {
		return tmSubSeq;
	}

	public void setTmSubSeq(int tmSubSeq) {
		this.tmSubSeq = tmSubSeq;
	}

	public int getTmSeq() {
		return tmSeq;
	}

	public void setTmSeq(int tmSeq) {
		this.tmSeq = tmSeq;
	}
	
}

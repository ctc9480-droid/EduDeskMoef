package com.educare.edu.comn.model;

/**
 */
public class LectureTmPlanTime {
	
	private int tmSeq;
	private int tmPlanSeq;
	private int tmSubSeq;
	private int tmTimeSeq;
	protected String startTm;
	protected String endTm;
	private String description;
	private String instrNm;
	
	public LectureTmPlanTime() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LectureTmPlanTime(int tmSeq) {
		this.tmSeq = tmSeq;
	}
	public LectureTmPlanTime(int tmSeq, int tmPlanSeq, int tmSubSeq) {
		this.tmSeq = tmSeq;
		this.tmPlanSeq = tmPlanSeq;
		this.tmSubSeq = tmSubSeq;
	}
	public LectureTmPlanTime(int tmSeq, int tmPlanSeq, int tmSubSeq, int tmTimeSeq) {
		this.tmSeq = tmSeq;
		this.tmPlanSeq = tmPlanSeq;
		this.tmSubSeq = tmSubSeq;
		this.tmTimeSeq = tmTimeSeq;
	}
	public LectureTmPlanTime(int tmSeq, int tmPlanSeq, int tmSubSeq, int tmTimeSeq, String startTm, String endTm, String description, String instrNm) {
		this.tmSeq = tmSeq;
		this.tmPlanSeq = tmPlanSeq;
		this.tmSubSeq = tmSubSeq;
		this.tmTimeSeq = tmTimeSeq;
		this.startTm = startTm;
		this.endTm = endTm;
		this.description = description;
		this.instrNm = instrNm;
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
	public int getTmTimeSeq() {
		return tmTimeSeq;
	}
	public void setTmTimeSeq(int tmTimeSeq) {
		this.tmTimeSeq = tmTimeSeq;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInstrNm() {
		return instrNm;
	}
	public void setInstrNm(String instrNm) {
		this.instrNm = instrNm;
	}
	
	
}

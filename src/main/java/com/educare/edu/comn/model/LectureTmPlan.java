package com.educare.edu.comn.model;

/**
 */
public class LectureTmPlan {
	
	private int tmSeq;
	private int tmPlanSeq;
	protected String tmPeriodBegin;
	private String tmPeriodEnd;
	private int repeatCycle;
	protected String repeatWeek;
	protected String tmCloseDt;
	protected String tmClosePeriod;
	
	public LectureTmPlan() {
		super();
	}
	public LectureTmPlan(int tmSeq, int tmPlanSeq, String tmPeriodBegin, String tmPeriodEnd, int repeatCycle, String repeatWeek,String tmCloseDt,String tmClosePeriod) {
		this.tmSeq = tmSeq;
		this.tmPlanSeq = tmPlanSeq;
		this.tmPeriodBegin = tmPeriodBegin;
		this.tmPeriodEnd = tmPeriodEnd;
		this.repeatCycle = repeatCycle;
		this.repeatWeek = repeatWeek;
		this.tmCloseDt = tmCloseDt;
		this.tmClosePeriod = tmClosePeriod;
	}
	public LectureTmPlan(int tmSeq,int tmPlanSeq) {
		this.tmSeq = tmSeq;
		this.tmPlanSeq = tmPlanSeq;
	}
	public LectureTmPlan(int tmSeq) {
		this.tmSeq = tmSeq;
	}
	
	
	public String getTmClosePeriod() {
		return tmClosePeriod;
	}
	public void setTmClosePeriod(String tmClosePeriod) {
		this.tmClosePeriod = tmClosePeriod;
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
	public String getTmPeriodBegin() {
		return tmPeriodBegin;
	}
	public void setTmPeriodBegin(String tmPeriodBegin) {
		this.tmPeriodBegin = tmPeriodBegin;
	}
	public String getTmPeriodEnd() {
		return tmPeriodEnd;
	}
	public void setTmPeriodEnd(String tmPeriodEnd) {
		this.tmPeriodEnd = tmPeriodEnd;
	}
	public int getRepeatCycle() {
		return repeatCycle;
	}
	public void setRepeatCycle(int repeatCycle) {
		this.repeatCycle = repeatCycle;
	}
	public String getRepeatWeek() {
		return repeatWeek;
	}
	public void setRepeatWeek(String repeatWeek) {
		this.repeatWeek = repeatWeek;
	}
	public String getTmCloseDt() {
		return tmCloseDt;
	}
	public void setTmCloseDt(String tmCloseDt) {
		this.tmCloseDt = tmCloseDt;
	}
	
	
}

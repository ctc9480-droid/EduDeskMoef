package com.educare.edu.eduOld.vo;

import java.util.Date;


/**
 */
public class EduOldVO{
	//페이징처리
	private String eduYear;
	private String eduNm;
	private int chasu;
	private Date dtEduStart;
	private Date dtEduEnd;
	
	public String getEduYear() {
		return eduYear;
	}
	public void setEduYear(String eduYear) {
		this.eduYear = eduYear;
	}
	public String getEduNm() {
		return eduNm;
	}
	public void setEduNm(String eduNm) {
		this.eduNm = eduNm;
	}
	public int getChasu() {
		return chasu;
	}
	public void setChasu(int chasu) {
		this.chasu = chasu;
	}
	public Date getDtEduStart() {
		return dtEduStart;
	}
	public void setDtEduStart(Date dtEduStart) {
		this.dtEduStart = dtEduStart;
	}
	public Date getDtEduEnd() {
		return dtEduEnd;
	}
	public void setDtEduEnd(Date dtEduEnd) {
		this.dtEduEnd = dtEduEnd;
	}

	
}	

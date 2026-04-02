package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 */
public class UserCareer{
	private String userId;
	private int careerSeq;
	private Date beginDt;
	private Date endDt;
	private String compNm;
	private String taskNm;
	private String updId;
	private Date updDe;
	
	private String beginDtStr;
	private String endDtStr;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getCareerSeq() {
		return careerSeq;
	}
	public void setCareerSeq(int careerSeq) {
		this.careerSeq = careerSeq;
	}
	public Date getBeginDt() {
		return beginDt;
	}
	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	public String getCompNm() {
		return compNm;
	}
	public void setCompNm(String compNm) {
		this.compNm = compNm;
	}
	public String getTaskNm() {
		return taskNm;
	}
	public void setTaskNm(String taskNm) {
		this.taskNm = taskNm;
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
	public String getBeginDtStr() {
		return beginDtStr;
	}
	public void setBeginDtStr(String beginDtStr) {
		this.beginDtStr = beginDtStr;
	}
	public String getEndDtStr() {
		return endDtStr;
	}
	public void setEndDtStr(String endDtStr) {
		this.endDtStr = endDtStr;
	}
	
	
	
}

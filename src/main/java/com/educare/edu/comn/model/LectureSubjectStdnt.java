package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.educare.component.VarComponent;

public class LectureSubjectStdnt{
	private int eduSeq;
	private int subSeq;
	private String userId;
	private int complState;		//이수처리, 1:이수
	private int passState;		//합격처리, 1:합격
	private String complCertNum;
	private String passCertNum;
	private Date complCertDe;
	private Date passCertDe;
	private Date updDe;
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public int getSubSeq() {
		return subSeq;
	}
	public void setSubSeq(int subSeq) {
		this.subSeq = subSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getComplState() {
		return complState;
	}
	public void setComplState(int complState) {
		this.complState = complState;
	}
	public int getPassState() {
		return passState;
	}
	public void setPassState(int passState) {
		this.passState = passState;
	}
	public String getComplCertNum() {
		return complCertNum;
	}
	public void setComplCertNum(String complCertNum) {
		this.complCertNum = complCertNum;
	}
	public String getPassCertNum() {
		return passCertNum;
	}
	public void setPassCertNum(String passCertNum) {
		this.passCertNum = passCertNum;
	}
	public Date getComplCertDe() {
		return complCertDe;
	}
	public void setComplCertDe(Date complCertDe) {
		this.complCertDe = complCertDe;
	}
	public Date getPassCertDe() {
		return passCertDe;
	}
	public void setPassCertDe(Date passCertDe) {
		this.passCertDe = passCertDe;
	}
	public Date getUpdDe() {
		return updDe;
	}
	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}
	
	
}

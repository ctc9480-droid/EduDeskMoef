package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.educare.component.VarComponent;

public class LectureSubject{
	private int subSeq;
	private String subNm;
	private int state;
	private int passIdx 	= -1;
	private int complIdx 	= -1;
	private String passCertNm;
	private String complCertNm;
	
	private String regId;
	private String updId;
	private Date regDe;
	private Date updDe;
	
	public LectureSubject(int subSeq, String subNm ,int passIdx,int complIdx,int state
			, String passCertNm, String complCertNm
			, String regId, String updId, Date regDe, Date updDe) {
		super();
		this.subSeq = subSeq;
		this.subNm = subNm;
		this.passIdx = passIdx;
		this.complIdx = complIdx;
		this.passCertNm = passCertNm;
		this.complCertNm = complCertNm;
		this.state = state;
		this.regId = regId;
		this.updId = updId;
		this.regDe = regDe;
		this.updDe = updDe;
	}
	public LectureSubject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getSubSeq() {
		return subSeq;
	}
	public void setSubSeq(int subSeq) {
		this.subSeq = subSeq;
	}
	public String getSubNm() {
		return subNm;
	}
	public void setSubNm(String subNm) {
		this.subNm = subNm;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public Date getRegDe() {
		return regDe;
	}
	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}
	public Date getUpdDe() {
		return updDe;
	}
	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getPassIdx() {
		return passIdx;
	}
	public void setPassIdx(int passIdx) {
		this.passIdx = passIdx;
	}
	public int getComplIdx() {
		return complIdx;
	}
	public void setComplIdx(int complIdx) {
		this.complIdx = complIdx;
	}
	public String getPassCertNm() {
		return passCertNm;
	}
	public void setPassCertNm(String passCertNm) {
		this.passCertNm = passCertNm;
	}
	public String getComplCertNm() {
		return complCertNm;
	}
	public void setComplCertNm(String complCertNm) {
		this.complCertNm = complCertNm;
	}

	
}

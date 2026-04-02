package com.educare.edu.comn.vo;

import java.util.Date;

/*
 * 과목결과 정보
 */
public class SubjectResultVO {
	private int eduSeq;
	private int subSeq;
	private String subNm;		
	private String endDeStr;	//과목마지막 일시
	private int complState;		//이수처리, 1:이수
	private int passState;		//합격처리, 1:합격
	private String complCertNum;
	private String passCertNum;
	private Date complCertDe;
	private Date passCertDe;
	private int complIdx;		//이수증번호
	private int passIdx;		//합격증번호
	
	private String userId;
	
	
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
	public String getSubNm() {
		return subNm;
	}
	public void setSubNm(String subNm) {
		this.subNm = subNm;
	}
	public String getEndDeStr() {
		return endDeStr;
	}
	public void setEndDeStr(String endDeStr) {
		this.endDeStr = endDeStr;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getPassState() {
		return passState;
	}
	public void setPassState(int passState) {
		this.passState = passState;
	}
	public int getComplState() {
		return complState;
	}
	public void setComplState(int complState) {
		this.complState = complState;
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
	public int getComplIdx() {
		return complIdx;
	}
	public void setComplIdx(int complIdx) {
		this.complIdx = complIdx;
	}
	public int getPassIdx() {
		return passIdx;
	}
	public void setPassIdx(int passIdx) {
		this.passIdx = passIdx;
	}
	public Date getPassCertDe() {
		return passCertDe;
	}
	public void setPassCertDe(Date passCertDe) {
		this.passCertDe = passCertDe;
	}
	public Date getComplCertDe() {
		return complCertDe;
	}
	public void setComplCertDe(Date complCertDe) {
		this.complCertDe = complCertDe;
	}
	

}

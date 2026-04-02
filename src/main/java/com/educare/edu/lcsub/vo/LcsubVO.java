package com.educare.edu.lcsub.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.educare.edu.quizBank.web.QuizBankComponent;

/**
 */
public class LcsubVO{
	//페이징처리
	private int rowCnt;
	private int firstIndex;
	private String srchWrd;
	
	//데이타
	private int subSeq;
	private String subNm;
	private int state;
	private int passIdx;
	private Date regDe;
	
	//add
	private String regNm;
	private String passNm;
	private String complNm;
	private String classTmNm;
	
	public int getRowCnt() {
		return rowCnt;
	}
	public void setRowCnt(int rowCnt) {
		this.rowCnt = rowCnt;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public String getSrchWrd() {
		return srchWrd;
	}
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
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
	public Date getRegDe() {
		return regDe;
	}
	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public String getPassNm() {
		return passNm;
	}
	public void setPassNm(String passNm) {
		this.passNm = passNm;
	}
	public String getComplNm() {
		return complNm;
	}
	public void setComplNm(String complNm) {
		this.complNm = complNm;
	}
	public String getClassTmNm() {
		return classTmNm;
	}
	public void setClassTmNm(String classTmNm) {
		this.classTmNm = classTmNm;
	}

	
}	

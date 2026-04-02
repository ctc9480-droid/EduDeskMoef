package com.educare.edu.lclike.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.educare.component.VarComponent;
import com.educare.edu.quizBank.web.QuizBankComponent;

/**
 */
public class LclikeVO{
	//페이징처리
	private int rowCnt;
	private int firstIndex;
	private String srchWrd;
	
	//데이타
	private int eduSeq;
	private String userId;
	private int state;
	
	private Date regDe;
	
	//add
	private String rceptPeriodBegin;
	private String rceptPeriodEnd;
	private String eduPeriodBegin;
	private String eduPeriodEnd;
	private int targetTp;
	private String eduNm;
	private int ctg1Seq;
	private String rceptPeriodYn;
	
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
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public Date getRegDe() {
		return regDe;
	}
	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRceptPeriodBegin() {
		return rceptPeriodBegin;
	}
	public void setRceptPeriodBegin(String rceptPeriodBegin) {
		this.rceptPeriodBegin = rceptPeriodBegin;
	}
	public String getRceptPeriodEnd() {
		return rceptPeriodEnd;
	}
	public void setRceptPeriodEnd(String rceptPeriodEnd) {
		this.rceptPeriodEnd = rceptPeriodEnd;
	}
	public String getEduPeriodBegin() {
		return eduPeriodBegin;
	}
	public void setEduPeriodBegin(String eduPeriodBegin) {
		this.eduPeriodBegin = eduPeriodBegin;
	}
	public String getEduPeriodEnd() {
		return eduPeriodEnd;
	}
	public void setEduPeriodEnd(String eduPeriodEnd) {
		this.eduPeriodEnd = eduPeriodEnd;
	}
	public int getTargetTp() {
		return targetTp;
	}
	public void setTargetTp(int targetTp) {
		this.targetTp = targetTp;
	}
	public String getEduNm() {
		return eduNm;
	}
	public void setEduNm(String eduNm) {
		this.eduNm = eduNm;
	}
	

	public int getCtg1Seq() {
		return ctg1Seq;
	}
	public void setCtg1Seq(int ctg1Seq) {
		this.ctg1Seq = ctg1Seq;
	}
	public String getRceptPeriodYn() {
		return rceptPeriodYn;
	}
	public void setRceptPeriodYn(String rceptPeriodYn) {
		this.rceptPeriodYn = rceptPeriodYn;
	}
	
	
}	

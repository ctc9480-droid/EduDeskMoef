package com.educare.edu.quizBank.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.educare.edu.quizBank.web.QuizBankComponent;

/**
 */
public class QuizBankVO{
	//페이징처리
	private int rowCnt;
	private int firstIndex;
	private String srchWrd;
	private int srchCtg1Seq;
	private int srchCtg2Seq;
	private int srchCtg3Seq;
	private int srchQstnTp;
	
	//데이타
	private int qstnSeq;
	private String qstnNm;
	private int qstnTp;
	private int diffType;
	private int testTmplSeq;
	private Date regDe;
	private String useYn;
	
	private String regNm;
	private String ctg1Nm;
	private String ctg2Nm;
	private String ctg3Nm;
	
	//add getter
	public String getAddDiffTypeNm(){
		return QuizBankComponent.getDiffTypeNm(this.diffType);
	}
	public String getAddQstnTpNm(){
		return QuizBankComponent.getQstnTpNm(this.qstnTp);
	}
	
	//data
	public int getQstnSeq() {
		return qstnSeq;
	}

	public void setQstnSeq(int qstnSeq) {
		this.qstnSeq = qstnSeq;
	}

	public int getRowCnt() {
		return rowCnt;
	}

	public void setRowCnt(int rowCnt) {
		this.rowCnt = rowCnt;
	}

	public String getQstnNm() {
		return qstnNm;
	}

	public void setQstnNm(String qstnNm) {
		this.qstnNm = qstnNm;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public Date getRegDe() {
		return regDe;
	}

	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}

	public String getSrchWrd() {
		return srchWrd;
	}

	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
	}

	public int getQstnTp() {
		return qstnTp;
	}

	public void setQstnTp(int qstnTp) {
		this.qstnTp = qstnTp;
	}

	public int getDiffType() {
		return diffType;
	}

	public void setDiffType(int diffType) {
		this.diffType = diffType;
	}
	public int getTestTmplSeq() {
		return testTmplSeq;
	}
	public void setTestTmplSeq(int testTmplSeq) {
		this.testTmplSeq = testTmplSeq;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getCtg1Nm() {
		return ctg1Nm;
	}
	public void setCtg1Nm(String ctg1Nm) {
		this.ctg1Nm = ctg1Nm;
	}
	public String getCtg2Nm() {
		return ctg2Nm;
	}
	public void setCtg2Nm(String ctg2Nm) {
		this.ctg2Nm = ctg2Nm;
	}
	public String getCtg3Nm() {
		return ctg3Nm;
	}
	public void setCtg3Nm(String ctg3Nm) {
		this.ctg3Nm = ctg3Nm;
	}
	public int getSrchCtg1Seq() {
		return srchCtg1Seq;
	}
	public void setSrchCtg1Seq(int srchCtg1Seq) {
		this.srchCtg1Seq = srchCtg1Seq;
	}
	public int getSrchCtg2Seq() {
		return srchCtg2Seq;
	}
	public void setSrchCtg2Seq(int srchCtg2Seq) {
		this.srchCtg2Seq = srchCtg2Seq;
	}
	public int getSrchCtg3Seq() {
		return srchCtg3Seq;
	}
	public void setSrchCtg3Seq(int srchCtg3Seq) {
		this.srchCtg3Seq = srchCtg3Seq;
	}
	public int getSrchQstnTp() {
		return srchQstnTp;
	}
	public void setSrchQstnTp(int srchQstnTp) {
		this.srchQstnTp = srchQstnTp;
	}
	
}	

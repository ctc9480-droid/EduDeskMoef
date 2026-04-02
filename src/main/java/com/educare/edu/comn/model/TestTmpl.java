package com.educare.edu.comn.model;

import java.util.Date;
//문제은행
public class TestTmpl{
	
	private int 	testTmplSeq;
	private String 	testTmplNm;
	private int 	testTp		=1;
	private int 	ctg1Seq;
	private int 	ctg2Seq;
	private int 	ctg3Seq;
	private int 	timeLimit;
	private int 	markTp		=1;
	private int 	lookTp		=1;
	private int 	ordTp		=1;
	private int 	selectTp;
	private int 	choiceTp	=1;
	private int 	lookCnt;
	private String 	totQstn;
	private String 	selectQstn;
	private String 	descr;
	private Double 	totMarks;
	private int 	status		=1;
	private String 	regId;
	private Date 	regDe;
	private String 	updId;
	private Date 	updDe;
	
	public TestTmpl() {
	}
	public TestTmpl(String testTmplNm, int testTp, int ctg1Seq, int ctg2Seq, int ctg3Seq, int timeLimit, int markTp, int lookTp, int ordTp, int selectTp, int choiceTp, int lookCnt, String totQstn, String selectQstn, String descr,
			Double totMarks, int status, String regId, Date regDe, String updId, Date updDe) {
		super();
		this.testTmplNm = testTmplNm;
		this.testTp = testTp;
		this.ctg1Seq = ctg1Seq;
		this.ctg2Seq = ctg2Seq;
		this.ctg3Seq = ctg3Seq;
		this.timeLimit = timeLimit;
		this.markTp = markTp;
		this.lookTp = lookTp;
		this.ordTp = ordTp;
		this.selectTp = selectTp;
		this.choiceTp = choiceTp;
		this.lookCnt = lookCnt;
		this.totQstn = totQstn;
		this.selectQstn = selectQstn;
		this.descr = descr;
		this.totMarks = totMarks;
		this.status = status;
		this.regId = regId;
		this.regDe = regDe;
		this.updId = updId;
		this.updDe = updDe;
	}
	public int getTestTmplSeq() {
		return testTmplSeq;
	}
	public void setTestTmplSeq(int testTmplSeq) {
		this.testTmplSeq = testTmplSeq;
	}
	public String getTestTmplNm() {
		return testTmplNm;
	}
	public void setTestTmplNm(String testTmplNm) {
		this.testTmplNm = testTmplNm;
	}
	public int getTestTp() {
		return testTp;
	}
	public void setTestTp(int testTp) {
		this.testTp = testTp;
	}
	public int getCtg1Seq() {
		return ctg1Seq;
	}
	public void setCtg1Seq(int ctg1Seq) {
		this.ctg1Seq = ctg1Seq;
	}
	public int getCtg2Seq() {
		return ctg2Seq;
	}
	public void setCtg2Seq(int ctg2Seq) {
		this.ctg2Seq = ctg2Seq;
	}
	public int getCtg3Seq() {
		return ctg3Seq;
	}
	public void setCtg3Seq(int ctg3Seq) {
		this.ctg3Seq = ctg3Seq;
	}
	public int getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	public int getMarkTp() {
		return markTp;
	}
	public void setMarkTp(int markTp) {
		this.markTp = markTp;
	}
	public int getLookTp() {
		return lookTp;
	}
	public void setLookTp(int lookTp) {
		this.lookTp = lookTp;
	}
	public int getOrdTp() {
		return ordTp;
	}
	public void setOrdTp(int ordTp) {
		this.ordTp = ordTp;
	}
	public int getSelectTp() {
		return selectTp;
	}
	public void setSelectTp(int selectTp) {
		this.selectTp = selectTp;
	}
	public int getChoiceTp() {
		return choiceTp;
	}
	public void setChoiceTp(int choiceTp) {
		this.choiceTp = choiceTp;
	}
	public int getLookCnt() {
		return lookCnt;
	}
	public void setLookCnt(int lookCnt) {
		this.lookCnt = lookCnt;
	}
	public String getTotQstn() {
		return totQstn;
	}
	public void setTotQstn(String totQstn) {
		this.totQstn = totQstn;
	}
	public String getSelectQstn() {
		return selectQstn;
	}
	public void setSelectQstn(String selectQstn) {
		this.selectQstn = selectQstn;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Double getTotMarks() {
		return totMarks;
	}
	public void setTotMarks(Double totMarks) {
		this.totMarks = totMarks;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getRegDe() {
		return regDe;
	}
	public void setRegDe(Date regDe) {
		this.regDe = regDe;
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
	
	
}

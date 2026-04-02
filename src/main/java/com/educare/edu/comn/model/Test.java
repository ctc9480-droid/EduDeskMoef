package com.educare.edu.comn.model;

import java.util.Date;
//문제은행
public class Test{
	private int testSeq;		//시험지아이디
	private int testTmplSeq;
	private int eduSeq;
	private String testNm;
	private int bfafTp;
	private Date startDe;
	private Date endDe;
	private String descr;
	private Double passMarks;
	private int status;
	private int timer;
	private int markTp;
	private int tryNo;
	private int prntTestSeq;
	private int subSeq;
	
	private String regId;
	private String updId;
	private Date regDe;
	private Date updDe;
	
	public Test() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Test(int testTmplSeq, int eduSeq, String testNm, int bfafTp, Date startDe, Date endDe, String descr, Double passMarks, int status,int timer,int markTp
			, int tryNo, int prntTestSeq, int subSeq
			, String regId, String updId, Date regDe, Date updDe) {
		this.testTmplSeq = testTmplSeq;
		this.eduSeq = eduSeq;
		this.testNm = testNm;
		this.bfafTp = bfafTp;
		this.startDe = startDe;
		this.endDe = endDe;
		this.descr = descr;
		this.passMarks = passMarks;
		this.status = status;
		this.timer = timer;
		this.markTp = markTp;
		this.tryNo = tryNo;
		this.prntTestSeq = prntTestSeq;
		this.setSubSeq(subSeq);
		
		this.regId = regId;
		this.updId = updId;
		this.regDe = regDe;
		this.updDe = updDe;
	}

	public int getTestSeq() {
		return testSeq;
	}

	public void setTestSeq(int testSeq) {
		this.testSeq = testSeq;
	}

	public int getTestTmplSeq() {
		return testTmplSeq;
	}

	public void setTestTmplSeq(int testTmplSeq) {
		this.testTmplSeq = testTmplSeq;
	}

	public int getEduSeq() {
		return eduSeq;
	}

	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}

	public String getTestNm() {
		return testNm;
	}

	public void setTestNm(String testNm) {
		this.testNm = testNm;
	}

	public int getBfafTp() {
		return bfafTp;
	}

	public void setBfafTp(int bfafTp) {
		this.bfafTp = bfafTp;
	}

	public Date getStartDe() {
		return startDe;
	}

	public void setStartDe(Date startDe) {
		this.startDe = startDe;
	}

	public Date getEndDe() {
		return endDe;
	}

	public void setEndDe(Date endDe) {
		this.endDe = endDe;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Double getPassMarks() {
		return passMarks;
	}

	public void setPassMarks(Double passMarks) {
		this.passMarks = passMarks;
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

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getMarkTp() {
		return markTp;
	}

	public void setMarkTp(int markTp) {
		this.markTp = markTp;
	}

	public int getTryNo() {
		return tryNo;
	}

	public void setTryNo(int tryNo) {
		this.tryNo = tryNo;
	}

	public int getPrntTestSeq() {
		return prntTestSeq;
	}

	public void setPrntTestSeq(int prntTestSeq) {
		this.prntTestSeq = prntTestSeq;
	}

	public int getSubSeq() {
		return subSeq;
	}

	public void setSubSeq(int subSeq) {
		this.subSeq = subSeq;
	}
	
	
}

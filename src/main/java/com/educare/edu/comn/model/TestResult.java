package com.educare.edu.comn.model;

import java.util.Date;
//결과지
public class TestResult{
	private int testSeq;		//시험아이디
	private String userId;
	private Date startDe;
	private Date endDe;
	private double totMarks = -1;
	private double marks = -1;
	private String passYn;
	private String achvLvl;
	private int state;			//1:대기, 2:제출, 3:채점완료
	private String checkId;		//채점자아이디
	private Date checkDe;
	
	private String regId;
	private Date regDe;
	private String updId;
	private Date updDe;
	
	public TestResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TestResult(int testSeq, String userId, Date startDe, Date endDe, double totMarks, double marks, String passYn, String achvLvl, int state, String regId, Date regDe, String updId, Date updDe) {
		super();
		this.testSeq = testSeq;
		this.userId = userId;
		this.startDe = startDe;
		this.endDe = endDe;
		this.totMarks = totMarks;
		this.marks = marks;
		this.passYn = passYn;
		this.achvLvl = achvLvl;
		this.state = state;
		this.regId = regId;
		this.regDe = regDe;
		this.updId = updId;
		this.updDe = updDe;
	}
	
	public int getTestSeq() {
		return testSeq;
	}
	public void setTestSeq(int testSeq) {
		this.testSeq = testSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public double getTotMarks() {
		return totMarks;
	}
	public void setTotMarks(double totMarks) {
		this.totMarks = totMarks;
	}
	public double getMarks() {
		return marks;
	}
	public void setMarks(double marks) {
		this.marks = marks;
	}
	public String getPassYn() {
		return passYn;
	}
	public void setPassYn(String passYn) {
		this.passYn = passYn;
	}
	public String getAchvLvl() {
		return achvLvl;
	}
	public void setAchvLvl(String achvLvl) {
		this.achvLvl = achvLvl;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public Date getCheckDe() {
		return checkDe;
	}

	public void setCheckDe(Date checkDe) {
		this.checkDe = checkDe;
	}
	
}

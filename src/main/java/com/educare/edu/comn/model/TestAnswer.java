package com.educare.edu.comn.model;

import java.util.Date;

public class TestAnswer{
	private int tqSeq;			//시험문제아이디
	private String userId;
	private int testSeq;
	private int testTmplSeq;
	private String answeredYn;
	private String optn;		//객관식을 배열로 저장함,[1,2]형태
	private String fillBlank;
	private String answer;
	private double marks;
	private double marksGet = -1;
	private int ord;			//문제순번
	private String chOrd;		//보기순번,json배열형태
	
	private String regId;
	private String updId;
	private Date regDe;
	private Date updDe;
	
	public TestAnswer(int tqSeq, String userId, int testSeq, int testTmplSeq, String answeredYn, String optn, String fillBlank, String answer, Double marks, Double marksGet, int ord, String regId, String updId, Date regDe, Date updDe) {
		super();
		this.tqSeq = tqSeq;
		this.userId = userId;
		this.testSeq = testSeq;
		this.testTmplSeq = testTmplSeq;
		this.answeredYn = answeredYn;
		this.optn = optn;
		this.fillBlank = fillBlank;
		this.answer = answer;
		this.marks = marks;
		this.marksGet = marksGet;
		this.ord = ord;
		this.regId = regId;
		this.updId = updId;
		this.regDe = regDe;
		this.updDe = updDe;
	}
	public TestAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTqSeq() {
		return tqSeq;
	}
	public void setTqSeq(int tqSeq) {
		this.tqSeq = tqSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getAnsweredYn() {
		return answeredYn;
	}
	public void setAnsweredYn(String answeredYn) {
		this.answeredYn = answeredYn;
	}
	public String getOptn() {
		return optn;
	}
	public void setOptn(String optn) {
		this.optn = optn;
	}
	public String getFillBlank() {
		return fillBlank;
	}
	public void setFillBlank(String fillBlank) {
		this.fillBlank = fillBlank;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public double getMarks() {
		return marks;
	}
	public void setMarks(double marks) {
		this.marks = marks;
	}
	public double getMarksGet() {
		return marksGet;
	}
	public void setMarksGet(double marksGet) {
		this.marksGet = marksGet;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
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
	public String getChOrd() {
		return chOrd;
	}
	public void setChOrd(String chOrd) {
		this.chOrd = chOrd;
	}
		
	
}

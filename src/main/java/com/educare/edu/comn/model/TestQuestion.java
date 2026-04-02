package com.educare.edu.comn.model;

import java.util.Date;
//문제은행
public class TestQuestion{
	private int tqSeq;		//시험문제아이디
	private int testTmplSeq;
	private int qstnSeq;
	private int qstnOrd;
	//private Double marks;
	private double marks;
	private String updId;
	private Date updDe;
	
	public TestQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TestQuestion(int testTmplSeq, int qstnSeq, int qstnOrd, double marks, String updId, Date updDe) {
		super();
		this.testTmplSeq = testTmplSeq;
		this.qstnSeq = qstnSeq;
		this.qstnOrd = qstnOrd;
		this.marks = marks;
		this.updId = updId;
		this.updDe = updDe;
	}
	public int getTqSeq() {
		return tqSeq;
	}
	public void setTqSeq(int tqSeq) {
		this.tqSeq = tqSeq;
	}
	public int getTestTmplSeq() {
		return testTmplSeq;
	}
	public void setTestTmplSeq(int testTmplSeq) {
		this.testTmplSeq = testTmplSeq;
	}
	public int getQstnSeq() {
		return qstnSeq;
	}
	public void setQstnSeq(int qstnSeq) {
		this.qstnSeq = qstnSeq;
	}
	public int getQstnOrd() {
		return qstnOrd;
	}
	public void setQstnOrd(int qstnOrd) {
		this.qstnOrd = qstnOrd;
	}
	public double getMarks() {
		return marks;
	}
	public void setMarks(double marks) {
		this.marks = marks;
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

package com.educare.edu.comn.model;

import java.util.Date;

public class ExamBankQuestion{
	private int ebqIdx;
	private String question;
	private String answerText;
	private int questionType;
	private Date regTime;
	private Date updTime;
	public ExamBankQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExamBankQuestion(String question, String answerText, int questionType,Date regTime,Date updTime) {
		super();
		this.question = question;
		this.answerText = answerText;
		this.questionType = questionType;
		this.regTime = regTime;
		this.updTime = updTime;
		
	}
	public int getEbqIdx() {
		return ebqIdx;
	}
	public void setEbqIdx(int ebqIdx) {
		this.ebqIdx = ebqIdx;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion1(String question) {
		this.question = question;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	public int getQuestionType() {
		return questionType;
	}
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public Date getUpdTime() {
		return updTime;
	}
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	
	
}

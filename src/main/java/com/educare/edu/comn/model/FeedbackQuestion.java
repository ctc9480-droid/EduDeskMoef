package com.educare.edu.comn.model;

import java.util.Date;

public class FeedbackQuestion{
	private int fbIdx;
	private int qtIdx;
	private int qtType;
	private String question;
	private String qtDiv;
	private String essntlYn;
	
	
	public FeedbackQuestion(int fbIdx, int qtIdx, int qtType, String question, String qtDiv, String essntlYn) {
		super();
		this.fbIdx = fbIdx;
		this.qtIdx = qtIdx;
		this.qtType = qtType;
		this.question = question;
		this.qtDiv = qtDiv;
		this.essntlYn = essntlYn;
	}
	public int getFbIdx() {
		return fbIdx;
	}
	public void setFbIdx(int fbIdx) {
		this.fbIdx = fbIdx;
	}
	public int getQtIdx() {
		return qtIdx;
	}
	public void setQtIdx(int qtIdx) {
		this.qtIdx = qtIdx;
	}
	public int getQtType() {
		return qtType;
	}
	public void setQtType(int qtType) {
		this.qtType = qtType;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQtDiv() {
		return qtDiv;
	}
	public void setQtDiv(String qtDiv) {
		this.qtDiv = qtDiv;
	}
	public String getEssntlYn() {
		return essntlYn;
	}
	public void setEssntlYn(String essntlYn) {
		this.essntlYn = essntlYn;
	}
	
	
}

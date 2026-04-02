package com.educare.edu.comn.model;

import java.util.Date;

public class FeedbackAnswer{
	private int eduSeq;
	private int feSeq;
	private int fbIdx;
	private int qtIdx;
	private String userId;
	private String answer;
	private int choice;
	private Date regTime;
	
	public FeedbackAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FeedbackAnswer(int eduSeq,int feSeq,int fbIdx, int qtIdx, String userId, String answer, int choice, Date regTime) {
		super();
		this.eduSeq = eduSeq;
		this.feSeq = feSeq;
		this.fbIdx = fbIdx;
		this.qtIdx = qtIdx;
		this.userId = userId;
		this.answer = answer;
		this.choice = choice;
		this.regTime = regTime;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getChoice() {
		return choice;
	}
	public void setChoice(int choice) {
		this.choice = choice;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public int getEduSeq() {
		return eduSeq;
	}

	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}

	public int getFeSeq() {
		return feSeq;
	}

	public void setFeSeq(int feSeq) {
		this.feSeq = feSeq;
	}

}

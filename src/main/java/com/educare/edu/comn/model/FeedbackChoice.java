package com.educare.edu.comn.model;

import java.util.Date;

public class FeedbackChoice{
	private int fbIdx;
	private int qtIdx;
	private int chIdx;
	private String choice;
	private int point;
	
	
	
	public FeedbackChoice(int fbIdx, int qtIdx, int chIdx, String choice,int point) {
		super();
		this.fbIdx = fbIdx;
		this.qtIdx = qtIdx;
		this.chIdx = chIdx;
		this.choice = choice;
		this.point = point;
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
	public int getChIdx() {
		return chIdx;
	}
	public void setChIdx(int chIdx) {
		this.chIdx = chIdx;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	
}

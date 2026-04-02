package com.educare.edu.comn.model;

public class ExamBankChoice{
	private int ebqIdx;
	private int ebcIdx;
	private String choice;
	private int correctType;
	private int status;
	public ExamBankChoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExamBankChoice(int ebqIdx, int ebcIdx, String choice, int correctType, int status) {
		super();
		this.ebqIdx = ebqIdx;
		this.ebcIdx = ebcIdx;
		this.choice = choice;
		this.correctType = correctType;
		this.status = status;
	}
	public int getEbqIdx() {
		return ebqIdx;
	}
	public void setEbqIdx(int ebqIdx) {
		this.ebqIdx = ebqIdx;
	}
	public int getEbcIdx() {
		return ebcIdx;
	}
	public void setEbcIdx(int ebcIdx) {
		this.ebcIdx = ebcIdx;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public int getCorrectType() {
		return correctType;
	}
	public void setCorrectType(int correctType) {
		this.correctType = correctType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}

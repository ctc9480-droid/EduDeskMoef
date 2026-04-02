package com.educare.edu.exam.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 */
public class ExamVO{
	private int ebqIdx;			//문제은행 문제번호
	private int ebcIdx;			//문제은행 객관식번호
	private int epIdx;			//시험지번호
	private int epqIdx;			//시험지문제번호
	private int epcIdx;			//시험지객관번호
	
	private String question;	//문제문구
	private String answerText;	//주관식답
	private int questionType;	//문제유형,0:주관식,1:객관식
	
	private String choice;		//객관식 문구
	private int correctType; 	//정답여부,0:오답,1:정답
	private int status;			//상태여부,0:감추기,1:보이기
	
	private String title; 		//시험지제목
	
	private Date regTime;
	private Date updTime;

	private int pageNo=1;
	private int firstRecordIndex=0;
	private int recordCountPerPage=10;
	private int rNum;
	
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

	public int getEpIdx() {
		return epIdx;
	}

	public void setEpIdx(int epIdx) {
		this.epIdx = epIdx;
	}

	public int getEpqIdx() {
		return epqIdx;
	}

	public void setEpqIdx(int epqIdx) {
		this.epqIdx = epqIdx;
	}

	public int getEpcIdx() {
		return epcIdx;
	}

	public void setEpcIdx(int epcIdx) {
		this.epcIdx = epcIdx;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	public String getFncQuestion(){
		try {
			return this.question.replaceAll("\\<.*?>", "");
		} catch (NullPointerException e) {
			return "!ERROR";
		}
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getFirstRecordIndex() {
		return firstRecordIndex;
	}

	public void setFirstRecordIndex(int firstRecordIndex) {
		this.firstRecordIndex = firstRecordIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public int getrNum() {
		return rNum;
	}

	public void setrNum(int rNum) {
		this.rNum = rNum;
	}
	
}	

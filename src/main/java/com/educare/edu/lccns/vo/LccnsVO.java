package com.educare.edu.lccns.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.educare.edu.quizBank.web.QuizBankComponent;

/**
 */
public class LccnsVO{
	//페이징처리
	private int rowCnt;
	private int firstIndex;
	private String srchWrd;
	
	//데이타
	private int cnsSeq;
	private int eduSeq;
	private String userId;
	private String title;
	private String content;
	private int state;
	private String regId;
	private Date regDe;
	private String updId;
	private Date updDe;
	
	//add
	private String regNm;
	
	public int getRowCnt() {
		return rowCnt;
	}
	public void setRowCnt(int rowCnt) {
		this.rowCnt = rowCnt;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public String getSrchWrd() {
		return srchWrd;
	}
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
	}
	public int getCnsSeq() {
		return cnsSeq;
	}
	public void setCnsSeq(int cnsSeq) {
		this.cnsSeq = cnsSeq;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
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
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	 
	
}	

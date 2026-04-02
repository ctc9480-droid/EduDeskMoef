package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 */
public class UserLang{
	private String userId;
	private int langSeq;
	private String langNm;		//외국어명
	private String testNm;		//시험명
	private String mark;		//성적
	private Date testDt;		//시험일자
	private String updId;
	private Date updDe;
	
	private String testDtStr;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getLangSeq() {
		return langSeq;
	}
	public void setLangSeq(int langSeq) {
		this.langSeq = langSeq;
	}
	public String getLangNm() {
		return langNm;
	}
	public void setLangNm(String langNm) {
		this.langNm = langNm;
	}
	public String getTestNm() {
		return testNm;
	}
	public void setTestNm(String testNm) {
		this.testNm = testNm;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Date getTestDt() {
		return testDt;
	}
	public void setTestDt(Date testDt) {
		this.testDt = testDt;
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
	public String getTestDtStr() {
		return testDtStr;
	}
	public void setTestDtStr(String testDtStr) {
		this.testDtStr = testDtStr;
	}

	
}

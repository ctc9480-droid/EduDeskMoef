package com.educare.edu.comn.vo;

import java.util.Date;

import com.educare.edu.comn.model.ContentHtml;


public class CnhtmlVO extends ContentHtml {
	private int page=1;
	private int row=10;
	private int firstIndex=0;
	
	private String srchWrd;
	private String srchCnhtType;
	
	private String regNm;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getSrchWrd() {
		return srchWrd;
	}
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public String getSrchCnhtType() {
		return srchCnhtType;
	}
	public void setSrchCnhtType(String srchCnhtType) {
		this.srchCnhtType = srchCnhtType;
	}
}

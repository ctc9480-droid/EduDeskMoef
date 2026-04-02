package com.educare.edu.comn.vo;

import java.util.Date;

import com.educare.edu.comn.model.PassCertTpl;

public class PassCertTplVO extends PassCertTpl{
	private String srchWrd;
	private int rowCnt;
	private int firstIndex;
	private String regNm;
	public String getSrchWrd() {
		return srchWrd;
	}

	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
	}

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

	public String getRegNm() {
		return regNm;
	}

	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	
}

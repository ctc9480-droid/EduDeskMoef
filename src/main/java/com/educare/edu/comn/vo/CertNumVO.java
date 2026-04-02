package com.educare.edu.comn.vo;

import java.util.Date;

public class CertNumVO{
	private int ctgrySeq;
	private int subSeq;
	private int certNum;
	private String year;
	private int certMode;
	

	public int getCtgrySeq() {
		return ctgrySeq;
	}
	public void setCtgrySeq(int ctgrySeq) {
		this.ctgrySeq = ctgrySeq;
	}
	public int getCertNum() {
		return certNum;
	}
	public void setCertNum(int certNum) {
		this.certNum = certNum;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return "CertNumVO [year=" + year + "]";
	}
	public int getSubSeq() {
		return subSeq;
	}
	public void setSubSeq(int subSeq) {
		this.subSeq = subSeq;
	}
	public int getCertMode() {
		return certMode;
	}
	public void setCertMode(int certMode) {
		this.certMode = certMode;
	}
	
}

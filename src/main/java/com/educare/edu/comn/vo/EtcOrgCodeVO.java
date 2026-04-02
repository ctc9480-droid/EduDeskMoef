package com.educare.edu.comn.vo;

import java.util.Date;

public class EtcOrgCodeVO {
	private String orgCd;
	private String fullNm;
	private String lowNm;
	
	private String srchWrd;
	private int pageNo=1;
	private int firstRecordIndex=0;
	private int recordCountPerPage=10;
	
	
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public String getFullNm() {
		return fullNm;
	}
	public void setFullNm(String fullNm) {
		this.fullNm = fullNm;
	}
	public String getLowNm() {
		return lowNm;
	}
	public void setLowNm(String lowNm) {
		this.lowNm = lowNm;
	}
	public String getSrchWrd() {
		return srchWrd;
	}
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
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
	
	
}

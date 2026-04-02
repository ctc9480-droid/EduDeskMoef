package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 */
public class UserCertif{
	private String userId;
	private int crtfSeq;
	private String crtfNm;
	private Date crtfDt;
	private String issueNm;
	private String updId;
	private Date updDe;
	
	private String crtfDtStr;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getCrtfSeq() {
		return crtfSeq;
	}
	public void setCrtfSeq(int crtfSeq) {
		this.crtfSeq = crtfSeq;
	}
	public String getCrtfNm() {
		return crtfNm;
	}
	public void setCrtfNm(String crtfNm) {
		this.crtfNm = crtfNm;
	}
	public Date getCrtfDt() {
		return crtfDt;
	}
	public void setCrtfDt(Date crtfDt) {
		this.crtfDt = crtfDt;
	}
	public String getIssueNm() {
		return issueNm;
	}
	public void setIssueNm(String issueNm) {
		this.issueNm = issueNm;
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
	public String getCrtfDtStr() {
		return crtfDtStr;
	}
	public void setCrtfDtStr(String crtfDtStr) {
		this.crtfDtStr = crtfDtStr;
	}
	
}

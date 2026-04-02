package com.educare.edu.comn.model;

import java.util.Date;

public class ContentHtml {
	private int cnhtSeq;
	private String cnhtType;
	private String title;
	private String content;
	private Date regDe;
	private String regId;
	private Date updDe;
	private String updId;
	private int state;
	public ContentHtml() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ContentHtml(int cnhtSeq, String cnhtType,String title, String content, Date regDe, String regId, Date updDe, String updId, int state) {
		super();
		this.cnhtSeq = cnhtSeq;
		this.cnhtType = cnhtType;
		this.title = title;
		this.content = content;
		this.regDe = regDe;
		this.regId = regId;
		this.updDe = updDe;
		this.updId = updId;
		this.state = state;
	}

	public int getCnhtSeq() {
		return cnhtSeq;
	}
	public void setCnhtSeq(int cnhtSeq) {
		this.cnhtSeq = cnhtSeq;
	}
	public String getCnhtType() {
		return cnhtType;
	}
	public void setCnhtType(String cnhtType) {
		this.cnhtType = cnhtType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegDe() {
		return regDe;
	}
	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getUpdDe() {
		return updDe;
	}
	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	
	
}

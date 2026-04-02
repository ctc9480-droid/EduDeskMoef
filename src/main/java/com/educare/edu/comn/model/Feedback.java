package com.educare.edu.comn.model;

import java.util.Date;

public class Feedback{
	private int idx;
	private String orgCd;
	private String title;
	private int status;
	private Date regTime;
	private String regId;
	private String regNm;
	private Date modTime;
	private String modId;
	private String content;
	
	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Feedback(int idx,String orgCd, String title, int status, Date regTime, String regId, String regNm, Date modTime, String modId, String content) {
		super();
		this.idx=idx;
		this.orgCd = orgCd;
		this.title = title;
		this.status = status;
		this.regTime = regTime;
		this.regId = regId;
		this.regNm = regNm;
		this.modTime = modTime;
		this.modId = modId;
		this.content = content;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public Date getModTime() {
		return modTime;
	}
	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}

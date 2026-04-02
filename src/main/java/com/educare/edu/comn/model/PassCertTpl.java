package com.educare.edu.comn.model;

import java.util.Date;

public class PassCertTpl{
	private int passIdx;
	private String title;
	private String content;
	private String orgNm;
	private String jspNm;
	private int passTp;
	private String prefix;
	
	private Date regDt;
	private String regId;
	private Date modDt;
	private String modId;
	public int getPassIdx() {
		return passIdx;
	}
	public void setPassIdx(int passIdx) {
		this.passIdx = passIdx;
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
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getJspNm() {
		return jspNm;
	}
	public void setJspNm(String jspNm) {
		this.jspNm = jspNm;
	}
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getModDt() {
		return modDt;
	}
	public void setModDt(Date modDt) {
		this.modDt = modDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public int getPassTp() {
		return passTp;
	}
	public void setPassTp(int passTp) {
		this.passTp = passTp;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	
	
}

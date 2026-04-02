package com.educare.edu.comn.model;

import java.util.Date;

public class FeedbackOpen{
	private int idx;
	private String orgCd;
	private String title;
	private int status;
	private Date regTime;
	private String regId;
	private String regNm;
	private Date modTime;
	private String modId;
	private int fbIdx;
	private String startDtime;
	private String endDtime;
	private int width;
	private int height;
	private int posX;
	private int posY;
	public FeedbackOpen() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FeedbackOpen(int idx, String orgCd, String title, int status, Date regTime, String regId, String regNm, Date modTime, String modId, int fbIdx, String startDtime, String endDtime, int width, int height, int posX, int posY) {
		super();
		this.idx = idx;
		this.orgCd = orgCd;
		this.title = title;
		this.status = status;
		this.regTime = regTime;
		this.regId = regId;
		this.regNm = regNm;
		this.modTime = modTime;
		this.modId = modId;
		this.fbIdx = fbIdx;
		this.startDtime = startDtime;
		this.endDtime = endDtime;
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.posY = posY;
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
	public int getFbIdx() {
		return fbIdx;
	}
	public void setFbIdx(int fbIdx) {
		this.fbIdx = fbIdx;
	}
	public String getStartDtime() {
		return startDtime;
	}
	public void setStartDtime(String startDtime) {
		this.startDtime = startDtime;
	}
	public String getEndDtime() {
		return endDtime;
	}
	public void setEndDtime(String endDtime) {
		this.endDtime = endDtime;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
}

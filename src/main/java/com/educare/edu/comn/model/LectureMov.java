package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class LectureMov{
	private int idx;
	private String orgCd;
	private String title;
	private String content;
	private String fileOrg;
	private String fileRename;
	private String instrctrNm;
	private Date regTime;
	private String regId;
	private Date modTime;
	private String duration;
	private String chapterData;
	public LectureMov(){
	}

	public LectureMov(int idx,String orgCd, String title, String content, String fileOrg, String fileRename, String instrctrNm, Date regTime, String regId, Date modTime,String duration) {
		super();
		this.idx=idx;
		this.orgCd = orgCd;
		this.title = title;
		this.content = content;
		this.fileOrg = fileOrg;
		this.fileRename = fileRename;
		this.instrctrNm = instrctrNm;
		this.regTime = regTime;
		this.regId = regId;
		this.modTime = modTime;
		this.setDuration(duration);
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileOrg() {
		return fileOrg;
	}

	public void setFileOrg(String fileOrg) {
		this.fileOrg = fileOrg;
	}

	public String getFileRename() {
		return fileRename;
	}

	public void setFileRename(String fileRename) {
		this.fileRename = fileRename;
	}

	public String getInstrctrNm() {
		return instrctrNm;
	}

	public void setInstrctrNm(String instrctrNm) {
		this.instrctrNm = instrctrNm;
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

	public Date getModTime() {
		return modTime;
	}

	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getChapterData() {
		return chapterData;
	}

	public void setChapterData(String chapterData) {
		this.chapterData = chapterData;
	}

	
	
}

package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class LectureMovTime{
	private int idx;
	private int mvIdx;
	private String title;
	private String url;
	private String mvTime;
	private int type;
	public LectureMovTime() {
		super();
	}
	public LectureMovTime(int idx, int mvIdx, String title, String url, String mvTime, int type) {
		this.idx = idx;
		this.mvIdx = mvIdx;
		this.title = title;
		this.url = url;
		this.mvTime = mvTime;
		this.type = type;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getMvIdx() {
		return mvIdx;
	}
	public void setMvIdx(int mvIdx) {
		this.mvIdx = mvIdx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMvTime() {
		return mvTime;
	}
	public void setMvTime(String mvTime) {
		this.mvTime = mvTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	
}

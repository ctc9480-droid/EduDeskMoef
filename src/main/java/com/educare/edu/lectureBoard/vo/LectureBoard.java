package com.educare.edu.lectureBoard.vo;

/**
 */
public class LectureBoard {
	private int idx;
	private String boardType;
	private Integer eduSeq;
	private int pIdx=0;
	private String title; 
	private String content;
	private int hit=1;
	protected String regId;
	protected String regNm;
	private String regDtime;
	private String modId;
	private String modNm;
	private String modDtime;
	private Integer status=1;
	private String thumbFile;
	private String fileTypeGb;
	private String scrtyYn;
	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public Integer getEduSeq() {
		return eduSeq;
	}

	public void setEduSeq(Integer eduSeq) {
		this.eduSeq = eduSeq;
	}

	public int getpIdx() {
		return pIdx;
	}

	public void setpIdx(int pIdx) {
		this.pIdx = pIdx;
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

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
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

	public String getRegDtime() {
		return regDtime;
	}

	public void setRegDtime(String regDtime) {
		this.regDtime = regDtime;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getModNm() {
		return modNm;
	}

	public void setModNm(String modNm) {
		this.modNm = modNm;
	}

	public String getModDtime() {
		return modDtime;
	}

	public void setModDtime(String modDtime) {
		this.modDtime = modDtime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getThumbFile() {
		return thumbFile;
	}

	public void setThumbFile(String thumbFile) {
		this.thumbFile = thumbFile;
	}

	public String getFileTypeGb() {
		return fileTypeGb;
	}

	public void setFileTypeGb(String fileTypeGb) {
		this.fileTypeGb = fileTypeGb;
	}

	public String getScrtyYn() {
		return scrtyYn;
	}

	public void setScrtyYn(String scrtyYn) {
		this.scrtyYn = scrtyYn;
	}

	
}	

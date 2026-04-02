package com.educare.edu.comn.vo;

import java.util.Date;

public class ExcelLogVO {
	private int idx;
	private String excelTy;
	private String content;
	private String userId;
	private String userNm;
	private int excelEduSeq;
	private String regId;
	private Date regDt;
	private String modId;
	private Date modDt;
	
	
	public ExcelLogVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ExcelLogVO(String excelTy,String content, String userId, String userNm, int excelEduSeq, String regId, Date regDt, String modId, Date modDt) {
		super();
		this.excelTy = excelTy;
		this.content = content;
		this.userId = userId;
		this.userNm = userNm;
		this.excelEduSeq = excelEduSeq;
		this.regId = regId;
		this.regDt = regDt;
		this.modId = modId;
		this.modDt = modDt;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getExcelTy() {
		return excelTy;
	}
	public void setExcelTy(String excelTy) {
		this.excelTy = excelTy;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public int getExcelEduSeq() {
		return excelEduSeq;
	}
	public void setExcelEduSeq(int excelEduSeq) {
		this.excelEduSeq = excelEduSeq;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public Date getModDt() {
		return modDt;
	}
	public void setModDt(Date modDt) {
		this.modDt = modDt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}

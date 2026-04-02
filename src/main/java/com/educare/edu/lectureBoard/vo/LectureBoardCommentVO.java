package com.educare.edu.lectureBoard.vo;

import java.util.Date;

import com.educare.edu.member.service.SessionUserInfoHelper;

/**
 */
public class LectureBoardCommentVO {
	private int idx=0;
	private int bIdx;
	private int pIdx;
	private int eduSeq;
	private String content;
	private String regId;
	private String regNm;
	private String regNk;
	private Date regDt;
	private String modId;
	private String modNm;
	private Date modDt;
	private int status=1;
	private int gIdx;
	private String gOrd="0";
	private int gLvl=0;
	private int userMemLvl;
	
	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getbIdx() {
		return bIdx;
	}

	public void setbIdx(int bIdx) {
		this.bIdx = bIdx;
	}

	public Date getRegDt() {
		return regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	public Date getModDt() {
		return modDt;
	}

	public void setModDt(Date modDt) {
		this.modDt = modDt;
	}

	public String getRegNk() {
		return regNk;
	}

	public void setRegNk(String regNk) {
		this.regNk = regNk;
	}

	public int getgIdx() {
		return gIdx;
	}

	public void setgIdx(int gIdx) {
		this.gIdx = gIdx;
	}

	public String getgOrd() {
		return gOrd;
	}

	public void setgOrd(String gOrd) {
		this.gOrd = gOrd;
	}

	public int getgLvl() {
		return gLvl;
	}

	public void setgLvl(int gLvl) {
		this.gLvl = gLvl;
	}
	
	public int getUserMemLvl() {
		return userMemLvl;
	}
	
	public void setUserMemLvl(int userMemLvl) {
		this.userMemLvl = userMemLvl;
	}
	
	public String getAddRegNmMasked(){
		try {
			if(userMemLvl<5 /*|| SessionUserInfoHelper.getUserId().equals(this.regId)*/){
				return this.regNm;
			}else{
				return this.regNm.substring(0,1)+"OO";
			}
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
}	

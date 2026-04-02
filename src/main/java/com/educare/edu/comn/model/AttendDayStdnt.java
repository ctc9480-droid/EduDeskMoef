package com.educare.edu.comn.model;

import java.util.Date;

public class AttendDayStdnt{
	private int eduSeq;
	private String eduDt;
	private String userId;
	private String attCd;
	private Date beginDe;
	private Date endDe;
	private int attTp;
	private String regId;
	private Date regDe;
	private String updId;
	private Date updDe;
	private String at1Cd;
	private String at2Cd;
	private String at3Cd;
	private Date at1De;
	private Date at2De;
	private Date at3De;
	
	public AttendDayStdnt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AttendDayStdnt(int eduSeq, String eduDt, String userId, String attCd, Date beginDe, Date endDe, int attTp, String regId, Date regDe, String updId, Date updDe) {
		super();
		this.setEduSeq(eduSeq);
		this.setEduDt(eduDt);
		this.setUserId(userId);
		this.setAttCd(attCd);
		this.setBeginDe(beginDe);
		this.setEndDe(endDe);
		this.attTp = attTp;
		this.setRegId(regId);
		this.setRegDe(regDe);
		this.updDe = updDe;
		this.updId = updId;
	}

	public int getEduSeq() {
		return eduSeq;
	}

	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}

	public String getEduDt() {
		return eduDt;
	}

	public void setEduDt(String eduDt) {
		this.eduDt = eduDt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAttCd() {
		return attCd;
	}

	public void setAttCd(String attCd) {
		this.attCd = attCd;
	}

	public Date getBeginDe() {
		return beginDe;
	}

	public void setBeginDe(Date beginDe) {
		this.beginDe = beginDe;
	}

	public Date getEndDe() {
		return endDe;
	}

	public void setEndDe(Date endDe) {
		this.endDe = endDe;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public Date getRegDe() {
		return regDe;
	}

	public void setRegDe(Date regDe) {
		this.regDe = regDe;
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

	public int getAttTp() {
		return attTp;
	}

	public void setAttTp(int attTp) {
		this.attTp = attTp;
	}

	public String getAt1Cd() {
		return at1Cd;
	}

	public void setAt1Cd(String at1Cd) {
		this.at1Cd = at1Cd;
	}

	public String getAt2Cd() {
		return at2Cd;
	}

	public void setAt2Cd(String at2Cd) {
		this.at2Cd = at2Cd;
	}

	public String getAt3Cd() {
		return at3Cd;
	}

	public void setAt3Cd(String at3Cd) {
		this.at3Cd = at3Cd;
	}

	public Date getAt1De() {
		return at1De;
	}

	public void setAt1De(Date at1De) {
		this.at1De = at1De;
	}

	public Date getAt2De() {
		return at2De;
	}

	public void setAt2De(Date at2De) {
		this.at2De = at2De;
	}

	public Date getAt3De() {
		return at3De;
	}

	public void setAt3De(Date at3De) {
		this.at3De = at3De;
	}

	
}

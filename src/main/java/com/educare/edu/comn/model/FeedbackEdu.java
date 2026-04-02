package com.educare.edu.comn.model;

import java.util.Date;

public class FeedbackEdu{
	private int feSeq;			
	private int eduSeq;			
	private int fbIdx;			
	private String fbNm;			
	private Date startDe;		
	private Date endDe;		
	private int state;			
	
	private String regId;
	private String updId;
	private Date regDe;
	private Date updDe;
	
	public FeedbackEdu(int feSeq, int eduSeq, int fbIdx,String fbNm, Date startDe, Date endDe, int state, String regId, String updId, Date regDe, Date updDe) {
		super();
		this.feSeq = feSeq;
		this.eduSeq = eduSeq;
		this.fbIdx = fbIdx;
		this.startDe = startDe;
		this.endDe = endDe;
		this.state = state;
		this.regId = regId;
		this.updId = updId;
		this.regDe = regDe;
		this.updDe = updDe;
		this.fbNm = fbNm;
	}
	public FeedbackEdu() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getFeSeq() {
		return feSeq;
	}
	public void setFeSeq(int feSeq) {
		this.feSeq = feSeq;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public int getFbIdx() {
		return fbIdx;
	}
	public void setFbIdx(int fbIdx) {
		this.fbIdx = fbIdx;
	}
	public Date getStartDe() {
		return startDe;
	}
	public void setStartDe(Date startDe) {
		this.startDe = startDe;
	}
	public Date getEndDe() {
		return endDe;
	}
	public void setEndDe(Date endDe) {
		this.endDe = endDe;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public Date getRegDe() {
		return regDe;
	}
	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}
	public Date getUpdDe() {
		return updDe;
	}
	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}
	public String getFbNm() {
		return fbNm;
	}
	public void setFbNm(String fbNm) {
		this.fbNm = fbNm;
	}
	
	
}

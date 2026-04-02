package com.educare.edu.comn.model;

import org.apache.commons.lang3.StringUtils;

import com.educare.component.VarComponent;

public class LectureTime{
	private int eduSeq;
	private int subSeq;
	private int timeSeq;
	private String eduDt;
	private String startTm;
	private String endTm;
	private String description;
	private String instrNm;
	private String checkId;
	private String checkNm;
	private int status;
	private String gooroomeeId;
	private int classHow;//0:오프라인,1:온라인,2:구루미
	private String url;
	private String urlPw;
	private int freeView;
	private String authNo;
	private String checkLocate;
	private int checkStatus;
	private int checkType;
	private int mvIdx;
	private int roomSeq;
	private String roomNm;
	private String ncsNm;
	private String mvNm;
	
	//add
	private String srchStartDt;
	private String srchEndDt;
	private String eduNm;
	private String subNm;
	
	public LectureTime(){
	}
	public LectureTime(int eduSeq,int timeSeq){
		this.eduSeq=eduSeq;
		this.timeSeq=timeSeq;
	}
	
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public int getTimeSeq() {
		return timeSeq;
	}
	public void setTimeSeq(int timeSeq) {
		this.timeSeq = timeSeq;
	}
	public String getEduDt() {
		return eduDt;
	}
	public void setEduDt(String eduDt) {
		this.eduDt = eduDt;
	}
	public String getStartTm() {
		return startTm;
	}
	public void setStartTm(String startTm) {
		this.startTm = startTm;
	}
	public String getEndTm() {
		return endTm;
	}
	public void setEndTm(String endTm) {
		this.endTm = endTm;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInstrNm() {
		return instrNm;
	}
	public void setInstrNm(String instrNm) {
		this.instrNm = instrNm;
	}
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	//추가 변수
	private String eduDt2;
	
	//추가 함수
	public String getStartHour(){
		if(!StringUtils.isEmpty(this.startTm)){
			return this.startTm.substring(0,2);
		}
		return "00";
	}
	public String getStartMin(){
		if(!StringUtils.isEmpty(this.startTm)){
			return this.startTm.substring(2,4);
		}
		return "00";
	}
	public String getEndHour(){
		if(!StringUtils.isEmpty(this.endTm)){
			return this.endTm.substring(0,2);
		}
		return "00";
	}
	public String getEndMin(){
		if(!StringUtils.isEmpty(this.endTm)){
			return this.endTm.substring(2,4);
		}
		return "00";
	}
	public String getEduDt2() {
		if(!StringUtils.isEmpty(eduDt)){
			return eduDt.substring(0,4)+"."+eduDt.substring(4,6)+"."+eduDt.substring(6,8);
		}
		return eduDt2;
	}
	public void setEduDt2(String eduDt2) {
		this.eduDt2 = eduDt2;
	}
	public String getCheckNm() {
		return checkNm;
	}
	public void setCheckNm(String checkNm) {
		this.checkNm = checkNm;
	}
	public int getClassHow() {
		return classHow;
	}
	public void setClassHow(int classHow) {
		this.classHow = classHow;
	}
	public String getGooroomeeId() {
		return gooroomeeId;
	}
	public void setGooroomeeId(String gooroomeeId) {
		this.gooroomeeId = gooroomeeId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getclassHowNm(){
		String result = "-";
		try {
			result = VarComponent.CLASS_HOW[this.classHow];
		} catch (NullPointerException e) {
			return result;
		}
		
		return result;
	}
	public int getFreeView() {
		return freeView;
	}
	public void setFreeView(int freeView) {
		this.freeView = freeView;
	}
	public String getAuthNo() {
		return authNo;
	}
	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}
	public String getCheckLocate() {
		return checkLocate;
	}
	public void setCheckLocate(String checkLocate) {
		this.checkLocate = checkLocate;
	}
	public int getCheckType() {
		return checkType;
	}
	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}
	public int getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public String getStartTmNm2(){
		try {
			return this.startTm.substring(0,2)+":"+this.startTm.substring(2,4);
		} catch (NullPointerException e) {
			return "-";
		} 
	}
	public String getEndTmNm2(){
		try {
			return this.endTm.substring(0,2)+":"+this.endTm.substring(2,4);
		} catch (NullPointerException e) {
			return "-";
		} 
	}
	public int getMvIdx() {
		return mvIdx;
	}
	public void setMvIdx(int mvIdx) {
		this.mvIdx = mvIdx;
	}
	public String getUrlPw() {
		return urlPw;
	}
	public void setUrlPw(String urlPw) {
		this.urlPw = urlPw;
	}
	public int getSubSeq() {
		return subSeq;
	}
	public void setSubSeq(int subSeq) {
		this.subSeq = subSeq;
	}
	public int getRoomSeq() {
		return roomSeq;
	}
	public void setRoomSeq(int roomSeq) {
		this.roomSeq = roomSeq;
	}
	public String getNcsNm() {
		return ncsNm;
	}
	public void setNcsNm(String ncsNm) {
		this.ncsNm = ncsNm;
	}
	public String getRoomNm() {
		return roomNm;
	}
	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}
	public String getMvNm() {
		return mvNm;
	}
	public void setMvNm(String mvNm) {
		this.mvNm = mvNm;
	}
	public String getSrchStartDt() {
		return srchStartDt;
	}
	public void setSrchStartDt(String srchStartDt) {
		this.srchStartDt = srchStartDt;
	}
	public String getSrchEndDt() {
		return srchEndDt;
	}
	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}
	public String getEduNm() {
		return eduNm;
	}
	public void setEduNm(String eduNm) {
		this.eduNm = eduNm;
	}
	public String getSubNm() {
		return subNm;
	}
	public void setSubNm(String subNm) {
		this.subNm = subNm;
	}
}

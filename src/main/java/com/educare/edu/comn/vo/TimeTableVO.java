package com.educare.edu.comn.vo;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.educare.component.VarComponent;

/**
 * 시간표 
 */
public class TimeTableVO{
	private int eduSeq;
	private int timeSeq;
	private String eduDt;
	private String startTm;
	private String endTm;
	private String description;
	private String instrNm;
	private String checkId;
	private String checkNm;
	private int status;
	private int classHow;//0:오프라인,1:온라인,2:구루미
	private String url;
	private int freeView;
	private String movTime;
	private String movAllTime;
	private String attCd;
	private String urlPw;
	private int roomSeq;
	private String roomNm;
	private int gyosi;
	private String ncsNm;
	private int mvIdx;
	private String mvNm;
	private String subNm;
	
	private int historyCnt;
	private String userId;
	private int rowCnt = 1;
	
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
	public int getHistoryCnt() {
		return historyCnt;
	}
	public void setHistoryCnt(int historyCnt) {
		this.historyCnt = historyCnt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMovTime() {
		return movTime;
	}
	public void setMovTime(String movTime) {
		this.movTime = movTime;
	}
	public String getMovAllTime() {
		return movAllTime;
	}
	public void setMovAllTime(String movAllTime) {
		this.movAllTime = movAllTime;
	}
	public String getAttCd() {
		return attCd;
	}
	public void setAttCd(String attCd) {
		this.attCd = attCd;
	}
	
	public String getAddMovRatio(){
		try {
			double al = Double.parseDouble(this.movTime);
			double bl = Double.parseDouble(this.movAllTime);
			String result = String.valueOf(Math.round(al/bl*100.0*10)/10.0);
			return result;
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
	public String getUrlPw() {
		return urlPw;
	}
	public void setUrlPw(String urlPw) {
		this.urlPw = urlPw;
	}
	public int getRoomSeq() {
		return roomSeq;
	}
	public void setRoomSeq(int roomSeq) {
		this.roomSeq = roomSeq;
	}
	public String getRoomNm() {
		return roomNm;
	}
	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}
	public int getGyosi() {
		return gyosi;
	}
	public void setGyosi(int gyosi) {
		this.gyosi = gyosi;
	}
	public String getNcsNm() {
		return ncsNm;
	}
	public void setNcsNm(String ncsNm) {
		this.ncsNm = ncsNm;
	}
	public int getMvIdx() {
		return mvIdx;
	}
	public void setMvIdx(int mvIdx) {
		this.mvIdx = mvIdx;
	}
	public String getMvNm() {
		return mvNm;
	}
	public void setMvNm(String mvNm) {
		this.mvNm = mvNm;
	}
	public String getSubNm() {
		return subNm;
	}
	public void setSubNm(String subNm) {
		this.subNm = subNm;
	}
	public int getRowCnt() {
		return rowCnt;
	}
	public void setRowCnt(int rowCnt) {
		this.rowCnt = rowCnt;
	}
	
}

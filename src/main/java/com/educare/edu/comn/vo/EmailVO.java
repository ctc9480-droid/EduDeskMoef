package com.educare.edu.comn.vo;

import java.util.Date;

import com.educare.component.VarComponent;
import com.educare.util.CryptoSeedUtil;

public class EmailVO{
	private int row=10;
	private int page=1;
	private int firstIndex=1;
	
	private int idx;
	private int sendType;
	private String title;
	private String text;
	private String fromAddr;
	private String toAddr;
	private int resultCd;
	private String resultMsg;
	private Date regDt;
	private Date sendDt;
	private int eduSeq;
	private String userId;
	private String tplCd;
	
//	private String tableMonth;
	private String srchBeginDt;
	private String srchWrd;
	
	private String message2;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getSendType() {
		return sendType;
	}
	public void setSendType(int sendType) {
		this.sendType = sendType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getFromAddr() {
		return fromAddr;
	}
	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}
	public String getToAddr() {
		//return EhCacheUtil.getUserPrvt(this.userId).getMobile();
		return toAddr;
	}
	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}
	public int getResultCd() {
		return resultCd;
	}
	public void setResultCd(int resultCd) {
		this.resultCd = resultCd;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public Date getSendDt() {
		return sendDt;
	}
	public void setSendDt(Date sendDt) {
		this.sendDt = sendDt;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	
	public String getAddResultNm(){
		return VarComponent.getSmsResultNm(this.resultCd);
	}
	public String getDecToAddr(){
		try {
			return CryptoSeedUtil.decrypt(this.toAddr);
		} catch (NullPointerException e) {
			return this.toAddr;
		}
	}
	public String getTplCd() {
		return tplCd;
	}
	public void setTplCd(String tplCd) {
		this.tplCd = tplCd;
	}
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
	}
//	public String getTableMonth() {
//		return tableMonth;
//	}
//	public void setTableMonth(String tableMonth) {
//		this.tableMonth = tableMonth;
//	}
	public String getSrchBeginDt() {
		return srchBeginDt;
	}
	public void setSrchBeginDt(String srchBeginDt) {
		this.srchBeginDt = srchBeginDt;
	}
	public String getSrchWrd() {
		return srchWrd;
	}
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
	}
}

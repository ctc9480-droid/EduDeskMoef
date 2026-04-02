package com.educare.edu.comn.vo;

import java.util.Date;
import java.util.List;

/**
 */
public class MovVO{
	
	//
	private int idx;
	private String orgCd;
	private String title;
	private String instrctrNm;
	private String content;
	private String fileOrg;
	private String fileRename;
	private Date regTime;
	private String regId;
	private Date modTime;
	private String duration;
	
	private int pageNo=1;
	private int firstRecordIndex=0;
	private int recordCountPerPage=10;
	private String srchWrd;
	
	//진행률관련
	private int eduSeq;
	private int timeSeq;
	private Date minRegDate;
	private Date maxRegDate;
	private String email;
	private String stdntNm;
	private String movTime;
	private String movAllTime;
	
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
	public Date getMinRegDate() {
		return minRegDate;
	}
	public void setMinRegDate(Date minRegDate) {
		this.minRegDate = minRegDate;
	}
	public Date getMaxRegDate() {
		return maxRegDate;
	}
	public void setMaxRegDate(Date maxRegDate) {
		this.maxRegDate = maxRegDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStdntNm() {
		return stdntNm;
	}
	public void setStdntNm(String stdntNm) {
		this.stdntNm = stdntNm;
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
	public String getInstrctrNm() {
		return instrctrNm;
	}
	public void setInstrctrNm(String instrctrNm) {
		this.instrctrNm = instrctrNm;
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
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getFirstRecordIndex() {
		return firstRecordIndex;
	}
	public void setFirstRecordIndex(int firstRecordIndex) {
		this.firstRecordIndex = firstRecordIndex;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	public String getSrchWrd() {
		return srchWrd;
	}
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
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
	
	
}

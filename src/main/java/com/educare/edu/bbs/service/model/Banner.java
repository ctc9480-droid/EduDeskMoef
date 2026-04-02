package com.educare.edu.bbs.service.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import com.educare.component.UtilComponent;
import com.educare.util.DateUtil;

public class Banner {
	//테이블컬럼
	private int idx;
	private String title;
	private int status=1;
	private String startDtime;
	private String endDtime;
	private String regId;
	private String regNm;
	private Date regDt;
	private String fileImgNm;
	private String fileImgRenm;
	private String url;
	private int sort;
	
	//검색컬럼
	private int pageNo=1;
	private int firstRecordIndex=0;
	private int recordCountPerPage=10;
	private String searchSelect;
	private String searchWord;
	private int searchStatus;
	private String searchNowDtime;
	private String nowDtime;
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getTitle() {
		if(title!=null){
			if(title.equals("")){
				return null;
			}
			return title;
		}
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
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
	public String getSearchSelect() {
		return searchSelect;
	}
	public void setSearchSelect(String searchSelect) {
		this.searchSelect = searchSelect;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public int getSearchStatus() {
		return searchStatus;
	}
	public void setSearchStatus(int searchStatus) {
		this.searchStatus = searchStatus;
	}
	public String getSearchNowDtime() {
		return searchNowDtime;
	}
	public void setSearchNowDtime(String searchNowDtime) {
		this.searchNowDtime = searchNowDtime;
	}
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public String getStartDtime() {
		return startDtime;
	}
	public void setStartDtime(String startDtime) {
		this.startDtime = startDtime;
	}
	public String getEndDtime() {
		return endDtime;
	}
	public void setEndDtime(String endDtime) {
		this.endDtime = endDtime;
	}
	
	public String getFileImgNm() {
		return fileImgNm;
	}
	public void setFileImgNm(String fileImgNm) {
		this.fileImgNm = fileImgNm;
	}
	public String getFileImgRenm() {
		return fileImgRenm;
	}
	public void setFileImgRenm(String fileImgRenm) {
		this.fileImgRenm = fileImgRenm;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	//////////////////////////////////////////////
	public Date getAddNowDt(){
		return DateUtil.getCalendar().getTime();
	}
	public String getAddStatusNm(){
		try {
			String[] statusArr = {"삭제","게시","미게시"};
			return statusArr[this.status];
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
	public String getNowDtime() {
		return nowDtime;
	}
	public void setNowDtime(String nowDtime) {
		this.nowDtime = nowDtime;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
}

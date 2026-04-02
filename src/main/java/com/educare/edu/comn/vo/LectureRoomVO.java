package com.educare.edu.comn.vo;

import com.educare.edu.comn.model.LectureRoom;

/**
 */
public class LectureRoomVO extends LectureRoom{
	private int rowCnt = 10;
	private int firstIndex = 1;
	private int page = 1;
	private String srchWrd;
	public int getRowCnt() {
		return rowCnt;
	}
	public void setRowCnt(int rowCnt) {
		this.rowCnt = rowCnt;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getSrchWrd() {
		return srchWrd;
	}
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
	}
	
}

package com.educare.edu.comn.vo;

import java.util.Date;

public class EtcGradeCodeVO {
	private String gradeCd;
	private String gradeNm;
	private String gongGbCd;
	private String jikjongCd;
	private String jikgunCd;
	private String jikryulCd;
	private String jikryuCd;
	private int closeSt;		//1:폐기
	private String jikjongNm;
	private String jikryulNm;
	private String jikryuNm;
	
	
	private String srchWrd;
	private int pageNo=1;
	private int firstRecordIndex=0;
	private int recordCountPerPage=10;
	
	
	public String getSrchWrd() {
		return srchWrd;
	}
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
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
	public String getGradeCd() {
		return gradeCd;
	}
	public void setGradeCd(String gradeCd) {
		this.gradeCd = gradeCd;
	}
	public String getGradeNm() {
		return gradeNm;
	}
	public void setGradeNm(String gradeNm) {
		this.gradeNm = gradeNm;
	}
	public String getGongGbCd() {
		return gongGbCd;
	}
	public void setGongGbCd(String gongGbCd) {
		this.gongGbCd = gongGbCd;
	}
	public String getJikjongCd() {
		return jikjongCd;
	}
	public void setJikjongCd(String jikjongCd) {
		this.jikjongCd = jikjongCd;
	}
	public String getJikgunCd() {
		return jikgunCd;
	}
	public void setJikgunCd(String jikgunCd) {
		this.jikgunCd = jikgunCd;
	}
	public String getJikryulCd() {
		return jikryulCd;
	}
	public void setJikryulCd(String jikryulCd) {
		this.jikryulCd = jikryulCd;
	}
	public String getJikryuCd() {
		return jikryuCd;
	}
	public void setJikryuCd(String jikryuCd) {
		this.jikryuCd = jikryuCd;
	}
	public int getCloseSt() {
		return closeSt;
	}
	public void setCloseSt(int closeSt) {
		this.closeSt = closeSt;
	}
	public String getJikjongNm() {
		return jikjongNm;
	}
	public void setJikjongNm(String jikjongNm) {
		this.jikjongNm = jikjongNm;
	}
	public String getJikryulNm() {
		return jikryulNm;
	}
	public void setJikryulNm(String jikryulNm) {
		this.jikryulNm = jikryulNm;
	}
	public String getJikryuNm() {
		return jikryuNm;
	}
	public void setJikryuNm(String jikryuNm) {
		this.jikryuNm = jikryuNm;
	}
	
	
}

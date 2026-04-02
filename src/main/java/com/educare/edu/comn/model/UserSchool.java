package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;



/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSchool{
	private String userId;
	private int schlSeq;
	private Date beginDt;
	private Date endDt;
	private String schoolNm;
	private String majorNm;		//학과
	private String submjrNm;	//복수전공/부전공
	private String location;
	private int grdtTp;			//졸업구분, 1:졸업, 2:수료
	private double mark;		//성적
	private double fullMark;	//만점
	private String updId;
	private Date updDe;
	
	//add
	private String beginDtStr;
	private String endDtStr;
	
	private final static String[] GRDT_TP_NM_ARR = {"-","수료","졸업"};
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getSchlSeq() {
		return schlSeq;
	}
	public void setSchlSeq(int schlSeq) {
		this.schlSeq = schlSeq;
	}
	public Date getBeginDt() {
		return beginDt;
	}
	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	public String getSchoolNm() {
		return schoolNm;
	}
	public void setSchoolNm(String schoolNm) {
		this.schoolNm = schoolNm;
	}
	public String getMajorNm() {
		return majorNm;
	}
	public void setMajorNm(String majorNm) {
		this.majorNm = majorNm;
	}
	public String getSubmjrNm() {
		return submjrNm;
	}
	public void setSubmjrNm(String submjrNm) {
		this.submjrNm = submjrNm;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getGrdtTp() {
		return grdtTp;
	}
	public void setGrdtTp(int grdtTp) {
		this.grdtTp = grdtTp;
	}
	public double getMark() {
		return mark;
	}
	public void setMark(double mark) {
		this.mark = mark;
	}
	public double getFullMark() {
		return fullMark;
	}
	public void setFullMark(double fullMark) {
		this.fullMark = fullMark;
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

	public String getBeginDtStr() {
		return beginDtStr;
	}

	public void setBeginDtStr(String beginDtStr) {
		this.beginDtStr = beginDtStr;
	}

	public String getEndDtStr() {
		return endDtStr;
	}

	public void setEndDtStr(String endDtStr) {
		this.endDtStr = endDtStr;
	}
	
	public String getAddGrdtTpNm(){
		return GRDT_TP_NM_ARR[this.grdtTp];
	}
}

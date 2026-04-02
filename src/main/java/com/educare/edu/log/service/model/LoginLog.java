package com.educare.edu.log.service.model;

import java.util.Date;

/**
 * @Class Name : LoginLog.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 4.
 * @version 1.0
 * @see
 * @Description 로그인 로그 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 4.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class LoginLog {
	private int page = 1;
	private int row = 10;
	private int firstIndex;	
	/** 로그 일련번호 */ 
	private Integer logSeq; 
	
	/** 회원 아이디 */
	private String userId;
	/** 로그인 아이디 */
	private String loginId;
	/** 년도 */
	private String year;
	
	/** 월 */
	private String month;
	
	/** 일 */
	private String day;
	
	/** 시 */
	private String hour;
	
	/** 로그인 일시 */
	private Date loginDe;
	/** ip  */
	private String ip;
	/** 접속매체 */
	private String device;
	/** 접속 브라우저 */
	private String browser;
	/** 검색시작시 */
	private String srchBeginDt;
	private String srchEndDt;
	private String role;
	private String srchWrd;
	
	private String userNm;
	
	/**
	 * 
	 */
	public LoginLog() {
		super();
	}

	/**
	 * @param logSeq
	 * @param userId
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param loginDe
	 */
	public LoginLog(int logSeq, String userId, String loginId, String year, String month, String day, String hour, Date loginDe, String ip, String device, String browser, String role) {
		super();
		this.logSeq = logSeq;
		this.userId = userId;
		this.loginId = loginId;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.loginDe = loginDe;
		this.ip = ip;
		this.device = device;
		this.browser = browser;
		this.role = role;
	}

	/**
	 * @return the logSeq
	 */
	public Integer getLogSeq() {
		return logSeq;
	}

	/**
	 * @param logSeq the logSeq to set
	 */
	public void setLogSeq(Integer logSeq) {
		this.logSeq = logSeq;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return the hour
	 */
	public String getHour() {
		return hour;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(String hour) {
		this.hour = hour;
	}

	/**
	 * @return the loginDe
	 */
	public Date getLoginDe() {
		return loginDe;
	}

	/**
	 * @param loginDe the loginDe to set
	 */
	public void setLoginDe(Date loginDe) {
		this.loginDe = loginDe;
	}
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getSrchBeginDt() {
		return srchBeginDt;
	}

	public void setSrchBeginDt(String srchBeginDt) {
		this.srchBeginDt = srchBeginDt;
	}

	public String getSrchEndDt() {
		return srchEndDt;
	}

	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSrchWrd() {
		return srchWrd;
	}

	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
}

package com.educare.edu.log.service.model;

import java.util.Date;

/**
 * @Class Name : ConnLog.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 3.
 * @version 1.0
 * @see
 * @Description 접속로그 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 3.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class ConnLog {
	
	/** 로그 일련번호 */
	private Integer logSeq;
	
	/** 년도 */
	private String year;
	
	/** 월 */
	private String month;
	
	/** 일 */
	private String day;
	
	/** 시 */
	private String hour;
	
	/** 접속일시 */
	private Date connDe;
	
	/** URL */
	private String pageUrl;
	
	/** 메뉴 아이디 */
	private String menuId;
	
	/** 메뉴 명 */
	private String menuNm;
	
	/** 페이지 구분 A:관리자, U:사용자 */
	private String menuDiv;
	
	/** 회원 아이디 (비회원 : GUEST) */
	private String userId;
	
	/** 브라우저 */
	private String browser;
	
	/** 디바이스 (P : pc, M : 모바일, T : 템플릿, E : 기타) */
	private String device;
	
	/** IP */
	private String ip;
	
	private String logAct;
	private String accUserId;
	private String accUserInfo;
	
	public ConnLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param logSeq
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param connDe
	 * @param pageUrl
	 * @param menuId
	 * @param menuNm
	 * @param menuDiv
	 * @param userId
	 * @param browser
	 * @param device
	 * @param ip
	 */
	public ConnLog(Integer logSeq, String year, String month, String day, String hour, Date connDe, String pageUrl,
			String menuId, String menuNm, String menuDiv, String userId, String browser, String device, String ip
			,String logAct,String accUserId,String accUserInfo) {
		super();
		this.logSeq = logSeq;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.connDe = connDe;
		this.pageUrl = pageUrl;
		this.menuId = menuId;
		this.menuNm = menuNm;
		this.menuDiv = menuDiv;
		this.userId = userId;
		this.browser = browser;
		this.device = device;
		this.ip = ip;
		this.logAct = logAct;
		this.accUserId = accUserId;
		this.accUserInfo = accUserInfo;
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
	 * @return the connDe
	 */
	public Date getConnDe() {
		return connDe;
	}

	/**
	 * @param connDe the connDe to set
	 */
	public void setConnDe(Date connDe) {
		this.connDe = connDe;
	}

	/**
	 * @return the pageUrl
	 */
	public String getPageUrl() {
		return pageUrl;
	}

	/**
	 * @param pageUrl the pageUrl to set
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the menuNm
	 */
	public String getMenuNm() {
		return menuNm;
	}

	/**
	 * @param menuNm the menuNm to set
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	/**
	 * @return the menuDiv
	 */
	public String getMenuDiv() {
		return menuDiv;
	}

	/**
	 * @param menuDiv the menuDiv to set
	 */
	public void setMenuDiv(String menuDiv) {
		this.menuDiv = menuDiv;
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
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * @param browser the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLogAct() {
		return logAct;
	}

	public void setLogAct(String logAct) {
		this.logAct = logAct;
	}

	public String getAccUserId() {
		return accUserId;
	}

	public void setAccUserId(String accUserId) {
		this.accUserId = accUserId;
	}

	public String getAccUserInfo() {
		return accUserInfo;
	}

	public void setAccUserInfo(String accUserInfo) {
		this.accUserInfo = accUserInfo;
	} 
	
}

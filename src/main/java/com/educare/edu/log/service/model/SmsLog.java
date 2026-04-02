package com.educare.edu.log.service.model;

import java.util.Date;

/**
 * @Class Name : SmsLog.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 14.
 * @version 1.0
 * @see
 * @Description 문자 발송 로그
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 14.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class SmsLog {
	
	private String uniqueKey;
	
	private String sendType;
	
	private String title;
	
	private String message;
	
	private String callbackNum;
	
	private String phoneNum;
	
	private String resultCd;
	
	private String resultMsg;
	
	private Date rgsde;

	/**
	 * @return the uniqueKey
	 */
	public String getUniqueKey() {
		return uniqueKey;
	}

	/**
	 * @param uniqueKey the uniqueKey to set
	 */
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	/**
	 * @return the sendType
	 */
	public String getSendType() {
		return sendType;
	}

	/**
	 * @param sendType the sendType to set
	 */
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the callbackNum
	 */
	public String getCallbackNum() {
		return callbackNum;
	}

	/**
	 * @param callbackNum the callbackNum to set
	 */
	public void setCallbackNum(String callbackNum) {
		this.callbackNum = callbackNum;
	}

	/**
	 * @return the phoneNum
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * @return the resultCd
	 */
	public String getResultCd() {
		return resultCd;
	}

	/**
	 * @param resultCd the resultCd to set
	 */
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	/**
	 * @return the rgsde
	 */
	public Date getRgsde() {
		return rgsde;
	}

	/**
	 * @param rgsde the rgsde to set
	 */
	public void setRgsde(Date rgsde) {
		this.rgsde = rgsde;
	}
	
}

package com.educare.util;

/**
 * @Class Name : SendSmsVo.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 14.
 * @version 1.0
 * @see
 * @Description 문자발송 vo class
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 14.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class SendSmsVo {
	
	private String sendType;
	
	private String title;
	
	private String message;
	
	private String callbackNum;
	
	private String phoneNum;
	
	private String uniqueKey;

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
	
}

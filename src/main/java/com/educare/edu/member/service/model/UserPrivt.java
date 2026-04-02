package com.educare.edu.member.service.model;

import java.io.Serializable;

public class UserPrivt implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7943865590027103470L;
	
	private String userId;
	private String compUserId;
	private String userNm;
	private String mobile;
	private String email;
	private String birth;
	private String loginId;
	private int mfType;
	private int userEmailYn;
	private int userSmsYn;
	private String userOrgNm;
	private String userGradeNm;
	private String mfTypeNm;
	public String getCompUserId() {
		return compUserId;
	}
	public void setCompUserId(String compUserId) {
		this.compUserId = compUserId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getUserEmailYn() {
		return userEmailYn;
	}
	public void setUserEmailYn(int userEmailYn) {
		this.userEmailYn = userEmailYn;
	}
	public int getUserSmsYn() {
		return userSmsYn;
	}
	public void setUserSmsYn(int userSmsYn) {
		this.userSmsYn = userSmsYn;
	}
	public int getMfType() {
		return mfType;
	}
	public void setMfType(int mfType) {
		this.mfType = mfType;
	}
	public String getUserOrgNm() {
		return userOrgNm;
	}
	public void setUserOrgNm(String userOrgNm) {
		this.userOrgNm = userOrgNm;
	}
	public String getUserGradeNm() {
		return userGradeNm;
	}
	public void setUserGradeNm(String userGradeNm) {
		this.userGradeNm = userGradeNm;
	}
	public String getMfTypeNm() {
		return mfTypeNm;
	}
	public void setMfTypeNm(String mfTypeNm) {
		this.mfTypeNm = mfTypeNm;
	}
}

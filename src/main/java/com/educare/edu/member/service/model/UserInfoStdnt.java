package com.educare.edu.member.service.model;

import java.util.Date;

import com.educare.util.LncUtil;

/**
 * @Class Name : UserInfoStdnt.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 9.
 * @version 1.0
 * @see
 * @Description 수강생 회원정보 model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 9.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class UserInfoStdnt extends UserInfo {
	
	public static final String STR_Y = "Y";
	public static final String STR_N = "N";
	
	/** 영문이름 */
	private String userNmEn;
	
	/** 소속 */
	private String belong;
	
	/** 직급 */
	private String position; 
	
	/** 관리자 계정 생성여부 */
	private String admRgsYn;
	
	/** 본인인증 여부 */
	private String selfAuthYn;
	
	/** 약관 동의일자 */
	private Date termsAcceptDe;
	
	public UserInfoStdnt(){
		super();
	}

	/**
	 * @param userNmEn
	 * @param belong
	 * @param position
	 * @param admRgsYn
	 * @param selfAuthYn
	 * @param termsAcceptDe
	 */
	public UserInfoStdnt(String userNmEn, String belong, String position, String admRgsYn, String selfAuthYn,
			Date termsAcceptDe) {
		super();
		this.userNmEn = userNmEn;
		this.belong = belong;
		this.position = position;
		this.admRgsYn = admRgsYn;
		this.selfAuthYn = selfAuthYn;
		this.termsAcceptDe = termsAcceptDe;
	}

	/**
	 * @return the userNmEn
	 */
	public String getUserNmEn() {
		return userNmEn;
	}

	/**
	 * @param userNmEn the userNmEn to set
	 */
	public void setUserNmEn(String userNmEn) {
		this.userNmEn = LncUtil.replaceXSS(userNmEn);
	}

	/**
	 * @return the belong
	 */
	public String getBelong() {
		return belong;
	}

	/**
	 * @param belong the belong to set
	 */
	public void setBelong(String belong) {
		this.belong = LncUtil.replaceXSS(belong);
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = LncUtil.replaceXSS(position);
	}

	/**
	 * @return the admRgsYn
	 */
	public String getAdmRgsYn() {
		return admRgsYn;
	}

	/**
	 * @param admRgsYn the admRgsYn to set
	 */
	public void setAdmRgsYn(String admRgsYn) {
		this.admRgsYn = admRgsYn;
	}

	/**
	 * @return the selfAuthYn
	 */
	public String getSelfAuthYn() {
		return selfAuthYn;
	}

	/**
	 * @param selfAuthYn the selfAuthYn to set
	 */
	public void setSelfAuthYn(String selfAuthYn) {
		this.selfAuthYn = selfAuthYn;
	}

	/**
	 * @return the termsAcceptDe
	 */
	public Date getTermsAcceptDe() {
		return termsAcceptDe;
	}

	/**
	 * @param termsAcceptDe the termsAcceptDe to set
	 */
	public void setTermsAcceptDe(Date termsAcceptDe) {
		this.termsAcceptDe = termsAcceptDe;
	}

}

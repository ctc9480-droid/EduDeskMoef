package com.educare.edu.member.service.model;

import com.educare.util.LncUtil;

/**
 * @Class Name : UserInfoInstrctr.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 9.
 * @version 1.0
 * @see
 * @Description 강사 회원정보 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 9.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class UserInfoInstrctr extends UserInfo {
	/**
	 * 
	 */

	/** 영문이름 */
	private String userNmEn;
	
	/** 소속 */
	private String belong;
	
	/** 직급 */
	private String position; 
	
	/** 사진 원본명 */
	private String photoOrg;
	
	/** 사진 저장명 */
	private String photoRename;
	
	/** 메모 */
	private String memo;
	
	/** 소속/경력 */
	private String area;


	public UserInfoInstrctr() {
		super();

	}

	/**
	 * @param belong
	 * @param position
	 * @param photoOrg
	 * @param photoRename
	 */
	public UserInfoInstrctr(String belong, String position, String photoOrg, String photoRename, String memo) {
		super();
		this.belong = belong;
		this.position = position;
		this.photoOrg = photoOrg;
		this.photoRename = photoRename;
		this.memo = memo;
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
		this.belong = belong;
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
		this.position = position;
	}

	/**
	 * @return the photoOrg
	 */
	public String getPhotoOrg() {
		return photoOrg;
	}

	/**
	 * @param photoOrg the photoOrg to set
	 */
	public void setPhotoOrg(String photoOrg) {
		this.photoOrg = photoOrg;
	}

	/**
	 * @return the photoRename
	 */
	public String getPhotoRename() {
		return photoRename;
	}

	/**
	 * @param photoRename the photoRename to set
	 */
	public void setPhotoRename(String photoRename) {
		this.photoRename = photoRename;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getUserNmEn() {
		return userNmEn;
	}

	public void setUserNmEn(String userNmEn) {
		this.userNmEn = LncUtil.replaceXSS(userNmEn);
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
}

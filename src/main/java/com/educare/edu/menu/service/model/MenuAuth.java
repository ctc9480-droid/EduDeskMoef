package com.educare.edu.menu.service.model;

/**
 * @Class Name : MenuAuth.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 2.
 * @version 1.0
 * @see
 * @Description 메뉴권한 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class MenuAuth {
	
	/** 메뉴 아이디 */
	private String menuId;
	
	/** 회원 아이디 */
	private String userId;

	/**
	 * 
	 */
	public MenuAuth() {
		super();
	}

	/**
	 * @param menuId
	 * @param userId
	 */
	public MenuAuth(String menuId, String userId) {
		super();
		this.menuId = menuId;
		this.userId = userId;
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
	
}

package com.educare.edu.menu.service.model;

/**
 * @Class Name : Menu.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 2.
 * @version 1.0
 * @see
 * @Description 메뉴 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class Menu {
	
	public static final String MENU_TYPE_HTML = "H";
	public static final String MENU_TYPE_FUNC = "F";
	public static final String MENU_DIV_USER = "U";
	public static final String MENU_DIV_ADM = "A";
	
	/** 메뉴 아이디 */
	private String menuId;
	
	/** 메뉴명 */
	private String menuNm;
	
	/** 메뉴타입 H:html, F:기능 */
	private String menuType;
	
	/** 메뉴구분 U:사용자, A:관리자 */
	private String menuDiv;
	
	/** URL */
	private String url;

	public Menu() {
		super();
	}

	/**
	 * @param menuId
	 * @param menuNm
	 * @param menuType
	 * @param menuDiv
	 * @param url
	 */
	public Menu(String menuId, String menuNm, String menuType, String menuDiv, String url) {
		super();
		this.menuId = menuId;
		this.menuNm = menuNm;
		this.menuType = menuType;
		this.menuDiv = menuDiv;
		this.url = url;
	}
	
	/**
	 * @param menuId
	 * @param menuNm
	 * @param menuType
	 * @param menuDiv
	 */
	public Menu(String menuId, String menuNm, String menuType, String menuDiv) {
		super();
		this.menuId = menuId;
		this.menuNm = menuNm;
		this.menuType = menuType;
		this.menuDiv = menuDiv;
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
	 * @return the menuType
	 */
	public String getMenuType() {
		return menuType;
	}

	/**
	 * @param menuType the menuType to set
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
}

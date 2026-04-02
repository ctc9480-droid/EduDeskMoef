package com.educare.edu.log.service.model;

import java.util.Date;

import com.educare.edu.menu.service.MenuUtil;
import com.educare.edu.menu.service.model.Menu;

/**
 * @Class Name : ConnLogAdm.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 9.
 * @version 1.0
 * @see
 * @Description 관리자 접속이력 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 9.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class ConnLogAdm {
	
	/** 아이디 */
	private String userId;
	
	/** 접속일시 */
	private Date connDe;
	
	/** 접속 IP */
	private String ip;
	
	/** 메뉴 아이디 */
	private String menuId;
	
	/** 접근메뉴 */
	private String menuNm;	
	private String logAct;
	private String accUserId;
	private String accUserInfo;

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

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	/*public void setMenuId(String menuId) {
		this.menuId = menuId;
		Menu menu = MenuUtil.getAdmMenuById(menuId);
		if(menu != null && !MenuUtil.ADM_MENU_DEPTH[0].equals(menu.getMenuId())){
			int menuDepth = Integer.parseInt(menu.getMenuId().substring(3, 4));
			this.menuNm = MenuUtil.ADM_1DEPTH_MENU[menuDepth] + " > " + menu.getMenuNm();
		}
	}*/

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


package com.educare.edu.menu.service;

import java.util.List;

import com.educare.edu.menu.service.model.MenuAuth;

/**
 * @Class Name : MenuService.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 2.
 * @version 1.0
 * @see
 * @Description 메뉴 서비스 인터페이스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public interface MenuService {
	
	/**
	 * 메뉴 권한을 반환한다.
	 * @param menuId
	 * @param userId
	 * @return com.educare.edu.menu.service.model.MenuAuth
	 */
	public MenuAuth getMenuAuth(String menuId, String userId);
	
	/**
	 * 메뉴 아이디로 메뉴 권한을 반환한다.
	 * @param menuId
	 * @return List<MenuAuth>
	 */
	public List<MenuAuth> getMenuAuthListByMenuId(String menuId);
	
	/**
	 * 사용자 아이디로 메뉴 권한을 반환한다.
	 * @param userId
	 * @return List<MenuAuth>
	 */
	public List<MenuAuth> getMenuAuthListByUserId(String userId);
	
	/**
	 * 메뉴권한을 저장한다.
	 * @param menuIds
	 * @param userId
	 */
	public void setMenuAuth(String[] menuIds, String userId);
	
	/**
	 * 메뉴 권한을 삭제한다.
	 * @param userId
	 */
	public void deleteMenuAuthByUserId(String userId);
}

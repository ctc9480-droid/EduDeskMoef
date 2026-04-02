package com.educare.edu.menu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.educare.edu.menu.service.MenuService;
import com.educare.edu.menu.service.model.MenuAuth;

/**
 * @Class Name : MenuServiceImpl.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 2.
 * @version 1.0
 * @see
 * @Description 메뉴 서비스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Service("MenuService")
public class MenuServiceImpl implements MenuService {
	
	/** 메뉴 Mapper */
	@Resource(name="MenuMapper")
	private MenuMapper menuMapper;
	
	/**
	 * 메뉴 권한을 반환한다.
	 * @param menuId
	 * @param userId
	 * @return com.educare.edu.menu.service.model.MenuAuth
	 */
	@Override
	public MenuAuth getMenuAuth(String menuId, String userId) {
		List<MenuAuth> list = (List<MenuAuth>) menuMapper.selectMenuAuthByPk(new MenuAuth(menuId, userId));
		MenuAuth menuAuth = null;
		if(list != null && list.size() > 0){
			menuAuth = list.get(0);
		}
		return menuAuth;
	}

	/**
	 * 메뉴 아이디로 메뉴 권한을 반환한다.
	 * @param menuId
	 * @return List<MenuAuth>
	 */
	@Override
	public List<MenuAuth> getMenuAuthListByMenuId(String menuId) {
		return menuMapper.selectMenuAuthByMenuId(menuId);
	}

	/**
	 * 사용자 아이디로 메뉴 권한을 반환한다.
	 * @param userId
	 * @return List<MenuAuth>
	 */
	@Override
	public List<MenuAuth> getMenuAuthListByUserId(String userId) {
		return menuMapper.selectMenuAuthByUserId(userId);
	}

	/**
	 * 메뉴권한을 저장한다.
	 * @param menuIds
	 * @param userId
	 */
	@Override
	public void setMenuAuth(String[] menuIds, String userId) {
		menuMapper.deleteMenuAuth(userId);
		for(String menuId : menuIds){
			menuMapper.insertMenuAuth(new MenuAuth(menuId, userId));
		}
	}

	/**
	 * 메뉴 권한을 삭제한다.
	 * @param userId
	 */
	@Override
	public void deleteMenuAuthByUserId(String userId) {
		menuMapper.deleteMenuAuth(userId);
	}
}

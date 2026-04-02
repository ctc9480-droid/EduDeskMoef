package com.educare.edu.menu.service.impl;

import java.util.List;

import com.educare.edu.menu.service.model.MenuAuth;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : MenuMapper.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 2.
 * @version 1.0
 * @see
 * @Description 메뉴 매퍼 클래스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Mapper("MenuMapper")
public interface MenuMapper {
	
	void deleteMenuAuth(String userId);
	
	void insertMenuAuth(MenuAuth menuAuth);
	
	List<MenuAuth> selectMenuAuthByPk(MenuAuth menuAuth);
	
	List<MenuAuth> selectMenuAuthByMenuId(String menuId);
	
	List<MenuAuth> selectMenuAuthByUserId(String userId);
}

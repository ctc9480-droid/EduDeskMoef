package com.educare.edu.menu.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import sun.util.logging.resources.logging;

import com.educare.edu.comn.mapper.CodeMapper;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.education.service.impl.CategoryMapper;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.menu.service.model.Menu;
import com.educare.edu.menu.service.model.MenuAuth;
import com.educare.util.LncUtil;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * @Class Name : MenuUtil.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 2.
 * @version 1.0
 * @see
 * @Description 메뉴관련 유틸
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Component
public class MenuUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(MenuUtil.class.getName());
	
	//public static final String[] ADM_1DEPTH_MENU = {"Dash Board", "회원관리", "교육관리", "통계관리", "자료실관리", "사이트관리","시험지 관리","어린이집컨설팅","아이사랑 안심보육공모전","이동체험관"};
	public static final String[] USER_1DEPTH_MENU = {"index", "전체교육", "교육안내", "고객센터", "마이페이지"};
	
	/**
	 * 메뉴 아이디로 관리자 메뉴를 반환한다.
	 * @param menuId
	 * @return com.educare.edu.menu.service.model.Menu
	 */
	/**/
	public static Menu getAdmMenuById(String menuIdp){
		String menuId = menuIdp;
		Menu menu = null;
		
		String menuNm = "";
		List<Map<String, Object>> list = getAdminBaseMenuList();
		Loop1:
		for(Map<String, Object> o:list){
			if(menuId.equals( o.get("cd").toString())){
				menuId=o.get("cd").toString();
				menuNm=o.get("nm").toString();
				break;
			}
			for(Map<String, Object> o2:(List<Map<String, Object>>)o.get("sub")){
				if(menuId.equals( o2.get("cd").toString())){
					menuId=o2.get("cd").toString();
					menuNm=o2.get("nm").toString();
					break Loop1;
				}
			}
		}
		
		if(!menuNm.equals("")){
			menu = new Menu(menuId,menuNm, Menu.MENU_TYPE_FUNC, Menu.MENU_DIV_ADM);
		}
		
		return menu;
	}
	
	/**
	 * uri로 관리자 메뉴를 반환한다.
	 * @param uri
	 * @return com.educare.edu.menu.service.model.Menu
	 */
	/**/
	public static Menu getAdmMenuByUri(String uri){
		Menu menu = new Menu();
		
		String menuId = "";
		String menuNm = "";
		List<Map<String, Object>> list = getAdminBaseMenuList();
		Loop1:
		for(Map<String, Object> o:list){
			if(uri.contains( o.get("cd").toString())){
				menuId=o.get("cd").toString();
				menuNm=o.get("nm").toString();
			}
			for(Map<String, Object> o2:(List<Map<String, Object>>)o.get("sub")){
				if(uri.contains( o2.get("cd").toString())){
					menuId=o2.get("cd").toString();
					menuNm=o2.get("nm").toString();
				}
				for(Map<String, Object> o3:(List<Map<String, Object>>)o2.get("sub")){
					if(uri.contains( o3.get("cd").toString())){
						menuId=o3.get("cd").toString();
						menuNm=o3.get("nm").toString();
						break Loop1;
					}
				}
			}
		}
		if(!menuId.equals("")){
			menu = new Menu(menuId, menuNm, Menu.MENU_TYPE_FUNC, Menu.MENU_DIV_USER);
		}
	
		return menu;
	}
	/**
	 * uri로 관리자 메뉴를 반환한다.
	 * @param uri
	 * @return com.educare.edu.menu.service.model.Menu
	 */
	/**/
	public static Menu getUserMenuByUri(String uri){
		Menu menu = new Menu();
		
		String menuId = "";
		String menuNm = "";
		List<Map<String, Object>> list = getBaseMenuList();
		Loop1:
		for(Map<String, Object> o:list){
			if(uri.contains( o.get("cd").toString())){
				menuId=o.get("cd").toString();
				menuNm=o.get("nm").toString();
			}
			for(Map<String, Object> o2:(List<Map<String, Object>>)o.get("sub")){
				if(uri.contains( o2.get("cd").toString())){
					menuId=o2.get("cd").toString();
					menuNm=o2.get("nm").toString();
					break Loop1;
				}
			}
		}
		if(!menuId.equals("")){
			menu = new Menu(menuId, menuNm, Menu.MENU_TYPE_FUNC, Menu.MENU_DIV_USER);
		}
	
		return menu;
	}	
	/**
	 * uri로 사용자 메뉴를 반환한다.
	 * @param uri
	 * @return com.educare.edu.menu.service.model.Menu
	 */
	/*public static Menu getUserMenuByUri(String uri){
		Menu menu = null;
		for(int i = 0; i < USER_MENU_URI.length; i++){
			if(LncUtil.replaceNull(uri).contains(USER_MENU_URI[i])){
				menu = new Menu(USER_MENU_DEPTH[i], USER_MENU_NM[i], Menu.MENU_TYPE_FUNC, Menu.MENU_DIV_ADM);
				break;
			}
		}
		
		return menu;
	}*/
	
	/**
	 * 사용자 메뉴 리스트를 반환한다.
	 * @return List<Menu>
	 */
	/*public static List<Menu> getUserMenuList(){
		List<Menu> list = new ArrayList<Menu>();
		Menu menu = null;
		int i = 0;
		for(String menuId : USER_MENU_DEPTH){
			if(!USER_MENU_DEPTH[0].equals(menuId)){
				menu = new Menu(menuId, USER_MENU_NM[i], Menu.MENU_TYPE_FUNC, Menu.MENU_DIV_USER);
				list.add(menu);
			}	
			i++;
		}
		
		return list;
	}*/
	
	/**
	 * 사용자 2차메뉴 리스트를 반환한다.
	 * @param menuDepth
	 * @return
	 */
	/*public static List<Menu> getUserSubMenuList(String menuDepth){
		List<Menu> list = new ArrayList<Menu>();
		Menu menu = null;
		if("1".equals(menuDepth)||"2".equals(menuDepth)){
			list = null;
		}else{
			int startIdx = "3".equals(menuDepth)? 3 : "4".equals(menuDepth)? 6 : "5".equals(menuDepth)? 10 : "6".equals(menuDepth)? 13 : 0;
			int endIdx = "3".equals(menuDepth)? 5 : "4".equals(menuDepth)? 12 : "5".equals(menuDepth)? 13 : "6".equals(menuDepth)? 16 : 0;
			for(int i = startIdx; i <= endIdx; i++){
				if(menuDepth.equals("4")){//마이페이지 메뉴인 경우만
					if(SessionUserInfoHelper.getUserMemLvl().equals("8")){
						if(i==6||i==7||i==8||i==9)continue;
					}else if("7,9".contains(SessionUserInfoHelper.getUserMemLvl())){
						if(i==10)continue;
					}
				}
				menu = new Menu(USER_MENU_DEPTH[i], USER_MENU_NM[i], Menu.MENU_TYPE_FUNC, Menu.MENU_DIV_USER, USER_MENU_LINK[i]);
				list.add(menu);
			}
		}
		
		return list;
	}*/
	
	
	/**
	 * 현재 URL을 반환한다.
	 * @return String 
	 */
	public static String getUrl(HttpServletRequest request){
		return (String)request.getRequestURI();
	}
	
	/**
	 * 호출 URI를 통해 패키지 명을 제외한 uri를 반환한다.
	 * @return String 
	 */
	public static String getUriReplace(HttpServletRequest request){
		String fullUri = getUrl(request);
		String arr[] = fullUri.split("/");
		
		String uri = arr[arr.length - 1].replaceAll(".do", "");
		
		return uri;
	}
	
	/**
	 * 호출 URI를 통해 패키지 명을 제외한 uri를 반환한다.
	 * @return String 
	 */
	public static String getReqUri(HttpServletRequest request){
		String bestPattern = (String)request.getAttribute("javax.servlet.forward.request_uri");
		String arr[] = bestPattern.split("/");
		
		String uri = arr[arr.length - 1].replaceAll(".do", "");
		
		return uri;
	}
	
	public static String getReqUriAll(HttpServletRequest request){
		String bestPattern = (String)request.getAttribute("javax.servlet.forward.request_uri");
		return bestPattern;
	}
	
	/**
	 * 메뉴 권한이 있는지 반환한다. true : 권한있음, false : 권한없음
	 * @param request
	 * @return 
	 */
	public static boolean isAdmMenuAuth(HttpServletRequest request){
		
		boolean isAuth = false;
		
		String uri = getUriReplace(request);
		MenuAuth auth = null;
		
		if(SessionUserInfoHelper.isSuperAdmin()){
			return true;
		}
		
		if("main,index".contains(uri)){
			return true;
		}
		
		String menuId = "";
		List<Map<String, Object>> list = getAdminBaseMenuList();
		Loop1:
		for(Map<String, Object> o:list){
			if(uri.contains( o.get("cd").toString())){
				menuId=o.get("cd").toString();
				break;
			}
			for(Map<String, Object> o2:(List<Map<String, Object>>)o.get("sub")){
				if(uri.contains( o2.get("cd").toString())){
					menuId=o2.get("cd").toString();
					break Loop1;
				}
			}
		}
		
		if(menuId.equals("")){
			return true;//빈값이면 권한 체크 안하는 메뉴
		}
		
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		if(ctx != null){
			MenuService service = (MenuService) ctx.getBean("MenuService");
			if(service != null){
				auth = service.getMenuAuth(menuId, SessionUserInfoHelper.getUserId());
				if(auth != null && auth.getMenuId() != null && auth.getUserId() != null){
					isAuth = true;
				}
			}else{
				LOG.error("Spring 환경에서만 동작가능합니다.");
			}
		}
		
		return isAuth;
	}
	
	/**
	 * 접근가능한 메뉴아이디를 반환한다.
	 * @return
	 */
	public static String accessMenuIds(){
		String menuIds = "";
		if(SessionUserInfoHelper.isAdmin()){
			if(SessionUserInfoHelper.isSuperAdmin()){
				/*for(int i = 0; i < ADM_MENU_DEPTH.length; i++){
					menuIds += ADM_MENU_DEPTH[i] + "|";
				}*/
				LOG.error("수퍼관리자 체크 넘어감");
			}else{
				WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
				if(ctx != null){
					MenuService service = (MenuService) ctx.getBean("MenuService");
					if(service != null){
						List<MenuAuth> list = service.getMenuAuthListByUserId(SessionUserInfoHelper.getUserId());
						for(MenuAuth auth : list){
							menuIds += auth.getMenuId() + "|";
						}
					}else{
						LOG.error("Spring 환경에서만 동작가능합니다.");
					}
				}
			}
		}
			
		return menuIds;
	}
	
	/**
	 * 네비 리스트
	 * @param menuDepth
	 * @return
	 */
	/*public static List<Menu> getUserNaviList(String menuDepth){
		List<Menu> list = new ArrayList<Menu>();
		Menu menu = null;
		if("1".equals(menuDepth)||"2".equals(menuDepth)){
			list = null;
		}else{
			int startIdx = "3".equals(menuDepth)? 3 : "4".equals(menuDepth)? 6 : "5".equals(menuDepth)? 10 : "6".equals(menuDepth)? 13 : 0;
			int endIdx = "3".equals(menuDepth)? 5 : "4".equals(menuDepth)? 12 : "5".equals(menuDepth)? 13 : "6".equals(menuDepth)? 16 : 0;
			for(int i = startIdx; i <= endIdx; i++){
				if(menuDepth.equals("4")){//마이페이지 메뉴인 경우만
					if(SessionUserInfoHelper.getUserMemLvl().equals("8")){
						if(i==6||i==7||i==8)continue;
					}else if("7,9".contains(SessionUserInfoHelper.getUserMemLvl())){
						if(i==9)continue;
					}
				}
				menu = new Menu(USER_MENU_DEPTH[i], USER_MENU_NM[i], Menu.MENU_TYPE_FUNC, Menu.MENU_DIV_USER, USER_MENU_LINK[i]);
				list.add(menu);
			}
		}
		
		return list;
	}*/
	
	
	
	////////////////////////////////////////////////////////////////new//////////////////////////////////////////////////////////////////////
	/**
	 * 기초메뉴
	 * @return
	 */
	public static List<Map<String, Object>> getBaseMenuList(){
	    try {
	    	HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	    	
	    	String path = req.getServletContext().getRealPath("/WEB-INF/menu_user.json");
	    	String json = LncUtil.getReadFile(path);
	    	
	    	ObjectMapper om = new ObjectMapper();
	    	List<Map<String,Object>> menuList = om.readValue(json, List.class);
	    	
	    	/*
	    	//카테고리 빈호출,230727
			HttpSession session = req.getSession();
			ServletContext conext = session.getServletContext();
			WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(conext);
			CategoryMapper categoryMapper = (CategoryMapper)wContext.getBean("CategoryMapper");
			List<CategoryVO> cateList = categoryMapper.selectCategoryList(2);
			List<Map<String,Object>> cateMenuList1 = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> cateMenuList2 = new ArrayList<Map<String,Object>>();
			Map<String,Object> cateMenu1 = new HashMap<String,Object>();
			cateMenu1.put("url", "/user/edu/eduList.do?srchCtgry=1");
			cateMenu1.put("cd", "edu1_0");
			cateMenu1.put("st", 1);
			cateMenu1.put("nm", "전체");
			cateMenuList1.add(cateMenu1);
			Map<String,Object> cateMenu2 = new HashMap<String,Object>();
			cateMenu2.put("url", "/user/edu/eduList.do?srchCtgry=2");
			cateMenu2.put("cd", "edu2_0");
			cateMenu2.put("st", 1);
			cateMenu2.put("nm", "전체");
			cateMenuList2.add(cateMenu2);
			for(CategoryVO o : cateList){
				if(!"Y".equals(o.getUseYn())){
					continue;
				}
				if(o.getParentSeq() == 1){
					cateMenu1 = new HashMap<String,Object>();
					cateMenu1.put("url", "/user/edu/eduList.do?srchCtgry=1&srchCtgry2="+o.getCtgrySeq());
					cateMenu1.put("cd", "edu1_"+o.getCtgrySeq());
					cateMenu1.put("st", 1);
					cateMenu1.put("tab", 0);
					cateMenu1.put("nm", o.getCtgryNm());
					cateMenu1.put("etc01", o.getCtgrySeq());
					cateMenuList1.add(cateMenu1);
				}else if(o.getParentSeq() == 2){
					cateMenu2 = new HashMap<String,Object>();
					cateMenu2.put("url", "/user/edu/eduList.do?srchCtgry=2&srchCtgry2="+o.getCtgrySeq());
					cateMenu2.put("cd", "edu2_"+o.getCtgrySeq());
					cateMenu2.put("st", 1);
					cateMenu2.put("tab", 0);
					cateMenu2.put("nm", o.getCtgryNm());
					cateMenu2.put("etc01", o.getCtgrySeq());
					cateMenuList2.add(cateMenu2);
				}
			}
			
			//카테고리 메뉴를 기존 메뉴에 덮는다.
			for(int i=0; i<menuList.size(); i++){
				if(i!=0 && i!=1){
					continue;
				}
				Map<String,Object> o = menuList.get(i);
				if(i==0){
					o.put("sub",cateMenuList1);
				}else{
					o.put("sub",cateMenuList2);
				}
				
			}
			*/
	    	
	    	return menuList;
		} catch (NullPointerException | IOException e) {
			return null;
		}
	}
	/**
	 * 관리자기초메뉴
	 * @return
	 */
	public static List<Map<String, Object>> getAdminBaseMenuList(){
		try {
			HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			
			String path = req.getServletContext().getRealPath("/WEB-INF/menu_admin.json");
			String json = LncUtil.getReadFile(path);
			
			ObjectMapper om = new ObjectMapper();
			List<Map<String,Object>> menuList = om.readValue(json, List.class);
			
			return menuList;
		} catch (NullPointerException | IOException e) {
			return null;
		}
	}
	
	/**
	 * 서브메뉴리스트 가져오기
	 * @return
	 */
	public static List<Map<String, Object>> getSubMenuList(){
		try {
			HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			String perm = "";
			if(SessionUserInfoHelper.isLogined()){
				String userMemLvl = SessionUserInfoHelper.getUserMemLvl();
				if(userMemLvl != null){
					perm = userMemLvl.equals("8")?"p":"s";
				}
			}
			List<Map<String, Object>> menuList = getBaseMenuList();
			String uri = MenuUtil.getReqUri(req);
			LOG.debug("uri:"+uri);
			//리턴할 리스트
			
			for(Map<String, Object> m1:menuList){
				System.out.println(m1);
				boolean isFind=false;
				if(ObjectUtils.isEmpty(m1.get("sub"))){
					continue;
				}
				List<Map<String, Object>> returnList = new ArrayList<>();
				List<Map<String, Object>> sub1 = (List<Map<String, Object>>) m1.get("sub");
				
				for(Map<String, Object> m2:sub1){
					returnList.add(m2);//일단 2레벨 정보 넣는다.
					String cd = (String) m2.get("cd");
					if(uri.contains(cd)){//2레벨에서 찾았다면
						m2.put("on", true);
						isFind=true;
					}
					if("p".equals(perm)){
						if(!"p".equals(m1.get("perm"))){
							isFind=false;
						}
					}
					
				}
				System.out.println(isFind);
				
				//2레벨 다 돌고
				if(isFind){
					for(Map<String, Object> o:returnList){
						System.out.println(o);
					}
					return returnList;
				}
			}
			
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
	public static List<Map<String, Object>> getNaviList(){
		try {
			HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			List<Map<String, Object>> menuList = getBaseMenuList();
			String uri = MenuUtil.getReqUri(req);
			//리턴할 리스트
			List<Map<String, Object>> returnList = null;
			
			for(Map<String, Object> m1:menuList){
				returnList = new ArrayList<>();
				
				String cd = (String) m1.get("cd");
				//String url = (String) m1.get("url");
				if(uri.contains(cd)){//2레벨에서 찾았다면
					returnList.add(m1);
				}
				List<Map<String, Object>> sub1 = (List<Map<String, Object>>) m1.get("sub");
				for(Map<String, Object> m2:sub1){
					String cd2 = (String) m2.get("cd");
					if(uri.contains(cd2)){//2레벨에서 찾았다면
						if(returnList.size()==0){
							returnList.add(m1);
						}
						returnList.add(m2);
						return returnList;
					}
					
				}
				if(returnList.size()==1){//1레벨들어왔는데 2레벨 없다면 멈춰
					return returnList;
				}
			}
			return returnList;
		} catch (NullPointerException e) {
			return null;
		}
	}
	public static List<Map<String, Object>> getAdminNaviList(){
		try {
			HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			List<Map<String, Object>> menuList = getAdminBaseMenuList();
			String uri = MenuUtil.getReqUri(req);
			//리턴할 리스트
			List<Map<String, Object>> returnList = null;
			
			for(Map<String, Object> m1:menuList){
				returnList = new ArrayList<>();
				
				String cd = (String) m1.get("cd");
				if(uri.contains(cd)){//2레벨에서 찾았다면
					returnList.add(m1);
				}
				List<Map<String, Object>> sub1 = (List<Map<String, Object>>) m1.get("sub");
				for(Map<String, Object> m2:sub1){
					String cd2 = (String) m2.get("cd");
					if(uri.contains(cd2)){//2레벨에서 찾았다면
						returnList.clear();
						returnList.add(m1);
						returnList.add(m2);
						if(ObjectUtils.isEmpty(m2.get("sub"))){
							return returnList;
						}
					}
					List<Map<String, Object>> sub2 = (List<Map<String, Object>>) m2.get("sub");
					for(Map<String, Object> m3:sub2){
						String cd3 = (String) m3.get("cd");
						if(uri.contains(cd3)){//3레벨에서 찾았다면
							returnList.clear();
							returnList.add(m1);
							returnList.add(m2);
							returnList.add(m3);
							return returnList;
						}
					}
					
				}
				if(returnList.size()==1){//1레벨들어왔는데 2레벨 없다면 멈춰
					return returnList;
				}
			}
			return returnList;
		} catch (NullPointerException e) {
			return null;
		}
	}
	public static int getMenuOrderNo(){
		try {
			HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			List<Map<String, Object>> menuList = getBaseMenuList();
			String uri = MenuUtil.getReqUri(req);
			int returnNo=0;
			for(int i=0;i<menuList.size();i++){
				Map<String, Object> m1 = menuList.get(i);
				
				String cd = (String) m1.get("cd");
				int imgNo = (int) m1.get("imgNo");
				if(uri.contains(cd)){//1레벨에서 찾았다면
					return imgNo;
				}
				//LOG.debug("m1 : "+m1);
				List<Map<String, Object>> sub1 = (List<Map<String, Object>>) m1.get("sub");
				for(int i2=0;i2<sub1.size();i2++){
					Map<String, Object> m2 = sub1.get(i2);
					String cd2 = (String) m2.get("cd");
					if(uri.contains(cd2)){//2레벨에서 찾았다면
						return imgNo;
					}
				}
				
				
			}
			return returnNo;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	public static String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }
}

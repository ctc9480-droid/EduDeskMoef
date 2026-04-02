package com.educare.edu.log.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.web.EduController;
import com.educare.edu.log.service.LogService;
import com.educare.edu.log.service.model.ConnLog;
import com.educare.edu.log.service.model.ConnLogAdm;
import com.educare.edu.log.service.model.LoginLog;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.menu.service.MenuUtil;
import com.educare.edu.menu.service.model.Menu;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;
import com.educare.util.MaxNumUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : LogServiceImpl.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 3.
 * @version 1.0
 * @see
 * @Description 접속로그 서비스 클래스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 3.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Service("LogService")
public class LogServiceImpl implements LogService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LogServiceImpl.class);
	
	public static final String IS_MOBILE = "M";
	public static final String IS_TABLET = "T";
	public static final String IS_PC = "P";
	public static final String GUEST = "GUEST";
	
	/** 접속로그 Mapper */
	@Resource(name = "LogMapper")
	private LogMapper logMapper;
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	
	/**
	 * 접속 디바이스를 반환한다.
	 * @param req
	 * @return
	 */
	private String getDevice(HttpServletRequest req) {
		LOG.info(req.getRemoteAddr());
	    String userAgent = req.getHeader("User-Agent").toUpperCase();
	    String device = "E";
	    
	    if( userAgent.indexOf( "MOBILE" ) > -1 || userAgent.indexOf( "IPHONE" ) > -1 ) {
	        device = IS_MOBILE;
	    } else {
	    	if( userAgent.indexOf( "IPAD" ) > -1 || ( userAgent.indexOf( "ANDROID" ) > -1 && userAgent.indexOf( "MOBILE" ) < 0 ) ) {
	    		device = IS_TABLET;
	    	} else {
	    		device = IS_PC;
	    	}
	    }
	    
	    return device;
	}
	
	/**
	 * 접속 브라우저를 반환한다.
	 * @param req
	 * @return
	 */
	private String getBrowser(HttpServletRequest req) {
		
		String userAgent = req.getHeader("User-Agent").toUpperCase();
		String browser = "ETC";
		LOG.info(userAgent);
		if(userAgent.indexOf("EDG") > -1){
			browser = "EDGE";
		}else if( userAgent.indexOf( "CHROME" ) > -1 ) {
			browser = "CHROME";
		} else if ( userAgent.indexOf( "SAFARI" ) > -1 ) {
			browser = "SAFARI";
		} else if ( userAgent.indexOf( "OPERA" ) > -1 || userAgent.indexOf( "OPR" ) > -1) {
			browser = "OPERA";
		} else if ( userAgent.indexOf( "FIREFOX" ) > -1 ) {
			browser = "FIREFOX";
		} else if ( userAgent.indexOf( "TRIDENT" ) > -1 || userAgent.indexOf( "MSIE" ) > -1) {
			browser = "MSIE";
		}
		
		return browser;
	}
	
	/**
	 * 접속로그 저장
	 * @param request
	 */
	@Override
	public void insertConnLog(HttpServletRequest request, String menuDiv) {
		String device = this.getDevice(request);
		String browser = this.getBrowser(request);
		String ip = LncUtil.getIp(request);
		
		Date connDe = new Date();
		String year = DateUtil.getDate2Str(connDe, "yyyy");
		String month = DateUtil.getDate2Str(connDe, "MM");
		String day = DateUtil.getDate2Str(connDe, "dd");
		String hour = DateUtil.getDate2Str(connDe, "HH");
		
		String pageUrl = LncUtil.replaceNull(MenuUtil.getUrl(request));
		String uri = LncUtil.replaceNull(MenuUtil.getUriReplace(request));
		
		String logAct = "R";
		String accUserId = "";
		String accUserInfo = "";
		

		
		Menu menu = null;
		
		if(Menu.MENU_DIV_USER.equals(menuDiv)){
			/** 사용자 메뉴 get 추가 */
			if(uri.contains("List") || uri.contains("Stats") || uri.contains("_")){
				menu = MenuUtil.getUserMenuByUri(uri);
			}
			/* 임시 */
		}else{
			if(uri.contains("List") || uri.contains("Stats") || uri.contains("sms") || uri.contains("stdnt")){
				menu = MenuUtil.getAdmMenuByUri(uri);
			}
		}

		//사용자관련
		try {
			accUserId = request.getParameter("userId");
			if("stdntView".equals(uri)){
				UserInfo user = memberMapper.selectUserInfoByPk(accUserId);
				accUserInfo += ("아이디 : "+user.getLoginId()+", 이름 : "+user.getUserNm());
			}else if("stdntRgs".equals(uri)){
				//UserInfo user = memberMapper.selectUserInfoByPk(accUserId);
				//accUserInfo += ("아이디 : "+user.getLoginId()+", 이름 : "+user.getUserNm());
				return;
			}else if("stdntRgsProc.json".equals(uri)){
				UserInfo user = memberMapper.selectUserInfoByPk(accUserId);
				accUserInfo += ("아이디 : "+user.getLoginId()+", 이름 : "+user.getUserNm());
				logAct = "U";
			}
		} catch (NullPointerException e) {
			accUserId = "";
			accUserInfo = "";
		}
		
		if(menu != null){
			
			ConnLog log = new ConnLog(
					MaxNumUtil.getSequence(MaxNumUtil.FUNC_LOG), 
					year, 
					month, 
					day, 
					hour, 
					connDe, 
					pageUrl, 
					menu.getMenuId(), 
					menu.getMenuNm(), 
					menuDiv, 
					LncUtil.replaceNull(SessionUserInfoHelper.getUserId(), GUEST), 
					browser, 
					device, 
					ip,
					logAct,
					accUserId,
					accUserInfo
					);
			logMapper.insertConnLog(log);
		}
	}

	/**
	 * 로그인로그 저장
	 * @param loginLog
	 */
	@Override
	public void insertLoginLog(HttpServletRequest request, String userId, String loginId, String role) {
		
		Date loginDe = new Date();
		String year = DateUtil.getDate2Str(loginDe, "yyyy");
		String month = DateUtil.getDate2Str(loginDe, "MM");
		String day = DateUtil.getDate2Str(loginDe, "dd"); 
		String hour = DateUtil.getDate2Str(loginDe, "HH");
		
		String reqDevice = this.getDevice(request);
		String reqBrowser = this.getBrowser(request);
		String reqIp = LncUtil.getIp(request);
		
		LoginLog loginLog = new LoginLog(0,userId, loginId, year, month, day, hour, loginDe,reqIp ,reqDevice, reqBrowser,role);
		logMapper.insertLoginLog(loginLog);
	}

	/**
	 * 관리자 접속이력
	 * @param userId
	 * @return
	 */
	@Override
	public List<ConnLogAdm> getAdminConnList(String userId) {
		return logMapper.selectAdminConnList(userId);
	}
	/**
	 * 관리자 접속이력리스트
	 * @param userId
	 * @return
	 */
	@Override
	public ResultVO getLctreLoginLogPageListByMngr(LoginLog vo,int listRow) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			//총 갯수
			int totalCnt = logMapper.selectLoginLogPageCnt(vo);
			
			//페이징처리
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
	        paginationInfo.setCurrentPageNo(vo.getPage());
	        paginationInfo.setRecordCountPerPage(vo.getRow());
	        paginationInfo.setPageSize(DEFAULT_PAGE_SIZE);
	        vo.setRow(listRow);
	        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			 
			//단체신청명단
			List<LoginLog> loginLogList = logMapper.selectLoginLogPageList(vo);
			rstData.put("LoginLogList", loginLogList);
			rstData.put("pageNavi",paginationInfo);
			result.setResult(1);
			return result; 
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}
}

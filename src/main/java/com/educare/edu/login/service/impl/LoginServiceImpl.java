package com.educare.edu.login.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.log.service.LogService;
import com.educare.edu.login.service.LoginService;
import com.educare.edu.login.web.LoginController;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.CryptoUtil;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

/**
 * @Class Name : LoginServiceImpl.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 2.
 * @version 1.0
 * @see
 * @Description 로그인 서비스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Service("LoginService")
public class LoginServiceImpl implements LoginService {
	
	private static final String ID_PWD_ERR_MSG = "아이디/패스워드를 확인하세요.";
	private static final String ERR_MSG = "에러가 발생하였습니다. 관리자에게 문의하세요";
	public static final String ID_ERR_MSG = "아이디를 확인하세요.";
	public static final String ID_ERR_MSG_LOCK = "현재 ID는 [계정잠금] 상태입니다.<br/>관리자에게 문의하세요.";
	public static final String ID_ERR_MSG_LOCK_START = "비밀번호 5회 오류입니다.<br/> 계정이 잠겼습니다. 관리자에게 연락주세요.";
	private static final String ADMIN_AUTH = "관리자만 접근 가능합니다.";
	private static final String LOGIN_LOCK_730 = "최종 로그인 730일이 경과하여<br/>[계정잠금] 상태가 되었습니다.<br/>관리자에게 문의하세요.";
	public static final String LOGIN_SUCCESS = "SUCCESS";
	private static final String SUB_DN_ERR_MSG = "GPKI 인증서 연계를 한 사용자가 존재하지 않습니다.<br/>먼저 GPKI 인증서를 연계하세요.";
	private static final String SUB_DN2_ERR_MSG = "모바일공무원증 연계를 한 사용자가 존재하지 않습니다.<br/>먼저 모바일공무원증을 연계하세요.";

	private static final Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class.getName());
	
//	@Autowired
//	private RedisTemplate<String, Object> redisTemplate;
	
	/** 회원 Mapper */
	@Resource(name="MemberMapper")
	private MemberMapper memberMapper;
	
	/** 로그 서비스 */
	@Resource(name="LogService")
	private LogService logService;
	
	/**
	 * 로그인 성공 후 처리
	 * 세션저장, 최종 로그인 UPDATE, 로그인 로그 저장
	 * @param userId
	 */
	private void loginSuccessProc(HttpServletRequest request, UserInfo user, String role){
		
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer(UserInfo.class));
//		
//		redisTemplate.opsForValue().set(user.getUserId(), user);
		
		SessionUserInfoHelper.saveUserInfo(user);
		logService.insertLoginLog(request, user.getUserId(), user.getLoginId(), role); 
		user.setLstLoginDe(new Date());
		memberMapper.updateLoginSuccess(user);
	}
	
	/**
	 * 관리자 로그인
	 * @param userId
	 * @param userPw
	 * @return
	 */
	@Override
	public String adminLogin(HttpServletRequest request, String userId, String userPw) {
		
		String result = ID_PWD_ERR_MSG;
		
		// 셀렉트ID 조회
		UserInfo user2 = memberMapper.selectUserInfoLoginAdminByLoginId(userId);
		// 아이디는 있음
		if(user2 != null && user2.getUserId() != null && !"W".equals(user2.getState())) { 
			UserInfo user = memberMapper.selectUserInfoLoginAdmin(new UserInfo(userId, CryptoUtil.encodeSHA256CryptoNotDecode(userPw)));
			if(user != null && user.getUserId() != null) {
				if(LncUtil.nvlInt(user.getUserMemLvl()) > 4) { 
					return ADMIN_AUTH;
				}
				
				if(user.getState().equals("S")){
					return "사용 중지된 부관리자 입니다.";
				} 
	
				Date lstLoginDe = user.getLstLoginDe();
				Date compareDe = DateUtil.dayCalForStr(Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "yyyy")), 
						Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "MM")), 
						Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "dd")), 730, "");
				
				int comPareDate = Integer.parseInt(DateUtil.getDate2Str(compareDe, "yyyyMMdd"));
				int curDate = Integer.parseInt(DateUtil.getDate2Str(new Date(), "yyyyMMdd"));
				
				if(comPareDate < curDate && !UserInfo.MEM_LVL_SUPER_ADM.equals(user.getUserMemLvl())){
					result = LOGIN_LOCK_730;
				}else{
					this.loginSuccessProc(request, user, "A");
					result = LOGIN_SUCCESS;
				}
			}else{
				//비밀번호 틀렸을때임
				//매퍼.셀렉트ID
				 
				//해당 ID 비밀번호 실패횟수 가져오기
				int pwdErrorCnt = user2.getPwdErrorCnt();
				if(pwdErrorCnt>=4&&!(Integer.parseInt(user2.getUserMemLvl())==1)){ 
					user2.setLoginId(userId);
					user2.setState("W");
					memberMapper.updateLoginState(user2);
					result = ID_ERR_MSG_LOCK_START;
				}else{
				//	매퍼.업데이트pwd_error_cnt 값 +1
					user2.setLoginId(userId);
					memberMapper.updateLoginPwdErrorCntPlus(user2);
					result = result + "<br/>비밀번호 틀린 횟수 : "+ (pwdErrorCnt+1) +"/5";
				}
			}
		}else if(user2 == null || user2.getUserId() == null){
			//아이디 자체가 없는 경우
			//result = "해당 아이디는 존재하지않습니다."
			result = ID_ERR_MSG; 
		}else if("W".equals(user2.getState())){
			//잠긴계정인 경우
			//result = "현재 ID는 [계정잠금] 상태입니다.<br/>관리자에게 문의하세요."
			result = ID_ERR_MSG_LOCK;
		}
		return result;
	}

	/**
	 * 사용자 로그인
	 * @param userInfo
	 * @return
	 */
	@Override
	public ResultVO userLogin(HttpServletRequest request, UserInfo userInfo) {
		ResultVO result =  new ResultVO();
		Map<String, Object> rstData = new HashMap<String, Object>();
		result.setData(rstData);
		try {
			String url = "/user/index.do"; 
			
			UserInfo user = memberMapper.selectUserInfoLogin(userInfo);
			if(user == null) {
				result.setResult(0);
				result.setMsg("사용자가 존재하지 않습니다.");
				return result;
			}
			
			String password0 = user.getUserPw();//저장비번
			String password1 = userInfo.getUserPw();//입력비번
			LOG.info("password0: {}",password0);
			LOG.info("password1: {}",password1);
			if(!password1.equals(password0)){
				result.setResult(0);
				result.setMsg(ID_PWD_ERR_MSG);
				return result;
			}
			
			Date lstLoginDe = user.getLstLoginDe();
			if(lstLoginDe==null){
				lstLoginDe = DateUtil.getCalendar().getTime();
			}
			Date compareDe = DateUtil.dayCalForStr(Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "yyyy")), 
					Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "MM")), 
					Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "dd")), 730, "");
			
			
			int comPareDate = Integer.parseInt(DateUtil.getDate2Str(compareDe, "yyyyMMdd"));
			int curDate = Integer.parseInt(DateUtil.getDate2Str(new Date(), "yyyyMMdd"));
			if(comPareDate < curDate){
				result.setMsg(LOGIN_LOCK_730);
				result.setResult(-1);
			}else{
				this.loginSuccessProc(request, user, "U");
				//result.setMsg("로그인했습니다.");
				result.setResult(1);
				if(SessionUserInfoHelper.isAdmin()){
					url = "/admin/main/index.do";
				}
			}
			rstData.put("url",url);
			return result;
			
		} catch (NullPointerException e) {
			result.setMsg(ERR_MSG);
			result.setResult(0);
			return result;
		}
		
	}

	@Override
	public ResultVO userLoginBySubDn(HttpServletRequest request, UserInfo userInfo) {
		ResultVO result =  new ResultVO();

		try {
			
			UserInfo user = memberMapper.selectUserInfoLoginBySubDn(userInfo);
			if(user == null) {
//				result.setResult(0);
				result.setMsg(SUB_DN_ERR_MSG);
				return result;
			}
			
			Date lstLoginDe = user.getLstLoginDe();
			if(lstLoginDe==null){
				lstLoginDe = DateUtil.getCalendar().getTime();
			}
			Date compareDe = DateUtil.dayCalForStr(Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "yyyy")), 
					Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "MM")), 
					Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "dd")), 730, "");
			
			
			int comPareDate = Integer.parseInt(DateUtil.getDate2Str(compareDe, "yyyyMMdd"));
			int curDate = Integer.parseInt(DateUtil.getDate2Str(new Date(), "yyyyMMdd"));
			if(comPareDate < curDate){
				result.setMsg(LOGIN_LOCK_730);
				result.setResult(-1);
			}else{
				this.loginSuccessProc(request, user, "U");
				//result.setMsg("로그인했습니다.");
				result.setResult(1);

			}

			return result;
			
		} catch (NullPointerException e) {
			result.setMsg(ERR_MSG);
			result.setResult(0);
			return result;
		}
		
	}
	
	@Override
	public ResultVO userLoginBySubDn2(HttpServletRequest request, UserInfo userInfo) {
		ResultVO result =  new ResultVO();

		try {
			
			UserInfo user = memberMapper.selectUserInfoLoginBySubDn2(userInfo);
			if(user == null) {
//				result.setResult(0);
				result.setMsg(SUB_DN2_ERR_MSG);
				return result;
			}
			
			Date lstLoginDe = user.getLstLoginDe();
			if(lstLoginDe==null){
				lstLoginDe = DateUtil.getCalendar().getTime();
			}
			Date compareDe = DateUtil.dayCalForStr(Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "yyyy")), 
					Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "MM")), 
					Integer.parseInt(DateUtil.getDate2Str(lstLoginDe, "dd")), 730, "");
			
			
			int comPareDate = Integer.parseInt(DateUtil.getDate2Str(compareDe, "yyyyMMdd"));
			int curDate = Integer.parseInt(DateUtil.getDate2Str(new Date(), "yyyyMMdd"));
			if(comPareDate < curDate){
				result.setMsg(LOGIN_LOCK_730);
				result.setResult(-1);
			}else{
				this.loginSuccessProc(request, user, "U");
				//result.setMsg("로그인했습니다.");
				result.setResult(1);

			}

			return result;
			
		} catch (NullPointerException e) {
			result.setMsg(ERR_MSG);
			result.setResult(0);
			return result;
		}
		
	}
}

package com.educare.edu.login.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.component.UtilComponent;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.SyncDataService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.login.service.LoginService;
import com.educare.edu.login.service.impl.LoginServiceImpl;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.mobileid.mapper.QrTableMapper;
import com.educare.edu.mobileid.model.QrTable;
import com.educare.util.AESCryptoUtil;
import com.educare.util.CryptoUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpki.gpkiapi.cert.X509Certificate;
import com.gpki.servlet.GPKIHttpServletRequest;
import com.gpki.servlet.GPKIHttpServletResponse;

/**
 * @Class Name : LoginController.java
 */
@Controller
public class LoginController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class.getName());
	/** 로그인 서비스 */
	@Resource(name = "LoginService")
	private LoginService loginService;
	/** 로그인 서비스 */
	@Resource(name = "SyncDataService")
	private SyncDataService syncDataService;
	@Resource(name = "CheckService")
	private CheckService checkService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	@Autowired
	private QrTableMapper qrTableMapper;
	
	/**
	 * 관리자 로그인
	 * @param userId
	 * @param userPw
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/login.do")
	public String adminLogin(
			String userId,
			String userPw,
			String encIv,
			String encKey,
			HttpServletRequest request,
			ModelMap model) {
		
		//관리자아이피 체크
		ResultVO adminIpCheck = checkService.checkAdminIp();
		if(adminIpCheck.getResult()!=1){
			model.addAttribute("message", adminIpCheck.getMsg());
			model.addAttribute("moveUrl", "/");	
			return "alert";
		}
		
		if(SessionUserInfoHelper.isLogined()){
			if(SessionUserInfoHelper.isAdmin()){
				return "redirect:/admin/main.do";
			}else{
				return "redirect:/user/index.do";
			}
		}
		
		if(userId != null && !"".equals(userId)){
			LOG.info("userId : {}",userId);
			LOG.info("userPw : {}",userPw);
			AESCryptoUtil.setIV(encIv);
			AESCryptoUtil.setKEY(encKey);
			userId = AESCryptoUtil.decrypt(userId);
			userPw = AESCryptoUtil.decrypt(userPw);
			LOG.info("userId2 : {}",userId);
			LOG.info("userPw2 : {}",userPw);
			
			String result = loginService.adminLogin(request, userId, userPw);	
			if(result != null && LoginServiceImpl.LOGIN_SUCCESS.equals(result)){
				return "redirect:/admin/main/index.do";
			}
			model.addAttribute("message", result);
			model.addAttribute("moveUrl", "/admin/login.do");	
			return "alert";
		}
		
		return "/login/admin/login";
	}
	
	/**
	 * 관리자 로그아웃
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/logout.do")
	public String adminLogout(
			ModelMap model) {
		
		SessionUserInfoHelper.removeUserInfo();
		model.addAttribute("moveUrl", "/admin/login.do");
		
		return "login/admin/logout";

	}
	
	/**
	 * 사용자 로그인
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/login.do")
	public String userLogin(ModelMap model) {
		if(SessionUserInfoHelper.isLogined()){
			return "redirect:/user/index.do";
		}
		return "/login/user/login";
	}
	
	/**
	 * 사용자 로그인 처리
	 * @param request
	 * @param model
	 * @param userId
	 * @param userPwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user/login.json")
	public ResultVO userLogin(
			ModelMap model,HttpSession session,
			HttpServletRequest request,
			@RequestParam String loginId,
			@RequestParam String loginPw,
			@RequestParam String encIv,
			@RequestParam String encKey,
			@RequestParam(defaultValue="9") String userMemLvl
			) {
		ResultVO result = new ResultVO();
		result.setMsg("로그인에 실패하였습니다.");
		
		//250521 enc
		LOG.info("loginId : {}",loginId);
		LOG.info("loginPw : {}",loginPw);
		AESCryptoUtil.setIV(encIv);
		AESCryptoUtil.setKEY(encKey);
		loginId = AESCryptoUtil.decrypt(loginId);
		loginPw = AESCryptoUtil.decrypt(loginPw);
		LOG.info("loginId2 : {}",loginId);
		LOG.info("loginPw2 : {}",loginPw);
		
		if(loginId != null && loginPw != null && loginPw.length() > 3){
			//학생인경우 데이터연동
			/*
			 */
			UserInfo userInfo = new UserInfo();
			userInfo.setLoginId(loginId);
			userInfo.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(loginPw));
			userInfo.setUserMemLvl(userMemLvl);
			result = loginService.userLogin(request,userInfo);
			
		}
		
        return result;
    }
	
	/**
	 * 회원 로그아웃
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/logout.do")
	public String userLogout(
			ModelMap model) {
		
		SessionUserInfoHelper.removeUserInfo();
		model.addAttribute("moveUrl", "/user/index.do");
		
		return "login/admin/logout";

	}

	@RequestMapping("/user/loginBySubDn.do")
	public String userLoginBySubDn(
		    HttpServletResponse response,
		    HttpServletRequest request,
			ModelMap model) {
 
    	// 1. GPKI 인증
    	GPKIHttpServletResponse gpkiresponse = null;
		GPKIHttpServletRequest gpkirequest = null;
		String dn = "";
		try{
			gpkiresponse = new GPKIHttpServletResponse(response);
			gpkirequest = new GPKIHttpServletRequest(request);
			gpkiresponse.setRequest(gpkirequest);
			X509Certificate cert = null; 
	 
			byte[] signData = null;
			byte[] privatekey_random = null;
			String signType = "";
			String queryString = "";
	 
			cert = gpkirequest.getSignerCert();
			dn = cert.getSubjectDN();
	 
			java.math.BigInteger b = cert.getSerialNumber();
			b.toString();
			int message_type =  gpkirequest.getRequestMessageType();
			if( message_type == gpkirequest.ENCRYPTED_SIGNDATA || 
			    message_type == gpkirequest.LOGIN_ENVELOP_SIGN_DATA ||
			    message_type == gpkirequest.ENVELOP_SIGNDATA || 
			    message_type == gpkirequest.SIGNED_DATA){
			    signData = gpkirequest.getSignedData();
			    if(privatekey_random != null) {
				privatekey_random   = gpkirequest.getSignerRValue();
			    }
			    signType = gpkirequest.getSignType();
			}       
			queryString = gpkirequest.getQueryString();
		}catch(Exception e){
			model.addAttribute("message", e.getMessage());
			model.addAttribute("moveUrl", "/user/login.do");	
			return "alert";
		}
	 
		// 2. 업무사용자 테이블에서 dn값으로 사용자의 ID, PW를 조회하여
		// 이를 일반로그인 형태로 인증하도록 함
		if (dn != null && !"".equals(dn)) {
			
			UserInfo userInfo = new UserInfo();
			userInfo.setSubDn(CryptoUtil.encodeSHA256CryptoNotDecode(dn));
			userInfo.setUserMemLvl("9");
			
			ResultVO result = loginService.userLoginBySubDn(request, userInfo);
			if (result.getResult() > 0){
			    return "redirect:/user/index.do";
			}else if(result.getResult() == -9){
				model.addAttribute("message", "통신오류로 인해 로그인이 실패하였습니다. 다시 시도하시기 바랍니다.");
			}else{
				model.addAttribute("message", result.getMsg());
			}
			model.addAttribute("moveUrl", "/user/login.do");	
			return "alert";
		}
		
		return "/login/user/login";
	}
	
	@RequestMapping("/user/loginBySubDn2.do")
	public String userLoginBySubDn2(
			HttpSession session,
		    HttpServletRequest request,
		    ModelMap model) {
		
		String nonce = (String) session.getAttribute("nonce");
		QrTable qrTable = qrTableMapper.selectQrTableByNonce(nonce);
		String dn = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(qrTable.getVcJson());
			JsonNode unprotectedArray = rootNode.get("unprotected");
			if (unprotectedArray.isArray() && unprotectedArray.size() > 0) {
	            dn = unprotectedArray.get(0).get("value").asText();
//	            System.out.println("value: " + dn);
	        }
		} catch (Exception e) {
            e.printStackTrace();
        }
		
		// 2. 업무사용자 테이블에서 dn값으로 사용자의 ID, PW를 조회하여
		// 이를 일반로그인 형태로 인증하도록 함
		if (dn != null && !"".equals(dn)) {
			
			UserInfo userInfo = new UserInfo();
			userInfo.setSubDn2(CryptoUtil.encodeSHA256CryptoNotDecode(dn));
			userInfo.setUserMemLvl("9");
			
			ResultVO result = loginService.userLoginBySubDn2(request, userInfo);
			if (result.getResult() > 0){
			    return "redirect:/user/index.do";
			}else if(result.getResult() == -9){
				model.addAttribute("message", "통신오류로 인해 로그인이 실패하였습니다. 다시 시도하시기 바랍니다.");
			}else{
				model.addAttribute("message", result.getMsg());
			}
			model.addAttribute("moveUrl", "/user/login.do");	
			return "alert";
		}
		
		return "/login/user/login";
	}
}
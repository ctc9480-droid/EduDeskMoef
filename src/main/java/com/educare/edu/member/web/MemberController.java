package com.educare.edu.member.web;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.component.EhCacheComponent;
import com.educare.component.UtilComponent;
import com.educare.component.VarComponent;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.mapper.UserCompMapper;
import com.educare.edu.comn.model.UserComp;
import com.educare.edu.comn.service.SmsService;
import com.educare.edu.comn.service.SyncDataService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.MemberVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.InstrctrAttach;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.mobileid.mapper.QrTableMapper;
import com.educare.edu.mobileid.model.QrTable;
import com.educare.util.CryptoUtil;
import com.educare.util.FileUtil;
import com.educare.util.JwtTokenProvider;
import com.educare.util.LncUtil;
import com.educare.util.SelfAuthUtil;
import com.educare.util.SendEmail;
import com.educare.util.SendEmailVo;
import com.educare.util.XmlBean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

/**
 * @Class Name : MemberController.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 30.
 * @version 1.0
 * @see
 * @Description 회원 컨트롤러
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 30.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Controller
public class MemberController {
	private static final Logger LOG = LoggerFactory.getLogger(MemberController.class.getName());
	/** 회원 서비스 */
	@Resource(name = "MemberService")
	private MemberService memberService;
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	@Resource(name = "TableMapper")
	private TableMapper tableMapper;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	@Resource(name = "UserCompMapper")
	private UserCompMapper userCompMapper;
	@Resource(name = "SyncDataService")
	private SyncDataService syncDataService;
	@Resource(name = "SmsService")
	private SmsService smsService;
	
	@Resource(name = "eccp")
	private EhCacheComponent eccp;
	
	@Autowired
	private QrTableMapper qrTableMapper;
	
	/**
	 * 강사 첨부파일 다운로드
	 * @param fileSeq
	 * @param request
	 * @param response
	 */
	@RequestMapping("/instrctr/attach/{fileSeq}/download.do")
	public void download(
			@PathVariable( "fileSeq" ) Integer fileSeq,
			HttpServletRequest request,
			HttpServletResponse response
			) {
		
		InstrctrAttach attach = memberService.getInstrctrAttach(fileSeq);
		
		String filePath = MemberService.INSTRCTR_ATTACH_PATH + attach.getFileRename();
		String fileOrg = attach.getFileOrg();
		
		FileUtil.download(filePath, fileOrg, request, response);
	}
	
	/**
	 * 강사 첨부파일 사이즈 
	 * @param model
	 * @param fileSeq
	 */
	@RequestMapping("/instrctr/attach/size.json")
	public String size(
			ModelMap model,
			Integer fileSeq) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuccess = false;
		int fileSize = 0;
		
		InstrctrAttach attach = memberService.getInstrctrAttach(fileSeq);
		if(attach != null){
			isSuccess = true;
			fileSize = attach.getFileSize();
		}
		
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("fileSize", fileSize);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
	}
	
	/**
	 * 아이디 대량등록 양식 다운로드
	 * @param request
	 * @param response
	 */
	@RequestMapping("/member/sample/bulkDown.do")
	public void bulkDown(
			HttpServletRequest request,
			HttpServletResponse response
			) {
		
		String filePath = (String) XmlBean.getServerContextRoot() + "/sample/user_bulk.xls";
		FileUtil.download(filePath, "아이디대량등록.xls", request, response);
	}
	
	/**
	 * 회원가입
	 * joinGb: S=학생,I=강사
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/user/join.do")
	public String joinStep1(ModelMap model ,HttpServletResponse res
			,@RequestParam(defaultValue="1") String stepNo
			,@RequestParam(defaultValue="S") String joinGb
			,String authTypeNm
			,String authEncData
			) throws Exception{
		
		if(SessionUserInfoHelper.isLogined()){
			return "redirect:/user/index.do";
		}
		
		/*
		 */
		if("3".equals(stepNo)){
			//중복가입인지 체크
			ResultVO result = memberService.checkDupAuth(authTypeNm, authEncData);
			if(result.getResult()!= 1){
				LncUtil.alertBack(res, result.getMsg());
				return null;
			}
		}
		
		model.addAttribute("stepNo",stepNo);
		model.addAttribute("joinGb",joinGb);
		return "/user/member/join";
	}
	
	/**
	 * 아이디/비밀번호 찾기
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/findIdPw.do")
	public String findIdPw(
			ModelMap model
			){
		
		if(SessionUserInfoHelper.isLogined()){
			return "redirect:/user/index.do";
		}
		
		return "/user/member/findIdPw";
	}
	@ResponseBody
	@RequestMapping("/user/findIdPwAuth.ajax")
	public ResultVO findIdPw(
			ModelMap model
			,String loginId
			,String authEncData
			){
		
		ResultVO result = memberService.checkDupAuth("CHECK", authEncData);
		Map<String,String> rstData = (Map<String, String>) result.getData();
		String loginId2 = rstData.get("loginId");
		
		ResultVO result2 = new ResultVO();
		if(!loginId2.equals(loginId)) {
			result2.setResult(0);
			result2.setMsg("입력하신 아이디와 인증정보가 일치하지 않습니다.");
			return result2;
		}
		
		result2.setResult(1);
		return result2;
	}
	
	
	/**
	 * 본인인증 중복검사
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/selfAuthDuplChk.json")
	public String selfAuthDuplChk(ModelMap model) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuccess = false;
		String message = "";
		String userNm = "";
		String ci = "";
		
		message = memberService.selfAuthDuplChk();
		if(message != null && MemberService.EXCUTE_SUCCESS.equals(message)){
			userNm = SelfAuthUtil.getUserNm();
			ci = SelfAuthUtil.getUserDi();
			isSuccess = true;
		}
		
		jsonObj.addProperty("userNm", userNm);
		jsonObj.addProperty("ci", ci);
		jsonObj.addProperty("message", message);
		jsonObj.addProperty("isSuccess", isSuccess);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	
	/**
	 * 아이디 중복체크
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("/member/loginIdChk.json")
	public String userIdChk(
			ModelMap model,
			@RequestParam String loginId) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuccess = false;
		String message = "";
		
		String regex = VarComponent.REGEX_USER_ID; 
		//String regex = VarComponent.REGEX_EMAIL; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(loginId); 
		
		if(!m.matches()) {
			message = "올바른 아이디 형식이 아닙니다.";
		}else{
			if(!memberService.existLoginId(loginId) ){
				isSuccess = true;
			}else{
				message = "사용중인 아이디 입니다.";
			}
		}
		
		jsonObj.addProperty("message", message);
		jsonObj.addProperty("isSuccess", isSuccess);
		model.addAttribute("obj", jsonObj);

        return "obj";
    }
	
	/**
	 * 회원가입 처리
	 * @param model
	 * @param userInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/member/joinProc.ajax")
	public ResultVO joinProc(	ModelMap model
			//,UserInfo userInfo	
			,String etc
			,int userTp
			,String loginId
			,String userPw
			,String email
			,String tel
			,String mobile
			,String userNm
			,String userEnNm
			,String authTypeNm
			,String authEncData
			,int mfType
			,String smsYn
			,String userGradeNm
			,String userGradeCd
			,String userOrgCd
			,String userOrgNm
			,String addr
			,String addrDetail
			,String postNo
			,String subDn
//			,String encIv
//			,String encKey
			) {
		
		//인증정보로 중복가입체크하기
		ResultVO result2 = memberService.checkDupAuth(authTypeNm,authEncData);
		if(result2.getResult() != 1){
			return result2;
		}
		Map<String,String> rstData = (Map<String, String>) result2.getData();
		String authDupInfo = rstData.get("authDupInfo");
		String authBirth = rstData.get("authBirth");
		String authGender = rstData.get("authGender");
		String authUserNm = rstData.get("authUserNm");
//		int mfType = LncUtil.nvlInt(authGender);
		
		//
		UserInfo user = new UserInfo();
		user.setUserTp(userTp);
		user.setAddr(LncUtil.getEncode(addr));
		user.setAddrDetail(LncUtil.getEncode(addrDetail));
		user.setPostNo(postNo);
		user.setUserOrgCd(userOrgCd);
		user.setUserOrgNm(LncUtil.getEncode(userOrgNm));
		user.setUserGradeCd(userGradeCd);
		user.setUserGradeNm(LncUtil.getEncode(userGradeNm));
		user.setEmail(email);
		user.setMobile(mobile);
		user.setSmsYn(smsYn);
		user.setUserTp(userTp);
		user.setUserEnNm(userEnNm);
		user.setBirth(authBirth);
		user.setMfType(mfType==0?2:mfType);
		user.setCi(authDupInfo);
		user.setUserNm(authUserNm);
		user.setUserPw(userPw);
		user.setLoginId(loginId);
		user.setTel(tel);
		
		if (subDn != null && !"".equals(subDn)) {
//			LOG.info("subDn : {}",subDn);
//			AESCryptoUtil.setIV(encIv);
//			AESCryptoUtil.setKEY(encKey);
//			subDn = AESCryptoUtil.decrypt(subDn);
//			LOG.info("subDn2 : {}",subDn);
			
			user.setSubDn(CryptoUtil.encodeSHA256CryptoNotDecode(subDn));
		}
		
		ResultVO result = memberService.joinUser(user);
		
		//회원가입알림톡 발송
		String userId = user.getUserId();
		String tplCd = "ktl_edu_001";
		String msg = VarComponent.getAlimTalkMsg(tplCd);
		//smsService.setSendSms(null,mobile,msg,null,0,userId,3,tplCd);
		
		return result;
    }
	@ResponseBody
	@RequestMapping("/member/updStdntProc.ajax")
	public ResultVO updStdntProc(ModelMap model
			,int userTp
			,String mobile
			,String tel
			,String email
			,String smsYn
			,String userGradeNm
			,String userGradeCd
			,String userOrgCd
			,String userOrgNm
			,String addr
			,String addrDetail
			,String postNo
			,String subDn
//			,String encIv
//			,String encKey
			) {
		
		String sessId = SessionUserInfoHelper.getUserId();
		
		//회원정보조회
		UserInfo user = memberMapper.selectUserInfoByPk(sessId);
		
		user.setUserTp(userTp);
		user.setAddr(LncUtil.getEncode(addr));
		user.setAddrDetail(LncUtil.getEncode(addrDetail));
		user.setPostNo(postNo);
		user.setUserOrgCd(userOrgCd);
		user.setUserOrgNm(LncUtil.getEncode(userOrgNm));
		user.setUserGradeCd(userGradeCd);
		user.setUserGradeNm(LncUtil.getEncode(userGradeNm));
		user.setEmail(email);
		user.setMobile(mobile);
		user.setSmsYn(smsYn);
		user.setTel(tel);
		
		if (subDn != null && !"".equals(subDn)) {
//			LOG.info("subDn : {}",subDn);
//			AESCryptoUtil.setIV(encIv);
//			AESCryptoUtil.setKEY(encKey);
//			subDn = AESCryptoUtil.decrypt(subDn);
//			LOG.info("subDn2 : {}",subDn);
			
			user.setSubDn(CryptoUtil.encodeSHA256CryptoNotDecode(subDn));
		}
		
		ResultVO result = memberService.updateUserInfoStdnt(user);
		
		eccp.updateUserPrivt(sessId);
		
		
		return result;
	}
	
	/**
	 * 아이디 찾기
	 * @param model
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/member/findId.json")
	public String findId(ModelMap model
			,String userNm
			,String mobile

			) {
		
		boolean isSuccess = false;
		String message = "";
		
		message = memberService.findId(userNm,mobile);
		if(message != null && message.contains("가입일자")){
			isSuccess = true;
		}
		
		model.addAttribute("isSuccess", isSuccess);
		model.addAttribute("message", message);
		
        return "jsonView";
    }
	
	/**
	 * 비밀번호 찾기
	 * @param model
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/member/findPw.json")
	public String findPw(
			ModelMap model,
			@ModelAttribute("vo") MemberVO vo) {
		
		boolean isSuccess = false;
		String message = "";
		
		String userId = vo.getLoginId();
		
		model.addAttribute("token", JwtTokenProvider.generateResetToken(userId));
		
		message = memberService.findPw(vo);
		if(message != null && MemberService.EXCUTE_SUCCESS.equals(message)){
			isSuccess = true;
		}
		
		model.addAttribute("isSuccess", isSuccess);
		model.addAttribute("message", message);
		
        return "jsonView";
    }
	
	/**
	 * 비밀번호 변경(찾기)
	 * @param model
	 * @param userId
	 * @param userPw
	 * @return
	 */
	@RequestMapping("/member/updPw.json")
	public String updPw(
			ModelMap model,
			String userPw,
			String token) {
		
//		JsonObject jsonObj = new JsonObject();
		boolean isSuccess = false;
		String message = "";
		
		String loginId = JwtTokenProvider.getUserIdFromToken(token);
		
		message = memberService.updPwStdnt(loginId, userPw);
		if(message != null && MemberService.EXCUTE_SUCCESS.equals(message)){
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		
		model.addAttribute("isSuccess", isSuccess);
		model.addAttribute("message", message);
		
        return "jsonView";
    }
	
	/**
	 * 비밀번호 변경
	 * @param model
	 * @param passOrg
	 * @param passNew
	 * @return
	 */
	@RequestMapping("/member/newPw.json")
	public String newPw(
			ModelMap model,
			String passOrg,
			String passNew) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isLogined = SessionUserInfoHelper.isLogined();
		boolean isSuccess = false;
		String message = "";
		
		if(isLogined){
			message = memberService.updPw(passOrg, passNew);
			if(message != null && MemberService.EXCUTE_SUCCESS.equals(message)){
				isSuccess = true;
			}
		}
		
		jsonObj.addProperty("isLogined", isLogined);
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	//개인정보 수정전 비밀번호 확인
	@RequestMapping("/user/mypage/chkPwdUser.do")
	public String chkPwdUser( 
			ModelMap model
			){
		
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message", "로그인이 필요합니다.");
			model.addAttribute("moveUrl", "/user/index.do");	
			return "alert";
		}
		
		if(!"7,8,9".contains(SessionUserInfoHelper.getUserMemLvl())){
			model.addAttribute("message", "접근 권한이 없습니다.");
			model.addAttribute("moveUrl", "/user/index.do");	
			return "alert";
		}
		
		return "/user/mypage/chkPwdUser" + LncUtil.TILES;
	}
	
	/**
	 * 개인정보 수정
	 * @param model
	 * @return
	 * @throws Exception 
 */
	@RequestMapping("/user/mypage/updUser.do")
	public String updUser( HttpServletResponse res,ModelMap model
			,String userPw
			) throws Exception{
		
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message", "로그인이 필요한 서비스입니다.");
			model.addAttribute("moveUrl", "/user/login.do?");	
			return "alert";
		}
		
		if(!"7,8,9".contains(SessionUserInfoHelper.getUserMemLvl())){
			model.addAttribute("message", "접근 권한이 없습니다.");
			model.addAttribute("moveUrl", "/user/index.do");	
			return "alert";
		}
		
		//사용자정보 가져오기
		UserInfo userInfo = memberMapper.selectUserInfoByPk(SessionUserInfoHelper.getUserId());
		model.addAttribute("user",userInfo);
		
		//if(!ObjectUtils.isEmpty(userPw)){
			//if(!userInfo.getUserPw().equals(CryptoUtil.mysql_password(userPw))){
				//LncUtil.alertBack(res, "비밀번호가 일치하지 않습니다.");
				//return null;
			//}
		//}
		
		if(userInfo.getUserMemLvl().equals("8")){
			return "/user/mypage/updInstrctr" + LncUtil.TILES;
		}else{
			return "/user/mypage/updUser" + LncUtil.TILES;
		}
	}
	
	@RequestMapping("/user/mypage/userDelete.json")
	public String userDelete(
			ModelMap model,
			UserInfo userInfo
			) {
		
		int result = memberService.deleteUser(userInfo);
		model.addAttribute("result", result);
		return "jsonView";
	}
		
	/**
	 * 비밀번호 검사
	 * @param model
	 * @param userPw
	 * @return
	 */
	@RequestMapping("/member/passWdChk.json")
	public String passWdChk(
			ModelMap model,
			String userPw) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuccess = false;
		boolean isLogined = SessionUserInfoHelper.isLogined();
		String message = "";
		
		if(isLogined){
			String loginId = "";
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			if(user != null){
				loginId = user.getLoginId();
			}
			message = memberService.passWdChk(loginId, userPw);
			if(message != null && MemberService.EXCUTE_SUCCESS.equals(message)){
				isSuccess = true;
			}
		}
		
		jsonObj.addProperty("isLogined", isLogined);
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	
	
	/**
	 * 회원정보 수정
	 * @param model
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/member/updInstrProc.json")
	public String updInstrProc(	ModelMap model,	UserInfo userInfo	
			) {

		JsonObject jsonObj = new JsonObject();
		boolean isSuccess = false;
		String message = "";
		
		if("7".contains(SessionUserInfoHelper.getUserMemLvl())){
			message = memberService.updateUserInfoInstrctr(userInfo);
		}
		if(message != null && MemberService.EXCUTE_SUCCESS.equals(message)){
			isSuccess = true;
		}
		
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	
	
	/**
	 * 비밀번호 변경
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/mypage/updPw.do")
	public String updPw(
			ModelMap model
			){
		
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message", "접근 권한이 없습니다.");
			model.addAttribute("moveUrl", "/user/index.do");	
			return "alert";
		}else{
			if(!UserInfo.MEM_LVL_STDNT.equals(SessionUserInfoHelper.getUserMemLvl())
					&& !UserInfo.MEM_LVL_INSTRCTR.equals(SessionUserInfoHelper.getUserMemLvl())
					){
				model.addAttribute("message", "접근 권한이 없습니다.");
				model.addAttribute("moveUrl", "/user/index.do");	
				return "alert";
			}
		}
		
		return "/user/mypage/updPw" + LncUtil.TILES;
	}
	
	@RequestMapping("/member/sendAuthNoEmail.json")
	public String sendAuthNoEmail(ModelMap model,HttpServletRequest req
			,String loginId
			){
		UserInfo vo2 = new UserInfo();
		vo2.setLoginId(loginId);
		vo2.setUserMemLvl("9");
		UserInfo userInfo = memberMapper.selectUserInfoByLoginId(loginId);
		
		String authNo = LncUtil.numberGen(4, 1);
		SendEmailVo vo = new SendEmailVo();
		vo.setToEmail(userInfo.getLoginId());
		vo.setSubject("KTL - 비밀번호 재설정");
		vo.setMessage("인증번호 : "+authNo);
		
		String result = SendEmail.sendEmail(vo);
		if(StringUtils.isEmpty(result)){
			req.getSession().setAttribute("authNo", authNo);
			model.addAttribute("result",1);
			return "jsonView";
		}else{
			req.getSession().removeAttribute("authNo");
			model.addAttribute("result",0);
			return "jsonView";
		}
	}
	
	@RequestMapping("/member/confirmAuthNoEmail.json")
	public String confirmAuthNoEmail(ModelMap model,HttpServletRequest req
			,String authNo
			){
		try {
			String sessAuthNo = req.getSession().getAttribute("authNo").toString();
			if(sessAuthNo.equals(authNo)){
				model.addAttribute("result",1);
				return "jsonView";
			}else{
				model.addAttribute("result",2);
				return "jsonView";
			}
		} catch (NullPointerException e) {
			model.addAttribute("result",0);
			return "jsonView";
		}
	}
	
	@RequestMapping("/member/stdnt/download.do")
	public void download(HttpServletRequest request,HttpServletResponse response
			,String fileGubun
			) throws Exception {
		if(!SessionUserInfoHelper.isLogined()){
			LncUtil.alertBack(response, "잘못된 접근입니다.");
		}
		
		String userId = SessionUserInfoHelper.getUserId();
		String filePath = "upload/member/";
		String fileOrg = "";
		
		if("bsnLcns".equals(fileGubun)){
			UserComp vo = new UserComp();
			vo.setUserId(userId);
			UserComp comp = userCompMapper.selectByPk(vo);
			filePath+=comp.getBsnLcnsRnm();
			fileOrg = comp.getBsnLcnsOrg();
		}else if("bsnBank".equals(fileGubun)){
			UserComp vo = new UserComp();
			vo.setUserId(userId);
			UserComp comp = userCompMapper.selectByPk(vo);
			filePath+=comp.getBsnBankRnm();
			fileOrg = comp.getBsnBankOrg();
		}
		
		FileUtil.download(filePath, fileOrg, request, response);
	}
	
	/**
	 * 회원가입 - 본인인증 sample
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/checkplus_main.do")
	public String checkplus_main(ModelMap model, HttpServletRequest req
			){
		
		//if(SessionUserInfoHelper.isLogined()){
			//return "redirect:/user/index.do";
		//}
		
		/*
		String device = LncUtil.getDevice(req);
		String mobile = "";
		if ("M".equals(device)) {
			mobile = "Mobile";
		}
		
		String domainNm = XmlBean.getConfigValue("domain.name");
		
		model.addAttribute("domainNm", domainNm);
		model.addAttribute("mobileInfo", mobile);\
		*/
		
		return "/user/member/niceId/checkplus_main";
	}
	
	/**
	 * 회원가입 - 본인인증  성공 sample, dykim
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/checkplus_success.do")
	public String checkplus_success(ModelMap model 
			){
		
		//if(SessionUserInfoHelper.isLogined()){
			//return "redirect:/user/index.do";
		//}
		return "/user/member/niceId/checkplus_success";
	}
	
	/**
	 * 회원가입 - 본인인증  실패 sample
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/checkplus_fail.do")
	public String checkplus_fail(ModelMap model 
			){
		
		//if(SessionUserInfoHelper.isLogined()){
			//return "redirect:/user/index.do";
		//}
		return "/user/member/niceId/checkplus_fail";
	}
	
	/**
	 * 회원가입 - 본인인증 IPIN sample
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/ipin_main.do")
	public String ipin_main(ModelMap model, HttpServletRequest req
			){
		
		//if(SessionUserInfoHelper.isLogined()){
			//return "redirect:/user/index.do";
		//}
		
		return "/user/member/niceId/ipin_main";
	}
	
	/**
	 * 회원가입 - 본인인증  IPIN process sample, dykim
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/ipin_process.do")
	public String ipin_process(ModelMap model 
			){
		
		//if(SessionUserInfoHelper.isLogined()){
			//return "redirect:/user/index.do";
		//}
		return "/user/member/niceId/ipin_process";
	}
	
	/**
	 * 회원가입 - 본인인증 IPIN result sample
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/ipin_result.do")
	public String ipin_result(ModelMap model 
			){
		
		//if(SessionUserInfoHelper.isLogined()){
			//return "redirect:/user/index.do";
		//}
		return "/user/member/niceId/ipin_result";
	}

	private String getCnValue(String dn) {
        if (dn == null || dn.isEmpty()) {
            return "";
        }

        String[] parts = dn.split(",");
        for (String part : parts) {
            part = part.trim();
            if (part.toLowerCase().startsWith("cn=")) {
                return part.substring(3);
            }
        }
        
        return "";
    }
	
	@RequestMapping("/user/mypage/updateGpkiInfo.do")
	public String updateGpkiInfo( HttpServletResponse res,ModelMap model
			,String subDn
//			,String encIv
//			,String encKey
			) throws Exception{
		
		if(!"7,8,9".contains(SessionUserInfoHelper.getUserMemLvl())){
			model.addAttribute("message", "접근 권한이 없습니다.");
			model.addAttribute("moveUrl", "/user/index.do");	
			return "alert";
		}
		
		//사용자정보 가져오기
		UserInfo userInfo = memberMapper.selectUserInfoByPk(SessionUserInfoHelper.getUserId());
		model.addAttribute("user",userInfo);
				
		if (subDn != null && !"".equals(subDn)) {
//			LOG.info("subDn : {}",subDn);
//			AESCryptoUtil.setIV(encIv);
//			AESCryptoUtil.setKEY(encKey);
//			subDn = AESCryptoUtil.decrypt(subDn);
//			LOG.info("subDn2 : {}",subDn);

			String cn = getCnValue(subDn);
			if (!cn.contains(userInfo.getUserNm())) {
				model.addAttribute("message", "올바른 GPKI 인증서가 아닙니다. 다시 시도하시기 바랍니다.");
				model.addAttribute("moveUrl", "/user/mypage/updUser.do");	
				return "alert";
			}
			userInfo.setSubDn(CryptoUtil.encodeSHA256CryptoNotDecode(subDn));
			
			String gongYn = "N";
//			if (!subDn.toLowerCase().contains("government of korea")) {
				gongYn = "Y";
				userInfo.setUserMemLvl("9");//공무원 인증시 정회원으로 변경,kbj250922
//			}
			userInfo.setGongYn(gongYn);
			
			ResultVO result = memberService.updateGpkiInfo(userInfo);
			if (result.getResult() > 0){
				SessionUserInfoHelper.saveUserInfo(userInfo);
				return "redirect:/user/mypage/updUser.do";
			}else{
				model.addAttribute("message", "GPKI 인증서 연계가 실패하였습니다. 다시 시도하시기 바랍니다.");
				model.addAttribute("moveUrl", "/user/mypage/updUser.do");	
				return "alert";
			}
		}
		
		if(userInfo.getUserMemLvl().equals("8")){
			return "/user/mypage/updInstrctr" + LncUtil.TILES;
		}else{
			return "/user/mypage/updUser" + LncUtil.TILES;
		}
	}
	
	@RequestMapping("/user/mypage/updateMobileId.do")
	public String updateMobileId( HttpServletResponse res,ModelMap model
			,HttpSession session
			) throws Exception{
		
		if(!"7,8,9".contains(SessionUserInfoHelper.getUserMemLvl())){
			model.addAttribute("message", "접근 권한이 없습니다.");
			model.addAttribute("moveUrl", "/user/index.do");	
			return "alert";
		}
		
		//사용자정보 가져오기
		UserInfo userInfo = memberMapper.selectUserInfoByPk(SessionUserInfoHelper.getUserId());
		model.addAttribute("user",userInfo);
				
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
		
		if (dn != null && !"".equals(dn)) {
//			LOG.info("subDn : {}",subDn);
//			AESCryptoUtil.setIV(encIv);
//			AESCryptoUtil.setKEY(encKey);
//			subDn = AESCryptoUtil.decrypt(subDn);
//			LOG.info("subDn2 : {}",subDn);

			String cn = getCnValue(dn);
			if (!cn.contains(userInfo.getUserNm())) {
				model.addAttribute("message", "올바른 모바일공무원증이 아닙니다. 다시 시도하시기 바랍니다.");
				model.addAttribute("moveUrl", "/user/mypage/updUser.do");	
				return "alert";
			}
			userInfo.setSubDn2(CryptoUtil.encodeSHA256CryptoNotDecode(dn));
			
			String gongYn = "N";
			if (!dn.toLowerCase().contains("government of korea")) {
				gongYn = "Y";
				userInfo.setUserMemLvl("9");//공무원 인증시 정회원으로 변경,kbj250922
			}
			userInfo.setGongYn(gongYn);
			
			ResultVO result = memberService.updateMobileId(userInfo);
			if (result.getResult() > 0){
			    return "redirect:/user/mypage/updUser.do";
			}else{
				model.addAttribute("message", "모바일공무원증 연계가 실패하였습니다. 다시 시도하시기 바랍니다.");
				model.addAttribute("moveUrl", "/user/mypage/updUser.do");	
				return "alert";
			}
		}
		
		if(userInfo.getUserMemLvl().equals("8")){
			return "/user/mypage/updInstrctr" + LncUtil.TILES;
		}else{
			return "/user/mypage/updUser" + LncUtil.TILES;
		}
	}
}

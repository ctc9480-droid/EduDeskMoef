package com.educare.edu.member.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.EhCacheComponent;
import com.educare.component.VarComponent;
import com.educare.edu.comn.service.StatService;
import com.educare.edu.comn.vo.CategoryAuthVO;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.CategoryAuthService;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.MemberVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.impl.MemberServiceImpl;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoInstrctr;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.google.gson.JsonObject;

/**
 * @Class Name : MemberAdminController.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 2.
 * @version 1.0
 * @see
 * @Description 회원 관리자 컨트롤러
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Controller
@RequestMapping(value="/admin/member/")
public class MemberAdminController {
	
	static final Logger LOG = LoggerFactory.getLogger(MemberAdminController.class);
	
	
	/** 회원 서비스 */
	@Resource(name = "MemberService")
	private MemberService memberService;
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	
	/** 카테고리 서비스 */
	@Resource(name = "CategoryService")
	private CategoryService categoryService;
	
	@Resource(name = "StatService")
	private StatService statService;
	@Resource(name = "CategoryAuthService")
	private CategoryAuthService categoryAuthService;
	
	@Resource(name = "eccp")
	private EhCacheComponent eccp;
	
	/**
	 * 회원정보 리스트
	 * @param vo
	 * @param model
	 * @return
	 */
	@RequestMapping("stdntList.do")
	public String stdntList(
			@ModelAttribute("vo") MemberVO vo,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.getStdntList(vo);
		
		model.addAttribute("pageNavi", resultMap.get("paginationInfo"));
		model.addAttribute("totalCnt", resultMap.get("totalCnt"));
		model.addAttribute("vo", vo);
		model.addAttribute("dataList", resultMap.get("dataList"));
		
        return "/admin/member/stdntList" + LncUtil.TILES;
    }
	
	/**
	 * 회원정보 리스트 엑셀다운
	 * @param model
	 * @return
	 */
	@RequestMapping("stdntListExcel.do")
	public void stdntListExcel(
			@ModelAttribute("vo") MemberVO vo,
			HttpServletRequest request, 
			HttpServletResponse response) {
		String excelFile = memberService.createStdntListExcel(vo);
		FileUtil.download(excelFile, "회원정보.xls", request, response);
    }
	@RequestMapping("stdntListExcel2.do")
	public String stdntListExcel2(
			@ModelAttribute("vo") MemberVO vo,
			ModelMap model) {
		List<UserInfo> dataList = memberMapper.selectStdntList(vo);
		
		model.addAttribute("dataList", dataList);
		return "admin/member/stdntList_excel";
    }
	
	/**
	 * 회원정보 상세보기
	 * @param vo
	 * @param model
	 * @return
	 */
	@RequestMapping("stdntView.do")
	public String stdntView(HttpServletRequest req
			,@RequestParam(defaultValue="") String userId
			,String tab
			,ModelMap model) {
		
		UserInfo user = memberService.getStdntInfo(userId);
		model.addAttribute("user", user);
		
		//진행중 교육,수료교육,신청중 교육, 내가 작성한 글  카운트
		//Map<String, Object> stat = statService.getMyStatCount(userId);
		//model.addAttribute("stat",stat);
		
		//캐시갱신
		eccp.updateUserPrivt(userId);
		
        return "/admin/member/stdntView" + LncUtil.TILES;
    }
	
	/**
	 * 회원정보 등록화면
	 * @param vo
	 * @param model
	 * @return
	 */
	@RequestMapping("stdntRgs.do")
	public String stdntRgs(
			@ModelAttribute("vo") MemberVO vo,
			ModelMap model) {
		
		UserInfo user = memberService.getStdntInfo(vo.getUserId());
		model.addAttribute("user", user);
		
        return "/admin/member/stdntRgs" + LncUtil.TILES;
    }
	
	/**
	 * 강사정보 리스트
	 * @param vo
	 * @param model
	 * @return
	 */
	@RequestMapping("instrctrList.do")
	public String instrctrList(
			@ModelAttribute("vo") MemberVO vo,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.getInstrctrList(vo);
		
		model.addAttribute("pageNavi", resultMap.get("paginationInfo"));
		model.addAttribute("totalCnt", resultMap.get("totalCnt"));
		model.addAttribute("vo", vo);
		model.addAttribute("dataList", resultMap.get("dataList"));
		model.addAttribute("moduleMap", resultMap.get("moduleMap"));
		
        return "/admin/member/instrctrList" + LncUtil.TILES;
    }
	
	/**
	 * 강사 등록화면
	 * @return
	 */
	@RequestMapping("instrctrRgs.do")
	public String instrctrRgs(ModelMap model, String userId) {
		LOG.info("test");
		UserInfo userInfo = memberService.getInstrctrInfo(userId);
		model.addAttribute("user",userInfo);
		
		List<CategoryVO> cateList = categoryService.getCategoryList(1);
		model.addAttribute("cateList",cateList);
		
		if(userInfo!=null){
			CategoryAuthVO authVO = new CategoryAuthVO();
			authVO.setUserId(userInfo.getUserId());
			List<CategoryAuthVO> authList = categoryAuthService.getCategoryAuthList(authVO);
			model.addAttribute("authList",authList);
		}
		
		return "/admin/member/instrctrRgs" + LncUtil.TILES;
    }
	
	/**
	 * 강사정보 보기화면
	 * @param vo
	 * @param model
	 * @return
	 */
	@RequestMapping("instrctrView.do")
	public String instrctrView(
			@ModelAttribute("vo") MemberVO vo,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.getInstrctrInfo(vo);

		model.addAttribute("user", resultMap.get("user"));
		model.addAttribute("module", resultMap.get("module"));
		model.addAttribute("attach01", resultMap.get("attach01"));
		model.addAttribute("attach02", resultMap.get("attach02"));
		model.addAttribute("attach03", resultMap.get("attach03"));
		model.addAttribute("attach09", resultMap.get("attach09"));
		
        return "/admin/member/instrctrView" + LncUtil.TILES;
    }
	
	/**
	 * 강사정보 수정화면
	 * @param vo
	 * @param model
	 * @return
	 */
	@RequestMapping("instrctrUpd.do")
	public String instrctrUpd(
			@ModelAttribute("vo") MemberVO vo,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.getInstrctrInfo(vo);

		model.addAttribute("user", resultMap.get("user"));
		model.addAttribute("module", resultMap.get("module"));
		model.addAttribute("moduleList", resultMap.get("moduleList"));
		model.addAttribute("attach01", resultMap.get("attach01"));
		model.addAttribute("attach02", resultMap.get("attach02"));
		model.addAttribute("attach03", resultMap.get("attach03"));
		model.addAttribute("attach09", resultMap.get("attach09"));
		model.addAttribute("ctgryList", categoryService.getCategoryEduList());
		
        return "/admin/member/instrctrUpd" + LncUtil.TILES;
    }
	
	@ResponseBody
	@RequestMapping("instrctrRgsProc.json")
	public ResultVO instrctrRgsProc(UserInfoInstrctr param
			,String authValJson
			,String content
			) {
		param.setArea(content);
		ResultVO result = memberService.saveMngrInstrctr(param);
		
		//ResultVO result2 = categoryAuthService.saveCategoryAuth(param.getUserId(),authValJson);
		return result;
	}
	
	/**
	 * 강사정보 수정
	 * @param request
	 * @param model
	 * @param userInfoInstrctr
	 * @param userInfo
	 * @param vo
	 * @return
	 */
	@RequestMapping("instrctrUpdProc.do")
	public String instrctrUpdProc(
			MultipartHttpServletRequest request,
			ModelMap model,
			UserInfoInstrctr userInfoInstrctr,
			UserInfo userInfo,
			@ModelAttribute("vo") MemberVO vo) {
		
		String message = "";
		
		vo.setUserInfo(userInfo);
		message = memberService.updateInstrctr(userInfoInstrctr, vo, request);
		
		if(message != null && MemberServiceImpl.EXCUTE_SUCCESS.equals(message)){
			message = "수정되었습니다.";
		}

		model.addAttribute("message", message);
		model.addAttribute("moveUrl", "/admin/member/instrctrView.do?userId=" + vo.getUserId());	
		return "alert";
    }
	
	/**
	 * 강사 이메일 검사(이메일을 아이디로 사용하기 때문에 중복검사)
	 * @param model
	 * @param eamil
	 * @return
	 */
	@RequestMapping("instrctrEmailChk.json")
	public String instrctrEmailChk(
			ModelMap model,
			@RequestParam String email) {
		String message = "";
		
		String regex = VarComponent.REGEX_EMAIL; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(email); 
		
		if(!m.matches()) {
			message = "이메일 형식에 맞게 입력하세요.";
		}else{
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			String orgCd = "";
			if(user!=null){
				orgCd = user.getOrgCd();
			}
			UserInfo userParam = new UserInfo();
			userParam.setOrgCd(orgCd);
			userParam.setEmail(email);
			if(memberService.existEmailByOrg(userParam)){
				message = "사용중인 이메일 입니다.";
			}
		}
		
		model.addAttribute("result", message);
        return "jsonView";
    }
	
	/**
	 * 강사정보 삭제
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("instrctrDelete.do")
	public String instrctrDelete(
			ModelMap model,
			@ModelAttribute("vo") MemberVO vo) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		
		if(isAdmin){
			memberService.deleteInstrctr(vo.getUserId());
		}
		
		jsonObj.addProperty("isAdmin", isAdmin);
		model.addAttribute("obj", jsonObj);

        return "obj";
    }
	
	
	/**
	 * 휴면회원 리스트
	 * @param model
	 * @return
	 */
	@RequestMapping("drmncyList.do")
	public String drmncyList(
			@ModelAttribute("vo") MemberVO vo,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.getDrmncyList(vo);
		
		model.addAttribute("pageNavi", resultMap.get("paginationInfo"));
		model.addAttribute("totalCnt", resultMap.get("totalCnt"));
		model.addAttribute("vo", vo);
		model.addAttribute("dataList", resultMap.get("dataList"));
		
        return "/admin/member/drmncyList" + LncUtil.TILES;
    }
	
	/**
	 * 계정잠금 해제
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("lockReleaseStdnt.json")
	public String lockReleaseStdnt(
			ModelMap model,
			@RequestParam String userId) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		
		if(isAdmin){
			memberService.lockRelease(userId);
		}
		
		jsonObj.addProperty("isAdmin", isAdmin);
		model.addAttribute("obj", jsonObj);

        return "obj";
    }
	
	/**
	 * 교육생 아이디생성
	 * @param model
	 * @param userInfoInstrctr
	 * @param userInfo
	 * @param vo
	 * @return
	 */
	@RequestMapping("stdntRgsProc.do")
	public String stdntRgsProc(
			ModelMap model,
			UserInfoStdnt userInfoStdnt,
			UserInfo userInfo,
			@ModelAttribute("vo") MemberVO vo) {
		
		String message = "";
		
		vo.setUserInfo(userInfo);
		message = memberService.saveStdnt(userInfoStdnt, vo);
		
		if(message != null && MemberServiceImpl.EXCUTE_SUCCESS.equals(message)){
			message = "아이디가 생성되었습니다.";
		}

		model.addAttribute("message", message);
		model.addAttribute("moveUrl", "/admin/member/stdntList.do");	
		return "alert";
    }
	@RequestMapping("stdntRgsProc.json")
	public String stdntRgsProcJson(
			ModelMap model,
			UserInfo userInfo
			) {
		
		int result = memberService.saveStdntAdmin(userInfo);
		model.addAttribute("result", result);
		model.addAttribute("userId",userInfo.getUserId());
		return "jsonView";
    }
	@RequestMapping("stdntAllowProc.json")
	public String stdntAllowProcJson(
			ModelMap model,
			UserInfo userInfo
			) {
		
		ResultVO result = memberService.updateUserAllow(userInfo);
		model.addAttribute("result", result);
		return "jsonView";
	}
	@RequestMapping("userDelete.json")
	public String userDelete(
			ModelMap model,
			UserInfo userInfo
			) {
		
		int result = memberService.deleteUser(userInfo);
		model.addAttribute("result", result);
		return "jsonView";
	}
	
	/**
	 * 아이디 대량등록
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("bulkRgs.json")
	public String bulkRgs(
			ModelMap model,
			MultipartHttpServletRequest request) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		boolean isSuccess = false;
		String message = "";
		
		if(isAdmin){
			message = memberService.saveStdntBulk(request);
			if(message != null && MemberServiceImpl.EXCUTE_SUCCESS.equals(message)){
				isSuccess = true;
			}
		}
		
		jsonObj.addProperty("isAdmin", isAdmin);
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);

        return "obj";
    }
	
	/**
	 * 강사정보 리스트 엑셀다운
	 * @param model
	 * @return
	 */
	@RequestMapping("instrctrListExcel.do")
	public void instrctrListExcel(
			@ModelAttribute("vo") MemberVO vo,
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		String excelFile = memberService.createInstrctrListExcel(vo);
		FileUtil.download(excelFile, "강사정보.xls", request, response);
    }
	
	//학생도 사용가능 한거 같다.
	//일단 학생등록시에도 사용해봄
	@RequestMapping("checkInstrctrId.json")
	public String checkInstrctrId(
			ModelMap model,
			@RequestParam String userId) {
		
		String message = memberService.checkInstrctrId(userId);
		model.addAttribute("result", message);
        return "jsonView";
    }
	@RequestMapping("userPwUpd.json")
	public String orgMngrPwUpd(
			ModelMap model,
			@RequestParam String userId,
			@RequestParam String userPw
			) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		boolean isSuccess = false;
		String message = "";
		
		if(isAdmin){
			message = memberService.mngrPwUpd(userId, userPw);
			if(message != null && MemberServiceImpl.EXCUTE_SUCCESS.equals(message)){
				isSuccess = true;
			}
		}
		
		jsonObj.addProperty("isAdmin", isAdmin);
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);

        return "obj";
    }
	
}	

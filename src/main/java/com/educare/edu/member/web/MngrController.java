package com.educare.edu.member.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.educare.aop.SuperAdmCheck;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.log.service.LogService;
import com.educare.edu.log.service.model.ConnLogAdm;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.MemberVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberServiceImpl;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.menu.service.MenuService;
import com.educare.edu.menu.service.model.MenuAuth;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.google.gson.JsonObject;

/**
 * @Class Name : MngrController.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 4.
 * @version 1.0
 * @see
 * @Description 관리자계정 컨트롤러
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 4.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Controller
@RequestMapping(value="/admin/member/")
public class MngrController {
	
	/** 회원 서비스 */
	@Resource(name = "MemberService")
	private MemberService memberService;
	
	/** 메뉴 서비스 */
	@Resource(name = "MenuService")
	private MenuService menuService;
	
	/** 로그 서비스 */
	@Resource(name = "LogService")
	private LogService logService;
	/** 테이블 서비스 */
	@Resource(name = "TableService")
	private TableService tableService;
	/** table Mapper */
	@Resource(name = "TableMapper")
	private TableMapper tableMapper;
	
	/**
	 * 관리자계정 리스트
	 * @param model
	 * @return
	 */
	@RequestMapping("mngrList.do")
	public String mngrList(
			@ModelAttribute("vo") MemberVO vo,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//if("2".equals(SessionUserInfoHelper.getUserMemLvl())){
			vo.setSrchMemLvl(SessionUserInfoHelper.getUserMemLvl());
		//}
		resultMap = memberService.getMngrList(vo);
		
		model.addAttribute("pageNavi", resultMap.get("paginationInfo"));
		model.addAttribute("totalCnt", resultMap.get("totalCnt"));
		model.addAttribute("vo", vo);
		model.addAttribute("dataList", resultMap.get("dataList"));
		model.addAttribute("curDate", new Date());
		
        return "/admin/member/mngrList" + LncUtil.TILES;
    }
	
	/**
	 * 관리자계정 등록화면
	 * @return
	 */
	@RequestMapping("mngrRgs.do")
	public String mngrRgs(ModelMap model,
			@RequestParam(defaultValue="0") String userId
			) {
		
		UserInfo user = memberService.getMngrDetail(userId);
		if(user!=null){
			OrgVO org = tableService.getOrgInfo(user.getOrgCd());
			model.addAttribute("org",org);
		}
		List<MenuAuth> menuAuthList = menuService.getMenuAuthListByUserId(userId);
		List<ConnLogAdm> connLogList =	logService.getAdminConnList(userId);
		
		model.addAttribute("user",user);
		model.addAttribute("menuAuthList",menuAuthList);
		model.addAttribute("connLogList",connLogList);
        return "/admin/member/mngrRgs" + LncUtil.TILES;
    }
	
	/**
	 * 관리자계정 등록
	 * @param model
	 * @param userInfo
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("mngrRgsProc.ajax")
	public ResultVO mngrRgsProc(ModelMap model
			,UserInfo userParam
			,String[] menuIds
			) {
		ResultVO result = new ResultVO();
		
		if(!SessionUserInfoHelper.isAdmin()){
			result.setResult(0);
			result.setMsg("권한이 없습니다.");
			return result;
		}
		result = memberService.saveMngr(userParam,menuIds,null);
		
        return result;
    }
	
	@RequestMapping("mngrFileProc.json")
	public String mngrFileProc(
			ModelMap model,
			MultipartRequest mr,
			String loginId,
			OrgVO orgParam
			) {
		
		boolean isSuperAdmin = SessionUserInfoHelper.isSuperAdmin();
		String message = "권한이 없습니다.";
		
		if(isSuperAdmin){
			message = memberService.saveMngrFile(loginId,mr,orgParam);
		}
		
		model.addAttribute("message",message);			
        return "jsonView";
    }
	
	
	/**
	 * 관리자계정 수정화면
	 * @param model
	 * @return
	 */
	@RequestMapping("mngrUpd.do")
	public String mngrUpd(
			@ModelAttribute("vo") MemberVO vo,
			ModelMap model) {
		
		vo.setUserInfo(memberService.getMngrDetail(vo.getUserId()));
		vo.setMenuAuthList(menuService.getMenuAuthListByUserId(vo.getUserId()));
		vo.setConnLogList(logService.getAdminConnList(vo.getUserId()));
		
		OrgVO orgInfo = tableService.getOrgInfo(vo.getUserInfo().getOrgCd());
		
		model.addAttribute("vo", vo);
		model.addAttribute("org",orgInfo);
        return "/admin/member/mngrUpd" + LncUtil.TILES;
    }
	
	/**
	 * 관리자 패스워드 변경
	 * @param model
	 * @param userId
	 * @param userPw
	 * @return
	 */
	@RequestMapping("mngrPwUpd.json")
	public String mngrPwUpd(
			ModelMap model,
			@RequestParam String userId,
			@RequestParam String userPw) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuperAdmin = SessionUserInfoHelper.isSuperAdmin();
		boolean isSuccess = false;
		String message = "";
		
		if(isSuperAdmin){
			message = memberService.mngrPwUpd(userId, userPw);
			if(message != null && MemberServiceImpl.EXCUTE_SUCCESS.equals(message)){
				isSuccess = true;
			}
		}
		
		jsonObj.addProperty("isSuperAdmin", isSuperAdmin);
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);

        return "obj";
    }
	
	/**
	 * 관리자 계정 삭제
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("mngrDelete.json")
	public String mngrDelete(
			ModelMap model,
			@RequestParam String userId) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuperAdmin = SessionUserInfoHelper.isSuperAdmin();
		boolean isSuccess = false;
		String message = "";
		
		if(isSuperAdmin){
			message = memberService.mngrDelete(userId);
			if(message != null && MemberServiceImpl.EXCUTE_SUCCESS.equals(message)){
				isSuccess = true;
			}
		}
		
		jsonObj.addProperty("isSuperAdmin", isSuperAdmin);
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);

        return "obj";
    }
	
	/**
	 * 계정잠금 해제
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("lockRelease.json")
	public String lockRelease(
			ModelMap model,
			@RequestParam String userId) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuperAdmin = SessionUserInfoHelper.isSuperAdmin();
		
		if(isSuperAdmin){
			memberService.lockRelease(userId);
		}
		
		jsonObj.addProperty("isSuperAdmin", isSuperAdmin);
		model.addAttribute("obj", jsonObj);

        return "obj";
    }	
	
	/**
	 * 관리자계정 수정
	 * @param model
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("mngrUpdProc.json")
	public String mngrUpdProc(
			ModelMap model,
			MultipartRequest mr,
			String userId,String loginId,String userNm,String tel,String orgNm,String orgEn,boolean jiginDel,String[] menuIds,String orgCd,String userMemLvl) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuperAdmin = SessionUserInfoHelper.isSuperAdmin();
		boolean isSuccess = false;
		String message = "";
		
		//한글처리
		String userNmp = LncUtil.getEncode(userNm);
		String orgNmp = LncUtil.getEncode(orgNm);
		String orgEnp = LncUtil.getEncode(orgEn);
		String jiginFileNm = "";
		try {
			//직인업로드
			MultipartFile mf = mr.getFile("jigin");
			
			if(mf!=null){
				//리사이징
				String resizePath = FileUtil.resizeImageFile(mf, 50, 50);
				
				String fileOrg = "";
				if(resizePath!=null){
					String folderName = "upload/jigin/";
					fileOrg = new String(mf.getOriginalFilename().getBytes("8859_1"),"utf-8");
					if(fileOrg == null){
						return "오류가 발생하였습니다.";
					}
					String ext = fileOrg.substring(fileOrg.lastIndexOf(".") + 1);
					String fileRename = loginId+"."+ext;
					//NaverObjectStorage.upload(resizePath, folderName+fileRename);
					FileUtil.move2Path(resizePath,folderName,fileRename);
					jiginFileNm=fileRename;
				}
			}
		} catch (IOException e) {
			jiginFileNm = "";
		}
		
		if(isSuperAdmin){
			message = memberService.updMngr(userId,userNmp,tel,orgNmp,orgEnp,jiginDel,jiginFileNm,menuIds,orgCd,userMemLvl);
			if(message != null && MemberServiceImpl.EXCUTE_SUCCESS.equals(message)){
				isSuccess = true;
			}
		}
		
		jsonObj.addProperty("isSuperAdmin", isSuperAdmin);
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	
	/**
	 * 기관코드 중복 체크
	 */
	@RequestMapping("checkOrgCd.json")
	public String checkOrgCdAjax(ModelMap model,
			@RequestParam String orgCd
			){
		String result = memberService.checkOrgCd(orgCd);
		model.addAttribute("result",result);
		return "jsonView";
	}
	/**
	 * 기관코드 중복 체크
	 */
	@RequestMapping("checkOrgNm.json")
	public String checkOrgNmAjax(ModelMap model,
			@RequestParam String orgCd,
			@RequestParam String orgNm
			){
		String result = memberService.checkOrgNm(orgCd,orgNm);
		model.addAttribute("result",result);
		return "jsonView";
	}
	

	/**
	 * 기관 관리자계정 수정화면,기관 관리자로그인 전용
	 * @param model
	 * @return
	 */
	@RequestMapping("orgMngrUpd.do")
	public String orgMngrUpd(
			@ModelAttribute("vo") MemberVO vo,
			ModelMap model) {
		String userId = SessionUserInfoHelper.getUserId();
		vo.setUserInfo(memberService.getMngrDetail(userId));
		OrgVO orgInfo = tableService.getOrgInfo(vo.getUserInfo().getOrgCd());
		
		model.addAttribute("vo", vo);
		model.addAttribute("org",orgInfo);
        return "/admin/member/orgMngrUpd" + LncUtil.TILES;
    }
	/**
	 * 관리자계정 수정
	 * @param model
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("orgMngrUpdProc.json")
	public String orgMngrUpdProc(
			ModelMap model,
			MultipartRequest mr,
			String userNm
			,String tel
			,String orgNm
			,String orgEn
			,boolean jiginDel) {
		
		//한글처리
		String userNmp = LncUtil.getEncode(userNm);
		String orgNmp = LncUtil.getEncode(orgNm);
		String orgEnp = LncUtil.getEncode(orgEn);
		
		JsonObject jsonObj = new JsonObject();
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		boolean isSuccess = false;
		String message = "";
		String jiginFileNm = "";
		try {
			//직인업로드
			MultipartFile mf = mr.getFile("jigin");
			
			//파일리사이징
			String resizePath = FileUtil.resizeImageFile(mf, 10, 10);
			
			String fileOrg = "";
			if(resizePath!=null){
				String folderName = "upload/jigin/";
				fileOrg = new String(mf.getOriginalFilename().getBytes("8859_1"),"utf-8");
				if(fileOrg == null){
					return "오류가 발생하였습니다.";
				}
				String ext = fileOrg.substring(fileOrg.lastIndexOf(".") + 1);
				String fileRename = SessionUserInfoHelper.getUserId()+"."+ext;
				//NaverObjectStorage.upload(resizePath, folderName+fileRename);
				FileUtil.move2Path(resizePath,folderName,fileRename);
				jiginFileNm=fileRename;
			}
		} catch (NullPointerException | UnsupportedEncodingException e) {
			jiginFileNm = "";
		}
		
		isSuccess = true;
		
		if(isAdmin){
			message = memberService.updOrgMngr(userNmp,tel,jiginFileNm,orgNmp,orgEnp,jiginDel);
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
	 * 관리자 패스워드 변경
	 * @param model
	 * @param userId
	 * @param userPw
	 * @return
	 */
	@RequestMapping("orgMngrPwUpd.json")
	public String orgMngrPwUpd(
			ModelMap model,
			@RequestParam String userPw) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		boolean isSuccess = false;
		String message = "";
		
		if(isAdmin){
			String userId = SessionUserInfoHelper.getUserId();
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

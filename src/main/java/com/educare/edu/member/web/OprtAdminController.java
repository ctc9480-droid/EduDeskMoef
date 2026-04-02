package com.educare.edu.member.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.stringtemplate.v4.compiler.CodeGenerator.mapTemplateRef_return;

import com.educare.aop.SuperAdmCheck;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.CategoryAuth;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.CategoryAuthVO;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.CategoryAuthService;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.impl.CategoryMapper;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.log.service.LogService;
import com.educare.edu.log.service.model.ConnLogAdm;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.MemberVO;
import com.educare.edu.member.service.OprtAdminService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberServiceImpl;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.menu.service.MenuService;
import com.educare.edu.menu.service.model.MenuAuth;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/admin/member/")
public class OprtAdminController {
	
	/** 운영자 서비스 */
	@Resource(name = "OprtAdminService")
	private OprtAdminService oprtAdminService;
	/** 운영자 서비스 */
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
	@Resource(name = "CategoryService")
	private CategoryService categoryService;
	@Resource(name = "CategoryAuthService")
	private CategoryAuthService categoryAuthService;
	@Resource(name = "CategoryMapper")
	private CategoryMapper categoryMapper;
	
	/**
	 * 관리자계정 리스트
	 * @param model
	 * @return
	 */
	@RequestMapping("oprtList.do")
	public String oprtList(
			@ModelAttribute("vo") MemberVO vo,
			ModelMap model) {
		
		ResultVO result = oprtAdminService.getOprtBbsList(vo);
		Map<String, Object> rstData = (Map<String, Object>) result.getData();
		model.addAttribute("pageNavi",rstData.get("page"));
		model.addAttribute("totalCnt", rstData.get("totalCnt"));
		model.addAttribute("dataList", rstData.get("dataList"));
		model.addAttribute("vo", vo);
        return "/admin/member/oprtList" + LncUtil.TILES;
    }
	
	/**
	 * 관리자계정 등록화면
	 * @return
	 */
	@RequestMapping("oprtRgs.do")
	public String oprtRgs(ModelMap model,
			@RequestParam(defaultValue="0") String userId
			) {
		
		UserInfo user = oprtAdminService.getOprt(userId);
		if(user!=null){
			if(user!=null){
				OrgVO org = tableService.getOrgInfo(user.getOrgCd());
				model.addAttribute("org",org);
			}
		}
		List<MenuAuth> menuAuthList = menuService.getMenuAuthListByUserId(userId);
		List<ConnLogAdm> connLogList =	logService.getAdminConnList(userId);
		
		List<CategoryVO> cateList = categoryService.getCategoryList(1);
		model.addAttribute("cateList", cateList);
		
		model.addAttribute("user",user);
		model.addAttribute("menuAuthList",menuAuthList);
		model.addAttribute("connLogList",connLogList);
		model.addAttribute("userId",userId);
        return "/admin/member/oprtRgs" + LncUtil.TILES;
    }
	
	@ResponseBody
	@RequestMapping("callAuthList.ajax")
	public ResultVO callAuthCategory(HttpServletRequest req
			,@RequestParam String userId
			){
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		//
		CategoryAuthVO vo = new CategoryAuthVO();
		vo.setUserId(userId);
		List<CategoryAuthVO> authList = categoryAuthService.getCategoryAuthList(vo);
		if(ObjectUtils.isEmpty(authList)){
			authList = new ArrayList<CategoryAuthVO>();
			authList.add(new CategoryAuthVO());
		}
		
		//전체 카테고리 조회
		List<CategoryVO> cateList = categoryMapper.selectCategoryAllList();
		
		rstData.put("authList", authList);
		rstData.put("cateList", cateList);
		
		return result;
	}
	
	/**
	 * 관리자계정 등록
	 * @param model
	 * @param userInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("oprtRgsProc.ajax")
	public ResultVO oprtRgsProc(ModelMap model
			,UserInfo userParam
			,Org orgParam
			,String[] menuIds
			,String[] authCtgrySeq
			) {
		
		ResultVO result = new ResultVO();
		
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		if(!isAdmin){
			result.setMsg("권한이 없습니다.");
			result.setResult(0);
			return result;
		}
		
		result = memberService.saveMngr(userParam,menuIds,authCtgrySeq);
		
        return result;
    }
	
	/**
	 * 관리자 계정 삭제
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("oprtDelete.ajax")
	public String oprtDelete(
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

	
}

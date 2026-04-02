package com.educare.edu.education.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.educare.edu.comn.mapper.MovMapper;
import com.educare.edu.comn.mapper.QrCodeMapper;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.QrCode;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.MovVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.AttachService;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.LctreRceptService;
import com.educare.edu.education.service.LctreService;
import com.educare.edu.education.service.impl.AttachMapper;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.quizCate.service.QuizCateService;
import com.educare.edu.quizCate.vo.QstnCategoryVO;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 */
@Controller
@RequestMapping(value="/admin/edu/")
public class EduAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EduAdminController.class);
	
	/** 카테고리 서비스 */
	@Resource(name = "CategoryService")
	private CategoryService categoryService;
	
	/** 교육관리 서비스 */
	@Resource(name = "EduService")
	private EduService eduService;
	
	/** 회원 서비스 */
	@Resource(name = "MemberService")
	private MemberService memberService;
	
	/** 첨부파일 서비스 */
	@Resource(name = "AttachService")
	private AttachService attachService;
	
	/** 교육신청 서비스 */
	@Resource(name = "LctreRceptService")
	private LctreRceptService lctreRceptService;
	
	/** 교육 서비스 */
	@Resource(name = "LctreService")
	private LctreService lctreService;
	
	/** 체크 서비스 */
	@Resource(name = "CheckService")
	private CheckService checkService;
	
	@Resource(name = "MovMapper")
	private MovMapper movMapper;
	@Resource(name = "EduMapper")
	private EduMapper eduMapper;
	@Resource(name = "AttachMapper")
	private AttachMapper attachMapper;
	
	@Resource(name = "TableService")
	private TableService tableService;
	@Resource(name = "QrCodeMapper")
	private QrCodeMapper qrCodeMapper;
	@Resource(name = "SmartcheckService")
	private SmartcheckService smartcheckService;
	@Resource(name = "QuizCateService")
	private QuizCateService quizCateService; 
	
	/**
	 * JsonArray를 response로 write
	 * @param jsonArr com.google.gson.JsonArray
	 * @param response javax.servlet.http.HttpServletResponse
	 */
	private void setJsonArrayToResponse(JsonArray jsonArr, HttpServletResponse response) {
		if(jsonArr != null && jsonArr.size() > 0) {
			response.setContentType( "application/json" );
			response.setHeader( "Content-Type","text/json" );
			response.setCharacterEncoding( "UTF-8" );
			PrintWriter writer = null;
			
			try {
				writer = response.getWriter();
				writer.write( jsonArr.toString() );
			} catch (IOException e) {
				LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
			} 
		}
	}
	
	/**
	 * 수업관리 리스트
	 * @param model
	 * @return
	 */
	@RequestMapping("lctreList.do")
	public String lctreList(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		
		//카테고리 
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		List<CategoryVO> cateList2 = categoryService.getCategoryChildList(vo.getSrchCtgry());
		List<CategoryVO> cateList3 = categoryService.getCategoryChildList(vo.getSrchCtgry2());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = eduService.getLctreListMngr(vo);
		
		List<Org> orgList = tableService.getOrgList();
		
		model.addAttribute("cateList", cateList);
		model.addAttribute("cateList2", cateList2);
		model.addAttribute("cateList3", cateList3);
		model.addAttribute("pageNavi", resultMap.get("paginationInfo"));
		model.addAttribute("totalCnt", resultMap.get("totalCnt"));
		model.addAttribute("vo", vo);
		model.addAttribute("dataList", resultMap.get("dataList"));
		model.addAttribute("orgList",orgList);
		
        return "/admin/edu/lctreList" + LncUtil.TILES;
    }
	
	/**
	 * 선택한 교육과정에 해당하는 강사 목록을 반환한다.
	 * @param model
	 * @param ctgrySeq
	 * @return
	 */
	@RequestMapping("getInstrctrList.json")
	public void getInstrctrList(
			ModelMap model,
			HttpServletResponse response,
			@RequestParam Integer ctgrySeq) {
		
		List<UserInfo> list = memberService.getInstrctrListByCategory(ctgrySeq);
		JsonArray jsonArr = eduService.convertDataListToJsonArray(list);
		
		setJsonArrayToResponse(jsonArr, response);
    }
	/**
	 * 선택한 교육과정에 해당하는  자식 카테고리를 가져온다
	 * @param model
	 * @param ctgrySeq
	 * @return
	 */
	@RequestMapping("getCategoryChildList.json")
	public void getCategoryChildList(
			ModelMap model,
			HttpServletResponse response,
			@RequestParam(defaultValue="0") int parentSeq) {
		
		List<CategoryVO> detailList = categoryService.getCategoryChildList(parentSeq);
		JsonArray jsonArr = eduService.convertDataListToJsonArray(detailList);
		
		setJsonArrayToResponse(jsonArr, response);
    }
	/**
	 * 교육과정 등록
	 * @param model
	 * @return
	 */
	@ResponseBody
	@Transactional
	@RequestMapping("lctreRgsProc.ajax")
	public ResultVO lctreRgsProc(
			ModelMap model,
			Lecture lecture,
			@RequestParam(defaultValue = "0") int copyEduSeq
			) {

		ResultVO result = new ResultVO();
		result = eduService.saveLctre(lecture);
		
		//카피일경우 시간표 복사
		if(copyEduSeq != 0) {
			//시간표 복사하는 서비스
			ResultVO result2 = eduService.saveLctreTime(lecture.getEduSeq(),copyEduSeq);
			if(result2.getResult()!=1){
				result.setMsg("시간표복사에 실패하였습니다.");
			}
		}
		
	
		return result;
    }
	
	@ResponseBody
	@RequestMapping("lctreRgsFileThumProc.ajax") 
	public ResultVO lctreRgsFileProc(
			ModelMap model,
			MultipartHttpServletRequest request,
			int eduSeq
			) {
		
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			MultipartFile mf = request.getFile("imgFile");
			if(mf==null){
				result.setResult(1);
				return result;
			}
			String orgNm = LncUtil.getEncode(mf.getOriginalFilename());
			if(orgNm != null && !"".equals(orgNm)){
				String fileType = orgNm.substring(orgNm.lastIndexOf(".") + 1, orgNm.length());
				String fileRename = eduSeq+"_thum." + fileType;
				
				ResultVO result2 = FileUtil.multiPartupload(mf, "upload/web/lctreThum/", orgNm, fileRename);
				Lecture lecture = new Lecture();
				lecture.setEduSeq(eduSeq);
				lecture.setImgOrg(orgNm);
				lecture.setImgRename(fileRename);
				eduMapper.updateLctreThum(lecture);
			}
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	@ResponseBody
	@RequestMapping(value="lctreDropzoneProc.ajax")
	public ResultVO lctreDropzoneProc( ModelMap model,MultipartHttpServletRequest req
			) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			int eduSeq = LncUtil.nvlInt(req.getParameter("eduSeq"));
			
			Iterator<String> itr = req.getFileNames();
			while(itr.hasNext()) {
				String fileName = itr.next();
				MultipartFile multipartFile = req.getFile(fileName);
				String orgNm = LncUtil.getEncode(multipartFile.getOriginalFilename());
				if(orgNm != null && !"".equals(orgNm)){
					String fileType = orgNm.substring(orgNm.lastIndexOf(".") + 1, orgNm.length());
					String fileRename = FileUtil.getFileRename(orgNm, eduSeq+"_attach");
					ResultVO result2 = FileUtil.multiPartupload(multipartFile, "upload/lctre/"+eduSeq+"/", orgNm, fileRename);
					Lecture lecture = new Lecture();
					lecture.setEduSeq(eduSeq);
					lecture.setImgOrg(orgNm);
					lecture.setImgRename(fileRename);
					
					String fileSection = "01";
					int fileSize=(int) multipartFile.getSize();
					LectureAttach attach = new LectureAttach(
							eduSeq, 
							fileSection, 
							orgNm, 
							fileRename, 
							fileType, 
							fileSize
						);
					attachMapper.inertAttach(attach);
				}
			}
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	@ResponseBody
	@RequestMapping(value="popup/deleteDropzoneFileLctre.ajax")
	public ResultVO deleteDropzoneFileTmlct(ModelMap model
			,@RequestParam(defaultValue="0") int fileSeq
			,@RequestParam(defaultValue="0") int eduSeq
			) {
		ResultVO result = new ResultVO();
		try {
			//파일정보가져오기
			LectureAttach vo = attachMapper.getAttach(fileSeq);
			String fileRename = vo.getFileRename();
			String folderName = "upload/lctre/"+eduSeq+"/";
			
			//스토리지에서 삭제
			//NaverObjectStorage.delete(folderName, fileRename);
			FileUtil.delete(XmlBean.getServerDataRoot()+folderName+fileRename);
			
			//테이블에서 삭제
			attachMapper.deleteAttach(fileSeq);
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	/**
	 * 수업관리 상세보기
	 * @param model
	 * @return
	 */
	@RequestMapping("lctreView.do")
	public String lctreView(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		
		model.addAttribute("lctre", eduService.getLctreDetail(vo.getEduSeq()));
		model.addAttribute("attachList01", attachService.getAttachList(vo.getEduSeq(),"01"));
		model.addAttribute("attachList02", attachService.getAttachList(vo.getEduSeq(),"02"));
		
		
		
        return "/admin/edu/lctreView" + LncUtil.TILES;
    }
	@RequestMapping("popup/lctreView.do")
	public String popuplctreView(ModelMap model
			,@ModelAttribute("vo") EduVO vo
			,@RequestParam(defaultValue="5") String tab
			) {
		int eduSeq = vo.getEduSeq();
		
		Lecture lctre = eduService.getLctreDetail(vo.getEduSeq());
		model.addAttribute("lctre",lctre );
		
		model.addAttribute("attachList01", attachService.getAttachList(vo.getEduSeq(),"01"));
		model.addAttribute("attachList02", attachService.getAttachList(vo.getEduSeq(),"02"));
		
		//출결QR여부
		QrCode param = new QrCode();
		param.setEduSeq(vo.getEduSeq());
		param.setQrType("attendBegin");
		QrCode qrAttendBegin = qrCodeMapper.selectByAttend(param);
		if(qrAttendBegin != null){
			model.addAttribute("qrAttendBegin",qrAttendBegin);
		}
		param.setQrType("attendEnd");
		QrCode qrAttendEnd = qrCodeMapper.selectByAttend(param);
		if(qrAttendEnd != null){
			model.addAttribute("qrAttendEnd",qrAttendEnd);
		}
		
		//출석률 가져오기
		double attRatio = 0;
		if(lctre.getLctreType() == 0){
			attRatio = smartcheckService.getAttendRatio(eduSeq,1);
		}else{
			attRatio = smartcheckService.getAttendRatio(eduSeq,0);
		}
		model.addAttribute("attRatio",attRatio);
		
		//퀴즈 카테고리 조회
		List<QstnCategoryVO> cateList = quizCateService.getCategoryListAdmin(QstnCategoryVO.DEPTH_EDU);
		model.addAttribute("cateList",cateList);
		
		return "/admin/edu/popup/lctreView" + LncUtil.TILES;
	}
	
	/**
	 * 수업 복사등록 페이지
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("popup/lctreCopy.do")
	public String lctreCopy(
			ModelMap model,
			@RequestParam int copyEduSeq) {
		
		Lecture lctre = eduService.getLctreDetail(copyEduSeq);
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		
		if(lctre!=null){
			//2차 주고  
			List<CategoryVO> cateList2 = categoryService.getCategoryChildList(lctre.getCtg1Seq());
			model.addAttribute("cateList2", cateList2);	
			//3차 주고  
			List<CategoryVO> cateList3 = categoryService.getCategoryChildList(lctre.getCtg2Seq());
			model.addAttribute("cateList3", cateList3);	
		}
		
		model.addAttribute("lctre", lctre);
		model.addAttribute("cateList", cateList);
		//model.addAttribute("attachList01", attachService.getAttachList(copyEduSeq,"01"));//파일복사는 임시 주석 처리,230406
			
        return "/admin/edu/popup/lctreRgs" + LncUtil.TILES;
    }
	
	/**
	 * 수업수정 페이지
	 * @param model
	 * @param vo
	 * @return
	 */ 
	@RequestMapping("lctreRgs.do")
	public String lctreUpd(
			ModelMap model,
			@RequestParam(defaultValue="0") int eduSeq 
			) {
		
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		
		model.addAttribute("lctre", lctre);
		model.addAttribute("cateList", cateList);
		model.addAttribute("attachList01", attachService.getAttachList(eduSeq,"01"));
		
        return "/admin/edu/lctreRgs" + LncUtil.TILES;
    }
	@RequestMapping("popup/lctreRgs.do")
	public String popupLctreRgs(
			ModelMap model,
			@RequestParam(defaultValue="0") int eduSeq 
			) {
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		model.addAttribute("cateList", cateList);
		
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		if(lctre==null){
			lctre = new Lecture();
		}else{
			List<CategoryVO> cateList2 = categoryService.getCategoryChildList(lctre.getCtg1Seq());
			model.addAttribute("cateList2", cateList2);
			List<CategoryVO> cateList3 = categoryService.getCategoryChildList(lctre.getCtg2Seq());
			model.addAttribute("cateList3", cateList3);
		}
		model.addAttribute("lctre", lctre);
		
		
		model.addAttribute("attachList01", attachService.getAttachList(eduSeq,"01"));
		return "/admin/edu/popup/lctreRgs" + LncUtil.TILES;
	}
	/**
	 * 단체수강 등록 처리
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("bulkUserRgs.json")
	public String bulkUserRgs(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo
			) {
		
		JsonObject jsonObj = new JsonObject();
		
		boolean isSuccess = false;
		String message = "";
		
		message = eduService.bulkUserRgs(vo);
		if(message != null && EduService.EXCUTE_SUCCESS.equals(message)){
			isSuccess = true;
		}
		
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	
	/**
	 * 승인마감 처리
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("rceptClose.json")
	public String rceptClose(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo
			) {
		
		JsonObject jsonObj = new JsonObject();
		
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		
		String rtnMsg = "";
		if(isAdmin){
			rtnMsg = eduService.rceptClose(vo);
		}
		
		jsonObj.addProperty("isAdmin", isAdmin);
		jsonObj.addProperty("rtnMsg", rtnMsg);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	@ResponseBody
	@RequestMapping("rceptCloseOnly.ajax")
	public ResultVO rceptCloseOnly(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo
			) {
		ResultVO result=eduService.rceptCloseOnly(vo);
		return result;
	}
	@ResponseBody
	@RequestMapping("rceptOpenOnly.ajax")
	public ResultVO rceptOpenOnly(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo
			) {
		ResultVO result=eduService.rceptOpenOnly(vo);
		return result;
	}
	
	/**
	 * 수강취소 처리
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("eduStdntCancel.json")
	public String eduStdntCancel(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo
			) {
		
		JsonObject jsonObj = new JsonObject();
		
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		
		if(isAdmin){
			eduService.eduStdntCancel(vo);
		}
		
		jsonObj.addProperty("isAdmin", isAdmin);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	
	/**
	 * 수강생 성적 수정
	 * @param model
	 * @param stdnt
	 * @return
	 */
	@RequestMapping("updScore.json")
	public String updScore(
			ModelMap model,
			LectureStdnt stdnt
			) {
		
		JsonObject jsonObj = new JsonObject();
		
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		
		if(isAdmin){
			eduService.updScore(stdnt);
		}
		
		jsonObj.addProperty("isAdmin", isAdmin);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	
	/**
	 * 교육종료
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("eduClose.json")
	public String eduClose(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo
			) {
		
		JsonObject jsonObj = new JsonObject();
		
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		String result = "";
		
		if(isAdmin){
			result = eduService.eduClose(vo);
		}
		
		jsonObj.addProperty("result", result);
		jsonObj.addProperty("isAdmin", isAdmin);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
	}
	
	/**
	 * 교육종료 취소
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("eduCloseCancel.json")
	public String eduCloseCancel(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo
			) {
		
		JsonObject jsonObj = new JsonObject();
		
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		String result = "";
		
		if(isAdmin){
			result = eduService.eduCloseCancel(vo);
		}
		
		jsonObj.addProperty("result", result);
		jsonObj.addProperty("isAdmin", isAdmin);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
	}
	
	/**
	 * 증빙서류 출력
	 * @param model
	 * @return
	 */
	@RequestMapping("certView.do")
	public String certView(ModelMap model
			,int eduSeq
			,int subSeq
			,String userId
			) {
		
		Map<String, Object> certMap = eduService.getCertInfo(eduSeq,subSeq,userId);
		model.addAttribute("certMap", certMap);
		
        
		//수료증 템플릿 가져오기
      	Lecture lecture = (Lecture) certMap.get("lctre");
      	int passIdx=lecture.getPassIdx();
      	
      	
      	return "/cert/certView"+passIdx;
        
    }
	
	/**
	 * 교육 결과자료 업로드 ajax 호출
	 * @param model
	 * @param org.springframework.web.multipart.MultipartHttpServletRequest request
	 * @param totalCnt : 첨부된 파일 수
	 * @param maxCnt : 첨부파일 수 제한(없으면 0)
	 * @param totalSize : 첨부된 파일 용량  
	 * @param maxSize : 첨부파일 용량 제한(바이트)
	 * @return
	 */
	@RequestMapping("upload.json")
	public String upload(
			ModelMap model,
			MultipartHttpServletRequest request,
			Integer eduSeq,
			String fileSection,
			String totalCnt,
			String maxCnt,
			String totalSize,
			String maxSize) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuccess = false;
		String message = "파일 업로드 중 오류가 발생하였습니다.";
		
		LectureAttach file = attachService.uploadFile(request, eduSeq, fileSection);
		if(file != null && file.getFileSeq() != null){
			isSuccess = true;
		}
		
		jsonObj.addProperty("fileSeq", file.getFileSeq());
		jsonObj.addProperty("fileRename", file.getFileRename());
		jsonObj.addProperty("fileOrg", file.getFileOrg());
		jsonObj.addProperty("fileType", file.getFileType());
		jsonObj.addProperty("fileSize", file.getFileSize());
		jsonObj.addProperty("fileSection", file.getFileSection());
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	
	/**
	 * 파일삭제
	 * @param model
	 * @param tmpfileSeq
	 * @return
	 */
	@RequestMapping("attachDelete.json")
	public String attachDelete(
			ModelMap model, Integer fileSeq){
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuccess = false;
		
		LectureAttach file = attachService.getAttach(fileSeq);
		
		if(file != null && file.getFileSeq() != null){
			jsonObj.addProperty("fileSize", file.getFileSize());
			attachService.deleteAttach(fileSeq);
			isSuccess = true;
		}
		
		jsonObj.addProperty("message", "파일삭제 오류");
		jsonObj.addProperty("isSuccess", isSuccess);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
	}
	
	
	/**
	 * 수강생목록 엑셀 다운로드
	 * @param model
	 * @return
	 */
	@RequestMapping("stdntExcel.do")
	public void stdntExcel(
			@ModelAttribute("vo") EduVO vo,
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		String excelFile = eduService.createStdntExcel(vo);
		FileUtil.download(excelFile, "수강생 목록.xls", request, response);
    }
	/**
	 * 수강생목록 엑셀 다운로드2
	 * 온라인,동영상 접속 여부
	 * @param model
	 * @return
	 */
	@RequestMapping("stdntExcel2.do")
	public void stdntExcel2(
			@ModelAttribute("vo") EduVO vo,
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		String excelFile = eduService.createStdntExcel(vo);
		FileUtil.download(excelFile, "수강생 목록.xls", request, response);
    }

	//동영상관리 리스트
	@RequestMapping("movList.do")
	public String movList(ModelMap model
			,MovVO param
			) {
		
		//int check = checkService.checkOrgUseMov(orgCd);
		//if(check==0){
		//	return "/admin/common/noUse" + LncUtil.TILES;
		//}
		
		//모든관리자 계정에서동영상 조회 되도록 수정,231011
		//if(!SessionUserInfoHelper.getUserMemLvl().equals("1")){
			//param.setRegId(SessionUserInfoHelper.getUserInfo().getUserId());
		//}
		//if(SessionUserInfoHelper.getUserMemLvl().equals("2")) {
			//param.setRegId(null);
		//}
		
		//param.setOrgCd(orgCd);
		
		int totalCnt = movMapper.getMovTotalCnt(param);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
		paginationInfo.setCurrentPageNo(param.getPageNo());
		paginationInfo.setRecordCountPerPage(5);
		paginationInfo.setPageSize(10);
		
		param.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
		param.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<MovVO> list = movMapper.getMovList(param);
		
		model.addAttribute("list",list);
		model.addAttribute("pageNavi",paginationInfo);
		return "/admin/edu/movList" + LncUtil.TILES;
	}
	//동영상관리 리스트
	@RequestMapping("movRgs.do")
	public String movReg(ModelMap model
			,@RequestParam(defaultValue="0") int idx
			) {
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		if(user == null){
			return null;
		}
		
		MovVO param = new MovVO();
		param.setOrgCd(user.getOrgCd());
		param.setIdx(idx);
		MovVO info = movMapper.getMovInfo(param);
		
		model.addAttribute("info",info);
		return "/admin/edu/movRgs" + LncUtil.TILES;
	}
	//웹컨텐츠관리 리스트
	@RequestMapping("webConList.do")
	public String webConList(ModelMap model
			,MovVO param
			) {
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		String orgCd = "";
		if(user!=null){
			orgCd = user.getOrgCd();
		}
		
		param.setOrgCd(orgCd);
		
		int totalCnt = movMapper.getMovTotalCnt(param);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
		paginationInfo.setCurrentPageNo(param.getPageNo());
		paginationInfo.setRecordCountPerPage(5);
		paginationInfo.setPageSize(10);
		
		param.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
		param.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<MovVO> list = movMapper.getMovList(param);
		
		model.addAttribute("list",list);
		model.addAttribute("pageNavi",paginationInfo);
		return "/admin/edu/webConList" + LncUtil.TILES;
	}
	//웹컨텐츠관리 리스트
	@RequestMapping("webConRgs.do")
	public String webConReg(ModelMap model
			,@RequestParam(defaultValue="0") int idx
			) {
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		if(user == null){
			return null;
		}
		
		MovVO param = new MovVO();
		param.setOrgCd(user.getOrgCd());
		param.setIdx(idx);
		MovVO info = movMapper.getMovInfo(param);
		
		model.addAttribute("info",info);
		return "/admin/edu/webConRgs" + LncUtil.TILES;
	}
	@ResponseBody
	@RequestMapping("delRceptUser.ajax")
	public ResultVO delRceptUser(ModelMap model
			, int eduSeq
			, String userId
			, int rceptSeq
			) {
		
		ResultVO result = eduService.delRceptUser(eduSeq,userId,rceptSeq);
		
		return result;
	}
	@ResponseBody
	@RequestMapping("updRceptNew.ajax")
	public ResultVO updRceptNew(ModelMap model
			, int rceptSeq
			) {
		
		ResultVO result = eduService.updRceptNew(rceptSeq);
		
		return result;
	}
	/**
	 * 신청서 조회
	 * @param model
	 * @param vo
	 */
	@ResponseBody
	@RequestMapping("lectureRceptList.ajax")
	public ResultVO lectureRceptList(ModelMap model
			,int eduSeq
			,@RequestParam(defaultValue="1") Integer pageNo
			,@RequestParam(defaultValue="0") Integer row
			,@RequestParam(defaultValue="0") Integer srchRceptState
			,String srchWrd
			) {
		
		ResultVO result = eduService.getLectureRceptList(row,pageNo,eduSeq,srchRceptState,srchWrd);
		
		return result;
    }
	
}

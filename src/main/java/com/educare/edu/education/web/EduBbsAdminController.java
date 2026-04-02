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

import org.antlr.grammar.v3.ANTLRParser.option_return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.BbsConstant;
import com.educare.edu.bbs.service.model.BoardAttach;
import com.educare.edu.comn.mapper.MovMapper;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.CheckService;
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
import com.educare.edu.lectureBoard.service.LectureBoardCommentService;
import com.educare.edu.lectureBoard.service.LectureBoardService;
import com.educare.edu.lectureBoard.vo.LectureBoardAttach;
import com.educare.edu.lectureBoard.vo.LectureBoardCommentVO;
import com.educare.edu.lectureBoard.vo.LectureBoardVO;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.tmpFile.service.TempFileService;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 */
@Controller
@RequestMapping(value="/admin/edu/bbs")
public class EduBbsAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EduBbsAdminController.class);
	
	@Resource(name = "LectureBoardService")
	private LectureBoardService lectureBoardService;
	@Resource(name="LectureBoardCommentService")
	private LectureBoardCommentService lectureBoardCommentService;
	
	/**
	 * 수업 게시판 리스트 조회
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getListObj.ajax")
	public ResultVO getListObj(
			ModelMap model,
			@ModelAttribute("vo") LectureBoardVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		
		//게시판목록 정보 가져오기
		int totalCnt = lectureBoardService.getLectureBoardTotalCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
		paginationInfo.setCurrentPageNo(vo.getPageNo());
		paginationInfo.setRecordCountPerPage(5);
		paginationInfo.setPageSize(10);
		vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		List<LectureBoardVO> lectureBoardList = lectureBoardService.getLectureBoardList(vo);
		 
		rstData.put("pageNavi",paginationInfo);
		rstData.put("lectureBoardList",lectureBoardList);
		
		result.setResult(1);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("getInfoObj.ajax")
	public ResultVO getInfoObj(
			ModelMap model,
			@ModelAttribute("vo") LectureBoardVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		
		//게시물 데이타 
		LectureBoardVO info = lectureBoardService.getLectureBoardByIndex(vo.getIdx());
		if(ObjectUtils.isEmpty(info)){
			info = new LectureBoardVO();
		}
		rstData.put("lectureBoardInfo", info);
		
		//첨부파일리스트
		LectureBoardAttach attachVo = new LectureBoardAttach();
		attachVo.setBoardIdx(vo.getIdx());
		attachVo.setEduSeq(vo.getEduSeq());
		List<LectureBoardAttach> attachList = lectureBoardService.getLectureBoardAttachList(attachVo);
		rstData.put("attachList", attachList);
		
		//댓글가져오기
		int status = 1;
		int bIdx = vo.getIdx();
		List<LectureBoardCommentVO> cmntList = lectureBoardCommentService.getLectureBoardCommentList(bIdx,status);
		rstData.put("cmntList",cmntList);
		
		result.setResult(1);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("saveInfoObj.ajax")
	public ResultVO saveInfoObj(
			ModelMap model,
			@ModelAttribute("vo") LectureBoardVO vo) {
		//저장
		ResultVO result = lectureBoardService.saveLectureBoard(vo);
		
		return result;
	}
	@ResponseBody
	@RequestMapping("deleteInfoObj.ajax")
	public ResultVO deleteInfoObj(
			ModelMap model,
			@ModelAttribute("vo") LectureBoardVO vo) {
		String userId = SessionUserInfoHelper.getUserId();
		String userNm = SessionUserInfoHelper.getUserNm();
		
		//저장
		ResultVO result = lectureBoardService.deleteLectureBoard(vo.getIdx(),userId,userNm);
		
		return result;
	}
	@RequestMapping(value="dropzoneUpload.ajax",produces = "application/json;charset=UTF-8")
	public String dropzoneUpload(ModelMap model
			,MultipartHttpServletRequest mhsr
			) {
		int resultCode = lectureBoardService.lectureBoardAttachWriteProc(mhsr);
			
			//model.addAttribute("idx",vo.getIdx());
			//model.addAttribute("resultCode", resultCode);	
	
		return "jsonView";
	}
	@RequestMapping("download/{fileSeq}.do")
	public void download(
			@PathVariable( "fileSeq" ) Integer fileSeq,
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		
		if(!SessionUserInfoHelper.isLogined()){
			LncUtil.alertBack(response, "로그인이 필요합니다.");
			return;
		}
		
		LectureBoardAttach attach = lectureBoardService.getLectureBoardAttachMap(fileSeq);
		
		String subPath = "upload/lectureBoard/";//스토리지에 경로
		String fileSave = attach.getFileRename();//저장파일이름
		String fileOrg = attach.getFileOrg();//원본파일이름
		//String filePath = tmpPath+BbsConstant.PATH_ATTACH+fileSave;//로컬 파일 경로
		
		//NaverObjectStorage.download(subPath,fileSave );
		//FileUtil.download(filePath, fileOrg, request, response);
		//FileUtil.delete(filePath);
		
		String filePath = "/"+subPath+fileSave;//로컬 파일 경로
		FileUtil.download(filePath, fileOrg, request, response);
	}
	@RequestMapping(value="deleteFile.ajax")
	public String deleteFile(ModelMap model
			,@RequestParam(defaultValue="0") int fileSeq
			) {
		int resultCode = lectureBoardService.lectureBoardAttachDeleteProc(fileSeq);
		model.addAttribute("resultCode", resultCode);	
		return "jsonView";
	}
	
	@ResponseBody 
	@RequestMapping("boardCommentWriteProc.ajax") 
	public ResultVO boardCommentWriteProc(ModelMap model
			,@RequestParam String comment
			,@RequestParam int bIdx
			,@RequestParam int idx
			) {
		
		ResultVO result = lectureBoardCommentService.lectureBoardCommentWriteProc(comment,bIdx,idx);
		
		return result; 
	}
	@ResponseBody 
	@RequestMapping("boardCommentDeleteProc.ajax") 
	public ResultVO boardCommentDeleteProc(ModelMap model
			,@RequestParam int idx
			) {
		
		ResultVO result = lectureBoardCommentService.lectureBoardCommentDeleteProc(idx);
		
		return result; 
	}
	
}

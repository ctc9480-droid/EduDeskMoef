package com.educare.edu.education.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.UtilComponent;
import com.educare.edu.bbs.service.model.Board;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.TimeTableVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.lectureBoard.service.LectureBoardCommentService;
import com.educare.edu.lectureBoard.service.LectureBoardService;
import com.educare.edu.lectureBoard.vo.LectureBoard;
import com.educare.edu.lectureBoard.vo.LectureBoardAttach;
import com.educare.edu.lectureBoard.vo.LectureBoardCommentVO;
import com.educare.edu.lectureBoard.vo.LectureBoardVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping(value="/user/mypage/popup/")
public class MypagePopupTabBoardController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(MypagePopupTabBoardController.class.getName());
	
	/** 교육관리 서비스 */
	@Resource(name = "EduService")
	private EduService eduService;
	
	
	@Resource(name="LectureBoardService")
	private LectureBoardService lectureBoardService;
	@Resource(name="LectureBoardCommentService")
	private LectureBoardCommentService lectureBoardCommentService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	
	/**  
	 * 통합게시판 목록화면
	 * 공지사항,자유,자료실
	 * @param model
	 * @param vo
	 * @return
	 */ 
	@RequestMapping("myEduView_inc_boardList.do")
	public String myEduView_inc_boardList(ModelMap model
			,@ModelAttribute LectureBoardVO vo
			) {
		vo.setBoardType("eduNotice");
		int totalCnt = lectureBoardService.getLectureBoardTotalCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
		paginationInfo.setCurrentPageNo(vo.getPageNo());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(10);
		
		vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<LectureBoardVO> lectureBoardList = lectureBoardService.getLectureBoardList(vo);
		 
		model.addAttribute("pageNavi",paginationInfo);
		model.addAttribute("lectureBoardList",lectureBoardList);
		model.addAttribute("vo",vo);
		return "/user/mypage/popup/myEduView_inc_boardList";
	}
	@RequestMapping("myEduView_inc_boardReg.do")
	public String myEduView_inc_boardReg(ModelMap model
			,@ModelAttribute LectureBoardVO vo
			) {
		
		return "/user/mypage/popup/myEduView_inc_boardReg";
	}
	@RequestMapping("myEduView_inc_boardView.do")
	public String myEduView_inc_boardView(ModelMap model
			,@ModelAttribute LectureBoardVO vo
			,@RequestParam("boardIdx") int boardIdx
			) {
		LectureBoardVO lectureBoard = lectureBoardService.getLectureBoardByIndex(boardIdx);
		model.addAttribute("lectureBoard", lectureBoard);
		
		//첨부파일리스트
		LectureBoardAttach attachVo = new LectureBoardAttach();
		attachVo.setBoardIdx(boardIdx);
		attachVo.setEduSeq(vo.getEduSeq());
		List<LectureBoardAttach> attachList = lectureBoardService.getLectureBoardAttachList(attachVo);
		model.addAttribute("attachList", attachList);
		
		//댓글가져오기
		int status = 1;
		int bIdx = boardIdx;
		List<LectureBoardCommentVO> cmntList = lectureBoardCommentService.getLectureBoardCommentList(bIdx,status);
		model.addAttribute("cmntList",cmntList);
		
		String sessId =  SessionUserInfoHelper.getUserId();
		lectureBoardService.tabBoardHitPlusByIndex(boardIdx,lectureBoard.getRegId(),sessId);
		
		return "/user/mypage/popup/myEduView_inc_boardView";
	}
	
	@RequestMapping("myEduView_inc_communityList.do")
	public String myEduView_inc_communityList(ModelMap model
			,@ModelAttribute LectureBoardVO vo
			) {
		vo.setBoardType("eduCommunity");
		int totalCnt = lectureBoardService.getLectureCommunityTotalCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
		paginationInfo.setCurrentPageNo(vo.getPageNo());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(10);
		
		vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		//List<LectureBoardVO> lectureCommunityList = lectureBoardService.getLectureCommunityList(vo);
		
		List<LectureBoardVO> lectureCommunityList = lectureBoardService.getLectureBoardList(vo);
		 
		model.addAttribute("pageNavi",paginationInfo);
		model.addAttribute("lectureCommunityList",lectureCommunityList);
		model.addAttribute("vo",vo);
		return "/user/mypage/popup/myEduView_inc_communityList";
	}
	
	// dykim, 20240618
	@RequestMapping("myEduView_inc_homeworkList.do")
	public String myEduView_inc_homeworkList(ModelMap model
			,@ModelAttribute LectureBoardVO vo
			) {
		//LOG.info("myEduView_inc_homeworkList start...");
		//System.out.println("myEduView_inc_homeworkList start...");
		
		vo.setBoardType("eduHomework");
		int totalCnt = lectureBoardService.getLectureCommunityTotalCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
		paginationInfo.setCurrentPageNo(vo.getPageNo());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(10);
		
		vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		//List<LectureBoardVO> lectureCommunityList = lectureBoardService.getLectureCommunityList(vo);
		
		List<LectureBoardVO> lectureCommunityList = lectureBoardService.getLectureBoardList(vo);
		 
		model.addAttribute("pageNavi",paginationInfo);
		model.addAttribute("lectureHomeworkList",lectureCommunityList);
		model.addAttribute("vo",vo);
		return "/user/mypage/popup/myEduView_inc_homeworkList";
	}
	
	// dykim, 20240618
	@RequestMapping("myEduView_inc_homeworkReg.do")
	public String myEduView_inc_homeworkReg(ModelMap model,HttpServletResponse res
			,@ModelAttribute LectureBoardVO vo
			,@RequestParam("boardIdx") int boardIdx
			) throws Exception {
		
		//LOG.info("myEduView_inc_homeworkReg start...");
		//System.out.println("myEduView_inc_homeworkReg start...");
		
		//교육종료면 글작성 안됨
		int eduSeq = vo.getEduSeq();
		CheckVO check = utcp.checkRcept(eduSeq);
		if(check.getResult()==4){
			LncUtil.alertBack(res, "교육이 종료되었습니다. 작성하실 수 없습니다.");
			return null;
		}
		
		LectureBoardVO lectureCommunity = lectureBoardService.getLectureCommunityByIndex(boardIdx);
		model.addAttribute("lectureCommunity", lectureCommunity);
		model.addAttribute("boardIdx", boardIdx); 
		
		return "/user/mypage/popup/myEduView_inc_homeworkReg";
	}
	
	// dykim, 20240618
	@RequestMapping("myEduView_inc_homeworkView.do")
	public String myEduView_inc_homeworkView(ModelMap model,HttpServletResponse res
			,@ModelAttribute LectureBoardVO vo 
			,@RequestParam("boardIdx") int boardIdx
			) throws Exception { 
		//LOG.info("myEduView_inc_homeworkView start...");
				
		LectureBoardVO lectureCommunity = lectureBoardService.getLectureCommunityByIndex(boardIdx);
		model.addAttribute("lectureCommunity", lectureCommunity);
		
		String userId =  SessionUserInfoHelper.getUserId();
		//비밀글이면 
		if("Y".equals(lectureCommunity.getScrtyYn())){
			//본인글이 아니면
			if(userId != null){
				if(!userId.equals(lectureCommunity.getRegId())){
					LncUtil.alertBack(res, "비밀글 입니다.");
					return null;
				}
			}else{
				LncUtil.alertBack(res, "비밀글 입니다.");
				return null;
			}
		}
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		Boolean regYn =  false;
		if(user != null){
			if(user.getLoginId() != null){
				regYn =  user.getLoginId().equals(lectureCommunity.getRegId());
			}
		}
		model.addAttribute("userId", regYn);
		model.addAttribute("boardIdx", boardIdx); 
		 
		lectureBoardService.tabBoardHitPlusByIndex(boardIdx,lectureCommunity.getRegId(),userId);
		
		//댓글가져오기
		int status = 1;
		int bIdx = boardIdx;
		List<LectureBoardCommentVO> cmntList = lectureBoardCommentService.getLectureBoardCommentList(bIdx,status);
		model.addAttribute("cmntList",cmntList);
		
		return "/user/mypage/popup/myEduView_inc_homeworkView";
	} 
	
	@RequestMapping("myEduView_inc_communityReg.do")
	public String myEduView_inc_communityReg(ModelMap model,HttpServletResponse res
			,@ModelAttribute LectureBoardVO vo
			,@RequestParam("boardIdx") int boardIdx
			) throws Exception {
		
		//교육종료면 글작성 안됨
		int eduSeq = vo.getEduSeq();
		CheckVO check = utcp.checkRcept(eduSeq);
		if(check.getResult()==4){
			LncUtil.alertBack(res, "교육이 종료되었습니다. 작성하실 수 없습니다.");
			return null;
		}
		
		LectureBoardVO lectureCommunity = lectureBoardService.getLectureCommunityByIndex(boardIdx);
		model.addAttribute("lectureCommunity", lectureCommunity);
		model.addAttribute("boardIdx", boardIdx); 
		
		return "/user/mypage/popup/myEduView_inc_communityReg";
	}
	@RequestMapping("myEduView_inc_communityView.do")
	public String myEduView_inc_communityView(ModelMap model,HttpServletResponse res
			,@ModelAttribute LectureBoardVO vo 
			,@RequestParam("boardIdx") int boardIdx
			) throws Exception { 
		LectureBoardVO lectureCommunity = lectureBoardService.getLectureCommunityByIndex(boardIdx);
		model.addAttribute("lectureCommunity", lectureCommunity);
		
		String userId =  SessionUserInfoHelper.getUserId();
		//비밀글이면 
		if("Y".equals(lectureCommunity.getScrtyYn())){
			//본인글이 아니면
			if(userId != null){
				if(!userId.equals(lectureCommunity.getRegId())){
					LncUtil.alertBack(res, "비밀글 입니다.");
					return null;
				}
			}
		}
		
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		Boolean regYn =  false;
		if(user != null){
			if(user.getLoginId() != null){
				regYn =  user.getLoginId().equals(lectureCommunity.getRegId());
			}
		}
		
		model.addAttribute("userId", regYn);
		model.addAttribute("boardIdx", boardIdx); 
		 
		lectureBoardService.tabBoardHitPlusByIndex(boardIdx,lectureCommunity.getRegId(),userId);
		
		//댓글가져오기
		int status = 1;
		int bIdx = boardIdx;
		List<LectureBoardCommentVO> cmntList = lectureBoardCommentService.getLectureBoardCommentList(bIdx,status);
		model.addAttribute("cmntList",cmntList);
		
		return "/user/mypage/popup/myEduView_inc_communityView";
	} 
	@ResponseBody 
	@RequestMapping("communityWriteProc.json") 
	public ResultVO communityWriteProc(ModelMap model
			,@RequestParam("title") String title
			,@RequestParam("content") String content
			,@RequestParam(name = "boardType", defaultValue = "") String boardType 
			,@RequestParam(name = "eduSeq", defaultValue = "0") int eduSeq
			,@RequestParam(name = "scrtyYn", defaultValue = "Y") String scrtyYn
			,@RequestParam(name = "boardIdx", defaultValue = "0") int boardIdx
			) {
		LectureBoardVO vo = new LectureBoardVO();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setBoardType(boardType);
		vo.setEduSeq(eduSeq);
		vo.setScrtyYn(scrtyYn);
		vo.setIdx(boardIdx);
		ResultVO result = lectureBoardService.saveLectureBoard(vo);
		return result; 
	}
	@ResponseBody 
	@RequestMapping("communityDeletProc.json") 
	public ResultVO communityDeletProc(ModelMap model
			,@RequestParam(name = "boardIdx", defaultValue = "0") int boardIdx
			) {
			ResultVO result = new ResultVO();
			result.setResult(0);
			result.setMsg("삭제에 실패하였습니다.");
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			String regId = "";
			String regNm = "";
			if(user != null){
				regId = user.getLoginId();
				regNm = user.getUserNm();
			}
			//LOG.debug("communityDeletProc - boardIdx, regId, regNm : {}, {}, {}", boardIdx, regId, regNm);
			int resultCode=0; 
			resultCode = lectureBoardService.communityDeleteProc(boardIdx, regId, regNm);
			//ResultVO deleteLectureBoard(int idx,String userId,String userNm)
			result.setMsg("삭제가 완료되었습니다.");
			if(resultCode!=0) { 
				result.setResult(1);
			}
		return result; 
	}
	@ResponseBody 
	@RequestMapping("boardWriteProc.json") 
	public ResultVO boardWriteProc(ModelMap model
			,@RequestParam("title") String title
			,@RequestParam("content") String content
			,@RequestParam(name = "boardType", defaultValue = "") String boardType 
			,@RequestParam(name = "eduSeq", defaultValue = "0") int eduSeq
			,@RequestParam(name = "scrtyYn", defaultValue = "Y") String scrtyYn
			,@RequestParam(name = "boardIdx", defaultValue = "0") int boardIdx
			) {
		LectureBoardVO vo = new LectureBoardVO();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setBoardType(boardType);
		vo.setEduSeq(eduSeq);
		vo.setScrtyYn(scrtyYn);
		vo.setIdx(boardIdx);
		ResultVO result = lectureBoardService.saveLectureBoard(vo);
		
		return result; 
	}
	
	@ResponseBody 
	@RequestMapping("boardDeleteProc.ajax") 
	public ResultVO boardDeletProc(ModelMap model
			,@RequestParam(name = "boardIdx", defaultValue = "0") int boardIdx
			) {
		//LOG.debug("boardDeleteProc.ajax - boardIdx : {}", boardIdx);
		String userId = SessionUserInfoHelper.getUserId();
		String userNm = SessionUserInfoHelper.getUserNm();
		ResultVO result = lectureBoardService.deleteLectureBoard(boardIdx,userId,userNm);
		return result; 
	}
	
	
	/**  
	 * 강사 통합게시판 목록화면   강사강사강사강사강사강사강사강사강사강사강사강사강사강사강사강사
	 * 공지사항,자유,자료실
	 * @param model
	 * @param vo
	 * @return
	 */ 
	@RequestMapping("instrctrEduView_inc_boardList.do")
	public String instrctrEduView_inc_boardList(ModelMap model
			,@ModelAttribute LectureBoardVO vo
			) {
		vo.setBoardType("eduNotice");
		
		int totalCnt = lectureBoardService.getLectureBoardTotalCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
		paginationInfo.setCurrentPageNo(vo.getPageNo());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(10);
		
		vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		List<LectureBoardVO> lectureBoardList = lectureBoardService.getLectureBoardList(vo);
		 
		model.addAttribute("pageNavi",paginationInfo);
		model.addAttribute("lectureBoardList",lectureBoardList);
		model.addAttribute("vo",vo); 
		return "/user/mypage/popup/instrctrEduView_inc_boardList";
	}
	@RequestMapping("instrctrEduView_inc_boardReg.do")
	public String instrctrEduView_inc_boardReg(ModelMap model
			,int boardIdx
			) {
		if(boardIdx>0){
			LectureBoardVO lectureBoard = lectureBoardService.getLectureBoardByIndex(boardIdx);
			model.addAttribute("lectureBoard", lectureBoard);
			
			//첨부파일리스트
			LectureBoardAttach attachVo = new LectureBoardAttach();
			attachVo.setBoardIdx(lectureBoard.getIdx());
			attachVo.setEduSeq(lectureBoard.getEduSeq());
			List<LectureBoardAttach> attachList = lectureBoardService.getLectureBoardAttachList(attachVo);
			model.addAttribute("attachList", attachList);
		}
		
		return "/user/mypage/popup/instrctrEduView_inc_boardReg";
	}
	@RequestMapping("instrctrEduView_inc_boardView.do")
	public String instrctrEduView_inc_boardView(ModelMap model,HttpServletResponse res
			,@ModelAttribute LectureBoardVO vo
			,@RequestParam("boardIdx") int boardIdx
			) throws Exception {
		LectureBoardVO lectureBoard = lectureBoardService.getLectureBoardByIndex(boardIdx);
		model.addAttribute("lectureBoard", lectureBoard);
		
		
		
		//첨부파일리스트
		LectureBoardAttach attachVo = new LectureBoardAttach();
		attachVo.setBoardIdx(lectureBoard.getIdx());
		attachVo.setEduSeq(lectureBoard.getEduSeq());
		List<LectureBoardAttach> attachList = lectureBoardService.getLectureBoardAttachList(attachVo);
		model.addAttribute("attachList", attachList);
		
		
		String userId =  SessionUserInfoHelper.getUserId();
		lectureBoardService.tabBoardHitPlusByIndex(boardIdx,lectureBoard.getRegId(),userId);
		
		//댓글가져오기
		int status = 1;
		int bIdx = boardIdx;
		List<LectureBoardCommentVO> cmntList = lectureBoardCommentService.getLectureBoardCommentList(bIdx,status);
		model.addAttribute("cmntList",cmntList);
		
		return "/user/mypage/popup/instrctrEduView_inc_boardView";
	}
	
	
	
	
	
	@RequestMapping("instrctrEduView_inc_communityList.do")
	public String instrctrEduView_inc_communityList(ModelMap model
			,@ModelAttribute LectureBoardVO vo
			) {
		vo.setBoardType("eduCommunity");
		int totalCnt = lectureBoardService.getLectureCommunityTotalCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
		paginationInfo.setCurrentPageNo(vo.getPageNo());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(10);
		
		vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		//List<LectureBoardVO> lectureCommunityList = lectureBoardService.getLectureCommunityList(vo);
		List<LectureBoardVO> lectureCommunityList = lectureBoardService.getLectureBoardList(vo);
		 
		model.addAttribute("pageNavi",paginationInfo);
		model.addAttribute("lectureCommunityList",lectureCommunityList);
		model.addAttribute("vo",vo);
		return "/user/mypage/popup/instrctrEduView_inc_communityList";
	}
	@RequestMapping("instrctrEduView_inc_communityReg.do") 
	public String instrctrEduView_inc_communityReg(ModelMap model
			,int boardIdx
			) {
		LectureBoardVO lectureCommunity = lectureBoardService.getLectureCommunityByIndex(boardIdx);
		model.addAttribute("lectureCommunity", lectureCommunity);
		model.addAttribute("boardIdx", boardIdx);
		return "/user/mypage/popup/instrctrEduView_inc_communityReg";
	}
	//강사 커뮤니티 상세보기
	@RequestMapping("instrctrEduView_inc_communityView.do")
	public String instrctrEduView_inc_communityView(ModelMap model,HttpServletResponse res
			,@ModelAttribute LectureBoardVO vo 
			,@RequestParam("boardIdx") int boardIdx
			) throws Exception { 
		LectureBoardVO lectureCommunity = lectureBoardService.getLectureCommunityByIndex(boardIdx);
		model.addAttribute("lectureCommunity", lectureCommunity);
		String userId = SessionUserInfoHelper.getUserId();
		
		//비밀글이면 
		if("Y".equals(lectureCommunity.getScrtyYn())){
			//본인글이 아니면
			if(userId != null){
				if(!userId.equals(lectureCommunity.getRegId())){
					LncUtil.alertBack(res, "비밀글 입니다.");
					return null;
				}
			}
		}
		
		Boolean regYn =  false;
		if(userId != null){
			regYn =  userId.equals(lectureCommunity.getRegId());
		}
		
		model.addAttribute("userId", regYn);
		model.addAttribute("boardIdx", boardIdx); 
		 
		lectureBoardService.tabBoardHitPlusByIndex(boardIdx,lectureCommunity.getRegId(),userId); 
		
		//댓글가져오기
		int status = 1;
		int bIdx = boardIdx;
		List<LectureBoardCommentVO> cmntList = lectureBoardCommentService.getLectureBoardCommentList(bIdx,status);
		model.addAttribute("cmntList",cmntList);
		
		return "/user/mypage/popup/instrctrEduView_inc_communityView";
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
}

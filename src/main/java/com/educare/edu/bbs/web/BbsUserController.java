package com.educare.edu.bbs.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.BbsConstant;
import com.educare.edu.bbs.service.BoardAttachService;
import com.educare.edu.bbs.service.BoardCommentService;
import com.educare.edu.bbs.service.BoardService;
import com.educare.edu.bbs.service.InqryService;
import com.educare.edu.bbs.service.PopupService;
import com.educare.edu.bbs.service.impl.BoardMapper;
import com.educare.edu.bbs.service.model.Board;
import com.educare.edu.bbs.service.model.BoardAttach;
import com.educare.edu.bbs.service.model.BoardComment;
import com.educare.edu.bbs.service.model.BoardMst;
import com.educare.edu.bbs.service.model.Inqry;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : BbsUserController.java
 * @author  SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 컨트롤러
 * 
 * <pre>
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	 SI개발팀 강병주     		최초생성 
 * </pre>
 */
@Controller
@RequestMapping(value="/user/")
public class BbsUserController {

	private final static String ERR_RTN_URL="/user/bbs/noticeList.do";
	/** 게시판 서비스 */
	@Resource(name = "BoardService")
	private BoardService boardService;
	@Resource(name = "BoardMapper")
	private BoardMapper boardMapper;
	
	/** 게시판 코멘트 서비스 */
	@Resource(name = "BoardCommentService")
	private BoardCommentService boardCommentService;
	
	/** 팝업 서비스 */
	@Resource(name = "PopupService")
	private PopupService popupService;
	
	/** 온라인 문의 서비스 */
	@Resource(name = "InqryService")
	private InqryService inqryService;
	
	/** 게시판 첨부파일 서비스 */
	@Resource(name = "BoardAttachService")
	private BoardAttachService boardAttachService;
	
	/** 멤버 서비스 */
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	
	/**
	 * 통합게시판 목록화면
	 * 공지사항,자유,자료실
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("{parent1}/{boardType}List.do")
	public String boardList(ModelMap model ,@ModelAttribute Board vo
			,@PathVariable String parent1
			) {
		
		if(BbsConstant.isUserBoard(vo.getBoardType(),parent1)){
			BoardMst boardMst = boardService.getBoardMstMap(vo.getBoardType());
			
			//목록개수세팅
			if(boardMst!=null){
				if(boardMst.getSkinNm()!=null&&boardMst.getSkinNm().equals("album")){
					vo.setRecordCountPerPage(9);
				}
			}
			
			int totalCnt = boardService.getBoardTotalCnt(vo);
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
			paginationInfo.setCurrentPageNo(vo.getPageNo());
			paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
			paginationInfo.setPageSize(10);
			
			vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
			vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			List<Board> boardList = boardService.getBoardList(vo);
			List<Board> noticeList = boardService.getNoticeList(vo);
			
			model.addAttribute("boardList",boardList);
			model.addAttribute("noticeList",noticeList);
			model.addAttribute("pageNavi",paginationInfo);
			model.addAttribute("vo",vo);
			model.addAttribute("boardMst",boardMst);
			if(boardMst!=null){
				if(boardMst.getSkinNm()!=null && !boardMst.getSkinNm().equals("")){
					return "/user/bbs/"+boardMst.getSkinNm()+"List" + LncUtil.TILES;
				}
			}
			return "/user/bbs/boardList" + LncUtil.TILES;
		}else{
			model.addAttribute("message",BbsConstant.MSG_ERR_WRONG_PATH);
			model.addAttribute("moveUrl",ERR_RTN_URL);
			return "alert";
		}
    }
	
	/**
	 * 통합게시판 등록화면
	 * 공지사항,자유,자료실
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("{parent1}/{boardType}Write.do")
	public String boardWrite(ModelMap model ,@ModelAttribute Board vo,@ModelAttribute BoardAttach attachVo
			,@PathVariable String parent1
			) {
		if(BbsConstant.isUserBoard(vo.getBoardType(),parent1)){
			//권한 체크,사용자는 자유게시판만 등록가능
			if("notice,recs".contains(vo.getBoardType())){
				model.addAttribute("message",BbsConstant.MSG_ERR);
				return "alert_back";
			}
			
			//로그인 체크
			if(!"hope".contains(vo.getBoardType())){
				if(!SessionUserInfoHelper.isLogined()){
					model.addAttribute("message",BbsConstant.MSG_ERR_LOGIN);
					return "alert_back";
				}
				//내글인지 체크
				int result = boardService.checkMyBoard(vo);
				if(result!=1){
					model.addAttribute("message",BbsConstant.MSG_RESULT_CODE[result]);
					return "alert_back";
				}
			}
			
			BoardMst boardMst = boardService.getBoardMstMap(vo.getBoardType());
			model.addAttribute("boardMst",boardMst);
			
			//게시물 정보
			Board boardMap = boardService.getBoardMap(vo);
			
			//첨부파일리스트
			attachVo.setBoardIdx(vo.getIdx());
			List<BoardAttach> boardAttachList = boardAttachService.getBoardAttachList(attachVo);
			
			//답글쓰기인 경우 부모글 가져오기
			if(vo.getpIdx()!=0){
				Board vo2 = new Board();
				vo2.setIdx(vo.getpIdx());
				Board parentBoardMap = boardService.getBoardMap(vo2);
				if(parentBoardMap==null){
					model.addAttribute("message",BbsConstant.MSG_ERR_WRONG_PATH);
					return "alert_back";
				}
				model.addAttribute("reTitle","[답글] "+parentBoardMap.getTitle());
			}
			
			model.addAttribute("boardMap",boardMap);
			model.addAttribute("boardAttachList",boardAttachList);
			model.addAttribute("vo",vo);
			model.addAttribute("attachVo",attachVo);
	        return "/user/bbs/boardWrite" + LncUtil.TILES;
		}else{
			model.addAttribute("message",BbsConstant.MSG_ERR_WRONG_PATH);
			model.addAttribute("moveUrl",ERR_RTN_URL);
			return "alert";
		}
    }
	
	/**
	 * 통합게시판 보기화면
	 * boardType:[공지사항:notice, 자유:free, 자료실:]
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("{parent1}/{boardType}View.do")
	public String boardView(ModelMap model ,@ModelAttribute Board vo,@ModelAttribute BoardAttach attachVo
			,@PathVariable String parent1
			) {
		if(BbsConstant.isUserBoard(vo.getBoardType(),parent1)){
			BoardMst boardMst = boardService.getBoardMstMap(vo.getBoardType());
			model.addAttribute("boardMst",boardMst);
			
			boardService.setBoardHit(vo);
			
			Board boardMap = boardService.getBoardMap(vo);
			if(boardMap==null){
				model.addAttribute("message",BbsConstant.MSG_ERR_VIEW);
				return "alert_back";
			}
			
			//첨부파일리스트
			attachVo.setBoardIdx(vo.getIdx());
			List<BoardAttach> boardAttachList = boardAttachService.getBoardAttachList(attachVo);
			
			//댓글리스트
			BoardComment commentVo = new BoardComment();
			commentVo.setbIdx(boardMap.getIdx());
			List<BoardComment> boardCommentList = boardCommentService.getBoardCommentList(commentVo);
			
			//이전글,다음글
			Board vo2 = new Board();
			vo2.setIdx(vo.getIdx());
			vo2.setNearGb(-1);
			vo2.setBoardType(vo.getBoardType());
			Board boardMapPrev = boardMapper.selectBoardMapNear(vo2);
			vo2.setNearGb(1);
			Board boardMapNext = boardMapper.selectBoardMapNear(vo2);
			
			model.addAttribute("boardMap",boardMap);
			model.addAttribute("boardAttachList",boardAttachList);
			model.addAttribute("boardCommentList",boardCommentList);
			model.addAttribute("boardMapPrev",boardMapPrev);
			model.addAttribute("boardMapNext",boardMapNext);
			model.addAttribute("vo",vo);
			return "/user/bbs/boardView" + LncUtil.TILES;
		}else{
			model.addAttribute("message",BbsConstant.MSG_ERR_WRONG_PATH);
			model.addAttribute("moveUrl",ERR_RTN_URL);
			return "alert";
		}
    }
	@RequestMapping("bbs/boardWriteProc.ajax")
	public String boardWriteProc(ModelMap model
			,@ModelAttribute Board vo
			) {
		vo.setNoticeYn("N");//사용자는 공지게시물 입력 안됨
		int resultCode = boardService.boardWriteProc(vo);
			
		model.addAttribute("idx",vo.getIdx());
		model.addAttribute("resultCode", resultCode);	
	
		return "jsonView";
	}
	@RequestMapping(value="bbs/dropzoneUpload.ajax",produces = "application/json;charset=UTF-8")
	public String dropzoneUpload(ModelMap model
			,MultipartHttpServletRequest mhsr
			) {
		int resultCode = boardAttachService.boardAttachWriteProc(mhsr);
			
			//model.addAttribute("idx",vo.getIdx());
			//model.addAttribute("resultCode", resultCode);	
	
		return "jsonView";
	}
	@RequestMapping(value="bbs/deleteFile.ajax")
	public String deleteFile(ModelMap model
			,@RequestParam(defaultValue="0") int fileSeq
			) {
		int resultCode = boardAttachService.boardAttachDeleteProc(fileSeq);
		model.addAttribute("resultCode", resultCode);	
		return "jsonView";
	}
	/**
	 * 게시판  글 등록,수정
	 * @param model
	 * @param vo
	 * @param attachVo
	 * @return
	 */
	@Transactional
	@RequestMapping("{parent1}/boardWriteProc.do")
	public String boardWriteProc(ModelMap model
			,@ModelAttribute Board vo
			,@PathVariable String parent1
			) {
		if(BbsConstant.isUserBoard(vo.getBoardType(),parent1)){
			//권한 체크,사용자는 자유게시판만 등록가능
			if("notice,recs".contains(vo.getBoardType())){
				model.addAttribute("message",BbsConstant.MSG_ERR);
				return "alert_back";
			}
			
			int result = boardService.checkMyBoard(vo);
			if(result!=1){
				model.addAttribute("message",BbsConstant.MSG_RESULT_CODE[result]);
				return "alert_back";
			}
			
			vo.setNoticeYn("N");
			int resultCode = boardService.boardWriteProc(vo);
			if(resultCode!=1){
				model.addAttribute("message",BbsConstant.MSG_RESULT_CODE[resultCode]);
				return "alert_back";
			}
			
			model.addAttribute("message", BbsConstant.MSG_RESULT_CODE[resultCode]);
			model.addAttribute("moveUrl", vo.getBoardType()+"View.do?idx="+vo.getIdx());	
		}else{
			String message = BbsConstant.MSG_ERR_WRONG_PATH;
			model.addAttribute("message", message);
			model.addAttribute("moveUrl", ERR_RTN_URL);
		}
		return "user/bbs/alert";
	}
	
	/**
	 * 게시글 영구 삭제
	 * 본인글만 영구 삭제 해야되는지 협의 필요 
	 * 현재는 관리자가 모든 글을 삭제 할 수 있음
	 * @param model
	 * @param vo
	 * @param attachVo
	 * @return
	 */
	@RequestMapping("{parent1}/boardDeleteProc.do")
	public String boardDeleteProc(ModelMap model,@ModelAttribute Board vo,@ModelAttribute BoardAttach attachVo
			,@PathVariable String parent1
			) {
		if(BbsConstant.isUserBoard(vo.getBoardType(),parent1)){
			//int resultCode = boardService.boardDeleteProc(vo);
			int resultCode = boardService.setBoardInvisible(vo);
			if(resultCode!=1){
				model.addAttribute("message",BbsConstant.MSG_RESULT_CODE[resultCode]);
				return "alert_back";
			}
			model.addAttribute("message", BbsConstant.MSG_RESULT_CODE[resultCode]);
			model.addAttribute("moveUrl", vo.getBoardType()+"List.do");	
		}else{
			model.addAttribute("message", BbsConstant.MSG_ERR_WRONG_PATH);
			model.addAttribute("moveUrl", ERR_RTN_URL);	
		}
		return "user/bbs/alert";
	}
	
	/** 온라인 문의 게시판 리스트
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("cs/inqryList.do")
	public String inqryList(ModelMap model ,@ModelAttribute Inqry vo
			) {
		int totalCnt = inqryService.getInqryTotalCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPageNo());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);
		
        vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		List<Inqry> inqryList = inqryService.getInqryList(vo);
		
		model.addAttribute("inqryList",inqryList);
		model.addAttribute("pageNavi",paginationInfo);
		model.addAttribute("vo",vo);
        return "/user/bbs/inqryList" + LncUtil.TILES;
    }
	@RequestMapping("cs/inqryWrite.do")
	public String inqryWrite(ModelMap model ,@ModelAttribute Inqry vo
			) {
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message",BbsConstant.MSG_RESULT_CODE[0]);
			return "user/bbs/alert";
		}
		
		//상세보기 가져오기
		Inqry inqryMap = inqryService.getInqryMap(vo);
		
		ResultVO result = inqryService.checkMyInqry(vo,inqryMap);
		if(result.getResult()!=1){
			model.addAttribute("message",result.getMsg());
			return "alert_back";
		}
		
		//로그인사용자정보 가져오기
		UserInfo userInfo = memberMapper.selectUserInfoByPk(SessionUserInfoHelper.getUserId());
		
		model.addAttribute("inqryMap",inqryMap);
		model.addAttribute("vo",vo);
		model.addAttribute("userInfo",userInfo);
        return "/user/bbs/inqryWrite" + LncUtil.TILES;
    }
	@RequestMapping("cs/inqryView.do")
	public String inqryView(ModelMap model ,@ModelAttribute Inqry vo
			) {
		//게시글 가져오기
		Inqry inqryMap = inqryService.getInqryMap(vo);
		
		//상세보기 가능한 글인지 체크하기
		ResultVO result = inqryService.checkMyInqry(vo,inqryMap);
		if(result.getResult()!=1){
			model.addAttribute("message",result.getMsg());
			return "alert_back";
		}
		
		if(inqryMap==null){
			model.addAttribute("message",BbsConstant.MSG_ERR_VIEW);
			model.addAttribute("moveUrl","inqryList.do");
			return "user/bbs/alert";
		}

		model.addAttribute("inqryMap",inqryMap);
		return "/user/bbs/inqryView" + LncUtil.TILES;
    }
	@RequestMapping("cs/inqryWriteProc.do")
	public String inqryWriteProc(ModelMap model ,@ModelAttribute Inqry vo
			) {
		//게시글 가져오기
		Inqry inqryMap = inqryService.getInqryMap(vo);
		if(inqryMap != null){
			if(inqryMap.getStatus() != 1){
				model.addAttribute("message","수정 할수 있는 상태가 아닙니다.");
				model.addAttribute("moveUrl","inqryList.do");
				return "user/bbs/alert";
			}
		}
		
		int result = inqryService.setInqryWriteProc(vo);
		
		String moveUrl="inqryWrite.do";
		if(result==1){
			moveUrl="inqryView.do?idx="+vo.getIdx();
		}
		
		model.addAttribute("message", BbsConstant.MSG_RESULT_CODE[result]);
		model.addAttribute("moveUrl", moveUrl);	
		return "user/bbs/alert";
	}
	@RequestMapping("cs/inqryDeleteProc.do")
	public String inqryDeleteProc(ModelMap model ,@ModelAttribute Inqry vo
			) {
		
		//int result = inqryService.setInqryDeleteProc(vo);
		ResultVO result = inqryService.setInqryInvisible(vo);
		if(result.getResult()!=1){
			model.addAttribute("message",result.getMsg());
			return "alert_back";
		}
		
		model.addAttribute("message", "정상적으로 처리하였습니다.");
		model.addAttribute("moveUrl", "inqryList.do");	
		return "user/bbs/alert";
	}
}

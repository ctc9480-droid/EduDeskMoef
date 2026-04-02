package com.educare.edu.bbs.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.educare.edu.bbs.BbsConstant;
import com.educare.edu.bbs.service.BannerService;
import com.educare.edu.bbs.service.BoardAttachService;
import com.educare.edu.bbs.service.BoardCommentService;
import com.educare.edu.bbs.service.BoardService;
import com.educare.edu.bbs.service.InqryService;
import com.educare.edu.bbs.service.PopupService;
import com.educare.edu.bbs.service.impl.BoardMapper;
import com.educare.edu.bbs.service.model.Banner;
import com.educare.edu.bbs.service.model.Board;
import com.educare.edu.bbs.service.model.BoardAttach;
import com.educare.edu.bbs.service.model.BoardComment;
import com.educare.edu.bbs.service.model.BoardMst;
import com.educare.edu.bbs.service.model.Inqry;
import com.educare.edu.bbs.service.model.Popup;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : BbsAdminController.java
 * @author  SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 컨트롤러
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	 SI개발팀 강병주     		최초생성 
 * </pre>
 */
@Controller
@RequestMapping(value="/admin/")
public class BbsAdminController {

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
	
	/** 배너 서비스 */
	@Resource(name = "BannerService")
	private BannerService bannerService;
	
	/**
	 * 통합게시판 목록화면
	 * 공지사항,자유,자료실
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("bbs/{boardType}List.do")
	public String boardList(ModelMap model ,@ModelAttribute Board vo
			) {
		if(BbsConstant.isAdminBoard(vo.getBoardType())){
			int totalCnt = boardService.getBoardTotalCnt(vo);
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
			paginationInfo.setCurrentPageNo(vo.getPageNo());
			paginationInfo.setRecordCountPerPage(10);
			paginationInfo.setPageSize(10);
			
			vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
			vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			List<Board> boardList = boardService.getBoardList(vo);
			List<Board> noticeList = boardService.getNoticeList(vo);
			
			BoardMst boardMst = boardService.getBoardMstMap(vo.getBoardType());
			
			model.addAttribute("boardList",boardList);
			model.addAttribute("noticeList",noticeList);
			model.addAttribute("pageNavi",paginationInfo);
			model.addAttribute("vo",vo);
			model.addAttribute("boardMst",boardMst);
			return "/admin/bbs/boardList" + LncUtil.TILES;
		}else{
			model.addAttribute("message",BbsConstant.MSG_ERR_WRONG_PATH);
			model.addAttribute("moveUrl","noticeList.do");
			return "user/bbs/alert";
		}
    }
	
	/**
	 * 통합게시판 등록화면
	 * 공지사항,자유,자료실
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("bbs/{boardType}Write.do")
	public String boardWrite(ModelMap model ,@ModelAttribute Board vo,@ModelAttribute BoardAttach attachVo
			) {
		if(BbsConstant.isAdminBoard(vo.getBoardType())){
			
			//게시물 정보
			Board boardMap = boardService.getBoardMap(vo);
			
			//첨부파일리스트
			attachVo.setBoardIdx(vo.getIdx());
			List<BoardAttach> boardAttachList = boardAttachService.getBoardAttachList(attachVo);
			
			//카테고리정보
			BoardMst boardMst = boardService.getBoardMstMap(vo.getBoardType());
			
			model.addAttribute("boardMap",boardMap);
			model.addAttribute("boardAttachList",boardAttachList);
			model.addAttribute("vo",vo);
			model.addAttribute("attachVo",attachVo);
			model.addAttribute("boardMst",boardMst);
	        return "/admin/bbs/boardWrite" + LncUtil.TILES;
		}else{
			model.addAttribute("message",BbsConstant.MSG_ERR_WRONG_PATH);
			model.addAttribute("moveUrl","noticeList.do");
			return "user/bbs/alert";
		}
    }
	
	/**
	 * 통합게시판 보기화면
	 * boardType:[공지사항:notice, 자유:free, 자료실:]
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("bbs/{boardType}View.do")
	public String boardView(ModelMap model ,@ModelAttribute Board vo,@ModelAttribute BoardAttach attachVo
			) {
		if(BbsConstant.isAdminBoard(vo.getBoardType())){
			Board boardMap = boardService.getBoardMap(vo);
			if(boardMap==null){
				model.addAttribute("message",BbsConstant.MSG_ERR_VIEW);
				model.addAttribute("moveUrl","noticeList.do");
				return "user/bbs/alert";
			}
			
			BoardMst boardMst = boardService.getBoardMstMap(vo.getBoardType());
			model.addAttribute("boardMst",boardMst);
			
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
			return "/admin/bbs/boardView" + LncUtil.TILES;
		}else{
			model.addAttribute("message",BbsConstant.MSG_ERR_WRONG_PATH);
			model.addAttribute("moveUrl","noticeList.do");
			return "user/bbs/alert";
		}
    }
	
	/**
	 * 게시판  글 등록,수정
	 * @param model
	 * @param vo
	 * @param attachVo
	 * @return
	 */
	@RequestMapping("bbs/boardWriteProc.json")
	public String boardWriteProc(ModelMap model
			,@ModelAttribute Board vo
			) {
			int resultCode = boardService.boardWriteProc(vo);
			
		model.addAttribute("idx",vo.getIdx());
		model.addAttribute("resultCode", resultCode);	
	
		return "jsonView";
	}
	@RequestMapping(value="bbs/dropzoneUpload.json",produces = "application/json;charset=UTF-8")
	public String dropzoneUpload(ModelMap model
			,MultipartHttpServletRequest mhsr
			) {
		int resultCode = boardAttachService.boardAttachWriteProc(mhsr);
			
			//model.addAttribute("idx",vo.getIdx());
			//model.addAttribute("resultCode", resultCode);	
	
		return "jsonView";
	}
	@RequestMapping(value="bbs/fileUpload.json")
	public String fileUpload(ModelMap model
			,int idx 
			,String boardType
			,MultipartHttpServletRequest mhsr
			) {
		int resultCode = boardService.boardWriteProcMultipart(idx,boardType,mhsr);
			
		model.addAttribute("idx",idx);
		model.addAttribute("resultCode", resultCode);	
	
		return "jsonView";
	}
	@RequestMapping(value="bbs/deleteFile.json")
	public String deleteFile(ModelMap model
			,@RequestParam(defaultValue="0") int fileSeq
			) {
		int resultCode = boardAttachService.boardAttachDeleteProc(fileSeq);
		model.addAttribute("resultCode", resultCode);
		return "jsonView";
	}
	@RequestMapping(value="bbs/deleteThumb.json")
	public String deleteThumb(ModelMap model
			,int idx
			) {
		int resultCode = boardService.deleteThumbFile(idx);
		model.addAttribute("resultCode", resultCode);	
		return "jsonView";
	}
	
	/**
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
		int resultCode = boardService.boardDeleteProc(vo);
		//int resultCode = boardService.setBoardInvisible(vo);
		
		model.addAttribute("message", BbsConstant.MSG_RESULT_CODE[resultCode]);
		model.addAttribute("moveUrl", "/admin/bbs/"+vo.getBoardType()+"List.do");	
		return "user/bbs/alert";
	}
	
	/**
	 * 게시판 코멘트 달기
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("bbs/boardCommentWriteProc.json")
	public String boardCommentWriteProc(ModelMap model,@ModelAttribute BoardComment vo
			) {
		int result = boardCommentService.boardCommentWriteProc(vo);
		vo.setResult(result);
		
		return "jsonView";
	}
	/**
	 * 코멘트  내것인지 체크, 코멘트 수정,삭제 할때 
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("bbs/boardCommentModCheck.json")
	public String boardCommentModCheck(ModelMap model ,@ModelAttribute BoardComment vo
			) {
		int result = boardCommentService.boardCommentModCheck(vo);
		vo.setResult(result);
		
		return "jsonView";
	}
	
	/** 팝업리스트
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("bbs/popupList.do")
	public String popupList(ModelMap model ,@ModelAttribute Popup vo
			) {
		int totalCnt = popupService.getPopupTotalCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPageNo());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);
		
        vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		List<Popup> popupList = popupService.getPopupList(vo);
		
		model.addAttribute("popupList",popupList);
		model.addAttribute("pageNavi",paginationInfo);
		model.addAttribute("vo",vo);
        return "/admin/bbs/popupList" + LncUtil.TILES;
    }
	@RequestMapping("bbs/popupWrite.do")
	public String popupWrite(ModelMap model ,@ModelAttribute Popup vo
			) {
		Popup popupMap = popupService.getPopupMap(vo);
		
		model.addAttribute("popupMap",popupMap);
		model.addAttribute("vo",vo);
        return "/admin/bbs/popupWrite" + LncUtil.TILES;
    }
	@RequestMapping("bbs/popupView.do")
	public String popupView(ModelMap model ,@ModelAttribute Popup vo
			) {
		Popup popupMap = popupService.getPopupMap(vo);
		if(popupMap==null){
			model.addAttribute("message",BbsConstant.MSG_ERR_VIEW);
			model.addAttribute("moveUrl","popupList.do");
			return "user/bbs/alert";
		}

		model.addAttribute("popupMap",popupMap);
		return "/admin/bbs/popupView" + LncUtil.TILES;
    }
	@RequestMapping(value="bbs/popupWriteProc.do")
	public String popupWriteProc(ModelMap model ,@ModelAttribute Popup vo
			) {
    	//팝업등록
		int resultCode = popupService.popupWriteProc(vo);
		model.addAttribute("message", BbsConstant.MSG_RESULT_CODE[resultCode]);
		if(resultCode!=1){
			return "alert_back";
		}
		model.addAttribute("moveUrl", "/admin/bbs/popupList.do");	
		return "user/bbs/alert";
	}
	@RequestMapping("bbs/popupDeleteProc.do")
	public String popupDeleteProc(ModelMap model,@RequestParam(required=false) Integer[] idxs
			) {
		
		int resultCode = popupService.setPopupDeleteListProc(idxs);
		
		model.addAttribute("message", BbsConstant.MSG_RESULT_CODE[resultCode]);
		model.addAttribute("moveUrl", "/admin/bbs/popupList.do");	
		return "user/bbs/alert";
	}
	
	/** 온라인 문의 게시판 리스트
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("bbs/inqryList.do")
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
		
		model.addAttribute("testList",inqryService.getInqryMiniList(4));
		
        return "/admin/bbs/inqryList" + LncUtil.TILES;
    }
	@RequestMapping("bbs/inqryWrite.do")
	public String inqryWrite(ModelMap model ,@ModelAttribute Inqry vo
			) {
		Inqry inqryMap = inqryService.getInqryMap(vo);
		
		model.addAttribute("inqryMap",inqryMap);
		model.addAttribute("vo",vo);
        return "/admin/bbs/inqryWrite" + LncUtil.TILES;
    }
	@RequestMapping("bbs/inqryView.do")
	public String inqryView(ModelMap model ,@ModelAttribute Inqry vo
			) {
		Inqry inqryMap = inqryService.getInqryMap(vo);
		if(inqryMap==null){
			model.addAttribute("message",BbsConstant.MSG_ERR_VIEW);
			model.addAttribute("moveUrl","/admin/bbs/inqryList.do");
			return "user/bbs/alert";
		}

		model.addAttribute("inqryMap",inqryMap);
		return "/admin/bbs/inqryView" + LncUtil.TILES;
    }
	@RequestMapping("bbs/inqryWriteProc.do")
	public String inqryWriteProc(ModelMap model ,@ModelAttribute Inqry vo
			) {
		int result = inqryService.setInqryWriteProc(vo);
		
		model.addAttribute("message", BbsConstant.MSG_RESULT_CODE[result]);
		model.addAttribute("moveUrl", "/admin/bbs/inqryView.do?idx="+vo.getIdx());	
		return "user/bbs/alert";
	}
	@RequestMapping("bbs/inqryDeleteProc.do")
	public String inqryDeleteProc(ModelMap model ,@ModelAttribute Inqry vo
			) {
		
		//int result = inqryService.setInqryDeleteProc(vo);//영구삭제
		ResultVO result = inqryService.setInqryInvisible(vo);//상태값만 변경
		if(result.getResult()!=1){
			model.addAttribute("message", result.getMsg());
			return "alert_back";
		}
		
		model.addAttribute("message", "정상적으로 처리하였습니다.");
		model.addAttribute("moveUrl", "/admin/bbs/inqryList.do");	
		return "user/bbs/alert";
	}
	
	/** 배너관리
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("bbs/bannerList.do")
	public String bannerList(ModelMap model ,@ModelAttribute Banner vo
			) {
		int totalCnt = bannerService.getBannerTotalCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPageNo());
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);
		
        vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		List<Banner> bannerList = bannerService.getBannerList(vo);
		
		model.addAttribute("bannerList",bannerList);
		model.addAttribute("pageNavi",paginationInfo);
		model.addAttribute("vo",vo);
        return "/admin/bbs/bannerList" + LncUtil.TILES;
    }
	@RequestMapping("bbs/bannerWrite.do")
	public String bannerWrite(ModelMap model ,@ModelAttribute Banner vo
			) {
		Banner bannerMap = bannerService.getBannerMap(vo);
		
		model.addAttribute("bannerMap",bannerMap);
		model.addAttribute("vo",vo);
        return "/admin/bbs/bannerWrite" + LncUtil.TILES;
    }
	@RequestMapping(value="bbs/bannerWriteProc.json")
	public String popupWriteProc(ModelMap model 
			,@ModelAttribute Banner vo
			) {
		
		int resultCode = bannerService.bannerWriteProc(vo);
		model.addAttribute("result",resultCode);
		model.addAttribute("idx",vo.getIdx());
		return "jsonView";
	}
	@RequestMapping(value="bbs/bannerImgProc.json")
	public String bannerImgProc(ModelMap model 
			,MultipartHttpServletRequest mhsr
			) {
		
		int resultCode = bannerService.bannerImgProc(mhsr);
		model.addAttribute("result",resultCode); 
		return "jsonView";
	}
	@RequestMapping("bbs/bannerDeleteProc.do")
	public String bannerDeleteProc(ModelMap model ,@ModelAttribute Banner vo
			) {
		
		int result = bannerService.setBannerInvisible(vo);
		//int result = inqryService.setInqryInvisible(vo);//상태값만 변경
		if(result!=1){
			model.addAttribute("message", BbsConstant.MSG_RESULT_CODE[result]);
			return "alert_back";
		}
		
		model.addAttribute("message", BbsConstant.MSG_RESULT_CODE[result]);
		model.addAttribute("moveUrl", "bannerList.do");	
		return "user/bbs/alert";
	}
}

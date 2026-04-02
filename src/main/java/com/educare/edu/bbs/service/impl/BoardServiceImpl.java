
package com.educare.edu.bbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.VarComponent;
import com.educare.edu.bbs.BbsConstant;
import com.educare.edu.bbs.service.BoardAttachService;
import com.educare.edu.bbs.service.BoardCommentService;
import com.educare.edu.bbs.service.BoardService;
import com.educare.edu.bbs.service.model.Board;
import com.educare.edu.bbs.service.model.BoardAttach;
import com.educare.edu.bbs.service.model.BoardComment;
import com.educare.edu.bbs.service.model.BoardMst;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.tmpFile.service.TempFileService;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.MaxNumUtil;

/**
 * @Class Name : BbsServiceImpl.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 서비스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
@Transactional
@Service("BoardService")
public class BoardServiceImpl implements BoardService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Resource(name = "BoardMapper")
	private BoardMapper boardMapper;
	@Resource(name = "BoardAttachMapper")
	private BoardAttachMapper boardAttachMapper;
	@Resource(name = "BoardMstMapper")
	private BoardMstMapper boardMstMapper;
	
	/** 임시파일 서비스 */
	@Resource(name = "TempFileService")
	private TempFileService tempFileService;
	
	/** 첨부파일 서비스 */
	@Resource(name = "BoardAttachService")
	private BoardAttachService boardAttachService;
	
	/** 코멘트 서비스 */
	@Resource(name = "BoardCommentService")
	private BoardCommentService boardCommentService;
	
	
	
	@Override
	public int getBoardTotalCnt(Board vo) {
		int totalCnt = boardMapper.selectBoardTotalCnt(vo);
		return totalCnt;
	}
	
	@Override
	public List<Board> getBoardList(Board vo) {
		List<Board> noticeList = boardMapper.selectBoardList(vo);
		return noticeList;
	}
	@Override
	public Board getBoardMap(Board vo) {
		Board noticeMap = boardMapper.selectBoardMap(vo);
		return noticeMap;
	}

	@Override
	public int boardWriteProc(Board vo) {
		int result=0;
		try {
			//등록아이디저장
			if(SessionUserInfoHelper.isLogined()){
				vo.setRegId(SessionUserInfoHelper.getUserId());
				vo.setModId(SessionUserInfoHelper.getUserId());
				vo.setRegNm(SessionUserInfoHelper.getUserNm());
				vo.setModNm(SessionUserInfoHelper.getUserNm());
			}else{
				vo.setRegId("0");
				vo.setModId("0");
				vo.setRegNm("익명");
				vo.setModNm("익명");
			}
			
			Board boardMap = boardMapper.selectBoardMap(vo);//등록,수정여부 위해
			if(boardMap!=null){
				UserInfo user = SessionUserInfoHelper.getUserInfo();
				if(user == null){
					return 6;
				}
				String userId = user.getUserId();
				String userMemLvl = user.getUserMemLvl();
				if(!"1".equals(userMemLvl)){
					if(!boardMap.getRegId().equals(userId)){
						return 6;
					}
				}
				
				boardMapper.updateBoardMap(vo);
			}else{
				boardMapper.insertBoardMap(vo);
				
				if(vo.getpIdx()!=0){
					//답글 로직
					Board vo2 = new Board();
					BeanUtils.copyProperties(vo, vo2);
					//부모글의 gIdx를 가져와야 한다.
					vo2.setIdx(vo.getpIdx());
					Board boardMap2 = boardMapper.selectBoardMap(vo2);
					String gOrd = boardMap2.getgOrd()+":"+vo.getIdx();
					vo.setgOrd(gOrd);
					vo.setgIdx(boardMap2.getgIdx());
					vo.setgLvl(boardMap2.getgLvl()+1);
					boardMapper.updateBoardMapForGroup(vo);//그룹오더값을 저장한다.
				}
			}
			result=1;
			
			//attachVo.setBoardIdx(vo.getIdx());
			//result = boardAttachService.boardAttachWriteProc(attachVo);
			//if(result!=1){
			//	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			//}
			
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
		}
		return result;
	}

	@Override
	public int boardDeleteProc(Board vo) {
		int result=0;
		try {
			
			Board boardMap = boardMapper.selectBoardMap(vo);//등록,수정여부 위해
			if(boardMap!=null){
				UserInfo user = SessionUserInfoHelper.getUserInfo();
				if(user == null){
					return 0;
				}
				String userMemLvl = user.getUserMemLvl();
				
				if(userMemLvl != null && !userMemLvl.equals("1")){
					if(!boardMap.getRegId().equals(user.getUserId())){
						return 6;
					}
				}
				
				//댓글 삭제
				BoardComment commentVo = new BoardComment();
				commentVo.setbIdx(vo.getIdx());
				result = boardCommentService.boardCommentDeleteProc(commentVo);
				
				//게시물삭제
				boardMapper.deleteBoardMap(vo);
				
				//파일삭제
				BoardAttach attachVo = new BoardAttach();
				attachVo.setBoardIdx(vo.getIdx());
				List<BoardAttach> baList = boardAttachMapper.selectBoardAttachList(attachVo);
				for(BoardAttach baMap:baList){
					result = boardAttachService.boardAttachDeleteProc(baMap.getFileSeq());
				}
			}
			result=1;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
		}
		return result;
	}

	@Override
	public void setBoardHit(Board vo) {
		int result = checkMyBoard(vo);
		if(result==1){//내글인경우
			return;
		}
		
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = req.getSession();
		Date lastHitDate = (Date) session.getAttribute(VarComponent.SESSION_KEY_BOARD+vo.getIdx());
		if(lastHitDate==null){
			lastHitDate = DateUtil.getStr2Date(DateUtil.getDate2Str(new Date().getTime()-(1000*60*60*24),"yyyyMMdd"),"yyyyMMdd");
		}
		Date nowHitDate = new Date();
		
		//ystem.out.println(lastHitDate);
		//ystem.out.println(nowHitDate);
		
		if(nowHitDate.getTime()-lastHitDate.getTime()<(1000*60*60*24)){
			return;
		}
		
		//조회수업데이트
		boardMapper.updateBoardHit(vo);
		
		//세션에 해당 글을 읽은 시간 체크
		session.setAttribute(VarComponent.SESSION_KEY_BOARD+vo.getIdx(), new Date());
	}

	@Override
	public int checkMyBoard(Board vo) {
		Board boardMap = boardMapper.selectBoardMap(vo);
		if(boardMap!=null){
			if(!boardMap.getRegId().equals(SessionUserInfoHelper.getUserId())){
				return 6;
			}
		}
		return 1;
	}

	@Override
	public int setBoardInvisible(Board vo) {
		try {
			//관리자가 아닌경우만 본인글 여부 체크
			if(!SessionUserInfoHelper.isAdmin()){
				int result = checkMyBoard(vo);
				if(result!=1){
					return result;
				}
			}
				
			vo.setStatus(0);
			boardMapper.updateBoardStatus(vo);
			return 1;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
		}
		return 0;
	}

	@Override
	public List<Board> getBoardMiniList(String boardType, int recordCountPerPage) {
		Board vo = new Board();
		vo.setBoardType(boardType);
		vo.setFirstRecordIndex(0);
		vo.setRecordCountPerPage(recordCountPerPage);
		//return boardMapper.selectBoardList(vo);
		
		List<Board> list1 = boardMapper.selectBoardList(vo);
		List<Board> list2 = boardMapper.selectNoticeList(vo);
		
		List<Board> list = new ArrayList<Board>();
		for(Board o : list2){
			list.add(o);
		}
		for(Board o : list1){
			list.add(o);
		}
		if(list.size()>5){
			list = list.subList(0, 5);
		}
		
		return list;
	}

	@Override
	public BoardMst getBoardMstMap(String boardType) {
		BoardMst result = boardMstMapper.selectBoardMstMap(boardType);
		return result;
	}

	@Override
	public List<Board> getBoardMiniList(String[] boardTypeArr, int recordCountPerPage) {
		Board vo = new Board();
		vo.setBoardTypeArr(boardTypeArr);
		vo.setFirstRecordIndex(0);
		vo.setRecordCountPerPage(recordCountPerPage);
		//return boardMapper.selectBoardList(vo);
		
		List<Board> list1 = boardMapper.selectBoardList(vo);
		List<Board> list2 = boardMapper.selectNoticeList(vo);
		
		List<Board> list = new ArrayList<Board>();
		for(Board o : list2){
			list.add(o);
		}
		for(Board o : list1){
			list.add(o);
		}
		if(list.size()>5){
			list = list.subList(0, 5);
		}
		
		return list;
	}

	@Override
	public int boardWriteProcMultipart(int idx,String boardType, MultipartHttpServletRequest mhsr) {
		try {
			
			MultipartFile mf = mhsr.getFile("thumbFile");
			if(mf!=null){
				int boardIdx = idx;
				String folderName = BbsConstant.PATH_ATTACH;
				String orgName = LncUtil.getEncode(mf.getOriginalFilename());
				String fileRename = FileUtil.getFileRename(orgName, boardType+"_"+boardIdx+"_"+"thumb_");
				
				//확장자 체크
				String ext = FileUtil.getFileExt(orgName);
				if(!"png,jpg,jpeg".contains(ext)){
					return 3;//업로드 금지 파일
				}
				
				//int result_nos=NaverObjectStorage.multiPartupload(mf, folderName, orgName, fileRename);
				ResultVO result = FileUtil.multiPartupload(mf, folderName, orgName, fileRename);
				if (result.getResult()==1) {
					Board vo=new Board();
					vo.setIdx(idx);
					vo.setThumbFile(fileRename);
					boardMapper.updateThumbFile(vo);
				}
				return 1;
			}
			return 2;//파일이 없음
			
		} catch (NullPointerException e) {
			LOG.info("e : "+e.getMessage());
			return 0;
		}
	}

	@Override
	public int deleteThumbFile(int idx) {
		try {
			Board vo = new Board();
			vo.setIdx(idx);
			//파일정보가져오기
			Board board = boardMapper.selectBoardMap(vo);
			String fileRename = board.getThumbFile();
			String folderName = BbsConstant.PATH_ATTACH;
			
			//스토리지에서 삭제
			//NaverObjectStorage.delete(folderName, fileRename);
			FileUtil.delete(folderName+fileRename);
			
			//테이블에서 삭제
			boardMapper.updateThumbFile(vo);
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	@Override
	public List<Board> getNoticeList(Board vo) {
		return boardMapper.selectNoticeList(vo);
	}

}

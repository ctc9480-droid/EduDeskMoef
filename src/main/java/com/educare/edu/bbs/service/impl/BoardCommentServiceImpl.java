
package com.educare.edu.bbs.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.educare.edu.bbs.service.BoardCommentService;
import com.educare.edu.bbs.service.model.BoardComment;
import com.educare.edu.member.service.SessionUserInfoHelper;

/**
 * @Class Name : BoardCommentServiceImpl.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 코멘트 서비스
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
@Service("BoardCommentService")
public class BoardCommentServiceImpl implements BoardCommentService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(BoardCommentServiceImpl.class);
	
	@Resource(name = "BoardCommentMapper")
	private BoardCommentMapper boardCommentMapper;
	
	
	@Override
	public int boardCommentWriteProc(BoardComment vo) {
		int result=0;
		try {
			//등록아이디저장
			vo.setRegId(SessionUserInfoHelper.getUserId());
			vo.setModId(SessionUserInfoHelper.getUserId());
			vo.setRegNm(SessionUserInfoHelper.getUserNm());
			vo.setRegNk(SessionUserInfoHelper.getUserNm());
			vo.setModNm(SessionUserInfoHelper.getUserNm());
			
			BoardComment boardMap = boardCommentMapper.selectBoardCommentMap(vo);//등록,수정여부 위해
			if(boardMap!=null){
				boardCommentMapper.updateBoardCommentMap(vo);
			}else{
				boardCommentMapper.insertBoardCommentMap(vo);
				
				if(vo.getpIdx()!=0){
					//답글 로직
					BoardComment vo2 = new BoardComment();
					BeanUtils.copyProperties(vo, vo2);
					//부모글의 gIdx를 가져와야 한다.
					vo2.setIdx(vo.getpIdx());
					BoardComment boardMap2 = boardCommentMapper.selectBoardCommentMap(vo2);
					String gOrd = boardMap2.getgOrd()+":"+vo.getIdx();
					vo.setgOrd(gOrd);
					vo.setgIdx(boardMap2.getgIdx());
					vo.setgLvl(boardMap2.getgLvl()+1);
					boardCommentMapper.updateBoardCommentMapForGroup(vo);//그룹오더값을 저장한다.
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
	public List<BoardComment> getBoardCommentList(BoardComment vo) {
		List<BoardComment> commentList = boardCommentMapper.selectBoardCommentList(vo);
		return commentList;
	}


	@Override
	public int boardCommentModCheck(BoardComment vo) {
		int result=0;
		try {
			BoardComment boardMap = boardCommentMapper.selectBoardCommentMap(vo);//등록,수정여부 위해
			if(boardMap!=null){
				if(boardMap.getRegId().equals(SessionUserInfoHelper.getUserId())){
					vo.setContent(boardMap.getContent());
					result=1;
				}
			}
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
		}
		return result;
	}

	@Override
	public int boardCommentDeleteProc(BoardComment commentVo) {
		int result=0;
		try {
			List<BoardComment> commentList = boardCommentMapper.selectBoardCommentList(commentVo);//등록,수정여부 위해
			if(commentList.size()>0){
				boardCommentMapper.deleteBoardCommentMap(commentVo);
			}
			result=1;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
		}
		return result;
	}
}

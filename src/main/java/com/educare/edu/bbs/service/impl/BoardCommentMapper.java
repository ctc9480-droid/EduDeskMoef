package com.educare.edu.bbs.service.impl;

import java.util.List;

import com.educare.edu.bbs.service.model.BoardComment;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : BoardCommentMapper.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 코멘트 매퍼
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
@Mapper("BoardCommentMapper")
public interface BoardCommentMapper {
	BoardComment selectBoardCommentMap(BoardComment vo);

	void updateBoardCommentMap(BoardComment vo);

	void insertBoardCommentMap(BoardComment vo);

	void updateBoardCommentMapForGroup(BoardComment vo);

	List<BoardComment> selectBoardCommentList(BoardComment vo);

	void deleteBoardCommentMap(BoardComment commentVo);
}

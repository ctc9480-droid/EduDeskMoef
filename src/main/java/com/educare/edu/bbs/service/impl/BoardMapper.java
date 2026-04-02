package com.educare.edu.bbs.service.impl;

import java.util.List;

import com.educare.edu.bbs.service.model.Board;
import com.educare.edu.bbs.service.model.BoardMst;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : BoardMapper.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 매퍼
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
@Mapper("BoardMapper")
public interface BoardMapper {

	List<Board> selectBoardList(Board vo);

	Board selectBoardMap(Board vo);

	void updateBoardMap(Board vo);

	void insertBoardMap(Board vo);

	int selectBoardTotalCnt(Board vo);

	void updateBoardMapForGroup(Board vo);

	void deleteBoardMap(Board vo);

	void updateBoardHit(Board vo);

	void updateBoardStatus(Board vo);
	
	/**
	 * 썸네일정보 업데이트
	 * @param vo
	 */
	void updateThumbFile(Board vo);

	Board selectBoardMapNear(Board vo2);
	
	List<Board> selectNoticeList(Board vo);
}

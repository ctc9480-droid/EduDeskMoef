package com.educare.edu.bbs.service.impl;

import java.util.List;

import com.educare.edu.bbs.service.model.BoardAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : BoardAttachMapper.java
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
@Mapper("BoardAttachMapper")
public interface BoardAttachMapper {

	void insertBopardAttach(BoardAttach boardAttach);

	List<BoardAttach> selectBoardAttachList(BoardAttach attachVo);

	BoardAttach selectBoardAttachMap(Integer fileSeq);

	void deleteBoardAttach(int parseInt);

}

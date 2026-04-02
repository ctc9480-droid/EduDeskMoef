package com.educare.edu.bbs.service.impl;

import java.util.List;

import com.educare.edu.bbs.service.model.Inqry;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : InqryMapper.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 온라인 문의 게시판 매퍼
 * 
 * <pre>
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
@Mapper("InqryMapper")
public interface InqryMapper {

	List<Inqry> selectInqryList(Inqry vo);

	Inqry selectInqryMap(Inqry vo);

	void updateInqryMap(Inqry vo);

	void insertInqryMap(Inqry vo);

	int selectInqryTotalCnt(Inqry vo);

	void deleteInqryMap(Inqry vo);

	void updateInqryMapForStatus(Inqry vo);

}

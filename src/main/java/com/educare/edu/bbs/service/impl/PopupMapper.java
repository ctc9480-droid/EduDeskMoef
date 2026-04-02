package com.educare.edu.bbs.service.impl;

import java.util.List;

import com.educare.edu.bbs.service.model.Popup;

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
@Mapper("PopupMapper")
public interface PopupMapper {

	List<Popup> selectPopupList(Popup vo);

	Popup selectPopupMap(Integer idx);

	void updatePopupMap(Popup vo);

	void insertPopupMap(Popup vo);

	int selectPopupTotalCnt(Popup vo);

	void deletePopupMap(Integer idx);

}

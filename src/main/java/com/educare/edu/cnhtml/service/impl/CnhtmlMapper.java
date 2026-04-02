package com.educare.edu.cnhtml.service.impl;

import java.util.List;

import com.educare.edu.bbs.service.model.Banner;
import com.educare.edu.bbs.service.model.Popup;
import com.educare.edu.comn.vo.CnhtmlVO;
import com.educare.edu.ebook.service.model.Ebook;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CnhtmlMapper")
public interface CnhtmlMapper {
	int selectCnhtmlPageCnt(CnhtmlVO vo);
	List<CnhtmlVO> selectCnhtmlPageList(CnhtmlVO vo);
}

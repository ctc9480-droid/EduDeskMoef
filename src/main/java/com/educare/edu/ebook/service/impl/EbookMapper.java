package com.educare.edu.ebook.service.impl;

import java.util.List;

import com.educare.edu.bbs.service.model.Banner;
import com.educare.edu.bbs.service.model.Popup;
import com.educare.edu.ebook.service.model.Ebook;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("EbookMapper")
public interface EbookMapper {

	List<Ebook> selectEbookList(Ebook vo);

	Ebook selectEbookMap(Integer idx);

	void updateEbookMap(Ebook vo);

	void insertEbookMap(Ebook vo);

	int selectEbookTotalCnt(Ebook vo);

	void updateEbookFile(Ebook vo);

}

package com.educare.edu.eduOld.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.eduOld.mapper.EduOldMapper;
import com.educare.edu.eduOld.service.EduOldService;
import com.educare.edu.eduOld.vo.EduOldVO;
import com.educare.edu.education.service.EduVO;

@Service("EduOldService")
public class EduOldServiceImpl implements EduOldService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EduOldServiceImpl.class.getName());

	@Resource(name = "EduOldMapper")
	private EduOldMapper eduOldMapper;
	
	
	
	@Override
	public ResultVO selectEduOldPageList(EduVO vo, int listRow) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			vo.setRow(listRow);
			
			//총 갯수
			int totalCnt = eduOldMapper.selectEduOldPageCnt(vo);
			
			//페이징처리
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
	        paginationInfo.setCurrentPageNo(vo.getPage());
	        paginationInfo.setRecordCountPerPage(vo.getRow());
	        paginationInfo.setPageSize(DEFAULT_PAGE_SIZE);
	        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			
			//신청명단
			List<EduOldVO> eduOldList = eduOldMapper.selectEduOldPageList(vo);
			rstData.put("eduOldList", eduOldList);
			rstData.put("pageNavi",paginationInfo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}

}

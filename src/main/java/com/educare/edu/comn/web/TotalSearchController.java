package com.educare.edu.comn.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.component.UtilComponent;
import com.educare.edu.comn.mapper.TotalSearchMapper;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping(value="/comm/totalSearch/")
public class TotalSearchController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(TotalSearchController.class.getName());
	
	
	@Resource(name="TotalSearchMapper")
	private TotalSearchMapper totalSearchMapper;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	@RequestMapping("searchResult.do")
	public String searchResult(ModelMap model
			,String totalSrchText
			) {
		
		model.addAttribute("totalSrchText",totalSrchText);
		return "/comm/totalSearch/searchResult" + LncUtil.TILES;
	}
	@ResponseBody
	@RequestMapping("searchResult.ajax")
	public ResultVO searchResult2(ModelMap model
			,String totalSrchText
			,String gubun
			,Integer page
			) {
		ResultVO result =  new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			EduVO vo = new EduVO();
			vo.setTotalSrchText(totalSrchText);
			vo.setRow(3);
			vo.setPage(page);
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(vo.getPage());
			paginationInfo.setRecordCountPerPage(vo.getRow());
			paginationInfo.setPageSize(10);
	        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
	        int totalCnt = 0;
	        
	        //개인
	        totalCnt = totalSearchMapper.selectPageCntLctre(vo);
	        paginationInfo.setTotalRecordCount(totalCnt);
	        List<Lecture> lctreList = totalSearchMapper.selectPageListLctre(vo);
	        //접수가능여부 세팅
	        for(Lecture dataMap:lctreList){
	        	dataMap.setCheckRcept(utcp.checkRcept(dataMap.getEduSeq()).getResult());
	        }
	        rstData.put("dataList", lctreList);
			
			rstData.put("totalCnt", totalCnt);
			rstData.put("pageNavi", paginationInfo);
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
		
		
	}
	
}

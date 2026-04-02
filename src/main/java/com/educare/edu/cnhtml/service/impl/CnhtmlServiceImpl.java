package com.educare.edu.cnhtml.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.educare.edu.accip.service.AccipService;
import com.educare.edu.cnhtml.service.CnhtmlService;
import com.educare.edu.comn.mapper.AdminIpMapper;
import com.educare.edu.comn.mapper.ContentHtmlMapper;
import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.model.ContentHtml;
import com.educare.edu.comn.vo.AdminIpVO;
import com.educare.edu.comn.vo.CnhtmlVO;
import com.educare.edu.comn.vo.ResultVO;




import com.educare.edu.member.service.SessionUserInfoHelper;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
@Service("CnhtmlService")
public class CnhtmlServiceImpl implements CnhtmlService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(CnhtmlServiceImpl.class.getName());

	@Resource(name="ContentHtmlMapper")
	private ContentHtmlMapper contentHtmlMapper;
	@Resource(name="CnhtmlMapper")
	private CnhtmlMapper cnhtmlMapper;

	@Override
	public ResultVO getCnhtmlPageList(String srchWrd, Integer page) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			int row = 10;
			CnhtmlVO vo = new CnhtmlVO();
			vo.setSrchWrd(srchWrd);
			vo.setPage(page);
			vo.setRow(row);
			
			int totalCnt = cnhtmlMapper.selectCnhtmlPageCnt(vo);
			
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
	        paginationInfo.setCurrentPageNo(vo.getPage());
	        paginationInfo.setRecordCountPerPage(row);
	        paginationInfo.setPageSize(10);
	        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			
			List<CnhtmlVO> list = cnhtmlMapper.selectCnhtmlPageList(vo);
			
			rstData.put("pageNavi", paginationInfo);
			rstData.put("list", list);
			
			result.setResult(1);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO saveCnhtmlProc(int cnhtSeq, String cnhtType, String title, String content, int state) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String userId = SessionUserInfoHelper.getUserId();
			Date regDe = new Date();
			Date updDe = new Date();
			String regId = userId;
			String updId = userId;
			ContentHtml vo = new ContentHtml(cnhtSeq, cnhtType,title, content, regDe, regId, updDe, updId, state);
			
			//아이피정보
			ContentHtml cnht = contentHtmlMapper.selectByPk(cnhtSeq);
			
			if(cnht!=null){
				contentHtmlMapper.updateByPk(vo);
			}else{
				contentHtmlMapper.insertByPk(vo);
			}
			
			result.setResult(1);
			return result;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			result.setMsg("오류");
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getCnhtmlData(int cnhtSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			ContentHtml cnht = contentHtmlMapper.selectByPk(cnhtSeq);
			rstData.put("cnht", cnht);
			
			result.setResult(1);
			return result;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			result.setMsg("오류");
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getCnhtmlDataByActive(String cnhtType) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			ContentHtml cnht = contentHtmlMapper.selectByActive(cnhtType);
			if(cnht == null){
				result.setMsg("컨텐츠가 없음 시스템 확인 필요");
				result.setResult(0);
				return result;
			}
			
			rstData.put("cnht", cnht);
			
			result.setResult(1);
			return result;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			result.setMsg("오류");
			result.setResult(0);
			return result;
		}
	}
	
}

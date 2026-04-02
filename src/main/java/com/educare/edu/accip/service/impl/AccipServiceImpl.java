package com.educare.edu.accip.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.educare.edu.accip.service.AccipService;
import com.educare.edu.comn.mapper.AdminIpMapper;
import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.vo.AdminIpVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
@Service("AccipService")
public class AccipServiceImpl implements AccipService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(AccipServiceImpl.class.getName());

	@Resource(name="AdminIpMapper")
	private AdminIpMapper adminIpMapper;

	@Override
	public ResultVO getAccipPageList(String srchWrd, Integer page) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			int row = 10;
			AdminIpVO vo = new AdminIpVO();
			vo.setSrchWrd(srchWrd);
			vo.setPage(page);
			vo.setRow(row);
			
			int totalCnt = adminIpMapper.selectAccipPageCnt(vo);
			
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
	        paginationInfo.setCurrentPageNo(vo.getPage());
	        paginationInfo.setRecordCountPerPage(row);
	        paginationInfo.setPageSize(10);
	        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			
			List<AdminIpVO> list = adminIpMapper.selectAccipPageList(vo);
			
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
	public ResultVO delAccipProc(int idx) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
//			String userId = SessionUserInfoHelper.getUserId();
//			int state = 0;
//			Date regDt = new Date();
//			Date updDt = new Date();
//			String regId = userId;
//			String updId = userId;
//			AdminIp vo = new AdminIp("", state, "", regDt, regId, updDt, updId);
			
			//동일한 아이피 체크
//			vo.setIdx(idx);
//			int dupCnt = adminIpMapper.selectAccipDupIp(vo);
//			if(dupCnt>0){
//				result.setMsg("이미 저장된 아이피입니다.");
//				result.setResult(0);
//				return result;
//			}
			
			//아이피정보
//			AdminIp ip = adminIpMapper.selectByPk(idx);
			
//			if(ip!=null){
//				adminIpMapper.updateByPk(vo);
//			}else{
//				adminIpMapper.insertByPk(vo);
//			}
			adminIpMapper.deleteByPk(idx);
			
			result.setResult(1);
			return result;
		} catch (Exception e) {
			result.setMsg("오류");
			e.printStackTrace();
			result.setResult(0);
			return result;
		}
	}
	
	@Override
	public ResultVO saveAccipProc(int idx,String ip4,String memo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String userId = SessionUserInfoHelper.getUserId();
			int state = 1;
			Date regDt = new Date();
			Date updDt = new Date();
			String regId = userId;
			String updId = userId;
			AdminIp vo = new AdminIp(ip4, state, memo, regDt, regId, updDt, updId);
			
			//동일한 아이피 체크
			vo.setIdx(idx);
			int dupCnt = adminIpMapper.selectAccipDupIp(vo);
			if(dupCnt>0){
				result.setMsg("이미 저장된 아이피입니다.");
				result.setResult(0);
				return result;
			}
			
			//아이피정보
			AdminIp ip = adminIpMapper.selectByPk(idx);
			
			if(ip!=null){
				adminIpMapper.updateByPk(vo);
			}else{
				adminIpMapper.insertByPk(vo);
			}
			
			result.setResult(1);
			return result;
		} catch (Exception e) {
			result.setMsg("오류");
			e.printStackTrace();
			result.setResult(0);
			return result;
		}
	}

	@Override
	public AdminIp getAccip(Integer idx) {
		AdminIp ip = adminIpMapper.selectByPk(idx);
		return ip;
	}

	
}

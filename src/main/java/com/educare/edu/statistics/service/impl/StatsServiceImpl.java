package com.educare.edu.statistics.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.stringtemplate.v4.compiler.CodeGenerator.includeExpr_return;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureRoomMapper;
import com.educare.edu.comn.model.LectureRoom;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.statistics.service.StatsService;
import com.educare.edu.statistics.service.StatsVO;
import com.educare.edu.statistics.service.model.EduStats;
import com.educare.edu.statistics.service.model.Tuition;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

@Service("StatsService")
public class StatsServiceImpl implements StatsService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(StatsServiceImpl.class.getName());
	
	/** 통계 Mapper */
	@Resource(name = "StatsMapper")
	private StatsMapper statsMapper;
	
	@Resource(name="FeedbackService")
	private FeedbackService feedbackService;
	@Resource(name="FeedbackMapper")
	private FeedbackMapper feedbackMapper;
	@Resource(name="LectureMapper")
	private LectureMapper lectureMapper;
	@Resource(name="LectureRoomMapper")
	private LectureRoomMapper lectureRoomMapper;




	@Override
	public Map<String, Object> getStatFeedback(StatsVO vo) {

		Map<String, Object> feedbackInfo = feedbackService.getFeedbackInfo(vo.getFbIdx() );
		
		//피드백 대상 수
		int allCnt = statsMapper.selectStatFeedbackUserCnt(vo);
		feedbackInfo.put("allCnt", allCnt);
		
		//
		List<Map<String, Object>> qtList =  (List<Map<String, Object>>) feedbackInfo.get("qtList");
		for(Map<String, Object> qtMap:qtList){
			FeedbackVO qt = (FeedbackVO) qtMap.get("qt");
			if(qt.getQtType()!=0){
				List<FeedbackVO> chList =  (List<FeedbackVO>) qtMap.get("chList");
				int qtAsCnt = 0;
				
				//새로 담을chList
				List<Map<String, Object>> chList2 = new ArrayList<Map<String, Object>>();
				
				for(FeedbackVO feedbackChoice:chList){
					Map<String, Object> chMap = new HashMap<String,Object>();
					vo.setFbIdx(feedbackChoice.getFbIdx());
					vo.setQtIdx(feedbackChoice.getQtIdx());
					vo.setChIdx(feedbackChoice.getChIdx());
					StatsVO asMap = statsMapper.selectStatFeedbackAnswerCnt(vo);
					if(!ObjectUtils.isEmpty(asMap)){
						qtAsCnt+=asMap.getAsCnt();
					}else{
						asMap = new StatsVO();
					}
					
					//합산 점수 넣기
					int sumPoint = asMap.getAsCnt()*feedbackChoice.getPoint();
					asMap.setSumPoint(sumPoint);
					
					chMap.put("ch", feedbackChoice);
					chMap.put("as", asMap);
					chList2.add(chMap);
				}
				qt.setQtAsCnt(qtAsCnt);
				qtMap.put("chList2", chList2);
			}
			if(qt.getQtType()==0){
				vo.setQtIdx(qt.getQtIdx());
				List<StatsVO> asList = statsMapper.selectStatFeedbackAnswerList(vo);
				qtMap.put("asList", asList);
			}
		}
		
		return feedbackInfo;
	}

	@Override
	public ResultVO getStatLctrePageList(StatsVO vo, int listRow) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			//총 갯수
			int totalCnt = statsMapper.selectStatLctrePageCnt(vo);
			
			vo.setRow(listRow);
			
			//페이징처리
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
	        paginationInfo.setCurrentPageNo(vo.getPage());
	        paginationInfo.setRecordCountPerPage(vo.getRow());
	        paginationInfo.setPageSize(10);
	        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			
			//개인신청명단
			List<StatsVO> pageList = statsMapper.selectStatLctrePageList(vo);
			rstData.put("pageList", pageList);
			
			rstData.put("pageNavi",paginationInfo);
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}
	
	@Override
	public ResultVO getStatCtgryList(StatsVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			List<StatsVO> pageList = statsMapper.selectStatCtgryList(vo);
			rstData.put("pageList", pageList);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getStatInstrctrList(StatsVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			List<StatsVO> pageList = statsMapper.selectStatInstrctr1List(vo);
			rstData.put("pageList", pageList);
			List<StatsVO> pageList2 = statsMapper.selectStatInstrctr2List(vo);
			rstData.put("pageList2", pageList2);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getStatRoomList(StatsVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			List<StatsVO> pageList = statsMapper.selectStatRoomList(vo);
				
			//검색일수
			int srchTotalDay = DateUtil.calDays(vo.getSrchStartDay().replaceAll("-", ""), vo.getSrchEndDay().replaceAll("-", ""));
			
			//강의실 목록조회
			LectureRoomVO roomVO = new LectureRoomVO();
			List<LectureRoomVO> roomList = lectureRoomMapper.selectByParam(roomVO);
			
			//재설정
			List<StatsVO> pageList2 = new ArrayList<StatsVO>();
			for(LectureRoomVO o: roomList){
				StatsVO stat = new StatsVO();
				stat.setRoomNm(o.getRoomNm());
				pageList2.add(stat);
				for(StatsVO o2: pageList ){
					if(o.getRoomSeq() == o2.getRoomSeq()){
						//stat.setRoomNm(o2.getRoomNm());
						stat.setEduPeriodBegin(o2.getEduPeriodBegin());
						stat.setEduPeriodEnd(o2.getEduPeriodEnd());
						int totalCnt = DateUtil.calDays(stat.getEduPeriodBegin(), stat.getEduPeriodEnd());
						stat.setTotalCnt(totalCnt);
						break;
					}
				}
			}
			
			rstData.put("pageList", pageList2);
			rstData.put("srchTotalDay",srchTotalDay);
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}

}

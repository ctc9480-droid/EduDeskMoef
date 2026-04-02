package com.educare.edu.feedback.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.educare.edu.comn.mapper.FeedbackEduMapper;
import com.educare.edu.comn.model.FeedbackEdu;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackEduService;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.DateUtil;

@Service("FeedbackEduService")
public class FeedbackEduServiceImpl implements FeedbackEduService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(FeedbackEduServiceImpl.class.getName());

	@Resource(name="FeedbackEduMapper")
	private FeedbackEduMapper feedbackEduMapper;
	@Resource(name="FeedbackMapper")
	private FeedbackMapper feedbackMapper;
	@Resource(name="FeedbackService")
	private FeedbackService feedbackService;
	
	@Override
	public ResultVO getFeedbackEduMap(int feSeq, int eduSeq,String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//설문지 조회
			FeedbackEdu fe = feedbackEduMapper.selectByPk(feSeq);
			rstData.put("fe", fe);
			
			int fbIdx = fe.getFbIdx();
			
			//문항가져오기
			Map<String, Object> feedbackInfo = feedbackService.getFeedbackInfo(fbIdx);
			
			//답변가져오기
			if(userId!=null){
				feedbackService.setAnswer(feedbackInfo,userId,eduSeq,feSeq);
			}
			
			rstData.put("feedbackInfo",feedbackInfo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO saveFeedbackEduMap(int feSeq, int eduSeq, int fbIdx ,String fbNm, String startDeStr, String endDeStr, int state) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//tmp
			
			//밸리데이션
			if(fbIdx == 0){
				result.setMsg("설문지가 제대로 설정되지 않았습니다. 관리자에게 문의하세요"); result.setResult(0);return result;
			}
			
			if(startDeStr.length()!=12 || endDeStr.length()!=12){
				result.setMsg("설문기간을 입력하셔야 합니다."); result.setResult(0);return result;
			}
			
			String userId = SessionUserInfoHelper.getUserId();
			Date nowDe = new Date();
			
			//set
			Date startDe = DateUtil.getStr2Date(startDeStr, "yyyyMMddHHmm");
			Date endDe = DateUtil.getStr2Date(endDeStr, "yyyyMMddHHmm");
			String regId = userId;
			String updId = userId;
			Date regDe = nowDe;
			Date updDe = nowDe;
			
			//저장
			FeedbackEdu vo = new FeedbackEdu(feSeq, eduSeq, fbIdx, fbNm, startDe, endDe, state, regId, updId, regDe, updDe);
			if(feSeq > 0){
				feedbackEduMapper.updateByPk(vo);
			}else{
				feedbackEduMapper.insertByPk(vo);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getFeedbackEduList(String userId, int eduSeq, int rowCnt, int pageNo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			FeedbackVO vo = new FeedbackVO();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			/*
			int totalCnt = feedbackMapper.selectFeedbackEduPageCnt(vo);
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
			paginationInfo.setCurrentPageNo(pageNo);
			paginationInfo.setRecordCountPerPage(rowCnt);
			paginationInfo.setPageSize(10);
			vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			if(rowCnt>0){
			}
			*/
//			FeedbackVO fb = feedbackMapper.selectFeedback(vo);
//			rstData.put("fb", fb);
			
			vo.setRowCnt(rowCnt);
			List<FeedbackVO> list = feedbackMapper.selectFeedbackEduPageList(vo);
			//rstData.put("pageNavi",paginationInfo);
			rstData.put("feList", list);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	
}

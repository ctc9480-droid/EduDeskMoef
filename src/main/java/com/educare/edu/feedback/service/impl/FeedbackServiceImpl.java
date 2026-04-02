package com.educare.edu.feedback.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.mapper.CheckMapper;
import com.educare.edu.comn.mapper.FeedbackAnswerMapper;
import com.educare.edu.comn.mapper.FeedbackEduMapper;
import com.educare.edu.comn.mapper.FeedbackEduResultMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackEdu;
import com.educare.edu.comn.model.FeedbackEduResult;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.Test;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.quizTest.vo.QuizTestVO;
import com.educare.util.LncUtil;

@Service("FeedbackService")
public class FeedbackServiceImpl implements FeedbackService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(FeedbackServiceImpl.class.getName());

	@Resource(name="FeedbackMapper")
	private FeedbackMapper feedbackMapper;
	@Resource(name="FeedbackAnswerMapper")
	private FeedbackAnswerMapper feedbackAnswerMapper;
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	@Resource(name="CheckService") 
	private CheckService checkService;
	@Resource(name="LectureMapper") 
	private LectureMapper lectureMapper;
	@Resource(name="FeedbackEduResultMapper") 
	private FeedbackEduResultMapper feedbackEduResultMapper;
	@Resource(name="FeedbackEduMapper") 
	private FeedbackEduMapper feedbackEduMapper;

	@Override
	public List<FeedbackVO> getfeedbackList(String orgCd,String userId,String srchWrd) {
		FeedbackVO param = new FeedbackVO();
		param.setOrgCd(orgCd);
		param.setSrchWrd(srchWrd);
		
		//모든 설문조사 출력으로 변경,231011
		//param.setRegId(userId);
		//if(SessionUserInfoHelper.getUserInfo().getUserMemLvl().equals("2")) {
		//	param.setRegId(null);
		//}
		List<FeedbackVO> list = feedbackMapper.getFeedbackList(param);
		return list;
	}

	@Override
	public Map<String, Object> getFeedbackInfo(int idx) {
		
		FeedbackVO param = new FeedbackVO();
		param.setIdx(idx);
		FeedbackVO map = feedbackMapper.getFeedbackMap(param);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("fb", map);
		
		map.setFbIdx(idx);
		List<FeedbackVO> qtList = feedbackMapper.selectFeedbackQuestionByFbIdx(map);
		List<Map<String, Object>> qtList2 = new ArrayList<Map<String, Object>>();
		map2.put("qtList", qtList2);
		
		for(FeedbackVO qtMap:qtList){
			Map<String, Object> qtMap2 = new HashMap<String, Object>();
			qtMap2.put("qt", qtMap);
			List<FeedbackVO> chList = feedbackMapper.selectFeedbackChoiceByFbIdxQtIdx(qtMap);
			qtMap2.put("chList", chList);
			qtList2.add(qtMap2);
		}
		
		return map2;
	}

	@Override
	public int saveFeedback(String orgCd, Map<String, Object> param) {
		try {
			int idx = 0;
			
			Map<String, Object> fb = (Map<String, Object>) param.get("fb");
			
			if(ObjectUtils.isEmpty(fb.get("title"))){
				return 0;
			}
			
			if(!StringUtils.isEmpty(fb.get("idx"))){
				idx = Integer.valueOf(fb.get("idx").toString());
			}
			
			//설문지 정보 저장
			Feedback feedback = new Feedback(
					idx
					,orgCd
					, fb.get("title").toString()
					, 0
					, Calendar.getInstance().getTime()
					, SessionUserInfoHelper.getUserId()
					, SessionUserInfoHelper.getUserNm()
					, Calendar.getInstance().getTime()
					, SessionUserInfoHelper.getUserId()
					, fb.get("content").toString()
					);
			if(idx>0){
				//수정 가능한 상태인지 체크
				int check = checkService.checkUpdFeedback(idx,orgCd);
				if(check!=1){
					return check;
				}
				
				feedbackMapper.updateFeedback(feedback);
			}else{
				feedbackMapper.insertFeedback(feedback);
			}
			
			//질문,보기 모두 삭제
			feedbackMapper.deleteFeedbackChoiceByFbIdx(feedback.getIdx());
			feedbackMapper.deleteFeedbackQuestionByFbIdx(feedback.getIdx());
			
			//질문 저장
			int fbIdx = feedback.getIdx();
			List<Map<String, Object>> qtList = (List<Map<String, Object>>) param.get("qtList");
			int qtIdx = 0;
			for(Map<String, Object> qtMap:qtList){
				qtIdx++;
				int qtType = Integer.valueOf(((Map<String, Object>) qtMap.get("qt")).get("qtType").toString());
				String question = ((Map<String, Object>) qtMap.get("qt")).get("question").toString();
				String qtDiv = LncUtil.replaceNull(((Map<String, Object>)qtMap.get("qt")).get("qtDiv"));
				String essntlYn = LncUtil.replaceNull(((Map<String, Object>)qtMap.get("qt")).get("essntlYn"));
				FeedbackQuestion feedbackQuestion = new FeedbackQuestion(
						fbIdx
						, qtIdx
						, qtType
						, question
						, qtDiv
						, essntlYn
						);
				feedbackMapper.insertFeedbackQuestion(feedbackQuestion);
				
				//보기저장
				List<Map<String, Object>> chList = (List<Map<String, Object>>) qtMap.get("chList");
				int chIdx = 0;
				for(Map<String, Object> chMap:chList){
					chIdx++;
					String choice = chMap.get("choice").toString();
					int point  = LncUtil.nvlInt(chMap.get("point"));
					FeedbackChoice feedbackChoice = new FeedbackChoice(
							fbIdx
							, qtIdx
							, chIdx
							, choice
							,point
							);
					feedbackMapper.insertFeedbackChoice(feedbackChoice);
				}
			}
			
			return 1;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
	}

	@Override
	public int deleteFeedback(String orgCd, int fbIdx) {
		try {
			UserInfo sess = SessionUserInfoHelper.getUserInfo();
			if(sess == null){
				return 0;
			}
			String userId = sess.getUserId();
			if(userId == null){
				return 0;
			}
			
			int check = checkService.checkUpdFeedback(fbIdx,orgCd);
			if(check!=1){
				return check;
			}
			
			//본인 작성 확인
			FeedbackVO feedback = feedbackMapper.selectOneFeedbackByFbIdx(fbIdx);
			if(!userId.equals(feedback.getRegId())){
				return 3;
			}
			
			feedbackMapper.deleteFeedbackChoiceByFbIdx(fbIdx);
			feedbackMapper.deleteFeedbackQuestionByFbIdx(fbIdx);
			feedbackMapper.deleteFeedback(fbIdx);
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
		
	}

	@Override
	public void setResult(Map<String, Object> feedbackInfo,int eduSeq,int feSeq) {
			List<Map<String, Object>> qtList =  (List<Map<String, Object>>) feedbackInfo.get("qtList");
			for(Map<String, Object> qtMap:qtList){
				FeedbackVO qt = (FeedbackVO) qtMap.get("qt");
				if(qt.getQtType()!=0){
					List<FeedbackVO> chList =  (List<FeedbackVO>) qtMap.get("chList");
					int qtAsCnt = 0;
					for(FeedbackVO feedbackChoice:chList){
						feedbackChoice.setEduSeq(eduSeq);
						feedbackChoice.setFeSeq(feSeq);
						FeedbackVO asMap = feedbackMapper.selectFeedbackAnswerByChIdxCnt(feedbackChoice);
						if(!ObjectUtils.isEmpty(asMap)){
							feedbackChoice.setAsCnt(asMap.getAsCnt());
							qtAsCnt+=asMap.getAsCnt();
						}
					}
					qt.setQtAsCnt(qtAsCnt);
				}
				if(qt.getQtType()==0){
					qt.setEduSeq(eduSeq);
					qt.setFeSeq(feSeq);
					List<FeedbackVO> asList = feedbackMapper.selectFeedbackAnswerByFbIdxQtIdx(qt);
					qtMap.put("asList", asList);
				}
			}
		
	}
	@Override
	public void setResultAll(Map<String, Object> feedbackInfo,List<Integer> eduSeqArr) {
		List<Map<String, Object>> qtList =  (List<Map<String, Object>>) feedbackInfo.get("qtList");
		for(Map<String, Object> qtMap:qtList){
			FeedbackVO qt = (FeedbackVO) qtMap.get("qt");
			if(qt.getQtType()!=0){
				List<FeedbackVO> chList =  (List<FeedbackVO>) qtMap.get("chList");
				int qtAsCnt = 0;
				for(FeedbackVO feedbackChoice:chList){
					
					feedbackChoice.setEduSeqArr(eduSeqArr);//선택한 수업만 답변조회
					
					FeedbackVO asMap = feedbackMapper.selectFeedbackAnswerByChIdxCntAll(feedbackChoice);
					if(!ObjectUtils.isEmpty(asMap)){
						feedbackChoice.setAsCnt(asMap.getAsCnt());
						qtAsCnt+=asMap.getAsCnt();
					}
				}
				qt.setQtAsCnt(qtAsCnt);
			}
			if(qt.getQtType()==0){
				qt.setEduSeqArr(eduSeqArr);//선택한 수업만 답변조회
				List<FeedbackVO> asList = feedbackMapper.selectFeedbackAnswerByFbIdxQtIdxAll(qt);
				qtMap.put("asList", asList);
			}
		}
			
	}
	@Override
	public void setAnswer(Map<String, Object> feedbackInfo,String userId,int eduSeq,int feSeq) {
			List<Map<String, Object>> qtList =  (List<Map<String, Object>>) feedbackInfo.get("qtList");
			for(Map<String, Object> qtMap:qtList){
				FeedbackVO qt = (FeedbackVO) qtMap.get("qt");
				//다중답안도 한꺼번에 가져오기위해 리스트로 조회
				FeedbackAnswer param = new FeedbackAnswer();
				param.setEduSeq(eduSeq);
				param.setFbIdx(qt.getFbIdx());
				param.setQtIdx(qt.getQtIdx());
				param.setFeSeq(feSeq);
				param.setUserId(userId);
				List<FeedbackAnswer> asList = feedbackAnswerMapper.selectBy_FbIdx_QtIdx_UserIdx(param);
				
				if(!ObjectUtils.isEmpty(asList)){
					for(FeedbackAnswer as:asList){
						if(qt.getQtType()==2){
							qt.getAsChIdx2().add(as.getChoice());
						}else{
							qt.setAsChIdx(as.getChoice());
							qt.setAnswer(as.getAnswer());
						}
					}
				}
			}
	}

	@Override
	public int saveFeedbackAnswer(Map<String, Object> param) {
		try {
			if(StringUtils.isEmpty(((Map<String, Object>) param.get("fb")).get("idx"))){
				return 0;
			}
			
			int eduSeq = Integer.parseInt(param.get("eduSeq").toString());
			int feSeq = LncUtil.nvlInt(param.get("feSeq"));//기본은 0,240614
			
			if(eduSeq == 0){
				return 0;
			}
			
			//설문지번호
			int fbIdx = Integer.valueOf(((Map<String, Object>) param.get("fb")).get("idx").toString());
			
			String userId = SessionUserInfoHelper.getUserId();
			
			FeedbackAnswer param3 = new FeedbackAnswer();
			param3.setEduSeq(eduSeq);
			param3.setFbIdx(fbIdx);
			param3.setFeSeq(feSeq);
			param3.setUserId(userId);
			feedbackAnswerMapper.deleteByFbIdxUserId(param3);
			List<Map<String, Object>> qtList = (List<Map<String, Object>>) param.get("qtList");
			for(Map<String, Object> qtMap:qtList){
				Map<String, Object> qt = ((Map<String, Object>) qtMap.get("qt"));
				
				int qtIdx = Integer.valueOf(qt.get("qtIdx").toString());
				String answer = qt.get("answer")!=null?qt.get("answer").toString():null;
				int choice = qt.get("asChIdx")!=null?Integer.valueOf(qt.get("asChIdx").toString()):0;
				int qtType = Integer.valueOf(qt.get("qtType").toString());
				
				FeedbackAnswer param2 = new FeedbackAnswer(eduSeq,feSeq,fbIdx, qtIdx, userId, answer, choice, Calendar.getInstance().getTime());
				if(qtType!=2){//다중객관식이 아닌경우
					feedbackAnswerMapper.insertByPk(param2);
				}else{//다중객관식인경우
					List<Integer> choice2 = qt.get("asChIdx2")!=null?(List<Integer>) qt.get("asChIdx2"):null;
					for(int asChIdx:choice2){
						int choice3=asChIdx;
						param2.setChoice(choice3);
						feedbackAnswerMapper.insertByPk(param2);
					}
				}
			}
			
			//객관식 전부 입력했는지 검증
			int noAnswerCnt = feedbackMapper.getFeedbackAnswerCheck(param3);
			if(noAnswerCnt>0){
				return 2;//전부 입력 안됨
			}
			
			if(feSeq == 0){
				LectureStdnt stdnt = new LectureStdnt();
				stdnt.setEduSeq(eduSeq);
				stdnt.setUserId(SessionUserInfoHelper.getUserId());
				stdnt.setSurveyYn(LectureStdnt.STR_Y);
				eduMapper.updateLectureStdntSurvey(stdnt);	
			}else{
				FeedbackEduResult vo = new FeedbackEduResult();
				vo.setFeSeq(feSeq);
				vo.setUserId(userId);
				vo.setEduSeq(eduSeq);
				vo.setState(2);
				vo.setRegDe(new Date());
				vo.setRegId(userId);
				vo.setUpdDe(new Date());
				vo.setUpdId(userId);
				FeedbackEduResult feResult = feedbackEduResultMapper.selectByPk(vo);
				if(ObjectUtils.isEmpty(feResult)){
					feedbackEduResultMapper.insertByPk(vo);
				}else{
					feedbackEduResultMapper.updateByPk(vo);
				}
			}
			
			return 1;
			
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public int getFbIdxByEduSeqArr(List<Integer> eduSeqChk) {
		List<Integer> fbIdxList = lectureMapper.selectFbIdxByEduSeqArr(eduSeqChk);
		if(ObjectUtils.isEmpty(fbIdxList)){
			return 0;
		}
		
		int fbIdx = fbIdxList.get(0);
		for(Integer fbIdx2 : fbIdxList){
			if(fbIdx!=fbIdx2){
				return 0;
			}
		}
		
		return fbIdx;
	}

	@Override
	public ResultVO checkOpenFeedback(int feSeq,String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			
			//학생 결과지 조회
			FeedbackEduResult vo = new FeedbackEduResult();
			vo.setFeSeq(feSeq);
			vo.setUserId(userId);
			FeedbackEduResult o = feedbackEduResultMapper.selectByPk(vo);
			if(o != null){
				if(o.getState() == 2){
					result.setResult(1);
					return result;
				}
			}
			
			FeedbackEdu fe = feedbackEduMapper.selectByPk(feSeq);
			Date now = new Date();
			if(fe.getStartDe().compareTo(now) > 0 || fe.getEndDe().compareTo(now) < 0){
				result.setMsg("아직 설문기간이 아닙니다.");
				result.setResult(0);
				return result;
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getFeedbackPageList(int pageNo, int rowCnt, String srchWrd) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			FeedbackVO vo = new FeedbackVO();
			vo.setSrchWrd(srchWrd);
			
			//전체 수
			int totalCnt = feedbackMapper.selectFeedbackPageCnt(vo);
			
			//페이징설정
			PaginationInfo page = new PaginationInfo();
			page.setTotalRecordCount(totalCnt);
			page.setCurrentPageNo(pageNo);
			page.setRecordCountPerPage(rowCnt);
			page.setPageSize(10);
	        vo.setRowCnt(rowCnt);
	        vo.setFirstIndex(page.getFirstRecordIndex());
			
			//목록
			List<QuizTestVO> list = feedbackMapper.selectFeedbackPageList(vo);
			
			rstData.put("list", list);
			rstData.put("pageNavi", page);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
}

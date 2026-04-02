package com.educare.edu.quizTest.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.LectureSubjectMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.mapper.TestAnswerMapper;
import com.educare.edu.comn.mapper.TestMapper;
import com.educare.edu.comn.mapper.TestQuestionMapper;
import com.educare.edu.comn.mapper.TestResultMapper;
import com.educare.edu.comn.mapper.TestTmplMapper;
import com.educare.edu.comn.model.LectureSubject;
import com.educare.edu.comn.model.Test;
import com.educare.edu.comn.model.TestAnswer;
import com.educare.edu.comn.model.TestQuestion;
import com.educare.edu.comn.model.TestResult;
import com.educare.edu.comn.model.TestTmpl;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.quizBank.mapper.QuizBankMapper;
import com.educare.edu.quizTest.mapper.QuizTestMapper;
import com.educare.edu.quizTest.service.QuizTestService;
import com.educare.edu.quizTest.vo.QuizTestVO;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("QuizTestService")
public class QuizTestServiceImpl implements QuizTestService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(QuizTestServiceImpl.class.getName());

	@Resource(name = "QuizTestMapper")
	private QuizTestMapper quizTestMapper;
	@Resource(name = "QuizBankMapper")
	private QuizBankMapper quizBankMapper;
	@Resource(name = "TestTmplMapper")
	private TestTmplMapper testTmplMapper;
	@Resource(name = "TestQuestionMapper")
	private TestQuestionMapper testQuestionMapper;
	@Resource(name = "TestMapper")
	private TestMapper testMapper;
	
	@Resource(name = "LectureStdntMapper")
	private LectureStdntMapper lectureStdntMapper;
	@Resource(name = "TestAnswerMapper")
	private TestAnswerMapper testAnswerMapper;
	@Resource(name = "TestResultMapper")
	private TestResultMapper testResultMapper;
	@Resource(name = "LectureSubjectMapper")
	private LectureSubjectMapper lectureSubjectMapper;

	@Override
	public ResultVO getQuizTestInfo(int testTmplSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			TestTmpl vo = new TestTmpl();
			vo.setTestTmplSeq(testTmplSeq);
			
			//시험지가져오기
			TestTmpl tmpl = testTmplMapper.selectByPk(vo);
			if(tmpl == null){
				tmpl = new TestTmpl();
			}
			rstData.put("tmpl", tmpl);
			
			//저장된 문제가져오기
			QuizTestVO testVO = new QuizTestVO();
			testVO.setTestTmplSeq(testTmplSeq);
			List<QuizTestVO> tqList = quizTestMapper.selectTestQstnByTestTmpl(testVO);
			rstData.put("tqList", tqList);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO saveQuizTest(String userId, int testTmplSeq, String testTmplNm, int testTp, int ctg1Seq, int ctg2Seq, int ctg3Seq, int timeLimit, int markTp, int lookTp, int ordTp, int selectTp, int choiceTp,
			int lookCnt, String totQstn, String selectQstn, String descr, Double totMarks, int status) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			
			//널체크
			if(LncUtil.checkEmptyStrs(testTmplNm,descr) == 0){
				result.setMsg("공백이 존재함");
				result.setResult(0);
				return result;
			}
			
			String regId = userId;
			String updId = userId;
			Date regDe = DateUtil.getCalendar().getTime();
			Date updDe = DateUtil.getCalendar().getTime();
			
			//문제저장
			TestTmpl tmplVO = new TestTmpl(testTmplNm, testTp, ctg1Seq, ctg2Seq, ctg3Seq
					, timeLimit, markTp, lookTp, ordTp, selectTp, choiceTp, lookCnt, totQstn, selectQstn, descr, totMarks, status
					, regId, regDe, updId, updDe);
			if(testTmplSeq>0){
				//수정
				tmplVO.setTestTmplSeq(testTmplSeq);
				testTmplMapper.updateByPk(tmplVO);
			}else{
				//저장
				testTmplMapper.insertByPk(tmplVO);
			}
			
			//문제저장처리
			
			
			rstData.put("testTmplSeq", tmplVO.getTestTmplSeq());
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}


	@Override
	public ResultVO getQuizTestPageList(int pageNo,int rowCnt,String srchWrd,int srchCtg1Seq,int srchCtg2Seq,int srchCtg3Seq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			QuizTestVO vo = new QuizTestVO();
			vo.setSrchWrd(srchWrd);
			vo.setSrchCtg1Seq(srchCtg1Seq);
			vo.setSrchCtg2Seq(srchCtg2Seq);
			vo.setSrchCtg3Seq(srchCtg3Seq);
			
			//전체 수
			int totalCnt = quizTestMapper.selectQuizTestPageCnt(vo);
			
			//페이징설정
			PaginationInfo page = new PaginationInfo();
			page.setTotalRecordCount(totalCnt);
			page.setCurrentPageNo(pageNo);
			page.setRecordCountPerPage(rowCnt);
			page.setPageSize(10);
	        vo.setRowCnt(rowCnt);
	        vo.setFirstIndex(page.getFirstRecordIndex());
			
			//목록
			List<QuizTestVO> list = quizTestMapper.selectQuizTestPageList(vo);
			
			rstData.put("list", list);
			rstData.put("pageNavi", page);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO saveQuizTestAddQstn(String userId, int testTmplSeq, int[] qstnSeqArr) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			String updId = userId;
			Date updDe = new Date();
			Double marks = 0.0;
			int qstnOrd = 0;
			
			TestQuestion vo = new TestQuestion(testTmplSeq, 0, qstnOrd, marks, updId, updDe);
			for(int qstnSeq : qstnSeqArr){
				vo.setQstnSeq(qstnSeq);
				TestQuestion tq = testQuestionMapper.selectByUk1(vo);
				if(tq!=null){
					result.setMsg("이미 추가된 문제입니다. ");
					result.setResult(0);
					return result;
				}
				testQuestionMapper.insertByPk(vo);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO delQuizTestAddQstn(String userId, int tqSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//삭제가능체크
			
			//삭제
			testQuestionMapper.deleteByPk(tqSeq);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getTestEduMap(int testSeq,int eduSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//시험지 조회
			Test test = testMapper.selectByPk(testSeq);
			rstData.put("test", test);
			
			//템플릿조회
			TestTmpl vo = new TestTmpl();
			vo.setTestTmplSeq(test.getTestTmplSeq());
			TestTmpl tmpl = testTmplMapper.selectByPk(vo);
			rstData.put("tmpl", tmpl);
			
			//재평가 시험지 존재 여부
			int test2Cnt = testMapper.selectCntByPrntTestSeq(test.getTestSeq());
			rstData.put("test2Cnt", test2Cnt);
			
			
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getTestTmplList(int eduSeq,String srchWrd,int srchCtg1Seq,int srchCtg2Seq,int srchCtg3Seq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//시험지 조회
			QuizTestVO vo = new QuizTestVO();
			vo.setStatus(1);
			vo.setEduSeq(eduSeq);
			vo.setSrchWrd(srchWrd);
			vo.setSrchCtg1Seq(srchCtg1Seq);
			vo.setSrchCtg2Seq(srchCtg2Seq);
			vo.setSrchCtg3Seq(srchCtg3Seq);
			List<QuizTestVO> testTmplList = quizTestMapper.selectQuizTestPageList(vo);
			rstData.put("testTmplList", testTmplList);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO saveTestEduMap(int testSeq, int eduSeq, int testTmplSeq, String testNm, String startDeStr, String endDeStr, int status,int timer,double passMarks,int markTp
			,int tryNo,int prntTestSeq,int subSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//tmp
			int bfafTp = 1;
			String descr = "";
			
			//밸리데이션
			if(ObjectUtils.isEmpty(testNm)){
				result.setMsg("시험지명을 등록하셔야합니다."); result.setResult(0);return result;
			}
			if(startDeStr.length()!=12 || endDeStr.length()!=12){
				result.setMsg("시험기간을 입력하셔야 합니다."); result.setResult(0);return result;
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
			Test vo = new Test(testTmplSeq, eduSeq, testNm, bfafTp, startDe, endDe, descr, passMarks, status, timer, markTp, tryNo, prntTestSeq, subSeq, regId, updId, regDe, updDe);
			if(testSeq > 0){
				//시험지 정보 조회
				Test test = testMapper.selectByPk(testSeq);
				
				vo.setTestSeq(testSeq);
				testMapper.updateByPk(vo);
			}else{
				if(tryNo > 1) {
					vo.setStatus(1);//재평가인경우 사용으로 변경
				}
				testMapper.insertByPk(vo);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getTestEduList(String userId,int eduSeq,int rowCnt,int pageNo,int status) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			QuizTestVO vo = new QuizTestVO();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			vo.setStatus(status);
			int totalCnt = quizTestMapper.selectTestEduPageCnt(vo);
			
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
			paginationInfo.setCurrentPageNo(pageNo);
			paginationInfo.setRecordCountPerPage(rowCnt);
			paginationInfo.setPageSize(10);
			vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			
			
			vo.setRowCnt(rowCnt);
			List<QuizTestVO> list = quizTestMapper.selectTestEduPageList(vo);
			
			//rstData.put("pageNavi",paginationInfo);
			rstData.put("testList", list);
			
			//과목조회
			List<LectureSubject> subList = lectureSubjectMapper.selectLctreTimeSubList(eduSeq);
			rstData.put("subList", subList);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getTestEduQstnList(int eduSeq, int testSeq, String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//본인수업 체크
			LectureStdnt ltsVO = new LectureStdnt();
			ltsVO.setEduSeq(eduSeq);
			ltsVO.setUserId(userId);
			LectureStdnt stdnt = lectureStdntMapper.selectByPk(ltsVO);
			if(stdnt == null){
				result.setMsg("수강생이 아닙니다.");
				result.setResult(0);
				return result;
			}
			
			//수강생 답변지 수 조회
			TestAnswer taVO = new TestAnswer();
			taVO.setTestSeq(testSeq);
			taVO.setUserId(userId);
			int cnt = testAnswerMapper.selectCntByUserId(taVO);
			
			//시험정보 조회
			QuizTestVO qtVO = new QuizTestVO();
			qtVO.setTestSeq(testSeq);
			qtVO.setUserId(userId);
			QuizTestVO test = quizTestMapper.selectTestEdu(qtVO);
			if(test == null){
				result.setMsg("올바른 시험지가 아닙니다.");
				result.setResult(0);
				return result;
			}
			
			if(test.getQstnCnt() != cnt){
				//랜덤 배열값
				int[] randomArr = LncUtil.getRandomNumList(test.getQstnCnt());
				
				//수강생 답변지 생성
				List<QuizTestVO> tqList = quizTestMapper.selectTestQstnByTestTmpl(test);
				int r1 = testAnswerMapper.deleteByUserId(taVO);
				TestAnswer vo = new TestAnswer();
				vo.setTestSeq(testSeq);
				vo.setUserId(userId);
				vo.setRegDe(new Date());
				vo.setRegId(userId);
				vo.setUpdDe(new Date());
				vo.setUpdId(userId);
				vo.setAnsweredYn("N");
				for(int i=0; i<tqList.size(); i++){
					QuizTestVO o = tqList.get(i);
					vo.setTqSeq(o.getTqSeq());
					vo.setTestTmplSeq(o.getTestTmplSeq());
					vo.setOrd(i+1);
					if(test.getOrdTp() == 2){
						vo.setOrd(randomArr[i]);//문제 순번 랜덤값 설정
					}
					
					int chCnt = o.getChCnt();//보기갯수
					int[] chOrdArr = LncUtil.generateRandomArray(chCnt);
					ObjectMapper om = new ObjectMapper();
					String chOrd = om.writeValueAsString(chOrdArr);
					vo.setChOrd(chOrd);//보기랜덤배열 저장
					
					//답안지 저장
					testAnswerMapper.insertByPk(vo);
				}
			}
			
			//문제목록 조회
			List<QuizTestVO> userQtList = quizTestMapper.selectTestUserQtList(qtVO);
			
			//전체보기 조회
			qtVO.setTestTmplSeq(test.getTestTmplSeq());
			List<QuizTestVO> userChList = quizTestMapper.selectTestUserChList(qtVO);
			
			//보기세팅 및 객체 재설정
			List<Map<String , Object>> userQtList2 = new ArrayList<Map<String,Object>>();//새로운 문제목록
			for(QuizTestVO qt: userQtList){
				Map<String , Object> o2 = new HashMap<String,Object>();//새로운 문제정보
				o2.put("qt", qt);
				
				List<QuizTestVO> userChList2 = new ArrayList<QuizTestVO>();//새로운 보기목록
				for(QuizTestVO ch: userChList){
					if(qt.getQstnSeq() == ch.getQstnSeq()){
						userChList2.add(ch);
						continue;
					}
				}
				
				//랜덤보기인경우 
				if(test.getChoiceTp() == 2){
					if(qt.getQstnTp() == 1 || qt.getQstnTp() == 2){
						LOG.info(qt.getChOrd());
						String[] ordArray = qt.getChOrd().replaceAll("[\\[\\]]", "").split(",");
						// Convert to list of integers
						List<Integer> ordList = new ArrayList<>();
						for (String s : ordArray) {
							LOG.info(s);
							ordList.add(Integer.parseInt(s.trim()));
						}
						userChList2.sort(Comparator.comparingInt(vo -> ordList.indexOf(vo.getChSeq())));
					}
				}
				o2.put("chList", userChList2);
				userQtList2.add(o2);
			}
			
			rstData.put("userQtList", userQtList2);
			rstData.put("userTest", test);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | IOException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO saveTestQstnAnswer(int eduSeq, int testSeq, int tqSeq, String userId,int qstnTp, String optn, String answer, String fillBlank) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String updId = SessionUserInfoHelper.getUserId();
			
			//제한시간 가져오기
			Test test = testMapper.selectByPk(testSeq);
			int timer = test.getTimer();
			long timerSec = (timer * 60);
			
			//시험시작후 지난 시간 계산하기 ,초단위
			TestResult vo = new TestResult();
			vo.setTestSeq(testSeq);
			vo.setUserId(userId);
			TestResult testResult = testResultMapper.selectByPk(vo);
			Date startDe = testResult.getStartDe();
			long diffSec = DateUtil.calSec(startDe, new Date());
			
			if(test.getTimer() > 0 && timerSec < diffSec){
				result.setMsg("응시 제한시간이 지났습니다. 더이상 답변을 제출하실 수 없습니다.");
				result.setResult(0);
				return result;
			}
			
			//유호성체크
			TestAnswer taVO = new TestAnswer();
			taVO.setTestSeq(testSeq);
			taVO.setTqSeq(tqSeq);
			taVO.setUserId(userId);
			taVO.setOptn(optn);
			taVO.setAnswer(answer);
			taVO.setFillBlank(fillBlank);
			taVO.setAnsweredYn("Y");
			taVO.setUpdId(updId);
			taVO.setUpdDe(new Date());
			TestAnswer ans = testAnswerMapper.selectByPk(taVO);
			if(ans == null){
				result.setMsg("시험지를 다시 실행하시기 바랍니다.");
				result.setResult(0);
				return result;
			}
			
			//답안이 변한게 없다면 중지
			if(qstnTp == 1 || qstnTp == 2){
				LOG.debug("optn : "+optn);
				if("[]".equals(optn)){
					taVO.setAnsweredYn("N");
				}else if(optn.equals(ans.getOptn())){
					result.setMsg("답변 동일함");
					result.setResult(1);
					return result;
				}
				
			}else if(qstnTp == 3){
				if(StringUtils.isEmpty(fillBlank)){
					taVO.setAnsweredYn("N");
				}else if(fillBlank.equals(ans.getFillBlank())){
					result.setMsg("답변 동일함");
					result.setResult(1);
					return result;
				}
				
			}else if(qstnTp == 4){
				if(StringUtils.isEmpty(answer)){
					taVO.setAnsweredYn("N");
				}else if(answer.equals(ans.getAnswer())){
					result.setMsg("답변 동일함");
					result.setResult(1);
					return result;
				}
				
			}
			
			//정답여부 체크하기
			QuizTestVO qtVO = new QuizTestVO();
			qtVO.setTestSeq(testSeq);
			qtVO.setUserId(userId);
			qtVO.setTqSeq(tqSeq);
			List<QuizTestVO> qtList = quizTestMapper.selectTestUserQtList(qtVO);
			QuizTestVO qt = qtList.get(0);
			boolean isCorrect = false;//정답여부
			if(qt.getQstnTp() == 1 || qt.getQstnTp() == 2){//객관식
				//if(qt.getOptn() == null){
					//continue;
				//}
				Pattern pattern = Pattern.compile("\\[\\s*\\d+(\\s*,\\s*\\d+)*\\s*\\]");
				if(!pattern.matcher(optn).matches()){//빈값이면
					result.setMsg("에러1");
					result.setResult(0);
					return result;
				}
				//답안 json 파싱
				ObjectMapper om = new ObjectMapper();
				int[] optnArr = om.readValue(optn, int[].class);
				//보기목록
				qtVO.setTestTmplSeq(test.getTestTmplSeq());
				qtVO.setChAnsYn("Y");
				qtVO.setTqSeq(tqSeq);
				List<QuizTestVO> chList = quizTestMapper.selectTestUserChList(qtVO);
				
				int ansCnt = 0;
				for(QuizTestVO o2: chList){//객관식목록
					if(qt.getTqSeq()==o2.getTqSeq() && "Y".equals(o2.getChAnsYn())){//해당문제의 정답보기만 필터
						ansCnt++;
						isCorrect = false;
						LOG.debug("정답 : "+o2.getChSeq());
						for(int ansChSeq: optnArr){//나의 답
							if(ansChSeq == o2.getChSeq()){
								LOG.debug("correct!!");
								isCorrect = true;
							}
						}
					}
				}
				if(isCorrect){
					if(ansCnt != optnArr.length){
						isCorrect = false;
					}
				}
			}else if(qt.getQstnTp() == 3){//단답형
				if(fillBlank.equals(qt.getFillBlankCo())){
					isCorrect = true;
				}
			}else if(qt.getQstnTp() == 4){
				if(qt.getMarksGet() > 0){
					isCorrect = true;
				}
			}
			//점수 저장
			double marks = qt.getMarks();//문제배점
			taVO.setMarksGet(0);
			if(isCorrect){
				taVO.setMarksGet(marks);
			}
			////
			
			int r = testAnswerMapper.updateByPk(taVO);
			if(r == 0){
				result.setMsg("시험지를 다시 실행하시기 바랍니다.");
				result.setResult(0);
				return result;
			}
			
			//내문제 조회,저장과 동시에 최신화데이터를 가져오지 말고 별도로 호출하기 위해 주석처리(240724)
			qtList = quizTestMapper.selectTestUserQtList(qtVO);
			if(qtList.size()>0){
				rstData.put("qt", qtList.get(0));
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO doneTestQstnAnswer(int eduSeq, int testSeq, String userId, int forceResultState,String checkId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			//유효성체크
			
			QuizTestVO qtVO = new QuizTestVO();
			
			//시험지 조회
			qtVO.setTestSeq(testSeq);
			QuizTestVO test = quizTestMapper.selectTestEdu(qtVO);
			
			//문제와 내답안 목록
			qtVO.setTestSeq(testSeq);
			qtVO.setUserId(userId);
			List<QuizTestVO> qtList = quizTestMapper.selectTestUserQtList(qtVO);
			
			//보기목록
			qtVO.setTestTmplSeq(test.getTestTmplSeq());
			qtVO.setChAnsYn("Y");
			List<QuizTestVO> chList = quizTestMapper.selectTestUserChList(qtVO);
			
			//답안 json 파싱
			ObjectMapper om = new ObjectMapper();
			
			String updId = SessionUserInfoHelper.getUserId();
			
			double totMarksGet = 0;
			double totMarks = 0;
			
			//완료처리, 250113,정담저장시에 계산하기위해 주석처리
			/*
			for(QuizTestVO o: qtList){
				LOG.debug("qstnSeq : "+o.getQstnSeq()+"	|"+o.getQstnNm()+"	|"+o.getOptn());
				boolean isCorrect = false;//정답여부
				if(o.getQstnTp() == 1 || o.getQstnTp() == 2){//객관식
					if(o.getOptn() == null){
						continue;
					}
					Pattern pattern = Pattern.compile("\\[\\s*\\d+(\\s*,\\s*\\d+)*\\s*\\]");
					if(!pattern.matcher(o.getOptn()).matches() && !"Y".equals(o.getAnsweredYn())){//빈값이면
						continue;
					}
					int[] optnArr = om.readValue(o.getOptn(), int[].class);
					
					for(QuizTestVO o2: chList){//객관식목록
						if(o.getTqSeq()==o2.getTqSeq() && "Y".equals(o2.getChAnsYn())){//해당문제의 정답보기만 필터
							isCorrect = false;
							LOG.debug("정답 : "+o2.getChSeq());
							for(int ansChSeq: optnArr){//나의 답
								if(ansChSeq == o2.getChSeq()){
									LOG.debug("correct!!");
									isCorrect = true;
								}
							}
						}
					}
					
				}else if(o.getQstnTp() == 3){//단답형
					if(o.getFillBlank().equals(o.getFillBlankCo())){
						isCorrect = true;
					}
				}else if(o.getQstnTp() == 4){
					if(o.getMarksGet() > 0){
						isCorrect = true;
					}
				}
				
				//LOG.debug("최종 : "+isCorrect);
				
				
				//점수 저장
				double marks = o.getMarks();//문제배점
				if(isCorrect){
					TestAnswer taVO = new TestAnswer();
					taVO.setTestSeq(testSeq);
					taVO.setTqSeq(o.getTqSeq());
					taVO.setUserId(userId);
					taVO.setUpdDe(new Date());
					taVO.setUpdId(updId);
					taVO.setMarksGet(marks);
					testAnswerMapper.updateByPk(taVO);
					
					totMarksGet += marks;
				}
				totMarks += marks;
				LOG.info("totMarks : "+totMarks);
			}
			*/
			for(QuizTestVO o: qtList){
				double marks = o.getMarks();//문제배점
				double marksGet = o.getMarksGet();
				if(o.getMarksGet() > 0){
					totMarksGet += marksGet;
				}
				totMarks += marks;
			}
			
			//자동채점여부 확인
			int reusltState = 2;
			if(test.getMarkTp() == 1){//자동채점이면 
				if(!SessionUserInfoHelper.isAdmin()){
					reusltState = 3;
				}
			}
			
			//강제결과상태체크
			if(forceResultState != 0){
				reusltState = forceResultState;
			}
			
			//결과지 작성
			Date endDe = new Date();
			String passYn = "N";
			if(test.getPassMarks() <= totMarksGet){
				passYn = "Y";
			}
			TestResult trVO = new TestResult();
			trVO.setTestSeq(testSeq);
			trVO.setUserId(userId);
			trVO.setEndDe(endDe);
			trVO.setTotMarks(totMarks);
			trVO.setMarks(totMarksGet);
			trVO.setPassYn(passYn);
			trVO.setUpdDe(new Date());
			trVO.setUpdId(sessId);
			trVO.setState(reusltState);
			trVO.setCheckId(checkId);
			testResultMapper.updateByPk(trVO);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			e.printStackTrace();
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO saveQuizTestQstnMark(Integer[] tqSeqArr, Double[] marksArr) {
		ResultVO result = new ResultVO();
		try {
			if(ObjectUtils.isEmpty(tqSeqArr)){
				result.setResult(1);
				return result;
			}
			
			String updId = SessionUserInfoHelper.getUserId();
			
			TestQuestion vo = new TestQuestion();
			vo.setUpdDe(new Date());
			vo.setUpdId(updId);
			for(int i=0; i<tqSeqArr.length; i++){
				int tqSeq = tqSeqArr[i];
				double marks = marksArr[i];
				
				vo.setTqSeq(tqSeq);
				vo.setMarks(marks);
				testQuestionMapper.updateByPk(vo);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO startTestQstnAnswer(int eduSeq, int testSeq, String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			
			//시험지조회
			Test test = testMapper.selectByPk(testSeq);
			rstData.put("test", test);
			
			//결과지 작성
			Date startDe = new Date();
			Date endDe = null;
			String passYn = "N";
			String achvLvl = null;
			double totMarks = 0;
			double totMarksGet = 0;
			String regId = sessId;
			String updId = sessId;
			int state = 1;
			
			TestResult trVO = new TestResult(testSeq, userId, startDe, endDe, totMarks, totMarksGet, passYn, achvLvl,state, regId, new Date(), updId, new Date());
			
			//결과지 조회
			TestResult testResult = testResultMapper.selectByPk(trVO);
			
			if(testResult == null){
				testResultMapper.insertByPk(trVO);
			}
			
			//내 결과지 조회
			TestResult tr = testResultMapper.selectByPk(trVO);
			rstData.put("tr", tr);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getTestResult(int eduSeq, int testSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			
			
			QuizTestVO vo = new QuizTestVO();
			vo.setEduSeq(eduSeq);
			vo.setTestSeq(testSeq);
			
			QuizTestVO test = quizTestMapper.selectTestEdu(vo);
			rstData.put("test", test);
			
			List<QuizTestVO> stdntList = quizTestMapper.selectTestResultStdntList(vo);
			rstData.put("stdntList", stdntList);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getTestResultStdnt(int eduSeq, Integer testSeq, String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			
			QuizTestVO vo = new QuizTestVO();
			vo.setEduSeq(eduSeq);
			vo.setTestSeq(testSeq);
			vo.setUserId(userId);
			
			//학생 결과지 조회
			//QuizTestVO stdntResult = quizTestMapper.selectTestResultStdnt(vo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO checkOpenTest(int eduSeq, int testSeq, String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			
			QuizTestVO vo = new QuizTestVO();
			vo.setEduSeq(eduSeq);
			vo.setTestSeq(testSeq);
			vo.setUserId(userId);
			
			//학생 결과지 조회
			List<QuizTestVO> o = quizTestMapper.selectTestResultStdnt(vo);
			if(o.get(0).getTestResultState() == 2 || o.get(0).getTestResultState() == 3){
				result.setResult(1);
				return result;
			}
			
			//시험지조회
			Test test = testMapper.selectByPk(testSeq);
			
			//재평가 대상여부 체크
			if(test.getTryNo() > 1){
				//본평가 시험결과 조회
				TestResult trVO = new TestResult();
				trVO.setTestSeq(test.getPrntTestSeq());
				trVO.setUserId(userId);
				TestResult test00 = testResultMapper.selectByPk(trVO);
				if(test00 == null){
					result.setResult(0);
					result.setMsg("본평가먼저 진행하세요");
					return result;
				}
				if("Y".equals(test00.getPassYn())){
					result.setResult(0);
					result.setMsg("본평가 통과상태 입니다. 재평가대상이 아닙니다.");
					return result;
				}
			}
			
			Date now = new Date();
			if(test.getStartDe().compareTo(now) > 0 ){
				result.setMsg("아직 시험시간이 아닙니다.");
				result.setResult(0);
				return result;
			}
			if(test.getEndDe().compareTo(now) < 0){
				result.setMsg("시험기간이 지났습니다.");
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
	public ResultVO saveTestQstnCorrect(int eduSeq, int testSeq, int tqSeq, String userId, String correctYn,int marksGet) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			
			TestQuestion vo = new TestQuestion();
			vo.setTqSeq(tqSeq);
		 	TestQuestion tq = testQuestionMapper.selectByPk(vo);
		 	if(tq == null){
		 		result.setResult(0);
		 		return result;
		 	}
		 	
		 	TestAnswer vo2 = new TestAnswer();
		 	vo2.setTestSeq(testSeq);
		 	vo2.setTqSeq(tqSeq);
		 	vo2.setUserId(userId);
		 	vo2.setUpdDe(new Date());
		 	vo2.setUpdId(sessId);
		 	vo2.setMarksGet(0);
		 	if("Y".equals(correctYn)){
		 		vo2.setMarksGet(tq.getMarks());
		 		if(marksGet > 0){
		 			vo2.setMarksGet(marksGet);
		 		}
		 	}
		 	testAnswerMapper.updateByPk(vo2);
		 	
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO openResultTest(int eduSeq, int testSeq, String openYn) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			String checkId = sessId;
			//학생 결과목록
			QuizTestVO vo = new QuizTestVO();
			vo.setEduSeq(eduSeq);
			vo.setTestSeq(testSeq);
			List<QuizTestVO> stdntList = quizTestMapper.selectTestResultStdntList(vo);
			
			if(ObjectUtils.isEmpty(stdntList)){
				result.setMsg("시험응시자가 없습니다.");
				result.setResult(0);
				return result;
			}
			
			//전부 채점 완료 처리,250113채점완료처리는 추석처리함 결과오픈상태값만 변경함
			/*
			for(QuizTestVO o: stdntList){
				String userId = o.getUserId();
				if(o.getTestResultState() == 3){
					//이미 채점완료는 진행할  필요 없음
					continue;
				}
				//응시데이터 처리
				if(o.getTestResultState() == 0){
					ResultVO subResult1 = startTestQstnAnswer(eduSeq, testSeq, userId);
					if(subResult1.getResult() != 1){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						result.setMsg("제출처리중 오류가 발생하였습니다.");
						result.setResult(0);
						return result; 
					}
				}
				int forceResultState = 3;//강제상태 변경
				//결과데이터 처리
				ResultVO subResult2 = doneTestQstnAnswer(eduSeq, testSeq, userId, forceResultState,checkId);
				if(subResult2.getResult() != 1){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					result.setMsg("제출처리중 오류가 발생하였습니다.");
					result.setResult(0);
					return result; 
				}
				//강제 채점완료
			}
			*/
			int status = 0;
			if("Y".equals(openYn)){
				status = 3;
			}else{
				status = 1;
			}
			Test vo2 =  new Test();
			vo2.setTestSeq(testSeq);
			vo2.setStatus(status);
			vo2.setUpdDe(new Date());
			vo2.setUpdId(sessId);
			vo2.setSubSeq(-1);
			vo2.setTimer(-1);
			testMapper.updateByPk(vo2);
			
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO saveTestQstnComplete(int eduSeq, int testSeq, String userId, String checkId, String completeYn) throws JsonMappingException,JsonParseException  {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			
			QuizTestVO qtVO = new QuizTestVO();
			//시험지 조회
			qtVO.setTestSeq(testSeq);
			QuizTestVO test = quizTestMapper.selectTestEdu(qtVO);
			
			//문제와 내답안 목록
			qtVO.setTestSeq(testSeq);
			qtVO.setUserId(userId);
			List<QuizTestVO> qtList = quizTestMapper.selectTestUserQtList(qtVO);
			
			//보기목록
			qtVO.setTestTmplSeq(test.getTestTmplSeq());
			qtVO.setChAnsYn("Y");
			List<QuizTestVO> chList = quizTestMapper.selectTestUserChList(qtVO);
			
			//답안 json 파싱
			ObjectMapper om = new ObjectMapper();
			
			String updId = SessionUserInfoHelper.getUserId();
			
			double totMarksGet = 0;
			double totMarks = 0;
			
			//완료처리, 250113,정담저장시에 계산하기위해 주석처리
			///*
			for(QuizTestVO o: qtList){
				LOG.debug("qstnSeq : "+o.getQstnSeq()+"	|"+o.getQstnNm()+"	|"+o.getOptn());
				boolean isCorrect = false;//정답여부
				double marks = o.getMarks();//문제배점
				if(o.getQstnTp() == 1 || o.getQstnTp() == 2){//객관식
					if(o.getOptn() == null){
						continue;
					}
					Pattern pattern = Pattern.compile("\\[\\s*\\d+(\\s*,\\s*\\d+)*\\s*\\]");
					if(!pattern.matcher(o.getOptn()).matches() && !"Y".equals(o.getAnsweredYn())){//빈값이면
						continue;
					}
					int[] optnArr = om.readValue(o.getOptn(), int[].class);
					
					for(QuizTestVO o2: chList){//객관식목록
						if(o.getTqSeq()==o2.getTqSeq() && "Y".equals(o2.getChAnsYn())){//해당문제의 정답보기만 필터
							isCorrect = false;
							LOG.debug("정답 : "+o2.getChSeq());
							for(int ansChSeq: optnArr){//나의 답
								if(ansChSeq == o2.getChSeq()){
									LOG.debug("correct!!");
									isCorrect = true;
								}
							}
						}
					}
					
				}else if(o.getQstnTp() == 3){//단답형
					marks = o.getMarksGet();
					if(o.getFillBlankCo().equals(o.getFillBlank()) || o.getMarksGet() > 0){
						isCorrect = true;
					}
				}else if(o.getQstnTp() == 4){
					marks = o.getMarksGet();
					if(o.getMarksGet() > 0){
						isCorrect = true;
					}
				}
				
				//LOG.debug("최종 : "+isCorrect);
				
				
				//점수 저장
				
				if(isCorrect){
					TestAnswer taVO = new TestAnswer();
					taVO.setTestSeq(testSeq);
					taVO.setTqSeq(o.getTqSeq());
					taVO.setUserId(userId);
					taVO.setUpdDe(new Date());
					taVO.setUpdId(updId);
					taVO.setMarksGet(marks);//채점완료시에는 이미 점수가 저장되어있으니까 갱신하지 않는다.250703
					testAnswerMapper.updateByPk(taVO);
					
					totMarksGet += marks;
				}
				totMarks += marks;
				LOG.info("totMarks : "+totMarks);
			}
			//*/
			//결과지 작성
			//sDate endDe = new Date();
			String passYn = "N";
			if(test.getPassMarks() <= totMarksGet){
				passYn = "Y";
			}
			
			TestResult vo =  new TestResult();
			vo.setCheckId("0");
			int state = 2;
			if("Y".equals(completeYn)){
				state = 3;
				vo.setCheckId(checkId);
			}
			
			vo.setTestSeq(testSeq);
			vo.setUserId(userId);
			vo.setState(state);
			vo.setCheckDe(new Date());
			vo.setUpdId(sessId);
			vo.setUpdDe(new Date());
			
			//vo.setEndDe(endDe);
			vo.setTotMarks(totMarks);
			vo.setMarks(totMarksGet);
			vo.setPassYn(passYn);
			vo.setUpdId(sessId);
			
			testResultMapper.updateByPk(vo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | IOException e) {
			LOG.info("error : {}",e.getMessage());
			result.setResult(0);
			return result;
		}
	}
	
	/*
	 * 시험지관리 > 삭제
	 * (non-Javadoc)
	 * @see com.educare.edu.quizTest.service.QuizTestService#delQuizTestQstn(java.lang.String, int)
	 */

	@Override
	public ResultVO delQuizTestQstn(String userId, int testTmplSeq ) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//삭제가능체크
			
			//삭제
			testQuestionMapper.delQuizTestQstn(testTmplSeq);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
}

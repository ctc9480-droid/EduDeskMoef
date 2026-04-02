package com.educare.edu.quizTest.service;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.exam.vo.ExamVO;

/**
 * 설문지 관리 서비스
 */
public interface QuizTestService {

	/**
	 * 문제가져오기
	 * @param returnData
	 * @param ebqIdx 
	 * @return
	 */
	ResultVO getQuizTestInfo(int qstnSeq);

	/**
	 * 문제 게시판 리스트
	 * @param pageNo
	 * @return
	 */
	ResultVO getQuizTestPageList(int pageNo,int rowCnt,String srchWrd,int srchCtg1Seq,int srchCtg2Seq,int srchCtg3Seq);
	
	/**
	 * 시험지 저장
	 * @param userId
	 * @param testTmplSeq
	 * @param testTmplNm
	 * @param ctg1Seq
	 * @param ctg2Seq
	 * @param ctg3Seq
	 * @param timeLimit
	 * @param markTp
	 * @param lookTp
	 * @param ordTp
	 * @param selectTp
	 * @param choiceTp
	 * @param lookCnt
	 * @param totQstn
	 * @param selectQstn
	 * @param descr
	 * @param totMarks
	 * @param status
	 * @return
	 */
	ResultVO saveQuizTest(String userId, int testTmplSeq, String testTmplNm, int testTp, int ctg1Seq, int ctg2Seq, int ctg3Seq, int timeLimit, int markTp, int lookTp, int ordTp, int selectTp, int choiceTp,
			int lookCnt, String totQstn, String selectQstn, String descr, Double totMarks, int status);

	ResultVO saveQuizTestAddQstn(String userId, int testTmplSeq, int[] qstnSeqArr);

	ResultVO delQuizTestAddQstn(String userId, int tqSeq);

	/**
	 * 교육에 매핑된 시험지 조회
	 * @param testSeq
	 * @return
	 */
	ResultVO getTestEduMap(int testSeq,int eduSeq);

	/**
	 * 시험지 목록 조회
	 * @param eduSeq
	 * @param srchWrd 
	 * @return
	 */
	ResultVO getTestTmplList(int eduSeq, String srchWrd,int srchCtg1Seq,int srchCtg2Seq,int srchCtg3Seq);

	/**
	 * 시험지매핑
	 * @return
	 */
	ResultVO saveTestEduMap(int testSeq, int eduSeq, int testTmplSeq, String testNm, String startDeStr, String endDeStr, int status,int timer,double passMarks,int markTp,int tryNo,int prntTestSeq,int subSeq);

	/**
	 * 시험지매핑 리스트
	 * @param eduSeq
	 * @return
	 */
	ResultVO getTestEduList(String userId,int eduSeq,int rowCnt,int pageNo,int status);

	/**
	 * <pre>
	 * 개인의 시험문제 조회
	 * 답안과 함께 조회
	 * </pre>
	 * @param eduSeq
	 * @param testSeq
	 * @param userId 
	 * @return
	 */
	ResultVO getTestEduQstnList(int eduSeq, int testSeq, String userId);

	/**
	 * 답안저장
	 * @param eduSeq
	 * @param testSeq
	 * @param tqSeq
	 * @param userId
	 * @param optn
	 * @param answer
	 * @param fillBlank
	 * @return
	 */
	ResultVO saveTestQstnAnswer(int eduSeq, int testSeq, int tqSeq, String userId,int qstnTp, String optn, String answer, String fillBlank);

	/**
	 * 시험지 최종 제출
	 * @param eduSeq
	 * @param testSeq
	 * @param userId: 학생아이디
	 * @return
	 */
	ResultVO doneTestQstnAnswer(int eduSeq, int testSeq, String userId, int forceResultState,String checkId);

	/**
	 * 시험지에 문제점수 할당하기
	 * @param tqSeqArr
	 * @param marksArr
	 * @return
	 */
	ResultVO saveQuizTestQstnMark(Integer[] tqSeqArr, Double[] marksArr);

	/**
	 * 시험시작 세팅
	 * @param eduSeq
	 * @param testSeq
	 * @param userId
	 * @return
	 */
	ResultVO startTestQstnAnswer(int eduSeq, int testSeq, String userId);

	/**
	 * 종합시험결과
	 * @param eduSeq
	 * @param testSeq
	 * @return
	 */
	ResultVO getTestResult(int eduSeq, int testSeq);

	/**
	 * <pre>
	 * 개별 종합시험결과만
	 * 문제목록은 따로
	 * </pre>
	 * @param eduSeq
	 * @param testSeq
	 * @param userId
	 * @return
	 */
	ResultVO getTestResultStdnt(int eduSeq, Integer testSeq, String userId);

	/**
	 * 시험지 오픈여부 체크
	 * 1: 가능 ,0: 안됨
	 * @param eduSeq
	 * @param testSeq
	 * @param userId
	 * @return
	 */
	ResultVO checkOpenTest(int eduSeq, int testSeq, String userId);

	/**
	 * 관리자 채점 기능
	 * @param eduSeq
	 * @param testSeq
	 * @param tqSeq
	 * @param userId
	 * @return
	 */
	ResultVO saveTestQstnCorrect(int eduSeq, int testSeq, int tqSeq, String userId,String correctYn,int marksGet);
	
	/***
	 * 해당시험 답안지 전부 결과 오픈
	 * 나중에 파라미터 추가하여 선택된 학생만 오픈여부 할수 있게 해야 함
	 * @param eduSeq
	 * @param testSeq
	 * @param openYn 
	 * @return
	 */
	ResultVO openResultTest(int eduSeq, int testSeq, String openYn);

	/**
	 * 시험지 채점 완료여부 저장
	 * @param eduSeq
	 * @param testSeq
	 * @param userId
	 * @param checkId
	 * @param completeYn 
	 * @return
	 * @throws JsonParseException 
	 * @throws JsonMappingException 
	 */
	ResultVO saveTestQstnComplete(int eduSeq, int testSeq, String userId, String checkId, String completeYn) throws JsonMappingException, JsonParseException;

   /**
    * 시험지관리 > 삭제
    * @param userId
    * @param tqSeq
    * @return
    */
	ResultVO delQuizTestQstn(String userId, int tqSeq);

}

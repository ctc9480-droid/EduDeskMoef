package com.educare.edu.quizBank.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.exam.vo.ExamVO;

/**
 * 설문지 관리 서비스
 */
public interface QuizBankService {

	/**
	 * 문제가져오기
	 * @param returnData
	 * @param ebqIdx 
	 * @return
	 */
	ResultVO getQuizBankInfo(int qstnSeq);

	/**
	 * 문제저장
	 * @param userId
	 * @param useYn 
	 * @param ansDesc 
	 * @param qstnDesc 
	 * @param qstnStr 
	 * @param qstnTp 
	 * @param diffType 
	 * @param ctg3Seq 
	 * @param ctg2Seq 
	 * @param ctg1Seq 
	 * @param qstnNm 
	 * @param chListJson 
	 * @return
	 */
	ResultVO saveQuizBank(String userId,int qstnSeq, String qstnNm, int ctg1Seq, int ctg2Seq, int ctg3Seq, int diffType, int qstnTp, String qstnStr, String qstnDesc, String ansDesc, String useYn, String chListJson,String fillBlank);

	/**
	 * 파일업로드 및 db처리
	 * @param mhsr
	 * @return
	 */
	ResultVO saveQuizBankFile(MultipartHttpServletRequest mhsr,int qstnSeq,String userId);

	/**
	 * 문제 게시판 리스트
	 * @param pageNo
	 * @return
	 */
	ResultVO getQuizBankPageList(int pageNo,int rowCnt,String srchWrd,int srchQstnTp,int srchCtg1Seq);

	ResultVO getQuizBankList(int testTmplSeq, String srchWrd, int srchCtgry, int srchCtgry2, int srchCtgry3);

	/**
	 * 문제,보기파일 삭제
	 * @param qstnSeq
	 * @param chSeq
	 * @param fnRnm 
	 * @return
	 */
	ResultVO delQuizBankFile(int qstnSeq, int chSeq, String fnRnm);

	/**
	 * 문제은행 엑셀업로드 프로세스
	 * @param mhsr
	 * forceReg: 1:검증없이 바로 업로드
	 * @return
	 */
	ResultVO uploadQuizBankExcel(MultipartHttpServletRequest mhsr,int ctg1Seq,int ctg2Seq,int ctg3Seq,int forceReg);

	/**
	 * 문제은행 > 삭제
	 * @param userId
	 * @param qstnSeq
	 * @return
	 */
	ResultVO quizBankDel(String userId, int qstnSeq);

	
}

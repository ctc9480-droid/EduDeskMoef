package com.educare.edu.quizTest.mapper;

import java.util.List;

import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.exam.vo.ExamVO;
import com.educare.edu.quizBank.vo.QuizBankVO;
import com.educare.edu.quizTest.vo.QuizTestVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("QuizTestMapper")
public interface QuizTestMapper {

	int selectQuizTestPageCnt(QuizTestVO vo);

	List<QuizTestVO> selectQuizTestPageList(QuizTestVO vo);

	List<QuizTestVO> selectTestQstnByTestTmpl(QuizTestVO testVO);

	/**
	 * 교육에 매핑된 시험지 목록
	 * @param vo
	 * @return
	 */
	List<QuizTestVO> selectTestEduPageList(QuizTestVO vo);

	/**
	 * 교육에 매핑된 시험지 갯수
	 * @param vo
	 * @return
	 */
	int selectTestEduPageCnt(QuizTestVO vo);

	/**
	 * 교육에 매핑된 시험지 정보
	 * @param qtVO
	 * @return
	 */
	QuizTestVO selectTestEdu(QuizTestVO qtVO);

	/**
	 * 학생의 시험문제 목록(답변지목록)
	 * @param qtVO
	 * @return
	 */
	List<QuizTestVO> selectTestUserQtList(QuizTestVO qtVO);

	/**
	 * 학생의 시험문제의 보기 목록
	 * @param qtVO (testTmplSeq)
	 * @return
	 */
	List<QuizTestVO> selectTestUserChList(QuizTestVO qtVO);

	/**
	 * 시험결과 학생목록
	 * @param vo
	 * @return
	 */
	List<QuizTestVO> selectTestResultStdntList(QuizTestVO vo);

	/**
	 * 학생결과지 조회
	 * @param vo
	 * @return
	 */
	List<QuizTestVO> selectTestResultStdnt(QuizTestVO vo);

	/**
	 * 사용중인 시험지인지 체크하기
	 * @param vo
	 * @return
	 */
	int selectTestByUsed(QuizTestVO vo);

}

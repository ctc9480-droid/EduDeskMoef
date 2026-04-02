package com.educare.edu.quizBank.mapper;

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

@Mapper("QuizBankMapper")
public interface QuizBankMapper {

	int selectQuizBankPageCnt(QuizBankVO vo);

	List<QuizBankVO> selectQuizBankPageList(QuizBankVO vo);

	List<QuizBankVO> selectQuizBankList(QuizBankVO vo);

}

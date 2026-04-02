package com.educare.edu.lclike.mapper;

import java.util.List;

import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.exam.vo.ExamVO;
import com.educare.edu.lclike.vo.LclikeVO;
import com.educare.edu.lcsub.vo.LcsubVO;
import com.educare.edu.quizBank.vo.QuizBankVO;
import com.educare.edu.quizTest.vo.QuizTestVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LclikeMapper")
public interface LclikeMapper {

	int selectLclikePageCnt(LclikeVO vo);

	List<LclikeVO> selectLclikePageList(LclikeVO vo);

}

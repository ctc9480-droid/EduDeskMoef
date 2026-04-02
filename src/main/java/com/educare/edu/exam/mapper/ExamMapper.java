package com.educare.edu.exam.mapper;

import java.util.List;

import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.exam.vo.ExamVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ExamMapper")
public interface ExamMapper {

	List<ExamVO> getBoardBankList(ExamVO exParam);

	ExamVO getExamBankQuestionInfo(ExamVO exParam);

	List<ExamVO> getExamBankChoiceList(ExamVO exParam);

	int getBoardBankCnt(ExamVO exParam);

}

package com.educare.edu.feedback.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.quizTest.vo.QuizTestVO;

@Mapper("FeedbackMapper")
public interface FeedbackMapper {

	List<FeedbackVO> getFeedbackList(FeedbackVO param);

	FeedbackVO getFeedbackMap(FeedbackVO param);

	void insertFeedback(Feedback feedback);

	void insertFeedbackQuestion(FeedbackQuestion feedbackQuestion);

	void insertFeedbackChoice(FeedbackChoice feedbackChoice);
 
	void updateFeedback(Feedback feedback);

	void deleteFeedbackQuestionByFbIdx(int idx);

	void deleteFeedbackChoiceByFbIdx(int idx);

	List<FeedbackVO> selectFeedbackQuestionByFbIdx(FeedbackVO param);

	List<FeedbackVO> selectFeedbackChoiceByFbIdxQtIdx(FeedbackVO qtMap);

	void deleteFeedback(int idx);

	List<FeedbackVO> selectFeedbackAnswerByFbIdx(FeedbackAnswer feedbackAnswer);

	FeedbackVO selectFeedbackAnswerByChIdxCnt(FeedbackVO feedbackChoice);
	FeedbackVO selectFeedbackAnswerByChIdxCntAll(FeedbackVO feedbackChoice);

	List<FeedbackVO> selectFeedbackAnswerByFbIdxQtIdx(FeedbackVO qt);
	List<FeedbackVO> selectFeedbackAnswerByFbIdxQtIdxAll(FeedbackVO qt);
	
	FeedbackVO selectOneFeedbackByFbIdx(int i);

	int getFeedbackAnswerCheck(FeedbackAnswer param3);

//	FeedbackVO selectFeedback(FeedbackVO vo);
	
	/**
	 * 교육에 연결된 설문지 조회
	 * @param vo
	 * @return
	 */
	List<FeedbackVO> selectFeedbackEduPageList(FeedbackVO vo);

	int selectFeedbackPageCnt(FeedbackVO vo);

	List<QuizTestVO> selectFeedbackPageList(FeedbackVO vo);

}

package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("FeedbackAnswerMapper")
public interface FeedbackAnswerMapper {

	void insertByPk(FeedbackAnswer param2);

	void deleteByFbIdxUserId(FeedbackAnswer param3);

	List<FeedbackAnswer> selectBy_FbIdx_QtIdx_UserIdx(FeedbackAnswer param);

}

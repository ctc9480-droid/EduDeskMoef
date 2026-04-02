package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackOpenAnswer;
import com.educare.edu.comn.vo.FeedbackVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("FeedbackOpenAnswerMapper")
public interface FeedbackOpenAnswerMapper {

	void insertByPk(FeedbackOpenAnswer param2);

	void deleteByFbIdxUserId(FeedbackOpenAnswer param3);

	List<FeedbackOpenAnswer> selectBy_FbIdx_QtIdx_UserIdx(FeedbackOpenAnswer param);

	int selectCntByUserIdOpenIdx(FeedbackOpenAnswer param2);

	int selectCntByOpenIdx(int idx);

}

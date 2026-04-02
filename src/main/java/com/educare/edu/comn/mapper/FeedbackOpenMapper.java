package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.FeedbackOpen;
import com.educare.edu.comn.model.FeedbackOpenAnswer;
import com.educare.edu.comn.vo.FeedbackOpenVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("FeedbackOpenMapper")
public interface FeedbackOpenMapper {

	FeedbackOpen selectByPk(int idx);

	List<FeedbackOpen> selectByOpen(FeedbackOpenVO param);

	List<FeedbackOpenVO> selectBySearch(FeedbackOpenVO param);

}

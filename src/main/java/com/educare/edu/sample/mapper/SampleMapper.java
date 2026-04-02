package com.educare.edu.sample.mapper;

import java.util.List;

import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.exam.vo.ExamVO;
import com.educare.edu.sample.vo.SampleVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("SampleMapper")
public interface SampleMapper {

	List<SampleVO> selectSampleList(SampleVO param);

}

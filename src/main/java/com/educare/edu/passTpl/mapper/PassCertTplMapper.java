package com.educare.edu.passTpl.mapper;

import java.util.List;

import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.model.PassCertTpl;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.PassCertTplVO;
import com.educare.edu.quizTest.vo.QuizTestVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("PassCertTplMapper")
public interface PassCertTplMapper {

	List<PassCertTplVO> selectPassCertTplList(PassCertTplVO param);

	PassCertTplVO selectPassCertTplMap(PassCertTplVO param);

	void updatePassCertTpl(PassCertTpl param);

	int selectPassTplPageCnt(PassCertTplVO vo);

	List<QuizTestVO> selectPassTplPageList(PassCertTplVO vo);
}

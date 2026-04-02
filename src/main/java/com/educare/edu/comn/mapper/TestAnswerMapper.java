package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.model.Question;
import com.educare.edu.comn.model.Test;
import com.educare.edu.comn.model.TestAnswer;
import com.educare.edu.comn.model.TestQuestion;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.PassCertVO;
import com.educare.edu.education.service.EduVO; 
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.quizBank.vo.QuizBankVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
  
@Mapper("TestAnswerMapper") 
public interface TestAnswerMapper {

	TestAnswer selectByPk(TestAnswer vo);

	int updateByPk(TestAnswer vo);

	void insertByPk(TestAnswer vo);

	void deleteByPk(int testSeq);

	/**
	 * 내 시험문제 갯수
	 * @param taVO
	 * @return
	 */
	int selectCntByUserId(TestAnswer taVO);

	int deleteByUserId(TestAnswer taVO);

} 

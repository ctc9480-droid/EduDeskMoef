package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.model.Question;
import com.educare.edu.comn.model.TestQuestion;
import com.educare.edu.comn.model.TestResult;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.PassCertVO;
import com.educare.edu.education.service.EduVO; 
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.quizBank.vo.QuizBankVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
  
@Mapper("TestResultMapper") 
public interface TestResultMapper {

	TestResult selectByPk(TestResult vo);

	void updateByPk(TestResult vo);

	void insertByPk(TestResult vo);

	List<TestResult> selectByTestSeq(int testSeq);

} 

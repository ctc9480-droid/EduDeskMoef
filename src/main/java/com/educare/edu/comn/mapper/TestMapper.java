package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.model.Question;
import com.educare.edu.comn.model.Test;
import com.educare.edu.comn.model.TestQuestion;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.PassCertVO;
import com.educare.edu.education.service.EduVO; 
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.quizBank.vo.QuizBankVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
  
@Mapper("TestMapper") 
public interface TestMapper {

	Test selectByPk(int testSeq);

	void updateByPk(Test vo);

	void insertByPk(Test vo);

	void deleteByPk(int testSeq);

	List<Test> selectByEduSeq(Test testVO);

	int selectCntByEduSeq(Test testVO);

	/**
	 * 재평가시험지 갯수
	 * @param testSeq
	 * @return
	 */
	int selectCntByPrntTestSeq(int testSeq);

} 

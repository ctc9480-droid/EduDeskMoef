package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.model.QuestionChoice;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.PassCertVO;
import com.educare.edu.education.service.EduVO; 
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.quizBank.vo.QuizBankVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
  
@Mapper("QuestionChoiceMapper") 
public interface QuestionChoiceMapper {

	List<QuestionChoice> selectByQstn(QuizBankVO vo);

	void insertByPk(QuestionChoice o);

	void updateByFile(QuestionChoice chVO);

	/**
	임시 사용안함처리
	 */
	void updateUseNByQstnSeq(int qstnSeq);

	QuestionChoice selectByPk(QuestionChoice o);

	void updateByPk(QuestionChoice o);

	void deleteByUseN(int qstnSeq);
	
    /*
     * 문제은행 삭제
     */
	void quizBankDel(int qstnSeq);  

} 

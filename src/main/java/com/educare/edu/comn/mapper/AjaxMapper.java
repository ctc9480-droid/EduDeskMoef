package com.educare.edu.comn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.PassCertVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureStdnt;
  
@Mapper("AjaxMapper") 
public interface AjaxMapper {  

	List<Lecture> selectLctreList(EduVO vo);
	 
	Integer selectLctreListCnt(EduVO vo);  
	
	List<FeedbackVO> selectFeedbackList(@Param("vo") FeedbackVO vo, @Param("eduVo") EduVO eduVo);
	
	List<PassCertVO> selectPassCertList(EduVO vo);
	
	PassCertVO selectOneByPassIdx(int i);

	void updateLectureStdntMeal(LectureStdnt vo);

	void updateLectureStdntDormi(LectureStdnt vo);
	
	void updateLectureStdntDeposit(LectureStdnt vo);
} 

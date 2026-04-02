package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.ExamBankQuestion;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.EtcGradeCodeVO;
import com.educare.edu.comn.vo.EtcOrgCodeVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("EtcGradeCodeMapper")
public interface EtcGradeCodeMapper {

	void insertByPk(EtcGradeCodeVO param);

	EtcGradeCodeVO selectByPk(EtcGradeCodeVO param);
	
	List<EtcGradeCodeVO> selectByPage(EtcGradeCodeVO param);

	int selectByPageCnt(EtcGradeCodeVO param);
	
	List<String> selectByAll();

	void insertBatch(List<EtcGradeCodeVO> batchList);

	void updateByParam(EtcGradeCodeVO vo);

	
}

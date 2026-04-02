package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.ExamBankQuestion;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.EtcOrgCodeVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("EtcOrgCodeMapper")
public interface EtcOrgCodeMapper {

	void insertByPk(EtcOrgCodeVO param);

	EtcOrgCodeVO selectByPk(EtcOrgCodeVO param);
	
	List<EtcOrgCodeVO> selectByPage(EtcOrgCodeVO param);

	List<String> selectByAll();

	void insertBatch(List<EtcOrgCodeVO> batchList);

	int selectByPageCnt(EtcOrgCodeVO param);
	
}

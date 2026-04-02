package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureTimeMapper")
public interface LectureTimeMapper {

	int selectLectTimeCnt(Integer eduSeq);

	LectureTime selectByPk(LectureTime param);

	void deleteByPk(LectureTime ltInfo);
	
	void deleteByEduSeq(Integer eduSeq);
	
	List<LectureTime> selectByEduSeq(LectureTime param);

	/**
	 * 시간강사 이름 전부 변경
	 * @param lttParam, eduSeq, instrNm
	 */
	void updateAllInstrNm(LectureTime lttParam);

	/**
	 * 시간표 등록
	 * @param ltVO
	 * @return
	 */
	int insertByPk(LectureTime ltVO);

	int updateByPk(LectureTime ltVO);

	List<LectureTime> selectByParam(LectureTime timeVO);

	List<LectureTime> selectByDt(LectureTime timeVO);
}

package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("SmartCheckMapper")
public interface SmartCheckMapper {

	List<SmartCheckVO> selectAttList(SmartCheckVO smartCheckParam);

	List<SmartCheckVO> selectLectureTimeByLectCd(SmartCheckVO smartCheckParam);

	List<SmartCheckVO> getStdntAttList(SmartCheckVO smartCheckParam);

	List<SmartCheckVO> getLectDtByLectCd(SmartCheckVO vo);

	int getLectureStatCnt(SmartCheckVO param);

	List<SmartCheckVO> getLectureStatList(SmartCheckVO param);

	SmartCheckVO getStdntAttInfo(SmartCheckVO param);

	List<SmartCheckVO> getEduTimeListByProf(SmartCheckVO param);

	List<SmartCheckVO> getStudentList(SmartCheckVO params);

	SmartCheckVO getEduAttInfo(SmartCheckVO params);


	/**
	 * 학생 인증여부 초기화
	 * @param params
	 */
	void updateLectureStdntAuthInit(SmartCheckVO params);
	

	/**
	 * 미인증 학생 결석처리
	 * @param params
	 */
	void insertAttendStdntByNoAuth(SmartCheckVO params);

	List<SmartCheckVO> getEduListByProf(SmartCheckVO params);

	List<SmartCheckVO> getEduTimeListByStud(SmartCheckVO param);

	List<SmartCheckVO> getEduListByStud(SmartCheckVO params);

	/**
	 * 일별 출결 데이터 조회
	 * @param vo
	 * @return
	 */
	List<SmartCheckVO> getStdntAttDayList(SmartCheckVO vo);

	/**
	 * 해당일자의 시작시간, 종료시간 조회
	 * @param vo2
	 * @return
	 */
	SmartCheckVO selectAttDay(SmartCheckVO vo2);

}

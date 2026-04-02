package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.LectureDormiVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureDormiMapper")
public interface LectureDormiMapper {

	List<LectureDormiVO> selectByParam(LectureDormiVO vo);

	List<LectureDormiVO> selectPageList(LectureDormiVO vo);

	int selectPageTotalCnt(LectureDormiVO vo);

	LectureDormiVO selectByPk(LectureDormiVO vo);

	void updateByPk(LectureDormiVO vo);

	void insertByPk(LectureDormiVO vo);

	/**
	 * 숙소리스트 조회
	 * @return
	 */
	List<LectureDormiVO> getLectureDormiList(LectureDormiVO param);

	List<LectureDormiVO> getLectureDormiListForPeriod(LectureDormiVO param);

	/**
	 * 학생명단 배정정보 포함
	 * @param param
	 * @return
	 */
	List<LectureDormiVO> selectLectureStdntListForAssign(LectureDormiVO param);

	List<LectureDormiVO> getAssignLectureDormi(LectureDormiVO vo);

	int selectLectureStdntPageCnt(LectureDormiVO param);

}

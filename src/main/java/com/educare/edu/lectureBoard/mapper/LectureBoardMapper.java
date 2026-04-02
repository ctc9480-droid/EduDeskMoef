package com.educare.edu.lectureBoard.mapper;
 
import java.util.List;

import com.educare.edu.lectureBoard.vo.LectureBoardVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LectureBoardMapper")
public interface LectureBoardMapper {

	List<LectureBoardVO> getLectureBoardList(LectureBoardVO vo);

	int getLectureBoardTotalCnt(LectureBoardVO vo);

	LectureBoardVO getLectureBoardByIndex(int idx);

	int getLectureCommunityTotalCnt(LectureBoardVO vo);  
	
	List<LectureBoardVO> getLectureCommunityList(LectureBoardVO vo);

	int updateLectureBoard(LectureBoardVO vo);

	int insertLectureBoard(LectureBoardVO vo);

	int deleteCommunityByIndex(int tabNum6Idx, String regId, String regNm);

	void updateTabBoardHitPlusByIndex(int tabNum6Idx);

	int updateLectureBoardStatus(LectureBoardVO vo);

} 

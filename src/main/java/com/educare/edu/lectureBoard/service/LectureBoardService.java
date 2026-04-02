package com.educare.edu.lectureBoard.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.service.model.BoardAttach;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.lectureBoard.vo.LectureBoardAttach;
import com.educare.edu.lectureBoard.vo.LectureBoardVO;

/**
 * 샘플 서비스
 */
public interface LectureBoardService {

	List<LectureBoardVO> getLectureBoardList(LectureBoardVO vo);

	int getLectureBoardTotalCnt(LectureBoardVO vo);
 
	LectureBoardVO getLectureBoardByIndex(int idx);

	int getLectureCommunityTotalCnt(LectureBoardVO vo);

	List<LectureBoardVO> getLectureCommunityList(LectureBoardVO vo);

	LectureBoardVO getLectureCommunityByIndex(int tabNum6Idx);

	/**
	 * 게시물 저장,수정
	 * @param vo
	 * @return
	 */
	ResultVO saveLectureBoard(LectureBoardVO vo);

	int communityDeleteProc(int tabNum6Idx, String regId, String regNm);

	void tabBoardHitPlusByIndex(int tabNum6Idx,String regId, String userId);

	int lectureBoardAttachWriteProc(MultipartHttpServletRequest mhsr);

	List<LectureBoardAttach> getLectureBoardAttachList(LectureBoardAttach attachVo);

	LectureBoardAttach getLectureBoardAttachMap(Integer fileSeq);

	int lectureBoardAttachDeleteProc(int fileSeq);

	ResultVO deleteLectureBoard(int idx,String userId,String userNm);
	
}

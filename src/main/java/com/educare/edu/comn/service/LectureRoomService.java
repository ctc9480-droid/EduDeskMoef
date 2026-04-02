package com.educare.edu.comn.service;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.comn.vo.ResultVO;

/**
 * <pre>
 * 강의실관리 서비스
 * </pre>
 */
public interface LectureRoomService {

	ResultVO getLectureRoomPageList(LectureRoomVO vo);

	/**
	 * 강의실 상세
	 * @param roomSeq
	 * @return
	 */
	LectureRoomVO getLectureRoom(int roomSeq);

	/**
	 * 저장 및 수정
	 * @param vo
	 * @return
	 */
	ResultVO saveLectureRoom(LectureRoomVO vo);

	/**
	 * 예외일 저장
	 * @param roomSeq
	 * @param jsonData
	 * @return
	 */
	ResultVO saveLectureRoomExcpt(int roomSeq, String jsonData);

	/**
	 * 강의실별 예외일 가져오기
	 * @param roomSeq
	 * @return
	 */
	ResultVO getLectureRoomExcpt(int roomSeq);
	
	ResultVO getLectureRoomChoice(String[] eduDtArr);

	/**
	 * <pre>
	 * 강의실 사용여부 체크
	 * 저장대상인 LectureTime정보를 활용하여  체크함
	 * </pre>
	 * @param finalTimeList
	 * @return
	 */
	ResultVO checkPossibleRoomByTimeList(List<LectureTime> finalTimeList);

	ResultVO deleteLectureRoom(int roomSeq);

}

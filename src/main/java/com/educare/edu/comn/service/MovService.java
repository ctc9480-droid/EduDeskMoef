package com.educare.edu.comn.service;

import java.util.List;

import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.MovVO;

/**
 * <pre>
 * 동영상 관리 서비스
 * </pre>
 */
public interface MovService {

	/**
	 * 동영상 삭제
	 * @param idx
	 * @return
	 */
	int deleteMov(int idx);

	/**
	 * 동영상 마지막 시청시간 기록
	 * @param eduSeq
	 * @param timeSeq
	 * @param historyTime
	 * @param userId
	 * @return
	 */
	int setHistoryTime(int eduSeq, int timeSeq, String historyTime, String allTime, String userId);
	
	/**
	 * 진도율
	 * @param eduSeq
	 * @param timeSeq
	 * @return
	 */
	List<MovVO> getStdntProgressList(int eduSeq, int timeSeq);

	
}

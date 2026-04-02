package com.educare.edu.comn.service;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.model.Org;

/**
 * <pre>
 * 통계관련 서비스
 * </pre>
 */
public interface StatService {

	int getMonthMain(Map<String, Object> datas);

	/**
	 * 대시보드에서 사용하는 토탈 카운트
	 * @param datas
	 * @return
	 */
	int getTotalMain(Map<String, Object> datas);

	/**
	 * 관리자 회원상세보기에 사용하는 각종 카운트
	 * @param userId
	 * @return
	 */
	Map<String, Object> getMyStatCount(String userId);

}

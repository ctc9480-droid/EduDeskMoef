package com.educare.edu.statistics.service;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.statistics.service.model.EduStats;
import com.educare.edu.statistics.service.model.Tuition;
import com.educare.util.XmlBean;

public interface StatsService {
	

	Map<String, Object> getStatFeedback(StatsVO vo);
	
	/**
	 * 개인교육 통계 페이징
	 * @param vo
	 * @param i
	 * @return
	 */
	ResultVO getStatLctrePageList(StatsVO vo, int i);
	
	/**
	 * 카텍고리별 통계
	 * @param vo
	 * @return
	 */
	ResultVO getStatCtgryList(StatsVO vo);

	/**
	 * 강사별 현황
	 * @param vo
	 * @return
	 */
	ResultVO getStatInstrctrList(StatsVO vo);

	/**
	 * 강의실 현황
	 * @param vo
	 * @return
	 */
	ResultVO getStatRoomList(StatsVO vo);
}

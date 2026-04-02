package com.educare.edu.statistics.service.impl;

import java.util.List;
import java.util.Map;

import com.educare.edu.statistics.service.StatsVO;
import com.educare.edu.statistics.service.model.EduStats;
import com.educare.edu.statistics.service.model.Tuition;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("StatsMapper")
public interface StatsMapper {
 
	StatsVO selectStatFeedbackAnswerCnt(StatsVO param);

	List<StatsVO> selectStatFeedbackAnswerList(StatsVO vo);

	int selectStatFeedbackUserCnt(StatsVO vo);

	int selectStatLctrePageCnt(StatsVO vo);

	List<StatsVO> selectStatLctrePageList(StatsVO vo);
	
	List<StatsVO> selectStatCtgryList(StatsVO vo);

	List<StatsVO> selectStatInstrctr1List(StatsVO vo);
	List<StatsVO> selectStatInstrctr2List(StatsVO vo);

	List<StatsVO> selectStatRoomList(StatsVO vo);


}

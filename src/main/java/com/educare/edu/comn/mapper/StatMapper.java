package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("StatMapper")
public interface StatMapper {

	List<Map<String, Object>> getRceptMonthMain(Map<String, Object> param);

	Map<String, String> getEduCntInfo(Map<String, Object> param);

	Map<String, String> getRceptStdntCntInfo(Map<String, Object> param);

	Map<String, String> getComplStdntCntInfo(Map<String, Object> param);

	List<Map<String, Object>> getTotalOnline(Map<String, Object> param);

	int getMyStatPlayEduCnt(String userId);

	int getMyStatComplEduCnt(String userId);

	int getMyStatRceptEduCnt(String userId);

	int getStdntCntByDetailCtgrySeq(Map<String, Object> param);

	int getPersonCntByDetailCtgrySeq(Map<String, Object> param);

}

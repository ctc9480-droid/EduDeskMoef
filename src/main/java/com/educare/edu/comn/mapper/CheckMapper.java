package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CheckMapper")
public interface CheckMapper {

	int selectCountLectureStndtByPk(Map<String, Object> param);

	int selectCountLectureByEduSeqGangsa(Map<String, Object> param);

	int selectCountLectureByEduSeqOrgCd(Map<String, Object> param);

	int useFeedbackCnt(int fbIdx);

	int selectCountLectureStndtByPassIdx(Map<String, Object> param);

	int selectLctrePassCtgryYearCnt(Map<String, Object> param);

	int selectCountLectureStndtByPass(Map<String, Object> param);

	int selectCountAssgnLectureRoom(Map<String, String> assgnVO);
}

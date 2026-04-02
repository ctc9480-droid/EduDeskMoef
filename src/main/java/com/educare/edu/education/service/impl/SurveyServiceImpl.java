package com.educare.edu.education.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.SurveyService;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.education.service.model.LectureSurvey;
import com.educare.edu.member.service.SessionUserInfoHelper;

/**
 * @Class Name : SurveyServiceImpl.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 10.
 * @version 1.0
 * @see
 * @Description 설문조사 서비스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 10.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Service("SurveyService")
public class SurveyServiceImpl implements SurveyService {
	
	/** 설문조사 Mapper */
	@Resource(name = "SurveyMapper")
	private SurveyMapper surveyMapper;
	
	/** 교육관리 Mapper */
	@Resource(name = "EduMapper")
	private EduMapper eduMapper;

	/**
	 * 설문조사를 저장한다.
	 * @param lectureSurvey
	 */
	@Override
	public void saveServey(LectureSurvey lectureSurvey) {
		lectureSurvey.setUserId(SessionUserInfoHelper.getUserId());
		lectureSurvey.setRgsde(new Date());
		surveyMapper.insertSurvey(lectureSurvey);
		
		LectureStdnt stdnt = new LectureStdnt();
		stdnt.setEduSeq(lectureSurvey.getEduSeq());
		stdnt.setUserId(SessionUserInfoHelper.getUserId());
		stdnt.setSurveyYn(LectureStdnt.STR_Y);
		eduMapper.updateLectureStdntSurvey(stdnt);	
	}

	/**
	 * 설문조사를 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public LectureSurvey getSurvey(EduVO vo) {
		return surveyMapper.selectSurvey(vo);
	}
	
	
}

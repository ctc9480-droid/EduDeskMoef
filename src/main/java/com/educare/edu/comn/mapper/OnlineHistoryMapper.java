package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.OnlineHistory;
import com.educare.edu.comn.model.Org;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("OnlineHistoryMapper")
public interface OnlineHistoryMapper {

	void insertByPk(OnlineHistory param2);

	List<OnlineHistory> selectByEduSeqTimeSeq(OnlineHistory param);

}

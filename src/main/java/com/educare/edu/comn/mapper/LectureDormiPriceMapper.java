package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.LectureDormiPrice;
import com.educare.edu.comn.model.LectureDormiStdnt;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.LectureDormiVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureDormiPriceMapper")
public interface LectureDormiPriceMapper {

	LectureDormiPrice selectByPk(LectureDormiPrice vo);
	List<LectureDormiPrice> selectByAll(LectureDormiPrice vo);
	int insertByPk(LectureDormiPrice vo);
	int updateByPk(LectureDormiPrice vo);


}

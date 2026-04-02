package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.Pay;
import com.educare.edu.comn.vo.PayVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("PayMapper")
public interface PayMapper {

	void updateAnyByPk(Pay param2);

	List<Pay> selectByVtWait();

	Pay selectByPk(String payNo);

	void insertByPk(PayVO payVo);

}

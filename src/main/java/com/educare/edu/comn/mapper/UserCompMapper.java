package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.AttendStdnt;
import com.educare.edu.comn.model.LectureMov;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.UserComp;
import com.educare.edu.comn.model.UserMemo;
import com.educare.edu.comn.vo.MovVO;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 *
 */
@Mapper("UserCompMapper")
public interface UserCompMapper {

	UserComp selectByPk(UserComp param3);

	void updateByPk(UserComp param3);

	void insertByPk(UserComp param3);
}

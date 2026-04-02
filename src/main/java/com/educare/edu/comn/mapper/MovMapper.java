package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.MovVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("MovMapper")
public interface MovMapper {

	List<MovVO> getMovList(MovVO param);

	int getCntByfileRenameOrgCd(MovVO param2);

	MovVO getMovInfo(MovVO param);

	int getMovTotalCnt(MovVO param);

	List<MovVO> getStdntProgressList(MovVO param);

}
 
package com.educare.edu.eduOld.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.educare.edu.eduOld.vo.EduOldVO;
import com.educare.edu.education.service.EduVO;

@Mapper("EduOldMapper")
public interface EduOldMapper {

	int selectEduOldPageCnt(EduVO vo);

	List<EduOldVO> selectEduOldPageList(EduVO vo);

}

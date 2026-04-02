package com.educare.edu.lectureBoard.mapper;
 
import java.util.List;

import com.educare.edu.bbs.service.model.BoardAttach;
import com.educare.edu.lectureBoard.vo.LectureBoardAttach;
import com.educare.edu.lectureBoard.vo.LectureBoardVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LectureBoardAttachMapper")
public interface LectureBoardAttachMapper {

	void insertLectureBopardAttach(LectureBoardAttach lectureBoardAttach);

	List<LectureBoardAttach> selectLectureBoardAttachList(LectureBoardAttach attachVo);

	LectureBoardAttach selectLectureBoardAttachMap(Integer fileSeq);

	void deleteLectureBoardAttach(int fileSeq);


} 

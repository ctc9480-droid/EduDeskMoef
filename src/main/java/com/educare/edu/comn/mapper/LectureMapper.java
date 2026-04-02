package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.education.service.model.Lecture;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureMapper")
public interface LectureMapper {

	Lecture selectByPk(Integer eduSeq);

	Lecture selectByTmEdu(Lecture lVO);

	void insertByPk(Lecture lVO);

	List<Lecture> selectByTmRcept(Lecture vo2);

	void deleteByPk(int eduSeq);

	void updateByPk(Lecture param);

	/**
	 * <pre>
	 * 배열 수업번호로 조회하여 설문지번호가 동일한지 그룹바이로 체크하는 쿼리
	 * 0:다른 설문지, 1이상:설문지번호
	 * </pre>
	 * @param eduSeqChk
	 * @return
	 */
	List<Integer> selectFbIdxByEduSeqArr(List<Integer> eduSeqArr);

	void updateByTmSeq(Lecture lctreVo);

}

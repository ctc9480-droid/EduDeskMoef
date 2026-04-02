package com.educare.edu.education.service.impl;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.vo.SubjectResultVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : EduMapper.java
 * @author SI개발팀 박용주
 * @since 2020. 7. 27.
 * @version 1.0
 * @see
 * @Description 교육관리 Mapper
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 27.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Mapper("EduMapper")
public interface EduMapper {
	
	int insertLctre(Lecture lecture);
	
	int selectLctreListMngrCnt(EduVO vo);
	
	List<Lecture> selectLctreListMngr(EduVO vo);
	
	Lecture selectLctreByPk(int eduSeq);
	
	List<Lecture> selectLctreMiniList(EduVO vo);
	
	List<Lecture> selectLctreUserList(EduVO vo);
	
	List<Lecture> selectLctreCalList(EduVO vo);
	
	List<Lecture> selectLctreBannerList(EduVO vo);
	
	int selectLctreRceptCnt(Integer eduSeq);
	
	LectureRcept selectLctreRceptByPk(EduVO vo);
	
	void deleteLectureRcept(EduVO vo);
	
	int selectLectureRceptListCnt(EduVO vo);
	
	List<LectureRcept> selectLectureRceptList(EduVO vo);
	
	int updateLctre(Lecture lecture);
	
	void updateLctreRceptClose(Lecture lecture);
	
	void updateLctreEduClose(Lecture lecture);
	
	int selectConfirmRceptCnt(Integer eduSeq);
	
	int selectRceptExceptMeCnt(EduVO vo);
	
	void insertLectureStdnt(LectureStdnt lectureStdnt);
	
	void deleteLectureStdnt(EduVO vo);
	
	int selectLectureStdntListCnt(EduVO vo);
	
	List<LectureStdnt> selectLectureStdntList(EduVO vo);
	
	List<LectureStdnt> selectLectureStdntListByEdu(Integer eduSeq);
	
	void updateLectureStdntScore(LectureStdnt lectureStdnt);
	
	void updateLectureStdntCertNum(LectureStdnt lectureStdnt);
	
	void updateLectureStdntIssuePass(LectureStdnt lectureStdnt);
	
	void updateLectureStdntIssueCompl(LectureStdnt lectureStdnt);
	
	void updateLectureStdntPass(LectureStdnt lectureStdnt);
	
	void updateLectureStdntSurvey(LectureStdnt lectureStdnt);
	
	int selectEduMemberListCnt(EduVO vo);
	
	List<LectureStdnt> selectEduMemberList(EduVO vo);
	
	/**
	 * eduseq,userId
	 * @param vo
	 * @return
	 */
	LectureStdnt selectLctreStdntByPk(EduVO vo);
	
	List<LectureRcept> selectRceptMiniList(Map<String, Object> param);
	
	List<LectureRcept> selectRceptExcelList(Integer eduSeq);
	
	List<LectureStdnt> selectStdntExcelList(Integer eduSeq);
	
	int selectInstrctrEduListCnt(EduVO vo);
	
	List<Lecture> selectInstrctrEduList(EduVO vo);

	void updateLectureRceptState(LectureRcept lc);

	int selectWaitRceptCnt(int eduSeq);

	List<LectureRcept> selectLctreRceptByEduSeq(Integer eduSeq);

	int selectLectureInstrctrListCnt(EduVO vo);

	List<Lecture> selectLectureInstrctrList(EduVO vo);

	void updateLectureFbIdx(Lecture param);

	int selectlctreStdntCntBySurveyY(int eduSeq);

	int selectMyEduCnt(EduVO vo);

	List<Lecture> selectMyEduList(EduVO vo);

	/**
	 * 썸네일 업데이트
	 * @param lecture
	 */
	void updateLctreThum(Lecture lecture);

	void updateLectureRceptSuccessPay(LectureRcept lr);

	void updateLectureRceptCancelPay(LectureRcept lr);

	List<Lecture> getResvLectureTimeByRoom(EduVO vo);

	void updateLectureInstrctr(Lecture lctre);

	/**
	 * 개인교육신청내역 페이지 카운트
	 * @param vo
	 * @return
	 */
	int selectLcrcpPageCnt(EduVO vo);

	/**
	 * 개인교육신청내역 페이지 리스트
	 * @param vo
	 * @return
	 */
	List<Lecture> selectLcrcpPageList(EduVO vo);

	/**
	 * eduSeq,userId별로 기타항목 한번에 조회
	 * @param vo
	 */
	List<Lecture> selectEtcIemDataList(EduVO vo);

	Lecture selectLcrcpInfo(EduVO eduVO);

	/**
	 * 과목결과리스트
	 * @param srVO
	 * @return
	 */
	List<SubjectResultVO> getLctreSubList(SubjectResultVO srVO);

}

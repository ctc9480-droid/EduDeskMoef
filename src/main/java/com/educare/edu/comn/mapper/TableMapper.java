package com.educare.edu.comn.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.CertNumVO;
import com.educare.edu.comn.vo.EmailVO;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.PayVO;
import com.educare.edu.comn.vo.SmsVO;
import com.educare.edu.comn.vo.TimeTableVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;

/**
 *
 */
@Mapper("TableMapper")
public interface TableMapper {

	List<Org> selectOrgList(Org param);

	OrgVO selectOrgByPk(String orgCd);

	void deleteLectureTimeByEduSeq(int eduSeq);
	void insertLectureTime(LectureTime timeMap);
	List<LectureTime> selectLectureTimeList(int eduSeq);
	void updateLectureTimeForGooroomeeId(LectureTime timeMap);
	
	// 20210916, Add, dhchoi, Zoom Url 업데이트
	void updateLectureTimeForZoomUrl(LectureTime timeMap);
	
	LectureTime selectLectureTimeMap(LectureTime lectureTime);
	
	/**
	 * 전자출결 사용여부 저장
	 * @param lectureParam
	 */
	void updateLectureForCheckYn(Lecture lectureParam);

	void insertOrgByPk(Org orgParam);

	void updateOrgByPk(Org orgParam);

	List<TimeTableVO> selectTimeTableList(TimeTableVO vo);


	void updateOrgByFileInfo(OrgVO orgParam);

	void updatePayByPk(PayVO payVo);
	PayVO selectPayByPk(String payNo);

	void insertSms(SmsVO sms);

	void insertSmsLog(SmsVO sms);
	void updateSmsLog(SmsVO sms);

	CertNumVO selectCertNumByPk(CertNumVO param);

	void insertCertNum(CertNumVO param);

	void updateCertNum(CertNumVO param);
	
	void updateLectureTimeForUrl(LectureTime param);
	
	/**
	 * sms테이블 오늘날짜 기준 으로 조회
	 * @param param
	 * @return
	 */
	List<SmsVO> selectSms(SmsVO param);

	void updateSms(SmsVO sms);

	void deleteSms(int idx);

	void deleteSmsByCancelRcept(SmsVO vo);
	void deleteSmsLogByCancelRcept(SmsVO vo);

	void updateSmsLogByCancelRcept(SmsVO vo);

	List<LectureRcept> selectLectureRceptByWait(int eduSeq);

	List<PayVO> selectPayByUserId(PayVO param);

	void insertMgov(SmsVO sms);
	
	void insertMgov2(EmailVO email);
}

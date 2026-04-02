package com.educare.edu.comn.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmartCheckVO;

/**
 * 전자출결 서비스 모음
 */
public interface SmartcheckService {
	
	/**
	 * 시간표 가져오기
	 * @param eduSeq
	 * @return
	 */
	List<SmartCheckVO> getTimeList(Integer eduSeq);
	
	List<SmartCheckVO> getStdntList(Integer eduSeq, String userId);

	List<SmartCheckVO> getDateList(Integer eduSeq);

	/**
	 * 수업,학생,일별 출결 리스트
	 * @param eduSeq
	 * @param studId
	 * @return
	 */
	List<SmartCheckVO> getStdntAttList(int eduSeq, String studId);

	/**
	 * 수업,학생,종합 정보
	 * @param eduSeq
	 * @param reqStudId
	 * @return
	 */
	SmartCheckVO getStdntAttInfo(int eduSeq, String reqStudId);


	/**
	 * 출결체크 화면에 사용할 학생리스트
	 * @param params
	 * @return
	 */
	List<SmartCheckVO> getStudentList(SmartCheckVO params);

	/**
	 * 강의 출결정보, 누적출석률
	 * @param params
	 * @return
	 */
	SmartCheckVO getEduAttInfo(SmartCheckVO params);

	/**
	 * 학생이 인증번호 제출하고 출결처리
	 * @param params
	 * @return
	 */
	int checkAuthNo(SmartCheckVO params);

	List<SmartCheckVO> getEduListByStud(SmartCheckVO params);

	/**
	 * <pre>
	 * 학생이 출석이벤트 발생 할때
	 * 로그도 같이 남겨야 함
	 * </pre>
	 * @param eduSeq
	 * @param timeSeq
	 * @param userId
	 * @param attCd
	 * @param log 
	 * @return
	 */
	ResultVO setAttendByStudent(int eduSeq, int timeSeq, String userId, String attCd, String log);

	/**
	 * 해당차시 전체 수정
	 * @param eduSeq
	 * @param timeSeq
	 * @param attCd
	 * @param string
	 * @return
	 */
	ResultVO setAttendByTime(int eduSeq, int timeSeq, String attCd, String string);

	/**
	 * 롤북가져오기
	 * @param eduSeq
	 * @return
	 */
	ResultVO getRollBook(int eduSeq,String userId);

	ResultVO getRollBookDay(int eduSeq, String userId);

	/**
	 * 일별 출결 저장
	 * @param eduSeq
	 * @param userId
	 * @param eduDt
	 * @param beginHhMm
	 * @param endHhMm
	 * @param attTp
	 * @return
	 */
	ResultVO saveAttDay(int eduSeq, String userId, String eduDt, Date beginDe, Date endDe, int attTp);

	/**
	 *
	 * @param eduSeq
	 * @param attTp : 0:교시별출석률 , 1:일별출석률
	 * @return
	 */
	double getAttendRatio(int eduSeq,int attTp);

	ResultVO saveAttDay_251023(int eduSeq, String userId, String eduDt, int attTp, Date at1De,Date at2De, Date at3De);
}

package com.educare.edu.education.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.TimeTableVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.util.XmlBean;
import com.google.gson.JsonArray;

/**
 * @Class Name : EduService.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 22.
 * @version 1.0
 * @see
 * @Description 교육관리 서비스 인터페이스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 22.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public interface EduService {
	
	public static final String EXCUTE_SUCCESS = "SUCCESS";
	public static final String EXCUTE_ERR_FILE = "파일저장 오류가 발생하였습니다.";
	public static final String IMG_PATH = XmlBean.getServerContextRoot() + "/upload/edu/img/";
	public static final String EXCEL_PATH = XmlBean.getServerContextRoot() + "/upload/edu/excel/";
	public static final Integer DEFAULT_PAGE_SIZE = 10;
	public static final Integer DEFAULT_ROW = 10;
	public static final Integer DETAIL_LIST_ROW = 5;
	public static final Integer MYPAGE_LIST_ROW = 10;
	public static final String AMS_COURSE_NM = "ISO/IEC 19011:2018";
	public static final String ISMS_COURSE_NM = "ISO/IEC 27001:2013";
	public static final String QMS_COURSE_NM = "ISO/IEC 9001:2015";
	public static final String AMS_FULL_NM = "Auditing Management Systems";
	public static final String ISMS_FULL_NM = "Information Security Management Systems";
	public static final String QMS_FULL_NM = "Quality Management Systems";
	
	/**
	 * 설정한 데이터 리스트를 Json 형태로 변환한다.
	 * @param dataList 설정한 데이터 리스트 
	 * @return Json 형태로 변환된 설정한 데이터 리스트
	 */
	public JsonArray convertDataListToJsonArray(List<?> dataList);
	
	/**
	 * 수업을 등록한다.
	 * @param lecture
	 * @param request
	 * @return
	 */
	public ResultVO saveLctre(Lecture lecture);
	
	/**
	 * 수업관리 리스트를 조회한다. 
	 * @param vo
	 * @return
	 */
	public Map<String, Object> getLctreListMngr(EduVO vo);
	
	/**
	 * 수업내용 상세조회
	 * @param eduSeq
	 * @return
	 */
	public Lecture getLctreDetail(int eduSeq);
	
	/**
	 * 메인화면용 교육일정
	 * @return
	 */
	public List<Lecture> getLctreMiniList(int srchCtgry);
	
	/**
	 * 사용자용 교육일정 조회
	 * @param vo
	 * @return
	 */
	public List<Lecture> getLctreUserList(EduVO vo);
	
	/**
	 * 전체교육일정용 
	 * @param srchColumn 
	 * @param srchWrd 
	 * @param srchCtgry3 
	 * @param srchCtgry2 
	 * @param vo
	 * @return
	 */
	public ResultVO getLctreYearInfo(String srchYear, int srchCtgry, int srchCtgry2, int srchCtgry3, String srchWrd, String srchColumn);
	
	/**
	 * 교육일정 배너 리스트
	 * @param vo
	 * @return
	 */
	public List<Lecture> getLctreBannerList(EduVO vo);
	
	/**
	 * 추후결제 교육신청
	 * 0:오류, 1:신청완료, 2:결제창으로
	 * @param userId
	 * @param mobile
	 * @param payType
	 * @param eduSeq
	 * @param useTrans 
	 * @param dormiAccessYn 
	 * @param dormiCapaCnt 
	 * @return
	 */
	public ResultVO saveLctreRceptAfterPay(MultipartHttpServletRequest request,String userId, String mobile,String email, String payType, Integer eduSeq, String etc, String etcIemDataJson
			,int rceptSeq,int feeTp, int dormiCapaCnt, String dormiAccessYn, int useTrans);
	
	/**
	 * 관리자용 교육 신청자 목록을 조회한다.
	 * @param vo
	 * @return
	 */
	public ResultVO getLectureRceptList(int rowCnt,int pageNo,int eduSeq,int srchRceptState,String srchWrd);
	
	/**
	 * 관리자용 교육 수강생 목록을 조회한다.
	 * @param vo
	 * @return
	 */
	public Map<String, Object> getLectureStdntInfo(EduVO vo);
	
	/**
	 * 단체 수강등록 처리
	 * @param vo
	 * @return
	 */
	public String bulkUserRgs(EduVO vo);
	
	/**
	 * 승인카운트를 조회한다.
	 * @param eduSeq
	 * @return
	 */
	public int getConfirmRceptCnt(Integer eduSeq);
	
	/**
	 * 강사 교육내역을 조회한다.
	 * @param vo
	 * @return
	 */
	public Map<String, Object> getInstrctrEduListList(EduVO vo);
	
	/**
	 * 마이페이지 교육신청 상세내용을 조회한다.
	 * @param vo
	 * @return
	 */
	public LectureRcept getMyRceptDetail(int rceptSeq,int eduSeq, String userId);
	
	/**
	 * 교육신청을 취소한다.
	 * @param eduSeq
	 * @return
	 */
	public ResultVO cancelRcept(int rceptSeq, int eduSeq,String userId);
	
	/**
	 * 교육 신청자 승인마감 처리.
	 * @param vo
	 */
	public String rceptClose(EduVO vo);
	
	/**
	 * <pre>
	 * 관리자에서 일괄 수강 취소 기능
	 * </pre>
	 * @param vo
	 */
	public void eduStdntCancel(EduVO vo);
	
	/**
	 * 수강생 성적 수정
	 * @param stdnt
	 */
	public void updScore(LectureStdnt stdnt);
	
	/**
	 * 교육종료
	 * @param vo
	 * @return
	 */
	public String eduClose(EduVO vo);
	
	/**
	 * 교육종료 취소
	 * @param vo
	 * @return
	 */
	public String eduCloseCancel(EduVO vo);
	
	/**
	 * 회원 이수내역을 조회한다.
	 * @param vo
	 * @return
	 */
	public Map<String, Object> getEduMemberList(EduVO vo);

	/**
	 * 마이페이지 교육수강 상세내용을 조회한다.
	 * @param vo
	 * @return
	 */
	public LectureStdnt getMyAtnlcDetail(int eduSeq,String userId);
	
	/**
	 * 교육신청 미니리스트를 조회한다.
	 * @param orgCd 
	 * @param row
	 * @return
	 */
	public List<LectureRcept> getRceptMiniList(String orgCd, int row);
	
	/**
	 * 증빙서류에 표시될 정보를 조회한다.
	 * @param vo
	 * @return
	 */
	public Map<String, Object> getCertInfo(int eduSeq, int subSeq, String userId);
	
	
	/**
	 * 수강생목록 엑셀파일을 생성한다.
	 * @param vo
	 * @return
	 */
	public String createStdntExcel(EduVO vo);

	public ResultVO setLectureStdnt(LectureRcept lrInfo) throws Exception;

	public Map<String, Object> getLectureInstrctrList(EduVO vo);
	
	/**
	 * 시간 저장
	 * @param eduSeq
	 * @param timeList
	 * @return
	 */
	public String saveLectureTime(int eduSeq, String checkYn, List<LectureTime> timeList);
	
	public List<LectureTime> getLectureTimeList(int eduSeq);

	/**
	 * 강의에 피드백 템플릿 저장
	 * @param eduSeq
	 * @param fbIdx
	 * @return
	 */
	public int loadFeedback(int eduSeq, int fbIdx);

	/**
	 * 강의에 피드백 템플릿 삭제
	 * @param eduSeq
	 * @return
	 */
	public int deleteEduFeedback(int eduSeq);

	/**
	 * 내(학생)시간표 가져오기
	 * @param Lecture,userId
	 * @return
	 */
	public List<TimeTableVO> getTimeTableList(Lecture lctre,String userId);

	/**
	 * 내 강의목록
	 * gubun:Open,Rcept,Stdnt
	 * @param vo
	 * @return
	 */
	public Map<String, Object> getMyEduList(EduVO vo);

	/**
	 * 동영상전용일때 수료여부 저장하기
	 * @param eduSeq
	 * @param userId
	 * @return
	 */
	public int setCheckPassByMov(int eduSeq, String userId);

	public int deleteRcept(Integer eduSeq);

	public List<LectureStdnt> getLectureStdntList(EduVO vo);

	/**
	 * 바로 수료처리
	 * @param lsparam
	 */
	public void setPass(LectureStdnt lsparam);

	public ResultVO saveLctreTime(int eduSeq, int copyEduSeq);
	
	//강제마감
	public ResultVO rceptCloseOnly(EduVO vo);
	//강제오픈
	public ResultVO rceptOpenOnly(EduVO vo);

	/**
	 * 강의실 사용 목록
	 * @param srchMonth 
	 * @param srchYear 
	 * @return
	 */
	public List<Lecture> getResvLectureRoom(int roomSeq, String srchYear, String srchMonth);

	/**
	 * 강의실,날짜별 교육 목록 조회
	 * @param eduDt
	 * @return
	 */
	public ResultVO getClassRoomInfoByEduDt(String eduDt);

	/**
	 * <pre>
	 * 신청내역 삭제
	 * 수강중이면 삭제 안됨, 대기거나, 취소상태만 삭제 가능
	 * </pre>
	 * @param eduSeq
	 * @param userId
	 * @return
	 */
	public ResultVO delRceptUser(int eduSeq, String userId,int rceptSeq);

	/**
	 * 대표강사,단체인경우 시간강사 변경하기
	 * @param eduSeq
	 * @param instrctrId
	 * @return
	 */
	public ResultVO setInstrctr(int eduSeq, String instrctrId);

	/**
	 * 관리자 > 개인교육신청내역 페이징목록
	 * @param vo
	 * @param i
	 * @return
	 */
	public ResultVO getLcrcpPageList(EduVO vo, int listRow);
	
	/**
	 * 신청서리스트로 기타항목 조회,lcrcpList에 기타항목값을 추가 세팅한다.
	 * @param lctreList
	 * @return
	 */
	public ResultVO setEtcIemDataList(List<Lecture> lcrcpList);

	/**
	 * 개인교육 신청서 조회
	 * @param eduSeq
	 * @param userId
	 * @return
	 */
	public ResultVO getLcrcpInfo(int eduSeq, String userId,int rceptSeq);

	public ResultVO updLctreRceptAfterPay(int rceptSeq,int feeTp,String etc);

	/**
	 * 개인 교육 신청서일자 변경
	 * @param rceptSeq
	 * @return
	 */
	public ResultVO updRceptNew(int rceptSeq);

	/**
	 * <pre>
	 * 시간표 추가저장,단건,반복 공용사용
	 * </pre>
	 * @param eduSeq
	 * @param timeTp 1:단건,2:반복
	 * @param startDtStr
	 * @param endDtStr
	 * @param weeks
	 * @param times
	 * @return
	 */
	public ResultVO addLectureTime(int eduSeq,int timeSeq, int timeTp, String startDtStr, String endDtStr,String eduDtStr, String[] weeks, String[] times);

	/**
	 * 시간표 삭제
	 * @param eduSeq
	 * @param timeSeq
	 * @return
	 */
	public ResultVO delLectureTime(int eduSeq, int timeSeq);

	public ResultVO getLectureTime(int eduSeq, int timeSeq);

	/**
	 * df
	 * @param rceptSeq
	 * @param compJson
	 * @param schoolListJson
	 * @param certifListJson
	 * @param languaListJson
	 * @param careerListJson
	 * @param bsnLcnsFile
	 * @param bizRgsTp 
	 * @param empSuccTp 
	 * @param learnCardTp 
	 * @param empInsrTp 
	 * @return
	 */
	public ResultVO saveLctreRceptAdd(int rceptSeq, String compJson, String schoolListJson, String certifListJson, String languaListJson, String careerListJson, MultipartFile bsnLcnsFile, int empInsrTp, int learnCardTp, int empSuccTp, int bizRgsTp);

	/**
	 * 학생 교육 종합 통계
	 * 수료건수
	 * @param userId
	 * @return
	 */
	public ResultVO getStdntEduStat(String userId);

	/**
	 * 과목 이수증,합격증 정보
	 * @param eduSeq
	 * @param userId
	 * @return
	 */
	public ResultVO getLctreSubResult(int eduSeq, String userId);

	/**
	 * 수료증 학생목록
	 * @param vo
	 * @return
	 */
	public ResultVO getLectureStdntCertList(EduVO vo);

	/**
	 * 과목 수료여부 저장
	 * @param eduSeq
	 * @param userId
	 * @param subSeq
	 * @param certMode
	 * @return
	 */
	public ResultVO setCertSub(int eduSeq, String userId, int subSeq, int certMode, int state);

	public List<Lecture> getLctreCalList(EduVO vo);
}

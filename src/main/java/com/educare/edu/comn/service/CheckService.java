package com.educare.edu.comn.service;

import java.util.HashMap;
import java.util.List;

import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoStdnt;

/**
 * <pre>
 * 어떤 것이든 확인할때 쓰는 서비스
 * 내가 듣는수업이 맞는지 , 수업은 어떤 상태인지 
 * </pre>
 */
public interface CheckService {

	/**
	 * <pre>
	 * 내가 수강한 과목이 맞는지 여부
	 * 0:아니다, 1:맞다
	 * </pre>
	 */
	int checkMySugang(int eduSeq, String userId);
	
	/**
	 * <pre>
	 * 내가 가르치는 수업이 맞는 여부
	 * 0:아니다, 1:맞다
	 * </pre>
	 */
	int checkMySuupByGangsa(int eduSeq, String userId);

	/**
	 * 기관이 생성한 수업이 맞는지 체크
	 * @param eduSeq
	 * @param userId
	 * @return
	 */
	int checkOrgByLecture(int eduSeq, String orgCd);

	/**
	 * 기관이 동영상 교육을 사용하는지
	 * 0:아니다, 1:맞다
	 * @param orgCd
	 * @return
	 */
	int checkOrgUseMov(String orgCd);

	/**
	 * <pre>
	 * 피드백이 수정또는 삭제 가능한 상태인지 체크
	 * 0:에러
	 * 1:통과
	 * 2:기관맞지않음
	 * 3:사용중
	 * </pre>
	 * @param fbIdx
	 * @return
	 */
	int checkUpdFeedback(int fbIdx,String orgCd);

	/**
	 * 해당수업이 전자출결을 사용하는지
	 * @param eduSeq
	 * @return
	 */
	int checkEduSeqUseCheck(Integer eduSeq);

	/**
	 * <pre>
	 * 로그인아이디 유효성 체크
	 * 
	 * </pre>
	 * @param loginId
	 * @return Hashmap
	 * result(0:실패,1:승인,2:사용가능하나 이메일인증필요),message
	 */
	ResultVO checkLoginId(String loginId);

	/**
	 * 패스워드 유효성 체크
	 * @param loginId
	 * @return
	 */
	HashMap<String, Object> checkUserPw(String userPw);

	/**
	 * 결제취소 가능한지 체크
	 * @param eduSeq
	 * @param userId
	 * @return 0:실패,1:가능(무료),2:미결제상태,3:수료함,4:접수기간지남,5:교육1일전 지남,6:가능(유료),7:교육시작됨
	 */
	ResultVO checkCancel(int rceptSeq,int eduSeq, String userId);

	int getCumNumByCtgry(int detailCtgrySeq,int subSeq, String eduPeriodBeginYear,int certMode);

	void checkPassCertIssue(LectureStdnt stdnt);

	/**
	 * 수업이 종료되었는지 체크
	 * @param lctre
	 * @return
	 */
	CheckVO checkEduClose(Lecture lctre);

	/**
	 * 해당수업과 동일한 과정을 같은 해에 수료했는지 여부
	 * @param lctre
	 * @param userId
	 * @return
	 */
	CheckVO checkDupYearCtgryPass(Lecture lctre, String userId);

	/**
	 * 신청 가능한지 
	 * @param lctre
	 * @param user
	 * @return 0:신청안됨, 1:일반으로신청 가능
	 */
	ResultVO checkEduRcept(Lecture lctre, UserInfo user);

	/**
	 * 비번과정 통과여부
	 * 작업 보류
	 * @param eduSeq
	 * @return
	 */
	ResultVO checkEduScrtyPass(int eduSeq);

	/**
	 * 신청가능 나이인지 체크
	 * @param age
	 * @param targets
	 * @return
	 */
	CheckVO checkRceptAge(int age, String targets);
	

	/**
	 * 관리자아이피접근 체크
	 * @return
	 */
	ResultVO checkAdminIp();

}

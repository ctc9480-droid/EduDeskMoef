package com.educare.edu.comn.service;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.comn.vo.PassCertVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduVO;

/** 
 * <pre>
 * 2023. 2. 23 		배현우	최초생성
 * </pre> 
 */ 
public interface AjaxService { 
	
	/**
	 * 수업관리 리스트를 조회한다.
	 * @param vo
	 * @return list
	 */
	public Map<String, Object> getLctreList(EduVO vo); 

	/**
	 * 설문지 리스트를 조회한다.
	 * @param String orgCd, EduVO eduVo
	 * @return List<FeedbackVO> 
	 */
	public List<FeedbackVO> getFeedbackList(String orgCd, EduVO eduVo);

	/**
	 * 수료증 리스트를 조회한다.
	 * @param eduVo
	 * @return List<PassCertVO> 
	 */
	public List<PassCertVO> getPassCertList(EduVO eduVo);

	/**
	 * 강의실 리스트를 조회한다.
	 * @param vo
	 * @return
	 */
	public List<LectureRoomVO> getLectureRoomList(LectureRoomVO vo);

	/**
	 * 엑셀다운로드 사유
	 * @param excelTy
	 * @param content
	 * @param excelEduSeq
	 * @return
	 */
	public ResultVO saveExcelLog(String excelTy, String content, int excelEduSeq);

	/**
	 * 일괄 노출,미노출 기능
	 * @param openYn
	 * @return
	 */
	public ResultVO saveOpenYnAll(String openYn,List<String> eduSeqArr);


	/**
	 * 학생 조회
	 * @param srchWrd
	 * @return
	 */
	public ResultVO getStdntList(String srchWrd);

	/**
	 * 환불신청
	 * @param userId
	 * @param rceptSeq
	 * @param rfndAccountNo 
	 * @param rfndAccountNm 
	 * @param rfndBankNm 
	 * @param rfndReqFee 
	 * @return
	 */
	public ResultVO refundRceptProc(String userId, int rceptSeq, int rfndReqFee, String rfndBankNm, String rfndAccountNm, String rfndAccountNo);

	/**
	 * 사용자가신청한 환불정보 조회
	 * @param rceptSeq
	 * @return
	 */
	public ResultVO getRefundRcept(int rceptSeq);

	public ResultVO getEtcOrgCdPage(String srchWrd,int page);

	public ResultVO getEtcGradeCdPage(String srchWrd, int pageNo);

	public ResultVO getNameCardInfo(Integer eduSeq, String userId);

	/**
	 * 식비 저장
	 * @param eduSeq
	 * @param mealFee
	 * @return
	 */
	public ResultVO setMealFee(int eduSeq, int mealFee, String userId);

	public ResultVO setDormiFee(int eduSeq, int dormiFee, String userId);

	public ResultVO setDepositYn(int eduSeq, String depositYn, String userId);
	
}

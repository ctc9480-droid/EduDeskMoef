package com.educare.edu.comn.service;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.model.LectureDormiPrice;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.LectureDormiVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.comn.vo.ResultVO;

/**
 * <pre>
 * 숙소관리 서비스
 * </pre>
 */
public interface LectureDormiService {

	ResultVO getLectureDormiPageList(LectureDormiVO vo);

	/**
	 * 숙소 상세
	 * @param roomSeq
	 * @return
	 */
	LectureDormiVO getLectureDormi(int dormiSeq);

	/**
	 * 저장 및 수정
	 * @param vo
	 * @return
	 */
	ResultVO saveLectureDormi(LectureDormiVO vo);

	ResultVO getLectureDormiList(int eduSeq,String userId);

	ResultVO setClassDormi(int eduSeq, List<Integer> dormiSeqs,String userId,int dormiSeq);

	/**
	 * 숙소배정현황명단
	 * @param eduSeq
	 * @return
	 */
	ResultVO getClassDormiStdntByEdu(int eduSeq ,int page);

	List<LectureDormiVO> getAssignLectureDormi(int dormiSeq, String srchYear, String srchMonth);

	ResultVO getClassDormiInfoByEduDt(String eduDt);

	ResultVO delClassDormi(int eduSeq, String userId);

	ResultVO deleteLectureDormi(int dormiSeq);

	/**
	 * 가격설정
	 * @param feeOff2
	 * @param feeOff4
	 * @param feePeak2
	 * @param feePeak4
	 * @param startDt
	 * @param endDt
	 * @return
	 */
	ResultVO savePriceProc(int feeOff2, int feeOff4, int feePeak2, int feePeak4, String startDt, String endDt);

	List<LectureDormiPrice> getLectureDormiPrice();
	
	/**
	 * 숙소요금 세팅
	 * @param eduSeq
	 * @return
	 */
	ResultVO setFeeDormi(int eduSeq);

}

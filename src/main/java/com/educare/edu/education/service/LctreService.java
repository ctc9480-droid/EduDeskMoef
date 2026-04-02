package com.educare.edu.education.service;

import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.ResultVO;

public interface LctreService {

	/**
	 * <pre>
	 * 신청서 상태값 변경 > 접수마감 여부 설정
	 * </pre>
	 * @param eduSeq
	 * @param userId
	 * @param state 
	 * @return 정상종료되면 메시지 없음
	 */
	ResultVO setRceptState(int rceptSeq, int eduSeq, String userId, int state);	
	
	/**
	 * 마감조건 확인하여 접수 상태 설정
	 * @param eduSeq
	 * @return
	 */
	String setRcept(int eduSeq);
	
	/**
	 * <pre>
	 * 접수 가능 여부 체크,내상태와 상관없이 수업자체의 상태임
	 * 0:에러,1:접수대기,2:접수중,3:접수마감,4:교육종료
	 * </pre>
	 * @param eduSeq
	 * @return
	 */
	CheckVO checkRcept(int eduSeq);

}

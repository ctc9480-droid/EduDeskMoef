package com.educare.edu.eduOld.service;

import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduVO;

/**
 * 구버전 교육관리시스템 서비스
 */
public interface EduOldService {
	public static final Integer DEFAULT_PAGE_SIZE = 10;

	/**
	 * 구버전 교육내역 페이징
	 * @param vo
	 * @param i
	 * @return	
	 */
	public ResultVO selectEduOldPageList(EduVO vo, int listRow);
	
	
}

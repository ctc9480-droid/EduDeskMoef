package com.educare.edu.accip.service;

import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.vo.ResultVO;

/**
 */
public interface AccipService {

	/**
	 * 아이피목록,페이징처리
	 * @param srchWrd
	 * @param page
	 * @return
	 */
	ResultVO getAccipPageList(String srchWrd, Integer page);

	ResultVO delAccipProc(int idx);
	
	/**
	 * 아이피 저장
	 * @param ip4
	 * @return
	 */
	ResultVO saveAccipProc(int idx,String ip4,String memo);

	AdminIp getAccip(Integer idx);
	
}

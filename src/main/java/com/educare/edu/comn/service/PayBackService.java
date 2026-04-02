package com.educare.edu.comn.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.PayVO;
import com.educare.edu.comn.vo.ResultVO;

public interface PayBackService {

	/**
	 * 학생의 환불계좌정보 가져오기
	 * @param userId
	 * @return
	 */
	ResultVO getPayBackByUserId(String userId);

	/**
	 * 환불계좌정보 저장하기
	 * @param bankNm
	 * @param accountNm
	 * @param accountNo
	 * @return
	 */
	ResultVO savePayBack(String userId,String bankNm, String accountNm, String accountNo);
	
}

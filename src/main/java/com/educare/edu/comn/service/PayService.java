package com.educare.edu.comn.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.comn.model.Agency;
import com.educare.edu.comn.vo.PayVO;

public interface PayService {
	/**
	 * <pre>
	 * 1. 신청 승인상태로 변경
	 * 2. 동영상전용인경우 수강생으로 등록
	 * 3. 결제정보 승인 저장
	 * </pre>
	 * @param eduSeq
	 * @param payNo
	 * @param userNm
	 * @param email
	 * @param amount
	 * @param payType
	 * @param tel
	 * @param mobile
	 * @param pgPayNo
	 * @param pgNm
	 * @return
	 */
	public int setSuccessPay(String payNo,String userId,int amount,int payType,String pgPayNo,String pgNm,String pgResultData
			);
	/**
	 * <pre>
	 * 1. 결제정보 취소 저장
	 * 2. 동영상 전용인경우 수강생 내역에서 삭제
	 * 3. 신청 대기 상태로 변경 > 신청서 삭제로 변경함
	 * </pre>
	 * @param eduSeq
	 * @return
	 */
	public int setCancelPay(int eduSeq,String payNo,String userId);
	
	public PayVO getPayInfo(int eduSeq,String userId);
	
}

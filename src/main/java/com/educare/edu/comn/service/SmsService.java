package com.educare.edu.comn.service;

import java.util.Date;
import java.util.List;

import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmsVO;

/**
 * sms 서비스 모음
 */
public interface SmsService {
	
	/**
	 * 전송대기 메시지 저장
	 * @param sms
	 */
	public CheckVO setSendSms(String fromNum,String toNum,String message,Date sendDt,int eduSeq,String userid,int sendType,String tplCd);
	
	/**
	 * <pre>
	 * 메시지 가져오기
	 * tpNo:메시지템플릿 번호
	 * tpValues:템플릿에 들어갈 값 
	 * </pre>
	 * @param tpNo
	 * @param tpValues
	 * @return
	 */
	public String getMsg(int tpNo,String[] tpValues);

	/**
	 * 발송대기중인 sms 가져오기
	 * @return
	 */
	public List<SmsVO> getSendSms();

	/**
	 * 교육취소시 sms 삭제
	 * @param eduSeq
	 * @param userId
	 */
	public void removeSms(Integer eduSeq, String userId);

	/**
	 * sms발송 리스트
	 * @param userId
	 * @param listRow
	 * @param page
	 * @param srchBeginDt 
	 * @return
	 */
	public ResultVO getSmsLogPageList(int listRow, int page, String srchBeginDt,String srchWrd);

	public ResultVO sendSmsProc(String fromNum, String toNum, String msg);
}

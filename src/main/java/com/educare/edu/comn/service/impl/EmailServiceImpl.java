package com.educare.edu.comn.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.educare.component.UtilComponent;
import com.educare.edu.comn.mapper.EmailMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.service.EmailService;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.EmailVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.util.SendEmail;
import com.educare.util.SendEmailVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 *
 *
 */
@Service("EmailService")
public class EmailServiceImpl implements EmailService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class.getName());

	@Resource(name="TableMapper")
	private TableMapper tableMapper;
	/** 교육관리 Mapper */
	@Resource(name = "EduMapper")
	private EduMapper eduMapper;
	/** 회원 Mapper */
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	@Resource(name = "LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	@Resource(name = "EmailMapper")
	private EmailMapper emailMapper;
	/** utilcomponent */
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	private static final String FROM_NUM = "0808080114";
	
	@Override
	public CheckVO setSendEmail(String fromAddr, String toAddr, String message, Date sendDt, int eduSeq, String userId, int sendType, String tplCd) {
		CheckVO result = new CheckVO();
		result.setResult(0);
		try {
			if(!toAddr.matches("^[_a-zA-Z0-9._%+-]+@[_a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
				result.setMsg("수신주소가 잘못되었습니다.");
				result.setResult(0);
				return result;
			}
			
			//이메일주소 30자리 초과면 중지
			if(toAddr.length() > 30) {
				result.setMsg("수신주소가 잘못되었습니다.");
				result.setResult(0);
				return result;
			}
			
			SendEmailVo vo = new SendEmailVo();
			vo.setFromEmail(fromAddr);
			vo.setToEmail(toAddr);
			vo.setSubject("이메일발송");
			vo.setMessage(message);
			LOG.info("[이메일발송]"+vo.getFromEmail()+"|"+vo.getToEmail()+"|"+vo.getSubject()+"|"+vo.getMessage());
			String emailResult = SendEmail.sendEmail(vo);
			if(StringUtils.isEmpty(emailResult)){
//				req.getSession().setAttribute("authNo", authNo);
//				req.getSession().setAttribute("authEmail", email);
//				req.getSession().setAttribute("authYn", "N");
				result.setResult(1);
			}

			EmailVO email = new EmailVO();
			
			email.setSendType(sendType);
			email.setResultCd(result.getResult());
			email.setText(message);
			email.setFromAddr(fromAddr);
			email.setToAddr(toAddr);
			email.setRegDt(new Date());
			email.setSendDt(new Date());
			email.setEduSeq(eduSeq);
			email.setUserId(userId);
			email.setTplCd(tplCd);
			
			Map<String, String> messageMap = new HashMap<String,String>();
			messageMap.put("text", message);
			ObjectMapper om = new ObjectMapper();
        	String messageJson = om.writeValueAsString(messageMap);
			
        	email.setMessage2(messageJson);
			if(sendDt!=null){
				email.setSendDt(sendDt);
			}
			LOG.info("messageJson : {}",messageJson);
			//tableMapper.insertSms(sms);
			//tableMapper.insertSmsLog(sms); 
			tableMapper.insertMgov2(email);
			
			result.setResult(1);
		} catch (NullPointerException | JsonProcessingException e) {
			e.printStackTrace();
			result.setResult(0);
		}
		return result;
	}

//	@Override
//	public String getMsg(int tpNo, String[] tpValues) {
//		try {
//			/*String[] tpArr = {"{{0}}이 신청되었습니다.\n감사합니다.\n-일시 : {{1}}\n-장소 : {{2}}"
//					,"{{0}}이 수료처리되었습니다.\n안전교육시스템에서 만족도 조사 후 수료증 발급이 가능합니다.\n감사합니다."
//					,"내일 {{0}}이 진행될 예정입니다.\n이론과정(온라인)을 수강하시고 참석하여 주시기 바랍니다.\n감사합니다. \n-일시 : {{1}} \n-장소 : {{2}} \n-기타 유의사항 : 마스크 착용, 강의실 내 취식금지, 실습 대비 편한 복장, 대중교통 이용 권장(주차비 자부담)"
//			};*/
//			String[] tpArr = {
//					"{{0}}님,{{1}}, 예약 되었습니다."
//					,"{{0}}님,{{1}}, 결제완료 되었습니다."
//					,"{{0}}님,{{1}}, 예약취소 되었습니다."
//					,"{{0}}님,{{1}}, 결제취소 되었습니다."
//					,"{{0}}님,{{1}}, 결제기한 만료로 자동취소 되었습니다."
//					,"{{0}}님\n{{2}}\n{{1}}, 신청 되었습니다."
//					,"{{0}}님\n{{2}}\n{{1}}, 신청취소 되었습니다."
//			};
//			String resultMsg = tpArr[tpNo];
//			for(int i=0;i<tpValues.length;i++){
//				resultMsg=resultMsg.replaceAll("\\{\\{"+i+"\\}\\}", tpValues[i]);
//			}
//			LOG.info("=문자 =\n"+resultMsg);
//			return resultMsg;
//		} catch (NullPointerException e) {
//			return null;
//		}
//	}

//	@Override
//	public List<SmsVO> getSendSms() {
//		try {
//			SmsVO param = new SmsVO();
//			param.setSendDt(new Date());
//			
//			List<SmsVO> smsList =  tableMapper.selectSms(param);
//			return smsList;
//		} catch (NullPointerException e) {
//			return null;
//		}
//	}

//	@Override
//	public void removeSms(Integer eduSeq, String userId) {
//		SmsVO vo = new SmsVO();
//		vo.setEduSeq(eduSeq);
//		vo.setUserId(userId);
//		tableMapper.deleteSmsByCancelRcept(vo);
//		vo.setResultMsg("교육신청취소");
//		vo.setResultCd(3);
//		tableMapper.updateSmsLogByCancelRcept(vo);
//		
//	}

	@Override
	public ResultVO getEmailLogPageList(int listRow, int page, String srchBeginDt, String srchWrd) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String, Object>();
		result.setData(rstData);
		try {
			
			EmailVO vo = new EmailVO();
			vo.setSrchWrd(srchWrd);
			
//			vo.setTableMonth(DateUtil.getDate2Str(new Date(), "yyyyMM"));
			if(!ObjectUtils.isEmpty(srchBeginDt)) {
				String srchBeginDt2 = srchBeginDt.replaceAll("-", "");
//				vo.setTableMonth(srchBeginDt2.substring(0,6));
				vo.setSrchBeginDt(srchBeginDt2);
			}
			System.out.println("srchBeginDt2 : "+vo.getSrchBeginDt());
			
			vo.setRow(listRow);
			vo.setPage(page);
			
			//총 갯수
			int totalCnt = emailMapper.selectEmailLogPageCnt(vo);
			
			//페이징처리
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
	        paginationInfo.setCurrentPageNo(vo.getPage());
	        paginationInfo.setRecordCountPerPage(vo.getRow());
	        paginationInfo.setPageSize(10);
	        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			
			//단체신청명단
			List<Map<String, Object>> emailList = emailMapper.selectEmailLogPageList(vo);
			
			rstData.put("emailList", emailList);
			rstData.put("pageNavi",paginationInfo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}


//	@Override
//	public ResultVO sendSmsProc(String fromNum, String toNum, String msg) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}

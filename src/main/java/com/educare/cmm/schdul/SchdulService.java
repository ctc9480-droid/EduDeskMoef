package com.educare.cmm.schdul;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.educare.component.DbTableSms;
import com.educare.component.EhCacheComponent;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureRceptMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.PayMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.Pay;
import com.educare.edu.comn.service.SmsService;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmsVO;
import com.educare.edu.comn.web.AdminExcelController;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

@Component
public class SchdulService {
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SchdulService.class.getName());

	@Resource(name = "SmsService")
	private SmsService smsService;
	@Resource(name = "TableMapper")
	private TableMapper tableMapper;
	@Resource(name = "PayMapper")
	private PayMapper payMapper;
	@Resource(name = "LectureStdntMapper")
	private LectureStdntMapper lectureStdntMapper;
	@Resource(name = "LectureRceptMapper")
	private LectureRceptMapper lectureRceptMapper;
	@Resource(name = "LectureMapper")
	private LectureMapper lectureMapper;
	@Resource(name = "eccp")
	private EhCacheComponent eccp;
	@Resource(name = "dbTableSms")
	private DbTableSms dbTableSms;

	/**
	 * 스케쥴러
	 **/

	@Scheduled(cron = "*/30 * * * * ?")//30초마다
	// @Scheduled( cron = "0 0 10 * * ?" ) //하루 한번 10시
	public void sendSms() {
		String ip = LncUtil.getServerIp();
		//LOG.info("ip : "+ip);
		try {
			if (ip.equals("10.16.1.157")) {
				
				List<SmsVO> smsList = smsService.getSendSms();
				for (SmsVO sms : smsList) {
					//String toNum = sms.getDecToNum();//개인정보때문에 주석처리, 통합db에서 가져온다.
					String toNum = eccp.getUserPrvt(sms.getUserId()).getMobile();
					toNum = toNum.replaceAll("-", "");
					ResultVO result = new ResultVO();
					LOG.info(sms.getSendType()+"");
					if (sms.getSendType() == 1) {
						//result = SuremSms.sendLms(sms.getFromNum(), toNum, sms.getMessage());
						LOG.info("문자발송인 경우");
					} else if (sms.getSendType() == 3) {
						String msg = sms.getMessage();
						String tplCd = sms.getTplCd();
						result = dbTableSms.sendAlimTalk(toNum, msg, tplCd);
					}
					
					sms.setResultCd(result.getResult());
					sms.setResultMsg(result.getMsg());
					if (result.getResult() == 1) {
						tableMapper.updateSmsLog(sms);// 내역 상태값 변경
						tableMapper.deleteSms(sms.getIdx());
					} else {
						tableMapper.updateSmsLog(sms);// 내역 상태값 변경
						tableMapper.updateSms(sms);
					}
				}
				
			}
		} catch (NullPointerException e) {
			LOG.error("문자 발송 처리 중 에러");
		}
	}

}

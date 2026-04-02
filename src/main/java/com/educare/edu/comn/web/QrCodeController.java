package com.educare.edu.comn.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.QrCodeMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.QrCode;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.CryptoSeedUtil;
import com.educare.util.DateUtil;

@Controller
@RequestMapping(value="/qrCode")
public class QrCodeController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(QrCodeController.class.getName());
	
	@Resource(name="QrCodeMapper")
	private QrCodeMapper qrCodeMapper;
	@Resource(name="SmartcheckService")
	private SmartcheckService smartcheckService;
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	@Resource(name="TableMapper")
	private TableMapper tableMapper;
	@Resource(name="SmartCheckMapper")
	private SmartCheckMapper smartCheckMapper;
	
	
	@RequestMapping(value="/attend/set.do")
	public String attendMap(ModelMap model,HttpSession session,HttpServletRequest req, HttpServletResponse res,RedirectAttributes rdat
			,@RequestParam(required=false) String data
			) {
		String datap = data;
		Date now = new Date();
		int eduSeq=0;
		String userId = SessionUserInfoHelper.getUserId();
		LOG.debug("data : "+datap);
		try {
			LOG.debug("qrData1 : "+session.getAttribute("qrData"));
			if(!SessionUserInfoHelper.isLogined()){
				//Cookie cookie = new Cookie("qrData", datap);
				//cookie.setSecure(true);
				//res.addCookie(cookie);
				datap = URLEncoder.encode(datap,"utf-8");
				rdat.addAttribute("qrData",datap);
				rdat.addAttribute("rtnUri","/qrCode/attend/set.do?a=1");
				return "redirect:/user/login.do?rtnUri=/qrCode/attend/set.do?a=1";
			}
			
			if(ObjectUtils.isEmpty(datap)){
				/*Cookie[] cookies = req.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						String name = cookie.getName();
						String value = cookie.getValue();
						// 필요한 작업 수행
						if("qrData".equals(name)){
							LOG.debug("qrData2 : "+value);
							datap = value;
							break;
						}
					}
				}*/
			}
			
				
			//data = URLEncoder.encode(data, "UTF-8");
			
			String dataDec = CryptoSeedUtil.decrypt(datap);
			LOG.debug("data_dec : "+dataDec);
			String[] dataArr = dataDec.split(",", 4);
			eduSeq = Integer.parseInt(dataArr[0]);
			String qrKey = dataArr[1];
			String qrType = dataArr[2];
			String qrDt = dataArr[3];//교육일별 qr사용을 위해
			
			QrCode param = new QrCode();
			param.setEduSeq(eduSeq);
			param.setQrType(qrType);
			param.setQrDt(qrDt);
			QrCode qr = qrCodeMapper.selectByAttend(param);
			if(!qr.getQrKey().equals(qrKey)){
				model.addAttribute("moveUrl","/");
				model.addAttribute("message","잘못된 QR코드입니다.");
				return "alert";
			}
			
			//본인교육 확인
			EduVO vo = new EduVO();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			LectureStdnt stdnt = eduMapper.selectLctreStdntByPk(vo);
			if(stdnt==null){
				model.addAttribute("moveUrl","/");
				model.addAttribute("message","내가 신청한 수업이 아닙니다. QR을 다시 확인해 주세요");
				return "alert";
			}
			
			if(stdnt.getState() != 2){
				model.addAttribute("moveUrl","/");
				model.addAttribute("message","내가 신청한 수업이 아닙니다. QR을 다시 확인해 주세요");
				return "alert";
			}
			
			String eduDt = DateUtil.getDate2Str(new Date(), "yyyyMMdd");
			
			//해당일자의 시작시간 ,종료시간 조회
			SmartCheckVO vo2 = new SmartCheckVO();
			vo2.setEduSeq(eduSeq);
			vo2.setEduDt(eduDt);
			SmartCheckVO attDay = smartCheckMapper.selectAttDay(vo2);
			if(attDay == null){
				model.addAttribute("moveUrl","/");
				model.addAttribute("message","출결 가능 시간이 없습니다.");
				return "alert";
			}
			
			
			int attTp = 1;//일반
			Date beginDe = null;
			Date endDe = null;
			if("attendBegin".equals(qrType)){
				beginDe = now;
			}
			if("attendEnd".equals(qrType)){
				endDe = now;
			}
			
			//오전,점심,오후 qr추가로직-251023
			Date at1De = null;
			Date at2De = null;
			Date at3De = null;
			if("1".equals(qrType)){
				at1De = now;
			}else if ("2".equals(qrType)){
				at2De = now;
			}else if ("3".equals(qrType)){
				at3De = now;
			}
			
			ResultVO result = smartcheckService.saveAttDay_251023(eduSeq, userId, eduDt,attTp,at1De,at2De,at3De);
			if(result.getResult() != 1){
				model.addAttribute("moveUrl","/");
				model.addAttribute("message",result.getMsg());
				return "alert";
			}
			
			String msgAttend = result.getMsg();
			
			//
			//Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
		
			//model.addAttribute("moveUrl","/user/mypage/myEduOpenView.do?eduSeq="+eduSeq);
			model.addAttribute("moveUrl","/user/mypage/myEduOpenList.do");
			model.addAttribute("message",msgAttend);
			return "alert";
		} catch (NullPointerException | UnsupportedEncodingException e) {
			model.addAttribute("moveUrl","/");
			model.addAttribute("message","에러가 발생하였습니다. 관리자에게 문의하세요");
			return "alert";
		}
		
		
	}
	
	@RequestMapping(value="/feedback/set.do")
	public String feedbackMap(ModelMap model,HttpSession session,HttpServletRequest req, HttpServletResponse res,RedirectAttributes rdat
			,@RequestParam(required=false) String data
			) {
		String datap = data;
		Date now = new Date();
		int eduSeq = 0;
		int feSeq = 0;
		String userId = SessionUserInfoHelper.getUserId();
		LOG.debug("data : "+datap);
		try {
			LOG.debug("qrData1 : "+session.getAttribute("qrData"));
			if(!SessionUserInfoHelper.isLogined()){
				//Cookie cookie = new Cookie("qrData", datap);
				//cookie.setSecure(true);
				//res.addCookie(cookie);
				datap = URLEncoder.encode(datap,"utf-8");
				rdat.addAttribute("qrData",datap);
				rdat.addAttribute("rtnUri","/qrCode/feedback/set.do?a=1");
				return "redirect:/user/login.do?rtnUri=/qrCode/feedback/set.do?a=1";
			}
			
			if(ObjectUtils.isEmpty(datap)){
				/*Cookie[] cookies = req.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						String name = cookie.getName();
						String value = cookie.getValue();
						// 필요한 작업 수행
						if("qrData".equals(name)){
							LOG.debug("qrData2 : "+value);
							datap = value;
							break;
						}
					}
				}*/
			}
			
				
//			byte[] decBytes = java.util.Base64.getUrlDecoder().decode(datap);
//			String decData1 = new String(decBytes, StandardCharsets.UTF_8);
//			
//			LOG.debug("decData : "+decData1);
			
			String decData = CryptoSeedUtil.decrypt(datap);
			LOG.debug("decData : "+decData);
			String[] dataArr = decData.split(",", 6);
			eduSeq = Integer.parseInt(dataArr[0]);
			feSeq = Integer.parseInt(dataArr[1]);
			String qrKey = dataArr[2];
			String qrType = dataArr[3];
			String qrStartDt = dataArr[4];
			String qrEndDt = dataArr[5];
			
			QrCode param = new QrCode();
			param.setEduSeq(eduSeq);
//			param.setFeSeq(feSeq);
			param.setQrType(qrType);
			QrCode qr = qrCodeMapper.selectByFeedback(param);
			if(!qr.getQrKey().equals(qrKey)){
				model.addAttribute("moveUrl","/");
				model.addAttribute("message","잘못된 QR코드입니다.");
				return "alert";
			}
			
			//본인교육 확인
			EduVO vo = new EduVO();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			LectureStdnt stdnt = eduMapper.selectLctreStdntByPk(vo);
			if(stdnt==null){
				model.addAttribute("moveUrl","/");
				model.addAttribute("message","내가 신청한 수업이 아닙니다. QR을 다시 확인해 주세요");
				return "alert";
			}
			
			if(stdnt.getFeResultState() == 2){
				model.addAttribute("moveUrl","/");
				model.addAttribute("message","완료된 설문입니다. QR을 다시 확인해 주세요");
				return "alert";
			}
			
//			String eduDt = DateUtil.getDate2Str(new Date(), "yyyyMMdd");
			Date qrBeginDe = DateUtil.getStr2Date(qrStartDt, "yyyy-MM-dd HH:mm");
			Date qrEndDe = DateUtil.getStr2Date(qrEndDt, "yyyy-MM-dd HH:mm");
			
			//해당일자의 시작시간 ,종료시간 조회
//			SmartCheckVO vo2 = new SmartCheckVO();
//			vo2.setEduSeq(eduSeq);
//			vo2.setEduDt(eduDt);
//			SmartCheckVO attDay = smartCheckMapper.selectAttDay(vo2);
//			if(attDay == null){
			if (qrBeginDe.compareTo(now) > 0 || qrEndDe.compareTo(now) < 0) {
				model.addAttribute("moveUrl","/");
				model.addAttribute("message","설문 가능 시간이 아닙니다.");
				return "alert";
			}
			
			
//			int attTp = 1;//일반
//			Date beginDe = null;
//			Date endDe = null;
//			if("attendBegin".equals(qrType)){
//				beginDe = now;
//			}
//			if("attendEnd".equals(qrType)){
//				endDe = now;
//			}
			
			//오전,점심,오후 qr추가로직-251023
//			Date at1De = null;
//			Date at2De = null;
//			Date at3De = null;
//			if("1".equals(qrType)){
//				at1De = now;
//			}else if ("2".equals(qrType)){
//				at2De = now;
//			}else if ("3".equals(qrType)){
//				at3De = now;
//			}
			
//			ResultVO result = smartcheckService.saveAttDay_251023(eduSeq, userId, eduDt,attTp,at1De,at2De,at3De);
//			if(result.getResult() != 1){
//				model.addAttribute("moveUrl","/");
//				model.addAttribute("message",result.getMsg());
//				return "alert";
//			}
			
//			String msgAttend = result.getMsg();
			
			//
			//Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
		
			//model.addAttribute("moveUrl","/user/mypage/myEduOpenView.do?eduSeq="+eduSeq);
//			model.addAttribute("moveUrl","/user/feedback/feedbackPopup.do?eduSeq="+eduSeq+"&feSeq="+feSeq);
//			model.addAttribute("message",msgAttend);
//			return "alert";
			return "redirect:/user/feedback/feedbackPopup.do?eduSeq="+eduSeq+"&feSeq="+feSeq+"&mobile=Y";
		} catch (NullPointerException | UnsupportedEncodingException e) {
			model.addAttribute("moveUrl","/");
			model.addAttribute("message","에러가 발생하였습니다. 관리자에게 문의하세요");
			return "alert";
		}
		
		
	}
}

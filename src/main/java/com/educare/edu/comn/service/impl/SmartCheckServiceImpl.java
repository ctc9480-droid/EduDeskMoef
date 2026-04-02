package com.educare.edu.comn.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.educare.edu.comn.mapper.AttendDayStdntMapper;
import com.educare.edu.comn.mapper.AttendStdntMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.QrCodeMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.model.AttendDayStdnt;
import com.educare.edu.comn.model.AttendStdnt;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.QrCode;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.smartCheck.SmartCheckUtil;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

@Service("SmartcheckService")
public class SmartCheckServiceImpl implements SmartcheckService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SmartCheckServiceImpl.class.getName());

	@Resource(name="SmartCheckMapper")
	private SmartCheckMapper smartCheckMapper;
	@Resource(name="LectureStdntMapper")
	private LectureStdntMapper lectureStdntMapper;
	@Resource(name="LectureMapper")
	private LectureMapper lectureMapper;
	@Resource(name = "AttendStdntMapper")
	private AttendStdntMapper attendStdntMapper;
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	@Resource(name="AttendDayStdntMapper")
	private AttendDayStdntMapper attendDayStdntMapper;
	@Resource(name="QrCodeMapper")
	private QrCodeMapper qrCodeMapper;

	@Override
	public List<SmartCheckVO> getTimeList(Integer eduSeq) {
		SmartCheckVO smartCheckParam = new SmartCheckVO();
		smartCheckParam.setEduSeq(eduSeq);
		List<SmartCheckVO> timeList = smartCheckMapper.selectLectureTimeByLectCd(smartCheckParam);
		return timeList;
	}

	@Override
	public List<SmartCheckVO> getStdntList(Integer eduSeq, String userId) {
		SmartCheckVO smartCheckParam = new SmartCheckVO();
		smartCheckParam.setEduSeq(eduSeq);
		smartCheckParam.setUserId(userId);
		List<SmartCheckVO> stdntList = smartCheckMapper.selectAttList(smartCheckParam);
		for(SmartCheckVO stdntMap:stdntList){
			smartCheckParam.setReqStudId(stdntMap.getUserId());
			List<SmartCheckVO> stdntAttList = smartCheckMapper.getStdntAttList(smartCheckParam);
			stdntMap.setStdntAttList(stdntAttList);
		}
		
		return stdntList;
	}

	@Override
	public List<SmartCheckVO> getDateList(Integer eduSeq) {
		SmartCheckVO smartCheckParam = new SmartCheckVO();
		smartCheckParam.setEduSeq(eduSeq);
		List<SmartCheckVO> dateList = smartCheckMapper.getLectDtByLectCd(smartCheckParam);
		return dateList;
	}

	@Override
	public List<SmartCheckVO> getStdntAttList(int eduSeq, String reqStudId) {
		try {
			SmartCheckVO param = new SmartCheckVO();
			param.setEduSeq(eduSeq);
			param.setReqStudId(reqStudId);
			List<SmartCheckVO> attList = smartCheckMapper.getStdntAttList(param);
			return attList;
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	public SmartCheckVO getStdntAttInfo(int eduSeq, String reqStudId) {
		try {
			SmartCheckVO param = new SmartCheckVO();
			param.setEduSeq(eduSeq);
			param.setReqStudId(reqStudId);
			SmartCheckVO attInfo = smartCheckMapper.getStdntAttInfo(param);
			return attInfo;
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	public List<SmartCheckVO> getStudentList(SmartCheckVO params) {
		List<SmartCheckVO> studentList = new ArrayList<>();
		try {
			studentList = smartCheckMapper.getStudentList(params);
			return studentList;
		} catch (NullPointerException e) {
			return studentList;
		}
	}
	
	@Override
	public SmartCheckVO getEduAttInfo(SmartCheckVO params) {
		SmartCheckVO attInfo = smartCheckMapper.getEduAttInfo(params);
		return attInfo;
	}

	@Override
	public int checkAuthNo(SmartCheckVO params) {
		try {
			LectureTime ltParam = new LectureTime(params.getEduSeq(),params.getTimeSeq());
			LectureTime ltInfo = lectureTimeMapper.selectByPk(ltParam);
			
			if(!params.getAuthNo().equals(ltInfo.getAuthNo())){
				return 2;//인증번호 틀리다.
			}
			
			AttendStdnt params1 = new AttendStdnt(params.getEduSeq(), params.getTimeSeq(), SessionUserInfoHelper.getUserId(), "O", DateUtil.getCalendar().getTime());
			AttendStdnt info = attendStdntMapper.selectByPk(params1);
			if(info!=null){
				attendStdntMapper.updateByPk(params1);
			}else{
				attendStdntMapper.insertByPk(params1);
			}
			
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public List<SmartCheckVO> getEduListByStud(SmartCheckVO params) {
		try {
			List<SmartCheckVO> list = smartCheckMapper.getEduListByStud(params);
			return list;
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	public ResultVO setAttendByStudent(int eduSeq, int timeSeq, String userId, String attCd, String log) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String, Object>();
		result.setData(rstData);
		try {
			AttendStdnt asParam = new AttendStdnt();
			asParam.setEduSeq(eduSeq);
			asParam.setTimeSeq(timeSeq);
			asParam.setUserId(userId);
			asParam.setAttCd(attCd);
			asParam.setRegDate(DateUtil.getCalendar().getTime());
			
			AttendStdnt asInfo = attendStdntMapper.selectByPk(asParam);
			if(asInfo!=null){
				attendStdntMapper.updateAttCdByPk(asParam);
			}else{
				attendStdntMapper.insertByPk(asParam);
			}
			
			//여기다 로그
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO setAttendByTime(int eduSeq, int timeSeq, String attCd, String log) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String, Object>();
		result.setData(rstData);
		try {
			
			//if(!"O,X".contains(attCd)){
			//	result.setResult(2);
			//	return result;
			//}
			
			AttendStdnt asParam = new AttendStdnt();
			asParam.setEduSeq(eduSeq);
			asParam.setTimeSeq(timeSeq);
			asParam.setAttCd(attCd);
			asParam.setRegDate(DateUtil.getCalendar().getTime());
			
			List<LectureStdnt> stdntList = lectureStdntMapper.selectByEduSeq(eduSeq);
			
			for(LectureStdnt o : stdntList){
				asParam.setUserId(o.getUserId());
				AttendStdnt asInfo = attendStdntMapper.selectByPk(asParam);
				if(asInfo!=null){
					attendStdntMapper.updateAttCdByPk(asParam);
				}else{
					attendStdntMapper.insertByPk(asParam);
				}
			}
			
			//여기다 로그
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg(e.getMessage());
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getRollBook(int eduSeq,String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//날짜
			List<SmartCheckVO> dateList = getDateList(eduSeq);
			// 시간
			List<SmartCheckVO> timeList = getTimeList(eduSeq);

			//시간표
			List<SmartCheckVO> stdntList = getStdntList(eduSeq,userId);
			
			rstData.put("dateList", dateList);
			rstData.put("timeList", timeList);
			rstData.put("stdntList", stdntList);

			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getRollBookDay(int eduSeq, String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//학생목록
			LectureStdnt lsVO = new LectureStdnt();
			lsVO.setEduSeq(eduSeq);
			lsVO.setState(2);
			lsVO.setUserId(userId);
			List<LectureStdnt> stdntList = lectureStdntMapper.selectByParam(lsVO);
			
			//날짜
			List<SmartCheckVO> dateList = getDateList(eduSeq);
			
			//출결정보
			SmartCheckVO vo = new SmartCheckVO();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			List<SmartCheckVO> allAttList = smartCheckMapper.getStdntAttDayList(vo);
			
			//새목록
			List<SmartCheckVO> stdntAttList = new ArrayList<SmartCheckVO>();
			
			//qr정보
			QrCode qcparam = new QrCode();
			qcparam.setEduSeq(eduSeq);
			List<QrCode> qrList = qrCodeMapper.selectByEdu(qcparam);
			
			//훈련일수
			int dateCnt = dateList.size();
			int qrCnt = qrList.size();
			
			for(LectureStdnt o: stdntList){
				int attendCnt = 0;
				int lateCnt = 0;
				int leaveCnt = 0;
				int absentCnt = 0;
				int attend2Cnt = 0;//공출
				int absentFinalCnt = 0;
				double attRatio = 0; //일,출석률
				double att2Ratio = 0;//분,출석률
				int totalEduMin = 0;	//총 교육시간
				int totalAttMin = 0; 	//총 출석인정시간
				
				SmartCheckVO stdntAtt = new SmartCheckVO();
				stdntAtt.setUserId(o.getUserId());
				//stdntAtt.setUserNm(o.getUserNm());
				
				List<SmartCheckVO> attList = new ArrayList<SmartCheckVO>();
				stdntAtt.setStdntAttList(attList);
				
				//출결 일자 정보를 세팅
				for(SmartCheckVO o2: dateList){
					SmartCheckVO att = new SmartCheckVO();
					att.setEduDt(o2.getEduDt());
					att.setUserId(o.getUserId());
					
					//LOG.info("{}",o2.getEduDt());
					//qr생성여부세팅
					for(QrCode o3: qrList) {
						//LOG.info("	{}",o3.getQrDt());
						if(o2.getEduDt().equals(o3.getQrDt()) && "1".equals(o3.getQrType())) {
							o2.setIsQr1(1);
						}else if(o2.getEduDt().equals(o3.getQrDt()) && "2".equals(o3.getQrType())) {
							o2.setIsQr2(1);
						}else if(o2.getEduDt().equals(o3.getQrDt()) && "3".equals(o3.getQrType())) {
							o2.setIsQr3(1);
						}
					}
					
					//출결정보를 세팅해야함
					for(SmartCheckVO o3: allAttList){
						if(att.getEduDt().equals(o3.getEduDt()) && stdntAtt.getUserId().equals(o3.getUserId())){
							att.setBeginDe(o3.getBeginDe());
							att.setEndDe(o3.getEndDe());
							att.setAttCd(o3.getAttCd());
							att.setAttTp(o3.getAttTp());
							att.setBeginHhMm(DateUtil.getDate2Str(o3.getBeginDe(), "HH:mm"));
							att.setEndHhMm(DateUtil.getDate2Str(o3.getEndDe(), "HH:mm"));
							att.setAt1HhMm(DateUtil.getDate2Str(o3.getAt1De(), "HH:mm"));
							att.setAt2HhMm(DateUtil.getDate2Str(o3.getAt2De(), "HH:mm"));
							att.setAt3HhMm(DateUtil.getDate2Str(o3.getAt3De(), "HH:mm"));
							att.setAt1Cd(o3.getAt1Cd());
							att.setAt2Cd(o3.getAt2Cd());
							att.setAt3Cd(o3.getAt3Cd());
							break;
						}
					}
					
					//각 출결현황 건수
					/*
					switch (LncUtil.replaceNull(att.getAttCd())) {
						case "O" : 
							attendCnt++;
							//공출건수 
							if(att.getAttTp() == 2){
								attend2Cnt++;
							}
							break;
						case "D" : lateCnt++; break;
						case "Z" : leaveCnt++; break;
						//case "X" : absentCnt++; break;
						default : absentCnt++; break;
					}
					*/
					//기재부3출결방식으로 계산변경
					String at1Cd = LncUtil.replaceNull(att.getAt1Cd());
					String at2Cd = LncUtil.replaceNull(att.getAt2Cd());
					String at3Cd = LncUtil.replaceNull(att.getAt3Cd());
					if ("O".equals(at1Cd)){
						attendCnt++;
					}
					if ("O".equals(at2Cd)){
						attendCnt++;
					}
					if ("O".equals(at3Cd)){
						attendCnt++;
					}
					LOG.info("userId: {},{},{},{},attendCnt: {}",att.getUserId(),at1Cd,at2Cd,at3Cd,attendCnt);
					
					//LOG.info("absentCnt : "+absentCnt);
				
					//수업시간(분)
					Date startDe = DateUtil.getStr2Date(o2.getEduDt()+o2.getStartTm(), "yyyyMMddHHmm");
					Date endDe = DateUtil.getStr2Date(o2.getEduDt()+o2.getEndTm(), "yyyyMMddHHmm");
					try {
						int eduMin = DateUtil.calMin(startDe,endDe);
						totalEduMin+=eduMin;
					}catch (Exception e) {
					}
					
					//인정시간(분)
					int attMin = SmartCheckUtil.calcAttMin(startDe,endDe,att.getBeginDe(),att.getEndDe());
					totalAttMin+=attMin;
					
					attList.add(att);
				}
				
				//최종 결석수계산,지각,조퇴 세번이면 결석처리
				absentFinalCnt = SmartCheckUtil.calcFinalAttendCnt(absentCnt,lateCnt,leaveCnt);
				//LOG.info("atsentFinalCnt : "+absentFinalCnt);
				//출석률(일)
				attRatio = SmartCheckUtil.calcRatio(dateCnt,(dateCnt-absentFinalCnt),1);
				
				//LOG.info("totalEduMin : "+totalEduMin);
				//LOG.info("totalAttMin : "+totalAttMin);
				//출석률 (분)
				att2Ratio = SmartCheckUtil.calcRatio(totalEduMin,(totalAttMin),1);
				
				stdntAtt.setDateCnt(dateCnt);
				stdntAtt.setQrCnt(qrCnt);
				stdntAtt.setAttendCnt(attendCnt);
				stdntAtt.setAbsentFinalCnt(absentFinalCnt);
				stdntAtt.setAttend2Cnt(attend2Cnt);
				stdntAtt.setAttRatio(attRatio);
				stdntAtt.setAtt2Ratio(att2Ratio);
				
				stdntAttList.add(stdntAtt);
			}
			
			rstData.put("dateList", dateList);
			rstData.put("stdntList", stdntAttList);

			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO saveAttDay(int eduSeq, String userId, String eduDt, Date beginDe, Date endDe, int attTp) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId =  SessionUserInfoHelper.getUserId();
			
			LOG.info("QR SAVE1, eduSeq:{},userId:{},eduDt:{},beginDe:{},endDe:{},attTp:{}",eduSeq,userId,eduDt,beginDe,endDe,attTp);
			
			//해당일자의 시작시간 ,종료시간 조회
			LectureTime timeVO = new LectureTime();
			timeVO.setEduSeq(eduSeq);
			timeVO.setEduDt(eduDt);
			List<LectureTime> timeList = lectureTimeMapper.selectByParam(timeVO);
			if(ObjectUtils.isEmpty(timeList)){
				result.setMsg("교육시간이 존재하지 않습니다. 관리자에게 문의하세요");
				result.setResult(0);
				return result;
			}
			
			
			AttendDayStdnt vo = new AttendDayStdnt();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			vo.setEduDt(eduDt);
			vo.setAttTp(attTp);
			vo.setBeginDe(beginDe);
			vo.setEndDe(endDe);
			
			vo.setRegDe(new Date());
			vo.setRegId(sessId);
			vo.setUpdDe(new Date());
			vo.setUpdId(sessId);
			AttendDayStdnt att = attendDayStdntMapper.selectByPk(vo);
			if(att == null){
				vo.setBeginDe(beginDe);
				attendDayStdntMapper.insertByPk(vo);
				result.setMsg("입실처리되었습니다.");
			}else{
				
				//사용자인경우 이미 출근시간 체크 하여 중지
				if(!SessionUserInfoHelper.isAdmin() && !ObjectUtils.isEmpty(att.getBeginDe())){
					//result.setMsg("이미 입실처리 되었습니다.");
					//result.setResult(0);
					//return result;
					vo.setBeginDe(null);//수정시에는 퇴근만 저장되야 하기때문에, 사용자인경우
					vo.setEndDe(beginDe);
				}
				
				attendDayStdntMapper.updateByPk(vo);
			}
			
			////인정시간 계산하여 결석여부 계산하기
			long totalEduSec = 0;
			long totalAttSec = 0;
			AttendDayStdnt att2 = attendDayStdntMapper.selectByPk(vo);
			String attCd = null;
			Date attBeginDe = att2.getBeginDe();
			Date attEndDe = att2.getEndDe();
			Date totalEduBeginDe = DateUtil.getStr2Date(eduDt+timeList.get(0).getStartTm(), "yyyyMMddHHmm");
			Date totalEduEndDe = DateUtil.getStr2Date(eduDt+timeList.get(timeList.size()-1).getEndTm(), "yyyyMMddHHmm");
			if(!ObjectUtils.isEmpty(attBeginDe) && !ObjectUtils.isEmpty(attEndDe)){
				for(int i= 0; i<timeList.size(); i++){
					LectureTime o = timeList.get(i);
					Date eduBeginDe = DateUtil.getStr2Date(eduDt+o.getStartTm(), "yyyyMMddHHmm");
					Date eduEndDe = DateUtil.getStr2Date(eduDt+o.getEndTm(), "yyyyMMddHHmm");
					
					//교육시간 구하기
					long eduSec = DateUtil.getSecondsDifference(eduBeginDe, eduEndDe);
					totalEduSec += eduSec;
					
					//인정시간 구하기
					long attSec = SmartCheckUtil.calcInterDate(eduBeginDe,eduEndDe,attBeginDe,attEndDe);
					totalAttSec += attSec;
					
					
					
				}
				
				double ratio = SmartCheckUtil.calcRatio(totalEduSec, totalAttSec, 1);
				if(ratio < 50){
					attCd = "X";
				}else{
					long diffSec1 = DateUtil.getSecondsDifference(totalEduBeginDe, attBeginDe);//출근초과시간
					long diffSec2 = DateUtil.getSecondsDifference(attEndDe,totalEduEndDe );//퇴근미만시간
					if(diffSec1 < 600 && diffSec2 < 600){
						attCd = "O";
					}else if(diffSec1 > 600 && diffSec2 < 600){
						attCd = "D";
					}else if(diffSec2 > 600){
						attCd = "Z";
					}
				}
				AttendDayStdnt vo3 = new AttendDayStdnt();
				vo3.setEduSeq(eduSeq);
				vo3.setUserId(userId);
				vo3.setEduDt(eduDt);
				vo3.setAttCd(attCd);
				vo3.setUpdDe(new Date());
				vo3.setUpdId(sessId);
				attendDayStdntMapper.updateByPk(vo3);
				
				//개별시간 출석처리하기
				for(int i= 0; i<timeList.size(); i++){
					LectureTime o = timeList.get(i);
					int timeSeq = o.getTimeSeq();
					if(o.getClassHow() != 3){//동영상이 아닌경우만 저장
						setAttendByStudent(eduSeq, timeSeq, userId, attCd, "");
					}
				}
				result.setMsg("퇴실처리되었습니다.");
			}
			LOG.info("QR SAVE2, eduSeq:{},userId:{},eduDt:{},beginDe:{},endDe:{},attTp:{}",eduSeq,userId,eduDt,beginDe,endDe,attTp);
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}
	@Override
	public ResultVO saveAttDay_251023(int eduSeq, String userId, String eduDt, int attTp,Date at1De,Date at2De, Date at3De) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId =  SessionUserInfoHelper.getUserId();
			
			LOG.info("QR SAVE2, eduSeq:{},userId:{},eduDt:{},attTp:{},at1De:{},at2De:{},at3De:{}",eduSeq,userId,eduDt,attTp,at1De,at2De,at3De);
			
			//해당일자의 시작시간 ,종료시간 조회
			LectureTime timeVO = new LectureTime();
			timeVO.setEduSeq(eduSeq);
			timeVO.setEduDt(eduDt);
			List<LectureTime> timeList = lectureTimeMapper.selectByParam(timeVO);
			if(ObjectUtils.isEmpty(timeList)){
				result.setMsg("교육시간이 존재하지 않습니다. 관리자에게 문의하세요");
				result.setResult(0);
				return result;
			}
			
			
			
			AttendDayStdnt vo = new AttendDayStdnt();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			vo.setEduDt(eduDt);
			vo.setAttTp(attTp);

			
			vo.setRegDe(new Date());
			vo.setRegId(sessId);
			vo.setUpdDe(new Date());
			vo.setUpdId(sessId);
			
			//출결데이터존재여부
			AttendDayStdnt att = attendDayStdntMapper.selectByPk(vo);
			
			//오전,점심,오후출결로직 추가
			if(at1De != null){
				QrCode qcParam = new QrCode();
				qcParam.setEduSeq(eduSeq);
				qcParam.setQrDt(eduDt);
				qcParam.setQrType("1");
				QrCode qc = qrCodeMapper.selectByAttend(qcParam);
				boolean isBetween = at1De.compareTo(qc.getQrBeginDe()) >= 0 && at1De.compareTo(qc.getQrEndDe()) <= 0;
				if(!isBetween){
					//출석불가처리
					result.setMsg("출결가능시간이 아닙니다.");
					result.setResult(0);
					return result;
				}
				//출석처리
				vo.setAt1Cd("O");
				vo.setAt1De(at1De);
				result.setMsg("출결처리 되었습니다.");
			}else if(at2De != null){
				//아침 출결 가능시간인지 체크하여
				QrCode qcParam = new QrCode();
				qcParam.setEduSeq(eduSeq);
				qcParam.setQrDt(eduDt);
				qcParam.setQrType("2");
				QrCode qc = qrCodeMapper.selectByAttend(qcParam);
				boolean isBetween = at2De.compareTo(qc.getQrBeginDe()) >= 0 && at2De.compareTo(qc.getQrEndDe()) <= 0;
				if(!isBetween){
					//출석불가처리
					result.setMsg("출결가능시간이 아닙니다.");
					result.setResult(0);
					return result;
				}
				//출석처리
				vo.setAt2Cd("O");
				vo.setAt2De(at2De);
				result.setMsg("출결처리 되었습니다.");
			}else if(at3De != null){
				//아침 출결 가능시간인지 체크하여
				QrCode qcParam = new QrCode();
				qcParam.setEduSeq(eduSeq);
				qcParam.setQrDt(eduDt);
				qcParam.setQrType("3");
				QrCode qc = qrCodeMapper.selectByAttend(qcParam);
				boolean isBetween = at3De.compareTo(qc.getQrBeginDe()) >= 0 && at3De.compareTo(qc.getQrEndDe()) <= 0;
				if(!isBetween){
					//출석불가처리
					result.setMsg("출결가능시간이 아닙니다.");
					result.setResult(0);
					return result;
				}
				//출석처리
				vo.setAt3Cd("O");
				vo.setAt3De(at3De);
				result.setMsg("출결처리 되었습니다.");
			}
			if(att == null){
				attendDayStdntMapper.insertByPk(vo);
			}else{
				attendDayStdntMapper.updateByPk(vo);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}

	@Override
	public double getAttendRatio(int eduSeq,int attTp) {
		try {
			String userId = null;
			
			if(attTp == 0){
				SmartCheckVO scVO  = new SmartCheckVO();
				scVO.setEduSeq(eduSeq);
				List<SmartCheckVO> stdntList = smartCheckMapper.selectAttList(scVO);
				double sumAttRatio = 0;
				for(SmartCheckVO o: stdntList){
					double attRatio = SmartCheckUtil.calcRatio(o.getTimeCnt(),(o.getTimeCnt()-o.getAbsentCnt()),1);
					sumAttRatio += attRatio;
				}
				long s1 = (100*stdntList.size());
				long s2 = (long) sumAttRatio;
				double totAttRatio = SmartCheckUtil.calcRatio(s1, s2, 1);
				
				return totAttRatio;
			}else{
				ResultVO result = getRollBookDay(eduSeq, userId);
				Map<String, Object> rstData = (Map<String, Object>) result.getData();
				List<SmartCheckVO> stdntList = (List<SmartCheckVO>) rstData.get("stdntList");			
				
				double sumAttRatio = 0;
				for(SmartCheckVO o: stdntList){
					sumAttRatio += o.getAttRatio();
				}
				long s1 = (100*stdntList.size());
				long s2 = (long) sumAttRatio;
				double totAttRatio = SmartCheckUtil.calcRatio(s1, s2, 1);
				
				return totAttRatio;
			}
			
		} catch (NullPointerException e) {
			return -1;
		}
	}
}

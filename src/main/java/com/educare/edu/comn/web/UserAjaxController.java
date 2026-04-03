package com.educare.edu.comn.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.component.VarComponent;
import com.educare.edu.bbs.service.model.BoardComment;
import com.educare.edu.comn.mapper.AttendStdntMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureMovMapper;
import com.educare.edu.comn.mapper.LectureMovTimeMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.LectureTimeStdntMapper;
import com.educare.edu.comn.mapper.MovMapper;
import com.educare.edu.comn.mapper.OnlineHistoryMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.Agency;
import com.educare.edu.comn.model.AttendStdnt;
import com.educare.edu.comn.model.LectureMov;
import com.educare.edu.comn.model.LectureMovTime;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.LectureTimeStdnt;
import com.educare.edu.comn.model.OnlineHistory;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.AjaxService;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.LectureDormiService;
import com.educare.edu.comn.service.MovService;
import com.educare.edu.comn.service.PayBackService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.SyncDataService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.service.impl.SyncDataServiceImpl;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.lclike.service.LclikeService;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;
import com.educare.util.SendEmail;
import com.educare.util.SendEmailVo;
import com.educare.util.XmlBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/user/ajax/")
public class UserAjaxController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(UserAjaxController.class.getName());
	
	@Resource(name="EduService")
	private EduService eduService;
	@Resource(name="MovService")
	private MovService movService;
	@Resource(name="TableService")
	private TableService tableService;
	@Resource(name="PayBackService")
	private PayBackService payBackService;
	
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	
	@Resource(name="SmartcheckService")
	private SmartcheckService smartcheckService;
	@Resource(name="SmartCheckMapper")
	private SmartCheckMapper smartCheckMapper;
	
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	@Resource(name="CheckService")
	private CheckService checkService;
	@Resource(name="OnlineHistoryMapper")
	private OnlineHistoryMapper onlineHistoryMapper;
	@Resource(name="TableMapper")
	private TableMapper tableMapper;
	@Resource(name="LectureMapper")
	private LectureMapper lectureMapper;
	@Resource(name="LectureTimeStdntMapper")
	private LectureTimeStdntMapper lectureTimeStdntMapper;
	@Resource(name="LectureMovMapper")
	private LectureMovMapper lectureMovMapper;
	@Resource(name="LectureMovTimeMapper")
	private LectureMovTimeMapper lectureMovTimeMapper;
	@Resource(name="MemberService")
	private MemberService memberService;
	@Resource(name="AjaxService")
	private AjaxService ajaxService;
	@Resource(name="AttendStdntMapper")
	private AttendStdntMapper attendStdntMapper;
	@Resource(name = "LclikeService")
	private LclikeService lclikeService;
	@Resource(name = "SyncDataService")
	private SyncDataService syncDataService;
	@Resource(name = "LectureDormiService")
	private LectureDormiService lectureDormiService;
	
	/*
	 * 학생 점수 저장
	 */
	@RequestMapping(value="saveStdntScore.json",produces="application/json")
	public String saveStdntScore(ModelMap model
			,@RequestBody Map<String, Object> param
			) {
		String result="";
		try {
			//교육종료여부 체크
			int eduSeq = Integer.valueOf(param.get("eduSeq").toString());
			Lecture lctre = eduService.getLctreDetail(eduSeq);
			if(lctre.getCloseYn().equals("Y")){
				model.addAttribute("result", "교육이 종료되었습니다. 수정 할 수 없습니다.");
				return "jsonView";
			}
			
			List<Map<String, Object>> dataList = (List)param.get("dataList");
			for(Map<String, Object> dataMap:dataList){
				//System.out.println(dataMap);
				LectureStdnt lsparam = new LectureStdnt();
				lsparam.setUserId(dataMap.get("userId").toString());
				lsparam.setEduSeq(Integer.valueOf(dataMap.get("eduSeq").toString()));
				if(dataMap.get("attCnt")!=null){
					lsparam.setAttCnt(Integer.valueOf(dataMap.get("attCnt").toString()));
				}
				lsparam.setWorkshopScore(Integer.valueOf(dataMap.get("workshopScore").toString()));
				lsparam.setExamScore(Integer.valueOf(dataMap.get("examScore").toString()));
				lsparam.setAttendScore(Integer.valueOf(dataMap.get("attendScore").toString()));
				lsparam.setPassYn(dataMap.get("passYn").toString());
				
				//자동수료이면 수료점수 계산하여 수료처리한다,동영상전용이 아닌경우만
				if("Y".equals(lctre.getAutoPassYn()) && lctre.getLctreType() != 3){
					int passPoint = lctre.getPassPoint();
					int passAttPoint = lsparam.getAttendScore();
					int passAsgPoint = lsparam.getWorkshopScore();
					int passTestPoint = lsparam.getExamScore();
					if((passAttPoint+passAsgPoint+passTestPoint) >= passPoint){
						lsparam.setPassYn("Y");
					}else{
						lsparam.setPassYn("N");
					}
				}
				
				eduMapper.updateLectureStdntScore(lsparam);
			}
		} catch (NullPointerException e) {
			result= VarComponent.MSG_ERROR;
		}
		model.addAttribute("result", result);
		return "jsonView";
	}
	@RequestMapping(value="rollBook.json",produces="application/json")
	public String checkList(ModelMap model
			,@RequestParam int eduSeq
			,@RequestParam(required=false) String userId
			) {
		int result = 0;
		try {
			//날짜
			List<SmartCheckVO> dateList = smartcheckService.getDateList(eduSeq);
			// 시간
			List<SmartCheckVO> timeList = smartcheckService.getTimeList(eduSeq);

			//시간표
			List<SmartCheckVO> stdntList = smartcheckService.getStdntList(eduSeq,userId);
			
			model.addAttribute("dateList", dateList);
			model.addAttribute("timeList", timeList);
			model.addAttribute("stdntList", stdntList);
			result=1;
		} catch (NullPointerException e) {
			result=0;
		}
		model.addAttribute("result", result);
		return "jsonView";
	}
	
	@RequestMapping(value="checkOnline.json",produces="application/json")
	public String checkOnline(ModelMap model
			,@RequestParam int eduSeq
			,@RequestParam int timeSeq
			) {
		int result = 1;
		String message = "";
		String url = "";
		int classHow = 0;
		String movTime = "0";
		String movAllTime = "0";
		String userId = SessionUserInfoHelper.getUserId();
		try {
			
			//동영상강의 재생 가능한 학생인지 체크
			int check1 = checkService.checkMySugang(eduSeq, SessionUserInfoHelper.getUserId());
			int check2 = checkService.checkMySuupByGangsa(eduSeq, SessionUserInfoHelper.getUserId());
			if(check1==0&&check2==0){
				result=0;
				message = "접근 권한이 없습니다.";
			}else{
				LectureTime param = new LectureTime(eduSeq, timeSeq);
				LectureTime lectureTime = lectureTimeMapper.selectByPk(param);
				Lecture lecture = lectureMapper.selectByPk(eduSeq);
				classHow=lectureTime.getClassHow();
				
				//동영상이면 퀴즈가져오기,챕터가져오기
				if(classHow==3){
					int mvIdx = lectureTime.getMvIdx();
					LectureMovTime lmtParam=new LectureMovTime();
					lmtParam.setMvIdx(mvIdx);
					List<LectureMovTime> movTimeList = lectureMovTimeMapper.selectByMv(lmtParam);
					model.addAttribute("movTimeList",movTimeList);
				}
				
				//입장가능한 시간인지 체크
				Calendar cal = Calendar.getInstance();
				String nowTm = DateUtil.getDate2Str(cal.getTime(), "yyyyMMddHHmm");
				cal.add(Calendar.MINUTE, 10);//10분전에 입장 가능하도록
				String addTm = DateUtil.getDate2Str(cal.getTime(), "yyyyMMddHHmm");
				
				String startTime = lectureTime.getEduDt()+lectureTime.getStartTm();
				String endTime = lectureTime.getEduDt()+lectureTime.getEndTm();
				
				boolean isNoTime = addTm.compareTo(startTime)<0 || nowTm.compareTo(endTime)>0;
				
				//입장 가능한 교육기간 인지 
				String eduStartTime = lecture.getEduPeriodBegin().replaceAll("-", "")+"0000";
				String eduEndTime = lecture.getEduPeriodEnd().replaceAll("-", "")+"2359";
				boolean isNoEduTime = addTm.compareTo(eduStartTime)<0 || nowTm.compareTo(eduEndTime)>0;
				int freeView = lectureTime.getFreeView();
				if(lecture.getLctreType()!=3 && (
					(classHow==3 && freeView==0 && isNoTime)
					|| (classHow==3 && freeView==1 && isNoEduTime)
					|| (classHow!=3 && isNoTime && freeView==0)
						)){
					result=0;
					message = "입장 가능한 시간이 아닙니다.";
				}else{
					url=lectureTime.getUrl();
					if(lectureTime.getClassHow()==2){
						//url = remoteService.getGooroomeeUrl(eduSeq,timeSeq);
						LectureTime timeMap = tableMapper.selectLectureTimeMap(new LectureTime(eduSeq,timeSeq));
						//Map<String,Object> ret = ZoomAPI.getMeetingRoomId(timeMap.getZoomId());
						url = timeMap.getUrl();
					}
					//System.out.println(url);
					
					if(lectureTime.getClassHow()==3){
						LectureMov param3 = new LectureMov();
						param3.setIdx(lectureTime.getMvIdx());
						LectureMov lectureMov = lectureMovMapper.selectByPk(param3);
						url = "/DATA/mov/"+lectureMov.getOrgCd()+"/"+lectureMov.getFileRename();
					}
					
					if(StringUtils.isEmpty(url)){
						result=0;
						message="주소가 올바르지 않습니다. 관리자에게 문의하세요";
					}else{
						//동영상이면 movTime가져오기
						LectureTimeStdnt ltsInfo = lectureTimeStdntMapper.selectByPk(new LectureTimeStdnt(eduSeq, timeSeq, userId));
						if(!ObjectUtils.isEmpty(ltsInfo)){
							movTime = ltsInfo.getMovTime();
							movAllTime = ltsInfo.getMovAllTime();
							lectureTimeStdntMapper.updateByPk(new LectureTimeStdnt(eduSeq, timeSeq, userId, null, null, 0));
						}
						
						//전차시 진도체크
						if(lecture.getLctreType() == 3){
							if(timeSeq>1){
								AttendStdnt asParam = new AttendStdnt();
								asParam.setUserId(userId);
								asParam.setEduSeq(eduSeq);
								asParam.setTimeSeq(timeSeq-1);
								AttendStdnt attend = attendStdntMapper.selectByPk(asParam);
								if(attend!=null){
									if(!attend.getAttCd().equals("O")){
										result=0;
										message = "전 차시 동영상을 시청하셔야 합니다.";
									}
								}else{
									message = "전 차시 동영상을 시청하셔야 합니다.";
									result=0;
								}
								
							}
						}
						
						//당해연도만 복습하기
						String eduYear = lecture.getEduPeriodBegin().substring(0,4);
						String nowYear = DateUtil.getDate2Str(new Date(), "yyyy");
						if(!eduYear.equals(nowYear)){
							message = "당해연도만 복습이 가능합니다.";
							result=0;
						}
						if(result!=0){
							result=1;
							//로그기록
							OnlineHistory param2 = new OnlineHistory(eduSeq, timeSeq, userId, SessionUserInfoHelper.getUserNm(), Calendar.getInstance().getTime()
									,SessionUserInfoHelper.getUserInfo().getEmail()
									);
							onlineHistoryMapper.insertByPk(param2);
							
							//출석 기록
							if(classHow!=3){
								smartcheckService.setAttendByStudent(eduSeq, timeSeq, userId, VarComponent.ATT_ATTEND_CD, "동영상을 제외한 나머지");
							}
						}
					}
				}
			}
			
		} catch (NullPointerException e) {
			result=0;
		}
		
		model.addAttribute("url",url);
		model.addAttribute("classHow",classHow);
		model.addAttribute("movTime", movTime);
		model.addAttribute("movAllTime", movAllTime);
		model.addAttribute("message", message);
		model.addAttribute("result", result);
		return "jsonView";
	}
	
	@RequestMapping(value="saveHistoryTime.json")
	public String saveHistoryTime(ModelMap model
			,@RequestParam int eduSeq
			,@RequestParam int timeSeq
			,@RequestParam String historyTime
			,@RequestParam String allTime
			) {
		
		int result = movService.setHistoryTime(eduSeq,timeSeq,historyTime,allTime,SessionUserInfoHelper.getUserId());
		
		model.addAttribute("result",result);
		return "jsonView";
	}

	//아이디중복체크
	@ResponseBody
	@RequestMapping(value="checkLoginId.ajax", method=RequestMethod.POST)
	public ResultVO checkEmail(ModelMap model	,@RequestParam(defaultValue="") String loginId) {
		ResultVO result = checkService.checkLoginId(loginId);
		return result;
	}
	@ResponseBody
	@RequestMapping(value="checkUserPw.json")
	public Object checkUserPw(ModelMap model ,@RequestParam(defaultValue="") String userPw) {
		HashMap<String, Object> result = checkService.checkUserPw(userPw);
		
		return result;
	}
	//이메일로 인증번호 보내기
	@ResponseBody
	@RequestMapping(value="sendEmailAuthNo.json")
	public Object sendEmailAuthNo(ModelMap model,HttpServletRequest req
			,@RequestParam(defaultValue="") String email
			) {
		Map<String, Object> result = new HashMap<>();
		req.getSession().removeAttribute("authNo");
		req.getSession().removeAttribute("authEmail");
		req.getSession().removeAttribute("isAuthEmail");
		result.put("result", false);
		
		if(!VarComponent.checkRegExp(VarComponent.REGEX_EMAIL, email)){
			return result;
		}
		
		String authNo = LncUtil.numberGen(4, 1);
		SendEmailVo vo = new SendEmailVo();
		vo.setToEmail(email);
		vo.setSubject("아동권리보장원 - 이메일인증");
		vo.setMessage("인증번호 : "+authNo);
		LOG.info("[인증번호보내기]"+vo.getToEmail()+"|"+vo.getSubject()+"|"+vo.getMessage());
		String emailResult = SendEmail.sendEmail(vo);
		if(StringUtils.isEmpty(emailResult)){
			req.getSession().setAttribute("authNo", authNo);
			req.getSession().setAttribute("authEmail", email);
			req.getSession().setAttribute("authYn", "N");
			result.put("result", true);
		}
		return result;
	}
	//이메일로 인증번호 보내기
	@ResponseBody
	@RequestMapping(value="checkEmailAuthNo.json")
	public Object checkEmailAuthNo(ModelMap model,HttpServletRequest req
			,@RequestParam(defaultValue="") String authNo
			) {
		Map<String, Object> result = new HashMap<>();
		
		String sessionAuthNo = req.getSession().getAttribute("authNo").toString();
		if(!sessionAuthNo.equals(authNo)){
			
			result.put("result", false);
			return result;
		}
		
		req.getSession().setAttribute("authYn", "Y");
		result.put("result", true);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="quitMemberProc.json")
	public CheckVO quitMemberProc(ModelMap model,HttpServletRequest req
			,@RequestParam(defaultValue="") String userPw
			) {
		CheckVO result =  new CheckVO();
		UserInfo sess = SessionUserInfoHelper.getUserInfo();
		if(sess == null){
			result.setResult(0);
			return result;
		}
		String loginId = sess.getLoginId();
		
		result = memberService.quitMemberProc(loginId,userPw);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="checkDupYearCtgryPass.json")
	public CheckVO checkDupYearCtgryPass(ModelMap model,HttpServletRequest req
			,@RequestParam(defaultValue="0") int eduSeq
			) {
		//이미 동일연도, 동일과정  , 수료이력이 있으면 알림
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		CheckVO dupCheck = checkService.checkDupYearCtgryPass(lctre,SessionUserInfoHelper.getUserId());
		
		return dupCheck;
	}
	
	/**
	 * 진도율 오류 찾기위한 임시 컨트롤ㄹ러
	 */
	@RequestMapping(value="temp_check.json")
	public String temp_check(ModelMap model,HttpServletRequest req
			,@RequestParam int eduSeq
			,@RequestParam int timeSeq
			,@RequestParam String historyTime
			,@RequestParam String allTime
			) {
		try {
			LOG.info(req.getRemoteAddr()+" eduSeq:"+eduSeq+", timeSeq:"+timeSeq+", userId:"+SessionUserInfoHelper.getUserId()+", historyTime:"+historyTime+", allTime:"+allTime);
		} catch (NullPointerException e) {
			LOG.info(req.getRemoteAddr()+" "+e.getMessage());
		}
		
		return "jsonView";
	}
	

	@ResponseBody
	@RequestMapping(value="saveLectureTimeUrl.json")
	public CheckVO saveLectureTimeUrl(HttpSession session
			,@RequestParam(defaultValue="0") int eduSeq
			,@RequestParam(defaultValue="0") int timeSeq
			,@RequestParam String url
			,@RequestParam(required=false) String urlPw
			) {
		CheckVO result = new CheckVO();
		
		result = tableService.saveLectureTimeUrl(eduSeq,timeSeq,url,urlPw);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="savePayBack.json")
	public ResultVO savePayBack(HttpSession session
			,String bankNm
			,String accountNm
			,String accountNo
			) {
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = payBackService.savePayBack(userId,bankNm,accountNm,accountNo);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="saveAttendInfo.ajax")
	public ResultVO saveCheckInfo(HttpSession session
			,int eduSeq
			,int timeSeq
			,String userId
			,String attCd
			) {
		//권한체크,관리자나,강사체크필요
		
		ResultVO result = smartcheckService.setAttendByStudent(eduSeq, timeSeq, userId, attCd, "로그내용");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="saveAttendTime.ajax")
	public ResultVO saveAttendTime(HttpSession session
			,int eduSeq
			,int timeSeq
			,String attCd
			) {
		//권한체크,관리자나,강사체크필요
		
		ResultVO result = smartcheckService.setAttendByTime(eduSeq, timeSeq, attCd, "로그내용");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("lcrcpUpdate.ajax")
	public ResultVO lcrcpUpdateAjax(ModelMap model,MultipartHttpServletRequest request
			,String userId
			,int rceptSeq
			,String etcIemDataJson
			,int feeTp
			,String compJson
			,String schoolListJson
			,String certifListJson
			,String languaListJson
			,String careerListJson
			,Integer empInsrTp
			,Integer learnCardTp
			,Integer empSuccTp
			,Integer bizRgsTp
			,String etc
			,@RequestParam(value="bsnLcnsFile", required=false) MultipartFile bsnLcnsFile
			) throws UnsupportedEncodingException {
		ResultVO result = new ResultVO();
		if(rceptSeq == 0){
			result.setResult(0);
			return result;
		}
		String etcIemDataJsonp = new String(etcIemDataJson.getBytes("8859_1"),"utf-8");
		String etcp = new String(etc.getBytes("8859_1"),"utf-8");
		String userIdp = userId;
		if(!SessionUserInfoHelper.isAdmin()){
			userIdp = SessionUserInfoHelper.getUserId();
		}
		
		
		result = eduService.updLctreRceptAfterPay(rceptSeq,feeTp,etcp);
		
		String compJsonp = LncUtil.getEncode(compJson);
		String schoolListJsonp = LncUtil.getEncode(schoolListJson);
		String certifListJsonp = LncUtil.getEncode(certifListJson);
		String languaListJsonp = LncUtil.getEncode(languaListJson);
		String careerListJsonp = LncUtil.getEncode(careerListJson);
		
		ResultVO result2 = eduService.saveLctreRceptAdd(rceptSeq,compJsonp,schoolListJsonp,certifListJsonp,languaListJsonp,careerListJsonp,bsnLcnsFile,empInsrTp,learnCardTp,empSuccTp,bizRgsTp);

        return result;
    }
	
	@ResponseBody
	@RequestMapping("eduClose.ajax")
	public ResultVO eduClose(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo
			) {
		ResultVO result = new ResultVO();
		result.setResult(0);
		
		if(!"8".equals(SessionUserInfoHelper.getUserMemLvl())){
			result.setResult(0);
			result.setMsg("권한이 없습니다.");
	        return result;
		}
		String msg = eduService.eduClose(vo);

		result.setResult(1);
		result.setMsg(msg);
        return result;
	}
	@ResponseBody
	@RequestMapping("srchStdnt.ajax")
	public ResultVO srchStdnt(ModelMap model
			,String srchWrds
			) {
		ResultVO result = ajaxService.getStdntList(srchWrds);
		return result;
	}
	@ResponseBody
	@RequestMapping("getMyUserInfoAdd.ajax")
	public ResultVO getMyUserInfoAdd(ModelMap model
			) {
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = memberService.getMyUserInfoAdd(userId);
		return result;
	}
	@ResponseBody
	@RequestMapping("setLikeEdu.ajax")
	public ResultVO setLikeEdu(ModelMap model
			,int eduSeq
			,int state
			) {
		ResultVO result = new ResultVO();
		if(!SessionUserInfoHelper.isLogined()){
			result.setMsg("로그인을 하셔야 합니다.");
			return result;
		}
		
		String userId = SessionUserInfoHelper.getUserId();
		result = lclikeService.saveLclike(userId, eduSeq, state);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("refundRceptProc.ajax")
	public ResultVO refundRceptProc(ModelMap model
			,int rceptSeq
			,@RequestParam(defaultValue="0") Integer rfndReqFee
			,String rfndBankNm
			,String rfndAccountNm
			,String rfndAccountNo
			) {
		ResultVO result = new ResultVO();
		if(!SessionUserInfoHelper.isLogined()){
			result.setMsg("로그인을 하셔야 합니다.");
			return result;
		}
		
		String userId = SessionUserInfoHelper.getUserId();
		//result = ajaxService.refundRceptProc(userId, rceptSeq,rfndReqFee,rfndBankNm,rfndAccountNm,rfndAccountNo);
		return result;
	}
	
	@RequestMapping("testRefundProc.do")
	public String refundRceptProc(ModelMap model,MultipartHttpServletRequest mhsr
			
			) {
		
		//System.out.println(mhsr.getMultiFileMap());
		
		return "redirect:/user/mypage/myLcRceptList.do";
	}
	
	@ResponseBody
	@RequestMapping("getClassDormiList.ajax")
	public ResultVO getClassDormiList(ModelMap model
			,int eduSeq
			,String userId
			) {
		ResultVO result = lectureDormiService.getLectureDormiList(eduSeq,userId);
		
		return result;
	}
	@ResponseBody
	@RequestMapping("setClassDormi.ajax")
	public ResultVO setClassDormi(ModelMap model
			,int eduSeq
			,@RequestParam(name="dormiSeqs",required=false) List<Integer> dormiSeqs
			,String userId
			,Integer dormiSeq
			) {
		
		ResultVO result = lectureDormiService.setClassDormi(eduSeq,dormiSeqs,userId,dormiSeq);
		if(result.getResult()==1) {
			ResultVO result2 = lectureDormiService.setFeeDormi(eduSeq);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("delClassDormi.ajax")
	public ResultVO delClassDormi(ModelMap model
			,int eduSeq
			,String userId
			) {
		
		ResultVO result = lectureDormiService.delClassDormi(eduSeq,userId);
		return result;
	}

	@ResponseBody
	@RequestMapping("lectureDormiStdnt.ajax")
	public ResultVO lectureDormiStdntAjax(ModelMap model
			,int eduSeq
			,@RequestParam(defaultValue="1") Integer pageNo
			) {
		
		ResultVO result = lectureDormiService.getClassDormiStdntByEdu(eduSeq, pageNo);
		return result;
	}
	@ResponseBody
	@RequestMapping("getEtcOrgCd.ajax")
	public ResultVO getEtcOrgCd(ModelMap model
			,String srchWrd
			,@RequestParam(defaultValue="1") Integer pageNo
			) {
		
		ResultVO result = ajaxService.getEtcOrgCdPage(srchWrd,pageNo);
		return result;
	}
	@ResponseBody
	@RequestMapping("getEtcGradeCd.ajax")
	public ResultVO getEtcGradeCd(ModelMap model
			,String srchWrd
			,@RequestParam(defaultValue="1") Integer pageNo
			) {
		
		ResultVO result = ajaxService.getEtcGradeCdPage(srchWrd,pageNo);
		return result;
	}
	
	
}

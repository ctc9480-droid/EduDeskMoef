package com.educare.edu.comn.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.educare.component.VarComponent;
import com.educare.edu.comn.mapper.AdminIpMapper;
import com.educare.edu.comn.mapper.CheckMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.PayMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.model.Pay;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.PayService;
import com.educare.edu.comn.vo.AdminIpVO;
import com.educare.edu.comn.vo.CertNumVO;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.LctreService;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

@Service("CheckService")
public class CheckServiceImpl implements CheckService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(CheckServiceImpl.class.getName());

	@Resource(name="CheckMapper")
	private CheckMapper checkMapper;
	@Resource(name="TableMapper")
	private TableMapper tableMapper;
	@Resource(name="LectureMapper")
	private LectureMapper lectureMapper;
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	@Resource(name="FeedbackMapper")
	private FeedbackMapper feedbackMapper;
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	@Resource(name="LectureStdntMapper")
	private LectureStdntMapper lectureStdntMapper;
	@Resource(name="EduService")
	private EduService eduService;
	@Resource(name="MemberMapper")
	private MemberMapper memberMapper;
	@Resource(name="LctreService")
	private LctreService lctreService;
	@Resource(name="AdminIpMapper")
	private AdminIpMapper adminIpMapper;
	@Resource(name="PayMapper")
	private PayMapper payMapper;
	@Override
	public int checkMySugang(int eduSeq, String userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eduSeq", eduSeq);
		param.put("userId",userId);
		int cnt = checkMapper.selectCountLectureStndtByPk(param);
		return cnt;
	}

	@Override
	public int checkMySuupByGangsa(int eduSeq, String userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eduSeq", eduSeq);
		param.put("instrctrId",userId);
		int cnt = checkMapper.selectCountLectureByEduSeqGangsa(param);
		return cnt;
	}

	@Override
	public int checkOrgByLecture(int eduSeq, String orgCd) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eduSeq", eduSeq);
		param.put("orgCd",orgCd);
		int cnt = checkMapper.selectCountLectureByEduSeqOrgCd(param);
		return cnt;
	}

	@Override
	public int checkOrgUseMov(String orgCd) {
		OrgVO orgInfo = tableMapper.selectOrgByPk(orgCd);
		return orgInfo.getUseMov();
	}

	@Override
	public int checkUpdFeedback(int fbIdx,String orgCd) {
		try {
			
			FeedbackVO param = new FeedbackVO();
			param.setIdx(fbIdx);
			FeedbackVO feedbackInfo = feedbackMapper.getFeedbackMap(param);
			
			if(!feedbackInfo.getOrgCd().equals(orgCd) && !orgCd.equals("0000")){
				return 2;
			}
			
			int cnt = checkMapper.useFeedbackCnt(fbIdx);
			if(cnt>0)return 3;
			
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public int checkEduSeqUseCheck(Integer eduSeq) {
		Lecture lecture =lectureMapper.selectByPk(eduSeq);
		if(lecture.getCheckYn().equals("Y")){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public ResultVO checkLoginId(String loginId) {
		ResultVO result = new ResultVO();
		HashMap<String, Object> rstData = new HashMap<String, Object>();
		result.setData(rstData);
		result.setResult(0);
		try {
			boolean checkRegExp = VarComponent.checkRegExp(VarComponent.REGEX_USER_ID, loginId);
			if(!checkRegExp){
				result.setMsg("사용하실 수 없습니다.");
				return result;
			}
			
			UserInfo user = memberMapper.selectUserInfoByLoginId(loginId);
			if(user != null){
				result.setMsg("이미 사용중인 아이디입니다.");
				return result;
			}
			
			//이메일인증 여부
			/*try {
				HttpServletRequest sr = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getRequest();
				String authYn = sr.getSession().getAttribute("authYn").toString();
				String authEmail = sr.getSession().getAttribute("authEmail").toString();
				if(!authYn.equals("Y")||!authEmail.equals(loginId)){
					result.put("message","이메일 인증이 필요합니다.");
					result.put("result", 2);
					return result;
				}
			} catch (NullPointerException e) {
				result.put("message","이메일 인증이 필요합니다.");
				result.put("result", 2);
				return result;
			}*/
			
			result.setMsg("사용하셔도 좋은 아이디 입니다.");
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			LOG.info(e.getMessage());
			result.setMsg("오류가 발생하였습니다. 다시 시도하시기 바립니다.");
			result.setResult(0);
			return result;
		}
	}

	@Override
	public HashMap<String, Object> checkUserPw(String userPw) {
		HashMap<String, Object> result = new HashMap<>();
		result.put("result", false);
		try {
			boolean checkRegExp = VarComponent.checkRegExp(VarComponent.REGEX_USER_PW, userPw);
			if(!checkRegExp){
				result.put("message","사용하실 수 없습니다.");
				return result;
			}
			
			result.put("message","사용하셔도 좋은 비밀번호 입니다.");
			result.put("result",true);
			return result;
		} catch (NullPointerException e) {
			return result;
		}
	}

	@Override
	public ResultVO checkCancel(int rceptSeq,int eduSeq, String userId) {
		ResultVO result = new ResultVO();
		try {
			EduVO vo = new EduVO();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			vo.setRceptSeq(rceptSeq);
			LectureRcept rcept = eduMapper.selectLctreRceptByPk(vo);
			
			if(rcept!=null&&rcept.getRceptSeq()!=rceptSeq){
				result.setMsg("잘못된 신청서 정보입니다. 관리자에게 문의하세요");
				result.setResult(0);
				return result;  
			}
			
			Pay pay = null;
			if(!ObjectUtils.isEmpty(rcept.getPayNo())){
				pay = payMapper.selectByPk(rcept.getPayNo());
			}
			
			if(rcept.getState() == 1 && pay == null){
				result.setResult(1);
				return result;  
			}
			if(pay != null){
				result.setResult(6);
				return result;  
			}
			
			
			
			//수료면 취소안됨
			LectureStdnt stdnt = eduMapper.selectLctreStdntByPk(vo);
			if(stdnt!=null){
				if(stdnt.getPassYn().equals("Y")){
					result.setMsg("이미 수료한 수업은 취소 할 수 없습니다.");
					result.setResult(3);return result; 
				}
			}
			
			Lecture lctre = eduService.getLctreDetail(eduSeq);
			
			//교육시작됐으면 취소안됨
			//if(lctre.getEduPeriodBegin().compareTo(DateUtil.getDate2Str(new Date(), "yyyy-MM-dd")) <= 0){
				//result.setMsg("교육이 시작되었습니다. 취소 할 수없습니다.");
				//result.setResult(7);
				//return result;  
			//}
			
			//if(!SessionUserInfoHelper.isAdmin()){
				//취소가능일 체크,관리자인경우 넘어감
				/*
				if(!ObjectUtils.isEmpty(lctre.getCancelLimitDt())){
					String cancelLimitDt = lctre.getCancelLimitDt();
					if(cancelLimitDt.compareTo(DateUtil.getDate2Str(new Date(), "yyyyMMdd")) < 0){
						result.setMsg("취소 가능일이 지났습니다.("+DateUtil.getDate2Str(DateUtil.getStr2Date(cancelLimitDt, "yyyyMMdd"),"yyyy.MM.dd")+")");
						result.setResult(0);
						return result;  
					}
				}
				*/
			//}
			
			//유무료체크
			if(lctre.getFee() > 0){//유료강의
				if(rcept==null){
					result.setMsg("신청명단에 없습니다.");
					result.setResult(0);
					return result;  
				}
				if(rcept.getPayType()!=9){//현장결제가 아닌경우
					//if(!rcept.getPayYn().equals("Y")){//결제전이면 취소 가능
					
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, 1);
					//if(lctre.getEduPeriodBegin().compareTo(DateUtil.getDate2Str(cal.getTime(), "yyyy-MM-dd"))<0){
					//result.setResult(5);return result;
					//}
					result.setMsg("결제완료 상태입니다. 결제 취소창으로 이동합니다.");
					result.setResult(6);return result;
				}
			}
			
			//LectureRcept rcept = eduMapper.selectLctreRceptByPk(vo);
			//결제여부체크
			//if(rcept.getPayYn().equals("N")){//결제안함
			//	result.setResult(2);return result;
			//}
			
			//동영상전용이면 아무때나 취소 가능함,이후에 정책이 바뀌면 수정되야함
			//if(lctre.getLctreType()==3){
			//	result.setResult(1);return result;
			//}
			
			//동영상전용아니면 
			//취소가능일 지남
			//long diffDay = UtilComponent.getDiffDayFromNow(rcept.getPayDt());
			//if(lctre.getFeeLimit()<diffDay){//취소가능기간 지남
			//	result.setResult(0);return result;
			//}
			
			//접수중 아님
			if(lctre.getCheckRcept()!=2 && lctre.getCheckRcept()!=3){//접수마감도 취소가능하도록 수정
				result.setResult(0);return result;
			}
			
			
			
			result.setResult(1);return result;
			
			
		} catch (NullPointerException e) {
			result.setResult(0);
			result.setMsg("오류가 발생하였습니다. 관리자에게 문의하세요");
			return result;
		}
	}

	@Override
	public int getCumNumByCtgry(int ctgrySeq,int subSeq,String tlyear,int certMode) {
		CertNumVO param = new CertNumVO();
		param.setCtgrySeq(ctgrySeq);
		param.setSubSeq(subSeq);
		param.setYear(tlyear);
		param.setCertMode(certMode);
		
		CertNumVO certNum = tableMapper.selectCertNumByPk(param);
		if(certNum==null){
			param.setCertNum(1);
			tableMapper.insertCertNum(param);
			return 1;
		}else{
			param.setCertNum(certNum.getCertNum()+1);
			tableMapper.updateCertNum(param);
			return certNum.getCertNum()+1;
		}
	}

	@Override
	public void checkPassCertIssue(LectureStdnt stdnt) {
		if(stdnt!=null){
			if(stdnt.getPassYn().equals("Y") && !ObjectUtils.isEmpty(stdnt.getPassCertNum()) && stdnt.getPassCertIssue().equals("N")){
				stdnt.setPassCertIssue("Y");
				eduMapper.updateLectureStdntIssuePass(stdnt);
			}
		}		
	}

	@Override
	public CheckVO checkEduClose(Lecture lctre) {
		CheckVO result = new CheckVO();
		result.setResult(0);
		try {
			String now = DateUtil.getDate2Str(new Date(), "yyyy-MM-dd");
			if(lctre.getEduPeriodEnd().compareTo(now)<0){
				result.setResult(1);
				return result;
			}
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public CheckVO checkDupYearCtgryPass(Lecture lctre, String userId) {
		CheckVO check = new CheckVO();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userId);
			param.put("year", lctre.getEduPeriodBegin().substring(0,4));
			//param.put("detailCtgrySeq",lctre.getDetailCtgrySeq());
			
			int cnt = checkMapper.selectLctrePassCtgryYearCnt(param);
			if(cnt>0){
				check.setResult(2);
				check.setMsg("이미 교육을 수료하셨습니다. 신청을 계속하시겠습니까?");
				return check;
			}
		} catch (NullPointerException e) {
			check.setResult(0);
		}
		return check;
	}
	@Override
	public ResultVO checkEduRcept(Lecture lctre, UserInfo user) {
		ResultVO result = new ResultVO();
		try {
			
			if(lctre==null){
				result.setResult(0);
				return result;
			}
			
			int eduSeq = lctre.getEduSeq();
			String userId = user.getUserId();
			
			if(user.getUserTp() != lctre.getTargetTp()){
				
				//result.setMsg("해당 수업은 "+lctre.getAddTargetTpNm()+"과정입니다. \\n[나의강의실 > 내정보수정]에서 "+lctre.getAddTargetTpNm()+"정보를 입력 후 신청하시기 바랍니다.");
				//result.setResult(0);
				//return result;
			}
			
			LectureStdnt stdntVO = new LectureStdnt();
			stdntVO.setEduSeq(eduSeq);
			stdntVO.setUserId(userId);
			LectureStdnt stdnt = lectureStdntMapper.selectByPk(stdntVO);
			if(stdnt!=null){
				if(stdnt.getState() == 1 || stdnt.getState() == 2){
					result.setMsg("이미 신청하신 교육입니다.");
					result.setResult(0);
					return result;
				}
			}
			
			//대상여부체크
			int userTp = user.getUserTp();
			String targets = lctre.getTargets();
			CheckVO checkAge = checkRceptTargets(userTp,targets);
			if(checkAge.getResult()==0){
				result.setResult(-2);
				result.setMsg(checkAge.getMsg());
				return result;
			}
			
			if("Y".equals(lctre.getPreEduYn())){//선행수업 체크
				Map<String, Object> param2 = new HashMap<String,Object>();
				param2.put("eduSeq", lctre.getPreEduSeq());
				param2.put("userId",user.getUserId());
				int cnt = checkMapper.selectCountLectureStndtByPass(param2);
				
				if(cnt==0){
					result.setResult(-2);
					result.setMsg("필수선행수업을 수료하셔야 합니다.");
					return result;
				}
			}
			
			//최종 결과, 기본 일반회원 신청 가능
			result.setResult(1);

			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	private CheckVO checkRceptTargets(int userTp, String targets) {
		CheckVO result = new CheckVO();
		try {
			if(ObjectUtils.isEmpty(targets)){
				result.setResult(1);
				return result;
			}
			
			//age가 0인경우
			if(userTp == 0){
				result.setMsg("기관유형 정보가 없습니다. 개인정보 수정에서 기간정보를 입력해 주세요");
				result.setResult(0);
				return result;
			}
			
			String[] targetArr = targets.split(",");
			for(String targetStr : targetArr){
				int target =  LncUtil.nvlInt(targetStr);
			
				if(userTp ==  target){
					result.setResult(1);
					return result;
				}
				
			}
			result.setMsg("교육대상이 아닙니다.");
			result.setResult(0);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO checkEduScrtyPass(int eduSeq) {
		ResultVO result = new ResultVO();
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			
			boolean scrtyPass = (boolean) session.getAttribute("SCRTY_PASS_"+eduSeq+"");
			if(scrtyPass){
				result.setResult(1);
				return result;
			}
			
			result.setResult(0);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public CheckVO checkRceptAge(int age, String targets) {
		CheckVO result = new CheckVO();
		try {
			if(ObjectUtils.isEmpty(targets)){
				result.setResult(1);
				return result;
			}
			
			//age가 0인경우
			if(age == 0){
				result.setMsg("생년월일 정보가 없습니다.");
				result.setResult(0);
				return result;
			}
			
			String[] targetArr = targets.split(",");
			for(String targetStr : targetArr){
				int age2 =  LncUtil.nvlInt(targetStr);
				//한살아래까지 가능하도록 체크하기
				if(age2>=8 && age2 <=19){
					int age3 = age+1;
					if(age3 ==  age2){
						result.setResult(1);
						return result;
					}
				}
				if(age ==  age2){
					result.setResult(1);
					return result;
				}
				if(age2==20){//성인누구나일 경우
					if(age>=20){
						result.setResult(1);
						return result;
					}
				}
			}
			result.setMsg("신청대상이 아닙니다.(나이 : "+age+")");
			result.setResult(0);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO checkAdminIp() {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			String clientIp = LncUtil.getIp(request);
			if(clientIp == null){
				result.setMsg("해당 페이지에 접근 할 수 없습니다. 관리자에게 문의하세요");
				result.setResult(0);
				return result;
			}
			LOG.info("clientIp : "+clientIp);
			
			AdminIpVO vo = new AdminIpVO();
			vo.setState(1);
			List<AdminIp> list = adminIpMapper.selectByParam(vo);
			for(AdminIp o : list){
				if(o.getState()==1){
					if(clientIp.equals(o.getIp4())){
						result.setResult(1);
						return result;
					}
				}
			}
			
			result.setMsg("해당 페이지에 접근 할 수 없습니다. 관리자에게 문의하세요");
			result.setResult(0);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

}

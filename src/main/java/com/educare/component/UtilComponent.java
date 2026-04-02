package com.educare.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.educare.edu.comn.mapper.CodeMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.StatMapper;
import com.educare.edu.comn.model.Code;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.LctreService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.ConfigHandle;
import com.educare.util.DateUtil;
import com.educare.util.EhCacheUtil;
import com.educare.util.LncUtil;

@Component("utcp")
public class UtilComponent {
	
	@Resource(name="LctreService")
	private LctreService lctreService;
	@Resource(name="CheckService")
	private CheckService checkService;
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	@Resource(name="messageSource")
	private MessageSource messageSource;
	@Resource(name="StatMapper")
	private StatMapper statMapper;
	@Resource(name="MemberMapper")
	private MemberMapper memberMapper;
	
	//private static CodeMapper CODE_MAPPER;
	private static CodeMapper codeMapper;
	
    public static String convBsnsNum(String input) {
    	String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String extracted = "";
        
        while (matcher.find()) {
            extracted += matcher.group();
        }
        
        if (extracted.length() == 10) {
            return extracted.replaceFirst("(\\d{3})(\\d{2})(\\d{5})", "$1-$2-$3");
        } else {
            return input;
        }
    }
	
	public String getLang(String key){
		try {
			String result = messageSource.getMessage(key,null, LocaleContextHolder.getLocale());
			return result;
		} catch (NullPointerException e) {
			return "error";
		}
	}
	
	public static String calcDDay(Date dt){
		try {
			String result = "Day";
			long left = dt.getTime()-Calendar.getInstance().getTime().getTime();
			if(left<0){
				return "-";
			}
			int dDay = (int) Math.floor(left/(1000*60*60*24));
			if(dDay>0){
				result = String.valueOf(dDay);
			}
			return "D-"+result;
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public static Date convStrToDate(String dtStr,String pattern){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.KOREA);
			Date result = sdf.parse(dtStr);
			return result;
		} catch (NullPointerException | ParseException e) {
			return null;
		}
	}
	public static String convDateToStrByLocale(Date dt,String pattern,String localeStr){
		try {
			Locale locale = Locale.KOREA;
			if("ENGLISH".equals(localeStr)){
				locale = Locale.ENGLISH;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
			String result = sdf.format(dt);
			return result;
		} catch (NullPointerException e) {
			return null;
		}
	}
	public static String convDateToStr(Date dt,String pattern){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.KOREA);
			String result = sdf.format(dt);
			return result;
		} catch (NullPointerException e) {
			return null;
		}
	}
	public static String convNewLine(String source){
		String target = "";
		if(LncUtil.isHTML(source)){
			target = source;
		}else{
			target = source.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
		}
		return target;
	}
	public CheckVO checkRcept(int eduSeq){
		try {
			return lctreService.checkRcept(eduSeq);
		} catch (NullPointerException e) {
			return null;
		}
	}
	/*public ResultVO checkTmlctRcept(int tmSeq,int tmPlanSeq){
		try {
			return eduTmlctService.checkTmlctRcept(tmSeq,tmPlanSeq);
		} catch (NullPointerException e) {
			return null;
		}
	}*/
	public static String filterPhone(String phone){
		String result = "-";
		return result;
	}
	public static int checkPattern(String target,String regex){
		try {
			Pattern p = Pattern.compile(regex); 
			Matcher m = p.matcher(target); 
			
			if(!m.matches()) {
				return 0;
			}
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	/**
	 * <pre>
	 * 수료여부구하기
	 * 0:교육종료전,아무것도 아님,1:수료,2:미수료,3:진행중
	 * </pre>
	 * @param closeYn
	 * @param passYn
	 * @return
	 */
	public int checkPass(String closeYnp, String passYn,int lctreType,String eduPeriodEnd,int state) {
		try {
			if(state != 2){
				return 0;
			}
			
			String closeYn = LncUtil.replaceNull(closeYnp);
			String now = DateUtil.getDate2Str(new Date(), "yyyy-MM-dd");
			if(lctreType!=3){//동영상전용이 아닌경우
				
				if(closeYn.equals("Y")){
					if(passYn.equals("N")){
						return 2;
					}
				}
				
				if(eduPeriodEnd.compareTo(now)<0){//교육기간 지남
					if(passYn.equals("N")){
						return 2;
					}
				}
				if(eduPeriodEnd.compareTo(now)>=0){//교육기간내
					if(closeYn.equals("Y")){
						if(passYn.equals("N")){
							return 2;
						}
					}
				}
				
				if(passYn.equals("Y")){
					return 1;
				}
			}else{
				//전용인경우
				if(eduPeriodEnd.compareTo(now)>0){//교육기간 내이고
					if(passYn.equals("N")){
						return 3;
					}
				}
			}
			if(passYn.equals("N")){
				return 2;
			}
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	public ResultVO checkCancel(int rceptSeq,int eduSeq,String userId){
		try {
			return checkService.checkCancel(rceptSeq,eduSeq, userId);
		} catch (NullPointerException e) {
			return null;
		}
	}
	public static String getCodeNm(String code){
		try {
			if(codeMapper==null){
				HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				HttpSession session = req.getSession();
				ServletContext conext = session.getServletContext();
				WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(conext);
				codeMapper = (CodeMapper)wContext.getBean("CodeMapper");
			}
			String codeNm = codeMapper.getCodeNmByCd(code);
			return codeNm;
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
	public static List<Code> getCodeList(int prntCd){
		try {
			if(codeMapper==null){
				HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				HttpSession session = req.getSession();
				ServletContext conext = session.getServletContext();
				WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(conext);
				codeMapper = (CodeMapper)wContext.getBean("CodeMapper");
			}
			List<Code> result = codeMapper.getCodeByParent(prntCd);
			return result;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public static String convByte2Base64(byte[] target){
		try {
			return Base64.encodeBase64String(target);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public static String getCdnUrl(String sub){
		String result = ConfigHandle.getProperties("cloud.cdn.url")+"/"+sub;
		return result;
	}
	
	public static long getDiffDayFromNow(Date target) {
		try {
			return DateUtil.getDiffDayFromNow(target);
		} catch (NullPointerException e) {
			return -1;
		}
	}
	
	public String getEduPeriodTxt(int eduSeq){
		try {
			LectureTime ltParam = new LectureTime();ltParam.setEduSeq(eduSeq);
			List<LectureTime> ltList = lectureTimeMapper.selectByEduSeq(ltParam);
			String txtBegin = "";
			String txtEnd = "";
			int lastIndex = ltList.size()-1;
			String beginDt = ltList.get(0).getEduDt();
			String beginTm = ltList.get(0).getStartTm();
			String endDt = ltList.get(lastIndex).getEduDt();
			String endTm = ltList.get(lastIndex).getEndTm();
			
			if(beginDt.equals(endDt)){
				txtBegin = DateUtil.getDate2Str(DateUtil.getStr2Date(beginDt+beginTm, "yyyyMMddHHmm"),"yyyy-MM-dd HH:mm"); 
				txtEnd = DateUtil.getDate2Str(DateUtil.getStr2Date(endDt+endTm, "yyyyMMddHHmm"),"HH:mm");
				return txtBegin+" ~ "+txtEnd;
			}else{
				txtBegin = DateUtil.getDate2Str(DateUtil.getStr2Date(beginDt+beginTm, "yyyyMMddHHmm"),"yyyy-MM-dd HH:mm"); 
				txtEnd = DateUtil.getDate2Str(DateUtil.getStr2Date(endDt+endTm, "yyyyMMddHHmm"),"yyyy-MM-dd HH:mm");
				return txtBegin+" ~ "+txtEnd;
			}
			
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
	
	public boolean isEduClose(Lecture lctre){
		CheckVO check = checkService.checkEduClose(lctre);
		if(check.getResult()==1){
			return true;
		}else{
			return false;
		}
	}
	
	public String convHtml(String target){
		try {
			String target2 = target
			.replaceAll("\'","")
			.replaceAll("","");
			return target2;
		} catch (NullPointerException e) {
			return target;
		}
	}
	
	//임시 함수, 기본과정전체 신청자 수 가져오는
	public int getStdntCntTemp(int detailCtgrySeq){
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("detailCtgrySeq", detailCtgrySeq);
			int cnt = statMapper.getStdntCntByDetailCtgrySeq(param);
			return cnt;
		} catch (NullPointerException e) {
			return -1;
		}
	}

	public Integer getPersonCntTemp(Integer detailCtgrySeq) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("detailCtgrySeq", detailCtgrySeq);
			int cnt = statMapper.getPersonCntByDetailCtgrySeq(param);
			return cnt;
		} catch (NullPointerException e) {
			return -1;
		}
	}

	public static String convPhoneNumber(String phoneNumber) {
        // 전화번호 형식 확인
        if (phoneNumber.matches("\\d{3}-\\d{3,4}-\\d{4}")) {
            // 중간 네 자리를 *로 변경
            String[] parts = phoneNumber.split("-");
            String filteredNumber = parts[0] + "-" + "****" + "-" + parts[2];
            return filteredNumber;
        } else {
            // 형식이 맞지 않는 경우 그대로 반환
            return phoneNumber;
        }
    }
	
	public String getCtxPath(){
		String result = ConfigHandle.getProperties("lnc.app.contextpath");
		return result;
	}
	public String calculatePercentage(int numerator, int denominator, int decimalPlaces){
		return LncUtil.calculatePercentage(numerator, denominator, decimalPlaces);
	}
	public String getMembershipTypeNm(String membershipType){
		try {
			String result = "일반회원";
			if("SV".equals(membershipType)){
				result = "연간회원";
			}
			return result;
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
	public boolean possibleEduPay(Date rceptDe){
		try {
			Calendar cal = DateUtil.getCalendar();
			cal.add(Calendar.HOUR_OF_DAY, -1);
			if(rceptDe.compareTo(cal.getTime())<0){
				return false;
			}
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public UserInfo getUserInfo(){
		return memberMapper.selectUserInfoByPk(SessionUserInfoHelper.getUserId());
	}
	public String getNow2Str(String pattern){
		return DateUtil.getDate2Str(new Date(), pattern);
	}
	public static String getMaskedName(String regNm){
		return LncUtil.getMaskedName(regNm);
	}
}

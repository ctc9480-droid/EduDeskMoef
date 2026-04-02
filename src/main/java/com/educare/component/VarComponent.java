package com.educare.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.educare.edu.comn.model.LectureTime;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

@Component("vcp")
public class VarComponent {
	
	public static final String REGEX_USER_ID = "^[a-z]{1}+[a-z0-9_-]{5,14}$";
	//public static final String REGEX_USER_PW = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&^])[A-Za-z\\d$@$!%*#?&^]{8,}$";
	public static final String REGEX_USER_PW = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\\-_=+]).{8,30}$";
	public static final String REGEX_EMAIL = "^[_a-zA-Z0-9._%+-]+@[_a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	public static final String REGEX_URL = "(http|https):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?"; 
	public static final String REGEX_YYYYMMDD = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])";
	public static final String REGEX_YYYYMMDDHH = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0-3])";
	public static final String REGEX_YYYYMMDDHHMI = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0-3])([0-5][0-9])";
	public static final String REGEX_YYYYMMDDHHMISS = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0-3])([0-5][0-9])([0-5][0-9])";
	public static final String REGEX_HH= "(0[0-9]|1[0-9]|2[0-3])";
	public static final String REGEX_MMDD = "(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])";
	public static final String REGEX_HHMI = "(0[0-9]|1[0-9]|2[0-3])([0-5][0-9])";
	
	public static final String MSG_ERROR = "알 수 없는 에러가 발생하였습니다. 관리자에게 문의 하세요";
	public static final String MSG_ERROR2 = "알 수 없는 에러가 발생하였습니다. 다시 시도 하세요";
	
	public static final String ATT_ATTEND_CD = "O";
	public static final String ATT_LATE_CD = "D";
	public static final String ATT_LEAVE_CD = "Z";
	public static final String ATT_ABSENT_CD = "X";
	
	public static final String[]LECTURE_TYPE={"집합교육","온라인교육","집합교육(+온라인교육)","온라인(동영상)"};
	public static final String[]CLASS_HOW={"오프라인","온라인","화상","동영상","시험","설문"};
	public static final String[]EDU_TP={"-","국기훈련","계좌제","일반과정"};
	public static final String[]RETURN_TP={"-","비환급","고용보험환급","국민내일배움카드"};
	public static final String[]FEE_TP={"-","온라인결제","현장결제","전자세금계산서(영수)","전자세금계산서(청구)"};
	public static final String[]BIZ_TP={"-","법인","개인","해외","비영리"};
	public static final String[]USER_MEM_LVL_TYPE={"-","최고관리자","기관관리자","기타관리자","","","","준회원","강사","정회원"};
	
	public static final String SESSION_KEY_BOARD = "BOARD_";
	public static final String SESSION_KEY_PROGRESS = "PROGRESS_";
	
	//public static final String[] OFFICE_POSITION = {"","센터장","돌봄선생님","관장","팀장","상담원","종사자","시설장","생활복지사","아동복지전담공무원","사회복지전담공무원","기아동복지교사(기본분야)","아동복지교사(특화분야)","원장","보육사","아동학대전담공무원","드림스타트","기관장","임상심리치료 전문인력","자립지원 전담요원","드림스타트(팀장)","드림스타트(전담공무원)","드림스타트(아동통합사례관리사)","아동보호 전담요원","기타"};
	
	public static final int POSSIBLE_TM_RCEPT_LIMIT = 2;
	
	public static final String[] RCEPT_STATE = {"","신청","승인","취소","가승인"};
	public static final String[] PAY_STATE = {"","결제승인","결제대기","결제취소"};
	public static final String[] PAY_TYPE = {"-","신용카드","계좌이체","-","-","-","-","-","-","현장결제"};
	public static final String[] EDU_TERM = {"","1","2","3","4","5","6","7","8","9","10"};
	public static final String[] SMS_RESULT = {"대기","발송","실패","-"};
	public static final String[] CNHT_STATE = {"","사용","미사용"};
	
	private static Map<String,String> TARGET_MAP = new HashMap<String,String>();
	static{
		TARGET_MAP.put("1", "재정경제부");
		TARGET_MAP.put("2", "기획예산처");
		TARGET_MAP.put("5", "중앙행정기관");
		TARGET_MAP.put("3", "지자체");
		TARGET_MAP.put("4", "공공기관");
	}
	
	private static final Map<String,String> CNHT_TYPE_MAP = new HashMap<String,String>();
	static{
		CNHT_TYPE_MAP.put("greeting", "인사말");
		CNHT_TYPE_MAP.put("orgInfo", "조직도");
		CNHT_TYPE_MAP.put("academyMap", "찾아 오시는길");
	}
	
	
	
	public static boolean checkRegExp(String regExp,String target){
		try {
			Pattern p = Pattern.compile(regExp); 
			Matcher m = p.matcher(target); 
			
			if(!m.matches()) {
				return false;
			}
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}
	public static String getUserMemLvlNm(int code){
		try {
			return USER_MEM_LVL_TYPE[code];
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
	
	public static String getEduTargetNm(String key){
		try {
			return TARGET_MAP.get(key);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getCnhtTypeNm(String key){
		try {
			return CNHT_TYPE_MAP.get(key);
		} catch (NullPointerException e) {
			return null;
		}
	}
	public static List<Map.Entry<String, String>> getCnhtTypeList() {
        return new ArrayList<>(CNHT_TYPE_MAP.entrySet());
    }
	public static String[] getCnhtStateArr() {
		return CNHT_STATE;
	}
	
	public static String  getRceptStateNm(int state){
		try {
			switch (state) {
			case 1:return "신청";
			case 2:return "승인";
			case 3:return "취소";
			default: return "-";
			}
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
	/*
	 */
	public static String getEduTermNm(int i){
		try {
			return EDU_TERM[i];
		} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
			return LncUtil.replaceNull(i);
		}
	}
	public static String getSmsResultNm(int i){
		try {
			return SMS_RESULT[i];
		} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
			return "ERROR";
		}
	}
	/*public static void main(String[] args) {
		String loginId="abc";
		boolean checkRegExp = VarComponent.checkRegExp(VarComponent.REGEX_USER_ID,loginId );
		System.out.println(checkRegExp);
		boolean checkRegExp2 = VarComponent.checkRegExp(VarComponent.REGEX_USER_ID2,loginId );
		System.out.println(checkRegExp2);
		
	}*/
	
	//알림메시지
	private final static Map<String,String> ALMTLK_MSG_MAP = new HashMap<String,String>();
	static{
		String msg001 = "융복합경제재정교육플랫폼 홈페이지 회원가입을 축하드립니다.";
		
		StringBuffer msg002 = new StringBuffer();
		msg002.append("안녕하십니까, 재정경제부 『{{0}}』 교육비 납부 관련해서 아래와 같이 안내드립니다.\n");
		msg002.append("\n");
		msg002.append("[교육기간]\n");
		msg002.append("{{1}} ~ {{2}}\n");
		msg002.append("\n");
		msg002.append("[교육비]\n");
		msg002.append("식비: {{3}}원 숙박비: {{4}}원\n");
		msg002.append("\n");
		msg002.append("[납부계좌]\n");
		msg002.append("식비: {{5}}\n");//(식비가 0원일 경우, "-" 표시)
		msg002.append("숙박비: {{6}}\n");//(숙박비가 0원일 경우, "-" 표시)
		msg002.append("\n");
		msg002.append("[납부기한]\n");
		msg002.append("{{7}}까지");
		
		StringBuffer msg003 = new StringBuffer();
		msg003.append("안녕하세요 KTL아카데미입니다.\n\n");
		msg003.append("KTL아카데미 교육과정 접수건이 정상적으로 취소처리 되었습니다.\n\n");
		msg003.append("1. 교육과정: {{0}}\n");
		msg003.append("2. 교육기간: {{1}}\n");
		msg003.append("3. 교육문의: {{2}}\n\n");
		msg003.append("감사합니다.");
		
		StringBuffer msg004 = new StringBuffer();
		msg004.append("안녕하세요 KTL아카데미입니다.\n\n");
		msg004.append("신청하신 교육 개강이 확정되어 안내드리오니 확인 부탁드립니다.\n\n");
		msg004.append("1. 교육과정: {{0}}\n");
		msg004.append("2. 교육일정: {{1}}\n");
		msg004.append("3. 교육장소: {{2}}\n");
		msg004.append("4. 주차지원: 지원불가(대중교통 권장)\n");
		msg004.append("5. 제공: {{4}}\n");
		msg004.append("6. 교육문의: {{3}}\n\n");
		msg004.append("* 교육비 현장카드결제도 가능하며, 세금계산서 발행이 필요하신 분은 교육담당자에게 연락주시기 바랍니다.\n\n");
		msg004.append("감사합니다.");
		
		StringBuffer msg008 = new StringBuffer();
		msg008.append("[교육 신청 완료]\n");
		msg008.append("- 교육명 : {{0}}\n");
		msg008.append("- 일시 : {{1}}\n");
		msg008.append("※ 교육 신청이 완료되었습니다. 추후 선발 절차를 통해 안내드릴 예정이며, 문의사항은 {{2}}으로 연락바랍니다.");
		
		StringBuffer msg009 = new StringBuffer();
		msg009.append("[교육 취소 완료]\n");
		msg009.append("- 교육명 : {{0}}\n");
		msg009.append("- 일시 : {{1}}\n");
		msg009.append("※ 신청하신 교육 취소가 완료되었습니다.");
		
		StringBuffer msg010 = new StringBuffer();
		msg010.append("[교육 선발 완료]\n");
		msg010.append("※ 아래 교육에 최종 선발되었습니다.\n");
		msg010.append("- 교육명 : {{0}}\n");
		msg010.append("- 일시 : {{1}}\n");
		msg010.append("교육생 등록 절차는 별도 안내드리도록 하겠습니다.");
				  
		ALMTLK_MSG_MAP.put("ktl_edu_001", msg001);
		ALMTLK_MSG_MAP.put("ktl_edu_002", msg002.toString());
		ALMTLK_MSG_MAP.put("ktl_edu_003", msg003.toString());
		ALMTLK_MSG_MAP.put("ktl_edu_004", msg004.toString());
		ALMTLK_MSG_MAP.put("ktl_edu_008", msg008.toString());
		ALMTLK_MSG_MAP.put("ktl_edu_009", msg009.toString());
		ALMTLK_MSG_MAP.put("ktl_edu_010", msg010.toString());
	}
	public static String getAlimTalkMsg(String tplCd,String... args){
		String msg = ALMTLK_MSG_MAP.get(tplCd);
		
		if(args != null){
			for(int i=0;i<args.length;i++){
				msg=msg.replaceAll("\\{\\{"+i+"\\}\\}", args[i]);
			}
		}
		
		return msg;
	}
	public static String getEduPeriod2Msg(String eduPeriodBegin, String eduPeriodEnd){
		String eduPeriodStr = DateUtil.getDate2Str(DateUtil.getStr2Date(eduPeriodBegin, "yyyy-MM-dd"),"yyyy년 MM월 dd일")
				+" ~ "+DateUtil.getDate2Str(DateUtil.getStr2Date(eduPeriodEnd, "yyyy-MM-dd"),"yyyy년 MM월 dd일");
		return eduPeriodStr;
	}
	public static String getEduPeriod1Msg(List<LectureTime> timeList){
		try {
			if(ObjectUtils.isEmpty(timeList)){
				return "시간표 준비 중";
			}
			
			Set<String> daySet = new TreeSet<>();
			for (LectureTime o : timeList) {
				daySet.add(o.getEduDt());
			}
			List<String> dayList = new ArrayList<>(daySet);
			String minDate = ((TreeSet<String>) daySet).first(); // 가장 작은 날짜
			String maxDate = ((TreeSet<String>) daySet).last();  // 가장 큰 날짜
			
			String eduPeriodStr = DateUtil.getDate2Str(DateUtil.getStr2Date(minDate, "yyyyMMdd"),"MM.dd")
					+" ~ "+DateUtil.getDate2Str(DateUtil.getStr2Date(maxDate, "yyyyMMdd"),"MM.dd")
					+"("+dayList.size()+"일)";
			
			return eduPeriodStr;
		} catch (NullPointerException e) {
			return "-";
		}
	}
	
	//--------------qr출결 가능 시간대-----------------------------
	private static final Map<String,String> ATT_ACPT_TIME_MAP = new HashMap<String,String>();
	static{
		ATT_ACPT_TIME_MAP.put("at1BeginTm"	,"09:20");//오전시작시
		ATT_ACPT_TIME_MAP.put("at1EndTm"	,"09:30");//오전종료시
		ATT_ACPT_TIME_MAP.put("at2BeginTm"	,"12:20");//점심시작시
		ATT_ACPT_TIME_MAP.put("at2EndTm"	,"12:30");//점심종료시
		ATT_ACPT_TIME_MAP.put("at3BeginTm"	,"15:20");//오후시작시
		ATT_ACPT_TIME_MAP.put("at3EndTm"	,"15:30");//오후종료시
	}
	public static String getAttAcptTime(String key){
		try {
			return ATT_ACPT_TIME_MAP.get(key);
		} catch (NullPointerException e) {
			return null;
		}
	}
}

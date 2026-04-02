package com.educare.edu.education.service.model;

import java.util.Date;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.educare.edu.comn.model.LectureTimeStdnt;
import com.educare.util.CryptoSeedUtil;
import com.educare.util.EhCacheUtil;

public class LectureStdnt {
	
	public static final String STR_Y = "Y";
	public static final String STR_N = "N";
	
	/** 수업 일련번호 */
	private Integer eduSeq;
	
	/** 아이디 */
	private String userId;
	
	/** 성명 */
	private String userNm;
	
	/** 연락처 */
	private String mobile;
	
	/** 이메일 */
	private String email;
	
	/** 소속 */
	private String belong;
	
	/** 직위 */
	private String position;
	
	/** 출석여부 */
	private String atendYn;
	
	/** 워크샵 점수 */
	private int workshopScore;
	
	/** 성적 */
	private int examScore;
	
	/** 합격여부 */
	private String passYn;
	
	/** 합격증 발급여부 */
	private String passCertIssue;
	
	/** 합격증 번호 */
	private String passCertNum;
	
	/** 수료증 발급여부 */
	private String complCertIssue;
	
	/** 수료증 번호 */
	private String complCertNum;
	
	/** 설문조사 완료여부 */
	private String surveyYn;
	private int rceptSeq;
	private int dormiFee = -1;
	private int mealFee = -1;
	private String depositYn = null;
	
	private int attendScore;
	private int state;		
	private Date updDe;
	private String updId;
	
	private Date certDe; 
	
	/** 출석수 */
	private int attCnt;
	private int testPassCnt;
	private int test2PassCnt;//재평가 용이지만 나중에 정리필요
	private int testCnt;
	
	private int attendCnt;
	private int absentCnt;
	private int dateCnt;
	private int qrCnt;
	private double attRatio;
	
	private String loginId;
	private String loginId2;
	private Date regDe;
	private String birth;
	private String ccTypeNm;
	private String taskFileOrg;
	private String taskFileRename;	
	private int feResultState;
	
	private List<LectureTimeStdnt> ltsInfo;//차시별 동영상진도율 정보
	
	/**
	 * 
	 */
	public LectureStdnt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LectureStdnt(Integer eduSeq, String userId, String taskFileOrg, String taskFileRename) {
		super();
		this.eduSeq = eduSeq;
		this.userId = userId;
		this.taskFileOrg = taskFileOrg;
		this.taskFileRename = taskFileRename;
	}
	/**
	 * @param eduSeq
	 * @param userId
	 * @param userNm
	 * @param mobile
	 * @param email
	 * @param belong
	 * @param position
	 * @param atendYn
	 * @param workshopScore
	 * @param examScore
	 * @param passYn
	 * @param passCertIssue
	 * @param passCertNum
	 * @param complCertIssue
	 * @param complCertNum
	 * @param surveyYn
	 */
	public LectureStdnt(Integer eduSeq, String userId, String userNm, String mobile, String email, String belong,
			String position, String atendYn, int workshopScore, int examScore, String passYn,
			String passCertIssue, String passCertNum, String complCertIssue, String complCertNum, String surveyYn,int state,int rceptSeq) {
		super();
		this.eduSeq = eduSeq;
		this.userId = userId;
		this.userNm = userNm;
		this.mobile = mobile;
		this.email = email;
		this.belong = belong;
		this.position = position;
		this.atendYn = atendYn;
		this.workshopScore = workshopScore;
		this.examScore = examScore;
		this.passYn = passYn;
		this.passCertIssue = passCertIssue;
		this.passCertNum = passCertNum;
		this.complCertIssue = complCertIssue;
		this.complCertNum = complCertNum;
		this.surveyYn = surveyYn;
		this.state = state;
		this.rceptSeq = rceptSeq;
	}



	/**
	 * @return the eduSeq
	 */
	public Integer getEduSeq() {
		return eduSeq;
	}

	/**
	 * @param eduSeq the eduSeq to set
	 */
	public void setEduSeq(Integer eduSeq) {
		this.eduSeq = eduSeq;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userNm
	 */
	public String getUserNm() {
		return userNm;
	}

	/**
	 * @param userNm the userNm to set
	 */
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	/**
	 * @return the mobile
	public String getMobile() {
		try {
			return CryptoSeedUtil.decrypt(this.mobile);
		} catch (NullPointerException e) {
			return this.mobile;
		}
	}
	 */
	public String getMobile() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getMobile();
			//return CryptoSeedUtil.decrypt(this.mobile);
		} catch (NullPointerException e) {
			return this.mobile;
		}
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the email
	public String getEmail() {
		return email;
	}
	 */
	public String getEmail() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getEmail();
			//return CryptoSeedUtil.decrypt(this.email);
		} catch (NullPointerException e) {
			return this.email;
		}
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the belong
	 */
	public String getBelong() {
		return belong;
	}

	/**
	 * @param belong the belong to set
	 */
	public void setBelong(String belong) {
		this.belong = belong;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the atendYn
	 */
	public String getAtendYn() {
		return atendYn;
	}

	/**
	 * @param atendYn the atendYn to set
	 */
	public void setAtendYn(String atendYn) {
		this.atendYn = atendYn;
	}

	/**
	 * @return the workshopScore
	 */
	public int getWorkshopScore() {
		return workshopScore;
	}

	/**
	 * @param workshopScore the workshopScore to set
	 */
	public void setWorkshopScore(int workshopScore) {
		this.workshopScore = workshopScore;
	}

	/**
	 * @return the examScore
	 */
	public int getExamScore() {
		return examScore;
	}

	/**
	 * @param examScore the examScore to set
	 */
	public void setExamScore(int examScore) {
		this.examScore = examScore;
	}

	/**
	 * @return the passYn
	 */
	public String getPassYn() {
		return passYn;
	}

	/**
	 * @param passYn the passYn to set
	 */
	public void setPassYn(String passYn) {
		this.passYn = passYn;
	}

	/**
	 * @return the passCertIssue
	 */
	public String getPassCertIssue() {
		return passCertIssue;
	}

	/**
	 * @param passCertIssue the passCertIssue to set
	 */
	public void setPassCertIssue(String passCertIssue) {
		this.passCertIssue = passCertIssue;
	}

	/**
	 * @return the passCertNum
	 */
	public String getPassCertNum() {
		return passCertNum;
	}

	/**
	 * @param passCertNum the passCertNum to set
	 */
	public void setPassCertNum(String passCertNum) {
		this.passCertNum = passCertNum;
	}

	/**
	 * @return the complCertIssue
	 */
	public String getComplCertIssue() {
		return complCertIssue;
	}

	/**
	 * @param complCertIssue the complCertIssue to set
	 */
	public void setComplCertIssue(String complCertIssue) {
		this.complCertIssue = complCertIssue;
	}

	/**
	 * @return the complCertNum
	 */
	public String getComplCertNum() {
		return complCertNum;
	}

	/**
	 * @param complCertNum the complCertNum to set
	 */
	public void setComplCertNum(String complCertNum) {
		this.complCertNum = complCertNum;
	}

	/**
	 * @return the surveyYn
	 */
	public String getSurveyYn() {
		return surveyYn;
	}

	/**
	 * @param surveyYn the surveyYn to set
	 */
	public void setSurveyYn(String surveyYn) {
		this.surveyYn = surveyYn;
	}

	public Date getCertDe() {
		return certDe;
	}

	public void setCertDe(Date certDe) {
		this.certDe = certDe;
	}

	public int getAttCnt() {
		return attCnt;
	}

	public void setAttCnt(int attCnt) {
		this.attCnt = attCnt;
	}

	public int getAttendCnt() {
		return attendCnt;
	}

	public void setAttendCnt(int attendCnt) {
		this.attendCnt = attendCnt;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public List<LectureTimeStdnt> getLtsInfo() {
		return ltsInfo;
	}

	public void setLtsInfo(List<LectureTimeStdnt> ltsInfo) {
		this.ltsInfo = ltsInfo;
	}
	
	public String getAddMovRatio(){
		try {
			int cnt=0;
			double totalResult = 0;
			for(LectureTimeStdnt o:ltsInfo){
				double result = 0;
				if(!ObjectUtils.isEmpty(o.getMovAllTime()) && !ObjectUtils.isEmpty(o.getMovTime())){
					double a = Double.parseDouble(o.getMovAllTime());
					double b = Double.parseDouble(o.getMovTime());
					result = b / a * 100.0;
				}
				cnt++;
				totalResult+=result;
			}
			totalResult = (totalResult)/(cnt*100)*100.0;
			
			if(Double.isNaN(totalResult)){
				return "0";
			}
			String result = String.format("%.1f", totalResult) + "";
			if(result.equals("100.0")){
				result = "100";
			}
			return result;
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}

	public Date getRegDe() {
		return regDe;
	}

	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}
	
	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getCcTypeNm() {
		return ccTypeNm;
	}

	public void setCcTypeNm(String ccTypeNm) {
		this.ccTypeNm = ccTypeNm;
	}

	public String getTaskFileOrg() {
		return taskFileOrg;
	}

	public void setTaskFileOrg(String taskFileOrg) {
		this.taskFileOrg = taskFileOrg;
	}

	public String getTaskFileRename() {
		return taskFileRename;
	}

	public void setTaskFileRename(String taskFileRename) {
		this.taskFileRename = taskFileRename;
	}

	public int getAttendScore() {
		return attendScore;
	}

	public void setAttendScore(int attendScore) {
		this.attendScore = attendScore;
	}
	
	public String getDecMobile(){
		try {
			return CryptoSeedUtil.decrypt(this.mobile);
		} catch (NullPointerException e) {
			return this.mobile;
		}
	}
	public String getDecEmail(){
		try {
			return CryptoSeedUtil.decrypt(this.email);
		} catch (NullPointerException e) {
			return this.email;
		}
	}

	public String getLoginId2() {
		return loginId2;
	}

	public void setLoginId2(String loginId2) {
		this.loginId2 = loginId2;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getUpdDe() {
		return updDe;
	}

	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}

	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public int getRceptSeq() {
		return rceptSeq;
	}

	public void setRceptSeq(int rceptSeq) {
		this.rceptSeq = rceptSeq;
	}

	public int getTestPassCnt() {
		return testPassCnt;
	}

	public void setTestPassCnt(int testPassCnt) {
		this.testPassCnt = testPassCnt;
	}

	public int getTestCnt() {
		return testCnt;
	}

	public void setTestCnt(int testCnt) {
		this.testCnt = testCnt;
	}

	public int getTest2PassCnt() {
		return test2PassCnt;
	}

	public void setTest2PassCnt(int test2PassCnt) {
		this.test2PassCnt = test2PassCnt;
	}

	public int getAbsentCnt() {
		return absentCnt;
	}

	public void setAbsentCnt(int absentCnt) {
		this.absentCnt = absentCnt;
	}

	public int getDateCnt() {
		return dateCnt;
	}

	public void setDateCnt(int dateCnt) {
		this.dateCnt = dateCnt;
	}

	public int getQrCnt() {
		return qrCnt;
	}
	
	public void setQrCnt(int qrCnt) {
		this.qrCnt = qrCnt;
	}
	
	public double getAttRatio() {
		return attRatio;
	}

	public void setAttRatio(double attRatio) {
		this.attRatio = attRatio;
	}

	public int getDormiFee() {
		return dormiFee;
	}

	public void setDormiFee(int dormiFee) {
		this.dormiFee = dormiFee;
	}

	public int getMealFee() {
		return mealFee;
	}

	public void setMealFee(int mealFee) {
		this.mealFee = mealFee;
	}

	public String getDepositYn() {
		return depositYn;
	}

	public void setDepositYn(String depositYn) {
		this.depositYn = depositYn;
	}

	public int getFeResultState() {
		return feResultState;
	}

	public void setFeResultState(int feResultState) {
		this.feResultState = feResultState;
	}
	
}

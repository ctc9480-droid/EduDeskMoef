package com.educare.edu.quizTest.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.educare.util.EhCacheUtil;

/**
 */
public class QuizTestVO{
	
	
	//페이징처리
	private int rowCnt;
	private int firstIndex;
	private String srchWrd;
	private int srchCtg1Seq;
	private int srchCtg2Seq;
	private int srchCtg3Seq;
	
	//데이타
	private String qstnNm;
	private String qstnStr;
	private String qstnDesc;
	private int testSeq;
	private String testNm;
	private int qstnSeq;
	private int tqSeq;
	private int qstnTp;
	private int diffType;
	private int testTmplSeq;
	private int timeLimit;
	private String testTmplNm;
	private int timer;			//제한시간
	private int ord; 			//문제순번
	private String optn;		//객관식 답안
	private String answeredYn;	//답안입력여부
	private String answer;		//주관식
	private String fillBlank;	//단답형
	private double marks;		//배점
	private double passMarks;	//합격점수
	private int markTp;			//채점방식
	private Date startDe;
	private Date endDe;
	private String ansDesc;
	private int status;
	private int ordTp;
	private int choiceTp;
	private double marksGet;
	private String qstnFnRnm;	//문제이미지
	private Date checkDe;
	
	private int chSeq;			//보기아이디
	private String chStr;		//보기명
	private String chAnsYn;		//객관식보기중 정답여부
	private int chCnt;			//보기 갯수
	private String chOrd;		//보기정렬기준
	private String chFnRnm;		//보기이미지
	
	private Date regDe;
	private String regId;

	private int qstnCnt;			//시험지 문제갯수
	private int testResultCnt;		//제출수수
	private String regNm;
	private String fillBlankCo;		//단답형 정답
	private double totMarksGet;		//내 총점
	private double totMarks;		//시험지 총점
	private int testResultState;	//1:대기,2:제출
	private Date testResultStartDe; //시험시작한 시간
	private Date testResultEndDe; 	//시험종료한 시간
	private String ctg1Nm;
	private String ctg2Nm;
	private String ctg3Nm;
	private String subNm;
	private int subSeq;
	private int testStdntCnt;
	private int tryNo;
	private String passYn;
	private String checkNm;
	
	private int eduSeq;
	private String userId;
	private String userNm;
	private String loginId;
	
	
	public String getAddTryNoNm(){
		try {
			if(this.tryNo > 1){
				return "재평가";
			}else{
				return "본평가";
			}
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
	/*
	 */
	public String getMobile() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getMobile();
		} catch (NullPointerException | IllegalArgumentException e) {
			return "ERROR";
		}
	}

	public int getRowCnt() {
		return rowCnt;
	}

	public void setRowCnt(int rowCnt) {
		this.rowCnt = rowCnt;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public Date getRegDe() {
		return regDe;
	}

	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}

	public int getTestTmplSeq() {
		return testTmplSeq;
	}

	public void setTestTmplSeq(int testTmplSeq) {
		this.testTmplSeq = testTmplSeq;
	}

	public String getTestTmplNm() {
		return testTmplNm;
	}

	public void setTestTmplNm(String testTmplNm) {
		this.testTmplNm = testTmplNm;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getSrchWrd() {
		return srchWrd;
	}

	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
	}

	public String getQstnNm() {
		return qstnNm;
	}

	public void setQstnNm(String qstnNm) {
		this.qstnNm = qstnNm;
	}

	public int getTqSeq() {
		return tqSeq;
	}

	public void setTqSeq(int tqSeq) {
		this.tqSeq = tqSeq;
	}

	public int getQstnSeq() {
		return qstnSeq;
	}

	public void setQstnSeq(int qstnSeq) {
		this.qstnSeq = qstnSeq;
	}

	public int getQstnTp() {
		return qstnTp;
	}

	public void setQstnTp(int qstnTp) {
		this.qstnTp = qstnTp;
	}

	public int getDiffType() {
		return diffType;
	}

	public void setDiffType(int diffType) {
		this.diffType = diffType;
	}

	public int getQstnCnt() {
		return qstnCnt;
	}

	public void setQstnCnt(int qstnCnt) {
		this.qstnCnt = qstnCnt;
	}

	public String getRegNm() {
		return regNm;
	}

	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getEduSeq() {
		return eduSeq;
	}

	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}

	public String getTestNm() {
		return testNm;
	}

	public void setTestNm(String testNm) {
		this.testNm = testNm;
	}

	public int getTestSeq() {
		return testSeq;
	}

	public void setTestSeq(int testSeq) {
		this.testSeq = testSeq;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getChSeq() {
		return chSeq;
	}

	public void setChSeq(int chSeq) {
		this.chSeq = chSeq;
	}

	public String getChStr() {
		return chStr;
	}

	public void setChStr(String chStr) {
		this.chStr = chStr;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public String getOptn() {
		return optn;
	}

	public void setOptn(String optn) {
		this.optn = optn;
	}

	public String getAnsweredYn() {
		return answeredYn;
	}

	public void setAnsweredYn(String answeredYn) {
		this.answeredYn = answeredYn;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getFillBlank() {
		return fillBlank;
	}

	public void setFillBlank(String fillBlank) {
		this.fillBlank = fillBlank;
	}

	public String getFillBlankCo() {
		return fillBlankCo;
	}

	public void setFillBlankCo(String fillBlankCo) {
		this.fillBlankCo = fillBlankCo;
	}

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}

	public String getChAnsYn() {
		return chAnsYn;
	}

	public void setChAnsYn(String chAnsYn) {
		this.chAnsYn = chAnsYn;
	}

	public double getPassMarks() {
		return passMarks;
	}

	public void setPassMarks(double passMarks) {
		this.passMarks = passMarks;
	}

	public double getTotMarksGet() {
		return totMarksGet;
	}

	public void setTotMarksGet(double totMarksGet) {
		this.totMarksGet = totMarksGet;
	}

	public int getTestResultState() {
		return testResultState;
	}

	public void setTestResultState(int testResultState) {
		this.testResultState = testResultState;
	}

	public Date getTestResultStartDe() {
		return testResultStartDe;
	}

	public void setTestResultStartDe(Date testResultStartDe) {
		this.testResultStartDe = testResultStartDe;
	}

	public Date getTestResultEndDe() {
		return testResultEndDe;
	}

	public void setTestResultEndDe(Date testResultEndDe) {
		this.testResultEndDe = testResultEndDe;
	}

	public int getMarkTp() {
		return markTp;
	}

	public void setMarkTp(int markTp) {
		this.markTp = markTp;
	}

	public Date getStartDe() {
		return startDe;
	}

	public void setStartDe(Date startDe) {
		this.startDe = startDe;
	}

	public Date getEndDe() {
		return endDe;
	}

	public void setEndDe(Date endDe) {
		this.endDe = endDe;
	}

	public int getTestResultCnt() {
		return testResultCnt;
	}

	public void setTestResultCnt(int testResultCnt) {
		this.testResultCnt = testResultCnt;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}



	public String getCtg1Nm() {
		return ctg1Nm;
	}

	public void setCtg1Nm(String ctg1Nm) {
		this.ctg1Nm = ctg1Nm;
	}

	public String getCtg2Nm() {
		return ctg2Nm;
	}

	public void setCtg2Nm(String ctg2Nm) {
		this.ctg2Nm = ctg2Nm;
	}

	public String getCtg3Nm() {
		return ctg3Nm;
	}

	public void setCtg3Nm(String ctg3Nm) {
		this.ctg3Nm = ctg3Nm;
	}

	public int getChCnt() {
		return chCnt;
	}

	public void setChCnt(int chCnt) {
		this.chCnt = chCnt;
	}

	public String getChOrd() {
		return chOrd;
	}

	public void setChOrd(String chOrd) {
		this.chOrd = chOrd;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOrdTp() {
		return ordTp;
	}

	public void setOrdTp(int ordTp) {
		this.ordTp = ordTp;
	}

	public int getChoiceTp() {
		return choiceTp;
	}

	public void setChoiceTp(int choiceTp) {
		this.choiceTp = choiceTp;
	}

	public double getTotMarks() {
		return totMarks;
	}

	public void setTotMarks(double totMarks) {
		this.totMarks = totMarks;
	}

	public int getTestStdntCnt() {
		return testStdntCnt;
	}

	public void setTestStdntCnt(int testStdntCnt) {
		this.testStdntCnt = testStdntCnt;
	}
	
	public String getAddGradeNm(){
		try {
			if(this.testResultState == 0){//응시전이면
				return "";
			}
			
			String result = "1";
			if(this.totMarksGet >= 90){ result = "5"; }
			else if(this.totMarksGet >= 80){ result = "4"; }
			else if(this.totMarksGet >= 70){ result = "3"; }
			else if(this.totMarksGet >= 60){ result = "2"; }
			return result+"수준";
		} catch (NullPointerException e) {
			return "ERR";
		}
	}
	
	public String getAddTestResultStateNm(){
		try {
			String[] o = {"","진행중","제출완료","채점완료"};
			if(this.testResultState == 3){
				if("Y".equals(this.passYn)){
					return "통과";
				}else{
					return "탈락";
				}
			}
			return o[this.testResultState];
		} catch (NullPointerException e) {
			return "-";
		}
	}

	public double getMarksGet() {
		return marksGet;
	}

	public void setMarksGet(double marksGet) {
		this.marksGet = marksGet;
	}

	public String getQstnFnRnm() {
		return qstnFnRnm;
	}

	public void setQstnFnRnm(String qstnFnRnm) {
		this.qstnFnRnm = qstnFnRnm;
	}

	public String getChFnRnm() {
		return chFnRnm;
	}

	public void setChFnRnm(String chFnRnm) {
		this.chFnRnm = chFnRnm;
	}

	public int getSrchCtg1Seq() {
		return srchCtg1Seq;
	}

	public void setSrchCtg1Seq(int srchCtg1Seq) {
		this.srchCtg1Seq = srchCtg1Seq;
	}

	public int getSrchCtg2Seq() {
		return srchCtg2Seq;
	}

	public void setSrchCtg2Seq(int srchCtg2Seq) {
		this.srchCtg2Seq = srchCtg2Seq;
	}

	public int getSrchCtg3Seq() {
		return srchCtg3Seq;
	}

	public void setSrchCtg3Seq(int srchCtg3Seq) {
		this.srchCtg3Seq = srchCtg3Seq;
	}
	public int getTryNo() {
		return tryNo;
	}
	public void setTryNo(int tryNo) {
		this.tryNo = tryNo;
	}
	public String getPassYn() {
		return passYn;
	}
	public void setPassYn(String passYn) {
		this.passYn = passYn;
	}
	public String getSubNm() {
		return subNm;
	}
	public void setSubNm(String subNm) {
		this.subNm = subNm;
	}
	public int getSubSeq() {
		return subSeq;
	}
	public void setSubSeq(int subSeq) {
		this.subSeq = subSeq;
	}
	public String getCheckNm() {
		return checkNm;
	}
	public void setCheckNm(String checkNm) {
		this.checkNm = checkNm;
	}
	public String getQstnDesc() {
		return qstnDesc;
	}
	public void setQstnDesc(String qstnDesc) {
		this.qstnDesc = qstnDesc;
	}
	public String getQstnStr() {
		return qstnStr;
	}
	public void setQstnStr(String qstnStr) {
		this.qstnStr = qstnStr;
	}
	public Date getCheckDe() {
		return checkDe;
	}
	public void setCheckDe(Date checkDe) {
		this.checkDe = checkDe;
	}
	public String getAnsDesc() {
		return ansDesc;
	}
	public void setAnsDesc(String ansDesc) {
		this.ansDesc = ansDesc;
	}
}	

package com.educare.edu.statistics.service;

import java.util.Date;

public class StatsVO {
	private Integer page = 1;
	private Integer row = 10;
	private Integer firstIndex;
	private int srchCtgry;
	private int srchCtgry2;
	private int srchCtgry3;
	private String srchWrd;
	
	private String srchYear;
	private String srchMonth;
	private String srchStartDay;
	private String srchEndDay;
	private int srchDetailCtgrySeq;
	private int srchCtgrySeq;
	private String gubunView;
	private String tabView;
	private int eduSeq;
	private int roomSeq;
	private int fbIdx;
	private int qtIdx;
	private int chIdx;
	private Integer detailCtgrySeq;
	private Integer eduCtgrySeq;
	private Integer subCtgrySeq;
	private String subCtgryNm;
	private String detailCtgryNm;
	private String eduCtgryNm;
	private int stdntCnt; //승인인원
	private int waitCnt; //대기인원,미결제 또는 대기상태
	private int personnel; //정원
	private int totalAmount;
	private String ctg1Nm;
	private String ctg2Nm;
	private String ctg3Nm;
	private String checkNm;
	
	private String year;
	private String month;
	private String addr1;
	private int totalCnt;
	private int passCnt;
	private int complCnt;
	private int empyTotCnt;
	private int empyPassCnt;
	private int empyComplCnt;
	private int noempyTotCnt;
	private int noempyPassCnt;
	private int noempyComplCnt;
	
	private int asCnt; //피드백 객관식 선택 갯수
	private String answer;
	private Date regTime;
	private int sumPoint;
	
	private String srchStatGubun = "requ"; //requ:신청일 기준 ,cert:수료일 기준
	
	private String eduNm;
	private String eduPeriodBegin;
	private String eduPeriodEnd;
	private String eduYear;
	private String eduTerm;
	private String fbTitle;
	private String roomNm;
	private int surveyCnt;//설문참여인원
	private int sumMinute;
	
	private int monthKr;
	private int weekKr;
	private int periodKr;
	private int eduAttendCnt;
	private int eduAttendCnt1;
	private int eduAttendCnt2;
	private int eduAttendCnt3;
	private int eduAttendCnt4;
	
	private int fbCnt;
	private double fbRate;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private String choice5;
	private String choice6;
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getPassCnt() {
		return passCnt;
	}
	public void setPassCnt(int passCnt) {
		this.passCnt = passCnt;
	}
	public int getEmpyTotCnt() {
		return empyTotCnt;
	}
	public void setEmpyTotCnt(int empyTotCnt) {
		this.empyTotCnt = empyTotCnt;
	}
	public int getEmpyPassCnt() {
		return empyPassCnt;
	}
	public void setEmpyPassCnt(int empyPassCnt) {
		this.empyPassCnt = empyPassCnt;
	}
	public int getNoempyTotCnt() {
		return noempyTotCnt;
	}
	public void setNoempyTotCnt(int noempyTotCnt) {
		this.noempyTotCnt = noempyTotCnt;
	}
	public int getNoempyPassCnt() {
		return noempyPassCnt;
	}
	public void setNoempyPassCnt(int noempyPassCnt) {
		this.noempyPassCnt = noempyPassCnt;
	}
	
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getSrchYear() {
		return srchYear;
	}
	public void setSrchYear(String srchYear) {
		this.srchYear = srchYear;
	}
	public int getSrchDetailCtgrySeq() {
		return srchDetailCtgrySeq;
	}
	public void setSrchDetailCtgrySeq(int srchDetailCtgrySeq) {
		this.srchDetailCtgrySeq = srchDetailCtgrySeq;
	}
	public String getGubunView() {
		return gubunView;
	}
	public void setGubunView(String gubunView) {
		this.gubunView = gubunView;
	}
	public String getTabView() {
		return tabView;
	}
	public void setTabView(String tabView) {
		this.tabView = tabView;
	}
	public String getSrchMonth() {
		return srchMonth;
	}
	public void setSrchMonth(String srchMonth) {
		this.srchMonth = srchMonth;
	}
	public String getSrchStartDay() {
		return srchStartDay;
	}
	public void setSrchStartDay(String srchStartDay) {
		this.srchStartDay = srchStartDay;
	}
	public String getSrchEndDay() {
		return srchEndDay;
	}
	public void setSrchEndDay(String srchEndDay) {
		this.srchEndDay = srchEndDay;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public int getFbIdx() {
		return fbIdx;
	}
	public void setFbIdx(int fbIdx) {
		this.fbIdx = fbIdx;
	}
	public int getQtIdx() {
		return qtIdx;
	}
	public void setQtIdx(int qtIdx) {
		this.qtIdx = qtIdx;
	}
	public int getChIdx() {
		return chIdx;
	}
	public void setChIdx(int chIdx) {
		this.chIdx = chIdx;
	}
	public int getAsCnt() {
		return asCnt;
	}
	public void setAsCnt(int asCnt) {
		this.asCnt = asCnt;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public int getSumPoint() {
		return sumPoint;
	}
	public void setSumPoint(int sumPoint) {
		this.sumPoint = sumPoint;
	}
	public Integer getDetailCtgrySeq() {
		return detailCtgrySeq;
	}
	public void setDetailCtgrySeq(Integer detailCtgrySeq) {
		this.detailCtgrySeq = detailCtgrySeq;
	}
	public Integer getEduCtgrySeq() {
		return eduCtgrySeq;
	}
	public void setEduCtgrySeq(Integer eduCtgrySeq) {
		this.eduCtgrySeq = eduCtgrySeq;
	}
	public Integer getSubCtgrySeq() {
		return subCtgrySeq;
	}
	public void setSubCtgrySeq(Integer subCtgrySeq) {
		this.subCtgrySeq = subCtgrySeq;
	}
	public String getSubCtgryNm() {
		return subCtgryNm;
	}
	public void setSubCtgryNm(String subCtgryNm) {
		this.subCtgryNm = subCtgryNm;
	}
	public String getDetailCtgryNm() {
		return detailCtgryNm;
	}
	public void setDetailCtgryNm(String detailCtgryNm) {
		this.detailCtgryNm = detailCtgryNm;
	}
	public String getEduCtgryNm() {
		return eduCtgryNm;
	}
	public void setEduCtgryNm(String eduCtgryNm) {
		this.eduCtgryNm = eduCtgryNm;
	}
	public int getSrchCtgrySeq() {
		return srchCtgrySeq;
	}
	public void setSrchCtgrySeq(int srchCtgrySeq) {
		this.srchCtgrySeq = srchCtgrySeq;
	}
	
	public String getSrchStatGubun() {
		return srchStatGubun;
	}
	public void setSrchStatGubun(String srchStatGubun) {
		this.srchStatGubun = srchStatGubun;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(Integer firstIndex) {
		this.firstIndex = firstIndex;
	}
	public int getStdntCnt() {
		return stdntCnt;
	}
	public void setStdntCnt(int stdntCnt) {
		this.stdntCnt = stdntCnt;
	}
	public int getWaitCnt() {
		return waitCnt;
	}
	public void setWaitCnt(int waitCnt) {
		this.waitCnt = waitCnt;
	}
	public int getPersonnel() {
		return personnel;
	}
	public void setPersonnel(int personnel) {
		this.personnel = personnel;
	}
	public String getEduNm() {
		return eduNm;
	}
	public void setEduNm(String eduNm) {
		this.eduNm = eduNm;
	}
	public String getEduPeriodBegin() {
		return eduPeriodBegin;
	}
	public void setEduPeriodBegin(String eduPeriodBegin) {
		this.eduPeriodBegin = eduPeriodBegin;
	}
	public String getEduPeriodEnd() {
		return eduPeriodEnd;
	}
	public void setEduPeriodEnd(String eduPeriodEnd) {
		this.eduPeriodEnd = eduPeriodEnd;
	}
	public int getSrchCtgry() {
		return srchCtgry;
	}
	public void setSrchCtgry(int srchCtgry) {
		this.srchCtgry = srchCtgry;
	}
	public int getSrchCtgry2() {
		return srchCtgry2;
	}
	public void setSrchCtgry2(int srchCtgry2) {
		this.srchCtgry2 = srchCtgry2;
	}
	public int getSrchCtgry3() {
		return srchCtgry3;
	}
	public void setSrchCtgry3(int srchCtgry3) {
		this.srchCtgry3 = srchCtgry3;
	}
	public String getEduYear() {
		return eduYear;
	}
	public void setEduYear(String eduYear) {
		this.eduYear = eduYear;
	}
	public String getEduTerm() {
		return eduTerm;
	}
	public void setEduTerm(String eduTerm) {
		this.eduTerm = eduTerm;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getFbTitle() {
		return fbTitle;
	}
	public void setFbTitle(String fbTitle) {
		this.fbTitle = fbTitle;
	}
	public int getSurveyCnt() {
		return surveyCnt;
	}
	public void setSurveyCnt(int surveyCnt) {
		this.surveyCnt = surveyCnt;
	}
	public String getSrchWrd() {
		return srchWrd;
	}
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
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
	public int getEmpyComplCnt() {
		return empyComplCnt;
	}
	public void setEmpyComplCnt(int empyComplCnt) {
		this.empyComplCnt = empyComplCnt;
	}
	public int getNoempyComplCnt() {
		return noempyComplCnt;
	}
	public void setNoempyComplCnt(int noempyComplCnt) {
		this.noempyComplCnt = noempyComplCnt;
	}
	public int getComplCnt() {
		return complCnt;
	}
	public void setComplCnt(int complCnt) {
		this.complCnt = complCnt;
	}
	public int getSumMinute() {
		return sumMinute;
	}
	public void setSumMinute(int sumMinute) {
		this.sumMinute = sumMinute;
	}
	public String getCheckNm() {
		return checkNm;
	}
	public void setCheckNm(String checkNm) {
		this.checkNm = checkNm;
	}
	public String getRoomNm() {
		return roomNm;
	}
	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}
	public int getRoomSeq() {
		return roomSeq;
	}
	public void setRoomSeq(int roomSeq) {
		this.roomSeq = roomSeq;
	}

	public int getMonthKr() {
		return monthKr;
	}
	public void setMonthKr(int monthKr) {
		this.monthKr = monthKr;
	}
	public int getWeekKr() {
		return weekKr;
	}
	public void setWeekKr(int weekKr) {
		this.weekKr = weekKr;
	}
	public int getPeriodKr() {
		return periodKr;
	}
	public void setPeriodKr(int periodKr) {
		this.periodKr = periodKr;
	}
	public int getEduAttendCnt() {
		return eduAttendCnt;
	}
	public void setEduAttendCnt(int eduAttendCnt) {
		this.eduAttendCnt = eduAttendCnt;
	}
	public int getEduAttendCnt1() {
		return eduAttendCnt1;
	}
	public void setEduAttendCnt1(int eduAttendCnt1) {
		this.eduAttendCnt1 = eduAttendCnt1;
	}
	public int getEduAttendCnt2() {
		return eduAttendCnt2;
	}
	public void setEduAttendCnt2(int eduAttendCnt2) {
		this.eduAttendCnt2 = eduAttendCnt2;
	}
	public int getEduAttendCnt3() {
		return eduAttendCnt3;
	}
	public void setEduAttendCnt3(int eduAttendCnt3) {
		this.eduAttendCnt3 = eduAttendCnt3;
	}
	public int getEduAttendCnt4() {
		return eduAttendCnt4;
	}
	public void setEduAttendCnt4(int eduAttendCnt4) {
		this.eduAttendCnt4 = eduAttendCnt4;
	}
	
	public int getFbCnt() {
		return fbCnt;
	}
	public void setFbCnt(int fbCnt) {
		this.fbCnt = fbCnt;
	}
	public double getFbRate() {
		return fbRate;
	}
	public void setFbRate(double fbRate) {
		this.fbRate = fbRate;
	}
	public String getChoice1() {
		return choice1;
	}
	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}
	public String getChoice2() {
		return choice2;
	}
	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}
	public String getChoice3() {
		return choice3;
	}
	public void setChoice3(String choice3) {
		this.choice3 = choice3;
	}
	public String getChoice4() {
		return choice4;
	}
	public void setChoice4(String choice4) {
		this.choice4 = choice4;
	}
	public String getChoice5() {
		return choice5;
	}
	public void setChoice5(String choice5) {
		this.choice5 = choice5;
	}
	public String getChoice6() {
		return choice6;
	}
	public void setChoice6(String choice6) {
		this.choice6 = choice6;
	}
	
}

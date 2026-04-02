package com.educare.edu.comn.vo;

import java.util.Date;
import java.util.List;

import com.educare.util.EhCacheUtil;


/**
 */
public class SmartCheckVO{
	//응답
	private int eduSeq;
	private int timeSeq;
	private String userId;
	private String attCd;
	private String eduDt;
	private String startTm;
	private String endTm;
	private String eduNm;
	private int stdntCnt;
	private int checkStatus;
	private Date moveDate;
	private Double attRatio;
	private Double att2Ratio;
	private String studNm;
	private int absentCnt;
	private int absent2Cnt;
	private int attendCnt;
	private int lateCnt;
	private int leaveCnt;
	private String authNo;
	private String checkLocate;
	private int checkType;
	private int absentFinalCnt;//최종결석 수
	private int checkTimeCnt;
	private int timeCnt;
	private String email;
	private int rNum;
	private String orgCd;
	private int attend2Cnt;//공출
	private int sumTimeCnt;
	private int eduDtCnt;
	private Date regDate;
	private String sunDt;
	private String monDt;
	private String tueDt;
	private String wedDt;
	private String thuDt;
	private String friDt;
	private String satDt;
	private String nowDt;
	private String profNm;
	private String eduCtgryNm;
	private String eduPeriodBegin;
	private int classHow;
	private String loginId;
	private String checkId;
	
	//etc
	private int isQr1;
	private int isQr2;
	private int isQr3;
	
	//요청
	private String reqOrgCd;
	private String reqProfId;
	private String reqUserMemLvl;
	private String reqStartDt;
	private String reqEndDt;
	private int reqMoveCnt;
	private String reqStudId;
	private String reqEduDt;
	private int reqCtgry1=0;
	private int reqCtgry2=0;
	private String reqWrd;
	private String reqWrdKey;
	
	//오프라인출석 관련
	private Date beginDe;
	private Date endDe;
	private String beginHhMm;
	private String endHhMm;
	private int attTp;
	private int dateCnt;
	private int qrCnt;
	private String at1Cd;
	private String at2Cd;
	private String at3Cd;
	private Date at1De;
	private Date at2De;
	private Date at3De;
	private String at1HhMm;
	private String at2HhMm;
	private String at3HhMm;
	private Date qr1BeginDe;
	private Date qr1EndDe;
	private Date qr2BeginDe;
	private Date qr2EndDe;
	private Date qr3BeginDe;
	private Date qr3EndDe;
	
	//
	private String userNm;
	
	private int pageNo=1;
	private int firstRecordIndex=0;
	private int recordCountPerPage=10;
	
	private List<SmartCheckVO> stdntAttList;
	
	public int getTimeSeq() {
		return timeSeq;
	}
	public void setTimeSeq(int timeSeq) {
		this.timeSeq = timeSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEduDt() {
		return eduDt;
	}
	public void setEduDt(String eduDt) {
		this.eduDt = eduDt;
	}
	public String getStartTm() {
		return startTm;
	}
	public void setStartTm(String startTm) {
		this.startTm = startTm;
	}
	public String getEndTm() {
		return endTm;
	}
	public void setEndTm(String endTm) {
		this.endTm = endTm;
	}
	public String getEduNm() {
		return eduNm;
	}
	public void setEduNm(String eduNm) {
		this.eduNm = eduNm;
	}
	public int getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Date getMoveDate() {
		return moveDate;
	}
	public void setMoveDate(Date moveDate) {
		this.moveDate = moveDate;
	}
	public Double getAttRatio() {
		return attRatio;
	}
	public void setAttRatio(Double attRatio) {
		this.attRatio = attRatio;
	}
	public String getStudNm() {
		return studNm;
	}
	public void setStudNm(String studNm) {
		this.studNm = studNm;
	}
	public String getReqOrgCd() {
		return reqOrgCd;
	}
	public void setReqOrgCd(String reqOrgCd) {
		this.reqOrgCd = reqOrgCd;
	}
	public String getReqProfId() {
		return reqProfId;
	}
	public void setReqProfId(String reqProfId) {
		this.reqProfId = reqProfId;
	}
	public String getReqUserMemLvl() {
		return reqUserMemLvl;
	}
	public void setReqUserMemLvl(String reqUserMemLvl) {
		this.reqUserMemLvl = reqUserMemLvl;
	}
	public String getReqStartDt() {
		return reqStartDt;
	}
	public void setReqStartDt(String reqStartDt) {
		this.reqStartDt = reqStartDt;
	}
	public String getReqEndDt() {
		return reqEndDt;
	}
	public void setReqEndDt(String reqEndDt) {
		this.reqEndDt = reqEndDt;
	}
	public int getReqMoveCnt() {
		return reqMoveCnt;
	}
	public void setReqMoveCnt(int reqMoveCnt) {
		this.reqMoveCnt = reqMoveCnt;
	}
	public String getReqStudId() {
		return reqStudId;
	}
	public void setReqStudId(String reqStudId) {
		this.reqStudId = reqStudId;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getFirstRecordIndex() {
		return firstRecordIndex;
	}
	public void setFirstRecordIndex(int firstRecordIndex) {
		this.firstRecordIndex = firstRecordIndex;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	public int getAttend2Cnt() {
		return attend2Cnt;
	}
	public void setAttend2Cnt(int attend2Cnt) {
		this.attend2Cnt = attend2Cnt;
	}
	public List<SmartCheckVO> getStdntAttList() {
		return stdntAttList;
	}
	public void setStdntAttList(List<SmartCheckVO> stdntAttList) {
		this.stdntAttList = stdntAttList;
	}
	public String getUserNm() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getUserNm();
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public String getAttCd() {
		return attCd;
	}
	public void setAttCd(String attCd) {
		this.attCd = attCd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public int getrNum() {
		return rNum;
	}
	public void setrNum(int rNum) {
		this.rNum = rNum;
	}
	
	
	public String getAddAttStr(){
		try {
			switch (this.attCd) {
			case "O":return "O";
			case "D":return "/";
			case "Z":return "/";
			case "X":return "X";
			default:return "-";
			}
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public int getAbsentCnt() {
		return absentCnt;
	}
	public void setAbsentCnt(int absentCnt) {
		this.absentCnt = absentCnt;
	}
	public int getLateCnt() {
		return lateCnt;
	}
	public void setLateCnt(int lateCnt) {
		this.lateCnt = lateCnt;
	}
	public int getLeaveCnt() {
		return leaveCnt;
	}
	public void setLeaveCnt(int leaveCnt) {
		this.leaveCnt = leaveCnt;
	}
	
	public String getAttNm2(){
		try {
			switch (this.attCd) {
			case "O":return "출석";
			case "D":return "지각";
			case "Z":return "조퇴";
			case "X":return "결석";
			default:return "대기";
			}
		} catch (NullPointerException e) {
			return "대기";
		}
	}
	public String getAttNm3(){
		try {
			switch (this.attCd) {
			case "O":return "○";
			case "D":return "/";
			case "Z":return "/";
			case "X":return "Χ";
			default:return "-";
			}
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public String checkStatusNm(){
		try {
			switch (this.checkStatus) {
				case 0: return "대기";
				case 1: return "완료";
				default: return "-";
			}
		} catch (NullPointerException e) {
			return "-";
		} 
	}
	public String tmNm(String tm){
		try {
			return tm.substring(0,2)+":"+tm.substring(2,4);
		} catch (NullPointerException e) {
			return "-";
		} 
	}
	public String getAuthNo() {
		return authNo;
	}
	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}
	public String getCheckLocate() {
		return checkLocate;
	}
	public void setCheckLocate(String checkLocate) {
		this.checkLocate = checkLocate;
	}
	public int getCheckType() {
		return checkType;
	}
	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}
	public int getAbsentFinalCnt() {
		return absentFinalCnt;
	}
	public void setAbsentFinalCnt(int absentFinalCnt) {
		this.absentFinalCnt = absentFinalCnt;
	}
	public int getTimeCnt() {
		return timeCnt;
	}
	public void setTimeCnt(int timeCnt) {
		this.timeCnt = timeCnt;
	}
	public int getStdntCnt() {
		return stdntCnt;
	}
	public void setStdntCnt(int stdntCnt) {
		this.stdntCnt = stdntCnt;
	}
	public int getCheckTimeCnt() {
		return checkTimeCnt;
	}
	public void setCheckTimeCnt(int checkTimeCnt) {
		this.checkTimeCnt = checkTimeCnt;
	}
	public int getSumTimeCnt() {
		return sumTimeCnt;
	}
	public void setSumTimeCnt(int sumTimeCnt) {
		this.sumTimeCnt = sumTimeCnt;
	}
	public int getEduDtCnt() {
		return eduDtCnt;
	}
	public void setEduDtCnt(int eduDtCnt) {
		this.eduDtCnt = eduDtCnt;
	}
	public int getAttendCnt() {
		return attendCnt;
	}
	public void setAttendCnt(int attendCnt) {
		this.attendCnt = attendCnt;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getMonDt() {
		return monDt;
	}
	public void setMonDt(String monDt) {
		this.monDt = monDt;
	}
	public String getTueDt() {
		return tueDt;
	}
	public void setTueDt(String tueDt) {
		this.tueDt = tueDt;
	}
	public String getWedDt() {
		return wedDt;
	}
	public void setWedDt(String wedDt) {
		this.wedDt = wedDt;
	}
	public String getThuDt() {
		return thuDt;
	}
	public void setThuDt(String thuDt) {
		this.thuDt = thuDt;
	}
	public String getFriDt() {
		return friDt;
	}
	public void setFriDt(String friDt) {
		this.friDt = friDt;
	}
	public String getSatDt() {
		return satDt;
	}
	public void setSatDt(String satDt) {
		this.satDt = satDt;
	}
	public String getNowDt() {
		return nowDt;
	}
	public void setNowDt(String nowDt) {
		this.nowDt = nowDt;
	}
	public String getSunDt() {
		return sunDt;
	}
	public void setSunDt(String sunDt) {
		this.sunDt = sunDt;
	}
	public String getReqEduDt() {
		return reqEduDt;
	}
	public void setReqEduDt(String reqEduDt) {
		this.reqEduDt = reqEduDt;
	}
	public String getProfNm() {
		return profNm;
	}
	public void setProfNm(String profNm) {
		this.profNm = profNm;
	}
	public String getAttRatio2(){
		try {
			double result0 = (timeCnt-((double)absentCnt+(double)((lateCnt+leaveCnt)/3)))/timeCnt*100.0;
			result0=Math.round(result0*10)/10.0;
			return String.valueOf(result0);
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public String getEduCtgryNm() {
		return eduCtgryNm;
	}
	public void setEduCtgryNm(String eduCtgryNm) {
		this.eduCtgryNm = eduCtgryNm;
	}
	public String getEduPeriodBegin() {
		return eduPeriodBegin;
	}
	public void setEduPeriodBegin(String eduPeriodBegin) {
		this.eduPeriodBegin = eduPeriodBegin;
	}
	public int getReqCtgry1() {
		return reqCtgry1;
	}
	public void setReqCtgry1(String reqCtgry1) {
		try {
			this.reqCtgry1 = Integer.parseInt(reqCtgry1);
		} catch (NullPointerException e) {
			this.reqCtgry1 = 0;
		}
	}
	public int getReqCtgry2() {
		return reqCtgry2;
	}
	public void setReqCtgry2(String reqCtgry2) {
		try {
			this.reqCtgry2 = Integer.parseInt(reqCtgry2);
		} catch (NullPointerException e) {
			this.reqCtgry2 = 0;
		}
	}
	public String getReqWrd() {
		return reqWrd;
	}
	public void setReqWrd(String reqWrd) {
		this.reqWrd = reqWrd;
	}
	public String getReqWrdKey() {
		return reqWrdKey;
	}
	public void setReqWrdKey(String reqWrdKey) {
		this.reqWrdKey = reqWrdKey;
	}
	public int getClassHow() {
		return classHow;
	}
	public void setClassHow(int classHow) {
		this.classHow = classHow;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	public Date getBeginDe() {
		return beginDe;
	}
	public void setBeginDe(Date beginDe) {
		this.beginDe = beginDe;
	}
	public Date getEndDe() {
		return endDe;
	}
	public void setEndDe(Date endDe) {
		this.endDe = endDe;
	}
	public String getBeginHhMm() {
		return beginHhMm;
	}
	public void setBeginHhMm(String beginHhMm) {
		this.beginHhMm = beginHhMm;
	}
	public String getEndHhMm() {
		return endHhMm;
	}
	public void setEndHhMm(String endHhMm) {
		this.endHhMm = endHhMm;
	}
	public int getAttTp() {
		return attTp;
	}
	public void setAttTp(int attTp) {
		this.attTp = attTp;
	}
	public Double getAtt2Ratio() {
		return att2Ratio;
	}
	public void setAtt2Ratio(Double att2Ratio) {
		this.att2Ratio = att2Ratio;
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
	public int getAbsent2Cnt() {
		return absent2Cnt;
	}
	public void setAbsent2Cnt(int absent2Cnt) {
		this.absent2Cnt = absent2Cnt;
	}
	public int getIsQr1() {
		return isQr1;
	}
	public void setIsQr1(int isQr1) {
		this.isQr1 = isQr1;
	}
	public int getIsQr2() {
		return isQr2;
	}
	public void setIsQr2(int isQr2) {
		this.isQr2 = isQr2;
	}
	public int getIsQr3() {
		return isQr3;
	}
	public void setIsQr3(int isQr3) {
		this.isQr3 = isQr3;
	}
	public String getAt1Cd() {
		return at1Cd;
	}
	public void setAt1Cd(String at1Cd) {
		this.at1Cd = at1Cd;
	}
	public String getAt2Cd() {
		return at2Cd;
	}
	public void setAt2Cd(String at2Cd) {
		this.at2Cd = at2Cd;
	}
	public String getAt3Cd() {
		return at3Cd;
	}
	public void setAt3Cd(String at3Cd) {
		this.at3Cd = at3Cd;
	}
	public String getAt1HhMm() {
		return at1HhMm;
	}
	public void setAt1HhMm(String at1HhMm) {
		this.at1HhMm = at1HhMm;
	}
	public String getAt2HhMm() {
		return at2HhMm;
	}
	public void setAt2HhMm(String at2HhMm) {
		this.at2HhMm = at2HhMm;
	}
	public String getAt3HhMm() {
		return at3HhMm;
	}
	public void setAt3HhMm(String at3HhMm) {
		this.at3HhMm = at3HhMm;
	}
	public Date getAt1De() {
		return at1De;
	}
	public void setAt1De(Date at1De) {
		this.at1De = at1De;
	}
	public Date getAt2De() {
		return at2De;
	}
	public void setAt2De(Date at2De) {
		this.at2De = at2De;
	}
	public Date getAt3De() {
		return at3De;
	}
	public void setAt3De(Date at3De) {
		this.at3De = at3De;
	}
	public Date getQr1BeginDe() {
		return qr1BeginDe;
	}
	public void setQr1BeginDe(Date qr1BeginDe) {
		this.qr1BeginDe = qr1BeginDe;
	}
	public Date getQr1EndDe() {
		return qr1EndDe;
	}
	public void setQr1EndDe(Date qr1EndDe) {
		this.qr1EndDe = qr1EndDe;
	}
	public Date getQr2BeginDe() {
		return qr2BeginDe;
	}
	public void setQr2BeginDe(Date qr2BeginDe) {
		this.qr2BeginDe = qr2BeginDe;
	}
	public Date getQr2EndDe() {
		return qr2EndDe;
	}
	public void setQr2EndDe(Date qr2EndDe) {
		this.qr2EndDe = qr2EndDe;
	}
	public Date getQr3BeginDe() {
		return qr3BeginDe;
	}
	public void setQr3BeginDe(Date qr3BeginDe) {
		this.qr3BeginDe = qr3BeginDe;
	}
	public Date getQr3EndDe() {
		return qr3EndDe;
	}
	public void setQr3EndDe(Date qr3EndDe) {
		this.qr3EndDe = qr3EndDe;
	}
	
	public String getUserOrgNm() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getUserOrgNm();
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public String getUserGradeNm() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getUserGradeNm();
		} catch (NullPointerException e) {
			return "-";
		}
	}
}

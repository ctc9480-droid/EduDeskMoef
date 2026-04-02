package com.educare.edu.comn.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 */
public class FeedbackVO{
	private int rowCnt;
	private int firstIndex;
	private int pageNo;
	private String srchWrd;
	
	private int eduSeq;
	private int idx;
	private String orgCd;
	private String title;
	private String content;
	private String regId;
	private String regNm;
	private Date regTime;
	
	private int fbIdx;
	private int qtIdx;
	private int qtType;
	private String question;
	private String qtDiv;
	private String essntlYn;
	
	private int chIdx;
	private String choice;
	private int point;
	
	private String answer;
	
	private int qtType0Cnt;//주관식 갯수
	private int qtType1Cnt;//객관식 갯수
	private int asCnt;//답안제출수
	private int qtAsCnt;//총 답안제출수
	private int asChIdx;
	private List<Integer> asChIdx2=new ArrayList<Integer>();
	private List<Integer> eduSeqArr = new ArrayList<Integer>();
	private Date regDe;
	private Date startDe;
	private Date endDe;
	private String userId;
	private int feSeq;
	private int feResultState;
	private Date feResultRegDe;
	private String fbNm;
	
	public int getFbIdx() {
		return fbIdx;
	}
	public void setFbIdx(int fbIdx) {
		this.fbIdx = fbIdx;
	}
	public int getQtType0Cnt() {
		return qtType0Cnt;
	}
	public void setQtType0Cnt(int qtType0Cnt) {
		this.qtType0Cnt = qtType0Cnt;
	}
	public int getQtType1Cnt() {
		return qtType1Cnt;
	}
	public void setQtType1Cnt(int qtType1Cnt) {
		this.qtType1Cnt = qtType1Cnt;
	}
	public int getQtIdx() {
		return qtIdx;
	}
	public void setQtIdx(int qtIdx) {
		this.qtIdx = qtIdx;
	}
	public int getQtType() {
		return qtType;
	}
	public void setQtType(int qtType) {
		this.qtType = qtType;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getChIdx() {
		return chIdx;
	}
	public void setChIdx(int chIdx) {
		this.chIdx = chIdx;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
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
	public int getAsChIdx() {
		return asChIdx;
	}
	public void setAsChIdx(int asChIdx) {
		this.asChIdx = asChIdx;
	}
	public List<Integer> getAsChIdx2() {
		return asChIdx2;
	}
	public void setAsChIdx2(List<Integer> asChIdx2) {
		this.asChIdx2 = asChIdx2;
	}
	public int getQtAsCnt() {
		return qtAsCnt;
	}
	public void setQtAsCnt(int qtAsCnt) {
		this.qtAsCnt = qtAsCnt;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getQtDiv() {
		return qtDiv;
	}
	public void setQtDiv(String qtDiv) {
		this.qtDiv = qtDiv;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getEssntlYn() {
		return essntlYn;
	}
	public void setEssntlYn(String essntlYn) {
		this.essntlYn = essntlYn;
	}
	public List<Integer> getEduSeqArr() {
		return eduSeqArr;
	}
	public void setEduSeqArr(List<Integer> eduSeqArr) {
		this.eduSeqArr = eduSeqArr;
	}
	public Date getRegDe() {
		return regDe;
	}
	public void setRegDe(Date regDe) {
		this.regDe = regDe;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getRowCnt() {
		return rowCnt;
	}
	public void setRowCnt(int rowCnt) {
		this.rowCnt = rowCnt;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getFeSeq() {
		return feSeq;
	}
	public void setFeSeq(int feSeq) {
		this.feSeq = feSeq;
	}
	public int getFeResultState() {
		return feResultState;
	}
	public void setFeResultState(int feResultState) {
		this.feResultState = feResultState;
	}
	public Date getFeResultRegDe() {
		return feResultRegDe;
	}
	public void setFeResultRegDe(Date feResultRegDe) {
		this.feResultRegDe = feResultRegDe;
	}
	public String getFbNm() {
		return fbNm;
	}
	public void setFbNm(String fbNm) {
		this.fbNm = fbNm;
	}
	public String getSrchWrd() {
		return srchWrd;
	}
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
}

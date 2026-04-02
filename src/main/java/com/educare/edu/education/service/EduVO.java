package com.educare.edu.education.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.educare.util.LncUtil;

/**
 
 */
public class EduVO {
	
	private Integer ctgrySeq;
	
	private Integer ctgryDepth;
	
	private Integer selectSeq;
	
	private Integer targetSeq;
	
	private Integer[] ctgrySeqs;
	
	private int eduSeq;
	
	private Integer[] eduSeqs;
	
	private String srchColumn;
	
	private String srchWrd;
	
	private int srchCtgry;
	private int srchCtgry2;
	private int srchCtgry3;
	
	private Integer page = 1;
	
	private Integer row = 10;
	
	private Integer firstIndex;
	
	private String srchYear;
	
	private String srchMonth;

	private String srchDay;
	
	private Integer viewCnt;
	
	private String srchDateBegin;
	
	private String srchDateEnd;
	
	private String userId;
	private String userNm;
	
	private String[] userIds;
	
	private String command;
	
	private String srchAgency;
	private String srchDate;
	private int srchSidoCd;
	private String srchElem;
	
	private String orgCd;
	private String srchSort;
	private String srchRcept;
	private String gubun = "Open";
	
	private String searchText;
	private String srchOprtUserId;
	private String srchRegId;
	private String srchCtgryAuthYn;
	private String scrtyPw;
	private String eduDt;
	private int roomSeq;
	private String totalSrchText;
	private String nowDtime;
	private Date nowDt;
	private String srchDtGb;//신청일(R),교육일(E) 구분
	private String eduYear;
	private String eduTerm;
	private int srchState;
	private int srchRceptState;
	private int srchStdntState;
	private int srchFeeTp;
	private int rceptSeq;
	private List<Map<String, Object>> srchArr;//배열조건용
	private boolean isAdmin;
	private int tabNo;
	private int passTp;		//수료증구분
	/**
	 * 
	 */
	public EduVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param eduSeq
	 * @param userId
	 */
	public EduVO(int eduSeq, String userId) {
		super();
		this.eduSeq = eduSeq;
		this.userId = userId;
	}

	/**
	 * @return the ctgrySeq
	 */
	public Integer getCtgrySeq() {
		return ctgrySeq;
	}

	/**
	 * @param ctgrySeq the ctgrySeq to set
	 */
	public void setCtgrySeq(Integer ctgrySeq) {
		this.ctgrySeq = ctgrySeq;
	}

	/**
	 * @return the ctgryDepth
	 */
	public Integer getCtgryDepth() {
		return ctgryDepth;
	}

	/**
	 * @param ctgryDepth the ctgryDepth to set
	 */
	public void setCtgryDepth(Integer ctgryDepth) {
		this.ctgryDepth = ctgryDepth;
	}

	/**
	 * @return the selectSeq
	 */
	public Integer getSelectSeq() {
		return selectSeq;
	}

	/**
	 * @param selectSeq the selectSeq to set
	 */
	public void setSelectSeq(Integer selectSeq) {
		this.selectSeq = selectSeq;
	}

	/**
	 * @return the targetSeq
	 */
	public Integer getTargetSeq() {
		return targetSeq;
	}

	/**
	 * @param targetSeq the targetSeq to set
	 */
	public void setTargetSeq(Integer targetSeq) {
		this.targetSeq = targetSeq;
	}

	/**
	 * @return the ctgrySeqs
	 */
	public Integer[] getCtgrySeqs() {
		if(ctgrySeqs == null){
			return null;
		}
		Integer[] newArr = new Integer[ctgrySeqs.length];
		System.arraycopy(ctgrySeqs, 0, newArr, 0, ctgrySeqs.length);
		return newArr;
	}

	/**
	 * @param ctgrySeqs the ctgrySeqs to set
	 */
	public void setCtgrySeqs(Integer[] ctgrySeqs) {
		this.ctgrySeqs = ctgrySeqs;
	}

	/**
	 * @return the eduSeq
	 */
	public int getEduSeq() {
		return eduSeq;
	}

	/**
	 * @param eduSeq the eduSeq to set
	 */
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}

	/**
	 * @return the eduSeqs
	 */
	public Integer[] getEduSeqs() {
		if(eduSeqs == null){
			return null;
		}
		Integer[] newArr = new Integer[eduSeqs.length];
		System.arraycopy(eduSeqs, 0, newArr, 0, eduSeqs.length);
		return newArr;
	}

	/**
	 * @param eduSeqs the eduSeqs to set
	 */
	public void setEduSeqs(Integer[] eduSeqs) {
		this.eduSeqs = eduSeqs;
	}

	/**
	 * @return the srchColumn
	 */
	public String getSrchColumn() {
		return srchColumn;
	}

	/**
	 * @param srchColumn the srchColumn to set
	 */
	public void setSrchColumn(String srchColumn) {
		this.srchColumn = LncUtil.replaceXSS(srchColumn);
	}

	/**
	 * @return the srchWrd
	 */
	public String getSrchWrd() {
		return srchWrd;
	}

	/**
	 * @param srchWrd the srchWrd to set
	 */
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = LncUtil.replaceXSS(srchWrd);
	}

	/**
	 * @return the srchCtgry
	 */
	public int getSrchCtgry() {
		return srchCtgry;
	}

	/**
	 * @param srchCtgry the srchCtgry to set
	 */
	public void setSrchCtgry(int srchCtgry) {
		this.srchCtgry = srchCtgry;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the row
	 */
	public Integer getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(Integer row) {
		this.row = row;
	}

	/**
	 * @return the firstIndex
	 */
	public Integer getFirstIndex() {
		return firstIndex;
	}

	/**
	 * @param firstIndex the firstIndex to set
	 */
	public void setFirstIndex(Integer firstIndex) {
		this.firstIndex = firstIndex;
	}

	/**
	 * @return the srchYear
	 */
	public String getSrchYear() {
		return srchYear;
	}

	/**
	 * @param srchYear the srchYear to set
	 */
	public void setSrchYear(String srchYear) {
		this.srchYear = LncUtil.replaceXSS(srchYear);
	}

	/**
	 * @return the srchMonth
	 */
	public String getSrchMonth() {
		return srchMonth;
	}

	/**
	 * @param srchMonth the srchMonth to set
	 */
	public void setSrchMonth(String srchMonth) {
		this.srchMonth = LncUtil.replaceXSS(srchMonth);
	}

	/**
	 * @return the viewCnt
	 */
	public Integer getViewCnt() {
		return viewCnt;
	}

	/**
	 * @param viewCnt the viewCnt to set
	 */
	public void setViewCnt(Integer viewCnt) {
		this.viewCnt = viewCnt;
	}

	/**
	 * @return the srchDateBegin
	 */
	public String getSrchDateBegin() {
		return srchDateBegin;
	}

	/**
	 * @param srchDateBegin the srchDateBegin to set
	 */
	public void setSrchDateBegin(String srchDateBegin) {
		this.srchDateBegin = LncUtil.replaceXSS(srchDateBegin);
	}

	/**
	 * @return the srchDateEdn
	 */
	public String getSrchDateEnd() {
		return srchDateEnd;
	}

	/**
	 * @param srchDateEdn the srchDateEdn to set
	 */
	public void setSrchDateEnd(String srchDateEnd) {
		this.srchDateEnd = LncUtil.replaceXSS(srchDateEnd);
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
		this.userId = LncUtil.replaceXSS(userId);
	}

	/**
	 * @return the userIds
	 */
	public String[] getUserIds() {
		if(userIds == null){
			return null;
		}
		String[] newArr = new String[userIds.length];
		System.arraycopy(userIds, 0, newArr, 0, userIds.length);
		return newArr;
	}

	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = LncUtil.replaceXSS(command);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////


	public String getSrchAgency() {
		return srchAgency;
	}

	public void setSrchAgency(String srchAgency) {
		this.srchAgency = srchAgency;
	}

	public String getSrchDate() {
		return srchDate;
	}

	public void setSrchDate(String srchDate) {
		this.srchDate = srchDate;
	}

	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}

	public String getSrchSort() {
		return srchSort;
	}

	public void setSrchSort(String srchSort) {
		this.srchSort = srchSort;
	}
	
	public String getSrchRcept() {
		return srchRcept;
	}

	public void setSrchRcept(String srchRcept) {
		this.srchRcept = srchRcept;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public String[] getSearchTextArr(){
		String[] result = {};
		try {
			String[] newStr = this.searchText.split("\\s+");
			result = newStr;
		} catch (NullPointerException e) {
			return result;
		}
		return result;
	}

	public int getSrchSidoCd() {
		return srchSidoCd;
	}

	public void setSrchSidoCd(int srchSidoCd) {
		this.srchSidoCd = srchSidoCd;
	}

	public int getSrchCtgry2() {
		return srchCtgry2;
	}

	public void setSrchCtgry2(int srchCtgry2) {
		this.srchCtgry2 = srchCtgry2;
	}
	public String getSrchOprtUserId() {
		return srchOprtUserId;
	}

	public void setSrchOprtUserId(String srchOprtUserId) {
		this.srchOprtUserId = srchOprtUserId;
	}

	public String getSrchRegId() {
		return srchRegId;
	}

	public void setSrchRegId(String srchRegId) {
		this.srchRegId = srchRegId;
	}

	public String getSrchDay() {
		return srchDay;
	}

	public void setSrchDay(String srchDay) {
		this.srchDay = srchDay;
	}

	public int getSrchCtgry3() {
		return srchCtgry3;
	}

	public void setSrchCtgry3(int srchCtgry3) {
		this.srchCtgry3 = srchCtgry3;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getScrtyPw() {
		return scrtyPw;
	}

	public void setScrtyPw(String scrtyPw) {
		this.scrtyPw = scrtyPw;
	}

	public String getSrchCtgryAuthYn() {
		return srchCtgryAuthYn;
	}

	public void setSrchCtgryAuthYn(String srchCtgryAuthYn) {
		this.srchCtgryAuthYn = srchCtgryAuthYn;
	}

	public String getEduDt() {
		return eduDt;
	}

	public void setEduDt(String eduDt) {
		this.eduDt = eduDt;
	}

	public int getRoomSeq() {
		return roomSeq;
	}

	public void setRoomSeq(int roomSeq) {
		this.roomSeq = roomSeq;
	}

	public String getTotalSrchText() {
		return totalSrchText;
	}

	public void setTotalSrchText(String totalSrchText) {
		this.totalSrchText = totalSrchText;
	}

	public String getNowDtime() {
		return nowDtime;
	}

	public void setNowDtime(String nowDtime) {
		this.nowDtime = nowDtime;
	}

	public String getSrchElem() {
		return srchElem;
	}

	public void setSrchElem(String srchElem) {
		this.srchElem = srchElem;
	}

	public String getSrchDtGb() {
		return srchDtGb;
	}

	public void setSrchDtGb(String srchDtGb) {
		this.srchDtGb = srchDtGb;
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

	public List<Map<String, Object>> getSrchArr() {
		return srchArr;
	}

	public void setSrchArr(List<Map<String, Object>> srchArr) {
		this.srchArr = srchArr;
	}
	public int getRceptSeq() {
		return rceptSeq;
	}

	public void setRceptSeq(int rceptSeq) {
		this.rceptSeq = rceptSeq;
	}

	public Date getNowDt() {
		return nowDt;
	}

	public void setNowDt(Date nowDt) {
		this.nowDt = nowDt;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getTabNo() {
		return tabNo;
	}

	public void setTabNo(int tabNo) {
		this.tabNo = tabNo;
	}

	public int getSrchRceptState() {
		return srchRceptState;
	}

	public void setSrchRceptState(int srchRceptState) {
		this.srchRceptState = srchRceptState;
	}

	public int getSrchStdntState() {
		return srchStdntState;
	}

	public void setSrchStdntState(int srchStdntState) {
		this.srchStdntState = srchStdntState;
	}

	public int getSrchState() {
		return srchState;
	}

	public void setSrchState(int srchState) {
		this.srchState = srchState;
	}

	public int getPassTp() {
		return passTp;
	}

	public void setPassTp(int passTp) {
		this.passTp = passTp;
	}

	public int getSrchFeeTp() {
		return srchFeeTp;
	}

	public void setSrchFeeTp(int srchFeeTp) {
		this.srchFeeTp = srchFeeTp;
	}


}

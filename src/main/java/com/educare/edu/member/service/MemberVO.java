package com.educare.edu.member.service;

import java.util.List;
import java.util.Map;

import com.educare.edu.log.service.model.ConnLogAdm;
import com.educare.edu.member.service.model.InstrctrRealm;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.menu.service.model.MenuAuth;
import com.educare.util.LncUtil;

/**
 * @Class Name : MemberVO.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 4.
 * @version 1.0
 * @see
 * @Description 회원 VO
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 4.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class MemberVO {
	
	/** 아이디 */
	private String userId;
	
	/** 사용자레벨 1:슈퍼관리자, 2:중간관리자, 8:강사, 9:교육생 */
	private String userMemLvl;
	
	/** 검색 컬럼 */
	private String srchColumn;
	
	/** 검색어 */
	private String srchWrd;
	
	/** 상태 */
	private String state;
	
	/** 페이지 번호 */
	private Integer page = 1;
	
	/** 페이지당 노출갯수 */
	private Integer row = 10;
	
	/** 현재페이지 첫번째 인덱스 */
	private Integer firstIndex;
	
	/** 정렬대상 컬럼 */
	private String sortColumn;
	
	/** 정렬기준 A:ASC, D:DESC */
	private String sortType;
	
	/** 아이디 배열 - 선택처리에 사용 */
	private String[] userIds;
	
	/** 메뉴아이디 배열 - 선택처리에 사용 */
	private String[] menuIds;
	
	/** 강사분야 배열 - 선택처리에 사용 */
	private Integer[] ctgrySeqs;
	
	/** 강사 첨부파일 삭제 일련번호 */
	private String attachDelSeqs;
	
	/** 임시파일(자격증) 일련번호 */
	private String fileSeqs01;
	
	/** 임시파일(이력서) 일련번호 */
	private String fileSeqs02;
	
	/** 임시파일(내부교육 수료증) 일련번호 */
	private String fileSeqs03;
	
	/** 임시파일(기타자료) 일련번호 */
	private String fileSeqs09;
	
	/** 비밀번호 확인 */
	private String userPw2;
	
	/** 파일구분 01:자격증, 02:이력서, 03:내부교육수료증, 09:기타*/
	private String fileSection;
	
	/** com.educare.edu.member.service.model.UserInfo */
	private UserInfo userInfo;
	
	/** 메뉴권한 리스트 */
	private List<MenuAuth> menuAuthList;
	
	/** 접속로그 리스트 */
	private List<ConnLogAdm> connLogList;
	
	/** 강사모듈 맵 */
	private Map<String, String> moduleMap;
	
	/** 강사모듈 리스트 */
	private List<InstrctrRealm> moduleList;
	
	/** 휴면회원 비교날짜 yyyyMMdd */
	private String compareDe;
	
	/** 성명 */
	private String userNm;
	
	/** 휴대전화 */
	private String mobile;
	
	private String email;
	private String loginId;
	private String srchMemLvl;
	private int srchPosition1;
	private String[] srchWrdArr;
	
	private String officePositionYn;
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
	 * @return the userMemLvl
	 */
	public String getUserMemLvl() {
		return userMemLvl;
	}

	/**
	 * @param userMemLvl the userMemLvl to set
	 */
	public void setUserMemLvl(String userMemLvl) {
		this.userMemLvl = LncUtil.replaceXSS(userMemLvl);
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
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = LncUtil.replaceXSS(state);
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
	public void setPage(Integer page) {
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
	 * @return the sortColumn
	 */
	public String getSortColumn() {
		return sortColumn;
	}

	/**
	 * @param sortColumn the sortColumn to set
	 */
	public void setSortColumn(String sortColumn) {
		this.sortColumn = LncUtil.replaceXSS(sortColumn);
	}

	/**
	 * @return the sortType
	 */
	public String getSortType() {
		return sortType;
	}

	/**
	 * @param sortType the sortType to set
	 */
	public void setSortType(String sortType) {
		this.sortType = LncUtil.replaceXSS(sortType);
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
	 * @return the menuIds
	 */
	public String[] getMenuIds() {
		if(menuIds == null){
			return null;
		}
		String[] newArr = new String[menuIds.length];
		System.arraycopy(menuIds, 0, newArr, 0, menuIds.length);
		return newArr;
	}

	/**
	 * @param menuIds the menuIds to set
	 */
	public void setMenuIds(String[] menuIds) {
		this.menuIds = menuIds;
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
	 * @return the attachDelSeqs
	 */
	public String getAttachDelSeqs() {
		return attachDelSeqs;
	}

	/**
	 * @param attachDelSeqs the attachDelSeqs to set
	 */
	public void setAttachDelSeqs(String attachDelSeqs) {
		this.attachDelSeqs = attachDelSeqs;
	}

	/**
	 * @return the userPw2
	 */
	public String getUserPw2() {
		return userPw2;
	}

	/**
	 * @param userPw2 the userPw2 to set
	 */
	public void setUserPw2(String userPw2) {
		this.userPw2 = LncUtil.replaceXSS(userPw2);
	}

	/**
	 * @return the fileSection
	 */
	public String getFileSection() {
		return fileSection;
	}

	/**
	 * @param fileSection the fileSection to set
	 */
	public void setFileSection(String fileSection) {
		this.fileSection = LncUtil.replaceXSS(fileSection);
	}

	/**
	 * @return the fileSeqs01
	 */
	public String getFileSeqs01() {
		return fileSeqs01;
	}

	/**
	 * @param fileSeqs01 the fileSeqs01 to set
	 */
	public void setFileSeqs01(String fileSeqs01) {
		this.fileSeqs01 = LncUtil.replaceXSS(fileSeqs01);
	}

	/**
	 * @return the fileSeqs02
	 */
	public String getFileSeqs02() {
		return fileSeqs02;
	}

	/**
	 * @param fileSeqs02 the fileSeqs02 to set
	 */
	public void setFileSeqs02(String fileSeqs02) {
		this.fileSeqs02 = LncUtil.replaceXSS(fileSeqs02);
	}

	/**
	 * @return the fileSeqs03
	 */
	public String getFileSeqs03() {
		return fileSeqs03;
	}

	/**
	 * @param fileSeqs03 the fileSeqs03 to set
	 */
	public void setFileSeqs03(String fileSeqs03) {
		this.fileSeqs03 = LncUtil.replaceXSS(fileSeqs03);
	}

	/**
	 * @return the fileSeqs09
	 */
	public String getFileSeqs09() {
		return fileSeqs09;
	}

	/**
	 * @param fileSeqs09 the fileSeqs09 to set
	 */
	public void setFileSeqs09(String fileSeqs09) {
		this.fileSeqs09 = LncUtil.replaceXSS(fileSeqs09);
	}

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the menuAuthList
	 */
	public List<MenuAuth> getMenuAuthList() {
		return menuAuthList;
	}

	/**
	 * @param menuAuthList the menuAuthList to set
	 */
	public void setMenuAuthList(List<MenuAuth> menuAuthList) {
		this.menuAuthList = menuAuthList;
	}

	/**
	 * @return the connLogList
	 */
	public List<ConnLogAdm> getConnLogList() {
		return connLogList;
	}

	/**
	 * @param connLogList the connLogList to set
	 */
	public void setConnLogList(List<ConnLogAdm> connLogList) {
		this.connLogList = connLogList;
	}

	/**
	 * @return the moduleMap
	 */
	public Map<String, String> getModuleMap() {
		return moduleMap;
	}

	/**
	 * @param moduleMap the moduleMap to set
	 */
	public void setModuleMap(Map<String, String> moduleMap) {
		this.moduleMap = moduleMap;
	}

	/**
	 * @return the moduleList
	 */
	public List<InstrctrRealm> getModuleList() {
		return moduleList;
	}

	/**
	 * @param moduleList the moduleList to set
	 */
	public void setModuleList(List<InstrctrRealm> moduleList) {
		this.moduleList = moduleList;
	}

	/**
	 * @return the compareDe
	 */
	public String getCompareDe() {
		return compareDe;
	}

	/**
	 * @param compareDe the compareDe to set
	 */
	public void setCompareDe(String compareDe) {
		this.compareDe = compareDe;
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
		this.userNm = LncUtil.replaceXSS(userNm);
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = LncUtil.replaceXSS(mobile);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getOfficePositionYn() {
		return officePositionYn;
	}

	public void setOfficePositionYn(String officePositionYn) {
		this.officePositionYn = officePositionYn;
	}

	public int getSrchPosition1() {
		return srchPosition1;
	}

	public void setSrchPosition1(int srchPosition1) {
		this.srchPosition1 = srchPosition1;
	}

	public String[] getSrchWrdArr() {
		return srchWrdArr;
	}

	public void setSrchWrdArr(String[] srchWrdArr) {
		this.srchWrdArr = srchWrdArr;
	}

	public String getSrchMemLvl() {
		return srchMemLvl;
	}

	public void setSrchMemLvl(String srchMemLvl) {
		this.srchMemLvl = srchMemLvl;
	}
}

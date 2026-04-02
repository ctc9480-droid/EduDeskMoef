package com.educare.edu.member.service.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.educare.component.VarComponent;
import com.educare.util.CryptoSeedUtil;
import com.educare.util.LncUtil;

/**
 * @Class Name : UserInfo.java
 * @author SI개발팀 박용주
 * @since 2020. 5. 27.
 * @version 1.0
 * @see
 * @Description 회원정보 Model (T_USER_INFO)
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 5. 27.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Component
public class UserInfo implements Serializable{
	
	private static final long serialVersionUID = 8314673113005944408L;

	/** 회원상태 - 승인대기 */
	public static final String STATE_WAIT = "W";
	
	/** 회원상태 - 승인 */
	public static final String STATE_APPROVAL = "A";
	
	/** 회원상태 - 반려 */
	public static final String STATE_REFUSE = "R";
	
	/** 회원상태 - 탈퇴 */
	public static final String STATE_SECESSION = "S";
	
	/** 회원상태 - 기타 */
	public static final String STATE_ETC = "E";
	
	/** 사용자레벨 - 슈퍼관리자 */
	public static final String MEM_LVL_SUPER_ADM = "1";
	
	/** 사용자레벨 - 중간관리자 */
	public static final String MEM_LVL_MID_ADM = "2";
	/** 사용자레벨 - 중간관리자2 */
	public static final String MEM_LVL_MID2_ADM = "3";
	/** 사용자레벨 - 중간관리자2 - 외부컨설팅 */
	public static final String MEM_LVL_MID3_ADM = "4";
	
	/** 사용자레벨 - 강사 */
	public static final String MEM_LVL_INSTRCTR = "8";
	
	/** 사용자레벨 - 교육생 */
	public static final String MEM_LVL_STDNT = "9";
	/** 사용자레벨 - 교육생 - 준회원 */
	public static final String MEM_LVL_STDNT2 = "7";
	
	
	/** 아이디 */
	private String userId;
	
	/** 이름 */
	private String userNm;
	private String userEnNm;
	
	/** 비밀번호 */
	private String userPw;
	
	/** 이메일 */
	private String email;
	
	/** 전화번호 */
	private String tel;
	
	/** 핸드폰번호 */
	private String mobile;
	
	/** 생년월일 */
	private String birth;
	
	/** 성별 1:남자, 2:여자 */
	private int mfType;
	
	/** 우편번호 */
	private String postNo;
	
	/** 주소 */
	private String addr;
	
	/** 주소상세 */
	private String addrDetail;
	
	/** 주소 참고항목 */
	private String addrEtc;
	
	/** 사용자레벨 1:슈퍼관리자, 2:기관관리자, 8:강사, 9:교육생 */
	private String userMemLvl;
	
	/** 가입일자 */
	private Date rgsde;
	
	/** 마직막 로그인 일자 */
	private Date lstLoginDe;
	
	/** 마지막 패스워드 변경일자 */
	private Date lstPwDe;
	
	/** 총 로그인 횟수 */
	private Integer totalLoginCnt;
	
	/** 비밀번호 오류 횟수 */
	private int pwdErrorCnt;
	
	/** 상태 W:승인대기, A:승인, R:반려, S:탈퇴, E:기타 */
	private String state;
	
	/** 상태 변경일자 */
	private Date stateUpdDe;
	
	/** 상태 변경자 아이디 */
	private String stateUpdUserId;
	
	/** 개인식별코드 */
	private String ci;
	
	/** 기타1 */
	private String etc1;
	
	/** 기타2 */
	private String etc2;
	
	/** 기타3 */
	private String etc3;
	
	/** 기타4 */
	private String etc4;
	
	/** 기타5 */
	private String etc5;
	
	/** 기관 코드 */
	private String orgCd;
	
	private String loginId;
	private String smsYn;
	private int userTp;		
	private int empInsrTp;
	private int learnCardTp;
	private int empSuccTp;
	private int bizRgsTp;
	private String etc;
	private String compUserId;
	//
	private String orgNm;
	private String userGradeCd;
	private String userGradeNm;
	private String userOrgCd;
	private String userOrgNm;
	private String orgEn;
	private String encUserPw;
	private int useCheck;
	private int useGooroomee;
	private int useMov;
	private int position1;
	private int position2;
	
	private String subDn;
	private String gongYn;
	private String subDn2;
	
	private List<Long> userIds;
	private List<Integer> userMemLvls;
	
	public UserInfo(){
		
	}
	
	public UserInfo(String loginId, String userPw) {
		this.loginId = loginId;
		this.userPw = userPw;
	}
	
	public UserInfo(String userId, String state, String stateUpdUserId) {
		this.userId = userId;
		this.state = state;
		this.stateUpdUserId = stateUpdUserId;
	}

	/**
	 * @param userId
	 * @param userNm
	 * @param userPw
	 * @param email
	 * @param tel
	 * @param mobile
	 * @param birth
	 * @param mfType
	 * @param postNo
	 * @param addr
	 * @param addrDetail
	 * @param addrEtc
	 * @param userMemLvl
	 * @param rgsde
	 * @param lstLoginDe
	 * @param lstPwDe
	 * @param totalLoginCnt
	 * @param pwdErrorCnt
	 * @param state
	 * @param stateUpdDe
	 * @param stateUpdUserId
	 * @param ci
	 * @param etc1
	 * @param etc2
	 * @param etc3
	 * @param etc4
	 * @param etc5
	 */
	public UserInfo(String userId, String userNm, String userPw, String email, String tel, String mobile, String birth,
			int mfType, String postNo, String addr, String addrDetail, String addrEtc, String userMemLvl, Date rgsde,
			Date lstLoginDe, Date lstPwDe, Integer totalLoginCnt, Integer pwdErrorCnt, String state, Date stateUpdDe,
			String stateUpdUserId, String ci, String etc1, String etc2, String etc3, String etc4, String etc5) {
		super();
		this.userId = userId;
		this.userNm = userNm;
		this.userPw = userPw;
		this.email = email;
		this.tel = tel;
		this.mobile = mobile;
		this.birth = birth;
		this.mfType = mfType;
		this.postNo = postNo;
		this.addr = addr;
		this.addrDetail = addrDetail;
		this.addrEtc = addrEtc;
		this.userMemLvl = userMemLvl;
		this.rgsde = rgsde;
		this.lstLoginDe = lstLoginDe;
		this.lstPwDe = lstPwDe;
		this.totalLoginCnt = totalLoginCnt;
		this.pwdErrorCnt = pwdErrorCnt;
		this.state = state;
		this.stateUpdDe = stateUpdDe;
		this.stateUpdUserId = stateUpdUserId;
		this.ci = ci;
		this.etc1 = etc1;
		this.etc2 = etc2;
		this.etc3 = etc3;
		this.etc4 = etc4;
		this.etc5 = etc5;
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
	 * @return the userPw
	 */
	public String getUserPw() {
		return userPw;
	}

	/**
	 * @param userPw the userPw to set
	 */
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}
	public String getDecEmail() {
		try {
			return CryptoSeedUtil.decrypt(this.email);
		} catch (NullPointerException |IllegalArgumentException e) {
			return this.email;
		}
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = LncUtil.replaceXSS(email);
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return this.tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = LncUtil.replaceXSS(tel);
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return this.mobile;
	}
	public String getDecMobile() {
		try {
			return CryptoSeedUtil.decrypt(this.mobile);
		} catch (NullPointerException| IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
			return this.mobile;
		}
	}
	public String getDecTel() {
		try {
			return CryptoSeedUtil.decrypt(this.tel);
		} catch (NullPointerException| IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
			return this.tel;
		}
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = LncUtil.replaceXSS(mobile);
	}

	/**
	 * @return the birth
	 */
	public String getBirth() {
		return this.birth;
		
	}

	/**
	 * @param birth the birth to set
	 */
	public void setBirth(String birth) {
		this.birth = LncUtil.replaceXSS(birth);
	}

	/**
	 * @return the mfType
	 */
	public int getMfType() {
		try {
			return this.mfType;
		} catch (NullPointerException e) {
			return this.mfType;
		}
	}

	/**
	 * @param mfType the mfType to set
	 */
	public void setMfType(int mfType) {
		this.mfType = mfType;
	}

	/**
	 * @return the postNo
	 */
	public String getPostNo() {
		return postNo;
	}

	/**
	 * @param postNo the postNo to set
	 */
	public void setPostNo(String postNo) {
		this.postNo = LncUtil.replaceXSS(postNo);
	}

	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = LncUtil.replaceXSS(addr);
	}

	/**
	 * @return the addrDetail
	 */
	public String getAddrDetail() {
		return addrDetail;
	}

	/**
	 * @param addrDetail the addrDetail to set
	 */
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = LncUtil.replaceXSS(addrDetail);
	}

	/**
	 * @return the addrEtc
	 */
	public String getAddrEtc() {
		return addrEtc;
	}

	/**
	 * @param addrEtc the addrEtc to set
	 */
	public void setAddrEtc(String addrEtc) {
		this.addrEtc = LncUtil.replaceXSS(addrEtc);
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
		this.userMemLvl = userMemLvl;
	}

	/**
	 * @return the rgsde
	 */
	public Date getRgsde() {
		return rgsde;
	}

	/**
	 * @param rgsde the rgsde to set
	 */
	public void setRgsde(Date rgsde) {
		this.rgsde = rgsde;
	}

	/**
	 * @return the lstLoginDe
	 */
	public Date getLstLoginDe() {
		return lstLoginDe;
	}

	/**
	 * @param lstLoginDe the lstLoginDe to set
	 */
	public void setLstLoginDe(Date lstLoginDe) {
		this.lstLoginDe = lstLoginDe;
	}

	/**
	 * @return the lstPwDe
	 */
	public Date getLstPwDe() {
		return lstPwDe;
	}

	/**
	 * @param lstPwDe the lstPwDe to set
	 */
	public void setLstPwDe(Date lstPwDe) {
		this.lstPwDe = lstPwDe;
	}

	/**
	 * @return the totalLoginCnt
	 */
	public Integer getTotalLoginCnt() {
		return totalLoginCnt;
	}

	/**
	 * @param totalLoginCnt the totalLoginCnt to set
	 */
	public void setTotalLoginCnt(Integer totalLoginCnt) {
		this.totalLoginCnt = totalLoginCnt;
	}

	/**
	 * @return the pwdErrorCnt
	 */
	public Integer getPwdErrorCnt() {
		return pwdErrorCnt;
	}

	/**
	 * @param pwdErrorCnt the pwdErrorCnt to set
	 */
	public void setPwdErrorCnt(Integer pwdErrorCnt) {
		this.pwdErrorCnt = pwdErrorCnt;
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
		this.state = state;
	}

	/**
	 * @return the stateUpdDe
	 */
	public Date getStateUpdDe() {
		return stateUpdDe;
	}

	/**
	 * @param stateUpdDe the stateUpdDe to set
	 */
	public void setStateUpdDe(Date stateUpdDe) {
		this.stateUpdDe = stateUpdDe;
	}

	/**
	 * @return the stateUpdUserId
	 */
	public String getStateUpdUserId() {
		return stateUpdUserId;
	}

	/**
	 * @param stateUpdUserId the stateUpdUserId to set
	 */
	public void setStateUpdUserId(String stateUpdUserId) {
		this.stateUpdUserId = stateUpdUserId;
	}

	/**
	 * @return the ci
	 */
	public String getCi() {
		return ci;
	}

	/**
	 * @param ci the ci to set
	 */
	public void setCi(String ci) {
		this.ci = LncUtil.replaceXSS(ci);
	}

	/**
	 * @return the etc1
	 */
	public String getEtc1() {
		return etc1;
	}

	/**
	 * @param etc1 the etc1 to set
	 */
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	/**
	 * @return the etc2
	 */
	public String getEtc2() {
		return etc2;
	}

	/**
	 * @param etc2 the etc2 to set
	 */
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

	/**
	 * @return the etc3
	 */
	public String getEtc3() {
		return etc3;
	}

	/**
	 * @param etc3 the etc3 to set
	 */
	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}

	/**
	 * @return the etc4
	 */
	public String getEtc4() {
		return etc4;
	}

	/**
	 * @param etc4 the etc4 to set
	 */
	public void setEtc4(String etc4) {
		this.etc4 = etc4;
	}

	/**
	 * @return the etc5
	 */
	public String getEtc5() {
		return etc5;
	}

	/**
	 * @param etc5 the etc5 to set
	 */
	public void setEtc5(String etc5) {
		this.etc5 = etc5;
	}

	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}

	public String getEncUserPw() {
		return encUserPw;
	}

	public void setEncUserPw(String encUserPw) {
		this.encUserPw = encUserPw;
	}

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public String getOrgEn() {
		return orgEn;
	}

	public void setOrgEn(String orgEn) {
		this.orgEn = orgEn;
	}

	public int getUseCheck() {
		return useCheck;
	}

	public void setUseCheck(int useCheck) {
		this.useCheck = useCheck;
	}

	public int getUseGooroomee() {
		return useGooroomee;
	}

	public void setUseGooroomee(int useGooroomee) {
		this.useGooroomee = useGooroomee;
	}

	public int getUseMov() {
		return useMov;
	}

	public void setUseMov(int useMov) {
		this.useMov = useMov;
	}
	
	public String getMfTypeNm(){
		try {
			switch (this.mfType) {
			case 1:return "남";
			case 2:return "여";
			default:return "-";
			}
		} catch (NullPointerException e) {
			return "-";
		}
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public int getPosition1() {
		return position1;
	}

	public void setPosition1(int position1) {
		this.position1 = position1;
	}

	public int getPosition2() {
		return position2;
	}

	public void setPosition2(int position2) {
		this.position2 = position2;
	}

	public String getAddStateNm(){
		try {
			switch (this.state) {
			case "W":return "대기";
			case "S":return "탈퇴";
			case "R":return "반려";
			default:return "승인";
			}
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}

	//암호화 대상
	public String getEncEmail(){
		if(ObjectUtils.isEmpty(this.email)){
			return null;
		}
		return CryptoSeedUtil.encrypt(this.email);
	}
	public String getEncMobile(){
		if(ObjectUtils.isEmpty(this.mobile)){
			return null;
		}
		return CryptoSeedUtil.encrypt(this.mobile);
	}
	public String getEncTel(){
		if(ObjectUtils.isEmpty(this.tel)){
			return null;
		}
		return CryptoSeedUtil.encrypt(this.tel);
	}
	public String getAddUserMemLvlNm(){
		try {
			switch (this.userMemLvl) {
			case "1":return "최고관리자";
			case "2":return "부관리자";
			case "3":return "내부";
			case "4":return "외주";
			default:return "-";
			}
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}

	public int getUserTp() {
		return userTp;
	}

	public void setUserTp(int userTp) {
		this.userTp = userTp;
	}

	public int getEmpInsrTp() {
		return empInsrTp;
	}

	public void setEmpInsrTp(int empInsrTp) {
		this.empInsrTp = empInsrTp;
	}

	public int getLearnCardTp() {
		return learnCardTp;
	}

	public void setLearnCardTp(int learnCardTp) {
		this.learnCardTp = learnCardTp;
	}

	public int getEmpSuccTp() {
		return empSuccTp;
	}

	public void setEmpSuccTp(int empSuccTp) {
		this.empSuccTp = empSuccTp;
	}

	public int getBizRgsTp() {
		return bizRgsTp;
	}

	public void setBizRgsTp(int bizRgsTp) {
		this.bizRgsTp = bizRgsTp;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}
	
	public String getAddUserTpNm(){
		try {
			return VarComponent.getEduTargetNm(this.userTp+"");
		} catch (NullPointerException e) {
			return "-";
		}
	}

	public String getCompUserId() {
		return compUserId;
	}

	public void setCompUserId(String compUserId) {
		this.compUserId = compUserId;
	}
	
	public String getAddMfTypeNm(){
		String[] a = {"-","남","여"};
		//return a[getMfType()];
		return a[this.mfType];
	}

	public String getUserEnNm() {
		return userEnNm;
	}

	public void setUserEnNm(String userEnNm) {
		this.userEnNm = userEnNm;
	}

	public String getSubDn() {
		return subDn;
	}

	public void setSubDn(String subDn) {
		this.subDn = subDn;
	}

	public String getGongYn() {
		return gongYn;
	}

	public void setGongYn(String gongYn) {
		this.gongYn = gongYn;
	}

	public String getSmsYn() {
		return smsYn;
	}

	public void setSmsYn(String smsYn) {
		this.smsYn = smsYn;
	}

	public String getUserGradeCd() {
		return userGradeCd;
	}

	public void setUserGradeCd(String userGradeCd) {
		this.userGradeCd = userGradeCd;
	}

	public String getUserGradeNm() {
		return userGradeNm;
	}

	public void setUserGradeNm(String userGradeNm) {
		this.userGradeNm = userGradeNm;
	}

	public String getUserOrgCd() {
		return userOrgCd;
	}

	public void setUserOrgCd(String userOrgCd) {
		this.userOrgCd = userOrgCd;
	}

	public String getUserOrgNm() {
		return userOrgNm;
	}

	public void setUserOrgNm(String userOrgNm) {
		this.userOrgNm = userOrgNm;
	}
	
	public String getSubDn2() {
		return subDn2;
	}

	public void setSubDn2(String subDn2) {
		this.subDn2 = subDn2;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public List<Integer> getUserMemLvls() {
		return userMemLvls;
	}

	public void setUserMemLvls(List<Integer> userMemLvls) {
		this.userMemLvls = userMemLvls;
	}
}

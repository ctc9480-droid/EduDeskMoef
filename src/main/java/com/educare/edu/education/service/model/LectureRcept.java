package com.educare.edu.education.service.model;

import java.util.Date;

import com.educare.component.VarComponent;
import com.educare.util.CryptoSeedUtil;
import com.educare.util.EhCacheUtil;

/**
 * @Class Name : LectureRcept.java
 * @author SI개발팀 박용주
 * @since 2020. 7. 9.
 * @version 1.0
 * @see
 * @Description 수업접수 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 9.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class LectureRcept {
		
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
	
	/** 기타 */
	private String etc;
	
	private int state;
	
	/** 결제 일련번호 */
	private String payNo;
	
	private int rceptSeq;
	
	private int dormiCapaCnt = -1;
	private String dormiAccessYn;
	private int useTrans = -1;
	
	private String dormiCapaYn;
	private String useTransYn;
	private String userOrgNm;
	private String userGradeNm;
	private String tel;
	private String eduNm;
	
	
	private int rfndReqFee = -1;
	private String rfndBankNm;
	private String rfndAccountNm;
	private String rfndAccountNo;
	private int rfndState;
	private int feeTp;				//-,카드,전자세금계산서,현장결제
	private String employYn;
	private int mfType;
	//
	private String pgPayNo;
	private Date payDt;
	private int payType;
	private int payState;
	
	private String vipYn;
	
	private String regId;
	private String updId;
	private Date regDe;
	private Date updDe;
	
	private String fileOrg;
	private String fileRe;
	private String loginId;
	
	private String etcIemJson;
	private int fee;//단체신청시 정액과 일반금액 정보를 저장하기위해
	private String birth;
	private int payFee;//pg결제금액
	private int limsFee;
	private int limsState;
	private String limsSrNo;
	
	/**
	 * 
	 */
	public LectureRcept() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param eduSeq
	 * @param userId
	 * @param userNm
	 * @param mobile
	 * @param email
	 * @param belong
	 * @param position
	 * @param payType
	 * @param payYn
	 * @param etc
	 * @param state
	 * @param paySeq
	 */
	public LectureRcept(
			int rceptSeq,Integer eduSeq, String userId, String userNm, String mobile, String email
			, String belong, String position,String etc
			, int state,String fileOrg,String fileRe,int feeTp, String regId,Date regDe,String updId,Date updDe) {
		super();
		this.rceptSeq = rceptSeq;
		this.eduSeq = eduSeq;
		this.userId = userId;
		this.userNm = userNm;
		this.mobile = mobile;
		this.email = email;
		this.belong = belong;
		this.position = position;
		this.etc = etc;
		this.state = state;
		this.fileOrg=fileOrg;
		this.fileRe=fileRe;
		this.feeTp=feeTp;
		this.setRegId(regId);
		this.setRegDe(regDe);
		this.setUpdId(updId);
		this.updDe=updDe;
	}
	
	public int getUserSmsYn(){
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getUserSmsYn();
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	public String getAddMfTypeNm(){
		String[] a = {"-","남","여"};
		//return a[getMfType()];
		return a[this.mfType];
	}
	
	public int getPayState() {
		return payState;
	}

	public void setPayState(int payState) {
		this.payState = payState;
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
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the paySeq
	 */
	public String getPayNo() {
		return payNo;
	}

	/**
	 * @param paySeq the paySeq to set
	 */
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	/**
	 * @return the etc
	 */
	public String getEtc() {
		return etc;
	}

	/**
	 * @param etc the etc to set
	 */
	public void setEtc(String etc) {
		this.etc = etc;
	}

	public String getFileOrg() {
		return fileOrg;
	}

	public void setFileOrg(String fileOrg) {
		this.fileOrg = fileOrg;
	}

	public String getFileRe() {
		return fileRe;
	}

	public void setFileRe(String fileRe) {
		this.fileRe = fileRe;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Date getPayDt() {
		return payDt;
	}

	public void setPayDt(Date payDt) {
		this.payDt = payDt;
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

	public String getEtcIemJson() {
		return etcIemJson;
	}

	public void setEtcIemJson(String etcIemJson) {
		this.etcIemJson = etcIemJson;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}
	
	public String getAddStateNm(){
		try {
			return VarComponent.RCEPT_STATE[this.state];
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}

	public String getVipYn() {
		return vipYn;
	}

	public void setVipYn(String vipYn) {
		this.vipYn = vipYn;
	}

	public String getBirth() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getBirth();
			//return CryptoSeedUtil.decrypt(this.mobile);
		} catch (NullPointerException|IllegalArgumentException e) {
			return this.birth;
		}
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public Date getUpdDe() {
		return updDe;
	}

	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}

	public String getPgPayNo() {
		return pgPayNo;
	}

	public void setPgPayNo(String pgPayNo) {
		this.pgPayNo = pgPayNo;
	}

	public int getRceptSeq() {
		return rceptSeq;
	}

	public void setRceptSeq(int rceptSeq) {
		this.rceptSeq = rceptSeq;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public Date getRegDe() {
		return regDe;
	}

	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}

	public int getRfndReqFee() {
		return rfndReqFee;
	}

	public void setRfndReqFee(int rfndReqFee) {
		this.rfndReqFee = rfndReqFee;
	}

	public String getRfndBankNm() {
		return rfndBankNm;
	}

	public void setRfndBankNm(String rfndBankNm) {
		this.rfndBankNm = rfndBankNm;
	}

	public String getRfndAccountNm() {
		return rfndAccountNm;
	}

	public void setRfndAccountNm(String rfndAccountNm) {
		this.rfndAccountNm = rfndAccountNm;
	}

	public String getRfndAccountNo() {
		return rfndAccountNo;
	}

	public void setRfndAccountNo(String rfndAccountNo) {
		this.rfndAccountNo = rfndAccountNo;
	}

	public int getRfndState() {
		return rfndState;
	}

	public void setRfndState(int rfndState) {
		this.rfndState = rfndState;
	}

	public int getFeeTp() {
		return feeTp;
	}

	public void setFeeTp(int feeTp) {
		this.feeTp = feeTp;
	}

	public String getEmployYn() {
		return employYn;
	}

	public void setEmployYn(String employYn) {
		this.employYn = employYn;
	}

	public int getLimsFee() {
		return limsFee;
	}

	public void setLimsFee(int limsFee) {
		this.limsFee = limsFee;
	}

	public int getLimsState() {
		return limsState;
	}

	public void setLimsState(int limsState) {
		this.limsState = limsState;
	}

	public String getLimsSrNo() {
		return limsSrNo;
	}

	public void setLimsSrNo(String limsSrNo) {
		this.limsSrNo = limsSrNo;
	}
	
	public String getAddLimsStateNm(){
		try {
			if(this.fee == 0){
				return "";
			}
			if(this.limsState == 1 && this.payState != 2  ){
				return "결제대기";
			}else if(this.limsState == 1 && this.payState == 2){
				return "결제완료 (확인 중)";
			}else if(this.limsState == 2){
				return "결제완료";
			}else if(this.limsState == 3){
				return "환불";
			}else if(this.limsState == 4){
				return "접수대기";
			}
			return "";
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
	public String getAddFeeTpNm(){
		try {
			return VarComponent.FEE_TP[this.feeTp];
		} catch (NullPointerException e) {
			return "-";
		}
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getPayFee() {
		return payFee;
	}

	public void setPayFee(int payFee) {
		this.payFee = payFee;
	}

	public int getMfType() {
		return mfType;
	}

	public void setMfType(int mfType) {
		this.mfType = mfType;
	}

	public int getDormiCapaCnt() {
		return dormiCapaCnt;
	}

	public void setDormiCapaCnt(int dormiCapaCnt) {
		this.dormiCapaCnt = dormiCapaCnt;
	}

	public String getDormiAccessYn() {
		return dormiAccessYn;
	}

	public void setDormiAccessYn(String dormiAccessYn) {
		this.dormiAccessYn = dormiAccessYn;
	}

	public int getUseTrans() {
		return useTrans;
	}

	public void setUseTrans(int useTrans) {
		this.useTrans = useTrans;
	}

	public String getUseTransYn() {
		return useTransYn;
	}

	public void setUseTransYn(String useTransYn) {
		this.useTransYn = useTransYn;
	}

	public String getUserOrgNm() {
		return userOrgNm;
	}

	public void setUserOrgNm(String userOrgNm) {
		this.userOrgNm = userOrgNm;
	}

	public String getUserGradeNm() {
		return userGradeNm;
	}

	public void setUserGradeNm(String userGradeNm) {
		this.userGradeNm = userGradeNm;
	}

	public String getTel() {
		try {
			return CryptoSeedUtil.decrypt(this.tel);
		} catch (NullPointerException e) {
			return this.tel;
		}
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDormiCapaYn() {
		return dormiCapaYn;
	}

	public void setDormiCapaYn(String dormiCapaYn) {
		this.dormiCapaYn = dormiCapaYn;
	}

	public String getEduNm() {
		return eduNm;
	}

	public void setEduNm(String eduNm) {
		this.eduNm = eduNm;
	}
}

package com.educare.edu.education.service.model;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.educare.component.VarComponent;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.CryptoSeedUtil;
import com.educare.util.DateUtil;
import com.educare.util.EhCacheUtil;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @Class Name : Lecture.java
 * @author SI개발팀 박용주
 * @since 2020. 7. 9.
 * @version 1.0
 * @see
 * @Description 수업 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 9.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class Lecture {
	
	//add
	private String ctg1Nm;
	private String ctg2Nm;
	private String ctg3Nm;
	
	
	private int eduSeq;		//수업번호
	private String eduNm;	//수업명
	private int ctg1Seq;	//카테고리1
	private int ctg2Seq;
	private int ctg3Seq;
	
	/** 노출여부 Y/N */
	private String openYn;
	
	/** 강사 아이디 */
	private String instrctrId;
	
	/** 강사명 */
	private String instrctrNm;
	
	/** 대표이미지 원본명 */
	private String imgOrg;
	
	/** 대표이미지 저장명 */
	private String imgRename;
	
	/** 대표이미지 사용여부 Y/N */
	private String imgUseYn;
	
	/** 교육기간 시작일 */
	private String eduPeriodBegin;
	
	/** 교육기간 종료일 */
	private String eduPeriodEnd;
	
	/** 접수 시작일 yyyyMMddHHmm */
	private String rceptPeriodBegin;
	
	/** 접수 종료일 yyyyMMddHHmm */
	private String rceptPeriodEnd;
	
	/** 접수기간 사용여부 */
	private String rceptPeriodYn;
	
	/** 모집인원 */
	private int personnel;
	
	/** 최소인원 */
	private int minPersonnel;
	
	/** 대기인원 */
	private int extPersonnel;
	
	/** 마감옵션 (01:선착순승인, 02:관리자승인) */
	private int closeTp;
	
	/** 교육금액 */
	private int fee;
	
	/** 단체 교육금액 */
	private int groupFee;
	
	/** 결제 완료 기한 */
	private int feeLimit;
	
	/** 단체결제 안내문구 */
	private String groupComment;
	
	/** 상세내용 */
	private String cn;
	
	/** 우편번호 */
	private String postNo;
	
	/** 주소 */
	private String addr;
	
	/** 주소상세 */
	private String addrDetail;
	
	/** 주소 참고항목 */
	private String addrEtc;
	
	/** 장소 메모 */
	private String addrMemo;
	
	/** 신청가능 여부 (Y : 가능, N : 마감) */
	private String rceptYn;
	
	/** 교육 종료여부 (Y : 종료, N : 미종료) */
	private String closeYn;
	
	/** 등록일 */
	private Date regDe;
	
	/** 등록자 아이디 */
	private String regId;
	
	/** 수정일 */
	private Date updDe;
	
	/** 수정자 아이디 */
	private String updId;
	
	/** 신청자 */
	private int rceptCnt;
	
	/** 대기자 */
	private int waitCnt;

	/** 온라인강의 여부 */
	private String onlineYn;
	/** 온라인강의 주소 */
	private String onlineUrl;
	/** 문의전화 */
	private String tel;
	
	private String eduYear;//연도
	private int eduTerm;//학기
	private int targetTp;
	private List<String> targetArr;
	private String targets;
	private int eduTp;
	private String rceptCntViewYn;
	private int returnTp;			//환급유형
	private String compStnd01;
	private String compStnd02;
	private String compStnd03;
	private String compStnd04;
	private String compStnd05;
	private String depositLimitDt;
	private int dormiFee;
	private int mealFee;
	private String dormiAccount;
	private String mealAccount;
	private String userOrgNm;
	
	private String orgCd;
	private String checkYn;
	private String wayCome;
	private int lctreType;
	private int smsAuto;
	private String orgNm;
	private int checkRcept;
	private int checkPass;//수료여부
	private int attend2Cnt;//출석수
	private int timeCnt;//수업시간수
	private int passAttPoint;	//종합출결점수
	private int passAsgPoint;	//종합과제점수
	private int passTestPoint;	//종합시험점수
	private int passPoint; //수료 점수
	private String autoPassYn; //자동수료여부
	private String eduEtc;
	private String cancelLimitDt;
	private String userNm;
	private String userId;
	private String compUserId;
	private String email;
	private String mobile;
	private String loginId;
	private String birth;
	private int mfType;
	private String membershipType;
	private int amount;
	private Date recptDe;
	private int rceptState;
	private int payState;
	private int payType;
	private Date payDe;
	private String payNo;
	private String pgPayNo;
	
	private String etcData01;//여분데이타필드
	private String compJson;
	
	/* 수료증번호 */
	private int passIdx;
	/* 수료증 여부 */
	private String passCertYn;
	/* 수료증 이름 */
	private String passCertNm;
	/* 선행수업 번호 */
	private int preEduSeq;
	/* 필수 선행 수업 여부 */
	private String preEduYn;
	/* 필수 선행수업 이름 */
	private String preEduNm;
	/* 피드백 번호 */
	private int fbIdx;
	/* 피드백 여부 */
	private String fbYn;
	/* 피드백 이름  */
	private String fbTitle;
	
	//기타항목json
	private String etcIemJson;
	private String etcIemYn;
	
	private String inqNm;
	private String etcAgreeYn;
	private String essntlFileYn;
	private String scrtyYn;
	private String scrtyPw;
	private String detailCtgryNm2;
	private String detailCtgryNm3;//무조건 카테고리3
	private String userEnNm;
	
	//교육일 관련
	private String eduDt;
	private String startTm;
	private String endTm;
	private String roomNm;
	
	private int roomSeq;
	
	//신청관련
	private int rceptSeq;
	private int rfndState;
	private int feeTp;
	
	//lims연동
	private int limsFee;
	private int limsState;	//1:결제대기,2:결제완료,3:환불,4:환불대기
	private String limsSrNo;
	/**
	 * 
	 */
	public Lecture() {
		super();
	}

	/**
	 */
	public Lecture(int eduSeq, String eduNm,  String openYn, String instrctrId, String instrctrNm, String imgOrg, String imgRename,
			String imgUseYn, String eduPeriodBegin, String eduPeriodEnd, String rceptPeriodBegin, String rceptPeriodEnd,
			String rceptPeriodYn, int personnel, int minPersonnel, int extPersonnel, int closeTp,
			int fee, int groupFee, int feeLimit, String groupComment, String cn, String postNo, String addr,
			String addrDetail, String addrEtc, String addrMemo, String rceptYn, String closeYn,
			Date regDe, String regId, Date updDe, String updId, int rceptCnt,
			int waitCnt) {
		super();
		this.eduSeq = eduSeq;
		this.eduNm = eduNm;
		this.openYn = openYn;
		this.instrctrId = instrctrId;
		this.instrctrNm = instrctrNm;
		this.imgOrg = imgOrg;
		this.imgRename = imgRename;
		this.imgUseYn = imgUseYn;
		this.eduPeriodBegin = eduPeriodBegin;
		this.eduPeriodEnd = eduPeriodEnd;
		this.rceptPeriodBegin = rceptPeriodBegin;
		this.rceptPeriodEnd = rceptPeriodEnd;
		this.rceptPeriodYn = rceptPeriodYn;
		this.personnel = personnel;
		this.minPersonnel = minPersonnel;
		this.extPersonnel = extPersonnel;
		this.closeTp = closeTp;
		this.fee = fee;
		this.groupFee = groupFee;
		this.feeLimit = feeLimit;
		this.groupComment = groupComment;
		this.cn = cn;
		this.postNo = postNo;
		this.addr = addr;
		this.addrDetail = addrDetail;
		this.addrEtc = addrEtc;
		this.addrMemo = addrMemo;
		this.rceptYn = rceptYn;
		this.closeYn = closeYn;
		this.regDe = regDe;
		this.regId = regId;
		this.updDe = updDe;
		this.updId = updId;
		this.rceptCnt = rceptCnt;
		this.waitCnt = waitCnt;
	}

	public int getMfType() {
		return mfType;
	}

	public void setMfType(int mfType) {
		this.mfType = mfType;
	}

	public String getAddMfTypeNm(){
		String[] a = {"-","남","여"};
		//return a[getMfType()];
		return a[this.mfType];
	}
	
	public String getEmail() {
		try {
			if(this.userId == null){
				return this.email;
			}
			return EhCacheUtil.getUserPrvt(this.userId).getEmail();
		} catch (NullPointerException e) {
			return this.mobile;
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		try {
			if(this.userId == null){
				return this.mobile;
			}
			return EhCacheUtil.getUserPrvt(this.userId).getMobile();
			//return CryptoSeedUtil.decrypt(this.mobile);
		} catch (NullPointerException e) {
			return this.mobile;
		}
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getPayState() {
		return payState;
	}

	public void setPayState(int payState) {
		this.payState = payState;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
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
	 * @return the eduNm
	 */
	public String getEduNm() {
		return eduNm;
	}

	/**
	 * @param eduNm the eduNm to set
	 */
	public void setEduNm(String eduNm) {
		this.eduNm = eduNm;
	}


	/**
	 * @return the openYn
	 */
	public String getOpenYn() {
		return openYn;
	}

	/**
	 * @param openYn the openYn to set
	 */
	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}

	/**
	 * @return the instrctrId
	 */
	public String getInstrctrId() {
		return instrctrId;
	}

	/**
	 * @param instrctrId the instrctrId to set
	 */
	public void setInstrctrId(String instrctrId) {
		this.instrctrId = instrctrId;
	}

	/**
	 * @return the instrctrNm
	 */
	public String getInstrctrNm() {
		return instrctrNm;
	}

	/**
	 * @param instrctrNm the instrctrNm to set
	 */
	public void setInstrctrNm(String instrctrNm) {
		this.instrctrNm = instrctrNm;
	}

	/**
	 * @return the imgOrg
	 */
	public String getImgOrg() {
		return imgOrg;
	}

	/**
	 * @param imgOrg the imgOrg to set
	 */
	public void setImgOrg(String imgOrg) {
		this.imgOrg = imgOrg;
	}

	/**
	 * @return the imgRename
	 */
	public String getImgRename() {
		return imgRename;
	}

	/**
	 * @param imgRename the imgRename to set
	 */
	public void setImgRename(String imgRename) {
		this.imgRename = imgRename;
	}

	/**
	 * @return the imgUseYn
	 */
	public String getImgUseYn() {
		return imgUseYn;
	}

	/**
	 * @param imgUseYn the imgUseYn to set
	 */
	public void setImgUseYn(String imgUseYn) {
		this.imgUseYn = imgUseYn;
	}

	/**
	 * @return the eduPeriodBegin
	 */
	public String getEduPeriodBegin() {
		return eduPeriodBegin;
	}

	/**
	 * @param eduPeriodBegin the eduPeriodBegin to set
	 */
	public void setEduPeriodBegin(String eduPeriodBegin) {
		this.eduPeriodBegin = eduPeriodBegin;
	}

	/**
	 * @return the eduPeriodEnd
	 */
	public String getEduPeriodEnd() {
		return eduPeriodEnd;
	}

	/**
	 * @param eduPeriodEnd the eduPeriodEnd to set
	 */
	public void setEduPeriodEnd(String eduPeriodEnd) {
		this.eduPeriodEnd = eduPeriodEnd;
	}

	/**
	 * @return the rceptPeriodBegin
	 */
	public String getRceptPeriodBegin() {
		return rceptPeriodBegin;
	}

	/**
	 * @param rceptPeriodBegin the rceptPeriodBegin to set
	 */
	public void setRceptPeriodBegin(String rceptPeriodBegin) {
		this.rceptPeriodBegin = rceptPeriodBegin;
	}

	/**
	 * @return the rceptPeriodEnd
	 */
	public String getRceptPeriodEnd() {
		return rceptPeriodEnd;
	}

	/**
	 * @param rceptPeriodEnd the rceptPeriodEnd to set
	 */
	public void setRceptPeriodEnd(String rceptPeriodEnd) {
		this.rceptPeriodEnd = rceptPeriodEnd;
	}

	/**
	 * @return the rceptPeriodYn
	 */
	public String getRceptPeriodYn() {
		return rceptPeriodYn;
	}

	/**
	 * @param rceptPeriodYn the rceptPeriodYn to set
	 */
	public void setRceptPeriodYn(String rceptPeriodYn) {
		this.rceptPeriodYn = rceptPeriodYn;
	}

	/**
	 * @return the personnel
	 */
	public int getPersonnel() {
		return personnel;
	}

	/**
	 * @param personnel the personnel to set
	 */
	public void setPersonnel(int personnel) {
		this.personnel = personnel;
	}

	/**
	 * @return the minPersonnel
	 */
	public int getMinPersonnel() {
		return minPersonnel;
	}

	/**
	 * @param minPersonnel the minPersonnel to set
	 */
	public void setMinPersonnel(int minPersonnel) {
		this.minPersonnel = minPersonnel;
	}

	/**
	 * @return the extPersonnel
	 */
	public int getExtPersonnel() {
		return extPersonnel;
	}

	/**
	 * @param extPersonnel the extPersonnel to set
	 */
	public void setExtPersonnel(int extPersonnel) {
		this.extPersonnel = extPersonnel;
	}

	/**
	 * @return the closeType
	 */
	public int getCloseTp() {
		return closeTp;
	}

	/**
	 * @param closeType the closeType to set
	 */
	public void setCloseTp(int closeTp) {
		this.closeTp = closeTp;
	}

	/**
	 * @return the fee
	 */
	public int getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(int fee) {
		this.fee = fee;
	}

	/**
	 * @return the groupFee
	 */
	public int getGroupFee() {
		return groupFee;
	}

	/**
	 * @param groupFee the groupFee to set
	 */
	public void setGroupFee(int groupFee) {
		this.groupFee = groupFee;
	}

	/**
	 * @return the feeLimit
	 */
	public int getFeeLimit() {
		return feeLimit;
	}

	/**
	 * @param feeLimit the feeLimit to set
	 */
	public void setFeeLimit(int feeLimit) {
		this.feeLimit = feeLimit;
	}

	/**
	 * @return the groupComment
	 */
	public String getGroupComment() {
		return groupComment;
	}

	/**
	 * @param groupComment the groupComment to set
	 */
	public void setGroupComment(String groupComment) {
		this.groupComment = groupComment;
	}

	/**
	 * @return the cn
	 */
	public String getCn() {
		return cn;
	}

	/**
	 * @param cn the cn to set
	 */
	public void setCn(String cn) {
		this.cn = cn;
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
		this.postNo = postNo;
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
		this.addr = addr;
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
		this.addrDetail = addrDetail;
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
		this.addrEtc = addrEtc;
	}

	/**
	 * @return the addrMemo
	 */
	public String getAddrMemo() {
		return addrMemo;
	}

	/**
	 * @param addrMemo the addrMemo to set
	 */
	public void setAddrMemo(String addrMemo) {
		this.addrMemo = addrMemo;
	}

	/**
	 * @return the rceptYn
	 */
	public String getRceptYn() {
		return rceptYn;
	}

	/**
	 * @param rceptYn the rceptYn to set
	 */
	public void setRceptYn(String rceptYn) {
		this.rceptYn = rceptYn;
	}

	/**
	 * @return the closeYn
	 */
	public String getCloseYn() {
		return closeYn;
	}

	/**
	 * @param closeYn the closeYn to set
	 */
	public void setCloseYn(String closeYn) {
		this.closeYn = closeYn;
	}

	/**
	 * @return the rgsDe
	 */
	public Date getRegDe() {
		return regDe;
	}

	/**
	 * @param rgsDe the rgsDe to set
	 */
	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}

	/**
	 * @return the rgsUserId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param rgsUserId the rgsUserId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the updDe
	 */
	public Date getUpdDe() {
		return updDe;
	}

	/**
	 * @param updDe the updDe to set
	 */
	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}

	/**
	 * @return the updUserId
	 */
	public String getUpdId() {
		return updId;
	}

	/**
	 * @param updUserId the updUserId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

	/**
	 * @return the rceptCnt
	 */
	public int getRceptCnt() {
		return rceptCnt;
	}

	/**
	 * @param rceptCnt the rceptCnt to set
	 */
	public void setRceptCnt(int rceptCnt) {
		this.rceptCnt = rceptCnt;
	}

	/**
	 * @return the waitCnt
	 */
	public int getWaitCnt() {
		return waitCnt;
	}

	/**
	 * @param waitCnt the waitCnt to set
	 */
	public void setWaitCnt(int waitCnt) {
		this.waitCnt = waitCnt;
	}

	public String getOnlineYn() {
		return onlineYn;
	}

	public void setOnlineYn(String onlineYn) {
		this.onlineYn = onlineYn;
	}

	public String getOnlineUrl() {
		return onlineUrl;
	}

	public void setOnlineUrl(String onlineUrl) {
		this.onlineUrl = onlineUrl;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public int getCheckRcept() {
		return checkRcept;
	}

	public void setCheckRcept(int checkRcept) {
		this.checkRcept = checkRcept;
	}

	public String getCheckYn() {
		return checkYn;
	}

	public void setCheckYn(String checkYn) {
		this.checkYn = checkYn;
	}

	public String getWayCome() {
		return wayCome;
	}

	public void setWayCome(String wayCome) {
		this.wayCome = wayCome;
	}

	public int getAttend2Cnt() {
		return attend2Cnt;
	}

	public void setAttend2Cnt(int attend2Cnt) {
		this.attend2Cnt = attend2Cnt;
	}

	public int getFbIdx() {
		return fbIdx;
	}

	public void setFbIdx(int fbIdx) {
		this.fbIdx = fbIdx;
	}

	public int getTimeCnt() {
		return timeCnt;
	}

	public void setTimeCnt(int timeCnt) {
		this.timeCnt = timeCnt;
	}
	
	public String getAddStatusNm(){
		String result = "-";
		switch (this.checkRcept) {
		case 0: result=""; break;
		case 1: result="접수대기"; break;
		case 2: result="접수중"; break;
		case 3: result="접수마감"; break;
		case 4: result="교육종료"; break;
		default:break;
		}
		return result;
	}
	public String getAddStatusBox(){
		String result = "-";
		switch (this.checkRcept) {
		case 0: result=""; break;
		case 1: result="<span class=\"icon_tag wait\">접수대기</span>"; break;
		case 2: result="<span class=\"icon_tag ing\">접수중</span>"; break;
		case 3: result="<span class=\"icon_tag end\">접수마감</span>"; break;
		case 4: result="<span class=\"icon_tag eduend\">교육종료</span>"; break;
		default:break;
		}
		return result;
	}
	public String getAddPassBox(){
		String result = "-";
		switch (this.checkPass) {
		case 0: result=""; break;
		case 1: result="<span class=\"icon_tag comp\">수료</span>"; break;
		case 2: result="<span class=\"icon_tag end\">미수료</span>"; break;
		case 3: result="<span class=\"icon_tag end\">진행중</span>"; break;
		default:break;
		}
		return result;
	}
	public String getAddPassNm(){
		String result = "-";
		switch (this.checkPass) {
		case 0: result=""; break;
		case 1: result="수료"; break;
		case 2: result="미수료"; break;
		case 3: result="진행중"; break;
		default:break;
		}
		return result;
	}

	public int getCheckPass() {
		return checkPass;
	}

	public void setCheckPass(int checkPass) {
		this.checkPass = checkPass;
	}

	public int getPassIdx() {
		return passIdx;
	}

	public void setPassIdx(int passIdx) {
		this.passIdx = passIdx;
	}

	public int getLctreType() {
		return lctreType;
	}

	public void setLctreType(int lctreType) {
		this.lctreType = lctreType;
	}
	
	public String getLctreTypeNm(){
		String lctreTypeNm = VarComponent.LECTURE_TYPE[this.lctreType];
		return lctreTypeNm;
	}
	public String getAddReturnTpNm(){
		String returnTpNm = VarComponent.RETURN_TP[this.returnTp];
		return returnTpNm;
	}
	
	public String getAddFeeCurrency(){
		try {
			DecimalFormat fm = new DecimalFormat("###,###");
			String result = fm.format(this.fee);
			return result;
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
	public String getAddGroupFeeCurrency(){
		try {
			DecimalFormat fm = new DecimalFormat("###,###");
			String result = fm.format(this.groupFee);
			return result;
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}

	public int getSmsAuto() {
		return smsAuto;
	}

	public void setSmsAuto(int smsAuto) {
		this.smsAuto = smsAuto;
	}


	public int getPassAttPoint() {
		return passAttPoint;
	}

	public void setPassAttPoint(int passAttPoint) {
		this.passAttPoint = passAttPoint;
	}

	public int getPassAsgPoint() {
		return passAsgPoint;
	}

	public void setPassAsgPoint(int passAsgPoint) {
		this.passAsgPoint = passAsgPoint;
	}

	public int getPassTestPoint() {
		return passTestPoint;
	}

	public void setPassTestPoint(int passTestPoint) {
		this.passTestPoint = passTestPoint;
	}
	public int getPassPoint() {
		return passPoint;
	}

	public void setPassPoint(int passPoint) {
		this.passPoint = passPoint;
	}

	public String getAutoPassYn() {
		return autoPassYn;
	}

	public void setAutoPassYn(String autoPassYn) {
		this.autoPassYn = autoPassYn;
	}

	public String getPassCertYn() {
		return passCertYn;
	}

	public void setPassCertYn(String passCertYn) {
		this.passCertYn = passCertYn;
	}

	public String getPreEduYn() {
		return preEduYn;
	}

	public void setPreEduYn(String preEduYn) {
		this.preEduYn = preEduYn;
	}

	public String getFbYn() {
		return fbYn;
	}

	public void setFbYn(String fbYn) {
		this.fbYn = fbYn;
	}

	public String getEduEtc() {
		return eduEtc;
	}

	public void setEduEtc(String eduEtc) {
		this.eduEtc = eduEtc;
	}

	public int getPreEduSeq() {
		return preEduSeq;
	}

	public void setPreEduSeq(int preEduSeq) {
		this.preEduSeq = preEduSeq;
	}

	public String getPreEduNm() {
		return preEduNm;
	}

	public void setPreEduNm(String preEduNm) {
		this.preEduNm = preEduNm;
	}

	public String getFbTitle() {
		return fbTitle;
	}

	public void setFbTitle(String fbTitle) {
		this.fbTitle = fbTitle;
	}

	public String getPassCertNm() {
		return passCertNm;
	}

	public void setPassCertNm(String passCertNm) {
		this.passCertNm = passCertNm;
	}

	public String getEtcIemJson() {
		return etcIemJson;
	}

	public void setEtcIemJson(String etcIemJson) {
		this.etcIemJson = etcIemJson;
	}

	public String getEtcIemYn() {
		return etcIemYn;
	}

	public void setEtcIemYn(String etcIemYn) {
		this.etcIemYn = etcIemYn;
	}

	public String getInqNm() {
		return inqNm;
	}

	public void setInqNm(String inqNm) {
		this.inqNm = inqNm;
	}

	public String getEtcAgreeYn() {
		return etcAgreeYn;
	}

	public void setEtcAgreeYn(String etcAgreeYn) {
		this.etcAgreeYn = etcAgreeYn;
	}

	public String getEssntlFileYn() {
		return essntlFileYn;
	}

	public void setEssntlFileYn(String essntlFileYn) {
		this.essntlFileYn = essntlFileYn;
	}

	public String getScrtyYn() {
		return scrtyYn;
	}

	public void setScrtyYn(String scrtyYn) {
		this.scrtyYn = scrtyYn;
	}

	public String getScrtyPw() {
		return scrtyPw;
	}

	public void setScrtyPw(String scrtyPw) {
		this.scrtyPw = scrtyPw;
	}

	public int getRoomSeq() {
		return roomSeq;
	}

	public void setRoomSeq(int roomSeq) {
		this.roomSeq = roomSeq;
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

	public String getRoomNm() {
		return roomNm;
	}

	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}


	public String getCancelLimitDt() {
		return cancelLimitDt;
	}

	public void setCancelLimitDt(String cancelLimitDt) {
		this.cancelLimitDt = cancelLimitDt;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getRecptDe() {
		return recptDe;
	}

	public void setRecptDe(Date recptDe) {
		this.recptDe = recptDe;
	}

	public int getRceptState() {
		return rceptState;
	}

	public void setRceptState(int rceptState) {
		this.rceptState = rceptState;
	}
	
	public String getAddRceptStateNm(){
		try {
			return VarComponent.RCEPT_STATE[this.rceptState];
		} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
			return "ERROR";
		}
	}
	public String getAddLimsStateNm(){
		try {
			if(this.fee == 0){
				return "";
			}
			if(this.limsState == 1 && this.payState != 2  ){
				return "결제대기";
			}else if(this.limsState == 1 && this.payState == 2){
				if (SessionUserInfoHelper.isAdmin()) {
					return "결제완료 (확인 중)";
				}else{
					return "결제완료";
				}
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
	public String getAddPayStateNm(){
		try {
			return VarComponent.PAY_STATE[this.payState];
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getPgPayNo() {
		return pgPayNo;
	}

	public void setPgPayNo(String pgPayNo) {
		this.pgPayNo = pgPayNo;
	}

	public String getEduYear() {
		return eduYear;
	}

	public void setEduYear(String eduYear) {
		this.eduYear = eduYear;
	}

	public int getEduTerm() {
		return eduTerm;
	}

	public void setEduTerm(int eduTerm) {
		this.eduTerm = eduTerm;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEtcData01() {
		return etcData01;
	}

	public void setEtcData01(String etcData01) {
		this.etcData01 = etcData01;
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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getBirth() {
		try {
			
			return EhCacheUtil.getUserPrvt(this.userId).getBirth();
			//return CryptoSeedUtil.decrypt(this.mobile);
		} catch (NullPointerException e) {
			return this.birth;
		}
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	public String getAddPayTypeNm(){
		try {
			return VarComponent.PAY_TYPE[this.payType];
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}

	public String getCompUserId() {
		return compUserId;
	}

	public void setCompUserId(String compUserId) {
		this.compUserId = compUserId;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}
	
	public int getAddSort(){
		try {
			if(this.checkRcept == 2){
				return 1;
			}else if(this.checkRcept == 3){
				return 2;
			}else if(this.checkRcept == 1){
				return 3;
			}else if(this.checkRcept == 4){
				return 4;
			}else{
				return 99;
			}
		} catch (NullPointerException e) {
			return 99;
		}
	}

	public int getRceptSeq() {
		return rceptSeq;
	}

	public void setRceptSeq(int rceptSeq) {
		this.rceptSeq = rceptSeq;
	}

	public String getDetailCtgryNm2() {
		return detailCtgryNm2;
	}

	public void setDetailCtgryNm2(String detailCtgryNm2) {
		this.detailCtgryNm2 = detailCtgryNm2;
	}

	public String getDetailCtgryNm3() {
		return detailCtgryNm3;
	}

	public void setDetailCtgryNm3(String detailCtgryNm3) {
		this.detailCtgryNm3 = detailCtgryNm3;
	}

	public int getCtg1Seq() {
		return ctg1Seq;
	}

	public void setCtg1Seq(int ctg1Seq) {
		this.ctg1Seq = ctg1Seq;
	}

	public int getCtg2Seq() {
		return ctg2Seq;
	}

	public void setCtg2Seq(int ctg2Seq) {
		this.ctg2Seq = ctg2Seq;
	}

	public int getCtg3Seq() {
		return ctg3Seq;
	}

	public void setCtg3Seq(int ctg3Seq) {
		this.ctg3Seq = ctg3Seq;
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

	public int getTargetTp() {
		return targetTp;
	}

	public void setTargetTp(int targetTp) {
		this.targetTp = targetTp;
	}

	public int getEduTp() {
		return eduTp;
	}

	public void setEduTp(int eduTp) {
		this.eduTp = eduTp;
	}
	
	public String getAddFeeTpNm(){
		try {
			return VarComponent.FEE_TP[this.feeTp];
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public String getAddEduTpNm(){
		try {
			return VarComponent.EDU_TP[this.eduTp];
		} catch (NullPointerException e) {
			return "-";
		}
	}

	public int getRfndState() {
		return rfndState;
	}

	public void setRfndState(int rfndState) {
		this.rfndState = rfndState;
	}

	public String getRceptCntViewYn() {
		return rceptCntViewYn;
	}

	public void setRceptCntViewYn(String rceptCntViewYn) {
		this.rceptCntViewYn = rceptCntViewYn;
	}

	public int getReturnTp() {
		return returnTp;
	}

	public void setReturnTp(int returnTp) {
		this.returnTp = returnTp;
	}

	public int getFeeTp() {
		return feeTp;
	}

	public void setFeeTp(int feeTp) {
		this.feeTp = feeTp;
	}

	public int getLimsFee() {
		//test 
		//return this.fee;
		
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

	public Date getPayDe() {
		return payDe;
	}

	public void setPayDt(Date payDe) {
		this.payDe = payDe;
	}

	public String getLimsSrNo() {
		return limsSrNo;
	}

	public void setLimsSrNo(String limsSrNo) {
		this.limsSrNo = limsSrNo;
	}

	public String getCompJson() {
		return compJson;
	}

	public void setCompJson(String compJson) {
		this.compJson = compJson;
	}

	public String getUserEnNm() {
		return userEnNm;
	}

	public void setUserEnNm(String userEnNm) {
		this.userEnNm = userEnNm;
	}

	public List<String> getTargetArr() {
		return targetArr;
	}

	public void setTargetArr(List<String> targetArr) {
		this.targetArr = targetArr;
	}
	public String getAddTargetsStr(){
		try {
			String result = "";
			if(ObjectUtils.isEmpty(this.targets)){
			//	return "제한 없음";
				return "아무나";
			}
			String[] targetArr = this.targets.split(",");
			for(int i=0; i<targetArr.length; i++){
				if(i>0){
					result += ",";
				}
				result +=VarComponent.getEduTargetNm(targetArr[i]);
			}
			return result;
		} catch (Exception e) {
			return "";
		}
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public String getCompStnd01() {
		return compStnd01;
	}

	public void setCompStnd01(String compStnd01) {
		this.compStnd01 = compStnd01;
	}

	public String getCompStnd02() {
		return compStnd02;
	}

	public void setCompStnd02(String compStnd02) {
		this.compStnd02 = compStnd02;
	}

	public String getCompStnd03() {
		return compStnd03;
	}

	public void setCompStnd03(String compStnd03) {
		this.compStnd03 = compStnd03;
	}

	public String getCompStnd04() {
		return compStnd04;
	}

	public void setCompStnd04(String compStnd04) {
		this.compStnd04 = compStnd04;
	}

	public String getCompStnd05() {
		return compStnd05;
	}

	public void setCompStnd05(String compStnd05) {
		this.compStnd05 = compStnd05;
	}

	public String getDepositLimitDt() {
		return depositLimitDt;
	}

	public void setDepositLimitDt(String depositLimitDt) {
		this.depositLimitDt = depositLimitDt;
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

	public String getDormiAccount() {
		return dormiAccount;
	}

	public void setDormiAccount(String dormiAccount) {
		this.dormiAccount = dormiAccount;
	}

	public String getMealAccount() {
		return mealAccount;
	}

	public void setMealAccount(String mealAccount) {
		this.mealAccount = mealAccount;
	}

	public String getUserOrgNm() {
		return userOrgNm;
	}

	public void setUserOrgNm(String userOrgNm) {
		this.userOrgNm = userOrgNm;
	}

}

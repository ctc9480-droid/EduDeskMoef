package com.educare.edu.comn.vo;

import java.util.List;

import com.educare.edu.comn.model.LectureDormi;
import com.educare.util.EhCacheUtil;

/**
 */
public class LectureDormiVO extends LectureDormi{
	private int rowCnt = 10;
	private int firstIndex = 1;
	private int page = 1;
	private String srchWrd;
	private String srchStartDt;
	private String srchEndDt;
	private String srchUserId;
	private int srchDormiSeq;
	private String srchYear;
	private String srchMonth;
	private String srchDt;
	private List<Integer> srchDormiSeqArr;
	private int emptySt;
	
	private int assignedCnt;
	private int eduSeq;
	private String userId;
	private String startDt;
	private String endDt;
	private int dormiFee;
	private int mealFee;
	private String depositYn;
	private String rceptAccessYn;
	private int rceptCapaCnt;
	private int useTrans;
	private String mfTypeNmTmp;
	
	public String getUserNm() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getUserNm();
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public String getLoginId() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getLoginId();
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public String getMobile() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getMobile();
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public String getEmail() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getEmail();
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public String getUserOrgNm() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getUserOrgNm();
		} catch (NullPointerException e) {
			return "-";
		}
	}
	public String getMfTypeNm() {
		try {
			return EhCacheUtil.getUserPrvt(this.userId).getMfTypeNm();
		} catch (NullPointerException e) {
			return "-";
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
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getSrchWrd() {
		return srchWrd;
	}
	public void setSrchWrd(String srchWrd) {
		this.srchWrd = srchWrd;
	}
	public String getSrchStartDt() {
		return srchStartDt;
	}
	public void setSrchStartDt(String srchStartDt) {
		this.srchStartDt = srchStartDt;
	}
	public String getSrchEndDt() {
		return srchEndDt;
	}
	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}
	public int getAssignedCnt() {
		return assignedCnt;
	}
	public void setAssignedCnt(int assignedCnt) {
		this.assignedCnt = assignedCnt;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSrchUserId() {
		return srchUserId;
	}
	public void setSrchUserId(String srchUserId) {
		this.srchUserId = srchUserId;
	}
	public int getSrchDormiSeq() {
		return srchDormiSeq;
	}
	public void setSrchDormiSeq(int srchDormiSeq) {
		this.srchDormiSeq = srchDormiSeq;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getSrchYear() {
		return srchYear;
	}
	public void setSrchYear(String srchYear) {
		this.srchYear = srchYear;
	}
	public String getSrchMonth() {
		return srchMonth;
	}
	public void setSrchMonth(String srchMonth) {
		this.srchMonth = srchMonth;
	}
	public String getSrchDt() {
		return srchDt;
	}
	public void setSrchDt(String srchDt) {
		this.srchDt = srchDt;
	}
	public List<Integer> getSrchDormiSeqArr() {
		return srchDormiSeqArr;
	}
	public void setSrchDormiSeqArr(List<Integer> srchDormiSeqArr) {
		this.srchDormiSeqArr = srchDormiSeqArr;
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
	public int getEmptySt() {
		return emptySt;
	}
	public void setEmptySt(int emptySt) {
		this.emptySt = emptySt;
	}
	public String getRceptAccessYn() {
		return rceptAccessYn;
	}
	public void setRceptAccessYn(String rceptAccessYn) {
		this.rceptAccessYn = rceptAccessYn;
	}
	public int getRceptCapaCnt() {
		return rceptCapaCnt;
	}
	public void setRceptCapaCnt(int rceptCapaCnt) {
		this.rceptCapaCnt = rceptCapaCnt;
	}
	public int getUseTrans() {
		return useTrans;
	}
	public void setUseTrans(int useTrans) {
		this.useTrans = useTrans;
	}
	public String getMfTypeNmTmp() {
		return mfTypeNmTmp;
	}
	public void setMfTypeNmTmp(String mfTypeNmTmp) {
		this.mfTypeNmTmp = mfTypeNmTmp;
	}
}

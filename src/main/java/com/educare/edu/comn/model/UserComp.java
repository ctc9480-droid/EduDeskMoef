package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 *
 */
public class UserComp{
	private String userId;
	private String compNm;
	private String ceoNm;
	private String addr;
	private String addrDtl;
	private String postNo;
	private String tel;
	private String bsnLcnsNo;
	private String bsnLcnsOrg;
	private String bsnLcnsRnm;
	private String employYn;
	private String employNum;
	private String deptNm;
	private String posiNm;
	private String bsnBankOrg;
	private String bsnBankRnm;
	private String bizTpNm;
	
	private String updId;
	private Date updDe;
	
	public UserComp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserComp(String userId, String compNm, String ceoNm, String addr, String addrDtl, String postNo, String tel, String bsnLcnsNo, String bsnLcnsOrg, String bsnLcnsRnm
			, String employYn, String employNum, String deptNm, String posiNm
			, String updId, Date updDe) {
		this.userId = userId;
		this.compNm = compNm;
		this.ceoNm = ceoNm;
		this.addr = addr;
		this.addrDtl = addrDtl;
		this.postNo = postNo;
		this.tel = tel;
		this.bsnLcnsNo = bsnLcnsNo;
		this.bsnLcnsOrg = bsnLcnsOrg;
		this.bsnLcnsRnm = bsnLcnsRnm;
		this.deptNm = deptNm;
		this.posiNm = posiNm;
		this.employYn = employYn;
		this.employNum = employNum;
		
		this.updId = updId;
		this.updDe = updDe;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCeoNm() {
		return ceoNm;
	}

	public void setCeoNm(String ceoNm) {
		this.ceoNm = ceoNm;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAddrDtl() {
		return addrDtl;
	}

	public void setAddrDtl(String addrDtl) {
		this.addrDtl = addrDtl;
	}

	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBsnLcnsOrg() {
		return bsnLcnsOrg;
	}

	public void setBsnLcnsOrg(String bsnLcnsOrg) {
		this.bsnLcnsOrg = bsnLcnsOrg;
	}

	public String getBsnLcnsRnm() {
		return bsnLcnsRnm;
	}

	public void setBsnLcnsRnm(String bsnLcnsRnm) {
		this.bsnLcnsRnm = bsnLcnsRnm;
	}

	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public Date getUpdDe() {
		return updDe;
	}

	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}

	public String getCompNm() {
		return compNm;
	}

	public void setCompNm(String compNm) {
		this.compNm = compNm;
	}

	public String getBsnLcnsNo() {
		return bsnLcnsNo;
	}

	public void setBsnLcnsNo(String bsnLcnsNo) {
		this.bsnLcnsNo = bsnLcnsNo;
	}

	public String getEmployYn() {
		return employYn;
	}

	public void setEmployYn(String employYn) {
		this.employYn = employYn;
	}

	public String getEmployNum() {
		return employNum;
	}

	public void setEmployNum(String employNum) {
		this.employNum = employNum;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getPosiNm() {
		return posiNm;
	}

	public void setPosiNm(String posiNm) {
		this.posiNm = posiNm;
	}

	public String getBsnBankOrg() {
		return bsnBankOrg;
	}

	public void setBsnBankOrg(String bsnBankOrg) {
		this.bsnBankOrg = bsnBankOrg;
	}

	public String getBsnBankRnm() {
		return bsnBankRnm;
	}

	public void setBsnBankRnm(String bsnBankRnm) {
		this.bsnBankRnm = bsnBankRnm;
	}

	public String getBizTpNm() {
		return bizTpNm;
	}

	public void setBizTpNm(String bizTpNm) {
		this.bizTpNm = bizTpNm;
	}
}

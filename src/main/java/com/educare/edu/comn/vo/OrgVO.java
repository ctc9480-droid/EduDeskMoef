package com.educare.edu.comn.vo;


/**
 * 기관 
 * @author koonsdev01
 *
 */
public class OrgVO{
	private String orgCd;
	private String orgNm;
	private String orgEn;
	private int useCheck;
	private int useGooroomee;
	private int useMov;
	private String jiginFileNm;
	private String jiginFileRenm;
	private byte[] jiginFileData;
	private String bsnFileNm;
	private String bsnFileRenm;
	private String bankFileNm;
	private String bankFileRenm;
	private String headNm;
	private String sidoCd;
	private String bsnNo;
	private String bankNm;
	private String bankNo;
	private String bankUserNm;
	
	private boolean delJigin;
	private boolean delBsnFile;
	private boolean delBankFile;
	
	public String getBsnFileNm() {
		return bsnFileNm;
	}
	public void setBsnFileNm(String bsnFileNm) {
		this.bsnFileNm = bsnFileNm;
	}
	public String getBankFileNm() {
		return bankFileNm;
	}
	public void setBankFileNm(String bankFileNm) {
		this.bankFileNm = bankFileNm;
	}
	public String getHeadNm() {
		return headNm;
	}
	public void setHeadNm(String headNm) {
		this.headNm = headNm;
	}
	public String getSidoCd() {
		return sidoCd;
	}
	public void setSidoCd(String sidoCd) {
		this.sidoCd = sidoCd;
	}
	public String getBsnNo() {
		return bsnNo;
	}
	public void setBsnNo(String bsnNo) {
		this.bsnNo = bsnNo;
	}
	public String getBankNm() {
		return bankNm;
	}
	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankUserNm() {
		return bankUserNm;
	}
	public void setBankUserNm(String bankUserNm) {
		this.bankUserNm = bankUserNm;
	}
	private boolean jiginDel;
	
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
	public String getJiginFileNm() {
		return jiginFileNm;
	}
	public void setJiginFileNm(String jiginFileNm) {
		this.jiginFileNm = jiginFileNm;
	}
	public boolean isJiginDel() {
		return jiginDel;
	}
	public void setJiginDel(boolean jiginDel) {
		this.jiginDel = jiginDel;
	}
	public boolean isDelJigin() {
		return delJigin;
	}
	public void setDelJigin(boolean delJigin) {
		this.delJigin = delJigin;
	}
	public boolean isDelBsnFile() {
		return delBsnFile;
	}
	public void setDelBsnFile(boolean delBsnFile) {
		this.delBsnFile = delBsnFile;
	}
	public boolean isDelBankFile() {
		return delBankFile;
	}
	public void setDelBankFile(boolean delBankFile) {
		this.delBankFile = delBankFile;
	}
	public String getBsnFileRenm() {
		return bsnFileRenm;
	}
	public void setBsnFileRenm(String bsnFileRenm) {
		this.bsnFileRenm = bsnFileRenm;
	}
	public String getBankFileRenm() {
		return bankFileRenm;
	}
	public void setBankFileRenm(String bankFileRenm) {
		this.bankFileRenm = bankFileRenm;
	}
	public String getJiginFileRenm() {
		return jiginFileRenm;
	}
	public void setJiginFileRenm(String jiginFileRenm) {
		this.jiginFileRenm = jiginFileRenm;
	}
	public byte[] getJiginFileData() {
		return jiginFileData;
	}
	public void setJiginFileData(byte[] jiginFileData) {
		this.jiginFileData = jiginFileData;
	}
}

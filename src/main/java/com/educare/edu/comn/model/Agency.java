package com.educare.edu.comn.model;

public class Agency {
	private int idx;
	private String addr1;
	private String addr2;
	private String addr3;
	private String typeNm;
	private String ccNm;
	private String addrDesc;
	private String zip;
	private String ccCd;
	
	
	
	public Agency() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param addr1
	 * @param addr2
	 * @param addr3
	 * @param typeNm
	 * @param ccNm
	 * @param addrDesc
	 * @param zip
	 * @param ccCd
	 */
	public Agency(String addr1, String addr2, String addr3, String typeNm, String ccNm, String addrDesc, String zip, String ccCd) {
		this.setAddr1(addr1);
		this.setAddr2(addr2);
		this.setAddr3(addr3);
		this.typeNm = typeNm;
		this.ccNm = ccNm;
		this.addrDesc = addrDesc;
		this.zip = zip;
		this.ccCd = ccCd;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getTypeNm() {
		return typeNm;
	}
	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}
	public String getCcNm() {
		return ccNm;
	}
	public void setCcNm(String ccNm) {
		this.ccNm = ccNm;
	}
	public String getAddrDesc() {
		return addrDesc;
	}
	public void setAddrDesc(String addrDesc) {
		this.addrDesc = addrDesc;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCcCd() {
		return ccCd;
	}
	public void setCcCd(String ccCd) {
		this.ccCd = ccCd;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr3() {
		return addr3;
	}
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	
	
	
}

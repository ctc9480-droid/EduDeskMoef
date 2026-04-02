package com.educare.edu.comn.vo;

import java.util.Date;

/*
 * 2023.03.07 [DB]-수료증관리 테이블
 */
public class PassCertVO {
	/* 수료증 번호*/
	private Integer passIdx;
	/* 제목 */
	private String title;
	/* 내용 */
	private String content;
	/* 발급기관명 */
	private String orgNm;
	/* 테마 jsp 이름*/
	private String jspNm;
	/* 입력일 */
	private Date regDt;
	/* 입력자 */
	private String regId;
	/* 수정일 */
	private Date modDt;
	/* 수정자 */
	private String modId; 

	public PassCertVO() {
		super();
	}
	public PassCertVO(Integer passIdx, String title, String content, String orgNm, String jspNm, Date regDt,
			String regId, Date modDt, String modId) {
		super();
		this.passIdx = passIdx;
		this.title = title;
		this.content = content;
		this.orgNm = orgNm;
		this.jspNm = jspNm;
		this.regDt = regDt;
		this.regId = regId;
		this.modDt = modDt;
		this.modId = modId;
	}
	
	public Integer getPassIdx() {
		return passIdx;
	}
	public void setPassIdx(Integer passIdx) {
		this.passIdx = passIdx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getJspNm() {
		return jspNm;
	}
	public void setJspNm(String jspNm) {
		this.jspNm = jspNm;
	}
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getModDt() {
		return modDt;
	}
	public void setModDt(Date modDt) {
		this.modDt = modDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
}

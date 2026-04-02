package com.educare.edu.comn.model;

import java.util.Date;

public class QrCode{
	private int qrSeq;
	private String qrKey;
	private String qrType;
	private int eduSeq;
	private Date genDe;
	private String qrDt;//yyyy-MM-dd
	private Date qrBeginDe;
	private Date qrEndDe;
	
	public QrCode(int qrSeq, String qrKey, String qrType, int eduSeq,String qrDt, Date genDe, Date qrBeginDe, Date qrEndDe) {
		super();
		this.qrSeq = qrSeq;
		this.qrKey = qrKey;
		this.qrType = qrType;
		this.eduSeq = eduSeq;
		this.qrDt = qrDt;
		this.genDe = genDe;
		this.qrBeginDe = qrBeginDe;
		this.qrEndDe = qrEndDe;
	}
	public QrCode() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getQrKey() {
		return qrKey;
	}
	public void setQrKey(String qrKey) {
		this.qrKey = qrKey;
	}
	public String getQrType() {
		return qrType;
	}
	public void setQrType(String qrType) {
		this.qrType = qrType;
	}
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public int getQrSeq() {
		return qrSeq;
	}
	public void setQrSeq(int qrSeq) {
		this.qrSeq = qrSeq;
	}
	public Date getGenDe() {
		return genDe;
	}
	public void setGenDe(Date genDe) {
		this.genDe = genDe;
	}
	public String getQrDt() {
		return qrDt;
	}
	public void setQrDt(String qrDt) {
		this.qrDt = qrDt;
	}
	public Date getQrBeginDe() {
		return qrBeginDe;
	}
	public void setQrBeginDe(Date qrBeginDe) {
		this.qrBeginDe = qrBeginDe;
	}
	public Date getQrEndDe() {
		return qrEndDe;
	}
	public void setQrEndDe(Date qrEndDe) {
		this.qrEndDe = qrEndDe;
	}
	
	
	
}

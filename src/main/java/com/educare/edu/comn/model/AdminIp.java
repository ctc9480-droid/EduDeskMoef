package com.educare.edu.comn.model;

import java.util.Date;

public class AdminIp {
	private int idx;
	private String ip4;
	private int state;
	private String memo;
	private Date regDt;
	private String regId;
	private Date updDt;
	private String updId;
	public AdminIp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AdminIp(String ip4, int state, String memo, Date regDt, String regId, Date updDt, String updId) {
		this.ip4 = ip4;
		this.state = state;
		this.memo = memo;
		this.regDt = regDt;
		this.regId = regId;
		this.updDt = updDt;
		this.updId = updId;
	}

	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getIp4() {
		return ip4;
	}
	public void setIp4(String ip4) {
		this.ip4 = ip4;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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

	public Date getUpdDt() {
		return updDt;
	}

	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}

	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}
	
	
	
}

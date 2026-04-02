package com.educare.edu.comn.vo;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ResultVO{
	private String msg;
	private int result=0;
	private Object data;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}

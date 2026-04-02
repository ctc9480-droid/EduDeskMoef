package com.educare.edu.comn.vo;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * 상태체크 용도로 사용할 vo
 * 공통으로 사용함
 * [return]
 * result(0:실패,1:성공,...)
 * msg(결과메시지)
 * </pre>
 */
public class CheckVO{
	private String msg;
	private int result=0;
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
	
	
}

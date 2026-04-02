package com.educare.edu.comn.model;

import java.util.Date;

/**
 */
public class LectureDormiPrice {
	
	private String seasonGb; //시즌구분
	private String startDt; 	//시작일시
	private String endDt; 		//종료일시
	private int fee2;			//가격
	private int fee4;			//가격
	public String getSeasonGb() {
		return seasonGb;
	}
	public void setSeasonGb(String seasonGb) {
		this.seasonGb = seasonGb;
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
	public int getFee2() {
		return fee2;
	}
	public void setFee2(int fee2) {
		this.fee2 = fee2;
	}
	public int getFee4() {
		return fee4;
	}
	public void setFee4(int fee4) {
		this.fee4 = fee4;
	}
	
	
}

package com.educare.edu.comn.model;

import java.util.List;


/**
 * 기관 
 * @author koonsdev01
 *
 */
public class Code{
	private int codeCd;
	private String codeNm;
	private int prntCd;
	private int sort;
	
	private List<Code> subList;
	
	public int getCodeCd() {
		return codeCd;
	}
	public void setCodeCd(int codeCd) {
		this.codeCd = codeCd;
	}
	public String getCodeNm() {
		return codeNm;
	}
	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}
	public int getPrntCd() {
		return prntCd;
	}
	public void setPrntCd(int prntCd) {
		this.prntCd = prntCd;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public List<Code> getSubList() {
		return subList;
	}
	public void setSubList(List<Code> subList) {
		this.subList = subList;
	}
	
	
	

}

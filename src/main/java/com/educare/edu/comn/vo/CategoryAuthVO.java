package com.educare.edu.comn.vo;

import com.educare.edu.comn.model.CategoryAuth;

public class CategoryAuthVO extends CategoryAuth{
	private int parentSeq;
	private int ctgryDepth;
	public int getParentSeq() {
		return parentSeq;
	}
	public void setParentSeq(int parentSeq) {
		this.parentSeq = parentSeq;
	}
	public int getCtgryDepth() {
		return ctgryDepth;
	}
	public void setCtgryDepth(int ctgryDepth) {
		this.ctgryDepth = ctgryDepth;
	}
}

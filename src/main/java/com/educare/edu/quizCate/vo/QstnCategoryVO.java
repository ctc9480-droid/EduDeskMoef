package com.educare.edu.quizCate.vo;

import java.util.Date;

public class QstnCategoryVO {
	
	public static final Integer DEPTH_EDU = 1;
	public static final Integer DEPTH_DETAIL = 2;
	
	/** 문제 카테고리 아이디 */
	//private Integer ctgrySeq;
	private Integer qstnCtgSeq;	//qstn_ctg_seq
	
	/** 카테고리이름 */
	//private String ctgryNm;
	private String ctgNm;	//ctg_nm
	
	/** 카테고리 단계: 1, 2, 3 */
	//private Integer ctgryDepth;
	private Integer ctgDepth;	//ctg_depth
	
	/** 부모카테고리번호 */
	private Integer parentSeq=0;	//parent_seq
	
	/** 사용여부 */
	private String useYn;	//use_yn
	
	/** 정렬번호 */
	private Integer orderNo;	//order_no
	
	private Date updDe;
	
		
	public QstnCategoryVO() {
		super();
	}

	/**
	 * @param qstnCtgSeq
	 * @param ctgNm
	 * @param ctgDepth
	 * @param useYn
	 * @param orderNo
	 */
	public QstnCategoryVO(Integer qstnCtgSeq, String ctgNm, Integer ctgDepth, String useYn, Integer orderNo) {
		super();
		this.qstnCtgSeq = qstnCtgSeq;
		this.ctgNm = ctgNm;
		this.ctgDepth = ctgDepth;
		this.useYn = useYn;
		this.orderNo = orderNo;
	}

	/**
	 * @return the qstnCtgSeq
	 */
	public Integer getQstnCtgSeq() {
		return qstnCtgSeq;
	}

	/**
	 * @param qstnCtgSeq the qstnCtgSeq to set
	 */
	public void setQstnCtgSeq(Integer qstnCtgSeq) {
		this.qstnCtgSeq = qstnCtgSeq;
	}
	
	/**
	 * @return the ctgNm
	 */
	public String getCtgNm() {
		return ctgNm;
	}

	/**
	 * @param ctgNm the ctgNm to set
	 */
	public void setCtgNm(String ctgNm) {
		this.ctgNm = ctgNm;
	}

	/**
	 * @return the ctgDepth
	 */
	public Integer getCtgDepth() {
		return ctgDepth;
	}

	/**
	 * @param ctgDepth the ctgDepth to set
	 */
	public void setCtgDepth(Integer ctgDepth) {
		this.ctgDepth = ctgDepth;
	}
	
	public Integer getParentSeq() {
		return parentSeq;
	}

	public void setParentSeq(Integer parentSeq) {
		this.parentSeq = parentSeq;
	}

	/**
	 * @return the useYn
	 */
	public String getUseYn() {
		return useYn;
	}

	/**
	 * @param useYn the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	/**
	 * @return the orderNo
	 */
	public Integer getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Date getUpdDe() {
		return updDe;
	}

	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}

}

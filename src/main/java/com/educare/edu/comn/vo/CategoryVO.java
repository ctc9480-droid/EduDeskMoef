package com.educare.edu.comn.vo;

public class CategoryVO {
	
	public static final Integer DEPTH_EDU = 1;
	public static final Integer DEPTH_DETAIL = 2;
	
	/** 카테고리 일련번호 */
	private Integer ctgrySeq;
	
	/** 카테고리 분류 */
	private Integer ctgryDepth;
	
	/** 카테고리명 */
	private String ctgryNm;
	
	/** 사용여부 */
	private String useYn;
	
	/** 정렬번호 */
	private Integer orderNo;
	
	/** 부모 일련번호 */
	private Integer parentSeq=0;
	
	private int fbIdx;
	private int passIdx;
	private int openClass;
	private String ctg1Nm;
	private String ctg2Nm;
	private String ctg3Nm;
	private int srchCtg1Seq;
	private int srchCtg2Seq;
	private int srchCtg3Seq;
	
	private String fbTitle;

	/** 사용여부 */
	private String passCd;
	public CategoryVO() {
		super();
	}

	/**
	 * @param ctgrySeq
	 * @param ctgryDepth
	 * @param ctgryNm
	 * @param useYn
	 * @param orderNo
	 */
	public CategoryVO(Integer ctgrySeq, Integer ctgryDepth, String ctgryNm, String useYn, Integer orderNo) {
		super();
		this.ctgrySeq = ctgrySeq;
		this.ctgryDepth = ctgryDepth;
		this.ctgryNm = ctgryNm;
		this.useYn = useYn;
		this.orderNo = orderNo;
	}

	/**
	 * @return the ctgrySeq
	 */
	public Integer getCtgrySeq() {
		return ctgrySeq;
	}

	/**
	 * @param ctgrySeq the ctgrySeq to set
	 */
	public void setCtgrySeq(Integer ctgrySeq) {
		this.ctgrySeq = ctgrySeq;
	}

	/**
	 * @return the ctgryDepth
	 */
	public Integer getCtgryDepth() {
		return ctgryDepth;
	}

	/**
	 * @param ctgryDepth the ctgryDepth to set
	 */
	public void setCtgryDepth(Integer ctgryDepth) {
		this.ctgryDepth = ctgryDepth;
	}

	/**
	 * @return the ctgryNm
	 */
	public String getCtgryNm() {
		return ctgryNm;
	}

	/**
	 * @param ctgryNm the ctgryNm to set
	 */
	public void setCtgryNm(String ctgryNm) {
		this.ctgryNm = ctgryNm;
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

	public Integer getParentSeq() {
		return parentSeq;
	}

	public void setParentSeq(Integer parentSeq) {
		this.parentSeq = parentSeq;
	}

	public int getFbIdx() {
		return fbIdx;
	}

	public void setFbIdx(int fbIdx) {
		this.fbIdx = fbIdx;
	}

	public int getPassIdx() {
		return passIdx;
	}

	public void setPassIdx(int passIdx) {
		this.passIdx = passIdx;
	}

	public int getOpenClass() {
		return openClass;
	}

	public void setOpenClass(int openClass) {
		this.openClass = openClass;
	}

	public String getFbTitle() {
		return fbTitle;
	}

	public void setFbTitle(String fbTitle) {
		this.fbTitle = fbTitle;
	}

	public String getPassCd() {
		return passCd;
	}

	public void setPassCd(String passCd) {
		this.passCd = passCd;
	}

	public String getCtg1Nm() {
		return ctg1Nm;
	}

	public void setCtg1Nm(String ctg1Nm) {
		this.ctg1Nm = ctg1Nm;
	}

	public String getCtg2Nm() {
		return ctg2Nm;
	}

	public void setCtg2Nm(String ctg2Nm) {
		this.ctg2Nm = ctg2Nm;
	}

	public String getCtg3Nm() {
		return ctg3Nm;
	}

	public void setCtg3Nm(String ctg3Nm) {
		this.ctg3Nm = ctg3Nm;
	}

	public int getSrchCtg1Seq() {
		return srchCtg1Seq;
	}

	public void setSrchCtg1Seq(int srchCtg1Seq) {
		this.srchCtg1Seq = srchCtg1Seq;
	}

	public int getSrchCtg2Seq() {
		return srchCtg2Seq;
	}

	public void setSrchCtg2Seq(int srchCtg2Seq) {
		this.srchCtg2Seq = srchCtg2Seq;
	}

	public int getSrchCtg3Seq() {
		return srchCtg3Seq;
	}

	public void setSrchCtg3Seq(int srchCtg3Seq) {
		this.srchCtg3Seq = srchCtg3Seq;
	}
	

}

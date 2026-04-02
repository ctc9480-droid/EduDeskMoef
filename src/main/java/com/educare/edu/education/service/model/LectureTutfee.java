package com.educare.edu.education.service.model;

/**
 * @Class Name : LectureTutfee.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 6.
 * @version 1.0
 * @see
 * @Description 수업료 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 6.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class LectureTutfee {
	
	/** 수업 일련번호 */
	private Integer eduSeq;
	
	/** 교육과정 카테고리 일련번호 */
	private Integer eduCtgrySeq;
	
	/** 상세과정 카테고리 일련번호 */
	private Integer detailCtgrySeq;
	
	/** 년도 */
	private String year;
	
	/** 월 */
	private String month;
	
	/** 수업료 */
	private Integer tutfee;
	
	/**
	 * 
	 */
	public LectureTutfee() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param eduSeq
	 * @param eduCtgrySeq
	 * @param detailCtgrySeq
	 * @param year
	 * @param month
	 * @param tutfee
	 */
	public LectureTutfee(Integer eduSeq, Integer eduCtgrySeq, Integer detailCtgrySeq, String year, String month,
			Integer tutfee) {
		super();
		this.eduSeq = eduSeq;
		this.eduCtgrySeq = eduCtgrySeq;
		this.detailCtgrySeq = detailCtgrySeq;
		this.year = year;
		this.month = month;
		this.tutfee = tutfee;
	}

	/**
	 * @return the eduSeq
	 */
	public Integer getEduSeq() {
		return eduSeq;
	}

	/**
	 * @param eduSeq the eduSeq to set
	 */
	public void setEduSeq(Integer eduSeq) {
		this.eduSeq = eduSeq;
	}

	/**
	 * @return the eduCtgrySeq
	 */
	public Integer getEduCtgrySeq() {
		return eduCtgrySeq;
	}

	/**
	 * @param eduCtgrySeq the eduCtgrySeq to set
	 */
	public void setEduCtgrySeq(Integer eduCtgrySeq) {
		this.eduCtgrySeq = eduCtgrySeq;
	}

	/**
	 * @return the detailCtgrySeq
	 */
	public Integer getDetailCtgrySeq() {
		return detailCtgrySeq;
	}

	/**
	 * @param detailCtgrySeq the detailCtgrySeq to set
	 */
	public void setDetailCtgrySeq(Integer detailCtgrySeq) {
		this.detailCtgrySeq = detailCtgrySeq;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the tutfee
	 */
	public Integer getTutfee() {
		return tutfee;
	}

	/**
	 * @param tutfee the tutfee to set
	 */
	public void setTutfee(Integer tutfee) {
		this.tutfee = tutfee;
	}
	
}

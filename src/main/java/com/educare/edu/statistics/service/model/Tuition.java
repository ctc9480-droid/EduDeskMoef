package com.educare.edu.statistics.service.model;

/**
 * @Class Name : Tuition.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 12.
 * @version 1.0
 * @see
 * @Description 수강료 통계 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 12.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class Tuition {
	
	/** 연도 */
	private String year;
	
	/** 월 */
	private String month;
	
	/** ISO19011 심사원기본 */
	private Integer amsTuition;
	
	/** ISO9001 품질경영시스템 */
	private Integer qmsTuition;
	
	/** ISO27001 정보보호경영시스템 */
	private Integer ismsTuition;

	/**
	 * 
	 */
	public Tuition() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param year
	 * @param month
	 * @param amsTuition
	 * @param qmsTuition
	 * @param ismsTuition
	 */
	public Tuition(String year, String month, Integer amsTuition, Integer qmsTuition, Integer ismsTuition) {
		super();
		this.year = year;
		this.month = month;
		this.amsTuition = amsTuition;
		this.qmsTuition = qmsTuition;
		this.ismsTuition = ismsTuition;
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
	 * @return the amsTuition
	 */
	public Integer getAmsTuition() {
		return amsTuition;
	}

	/**
	 * @param amsTuition the amsTuition to set
	 */
	public void setAmsTuition(Integer amsTuition) {
		this.amsTuition = amsTuition;
	}

	/**
	 * @return the qmsTuition
	 */
	public Integer getQmsTuition() {
		return qmsTuition;
	}

	/**
	 * @param qmsTuition the qmsTuition to set
	 */
	public void setQmsTuition(Integer qmsTuition) {
		this.qmsTuition = qmsTuition;
	}

	/**
	 * @return the ismsTuition
	 */
	public Integer getIsmsTuition() {
		return ismsTuition;
	}

	/**
	 * @param ismsTuition the ismsTuition to set
	 */
	public void setIsmsTuition(Integer ismsTuition) {
		this.ismsTuition = ismsTuition;
	}
	
}

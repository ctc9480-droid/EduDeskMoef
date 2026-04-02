package com.educare.edu.statistics.service.model;

/**
 * @Class Name : EduStats.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 19.
 * @version 1.0
 * @see
 * @Description 교육통계 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 19.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class EduStats {
	
	/** 연도 */
	private String year;
	
	/** 월 */
	private String month;
	
	/** ISO19011 심사원기본 교육건수 */
	private Integer amsCnt;
	
	/** ISO9001 품질경영시스템 교육건수 */
	private Integer qmsCnt;
	
	/** ISO27001 정보보호경영시스템 교육건수 */
	private Integer ismsCnt;
	
	/** ISO19011 심사원기본 수강인원 수 */
	private Integer amsPersonnel;
	
	/** ISO9001 품질경영시스템 수강인원 수 */
	private Integer qmsPersonnel;
	
	/** ISO27001 정보보호경영시스템 수강인원 수 */
	private Integer ismsPersonnel;

	/**
	 * 
	 */
	public EduStats() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param year
	 * @param month
	 * @param amsCnt
	 * @param qmsCnt
	 * @param ismsCnt
	 * @param amsPersonnel
	 * @param qmsPersonnel
	 * @param ismsPersonnel
	 */
	public EduStats(String year, String month, Integer amsCnt, Integer qmsCnt, Integer ismsCnt, Integer amsPersonnel,
			Integer qmsPersonnel, Integer ismsPersonnel) {
		super();
		this.year = year;
		this.month = month;
		this.amsCnt = amsCnt;
		this.qmsCnt = qmsCnt;
		this.ismsCnt = ismsCnt;
		this.amsPersonnel = amsPersonnel;
		this.qmsPersonnel = qmsPersonnel;
		this.ismsPersonnel = ismsPersonnel;
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
	 * @return the amsCnt
	 */
	public Integer getAmsCnt() {
		return amsCnt;
	}

	/**
	 * @param amsCnt the amsCnt to set
	 */
	public void setAmsCnt(Integer amsCnt) {
		this.amsCnt = amsCnt;
	}

	/**
	 * @return the qmsCnt
	 */
	public Integer getQmsCnt() {
		return qmsCnt;
	}

	/**
	 * @param qmsCnt the qmsCnt to set
	 */
	public void setQmsCnt(Integer qmsCnt) {
		this.qmsCnt = qmsCnt;
	}

	/**
	 * @return the ismsCnt
	 */
	public Integer getIsmsCnt() {
		return ismsCnt;
	}

	/**
	 * @param ismsCnt the ismsCnt to set
	 */
	public void setIsmsCnt(Integer ismsCnt) {
		this.ismsCnt = ismsCnt;
	}

	/**
	 * @return the amsPersonnel
	 */
	public Integer getAmsPersonnel() {
		return amsPersonnel;
	}

	/**
	 * @param amsPersonnel the amsPersonnel to set
	 */
	public void setAmsPersonnel(Integer amsPersonnel) {
		this.amsPersonnel = amsPersonnel;
	}

	/**
	 * @return the qmsPersonnel
	 */
	public Integer getQmsPersonnel() {
		return qmsPersonnel;
	}

	/**
	 * @param qmsPersonnel the qmsPersonnel to set
	 */
	public void setQmsPersonnel(Integer qmsPersonnel) {
		this.qmsPersonnel = qmsPersonnel;
	}

	/**
	 * @return the ismsPersonnel
	 */
	public Integer getIsmsPersonnel() {
		return ismsPersonnel;
	}

	/**
	 * @param ismsPersonnel the ismsPersonnel to set
	 */
	public void setIsmsPersonnel(Integer ismsPersonnel) {
		this.ismsPersonnel = ismsPersonnel;
	}
	
}

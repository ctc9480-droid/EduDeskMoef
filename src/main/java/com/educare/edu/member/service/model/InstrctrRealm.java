package com.educare.edu.member.service.model;

/**
 * @Class Name : InstrctrRealm.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 9.
 * @version 1.0
 * @see
 * @Description 강사 분야 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 9.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class InstrctrRealm {
	
	/** 아이디 */
	private String userId;
	
	/** 카테고리 일련번호 */
	private Integer ctgrySeq;
	
	/** 카테고리 명 */
	private String ctgryNm;
	
	
	/**
	 * 
	 */
	public InstrctrRealm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param userId
	 * @param ctgrySeq
	 * @param ctgryNm
	 */
	public InstrctrRealm(String userId, Integer ctgrySeq, String ctgryNm) {
		super();
		this.userId = userId;
		this.ctgrySeq = ctgrySeq;
		this.ctgryNm = ctgryNm;
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	
	
}

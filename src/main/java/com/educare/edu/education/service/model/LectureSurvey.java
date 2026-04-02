package com.educare.edu.education.service.model;

import java.util.Date;

/**
 * @Class Name : LectureSurvey.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 10.
 * @version 1.0
 * @see
 * @Description 교육만족도 설문조사 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 10.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class LectureSurvey {
	
	private Integer eduSeq;
	private String userId;
	private Date rgsde;
	private Integer item01;
	private Integer item02;
	private Integer item03;
	private Integer item04;
	private Integer item05;
	private Integer item06;
	private Integer item07;
	private Integer item08;
	private Integer item09;
	private Integer item10;
	private Integer item11;
	private Integer item12;
	private Integer item13;
	private Integer item14;
	private Integer item15;
	private String item16;
	
	/**
	 * 
	 */
	public LectureSurvey() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param eduSeq
	 * @param userId
	 * @param rgsde
	 * @param item01
	 * @param item02
	 * @param item03
	 * @param item04
	 * @param item05
	 * @param item06
	 * @param item07
	 * @param item08
	 * @param item09
	 * @param item10
	 * @param item11
	 * @param item12
	 * @param item13
	 * @param item14
	 * @param item15
	 * @param item16
	 */
	public LectureSurvey(Integer eduSeq, String userId, Date rgsde, Integer item01, Integer item02, Integer item03,
			Integer item04, Integer item05, Integer item06, Integer item07, Integer item08, Integer item09,
			Integer item10, Integer item11, Integer item12, Integer item13, Integer item14, Integer item15,
			String item16) {
		super();
		this.eduSeq = eduSeq;
		this.userId = userId;
		this.rgsde = rgsde;
		this.item01 = item01;
		this.item02 = item02;
		this.item03 = item03;
		this.item04 = item04;
		this.item05 = item05;
		this.item06 = item06;
		this.item07 = item07;
		this.item08 = item08;
		this.item09 = item09;
		this.item10 = item10;
		this.item11 = item11;
		this.item12 = item12;
		this.item13 = item13;
		this.item14 = item14;
		this.item15 = item15;
		this.item16 = item16;
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
	 * @return the rgsde
	 */
	public Date getRgsde() {
		return rgsde;
	}

	/**
	 * @param rgsde the rgsde to set
	 */
	public void setRgsde(Date rgsde) {
		this.rgsde = rgsde;
	}

	/**
	 * @return the item01
	 */
	public Integer getItem01() {
		return item01;
	}

	/**
	 * @param item01 the item01 to set
	 */
	public void setItem01(Integer item01) {
		this.item01 = item01;
	}

	/**
	 * @return the item02
	 */
	public Integer getItem02() {
		return item02;
	}

	/**
	 * @param item02 the item02 to set
	 */
	public void setItem02(Integer item02) {
		this.item02 = item02;
	}

	/**
	 * @return the item03
	 */
	public Integer getItem03() {
		return item03;
	}

	/**
	 * @param item03 the item03 to set
	 */
	public void setItem03(Integer item03) {
		this.item03 = item03;
	}

	/**
	 * @return the item04
	 */
	public Integer getItem04() {
		return item04;
	}

	/**
	 * @param item04 the item04 to set
	 */
	public void setItem04(Integer item04) {
		this.item04 = item04;
	}

	/**
	 * @return the item05
	 */
	public Integer getItem05() {
		return item05;
	}

	/**
	 * @param item05 the item05 to set
	 */
	public void setItem05(Integer item05) {
		this.item05 = item05;
	}

	/**
	 * @return the item06
	 */
	public Integer getItem06() {
		return item06;
	}

	/**
	 * @param item06 the item06 to set
	 */
	public void setItem06(Integer item06) {
		this.item06 = item06;
	}

	/**
	 * @return the item07
	 */
	public Integer getItem07() {
		return item07;
	}

	/**
	 * @param item07 the item07 to set
	 */
	public void setItem07(Integer item07) {
		this.item07 = item07;
	}

	/**
	 * @return the item08
	 */
	public Integer getItem08() {
		return item08;
	}

	/**
	 * @param item08 the item08 to set
	 */
	public void setItem08(Integer item08) {
		this.item08 = item08;
	}

	/**
	 * @return the item09
	 */
	public Integer getItem09() {
		return item09;
	}

	/**
	 * @param item09 the item09 to set
	 */
	public void setItem09(Integer item09) {
		this.item09 = item09;
	}

	/**
	 * @return the item10
	 */
	public Integer getItem10() {
		return item10;
	}

	/**
	 * @param item10 the item10 to set
	 */
	public void setItem10(Integer item10) {
		this.item10 = item10;
	}

	/**
	 * @return the item11
	 */
	public Integer getItem11() {
		return item11;
	}

	/**
	 * @param item11 the item11 to set
	 */
	public void setItem11(Integer item11) {
		this.item11 = item11;
	}

	/**
	 * @return the item12
	 */
	public Integer getItem12() {
		return item12;
	}

	/**
	 * @param item12 the item12 to set
	 */
	public void setItem12(Integer item12) {
		this.item12 = item12;
	}

	/**
	 * @return the item13
	 */
	public Integer getItem13() {
		return item13;
	}

	/**
	 * @param item13 the item13 to set
	 */
	public void setItem13(Integer item13) {
		this.item13 = item13;
	}

	/**
	 * @return the item14
	 */
	public Integer getItem14() {
		return item14;
	}

	/**
	 * @param item14 the item14 to set
	 */
	public void setItem14(Integer item14) {
		this.item14 = item14;
	}

	/**
	 * @return the item15
	 */
	public Integer getItem15() {
		return item15;
	}

	/**
	 * @param item15 the item15 to set
	 */
	public void setItem15(Integer item15) {
		this.item15 = item15;
	}

	/**
	 * @return the item16
	 */
	public String getItem16() {
		return item16;
	}

	/**
	 * @param item16 the item16 to set
	 */
	public void setItem16(String item16) {
		this.item16 = item16;
	}
	
}

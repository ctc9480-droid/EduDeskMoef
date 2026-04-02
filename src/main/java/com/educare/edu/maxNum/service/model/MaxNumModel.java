
package com.educare.edu.maxNum.service.model;

/**
 * @Class Name : MaxNumModel.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 1.
 * @version 1.0
 * @see
 * @Description 맥스넘 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 1.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class MaxNumModel {

	private String func;
	private Integer seq;

	public MaxNumModel() {
		super();
	}

	/**
	 * @param func
	 * @param seq
	 */
	public MaxNumModel(String func, Integer seq) {
		super();
		this.func = func;
		this.seq = seq;
	}

	/**
	 * @return the func
	 */
	public String getFunc() {
		return func;
	}

	/**
	 * @param func the func to set
	 */
	public void setFunc(String func) {
		this.func = func;
	}

	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	} 
	
}

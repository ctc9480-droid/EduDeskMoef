package com.educare.edu.comn.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;



/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LectureCouns{
	private int cnsSeq;
	private String userId;
	private int eduSeq;
	private String title;
	private String content;
	private int state;
	
	private String regId;
	private Date regDe;
	private String updId;
	private Date updDe;
	
	public LectureCouns(int cnsSeq, String userId, int eduSeq, String title, String content, int state, String regId, Date regDe, String updId, Date updDe) {
		super();
		this.cnsSeq = cnsSeq;
		this.userId = userId;
		this.eduSeq = eduSeq;
		this.title = title;
		this.content = content;
		this.state = state;
		this.regId = regId;
		this.regDe = regDe;
		this.updId = updId;
		this.updDe = updDe;
	}



	public LectureCouns() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getCnsSeq() {
		return cnsSeq;
	}



	public void setCnsSeq(int cnsSeq) {
		this.cnsSeq = cnsSeq;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public int getEduSeq() {
		return eduSeq;
	}



	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public int getState() {
		return state;
	}



	public void setState(int state) {
		this.state = state;
	}



	public String getRegId() {
		return regId;
	}



	public void setRegId(String regId) {
		this.regId = regId;
	}



	public Date getRegDe() {
		return regDe;
	}



	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}



	public String getUpdId() {
		return updId;
	}



	public void setUpdId(String updId) {
		this.updId = updId;
	}



	public Date getUpdDe() {
		return updDe;
	}



	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}



	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
}

package com.educare.edu.comn.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
//문제 보기
public class QuestionChoice{
	private boolean isChAns;
	
	private int qstnSeq;	//문제아이디
	private int chSeq;		//보기번호
	private String chStr;	//보기 문자열
	private String chFnOrg;
	private String chFnRnm;
	private String chAnsYn;
	private String updId;
	private Date updDe;
	private String useYn;
	
	
	
	public QuestionChoice() {
	}

	@JsonCreator
    public QuestionChoice(@JsonProperty("chStr") String chStr,@JsonProperty("isChAns") boolean isChAns) {
        this.chStr = chStr;
        this.isChAns = isChAns;
    }
	
	public QuestionChoice(int qstnSeq, int chSeq, String chStr, String chFnOrg, String chFnRnm, String chAnsYn, String updId, Date updDe,String useYn) {
		//super();
		this.qstnSeq = qstnSeq;
		this.chSeq = chSeq;
		this.chStr = chStr;
		this.chFnOrg = chFnOrg;
		this.chFnRnm = chFnRnm;
		this.chAnsYn = chAnsYn;
		this.setUpdId(updId);
		this.setUpdDe(updDe);
		this.useYn = useYn;
	}
	public int getQstnSeq() {
		return qstnSeq;
	}
	public void setQstnSeq(int qstnSeq) {
		this.qstnSeq = qstnSeq;
	}
	public int getChSeq() {
		return chSeq;
	}
	public void setChSeq(int chSeq) {
		this.chSeq = chSeq;
	}
	public String getChStr() {
		return chStr;
	}
	public void setChStr(String chStr) {
		this.chStr = chStr;
	}
	public String getChFnOrg() {
		return chFnOrg;
	}
	public void setChFnOrg(String chFnOrg) {
		this.chFnOrg = chFnOrg;
	}
	public String getChFnRnm() {
		return chFnRnm;
	}
	public void setChFnRnm(String chFnRnm) {
		this.chFnRnm = chFnRnm;
	}
	public String getChAnsYn() {
		return chAnsYn;
	}
	public void setChAnsYn(String chAnsYn) {
		this.chAnsYn = chAnsYn;
	}

	public boolean isChAns() {
		return isChAns;
	}
	public void setChAns(boolean isChAns) {
		this.isChAns = isChAns;
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

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
	
	
}

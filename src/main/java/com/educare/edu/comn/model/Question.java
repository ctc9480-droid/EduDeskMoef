package com.educare.edu.comn.model;

import java.util.Date;
//문제은행
public class Question{
	private int qstnSeq;	//문제아이디
	private String qstnNm;	//문제명
	private int qstnTp;		//문제종류 (1 - 객관식, 2 - 객관식다중선택, 3 - 단답형, 4 - 서술형)
	private int ctg1Seq;
	private int ctg2Seq;
	private int ctg3Seq;
	private int diffType;
	private String qstnDesc;
	private String qstnStr;
	private String qstnFnOrg;
	private String qstnFnRnm;
	private int optionCnt;
	private String fillBlank;
	private String ansDesc;
	private String useYn;
	private String regId;
	private String updId;
	private Date regDe;
	private Date updDe;
	
	
	
	public Question() {
	}
	public Question(String qstnNm, int qstnTp, int ctg1Seq, int ctg2Seq, int ctg3Seq, int diffType, String qstnDesc, String qstnStr, String qstnFnOrg, String qstnFnRnm, int optionCnt, String fillBlank, String ansDesc, String useYn, String regId,
			String updId, Date regDe, Date updDe) {
		super();
		this.qstnNm = qstnNm;
		this.qstnTp = qstnTp;
		this.ctg1Seq = ctg1Seq;
		this.ctg2Seq = ctg2Seq;
		this.ctg3Seq = ctg3Seq;
		this.diffType = diffType;
		this.qstnDesc = qstnDesc;
		this.qstnStr = qstnStr;
		this.qstnFnOrg = qstnFnOrg;
		this.qstnFnRnm = qstnFnRnm;
		this.optionCnt = optionCnt;
		this.fillBlank = fillBlank;
		this.ansDesc = ansDesc;
		this.useYn = useYn;
		this.regId = regId;
		this.updId = updId;
		this.regDe = regDe;
		this.updDe = updDe;
	}
	public int getQstnSeq() {
		return qstnSeq;
	}
	public void setQstnSeq(int qstnSeq) {
		this.qstnSeq = qstnSeq;
	}
	public String getQstnNm() {
		return qstnNm;
	}
	public void setQstnNm(String qstnNm) {
		this.qstnNm = qstnNm;
	}
	public int getQstnTp() {
		return qstnTp;
	}
	public void setQstnTp(int qstnTp) {
		this.qstnTp = qstnTp;
	}
	public int getCtg1Seq() {
		return ctg1Seq;
	}
	public void setCtg1Seq(int ctg1Seq) {
		this.ctg1Seq = ctg1Seq;
	}
	public int getCtg2Seq() {
		return ctg2Seq;
	}
	public void setCtg2Seq(int ctg2Seq) {
		this.ctg2Seq = ctg2Seq;
	}
	public int getDiffType() {
		return diffType;
	}
	public void setDiffType(int diffType) {
		this.diffType = diffType;
	}
	public String getQstnDesc() {
		return qstnDesc;
	}
	public void setQstnDesc(String qstnDesc) {
		this.qstnDesc = qstnDesc;
	}
	public String getQstnStr() {
		return qstnStr;
	}
	public void setQstnStr(String qstnStr) {
		this.qstnStr = qstnStr;
	}
	public String getQstnFnOrg() {
		return qstnFnOrg;
	}
	public void setQstnFnOrg(String qstnFnOrg) {
		this.qstnFnOrg = qstnFnOrg;
	}
	public String getQstnFnRnm() {
		return qstnFnRnm;
	}
	public void setQstnFnRnm(String qstnFnRnm) {
		this.qstnFnRnm = qstnFnRnm;
	}
	public int getOptionCnt() {
		return optionCnt;
	}
	public void setOptionCnt(int optionCnt) {
		this.optionCnt = optionCnt;
	}
	public String getFillBlank() {
		return fillBlank;
	}
	public void setFillBlank(String fillBlank) {
		this.fillBlank = fillBlank;
	}
	public String getAnsDesc() {
		return ansDesc;
	}
	public void setAnsDesc(String ansDesc) {
		this.ansDesc = ansDesc;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public Date getRegDe() {
		return regDe;
	}
	public void setRegDe(Date regDe) {
		this.regDe = regDe;
	}
	public Date getUpdDe() {
		return updDe;
	}
	public void setUpdDe(Date updDe) {
		this.updDe = updDe;
	}
	public int getCtg3Seq() {
		return ctg3Seq;
	}
	public void setCtg3Seq(int ctg3Seq) {
		this.ctg3Seq = ctg3Seq;
	}
	
	
	
}

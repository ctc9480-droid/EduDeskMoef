package com.educare.edu.bbs.service.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.educare.util.DateUtil;

/**
 * @Class Name : BoardComment.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
public class BoardComment {
	private int idx=0;
	private int bIdx;
	private int pIdx;
	private String content;
	private String regId;
	private String regNm;
	private String regNk;
	private String regDtime;
	private String modId;
	private String modNm;
	private String modDtime;
	private int status=1;
	private int gIdx;
	private String gOrd="0";
	private int gLvl=0;
	
	private int rnum;
	
	private int pageNo=1;
	private int firstRecordIndex=0;
	private int recordCountPerPage=10;
	private String searchSelect;
	private String searchWord;
	
	private int result=1;
	
	public String getContent() {
		if(content!=null){
			if(content.equals("")){
				return null;
			}
			return content;
		}
		return content;
	}
	public String getContent2() {
		if(content!=null){
			content=content.replaceAll("(\r\n|\r|\n|\n\r)","<br/>");
		}
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDtime() {
		if(regDtime==null){
			return DateUtil.getDate2Str(Calendar.getInstance().getTime(),"yyyyMMddHHmmssSSS");
		}
		return regDtime;
	}
	public String getRegDtime2() {
		String result=regDtime;
		try {
			if(regDtime.length()==17){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS",Locale.KOREA);
				result=DateUtil.getDate2Str(sdf.parse(regDtime),"yyyy.MM.dd HH:mm:ss");
			}
		} catch (NullPointerException | ParseException e) {
			return "ERROR";
		}
		return result;
	}
	public void setRegDtime(String regDtime) {
		this.regDtime = regDtime;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getModDtime() {
		if(modDtime==null){
			return DateUtil.getDate2Str(Calendar.getInstance().getTime(),"yyyyMMddHHmmssSSS");
		}
		return modDtime;
	}
	public void setModDtime(String modDtime) {
		this.modDtime = modDtime;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public String getModNm() {
		return modNm;
	}
	public void setModNm(String modNm) {
		this.modNm = modNm;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getFirstRecordIndex() {
		return firstRecordIndex;
	}
	public void setFirstRecordIndex(int firstRecordIndex) {
		this.firstRecordIndex = firstRecordIndex;
	}
	public String getSearchSelect() {
		return searchSelect;
	}
	public void setSearchSelect(String searchSelect) {
		this.searchSelect = searchSelect;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public int getpIdx() {
		return pIdx;
	}
	public void setpIdx(int pIdx) {
		this.pIdx = pIdx;
	}
	public int getgIdx() {
		return gIdx;
	}
	public void setgIdx(int gIdx) {
		this.gIdx = gIdx;
	}
	public String getgOrd() {
		return gOrd;
	}
	public void setgOrd(String gOrd) {
		this.gOrd = gOrd;
	}
	public int getgLvl() {
		return gLvl;
	}
	public void setgLvl(int gLvl) {
		this.gLvl = gLvl;
	}
	public String getChildFlagStr() {
		String result="";
		if(gLvl>0){
			for(int i=0;i<gLvl;i++){
				result+="&nbsp;&nbsp;&nbsp;";
			}
			result=(result+"└>");
		}
		return result;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getbIdx() {
		return bIdx;
	}
	public void setbIdx(int bIdx) {
		this.bIdx = bIdx;
	}
	public String getRegNk() {
		return regNk;
	}
	public void setRegNk(String regNk) {
		this.regNk = regNk;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	
}

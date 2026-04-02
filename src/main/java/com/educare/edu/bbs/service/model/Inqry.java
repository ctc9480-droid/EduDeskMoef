package com.educare.edu.bbs.service.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

/**
 * @Class Name : Inqry.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 온라인 문의 게시판 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
public class Inqry {
	//테이블컬럼
	public int idx;
	public String title;
	public String content;
	public String answer;
	public int status=1;
	public int agreeGb=0;
	public String password;
	public String contact;
	public String email;
	public String regNk;
	public String regId;
	public String regNm;
	public String regDtime;
	public String modId;
	public String modNm;
	public String modDtime;
	
	//추가 컬럼
	public int rNum;
	
	//페이징컬럼
	public int pageNo=1;
	public int firstRecordIndex=0;
	public int recordCountPerPage=10;
	
	//검색컬럼
	public String searchSelect;
	public String searchWord="";
	public String searchStatus;
	
	//성공여부컬럼
	public int result=1;
	
	//status 이름
	public final String STATUS_NM[]={"비공개","미답변","답변완료"};
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getTitle() {
		if(title.isEmpty()){
			return null;
		}
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		if(content.isEmpty()){
			return null;
		}
		return content;
	}
	public String getContent2() {
		if(content!=null){
			return content.replaceAll("(\r\n|\r|\n|\n\r)","<br/>");
		}else{
			return "ERROR";
		}
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getrNum() {
		return rNum;
	}
	public void setrNum(int rNum) {
		this.rNum = rNum;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
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
	public int getStatus() {
		return status;
	}
	public String getStatusNm() {
		return STATUS_NM[status];
	}
	public void setStatus(int status) {
		this.status = status;
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
	public void setRegDtime(String regDtime) {
		this.regDtime = regDtime;
	}
	public String replaceRegDtime(String pattern) {
		String result=regDtime;
		try {
			if(regDtime.length()==17){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS",Locale.KOREA);
				result=DateUtil.getDate2Str(sdf.parse(regDtime),pattern);
			}
		} catch (NullPointerException | ParseException e) {
			return result;
		}
		return result;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getAgreeGb() {
		return agreeGb;
	}
	public void setAgreeGb(int agreeGb) {
		this.agreeGb = agreeGb;
	}
	public String getRegNk() {
		return regNk;
	}
	public void setRegNk(String regNk) {
		this.regNk = regNk;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getModNm() {
		return modNm;
	}
	public void setModNm(String modNm) {
		this.modNm = modNm;
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
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	public String getSearchStatus() {
		return searchStatus;
	}
	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String[] getStatusArr() {
		return STATUS_NM;
	}
	public boolean isAdmin() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getRequest();
		if(request.getRequestURI().indexOf("/admin/")>=0){
			return true;
		}
		return false;
	}
	//잠금표시여부
	public int getLockStatus(){
		if(password!=null){
			if(password.length()>0){
				return 1;
			}
		}
		if(regId!=null){
			if(!regId.equals(SessionUserInfoHelper.getUserId())){
				return 2;
			}
		}
		return 0;
	}
}

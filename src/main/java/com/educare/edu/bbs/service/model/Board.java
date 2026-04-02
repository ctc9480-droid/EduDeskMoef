package com.educare.edu.bbs.service.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.DateUtil;

/**
 * @Class Name : Board.java
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
@Component
public class Board {
	
	public int idx;
	public int pIdx=0;
	public String boardType;
	public String title;
	public String content;
	public int hit=1;
	public String regId="0";
	public String regNm;
	public String regDtime;
	public String modId="0";
	public String modNm;
	public String modDtime;
	public int gIdx;
	public String gOrd="0";
	public int gLvl=0;
	public Integer status=1;
	public String cate;
	public String thumbFile;
	public String fileTypeGb;
	public String noticeYn;
	public String tmp01;
	
	public String regNm2;
	public int rNum;
	public int commentCnt;
	public String fileSeqs01;//파일섹션번호
	
	public int pageNo=1;
	public int firstRecordIndex=0;
	public int recordCountPerPage=10;
	public String searchSelect;
	public String searchWord="";
	public String searchStatus;
	public String[] boardTypeArr;
	public int nearGb = 0;//게시물 이전,이후값 (-1:이전,1:이후)
	
	public int result=1;
	
	//status 이름
	public final String STATUS_NM[]={"삭제","게시","미게시"};
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public String getTitle() {
		if(title!=null){
			if(title.equals("")){
				return null;
			}
			return title;
		}
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public String getContent2() {
		return content.replaceAll("(\r\n|\r|\n|\n\r)","<br/>");
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
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
	public String replaceRegDtime(String pattern) {
		String result=regDtime;
		try {
			if(regDtime.length()==17){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS",Locale.KOREA);
				result=DateUtil.getDate2Str(sdf.parse(regDtime),pattern);
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
			result=(result+"└>RE:");
		}
		return result;
	}
	public String getChildFlag(){
		String result="";
		if(gLvl>0){
			result = "listRe";
		}
		return result;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCommentCnt() {
		return commentCnt;
	}
	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	public String getFileSeqs01() {
		return fileSeqs01;
	}
	public void setFileSeqs01(String fileSeqs01) {
		this.fileSeqs01 = fileSeqs01;
	}
	
	public String[] getStatusArr() {
		return STATUS_NM;
	}
	public String getStatusNm() {
		return STATUS_NM[status];
	}
	public String getSearchStatus() {
		return searchStatus;
	}
	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}
	public boolean isAdmin(){
		if(SessionUserInfoHelper.isAdmin()){
			return true;
		}
		return false;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String[] getBoardTypeArr() {
		return boardTypeArr;
	}
	public void setBoardTypeArr(String[] boardTypeArr) {
		this.boardTypeArr = boardTypeArr;
	}
	public String getThumbFile() {
		return thumbFile;
	}
	public void setThumbFile(String thumbFile) {
		this.thumbFile = thumbFile;
	}
	public String getFileTypeGb() {
		return fileTypeGb;
	}
	public void setFileTypeGb(String fileTypeGb) {
		this.fileTypeGb = fileTypeGb;
	}
	
	public String getAddFileTypeIco(){
		try {
			switch (this.fileTypeGb) {
			case "img":return "icoImg";
			case "doc":return "icoDoc";
			case "ppt":return "icoPpt";
			case "pdf":return "icoPdf";
			case "vid":return "icoVideo";

			default:return "";			
			}
		} catch (NullPointerException e) {
			return "";
		}
	}
	public int getNearGb() {
		return nearGb;
	}
	public void setNearGb(int nearGb) {
		this.nearGb = nearGb;
	}
	public String getNoticeYn() {
		return noticeYn;
	}
	public void setNoticeYn(String noticeYn) {
		this.noticeYn = noticeYn;
	}
	public String getRegNm2() {
		return regNm2;
	}
	public void setRegNm2(String regNm2) {
		this.regNm2 = regNm2;
	}
	public String getTmp01() {
		return tmp01;
	}
	public void setTmp01(String tmp01) {
		this.tmp01 = tmp01;
	}
}

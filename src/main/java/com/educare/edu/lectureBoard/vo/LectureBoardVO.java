package com.educare.edu.lectureBoard.vo;

import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;

/**
 */
public class LectureBoardVO extends LectureBoard{
	private int rNum;
	private int commentCnt;
	private String fileSeqs01;//파일섹션번호
	private int userMemLvl;
	
	private int pageNo=1;
	private int firstRecordIndex=0;
	private int recordCountPerPage=10;
	private String searchSelect;
	private String searchWord="";
	private String searchStatus;
	private String[] boardTypeArr;
	
	public int getUserMemLvl() {
		return userMemLvl;
	}

	public void setUserMemLvl(int userMemLvl) {
		this.userMemLvl = userMemLvl;
	}
	private int result=1;
	
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

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
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

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

	public String[] getBoardTypeArr() {
		return boardTypeArr;
	}

	public void setBoardTypeArr(String[] boardTypeArr) {
		this.boardTypeArr = boardTypeArr;
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

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	public String getFileSeqs01() {
		return fileSeqs01;
	}

	public void setFileSeqs01(String fileSeqs01) {
		this.fileSeqs01 = fileSeqs01;
	}

	public boolean isMyReg(){
		try {
			String userId = SessionUserInfoHelper.getUserId();
			if(userId == null){
				return false;
			}
			if(SessionUserInfoHelper.getUserId().equals(this.regId)){
				return true;
			}
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	public String getAddRegNmMasked(){
		try {
			String result = this.regNm;
			//if(userMemLvl>5 /*|| SessionUserInfoHelper.getUserId().equals(this.regId)*/){
				//result = this.regNm.substring(0,1)+"OO";
			//}
			return result;
		} catch (NullPointerException e) {
			return "ERROR";
		}
	}
}	

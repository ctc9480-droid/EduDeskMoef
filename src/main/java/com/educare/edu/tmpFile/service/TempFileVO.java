package com.educare.edu.tmpFile.service;

/**
 * @Class Name : TempFileVO.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 25.
 * @version 1.0
 * @see
 * @Description 임시파일 VO
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 25.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class TempFileVO {
	
	/** 파일 일련번호 */
	private Integer fileSeq;
	
	/** 파일 원본명 */
	private String fileOrg;
	
	/** 파일 저장명 */
	private String fileRename;
	
	/** 파일 확장자 */
	private String fileType;
	
	/** 파일용량 */
	private Integer fileSize;
	
	private String totalCnt;
	private String maxCnt;
	private String totalSize;
	private String maxSize;

	/**
	 * @return the fileSeq
	 */
	public Integer getFileSeq() {
		return fileSeq;
	}

	/**
	 * @param fileSeq the fileSeq to set
	 */
	public void setFileSeq(Integer fileSeq) {
		this.fileSeq = fileSeq;
	}

	/**
	 * @return the fileOrg
	 */
	public String getFileOrg() {
		return fileOrg;
	}

	/**
	 * @param fileOrg the fileOrg to set
	 */
	public void setFileOrg(String fileOrg) {
		this.fileOrg = fileOrg;
	}

	/**
	 * @return the fileRename
	 */
	public String getFileRename() {
		return fileRename;
	}

	/**
	 * @param fileRename the fileRename to set
	 */
	public void setFileRename(String fileRename) {
		this.fileRename = fileRename;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the fileSize
	 */
	public Integer getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the totalCnt
	 */
	public String getTotalCnt() {
		return totalCnt;
	}

	/**
	 * @param totalCnt the totalCnt to set
	 */
	public void setTotalCnt(String totalCnt) {
		this.totalCnt = totalCnt;
	}

	/**
	 * @return the maxCnt
	 */
	public String getMaxCnt() {
		return maxCnt;
	}

	/**
	 * @param maxCnt the maxCnt to set
	 */
	public void setMaxCnt(String maxCnt) {
		this.maxCnt = maxCnt;
	}

	/**
	 * @return the totalSize
	 */
	public String getTotalSize() {
		return totalSize;
	}

	/**
	 * @param totalSize the totalSize to set
	 */
	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * @return the maxSize
	 */
	public String getMaxSize() {
		return maxSize;
	}

	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(String maxSize) {
		this.maxSize = maxSize;
	}
	
	
}

package com.educare.edu.tmpFile.service.model;

/**
 * @Class Name : TempFile.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 10.
 * @version 1.0
 * @see
 * @Description 임시저장파일 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 10.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class TempFile {
	
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
	
	/**
	 * 
	 */
	public TempFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param fileSeq
	 * @param fileOrg
	 * @param fileRename
	 * @param fileType
	 * @param fileSize
	 */
	public TempFile(Integer fileSeq, String fileOrg, String fileRename, String fileType, Integer fileSize) {
		super();
		this.fileSeq = fileSeq;
		this.fileOrg = fileOrg;
		this.fileRename = fileRename;
		this.fileType = fileType;
		this.fileSize = fileSize;
	}

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
	
}

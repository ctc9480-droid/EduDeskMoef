package com.educare.edu.member.service.model;

/**
 * @Class Name : InstrctrAttach.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 9.
 * @version 1.0
 * @see
 * @Description 강사 첨부파일 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 9.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class InstrctrAttach {
	
	public static final String FILE_SECTION_01 = "01";
	public static final String FILE_SECTION_02 = "02";
	public static final String FILE_SECTION_03 = "03";
	public static final String FILE_SECTION_09 = "09";
	
	/** 파일 일련번호 */
	private Integer fileSeq;
	
	/** 아이디 */
	private String userId;
	
	/** 파일구분 01:자격증, 02:이력서, 03:내부교육수료증, 09:기타*/
	private String fileSection;
	
	/** 파일 원본명 */
	private String fileOrg;
	
	/** 파일 저장명 */
	private String fileRename;
	
	/** 파일 확장자 */
	private String fileType;
	
	/** 파일용량 */
	private Integer fileSize;

	public InstrctrAttach() {
		super();
	}

	/**
	 * @param fileSeq
	 * @param userId
	 * @param fileSection
	 * @param fileOrg
	 * @param fileRename
	 * @param fileType
	 * @param fileSize
	 */
	public InstrctrAttach(Integer fileSeq, String userId, String fileSection, String fileOrg, String fileRename,
			String fileType, Integer fileSize) {
		super();
		this.fileSeq = fileSeq;
		this.userId = userId;
		this.fileSection = fileSection;
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the fileSection
	 */
	public String getFileSection() {
		return fileSection;
	}

	/**
	 * @param fileSection the fileSection to set
	 */
	public void setFileSection(String fileSection) {
		this.fileSection = fileSection;
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

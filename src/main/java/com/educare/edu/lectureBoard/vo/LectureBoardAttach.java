package com.educare.edu.lectureBoard.vo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.educare.edu.bbs.BbsConstant;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;

/**

 */
public class LectureBoardAttach {
	
	/** 파일 일련번호 */
	private Integer fileSeq;
	
	/** 게시물 번호 */
	private Integer boardIdx;
	
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

	private String attachDelSeqs;
	private String fileSeqs01;
	private Integer sumFileSize;
	private Integer sumFileCnt;
	
	private int eduSeq;
	
	public LectureBoardAttach() {
		super();
	}

	/**
	 * @param fileSeq
	 * @param boardIdx
	 * @param fileSection
	 * @param fileOrg
	 * @param fileRename
	 * @param fileType
	 * @param fileSize
	 */
	public LectureBoardAttach(Integer fileSeq, Integer boardIdx, String fileSection, String fileOrg, String fileRename,
			String fileType, Integer fileSize) {
		super();
		this.fileSeq = fileSeq;
		this.boardIdx = boardIdx;
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
	public String getReplaceFileSize() {
		if(fileSize!=null){
			return LncUtil.replaceFileSize(fileSize);
		}
		return "0 byte";
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(Integer boardIdx) {
		this.boardIdx = boardIdx;
	}

	public String getAttachDelSeqs() {
		return attachDelSeqs;
	}

	public void setAttachDelSeqs(String attachDelSeqs) {
		this.attachDelSeqs = attachDelSeqs;
	}

	public String getFileSeqs01() {
		return fileSeqs01;
	}

	public void setFileSeqs01(String fileSeqs01) {
		this.fileSeqs01 = fileSeqs01;
	}

	public Integer getSumFileSize() {
		return sumFileSize;
	}
	
	public String getReplaceSumFileSize() {
		if(sumFileSize!=null){
			return LncUtil.replaceFileSize(sumFileSize);
		}
		return "0 byte";
	}

	public void setSumFileSize(Integer sumFileSize) {
		this.sumFileSize = sumFileSize;
	}

	public Integer getSumFileCnt() {
		return sumFileCnt;
	}

	public void setSumFileCnt(Integer sumFileCnt) {
		this.sumFileCnt = sumFileCnt;
	}
	
	/**
	 * 업로드한 이미지 파일을 출력한다.
	 * @return
	 */
	public String getFileToImg2() {
		String errReturn="/에러용이미지.jpg";
		String errResult = errReturn;
		ByteArrayOutputStream baos=null;
		FileInputStream in=null;
		try {
			if(!"jpg,jpeg,png,gif".contains(getFileType())){
				return errReturn;
			}
			String filePath = BbsConstant.PATH_ATTACH + getFileRename();
			
			filePath = LncUtil.filePathReplaceAll(filePath);
			File file = new File(filePath);
			in = new FileInputStream(file);
			BufferedImage bimg = ImageIO.read(in);
			in.close();
			baos = new ByteArrayOutputStream();
			ImageIO.write( bimg, "png", baos );
			baos.flush();
			byte[] imageInByteArray = baos.toByteArray();
			baos.close();
			return "data:x-image/jpg;base64,"+javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
		} catch (NullPointerException | IOException e) {
			errResult = errReturn;
		}finally{
			try {
				if(baos!=null)baos.close();
				if(in!=null)in.close();
			} catch (IOException e2) {
				errResult = errReturn;
			}
		}
		return errResult;
	}

	public int getEduSeq() {
		return eduSeq;
	}

	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	
}

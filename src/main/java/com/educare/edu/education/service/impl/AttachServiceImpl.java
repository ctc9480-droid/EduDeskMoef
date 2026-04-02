package com.educare.edu.education.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.education.service.AttachService;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.util.FileUtil;
import com.educare.util.MaxNumUtil;

/**
 * @Class Name : AttachServiceImpl.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 11.
 * @version 1.0
 * @see
 * @Description 교육결과 첨부파일 서비스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 11.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Service("AttachService")
public class AttachServiceImpl implements AttachService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(AttachServiceImpl.class.getName());
	
	/** 첨부파일 Mapper */
	@Resource(name = "AttachMapper")
	private AttachMapper attachMapper;
	
	/**
	 * 교육결과자료 업로드
	 * @param request
	 * @param eduSeq
	 * @return
	 */
	@Override
	public LectureAttach uploadFile(MultipartHttpServletRequest request, Integer eduSeq,String fileSection) {
		
		boolean isSuccess = false;
		Iterator<?> itr =  request.getFileNames();
		String fileRename = "";
		String orgNm = "";
		String fileType = "";
		int fileSize=0;
		LectureAttach attach = null;
		
		if(itr.hasNext()) {
			List<MultipartFile> list = request.getFiles((String) itr.next());
			for(MultipartFile multipartFile : list) {
				orgNm = multipartFile.getOriginalFilename();
				try {
					orgNm = new String(orgNm.getBytes("8859_1"),"utf-8");
				} catch (UnsupportedEncodingException e) {
					LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
					isSuccess = false;
				}
				fileType = orgNm.substring(orgNm.lastIndexOf(".") + 1, orgNm.length());
				fileRename = System.currentTimeMillis()+"_"+fileSection+ "." + fileType;
				fileSize=(int) multipartFile.getSize();
				//NaverObjectStorage.multiPartupload(multipartFile, "upload/edu/"+eduSeq+"/", orgNm, fileRename);
				FileUtil.multiPartupload(multipartFile, "upload/edu/"+eduSeq+"/", orgNm, fileRename);
			}
			isSuccess = true;
		}
		
		if(isSuccess){
			attach = new LectureAttach(
					eduSeq, 
					fileSection, 
					orgNm, 
					fileRename, 
					fileType, 
					fileSize
				);
			attachMapper.inertAttach(attach);
		}
		
		return attach;
	}
	
	/**
	 * 교육결과자료 업로드
	 * @param request
	 * @param eduSeq
	 * @return
	
	@Override
	public LectureAttach uploadFile(MultipartHttpServletRequest request, Integer eduSeq,String fileSection) {
		
		boolean isSuccess = false;
		Iterator<?> itr =  request.getFileNames();
		File file = null;
		String fileRename = "";
		String orgNm = "";
		String fileType = "";
		LectureAttach attach = null;
		
		
		File dir = new File(EDU_ATTACH_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		if(itr.hasNext()) {
			List<MultipartFile> list = request.getFiles((String) itr.next());
			for(MultipartFile multipartFile : list) {
				orgNm = multipartFile.getOriginalFilename();
				try {
					orgNm = new String(orgNm.getBytes("8859_1"),"utf-8");
				} catch (UnsupportedEncodingException e) {
					LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
					isSuccess = false;
				}
				fileType = orgNm.substring(orgNm.lastIndexOf(".") + 1, orgNm.length());
				fileRename = System.currentTimeMillis() + "." + fileType;
				file = new File( EDU_ATTACH_PATH  + fileRename );
				try {
					multipartFile.transferTo(file);
				} catch (IllegalStateException e) {
					LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
				} catch (IOException e) {
					LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
				}	
			}
			isSuccess = true;
		}
		
		if(isSuccess){
			attach = new LectureAttach(
					MaxNumUtil.getSequence(MaxNumUtil.FUNC_FILE), 
					eduSeq, 
					fileSection, 
					orgNm, 
					fileRename, 
					fileType, 
					(int) file.length()
				);
			attachMapper.inertAttach(attach);
		}
		
		return attach;
	}
 */
	/**
	 * 교육 결과자료 삭제
	 * @param fileSeq
	 */
	@Override
	public void deleteAttach(Integer fileSeq) {
		LectureAttach attach = attachMapper.getAttach(fileSeq);
		if(attach != null){
			FileUtil.delete(EDU_ATTACH_PATH+attach.getEduSeq()+"/"+attach.getFileRename());
			//NaverObjectStorage.delete(EDU_ATTACH_PATH+attach.getEduSeq()+"/", attach.getFileRename());
			attachMapper.deleteAttach(fileSeq);
		}	
	}

	/**
	 * 교육결과자료 목록 조회
	 * @param eduSeq
	 * @return
	 */
	@Override
	public List<LectureAttach> getAttachList(int eduSeq,String fileSection) {
		LectureAttach la = new LectureAttach();
		la.setEduSeq(eduSeq);
		la.setFileSection(fileSection);
		return attachMapper.selectAttachList(la);
	}

	/**
	 * 교육 결과자료 조회
	 * @param fileSeq
	 * @return
	 */
	@Override
	public LectureAttach getAttach(Integer fileSeq) {
		return attachMapper.getAttach(fileSeq);
	}

	@Override
	public boolean isAttach(Integer eduSeq, String fileSection) {
		LectureAttach la = new LectureAttach();
		la.setEduSeq(eduSeq);
		la.setFileSection(fileSection);
		if(attachMapper.selectAttachCnt(la)==0){
			return false;
		}
		
		return true;
	}
	
}

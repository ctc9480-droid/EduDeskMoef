package com.educare.edu.tmpFile.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.tmpFile.service.TempFileService;
import com.educare.edu.tmpFile.service.model.TempFile;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.MaxNumUtil;

/**
 * @Class Name : TempFileServiceImpl.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 10.
 * @version 1.0
 * @see
 * @Description 임시파일 서비스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 10.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Service("TempFileService")
public class TempFileServiceImpl implements TempFileService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(TempFileServiceImpl.class.getName());
	
	/** 임시파일 Mapper */
	@Resource(name="TempFileMapper")
	private TempFileMapper tempFileMapper;

	/**
	 * 임시파일을 저장한다.
	 * @param request
	 * @return
	 */
	@Override
	public TempFile setTempFile(MultipartHttpServletRequest request) {
		
		boolean isSuccess = false;
		Iterator<?> itr =  request.getFileNames();
		File file = null;
		String fileRename = "";
		String orgNm = "";
		String fileType = "";
		TempFile tempFile = null;
		
		FileUtil.deleteOldDirectory(new File(TEMP_PATH));
		File dir = new File(TEMP_UPLOAD_PATH);
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
				fileRename = "temp_" + System.currentTimeMillis() + "." + fileType;
				file = new File( TEMP_UPLOAD_PATH + "/" + fileRename );
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
			if(file != null){
				tempFile = new TempFile(
						MaxNumUtil.getSequence(MaxNumUtil.FUNC_FILE), 
						orgNm, 
						fileRename, 
						fileType, 
						(int) file.length()
						);
				
				tempFileMapper.insertTempFile(tempFile);
			}
		}
		
		return tempFile;
	}

	/**
	 * 임시파일을 삭제한다.
	 * @param fileSeq
	 */
	@Override
	public void deleteTempFile(Integer fileSeq) {
		TempFile tempFile = tempFileMapper.getTempFile(fileSeq);
		if(tempFile != null){
			//FileUtil.delete(TEMP_FILE_PATH + "/" + DateUtil.getDate2Str(new Date(), "yyyyMMdd") + "/" + tempFile.getFileRename());
			//임시파일올릴대는 날짜폴더 없어서 삭제할때도 없앰
			FileUtil.delete(TEMP_UPLOAD_PATH + "/" + tempFile.getFileRename());
			tempFileMapper.deleteTempFile(fileSeq);
		}
	}

	/**
	 * 임시파일을 조회한다.
	 * @param fileSeq
	 * @return
	 */
	@Override
	public TempFile getTempFile(Integer fileSeq) {
		return tempFileMapper.getTempFile(fileSeq);
	}
}

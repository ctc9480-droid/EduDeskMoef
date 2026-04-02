package com.educare.edu.tmpFile.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.tmpFile.service.model.TempFile;
import com.educare.util.ConfigHandle;
import com.educare.util.XmlBean;

/**
 * @Class Name : TempFileService.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 10.
 * @version 1.0
 * @see
 * @Description 임시파일 서비스 인터페이스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 10.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public interface TempFileService {
	
	//public static String TEMP_FILE_PATH = XmlBean.getServerContextRoot() + "/tempFile";
	public static String TEMP_PATH = ConfigHandle.getProperties("lnc.server.tmproot");
	public static String TEMP_UPLOAD_PATH = ConfigHandle.getProperties("lnc.server.tmproot")+"upload/";
	public static String TEMP_DOWNLOAD_PATH = ConfigHandle.getProperties("lnc.server.tmproot")+"download/";
	
	/**
	 * 임시파일을 저장한다.
	 * @param request
	 * @return
	 */
	public TempFile setTempFile(MultipartHttpServletRequest request);
	
	/**
	 * 임시파일을 삭제한다.
	 * @param fileSeq
	 */
	public void deleteTempFile(Integer fileSeq);
	
	/**
	 * 임시파일을 조회한다.
	 * @param fileSeq
	 * @return
	 */
	public TempFile getTempFile(Integer fileSeq);
}

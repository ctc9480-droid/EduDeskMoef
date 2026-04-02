package com.educare.edu.education.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.education.service.model.LectureAttach;
import com.educare.util.XmlBean;

/**
 * @Class Name : AttachService.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 6.
 * @version 1.0
 * @see
 * @Description 교육결과 첨부파일 서비스 인터페이스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 6.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public interface AttachService {
	
	public static final String EDU_ATTACH_PATH = "upload/lctre/";
	
	/**
	 * 교육결과자료 업로드
	 * @param request
	 * @param eduSeq
	 * @return
	 */
	public LectureAttach uploadFile(MultipartHttpServletRequest request, Integer eduSeq,String fileSection);
	
	/**
	 * 교육 결과자료 삭제
	 * @param fileSeq
	 */
	public void deleteAttach(Integer fileSeq);
	
	/**
	 * 교육자료 목록 조회
	 * @param eduSeq
	 * @return
	 */
	public List<LectureAttach> getAttachList(int eduSeq,String fileSection);
	
	/**
	 * 교육 결과자료 조회
	 * @param fileSeq
	 * @return
	 */
	public LectureAttach getAttach(Integer fileSeq);

	/**
	 * 교육자료가 존재하는지 체크한다.
	 * @param eduSeq
	 * @param fileSection
	 * @return
	 */
	public boolean isAttach(Integer eduSeq, String fileSection);
}

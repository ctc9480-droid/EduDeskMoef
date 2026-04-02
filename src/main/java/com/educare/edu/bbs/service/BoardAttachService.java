
package com.educare.edu.bbs.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.service.model.BoardAttach;

/**
 * @Class Name : BoardAttachService.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 첨부파일 서비스 인터페이스
 * 
 * <pre>
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
public interface BoardAttachService {
	List<BoardAttach> getBoardAttachList(BoardAttach attachVo);

	BoardAttach getBoardAttachMap(Integer fileSeq);

	/**
	 * 첨부파일 등록
	 * @param attachVo
	 * @return
	 */
	int boardAttachWriteProc(MultipartHttpServletRequest mhsr);

	int boardAttachDeleteProc(int fileSeq);
	
}

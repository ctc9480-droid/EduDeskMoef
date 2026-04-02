
package com.educare.edu.bbs.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.service.model.Board;
import com.educare.edu.bbs.service.model.BoardAttach;
import com.educare.edu.bbs.service.model.BoardMst;

/**
 * @Class Name : BoardService.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 서비스 인터페이스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
public interface BoardService {

	/**
	 * <pre>
	 * 기본
	 * boardType
	 * 
	 * 검색어
	 * searchStatus:0=미게시,1=게시
	 * searchSelect
	 * searchWord
	 * 
	 * 페이징
	 * firstRecordIndex:페이지 번호
	 * recordCountPerPage:목록 갯수
	 * </pre>
	 * @param Board
	 * @return List<Board>
	 */
	List<Board> getBoardList(Board vo);
	
	/**
	 * 게시글 등록,수정
	 * @param vo
	 * @param attachVo
	 * @return
	 */
	int boardWriteProc(Board vo);

	Board getBoardMap(Board vo);

	int getBoardTotalCnt(Board vo);

	int boardDeleteProc(Board vo);

	void setBoardHit(Board vo);

	int checkMyBoard(Board vo);

	int setBoardInvisible(Board vo);
	
	/**
	 * <pre> 
	 * </pre>
	 * @param boardType (notice:공지사항,free:자유,recs:자료실)
	 * @param recordCountPerPage (목록갯수)
	 * @return
	 */
	List<Board> getBoardMiniList(String boardType, int recordCountPerPage);

	/**
	 * 마스터게시판 정보 가져오기
	 * @param boardType
	 * @return
	 */
	BoardMst getBoardMstMap(String boardType);
	
	/**
	 * 미니게시판 통합
	 * @param strings
	 * @param recordCountPerPage
	 * @return
	 */
	List<Board> getBoardMiniList(String[] strings, int recordCountPerPage);

	/**
	 * 멀티파트등록 서비스
	 * @param vo
	 * @param mhsr
	 * @return
	 */
	int boardWriteProcMultipart(int idx,String boardType, MultipartHttpServletRequest mhsr);
	
	/**
	 * 썸네일 삭제
	 * @param idx
	 * @return
	 */
	int deleteThumbFile(int idx);
	
	List<Board> getNoticeList(Board vo);

}

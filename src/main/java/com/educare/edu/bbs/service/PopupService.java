
package com.educare.edu.bbs.service;

import java.util.List;

import com.educare.edu.bbs.service.model.Popup;

/**
 * @Class Name : PopupService.java
 * @author  SI개발팀 강병주
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
 * 2020. 7. 1.	  	 SI개발팀 강병주     		최초생성 
 * </pre>
 */
public interface PopupService {

	List<Popup> getPopupList(Popup vo);

	int popupWriteProc(Popup vo);

	Popup getPopupMap(Popup vo);

	int getPopupTotalCnt(Popup vo);
	
	/**
	 * <pre>
	 * 관리자에서 팝업을 여러개 한번에 삭제 할때
	 * 영구삭제
	 * </pre>
	 * @return int
	 */
	int setPopupDeleteListProc(Integer[] idxs);

}

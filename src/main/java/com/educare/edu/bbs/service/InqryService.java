
package com.educare.edu.bbs.service;

import java.util.List;

import com.educare.edu.bbs.service.model.Inqry;
import com.educare.edu.comn.vo.ResultVO;

/**
 * @Class Name : InqryService.java
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
public interface InqryService {

	List<Inqry> getInqryList(Inqry vo);

	int setInqryWriteProc(Inqry vo);

	Inqry getInqryMap(Inqry vo);

	int getInqryTotalCnt(Inqry vo);

	ResultVO setInqryDeleteProc(Inqry vo);

	ResultVO setInqryInvisible(Inqry vo);

	ResultVO checkMyInqry(Inqry vo,Inqry inqryMap);
	
	List<Inqry> getInqryMiniList(int recordCountPerPage);

}

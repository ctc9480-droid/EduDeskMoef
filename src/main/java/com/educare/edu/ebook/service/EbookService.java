
package com.educare.edu.ebook.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.service.model.Banner;
import com.educare.edu.bbs.service.model.Popup;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.ebook.service.model.Ebook;

public interface EbookService {

	List<Ebook> getEbookList(Ebook vo);

	int ebookWriteProc(Ebook vo);

	Ebook getEbookMap(Ebook vo);

	int getEbookTotalCnt(Ebook vo);

	int ebookFileProc(MultipartHttpServletRequest mhsr);

	ResultVO getPdf(Ebook vo);

}

package com.educare.edu.ebook.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.service.model.Banner;
import com.educare.edu.bbs.service.model.Board;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.ebook.service.EbookService;
import com.educare.edu.ebook.service.model.Ebook;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


@Controller
@RequestMapping(value="/user/ebook/")
public class EbookUserController {
	@Resource(name="EbookService")
	private EbookService ebookService;
	
	/** 배너관리
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("ebookList.do")
	public String ebookList(ModelMap model ,@ModelAttribute Ebook vo
			) {
		int totalCnt = ebookService.getEbookTotalCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPageNo());
        paginationInfo.setRecordCountPerPage(8);
        paginationInfo.setPageSize(10);
		
        vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        vo.setSearchStatus(1);
		List<Ebook> ebookList = ebookService.getEbookList(vo);
		
		model.addAttribute("ebookList",ebookList);
		model.addAttribute("pageNavi",paginationInfo);
		model.addAttribute("vo",vo);
        return "/user/ebook/ebookList" + LncUtil.TILES;
    }
	@ResponseBody
	@RequestMapping("getPdf.json")
	public ResultVO getPdf(ModelMap model ,@ModelAttribute Ebook vo
			) {
		ResultVO result = ebookService.getPdf(vo);
        return result;
    }
	
}

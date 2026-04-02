package com.educare.edu.ebook.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.service.model.Banner;
import com.educare.edu.bbs.service.model.Board;
import com.educare.edu.ebook.service.EbookService;
import com.educare.edu.ebook.service.model.Ebook;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


@Controller
@RequestMapping(value="/admin/ebook/")
public class EbookAdminController {
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
        paginationInfo.setRecordCountPerPage(10);
        paginationInfo.setPageSize(10);
		
        vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		List<Ebook> ebookList = ebookService.getEbookList(vo);
		
		model.addAttribute("ebookList",ebookList);
		model.addAttribute("pageNavi",paginationInfo);
		model.addAttribute("vo",vo);
        return "/admin/ebook/ebookList" + LncUtil.TILES;
    }
	@RequestMapping("ebookWrite.do")
	public String ebookWrite(ModelMap model ,@ModelAttribute Ebook vo
			) {
		Ebook ebookMap = ebookService.getEbookMap(vo);
		
		model.addAttribute("ebookMap",ebookMap);
		model.addAttribute("vo",vo);
        return "/admin/ebook/ebookWrite" + LncUtil.TILES;
    }
	@RequestMapping(value="ebookWriteProc.json")
	public String ebookWriteProc(ModelMap model 
			,@ModelAttribute Ebook vo
			) {
		
		int resultCode = ebookService.ebookWriteProc(vo);
		model.addAttribute("result",resultCode);
		model.addAttribute("idx",vo.getIdx());
		return "jsonView";
	}
	@RequestMapping(value="ebookFileProc.json")
	public String ebookFileProc(ModelMap model 
			,MultipartHttpServletRequest mhsr
			) {
		
		int resultCode = ebookService.ebookFileProc(mhsr);
		model.addAttribute("result",resultCode);
		return "jsonView";
	}
}

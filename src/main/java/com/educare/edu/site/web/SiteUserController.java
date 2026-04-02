package com.educare.edu.site.web;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.educare.component.UtilComponent;
import com.educare.edu.bbs.service.BannerService;
import com.educare.edu.bbs.service.BoardService;
import com.educare.edu.bbs.service.PopupService;
import com.educare.edu.bbs.service.model.Banner;
import com.educare.edu.bbs.service.model.Board;
import com.educare.edu.bbs.service.model.Popup;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

/**
 * @Class Name : SiteUserController.java
 * @author SI개발팀 박용주
 * @since 2020. 5. 26.
 * @version 1.0
 * @see
 * @Description 사용자 사이트 컨트롤러
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 5. 26.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Controller
public class SiteUserController {
	
	/** 게시판 서비스 */
	@Resource(name = "BoardService")
	private BoardService boardService;
	
	/** 교육관리 서비스 */
	@Resource(name = "EduService")
	private EduService eduService;
	
	//배너서비스
	@Resource(name = "BannerService")
	private BannerService bannerService;
	@Resource(name = "PopupService")
	private PopupService popupService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	/**
	 * index.do
	 * @return
	 */
	@RequestMapping("/index.do")
	public String index() {
        return "redirect:/user/index.do";
    }
	
	/**
	 * 사용자 메인페이지
	 * @return
	 */
	@RequestMapping("/user/index.do")
	public String userIndex(ModelMap model) {
		
		//미니게시판
		List<Board> noticeList = boardService.getBoardMiniList("notice", 5);
		model.addAttribute("noticeList", noticeList);
		List<Board> recsList = boardService.getBoardMiniList("recs", 5);
		model.addAttribute("recsList", recsList);
		//List<Board> recsChildList = boardService.getBoardMiniList(new String[]{"recsChild","recsInstrctr","recsCenterList"}, 5);
		//model.addAttribute("recsChildList", recsChildList);
		
		int srchCtgry = 0;
		List<Lecture> lctreList1 = eduService.getLctreMiniList(srchCtgry);
		model.addAttribute("lctreList1", lctreList1);
		
		List<Banner> bannerList = bannerService.getBannerListMain();
		model.addAttribute("bannerList", bannerList);
		
		Popup vo = new Popup();
		vo.setSearchNowDtime(DateUtil.getDate2Str(Calendar.getInstance().getTime(), "yyyyMMddHHmmssSSS"));
		vo.setSearchStatus(1);
		List<Popup> popupList = popupService.getPopupList(vo);
		model.addAttribute("popupList", popupList);
		
		
		return "index";
    }
	
	/**
	 * html 페이지
	 * @param pakageNm
	 * @param contentsId
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/{pakageNm}/contents/{contentsId}.do")
	public String contentsView(
			@PathVariable( "pakageNm" ) String pakageNm,
			@PathVariable( "contentsId" ) String contentsId,
			ModelMap model) {

		return "/user/" + pakageNm + "/contents/" + contentsId + LncUtil.TILES;
	}
}

package com.educare.edu.education.web;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.educare.component.UtilComponent;
import com.educare.edu.comn.mapper.LectureTimeStdntMapper;
import com.educare.edu.comn.model.LectureTimeStdnt;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.comn.vo.TimeTableVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.lectureBoard.service.LectureBoardService;
import com.educare.edu.lectureBoard.vo.LectureBoard;
import com.educare.edu.lectureBoard.vo.LectureBoardVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping(value="/user/mypage/popup/")
public class MypagePopupController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(MypagePopupController.class.getName());
	
	/** 교육관리 서비스 */
	@Resource(name = "EduService")
	private EduService eduService;
	
	
	@Resource(name="LectureBoardService")
	private LectureBoardService lectureBoardService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	@Resource(name = "LectureTimeStdntMapper")
	private LectureTimeStdntMapper lectureTimeStdntMapper;
	@Resource(name = "SmartcheckService")
	private SmartcheckService smartcheckService;
	
	/**
	 * 마이페이지 내 강의실 팝업
	 * @param model
	 * @param gubun
	 * @param vo
	 * @param tabNum
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("myEdu{gubun}View.do")
	public String popupMyEduView(ModelMap model,HttpServletResponse res
			,@PathVariable String gubun
			,@ModelAttribute("vo") EduVO vo
			,@RequestParam(defaultValue="1") String tabNum
			,@RequestParam(defaultValue="1") String tabAction
			,int eduSeq
			) throws Exception {
		String userId = SessionUserInfoHelper.getUserId();
		
		Lecture lctre = eduService.getLctreDetail(vo.getEduSeq());
		model.addAttribute("lctre", lctre);
		
		vo.setUserId(userId);
		LectureStdnt stdnt = eduService.getMyAtnlcDetail(eduSeq,userId);
		model.addAttribute("stdnt", stdnt);
		
		LectureTimeStdnt p = new LectureTimeStdnt();
		p.setEduSeq(stdnt.getEduSeq());
		p.setUserId(stdnt.getUserId());
		List<LectureTimeStdnt> list2 = lectureTimeStdntMapper.selectByEduSeqUserId(p);
		stdnt.setLtsInfo(list2);
		
		List<SmartCheckVO> attendStdntList = smartcheckService.getStdntAttList(vo.getEduSeq(), userId);
		model.addAttribute("attendStdntList", attendStdntList);
		
		List<TimeTableVO> timeList = eduService.getTimeTableList(lctre,userId);
		model.addAttribute("timeList", timeList);
		
		//오프라인출석부통해 출석정보 세팅하기
		if(lctre.getLctreType() == 0 || lctre.getLctreType() == 2){
			ResultVO result = smartcheckService.getRollBookDay(eduSeq, userId);
			model.addAttribute("rollbook",result.getData());
		}
		
		//과목수료증
		if("3".equals(tabNum)){
			ResultVO result = eduService.getLctreSubResult(eduSeq,userId);
			model.addAttribute("subData",result.getData());
		}
		
		model.addAttribute("vo", vo);
		model.addAttribute("tabNum", tabNum);
		model.addAttribute("tabAction", tabAction);
        return "/user/mypage/popup/myEduView" + LncUtil.TILES;
    }
	
	@RequestMapping("myEduView_inc_{tabAction}.do")
	public String myEduView_inc_1(ModelMap model
			,@ModelAttribute("vo") EduVO vo
			,@PathVariable String tabAction
			) {
		
		return "/user/mypage/popup/myEduView_inc_"+tabAction;
	}
	
	////////////////강사시작///////////////////////////////////////////////////
	@RequestMapping("instrctrEdu{gubun}View.do")
	public String popupInstrctrEduView(ModelMap model,HttpServletResponse res
			,@PathVariable String gubun
			,@ModelAttribute("vo") EduVO vo
			,@RequestParam(defaultValue="1") String tabNum
			,@RequestParam(defaultValue="1") String tabAction
			) throws Exception {
		
		String jspPath = "/user/mypage/popup/instrctrEduView" + LncUtil.TILES;
		Lecture lctre = eduService.getLctreDetail(vo.getEduSeq());
		
		int eduSeq = vo.getEduSeq();
		
		//권한번호
		int authLv = 0;//0:권한없음, 1:대표강사, 2:시간강사
		
		//시간표
		List<TimeTableVO> timeList = eduService.getTimeTableList(lctre,null);
		
		String userId = SessionUserInfoHelper.getUserId();
		//대표강사 체크
		if(!lctre.getInstrctrId().equals(userId)){
			
			//시간강사체크
			boolean isInstr = false;
			for(TimeTableVO o : timeList){
				String checkId = LncUtil.replaceNull(o.getCheckId());
				if(checkId.equals(userId)){
					isInstr = true;
					authLv = 2;
				}
			}
			if(!isInstr){
				//model.addAttribute("rtnMsg","에러");
				//return jspPath;
				LncUtil.alertClose(res, "잘못된 경로로 입장하셨습니다.");
				return null;
			}
		}else{
			authLv = 1;
		}
		
		//출석률 가져오기
		double attRatio = 0;
		if(lctre.getLctreType() == 0){
			attRatio = smartcheckService.getAttendRatio(eduSeq,1);
		}else{
			attRatio = smartcheckService.getAttendRatio(eduSeq,0);
		}
		model.addAttribute("attRatio",attRatio);

		model.addAttribute("lctre", lctre);
		model.addAttribute("timeList", timeList);
		model.addAttribute("vo", vo);
		model.addAttribute("tabNum", tabNum);
		model.addAttribute("tabAction", tabAction);
		model.addAttribute("authLv", authLv);
		return jspPath;
    }
	@RequestMapping("instrctrEduView_inc_1.do")
	public String instrctrEduView_inc_1(ModelMap model
			,@ModelAttribute("vo") EduVO vo
			) {
		return "/user/mypage/popup/instrctrEduView_inc_1";
	}
	@RequestMapping("instrctrEduView_inc_2.do")
	public String instrctrEduView_inc_2(ModelMap model
			) {
		
		return "/user/mypage/popup/instrctrEduView_inc_2";
	}
	@RequestMapping("instrctrEduView_inc_3.do")
	public String instrctrEduView_inc_3(ModelMap model
			) {
		
		return "/user/mypage/popup/instrctrEduView_inc_3";
	}
	@RequestMapping("instrctrEduView_inc_testList.do")
	public String instrctrEduView_inc_testList(ModelMap model
			) {
		
		return "/user/mypage/popup/instrctrEduView_inc_testList";
	}
}

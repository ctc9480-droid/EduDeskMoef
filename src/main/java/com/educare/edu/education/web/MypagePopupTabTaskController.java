package com.educare.edu.education.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.UtilComponent;
import com.educare.edu.bbs.BbsConstant;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.lectureBoard.service.LectureBoardService;
import com.educare.edu.lectureBoard.vo.LectureBoardVO;
import com.educare.edu.lectureTask.mapper.LectureTaskMapper;
import com.educare.edu.lectureTask.service.LectureTaskService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.FileUtil;

@Controller
@RequestMapping(value="/user/mypage/popup/")
public class MypagePopupTabTaskController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(MypagePopupTabTaskController.class.getName());
	
	/** 교육관리 서비스 */
	@Resource(name = "EduService")
	private EduService eduService;
	@Resource(name="LectureBoardService")
	private LectureBoardService lectureBoardService;
	@Resource(name="LectureTaskService")
	private LectureTaskService lectureTaskService;
	@Resource(name="LectureTaskMapper")
	private LectureTaskMapper lectureTaskMapper;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	@ResponseBody 
	@RequestMapping(value="taskDropzoneUpload.json",produces = "application/json;charset=UTF-8")
	public ResultVO taskDropzoneUpload(ModelMap model
			,MultipartHttpServletRequest mhsr
			) {
		ResultVO result = new ResultVO();
		result.setResult(0);
		result.setMsg("제출중 오류가 발생하였습니다.");
		int resultCode = lectureTaskService.taskAttachWriteProc(mhsr);
		if(resultCode!=0) {
			result.setMsg("제출 완료 되었습니다.");
			result.setResult(1);
		} 
		
		return result;
	}
	@RequestMapping("instrctrEduView_inc_taskList.do")
	public String instrctrEduView_inc_taskList(ModelMap model
			,@ModelAttribute LectureBoardVO vo
			) {
		return "/user/mypage/popup/instrctrEduView_inc_taskList";
	}
	@RequestMapping("taskDownload.do")
	public void taskDownload(
			@RequestParam Map<String, String> param,
			HttpServletRequest request,
			HttpServletResponse response
			) { 
		//로그인 된상태로 되게 리폼 
		if(!SessionUserInfoHelper.isLogined()){
			return;
		}
		
		if("9".equals(SessionUserInfoHelper.getUserMemLvl())){//학생일 경우
			param.put("userId",SessionUserInfoHelper.getUserId());
		}
		
		Map<String, String> fileMap = lectureTaskMapper.selectLectureTaskFileMap(param);
		
		//학생정보 가져오기
		String prefixFileName = fileMap.get("user_nm")+"_";
		
		String taskFileOrg = fileMap.get("task_file_org");
		String taskFileRename = fileMap.get("task_file_rename");
		String subPath = "upload/lectureTask/";//스토리지에 경로 
		String filePath = subPath+taskFileRename;//로컬 파일 경로
		FileUtil.download(filePath, prefixFileName+taskFileOrg, request, response);
		
	}
	@RequestMapping(value="deleteLectureTaskFile.ajax")
	public String deleteFile(ModelMap model
			,@RequestParam Map<String, String> param
			) {
		if("9".equals(SessionUserInfoHelper.getUserMemLvl())){//학생일 경우
			param.put("userId",SessionUserInfoHelper.getUserId());
		}
		int resultCode = lectureTaskService.lectureTaskDeleteProc(param);
		model.addAttribute("resultCode", resultCode);	
		return "jsonView";
	}
}

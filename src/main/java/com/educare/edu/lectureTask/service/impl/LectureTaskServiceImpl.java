package com.educare.edu.lectureTask.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.BbsConstant;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.lectureBoard.vo.LectureBoardAttach;
import com.educare.edu.lectureBoard.vo.LectureBoardVO;
import com.educare.edu.lectureTask.mapper.LectureTaskMapper;
import com.educare.edu.lectureTask.service.LectureTaskService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;

@Service("LectureTaskService")
public class LectureTaskServiceImpl implements LectureTaskService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LectureTaskServiceImpl.class.getName());

	@Resource(name="LectureTaskMapper")
	private LectureTaskMapper lectureTaskMapper;
	
	@Override
	public int taskAttachWriteProc(MultipartHttpServletRequest mhsr) {
		
		ResultVO result = new ResultVO();
		try {
			//int boardIdx = LncUtil.nvlInt(mhsr.getParameter("idx"));
			Integer eduSeq =LncUtil.nvlInt(mhsr.getParameter("eduSeq"));
			
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			if(user == null){
				return 4;
			}
			
			String userId = user.getUserId();
			String boardType = "eduTask";
			String folderName = "upload/lectureTask/";
			
			
			Map <String, MultipartFile > paramMap = mhsr.getFileMap ();
			Iterator itr = paramMap.keySet().iterator();
			while (itr.hasNext()) {
				MultipartFile mf = paramMap.get((String) itr.next());
				String orgName = LncUtil.getEncode(mf.getOriginalFilename());
				
				String fileRename = FileUtil.getFileRename(orgName, boardType+"_"+eduSeq+"_");
				//tempFile에 있는 파일을 스토리지에 업로드함
				//int result_nos=NaverObjectStorage.multiPartupload(mf, folderName, orgName, fileRename);
				result = FileUtil.multiPartupload(mf, folderName, orgName, fileRename);
				if (result.getResult()==1) {
					lectureTaskMapper.updateTaskAttach( 
							new LectureStdnt(
									eduSeq 
									,userId
									,orgName
									,fileRename
									)
					);
				} else {
					return 4; 
				}
			}
			result.setResult(1);
		} catch (NullPointerException e) {
			LOG.debug(e.getMessage());
			return 4;
		}
		return result.getResult();
	}

	@Override
	public List<LectureBoardVO> getLectureBoardList(LectureBoardVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lectureTaskDeleteProc(Map<String, String> param) {
		int result = 0;
		try {
			
			//파일정보가져오기
			Map<String, String> fileMap = lectureTaskMapper.selectLectureTaskFileMap(param);
			String fileRename = fileMap.get("task_file_rename");
			String folderName = "upload/lectureTask/";
			
			//스토리지에서 삭제
			//NaverObjectStorage.delete(folderName, fileRename);
			FileUtil.delete(folderName+ fileRename);
			
			//테이블에서 삭제
			lectureTaskMapper.updateLectureTaskFile(param);
			result = 1;
		} catch (NullPointerException e) {
			return 5;
		}
		return result;
	}


	
}

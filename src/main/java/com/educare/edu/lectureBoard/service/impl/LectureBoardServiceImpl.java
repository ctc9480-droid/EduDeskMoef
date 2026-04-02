package com.educare.edu.lectureBoard.service.impl;

import java.util.Calendar;
import java.util.HashMap;
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
import com.educare.edu.bbs.service.model.BoardAttach;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.lectureBoard.mapper.LectureBoardAttachMapper;
import com.educare.edu.lectureBoard.mapper.LectureBoardMapper;
import com.educare.edu.lectureBoard.service.LectureBoardService;
import com.educare.edu.lectureBoard.vo.LectureBoardAttach;
import com.educare.edu.lectureBoard.vo.LectureBoardVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.MaxNumUtil;

@Service("LectureBoardService")
public class LectureBoardServiceImpl implements LectureBoardService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LectureBoardServiceImpl.class.getName());

	@Resource(name = "LectureBoardMapper")
	private LectureBoardMapper lectureBoardMapper;
	@Resource(name = "LectureBoardAttachMapper")
	private LectureBoardAttachMapper lectureBoardAttachMapper;

	@Override
	public List<LectureBoardVO> getLectureBoardList(LectureBoardVO vo){
		List<LectureBoardVO> boardList = lectureBoardMapper.getLectureBoardList(vo);
	    return boardList;
	}
	@Override
	public int getLectureBoardTotalCnt(LectureBoardVO vo) {
		return lectureBoardMapper.getLectureBoardTotalCnt(vo);
	} 
	@Override
	public LectureBoardVO getLectureBoardByIndex(int idx) {
		return lectureBoardMapper.getLectureBoardByIndex(idx);
	}
	@Override
	public int getLectureCommunityTotalCnt(LectureBoardVO vo) {
		// TODO Auto-generated method stub
		return lectureBoardMapper.getLectureCommunityTotalCnt(vo);
	}
	@Override
	public List<LectureBoardVO> getLectureCommunityList(LectureBoardVO vo) {
		
		List<LectureBoardVO> boardList = lectureBoardMapper.getLectureCommunityList(vo);
	    return boardList;
	} 
	@Override
	public LectureBoardVO getLectureCommunityByIndex(int idx) {
		return lectureBoardMapper.getLectureBoardByIndex(idx);
	}
	@Override 
	public ResultVO saveLectureBoard(LectureBoardVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String userId = SessionUserInfoHelper.getUserId();
			vo.setRegId(userId);
			vo.setRegNm(SessionUserInfoHelper.getUserNm());
			vo.setRegDtime(DateUtil.getDate2Str(Calendar.getInstance().getTime(),"yyyyMMddHHmmssSSS"));
			vo.setModId(userId);
			vo.setModNm(SessionUserInfoHelper.getUserNm());
			vo.setModDtime(DateUtil.getDate2Str(Calendar.getInstance().getTime(),"yyyyMMddHHmmssSSS"));
			
			//저장및 수정
		    LectureBoardVO info = lectureBoardMapper.getLectureBoardByIndex(vo.getIdx());
		    if(info!=null){
		    	//본인글 체크
		    	if(!info.getRegId().equals(userId)){
		    		result.setMsg("본인글만 수정 가능합니다.");
		    		result.setResult(-1);
					return result;
		    	}
		    	
		    	//수정
		    	int updateResult = lectureBoardMapper.updateLectureBoard(vo);
		    }else{
		    	//등록
		    	int updateResult = lectureBoardMapper.insertLectureBoard(vo);
		    }
		    rstData.put("idx", vo.getIdx());
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("시스템 오류입니다. 관리자에게 문의하세요");
			result.setResult(0);
			return result;
		}
	} 
	@Override
	public int communityDeleteProc(int tabNum6Idx, String regId, String regNm) {
		return lectureBoardMapper.deleteCommunityByIndex(tabNum6Idx, regId, regNm);
	}
	@Override
	public void tabBoardHitPlusByIndex(int tabNum6Idx,String regId,String userId) {
		
		if(regId.equals(userId)){
			return;
		}
		
		lectureBoardMapper.updateTabBoardHitPlusByIndex(tabNum6Idx);
	}
	@Override
	public int lectureBoardAttachWriteProc(MultipartHttpServletRequest mhsr) {

		ResultVO result = new ResultVO();
		try {
			int boardIdx = LncUtil.nvlInt(mhsr.getParameter("idx"));
			int eduSeq = LncUtil.nvlInt(mhsr.getParameter("eduSeq"));
			String folderName = "upload/lectureBoard/";
			
			Map <String, MultipartFile > paramMap = mhsr.getFileMap ();
			Iterator itr = paramMap.keySet().iterator();
			while (itr.hasNext()) {
				MultipartFile mf = paramMap.get((String) itr.next());
				String orgName = LncUtil.getEncode(mf.getOriginalFilename());
				
				String fileRename = FileUtil.getFileRename(orgName, eduSeq+"_"+boardIdx+"_");
				//tempFile에 있는 파일을 스토리지에 업로드함
				//int result_nos=NaverObjectStorage.multiPartupload(mf, folderName, orgName, fileRename);
				result = FileUtil.multiPartupload(mf, folderName, orgName, fileRename);
				if (result.getResult()==1) {
					lectureBoardAttachMapper.insertLectureBopardAttach(
							new LectureBoardAttach(
									MaxNumUtil.getSequence(MaxNumUtil.FUNC_FILE)
									, boardIdx
									, "01"
									, orgName
									, fileRename
									, mf.getContentType()
									, (int)mf.getSize()
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
	public List<LectureBoardAttach> getLectureBoardAttachList(LectureBoardAttach attachVo) {
		List<LectureBoardAttach> list = lectureBoardAttachMapper.selectLectureBoardAttachList(attachVo);

		// 파일 통계 구하기
		int sumFileSize = 0;
		int sumFileCnt = 0;
		for (LectureBoardAttach map : list) {
			sumFileCnt++;
			sumFileSize += map.getFileSize();
		}
		attachVo.setSumFileCnt(sumFileCnt);
		attachVo.setSumFileSize(sumFileSize);

		return list;
	}
	@Override
	public LectureBoardAttach getLectureBoardAttachMap(Integer fileSeq) {
		LectureBoardAttach map = lectureBoardAttachMapper.selectLectureBoardAttachMap(fileSeq);
		return map;
	}
	@Override
	public int lectureBoardAttachDeleteProc(int fileSeq) {
		int result = 0;
		try {
			
			//파일정보가져오기
			LectureBoardAttach baMap = lectureBoardAttachMapper.selectLectureBoardAttachMap(fileSeq);
			String fileRename = baMap.getFileRename();
			String folderName = "upload/lectureBoard/";
			
			//스토리지에서 삭제
			//NaverObjectStorage.delete(folderName, fileRename);
			FileUtil.delete(folderName+ fileRename);
			
			//테이블에서 삭제
			lectureBoardAttachMapper.deleteLectureBoardAttach(fileSeq);
			result = 1;
		} catch (NullPointerException e) {
			return 5;
		}
		return result;
	}
	@Override
	public ResultVO deleteLectureBoard(int idx,String userId,String userNm) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			LectureBoardVO vo =  new LectureBoardVO();
			vo.setIdx(idx);
			vo.setModId(userId);
			vo.setModNm(userNm);
			vo.setModDtime(DateUtil.getDate2Str(Calendar.getInstance().getTime(),"yyyyMMddHHmmssSSS"));
			
			//검증
			LectureBoardVO board = lectureBoardMapper.getLectureBoardByIndex(idx);
			if(!"1,2,3,4".contains(SessionUserInfoHelper.getUserMemLvl())){
				if(!board.getRegId().equals(userId)){
					result.setMsg("삭제권한이 없습니다.");
					result.setResult(0);
					return result;
				}
			}
			
			//저장및 수정
			vo.setStatus(0);
		    int updateResult = lectureBoardMapper.updateLectureBoardStatus(vo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
}

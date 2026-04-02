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
import com.educare.edu.lectureBoard.mapper.LectureBoardCommentMapper;
import com.educare.edu.lectureBoard.mapper.LectureBoardMapper;
import com.educare.edu.lectureBoard.service.LectureBoardCommentService;
import com.educare.edu.lectureBoard.service.LectureBoardService;
import com.educare.edu.lectureBoard.vo.LectureBoardAttach;
import com.educare.edu.lectureBoard.vo.LectureBoardCommentVO;
import com.educare.edu.lectureBoard.vo.LectureBoardVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.MaxNumUtil;

@Service("LectureBoardCommentService")
public class LectureBoardCommentServiceImpl implements LectureBoardCommentService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LectureBoardCommentServiceImpl.class.getName());

	@Resource(name = "LectureBoardCommentMapper")
	private LectureBoardCommentMapper lectureBoardCommentMapper;
	
	
	@Override
	public ResultVO lectureBoardCommentWriteProc(String comment, int bIdx,int idx) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			if(bIdx==0){
				result.setMsg("시스템 오류입니다. 관리자에게 문의하세요");
				result.setResult(0);
				return result;
			}
			
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			if(user == null){
				result.setMsg("시스템 오류입니다. 관리자에게 문의하세요");
				result.setResult(0);
				return result;
			}
			String userId = user.getUserId();
			String userNm = user.getUserNm();
			LectureBoardCommentVO vo = new LectureBoardCommentVO();
			vo.setContent(comment);
			vo.setbIdx(bIdx);
			vo.setIdx(idx);
			vo.setRegDt(DateUtil.getCalendar().getTime());
			vo.setModDt(DateUtil.getCalendar().getTime());
			vo.setRegId(userId);
			vo.setRegNm(userNm);
			vo.setRegNk(userNm);
			vo.setModId(userId);
			vo.setModNm(userNm);
			
			if(idx>0){
				LectureBoardCommentVO cmnt = lectureBoardCommentMapper.selectLectureBoardCommentMap(vo);
				//본인글체크
				if(!cmnt.getRegId().equals(userId)){
					result.setMsg("본인글이 아닙니다.");
					result.setResult(0);
					return result;
				}
				lectureBoardCommentMapper.updateLectureBoardCommentMap(vo);
			}else{
				lectureBoardCommentMapper.insertLectureBoardCommentMap(vo);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("시스템 오류입니다. 관리자에게 문의하세요");
			result.setResult(0);
			return result;
		}
	}


	@Override
	public List<LectureBoardCommentVO> getLectureBoardCommentList(int bIdx, int status) {
		LectureBoardCommentVO vo = new LectureBoardCommentVO();
		vo.setbIdx(bIdx);
		vo.setStatus(status);
		List<LectureBoardCommentVO> list =  lectureBoardCommentMapper.selectLectureBoardCommentList(vo);
		return list;
	}


	@Override
	public ResultVO lectureBoardCommentDeleteProc(int idx) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			if(idx==0){
				result.setMsg("시스템 오류입니다. 관리자에게 문의하세요");
				result.setResult(0);
				return result;
			}
			
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			if(user == null){
				result.setMsg("시스템 오류입니다. 관리자에게 문의하세요");
				result.setResult(0);
				return result;
			}
			String userId = user.getUserId();
			String userNm = user.getUserNm();
			LectureBoardCommentVO vo = new LectureBoardCommentVO();
			vo.setIdx(idx);
			vo.setStatus(0);
			vo.setModDt(DateUtil.getCalendar().getTime());
			vo.setModId(userId);
			vo.setModNm(userNm);
			
			LectureBoardCommentVO cmnt = lectureBoardCommentMapper.selectLectureBoardCommentMap(vo);
			if(!"1,2,3,4".contains(user.getUserMemLvl())){
				//본인글체크
				if(!cmnt.getRegId().equals(userId)){
					result.setMsg("본인글이 아닙니다.");
					result.setResult(0);
					return result;
				}
			}
			lectureBoardCommentMapper.updateLectureBoardCommentMapByStatus(vo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("시스템 오류입니다. 관리자에게 문의하세요");
			result.setResult(0);
			return result;
		}
	}

}

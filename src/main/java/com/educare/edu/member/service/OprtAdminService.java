package com.educare.edu.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.member.service.model.InstrctrAttach;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoInstrctr;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.util.XmlBean;

public interface OprtAdminService {
	
	public static final int PAGE_SIZE = 10;

	/**
	 * 운영자 게시판 목록
	 * @param vo
	 * @return
	 */
	public ResultVO getOprtBbsList(MemberVO vo);

	/**
	 * 운영자정보 조회
	 * @param userId
	 * @return
	 */
	public UserInfo getOprt(String userId);
	

}

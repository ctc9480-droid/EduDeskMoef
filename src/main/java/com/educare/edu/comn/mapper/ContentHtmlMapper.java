package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.model.ContentHtml;
import com.educare.edu.comn.vo.AdminIpVO;
import com.educare.edu.comn.vo.CnhtmlVO;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.PassCertVO;
import com.educare.edu.education.service.EduVO; 
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.model.UserInfo;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
  
@Mapper("ContentHtmlMapper") 
public interface ContentHtmlMapper {  
	
	List<ContentHtml> selectByParam(CnhtmlVO vo);
	
	void insertByPk(ContentHtml vo);
	
	void updateByPk(ContentHtml vo);

	ContentHtml selectByPk(int cnhtSeq);

	/**
	 * 활성화 컨텐츠 조회
	 * @param cnhtType
	 * @return
	 */
	ContentHtml selectByActive(String cnhtType);
	
} 

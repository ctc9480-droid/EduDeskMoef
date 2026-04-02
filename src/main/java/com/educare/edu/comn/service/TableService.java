package com.educare.edu.comn.service;

import java.util.List;

import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.ResultVO;

/**
 * 테이블 서비스 모음
 */
public interface TableService {
	
	/**
	 * T_ORG 테이블 리스트
	 * @return
	 */
	List<Org> getOrgList();

	OrgVO getOrgInfo(String orgCd);

	CheckVO saveLectureTimeUrl(int eduSeq, int timeSeq, String url, String urlPw);


	
}

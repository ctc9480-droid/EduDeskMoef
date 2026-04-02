package com.educare.edu.cnhtml.service;

import java.util.Date;
import java.util.List;

import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmsVO;

/**
 */
public interface CnhtmlService {

	ResultVO getCnhtmlPageList(String srchWrd, Integer page);

	ResultVO saveCnhtmlProc(int cnhtSeq, String cnhtType,String title, String content, int state);

	ResultVO getCnhtmlData(int cnhtSeq);

	/**
	 * <pre>
	 * 활성화 컨텐츠 불러오기
	 * state:1, cnhtSeq가 제일 큰거 불러와야함
	 * </pre>
	 * @param cnhtTypep
	 * @return
	 */
	ResultVO getCnhtmlDataByActive(String cnhtTypep);

	
}

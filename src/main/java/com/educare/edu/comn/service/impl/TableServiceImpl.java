package com.educare.edu.comn.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.educare.edu.comn.mapper.AttendStdntMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.AttendStdnt;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.member.service.SessionUserInfoHelper;


/**
 *
 *
 */
@Service("TableService")
public class TableServiceImpl implements TableService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(TableServiceImpl.class.getName());

	@Resource(name="TableMapper")
	private TableMapper tableMapper;

	
	@Override
	public List<Org> getOrgList() {
		Org param = new Org();
		List<Org> orgList = tableMapper.selectOrgList(param);
		return orgList;
	}

	@Override
	public OrgVO getOrgInfo(String orgCd) {
		OrgVO org = tableMapper.selectOrgByPk(orgCd);
		return org;
	}

	@Override
	public CheckVO saveLectureTimeUrl(int eduSeq, int timeSeq, String url,String urlPw) {
		CheckVO result = new CheckVO();
		try {
			LectureTime param = new LectureTime();
			param.setEduSeq(eduSeq);
			param.setTimeSeq(timeSeq);
			param.setUrl(url);
			param.setUrlPw(urlPw);
			tableMapper.updateLectureTimeForUrl(param);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	
}

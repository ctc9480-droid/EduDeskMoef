package com.educare.edu.comn.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.mapper.CheckMapper;
import com.educare.edu.comn.mapper.StatMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.StatService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

@Service("StatService")
public class StatServiceImpl implements StatService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(StatServiceImpl.class.getName());

	@Resource(name="StatMapper")
	private StatMapper statMapper;

	@Override
	public int getMonthMain(Map<String, Object> datas) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			
			Date startDtime = null;
			Calendar cal1 = Calendar.getInstance();
			cal1.add(Calendar.MONTH, -6);
			cal1.set(Calendar.DAY_OF_MONTH, 1);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);
			startDtime=cal1.getTime();
			
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			if(user != null){
				param.put("orgCd", user.getOrgCd());
			}
			
			param.put("startDtime", startDtime);
			List<Map<String, Object>> rceptList = statMapper.getRceptMonthMain(param);
			
			datas.put("list", rceptList);
			return 1;
			
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public int getTotalMain(Map<String, Object> datas) {
		try {
			int totalEduCnt=0;//총 교육수
			int totalRceptStdntCnt=0;//총 신청자
			int nowRceptStdntCnt=0;//오늘신청자
			int totalComplStdntCnt=0;//총 수료자
			int playEduCnt=0;//진행중교육
			int rceptEduCnt=0;//접수중교육
			
			Date startDtime = null;
			Calendar cal1 = Calendar.getInstance();
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);
			startDtime=cal1.getTime();
			
			Map<String, Object> param = new HashMap<String, Object>();
			
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			if(user != null){
				param.put("orgCd", user.getOrgCd());
			}
			param.put("startDtime", startDtime);
			
			Map<String, String> eduCntInfo = statMapper.getEduCntInfo(param);
			Map<String, String> rceptStdntCntInfo = statMapper.getRceptStdntCntInfo(param);
			Map<String, String> complStdntCntInfo = statMapper.getComplStdntCntInfo(param);
			
			totalEduCnt= LncUtil.nvlInt(eduCntInfo.get("totalEduCnt"));
			playEduCnt=Integer.parseInt(eduCntInfo.get("playEduCnt"));
			rceptEduCnt=Integer.parseInt(eduCntInfo.get("rceptEduCnt"));
			if(rceptStdntCntInfo != null){
				totalRceptStdntCnt=LncUtil.nvlInt(rceptStdntCntInfo.get("totalRceptStdntCnt"));
				nowRceptStdntCnt=LncUtil.nvlInt(rceptStdntCntInfo.get("nowRceptStdntCnt"));
			}
			totalComplStdntCnt=Integer.parseInt(complStdntCntInfo.get("totalComplStdntCnt"));
			
			datas.put("totalEduCnt", totalEduCnt);
			datas.put("playEduCnt", playEduCnt);
			datas.put("rceptEduCnt", rceptEduCnt);
			datas.put("totalRceptStdntCnt", totalRceptStdntCnt);
			datas.put("nowRceptStdntCnt", nowRceptStdntCnt);
			datas.put("totalComplStdntCnt", totalComplStdntCnt);
			
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public Map<String, Object> getMyStatCount(String userId) {
		try {
			Map<String, Object> result = new HashMap<>();
			
			int playEduCnt = statMapper.getMyStatPlayEduCnt(userId);
			int complEduCnt = statMapper.getMyStatComplEduCnt(userId);
			int rceptEduCnt = statMapper.getMyStatRceptEduCnt(userId);
			
			result.put("playEduCnt", playEduCnt);
			result.put("complEduCnt", complEduCnt);
			result.put("rceptEduCnt", rceptEduCnt);
			return result;
		} catch (NullPointerException e) {
			return null;
		}
	}
}

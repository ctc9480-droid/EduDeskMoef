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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.educare.edu.comn.mapper.CheckMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.LectureTimeStdntMapper;
import com.educare.edu.comn.mapper.PayBackMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.LectureTimeStdnt;
import com.educare.edu.comn.model.OnlineHistory;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.PayBack;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.PayBackService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.CertNumVO;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

@Service("PayBackService")
public class PayBackServiceImpl implements PayBackService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(PayBackServiceImpl.class.getName());

	@Resource(name="PayBackMapper")
	private PayBackMapper payBackMapper;

	@Override
	public ResultVO getPayBackByUserId(String userId) {
		ResultVO result = new ResultVO();
		try {
			Map<String ,Object> data = new HashMap<>();
			
			PayBack param = new PayBack();
			param.setUserId(userId);
			PayBack payBack = payBackMapper.selectByUk(param);
			data.put("payBack", payBack);
			
			result.setData(data);
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO savePayBack(String userId,String bankNm, String accountNm, String accountNo) {
		ResultVO result = new ResultVO();
		try {
			PayBack param = new PayBack(bankNm, accountNm, accountNo, userId);
			PayBack payBack = payBackMapper.selectByUk(param);
			
			if(payBack!=null){
				param.setIdx(payBack.getIdx());
				payBackMapper.updateAnyByUk(param);
			}else{
				payBackMapper.insertByPk(param);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	
}

package com.educare.edu.comn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.educare.edu.comn.mapper.LectureEtcIemDataMapper;
import com.educare.edu.comn.service.LectureEtcIemService;
import com.educare.edu.comn.vo.LectureEtcIemDataVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.util.LncUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 *
 *
 */
@Service("LectureEtcIemService")
public class LectureEtcIemServiceImpl implements LectureEtcIemService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LectureEtcIemServiceImpl.class.getName());

	@Resource(name = "LectureEtcIemDataMapper")
	private LectureEtcIemDataMapper lectureEtcIemDataMapper;
	
	@Override
	public ResultVO saveLectureEtcIemData(Integer eduSeq, String userId, String etcIemDataJson) {
		ResultVO result = new ResultVO();
		try {
			if(etcIemDataJson == null){//관리자 등록시 기타항목이 널이기때문에 무시한다.
				result.setResult(1);
				return result;
			}
			
			LectureEtcIemDataVO leidVO = new LectureEtcIemDataVO();
			leidVO.setEduSeq(eduSeq);
			leidVO.setUserId(userId);
			lectureEtcIemDataMapper.deleteByRcept(leidVO);
			
//			ObjectMapper om = new ObjectMapper();
			etcIemDataJson = etcIemDataJson.replaceAll("&amp;", "&").replaceAll("&quot;", "\"") ;
			String json = StringEscapeUtils.unescapeHtml4(etcIemDataJson);
			ObjectMapper mapper = new ObjectMapper(); 
			Map<String, Object> etcIemDataMap = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
			List<Map<String, Object>> etcIemList = (List<Map<String, Object>>) etcIemDataMap.get("etcIemList");
			for(Map<String, Object> etcIem : etcIemList){
				leidVO.setEtcIemSeq(LncUtil.nvlInt(etcIem.get("etcIemSeq")));
				leidVO.setEtcIemData(LncUtil.replaceNull(etcIem.get("etcIemData")));
				lectureEtcIemDataMapper.insertByPk(leidVO);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | JsonProcessingException e) {
			e.printStackTrace();
			result.setResult(0);
			return result;
		}
	}

	
}

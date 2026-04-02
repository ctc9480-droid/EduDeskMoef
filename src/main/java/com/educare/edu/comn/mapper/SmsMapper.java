package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.educare.edu.comn.vo.SmsVO;

/**
 *
 */
@Mapper("SmsMapper")
public interface SmsMapper {

	int selectSmsLogPageCnt(SmsVO vo);

	List<Map<String, Object>> selectSmsLogPageList(SmsVO vo);

}

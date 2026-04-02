package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.educare.edu.comn.vo.EmailVO;

/**
 *
 */
@Mapper("EmailMapper")
public interface EmailMapper {

	int selectEmailLogPageCnt(EmailVO vo);

	List<Map<String, Object>> selectEmailLogPageList(EmailVO vo);

}

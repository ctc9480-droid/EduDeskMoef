package com.educare.edu.comn.mapper;


import com.educare.edu.comn.vo.ExcelLogVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
 
@Mapper("ExcelLogMapper")
public interface ExcelLogMapper {

	int insertByPk(ExcelLogVO vo);
	
  
}
 
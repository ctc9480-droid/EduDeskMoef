package com.educare.edu.comn.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.vo.AdminIpVO;
  
@Mapper("AdminIpMapper") 
public interface AdminIpMapper {  
	
	List<AdminIp> selectByParam(AdminIpVO vo);
	
	void insertByPk(AdminIp vo);
	
	void updateByPk(AdminIp vo);
	
	void deleteByPk(int idx);

	int selectAccipPageCnt(AdminIpVO vo);
	List<AdminIpVO> selectAccipPageList(AdminIpVO vo);

	AdminIp selectByPk(int idx);

	int selectAccipDupIp(AdminIp vo);
} 

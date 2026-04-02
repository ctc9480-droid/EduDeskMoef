
package com.educare.edu.education.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.educare.edu.comn.mapper.CategoryAuthMapper;
import com.educare.edu.comn.model.CategoryAuth;
import com.educare.edu.comn.vo.CategoryAuthVO;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.CategoryAuthService;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.util.MaxNumUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 */
@Service("CategoryAuthService")
public class CategoryAuthServiceImpl implements CategoryAuthService {
	
	/** 카테고리 권한 Mapper */
	@Resource(name = "CategoryAuthMapper")
	private CategoryAuthMapper categoryAuthMapper;

	@Override
	public List<CategoryAuthVO> getCategoryAuthList(CategoryAuthVO vo) {
		return categoryAuthMapper.selectCategoryAuthList(vo);
	}

	@Override
	public ResultVO saveCategoryAuth(String userId, String authValJson) {
		ResultVO result = new ResultVO();
		try {
			ObjectMapper om = new ObjectMapper();
			List<Integer> authValList  = om.readValue(authValJson, List.class);
			
			CategoryAuthVO vo = new CategoryAuthVO();
			vo.setUserId(userId);
			categoryAuthMapper.deleteCategoryAuthList(vo);
			
			for(Integer ctgrySeq : authValList){
				vo.setCtgrySeq(ctgrySeq);
				categoryAuthMapper.insertCategoryAuthList(vo);
			}
			result.setResult(1);
			return result;
		} catch (NullPointerException | JsonProcessingException e) {
			result.setMsg("오류");
			result.setResult(0);
			return result;
		}
	}
	
}

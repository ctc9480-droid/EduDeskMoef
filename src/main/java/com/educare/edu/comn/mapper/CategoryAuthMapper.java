package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.vo.CategoryAuthVO;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.education.service.model.Category;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 */
@Mapper("CategoryAuthMapper")
public interface CategoryAuthMapper {

	List<CategoryAuthVO> selectCategoryAuthList(CategoryAuthVO vo);

	void deleteCategoryAuthList(CategoryAuthVO vo);

	void insertCategoryAuthList(CategoryAuthVO vo);
	
}

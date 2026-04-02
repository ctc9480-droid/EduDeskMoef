package com.educare.edu.education.service.impl;

import java.util.List;

import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.education.service.model.Category;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : CategoryMapper.java
 * @author SI개발팀 박용주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 카테고리 Mapper 
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Mapper("CategoryMapper")
public interface CategoryMapper {
	
	List<CategoryVO> selectCategoryList(Integer ctgryDepth);
	
	List<CategoryVO> selectCategoryUseList(Integer ctgryDepth);
	
	CategoryVO selectCategoryByPk(Integer ctgrySeq);
	
	void insertCategory(Category category);
	
	void updateCategory(Category category);
	
	void updateCategoryOrder(Category category);
	
	void deleteCategory(Integer ctgrySeq);
	
	/** 자식 카테고리 리스트 */
	List<CategoryVO> selectCategoryChildUseList(Integer parentSeq);

	List<Category> selectCategoryChildAllList(int parentSeq);
	
	CategoryVO selectParentCategoryByPk(Integer ctgrySeq);

	List<CategoryVO> selectCategoryAllList();

	CategoryVO selectParentCategoryByChild(int detailCtgrySeq);

	void disabledCategory(Integer ctgrySeq);

	List<Category> selectCategoryChildAllListAdmin(Integer parentSeq);

	List<CategoryVO> selectCategoryListAdmin(Integer ctgryDepth);

	List<CategoryVO> selectCategoryLastList(CategoryVO vo);
}

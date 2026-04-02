package com.educare.edu.quizCate.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.quizCate.vo.QstnCategoryVO;

/**
 * @Class Name : CategoryMapper.java
 * @author 김동영
 * @since 2024. 6. 5.
 * @version 1.0
 * @see
 * @Description 문제카테고리 Mapper 
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자			    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 5.	  	김동영 	    		최초생성 
 * </pre>
 */
@Mapper("QuizCateMapper")
public interface QuizCateMapper {
	
	//List<CategoryVO> selectCategoryList(Integer ctgryDepth);
	List<QstnCategoryVO> selectCategoryList(Integer ctgDepth);
	
	//List<CategoryVO> selectCategoryListAdmin(Integer ctgryDepth);
	List<QstnCategoryVO> selectCategoryListAdmin(Integer ctgDepth);
	
	//List<CategoryVO> selectCategoryUseList(Integer ctgryDepth);
	List<QstnCategoryVO> selectCategoryUseList(Integer ctgDepth);
	
	QstnCategoryVO selectCategoryByPk(Integer ctgrySeq);
	
	void insertCategory(QstnCategoryVO qstnCategoryVO);
	
	void updateCategory(QstnCategoryVO qstnCategoryVO);
	
	void updateCategoryOrder(QstnCategoryVO qstnCategoryVO);
	
	void deleteCategory(Integer qstnCtgSeq);
	
	/** 자식 카테고리 리스트 */
	//List<CategoryVO> selectCategoryChildUseList(Integer parentSeq);
	List<QstnCategoryVO> selectCategoryChildUseList(Integer parentSeq);

	List<QstnCategoryVO> selectCategoryChildAllList(int parentSeq);
	
	QstnCategoryVO selectParentCategoryByPk(Integer qstnCtgSeq);

	List<QstnCategoryVO> selectCategoryAllList();

	QstnCategoryVO selectParentCategoryByChild(int detailCtgrySeq);

	void disabledCategory(Integer qstnCtgSeq);

	//List<Category> selectCategoryChildAllListAdmin(Integer parentSeq);
	List<QstnCategoryVO> selectCategoryChildAllListAdmin(Integer parentSeq);

	
}

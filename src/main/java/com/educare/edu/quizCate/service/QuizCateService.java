package com.educare.edu.quizCate.service;

import java.util.List;

import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.quizCate.vo.QstnCategoryVO;

/**
 * @Class Name : QuizCateService.java
 * @author 김동영
 * @since 2024. 6. 5.
 * @version 1.0
 * @see
 * @Description 퀴즈 카테고리 서비스 인터페이스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자			    	수정내용
 * --------	  		---------	  		------------------------
 * 2024. 6. 5.	  	김동영 	    		최초생성 
 * </pre>
 */

public interface QuizCateService {
	/**
	 * 카테고리 목록을 조회한다.
	 * @param ctgryDepth 1 : 교육과정, 2 : 상세과정
	 * @return
	 */
	//public List<CategoryVO> getCategoryList(Integer ctgryDepth);
	public List<QstnCategoryVO> getCategoryList(Integer ctgDepth);
	
	//public List<CategoryVO> getCategoryListAdmin(Integer depthEdu);
	public List<QstnCategoryVO> getCategoryListAdmin(Integer ctgDepth);
	
	/**
	 * 교육과정 목록을 조회한다.(사용여부 Y만 조회)
	 * @return
	 */
	//public List<CategoryVO> getCategoryEduList();
	public List<QstnCategoryVO> getCategoryEduList();
	
	/**
	 * 상세과정 목록을 조회한다.(사용여부 Y만 조회)
	 * @return
	 */
	//public List<CategoryVO> getCategoryDetailList();
	public List<QstnCategoryVO> getCategoryDetailList();
	
	/**
	 * 카테고리를 조회한다.
	 * @param ctgrySeq
	 * @return
	 */
	public QstnCategoryVO getCategory(Integer ctgrySeq);
	
	/**
	 * 카테고리를 저장한다.
	 * @param category
	 */
	public void setCategory(QstnCategoryVO qstnCategoryVO); 
	
	/**
	 * 카테고리를 수정한다.
	 * @param category
	 */
	public void updateCategory(QstnCategoryVO qstnCategoryVO);
	
	/**
	 * 카테고리를 삭제한다. 
	 * @param category
	 */
	public void deleteCategory(Integer ctgrySeq);
	
	/**
	 * 카테고리 정렬 순서를 변경한다.
	 * @param vo
	 */
	public void updateCategoryOrder(EduVO vo);
	
	/**
	 * 선택한 카테고리를 삭제한다.
	 * @param category
	 */
	public void deleteCategorys(EduVO vo);
	
	/**
	 * 자식 카테고리 리스트
	 * @param parentSeq
	 * @return
	 */
	//public List<CategoryVO> getCategoryChildList(int parentSeq);
	public List<QstnCategoryVO> getCategoryChildList(int parentSeq);

	/**
	 * <pre>
	 * 카테고리 리스트 가져오기
	 * ctgry가 0이면 최상위 카테고리 가져오고
	 * 아니면 해당 카테고리의 자식 카테고리를 가져옴
	 * </pre>
	 * @param ctgrySeq
	 * @return
	 */
	//public List<CategoryVO> getCategoryList2(int ctgrySeq);
	public List<QstnCategoryVO> getCategoryList2(int ctgrySeq);

	public List<QstnCategoryVO> getCategoryChildAllList(Integer parentSeq);
	/**
	 * 부모 카테고리 불러오기 
	 * by 배현우
	 * @param detailCtgrySeq
	 * @return CategoryVO 부모
	 */
	public QstnCategoryVO getCategoryParent(Integer detailCtgrySeq);

	//public List<Category> getCategoryChildAllListAdmin(Integer parentSeq1);
	public List<QstnCategoryVO> getCategoryChildAllListAdmin(Integer parentSeq1);
	
	

	

}






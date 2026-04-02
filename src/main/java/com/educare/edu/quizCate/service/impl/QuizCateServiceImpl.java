package com.educare.edu.quizCate.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.quizCate.mapper.QuizCateMapper;
import com.educare.edu.quizCate.service.QuizCateService;
import com.educare.edu.quizCate.vo.QstnCategoryVO;
import com.educare.util.MaxNumUtil;

/**
 * @Class Name : QuizCateServiceImpl.java
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

@Service("QuizCateService")
public class QuizCateServiceImpl implements QuizCateService {
	
	/** 퀴즈 카테고리 Mapper */
	@Resource(name = "QuizCateMapper")
	private QuizCateMapper quizCateMapper;
	
	/** 카테고리 Mapper */
	//@Resource(name = "CategoryMapper")
	//private CategoryMapper categoryMapper;
	
	/** 카테고리 Mapper */
	//@Resource(name = "CategoryAuthMapper")
	//private CategoryAuthMapper categoryAuthMapper;
	
	/** 회원 Mapper */
	//@Resource(name = "MemberMapper")
	//private MemberMapper memberMapper;
	
	/**
	 * 카테고리 목록을 조회한다.
	 * @param ctgryDepth 1 : 교육과정, 2 : 상세과정
	 * @return
	 */
	/*
	@Override
	public List<CategoryVO> getCategoryList(Integer ctgryDepth) {
		return categoryMapper.selectCategoryList(ctgryDepth);
	}
	*/
	
	@Override
	public List<QstnCategoryVO> getCategoryList(Integer ctgDepth) {
		return quizCateMapper.selectCategoryList(ctgDepth);
	}

	/**
	 * 카테고리를 조회한다.
	 * @param ctgrySeq
	 * @return
	 */
	@Override
	public QstnCategoryVO getCategory(Integer ctgrySeq) {
		//return categoryMapper.selectCategoryByPk(ctgrySeq);
		return quizCateMapper.selectCategoryByPk(ctgrySeq);
	}

	/**
	 * 카테고리를 저장한다.
	 * @param category
	 */
	@Override
	public void setCategory(QstnCategoryVO qstnCategoryVO) {
		//categoryVO.setQstnCtgSeq(MaxNumUtil.getSequence(MaxNumUtil.FUNC_CTGRY));
		qstnCategoryVO.setUpdDe(new Date());
		quizCateMapper.insertCategory(qstnCategoryVO);
	}

	/**
	 * 카테고리를 수정한다.
	 * @param category
	 */
	@Override
	public void updateCategory(QstnCategoryVO qstnCategoryVO) {
		quizCateMapper.updateCategory(qstnCategoryVO);
	}

	/**
	 * 카테고리를 삭제한다.
	 * @param category
	 */
	@Override
	public void deleteCategory(Integer ctgrySeq) {
		//memberMapper.deleteInstrctrRealm(ctgrySeq);
		//categoryMapper.deleteCategory(ctgrySeq);	
		//categoryMapper.disabledCategory(ctgrySeq);
		
		quizCateMapper.deleteCategory(ctgrySeq);	
		//quizCateMapper.disabledCategory(ctgrySeq);
	}

	/**
	 * 카테고리 정렬 순서를 변경한다.
	 * @param vo
	 */
	@Override
	public void updateCategoryOrder(EduVO vo) {
		//CategoryVO selectCate = categoryMapper.selectCategoryByPk(vo.getSelectSeq());
		//CategoryVO targetCate = categoryMapper.selectCategoryByPk(vo.getTargetSeq());
		QstnCategoryVO selectCate = quizCateMapper.selectCategoryByPk(vo.getSelectSeq());
		QstnCategoryVO targetCate = quizCateMapper.selectCategoryByPk(vo.getTargetSeq());
		
		if(targetCate == null){
			return;
		}
		
		QstnCategoryVO category1 = new QstnCategoryVO();
		category1.setQstnCtgSeq(vo.getTargetSeq());
		category1.setOrderNo(selectCate.getOrderNo());
		//categoryMapper.updateCategoryOrder(category1);
		quizCateMapper.updateCategoryOrder(category1);
		
		QstnCategoryVO category2 = new QstnCategoryVO();
		category2.setQstnCtgSeq(vo.getSelectSeq());
		category2.setOrderNo(targetCate.getOrderNo());
		//categoryMapper.updateCategoryOrder(category2);
		quizCateMapper.updateCategoryOrder(category2);
	}

	/**
	 * 교육과정 목록을 조회한다.(사용여부 Y만 조회)
	 * @return
	 */
	@Override
	//public List<CategoryVO> getCategoryEduList() {
	public List<QstnCategoryVO> getCategoryEduList() {
		//List<CategoryVO> list = categoryMapper.selectCategoryUseList(Category.DEPTH_EDU);
		List<QstnCategoryVO> list = quizCateMapper.selectCategoryUseList(QstnCategoryVO.DEPTH_EDU);
		
		//운영자인경우 허용된 카테고리만 재 설정
		/*
		try {
			if("3".equals(SessionUserInfoHelper.getUserMemLvl()) || "4".equals(SessionUserInfoHelper.getUserMemLvl())){
				List<CategoryVO> list2 = new ArrayList<CategoryVO>();
				CategoryAuthVO vo = new CategoryAuthVO();
				vo.setUserId(SessionUserInfoHelper.getUserId());
				List<CategoryAuthVO> caList = categoryAuthMapper.selectCategoryAuthList(vo);
				if(!ObjectUtils.isEmpty(caList)){
					for(CategoryVO map:list){
						for(CategoryAuthVO caVO : caList){
							if(caVO.getCtgrySeq() == map.getCtgrySeq()){
								list2.add(map);
								break;
							}
						}
					}
					return list2;
				}
			}
		} catch (NullPointerException e) {
		}
		*/
		
		return list;
	}

	/**
	 * 상세과정 목록을 조회한다.(사용여부 Y만 조회)
	 * @return
	 */
	@Override
	//public List<CategoryVO> getCategoryDetailList() {
	public List<QstnCategoryVO> getCategoryDetailList() {
		//return categoryMapper.selectCategoryUseList(Category.DEPTH_DETAIL);
		return quizCateMapper.selectCategoryUseList(Category.DEPTH_DETAIL);
	}

	/**
	 * 선택한 카테고리를 삭제한다.
	 * @param category
	 */
	@Override
	public void deleteCategorys(EduVO vo) {
		if( vo.getCtgrySeqs() != null && vo.getCtgrySeqs().length > 0 ) {
			for( Integer seq : vo.getCtgrySeqs() ) {
				this.deleteCategory(seq);
			}
		}
	}

	@Override
	//public List<CategoryVO> getCategoryChildList(int parentSeq) {
	public List<QstnCategoryVO> getCategoryChildList(int parentSeq) {
		if(parentSeq!=0){
			//List<CategoryVO> list = categoryMapper.selectCategoryChildUseList(parentSeq);
			List<QstnCategoryVO> list = quizCateMapper.selectCategoryChildUseList(parentSeq);

			return list;
		}
		return null;
	}
	@Override
	public List<QstnCategoryVO> getCategoryChildAllList(Integer parentSeq) {
		if(parentSeq!=0){
			//return categoryMapper.selectCategoryChildAllList(parentSeq);
			return quizCateMapper.selectCategoryChildAllList(parentSeq);
		}
		return null;
	} 

	@Override
	//public List<CategoryVO> getCategoryList2(int ctgrySeq) {
	public List<QstnCategoryVO> getCategoryList2(int ctgrySeq) {
		try {
			if(ctgrySeq==0){
				//return categoryMapper.selectCategoryUseList(1);
				return quizCateMapper.selectCategoryUseList(1);
			}else{
				//return categoryMapper.selectCategoryChildUseList(ctgrySeq);
				return quizCateMapper.selectCategoryChildUseList(ctgrySeq);
			}
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public QstnCategoryVO getCategoryParent(Integer detailCtgrySeq) {
		//CategoryVO categoryVo = categoryMapper.selectParentCategoryByPk(detailCtgrySeq);
		QstnCategoryVO qstnCategoryVO = quizCateMapper.selectParentCategoryByPk(detailCtgrySeq);
		return qstnCategoryVO;
		//상위 카테고리(eduCtgrySeq)와 불러온 seq가 같다면 2차,
		/*
		 * if(categoryVo.getCtgryDepth()==3){ return 3; }else
		 * if(categoryVo.getCtgryDepth()==2){ return 2; }else{ return 1; }
		 */
	}

	@Override
	public List<QstnCategoryVO> getCategoryChildAllListAdmin(Integer parentSeq) {
		if(parentSeq!=0){
			//return categoryMapper.selectCategoryChildAllListAdmin(parentSeq);
			return quizCateMapper.selectCategoryChildAllListAdmin(parentSeq);
		}
		return null;
	}

	/*
	@Override
	public List<CategoryVO> getCategoryListAdmin(Integer ctgryDepth) {
		return categoryMapper.selectCategoryListAdmin(ctgryDepth);
	}
	*/
	
	@Override
	public List<QstnCategoryVO> getCategoryListAdmin(Integer ctgDepth) {
		return quizCateMapper.selectCategoryListAdmin(ctgDepth);
	}
}







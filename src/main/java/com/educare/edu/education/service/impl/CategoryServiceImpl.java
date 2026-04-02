
package com.educare.edu.education.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.educare.edu.comn.mapper.CategoryAuthMapper;
import com.educare.edu.comn.vo.CategoryAuthVO;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.util.MaxNumUtil;

/**
 * @Class Name : CategoryServiceImpl.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 8.
 * @version 1.0
 * @see
 * @Description 카테고리 서비스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 8.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService {
	
	/** 카테고리 Mapper */
	@Resource(name = "CategoryMapper")
	private CategoryMapper categoryMapper;
	/** 카테고리 Mapper */
	@Resource(name = "CategoryAuthMapper")
	private CategoryAuthMapper categoryAuthMapper;
	
	/** 회원 Mapper */
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	
	/**
	 * 카테고리 목록을 조회한다.
	 * @param ctgryDepth 1 : 교육과정, 2 : 상세과정
	 * @return
	 */
	@Override
	public List<CategoryVO> getCategoryList(Integer ctgryDepth) {
		return categoryMapper.selectCategoryList(ctgryDepth);
	}

	/**
	 * 카테고리를 조회한다.
	 * @param ctgrySeq
	 * @return
	 */
	@Override
	public CategoryVO getCategory(Integer ctgrySeq) {
		return categoryMapper.selectCategoryByPk(ctgrySeq);
	}

	/**
	 * 카테고리를 저장한다.
	 * @param category
	 */
	@Override
	public void setCategory(Category category) { 
		category.setCtgrySeq(MaxNumUtil.getSequence(MaxNumUtil.FUNC_CTGRY));
		categoryMapper.insertCategory(category);		
	}

	/**
	 * 카테고리를 수정한다.
	 * @param category
	 */
	@Override
	public void updateCategory(Category category) {
		categoryMapper.updateCategory(category);
	}

	/**
	 * 카테고리를 삭제한다.
	 * @param category
	 */
	@Override
	public void deleteCategory(Integer ctgrySeq) {
		memberMapper.deleteInstrctrRealm(ctgrySeq);
		//categoryMapper.deleteCategory(ctgrySeq);	
		categoryMapper.disabledCategory(ctgrySeq);
	}

	/**
	 * 카테고리 정렬 순서를 변경한다.
	 * @param vo
	 */
	@Override
	public void updateCategoryOrder(EduVO vo) {
		CategoryVO selectCate = categoryMapper.selectCategoryByPk(vo.getSelectSeq());
		CategoryVO targetCate = categoryMapper.selectCategoryByPk(vo.getTargetSeq());
		
		if(targetCate == null){
			return;
		}
		
		Category category1 = new Category();
		category1.setCtgrySeq(vo.getTargetSeq());
		category1.setOrderNo(selectCate.getOrderNo());
		categoryMapper.updateCategoryOrder(category1);
		
		Category category2 = new Category();
		category2.setCtgrySeq(vo.getSelectSeq());
		category2.setOrderNo(targetCate.getOrderNo());
		categoryMapper.updateCategoryOrder(category2);
	}

	/**
	 * 교육과정 목록을 조회한다.(사용여부 Y만 조회)
	 * @return
	 */
	@Override
	public List<CategoryVO> getCategoryEduList() {
		List<CategoryVO> list = categoryMapper.selectCategoryUseList(Category.DEPTH_EDU);
		
		//운영자인경우 허용된 카테고리만 재 설정
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
			list = null;
		}
		
		return list;
	}

	/**
	 * 상세과정 목록을 조회한다.(사용여부 Y만 조회)
	 * @return
	 */
	@Override
	public List<CategoryVO> getCategoryDetailList() {
		return categoryMapper.selectCategoryUseList(Category.DEPTH_DETAIL);
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
	public List<CategoryVO> getCategoryChildList(int parentSeq) {
		if(parentSeq!=0){
			List<CategoryVO> list = categoryMapper.selectCategoryChildUseList(parentSeq);

			//기관 관리자인경우 허용된 카테고리만 재설정
			try {
				if("2".equals(SessionUserInfoHelper.getUserMemLvl())){
					List<CategoryVO> list2 = new ArrayList<CategoryVO>();
					for(CategoryVO map:list){
						if(map.getOpenClass()==1){
							list2.add(map);
						}
					}
					return list2;
				}
			} catch (NullPointerException e) {
				return list;
			}
			
			return list;
		}
		return null;
	}
	@Override
	public List<Category> getCategoryChildAllList(Integer parentSeq) {
		if(parentSeq!=0){
			return categoryMapper.selectCategoryChildAllList(parentSeq);
		}
		return null;
	} 

	@Override
	public List<CategoryVO> getCategoryList2(int ctgrySeq) {
		try {
			if(ctgrySeq==0){
				return categoryMapper.selectCategoryUseList(1);
			}else{
				return categoryMapper.selectCategoryChildUseList(ctgrySeq);
			}
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public CategoryVO getCategoryParent(Integer detailCtgrySeq) {
		try {
			CategoryVO categoryVo = categoryMapper.selectParentCategoryByPk(detailCtgrySeq);
			return categoryVo;
			//상위 카테고리(eduCtgrySeq)와 불러온 seq가 같다면 2차,
			/*
			 * if(categoryVo.getCtgryDepth()==3){ return 3; }else
			 * if(categoryVo.getCtgryDepth()==2){ return 2; }else{ return 1; }
			 */
		}catch(NullPointerException e) {
			return null;
		}
	}

	@Override
	public List<Category> getCategoryChildAllListAdmin(Integer parentSeq) {
		if(parentSeq!=0){
			return categoryMapper.selectCategoryChildAllListAdmin(parentSeq);
		}
		return null;
	}

	@Override
	public List<CategoryVO> getCategoryListAdmin(Integer ctgryDepth) {
		return categoryMapper.selectCategoryListAdmin(ctgryDepth);
	}
}

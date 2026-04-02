
package com.educare.edu.education.service;

import java.util.List;

import com.educare.edu.comn.model.CategoryAuth;
import com.educare.edu.comn.vo.CategoryAuthVO;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.model.Category;

/**
 */
public interface CategoryAuthService {

	List<CategoryAuthVO> getCategoryAuthList(CategoryAuthVO vo);

	ResultVO saveCategoryAuth(String userId, String authValJson);
	
}

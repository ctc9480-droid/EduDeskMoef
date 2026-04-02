
package com.educare.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.educare.edu.maxNum.service.impl.MaxNumMapper;
import com.educare.edu.maxNum.service.model.MaxNumModel;

/**
 * @Class Name : MaxNumUtil.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 1.
 * @version 1.0
 * @see
 * @Description 
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 1.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class MaxNumUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(MaxNumUtil.class.getName());
	public static final String FUNC_LOG = "log";
	public static final String FUNC_BBS = "bbs";
	public static final String FUNC_CTGRY = "ctgry";
	public static final String FUNC_LCTRE = "lctre";
	public static final String FUNC_FILE = "file";
	public static final String FUNC_SMS = "sms";
	
	/**
	 * 기능별 MaxNum +1 값
	 * 리턴값이 0 이 나오면func가 입력되지 않거나, insert or update가 되지 않은 상태임.
	 * => 0일경우를 항상 처리해야함.
	 * (하이버네이트는 non-static 객체이므로 사용하지 못함.)
	 * @param func
	 * @return
	 */
	public static int getSequence(String func){
		int seq = 0;
		if(func != null && !"".equals(func)){
			WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
			if(ctx != null){
				MaxNumMapper mapper = (MaxNumMapper) ctx.getBean("MaxNumMapper");
				if(mapper != null){
					Integer maxNum = mapper.getMax(func);
					if(maxNum == null){
						maxNum = 0;
					}
					seq = maxNum +1;
					if(maxNum == 0){
						mapper.save(new MaxNumModel(func, seq));
					}else{
						mapper.update(new MaxNumModel(func, seq));
					}
				}
			}else{
				LOG.error("Spring 환경에서만 동작가능합니다.");
			}
		}
		return seq;
	}
}

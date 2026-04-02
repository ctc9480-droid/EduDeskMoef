
package com.educare.edu.bbs.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.educare.edu.bbs.service.PopupService;
import com.educare.edu.bbs.service.model.Popup;
import com.educare.edu.member.service.SessionUserInfoHelper;

/**
 * @Class Name : PopupServiceImpl.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 서비스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
@Transactional
@Service("PopupService")
public class PopupServiceImpl implements PopupService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(PopupServiceImpl.class);
	
	@Resource(name = "PopupMapper")
	private PopupMapper popupMapper;
	
	@Override
	public List<Popup> getPopupList(Popup vo) {
		List<Popup> popupList = popupMapper.selectPopupList(vo);
		return popupList;
	}
	@Override
	public Popup getPopupMap(Popup vo) {
		Popup popupMap = popupMapper.selectPopupMap(vo.getIdx());
		return popupMap;
	}

	@Override
	public int popupWriteProc(Popup vo) {
		int result=0;
		vo.setRegId(SessionUserInfoHelper.getUserId());
		vo.setRegNm(SessionUserInfoHelper.getUserNm());
		try {
			Popup popupMap = popupMapper.selectPopupMap(vo.getIdx());//등록,수정여부 위해
			if(popupMap!=null){
				popupMapper.updatePopupMap(vo);
			}else{
				popupMapper.insertPopupMap(vo);
			}
			result=1;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
		}
		return result;
	}
	@Override
	public int getPopupTotalCnt(Popup vo) {
		int totalCnt = popupMapper.selectPopupTotalCnt(vo);
		return totalCnt;
	}
	@Override
	public int setPopupDeleteListProc(Integer[] idxs) {
		int result=0;
		try {
			for(Integer idx:idxs){
				Popup popupMap = popupMapper.selectPopupMap(idx);//등록,수정여부 위해
				if(popupMap!=null){
					popupMapper.deletePopupMap(idx);
				}
			}
			result=1;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
		}
		return result;
	}

}

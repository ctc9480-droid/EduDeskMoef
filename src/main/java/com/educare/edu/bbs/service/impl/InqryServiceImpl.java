
package com.educare.edu.bbs.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.educare.edu.bbs.service.InqryService;
import com.educare.edu.bbs.service.model.Inqry;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.member.service.SessionUserInfoHelper;

/**
 * @Class Name : InqryServiceImpl.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 온라인 문의 게시판 서비스
 * 
 * <pre>
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
@Transactional
@Service("InqryService")
public class InqryServiceImpl implements InqryService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(InqryServiceImpl.class);
	
	@Resource(name = "InqryMapper")
	private InqryMapper inqryMapper;
	
	@Override
	public List<Inqry> getInqryList(Inqry vo) {
		List<Inqry> inqryList = inqryMapper.selectInqryList(vo);
		return inqryList;
	}
	@Override
	public Inqry getInqryMap(Inqry vo) {
		Inqry inqryMap = inqryMapper.selectInqryMap(vo);
		return inqryMap;
	}

	@Override
	public int setInqryWriteProc(Inqry vo) {
		int result=0;
		
		if(SessionUserInfoHelper.isLogined()){
			vo.setRegId(SessionUserInfoHelper.getUserId());
			vo.setRegNm(SessionUserInfoHelper.getUserNm());
			vo.setModId(SessionUserInfoHelper.getUserId());
			vo.setModNm(SessionUserInfoHelper.getUserNm());
		}else{
			vo.setModNm(vo.getRegNk());
			vo.setRegNm(vo.getRegNk());
		}
		
		try {
			Inqry inqryMap = inqryMapper.selectInqryMap(vo);//등록,수정여부 위해
			if(inqryMap!=null){
				inqryMapper.updateInqryMap(vo);
			}else{
				inqryMapper.insertInqryMap(vo);
			}
			result=1;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
		}
		return result;
	}
	@Override
	public int getInqryTotalCnt(Inqry vo) {
		int totalCnt = inqryMapper.selectInqryTotalCnt(vo);
		return totalCnt;
	}
	
	/**
	 * 온라인 문의 삭제 프로세스
	 */
	@Override
	public ResultVO setInqryDeleteProc(Inqry vo) {
		ResultVO result = new ResultVO();
		try {
			Inqry inqryMap = inqryMapper.selectInqryMap(vo);//등록,수정여부 위해
			
			if(inqryMap == null){
				result.setResult(0);
				return result;
			}
			
			result = checkMyInqry(vo,inqryMap);
			if(result.getResult()!=1){
				return result;
			}
			
			if(inqryMap.getStatus()==2){
				result.setMsg("이미 답변완료 처리 되었습니다.");
				result.setResult(0);
				return result;
			}
			
			inqryMapper.deleteInqryMap(vo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
			result.setResult(0);
			return result;
		}
	}
	/**
	 * 미게시
	 */
	@Override
	public ResultVO setInqryInvisible(Inqry vo) {
		ResultVO result = new ResultVO();
		try {
			Inqry inqryMap = inqryMapper.selectInqryMap(vo);//등록,수정여부 위해
			
			if(inqryMap == null){
				result.setResult(0);
				return result;
			}
			
			if(!vo.isAdmin()){
				result = checkMyInqry(vo,inqryMap);
				if(result.getResult()!=1){
					return result;
				}
				
				if(inqryMap.getStatus()==2){
					result.setMsg("이미 답변완료 처리 되었습니다.");
					result.setResult(0);
					return result;
				}
			}
			vo.setStatus(0);
			inqryMapper.updateInqryMapForStatus(vo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
			result.setResult(0);
			return result;
		}
	}
	/**
	 * 본인글 체크
	 */
	@Override
	public ResultVO checkMyInqry(Inqry vo,Inqry inqryMap) {
		ResultVO result = new ResultVO();
		try {
			if(inqryMap!=null){
				if(inqryMap.getRegId()!=null){
					if(!inqryMap.getRegId().equals(SessionUserInfoHelper.getUserId())){
						result.setResult(0);
						result.setMsg("본인이 작성한 글이 아닙니다.");
						return result;
					}
				}
				if(inqryMap.getPassword()!=null){
					if(!inqryMap.getPassword().equals(vo.getPassword())){
						result.setResult(0);
						result.setMsg("패스워드가 일치하지 않습니다.");
						return result;
					}
				}
			}
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	@Override
	public List<Inqry> getInqryMiniList(int recordCountPerPage) {
		Inqry vo = new Inqry();
		vo.setFirstRecordIndex(0);
		vo.setRecordCountPerPage(recordCountPerPage);
		return inqryMapper.selectInqryList(vo);
	}
}

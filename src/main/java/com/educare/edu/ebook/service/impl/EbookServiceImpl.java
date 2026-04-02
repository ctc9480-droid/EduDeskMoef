
package com.educare.edu.ebook.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.ebook.service.EbookService;
import com.educare.edu.ebook.service.model.Ebook;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;

@Transactional
@Service("EbookService")
public class EbookServiceImpl implements EbookService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EbookServiceImpl.class);
	
	@Resource(name = "EbookMapper")
	private EbookMapper ebookMapper;
	
	@Override
	public List<Ebook> getEbookList(Ebook vo) {
		List<Ebook> list = ebookMapper.selectEbookList(vo);
		return list;
	}
	@Override
	public Ebook getEbookMap(Ebook vo) {
		Ebook map = ebookMapper.selectEbookMap(vo.getIdx());
		return map;
	}

	@Override
	public int ebookWriteProc(Ebook vo) {
		int result=0;
		vo.setRegId(SessionUserInfoHelper.getUserId());
		vo.setRegNm(SessionUserInfoHelper.getUserNm());
		try {
			Ebook map = ebookMapper.selectEbookMap(vo.getIdx());//등록,수정여부 위해
			if(map!=null){
				ebookMapper.updateEbookMap(vo);
			}else{
				ebookMapper.insertEbookMap(vo);
			}
			result=1;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
		}
		return result;
	}
	@Override
	public int getEbookTotalCnt(Ebook vo) {
		int totalCnt = ebookMapper.selectEbookTotalCnt(vo);
		return totalCnt;
	}
	@Override
	public int ebookFileProc(MultipartHttpServletRequest mhsr) {
		try {
			int idx=LncUtil.nvlInt(mhsr.getParameter("idx"));
			Ebook vo = new Ebook();
			vo.setIdx(idx);
			
			MultipartFile mf = mhsr.getFile("file_pdf");
			if(mf != null){
				String fileOrg = LncUtil.getEncode(mf.getOriginalFilename());
				if(fileOrg == null){
					return 0;
				}
				String ext = fileOrg.substring(fileOrg.lastIndexOf(".") + 1);
				String fileRename = idx+"."+ext;
				FileUtil.multiPartupload(mf, "upload/ebook/", fileOrg, fileRename);
				vo.setFileNm(fileOrg);
				vo.setFileRenm(fileRename);
			}
			
			MultipartFile mf2 = mhsr.getFile("file_thum");
			if(mf2!=null){
				String fileOrg2 = LncUtil.getEncode(mf2.getOriginalFilename());
				String ext2 = "";
				if(fileOrg2 == null){
					return 0;
				}
				ext2 = fileOrg2.substring(fileOrg2.lastIndexOf(".") + 1);
				String fileRename2 = idx+"_thum."+ext2;
				//NaverObjectStorage.multiPartupload(mf2, "upload/ebook/", fileOrg2, fileRename2);
				FileUtil.multiPartupload(mf2, "upload/ebook/", fileOrg2, fileRename2);
				vo.setThumNm(fileOrg2);
				vo.setThumRenm(fileRename2);
			}
			
			ebookMapper.updateEbookFile(vo);
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	@Override
	public ResultVO getPdf(Ebook vo) {
		ResultVO result = new ResultVO();
		try {
			
			Ebook ebookMap = ebookMapper.selectEbookMap(vo.getIdx());
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("fileRenm", ebookMap.getFileRenm());
			
			result.setData(data);
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
}

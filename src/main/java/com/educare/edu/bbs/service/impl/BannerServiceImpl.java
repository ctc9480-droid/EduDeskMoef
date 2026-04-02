
package com.educare.edu.bbs.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.UtilComponent;
import com.educare.edu.bbs.service.BannerService;
import com.educare.edu.bbs.service.PopupService;
import com.educare.edu.bbs.service.model.Banner;
import com.educare.edu.bbs.service.model.Inqry;
import com.educare.edu.bbs.service.model.Popup;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;

@Transactional
@Service("BannerService")
public class BannerServiceImpl implements BannerService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(BannerServiceImpl.class);
	
	@Resource(name = "BannerMapper")
	private BannerMapper bannerMapper;
	
	@Override
	public List<Banner> getBannerList(Banner vo) {
		List<Banner> bannerList = bannerMapper.selectBannerList(vo);
		return bannerList;
	}
	@Override
	public Banner getBannerMap(Banner vo) {
		Banner bannerMap = bannerMapper.selectBannerMap(vo.getIdx());
		return bannerMap;
	}

	@Override
	public int bannerWriteProc(Banner vo) {
		int result=0;
		vo.setRegId(SessionUserInfoHelper.getUserId());
		vo.setRegNm(SessionUserInfoHelper.getUserNm());
		try {
			Banner bannerMap = bannerMapper.selectBannerMap(vo.getIdx());//등록,수정여부 위해
			if(bannerMap!=null){
				bannerMapper.updateBannerMap(vo);
			}else{
				bannerMapper.insertBannerMap(vo);
			}
			result=1;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.debug(e.getMessage());
		}
		return result;
	}
	@Override
	public int getBannerTotalCnt(Banner vo) {
		int totalCnt = bannerMapper.selectBannerTotalCnt(vo);
		return totalCnt;
	}
	@Override
	public int bannerImgProc(MultipartHttpServletRequest mhsr) {
		try {
			MultipartFile mf = mhsr.getFile("file_img");
			int idx=LncUtil.nvlInt(mhsr.getParameter("idx"));
			String fileOrg = LncUtil.getEncode(mf.getOriginalFilename());
			if(fileOrg == null){
				return 0;
			}
			String ext = fileOrg.substring(fileOrg.lastIndexOf(".") + 1);
			String fileRename = idx+"."+ext;
			FileUtil.multiPartupload(mf, "upload/web/banner/", fileOrg, fileRename);
			Banner vo = new Banner();
			vo.setIdx(idx);
			vo.setFileImgNm(fileOrg);
			vo.setFileImgRenm(fileRename);
			bannerMapper.updateBannerImg(vo);
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	@Override
	public List<Banner> getBannerListMain() {
		try {
			Banner vo = new Banner();
			vo.setNowDtime(DateUtil.getDate2Str(Calendar.getInstance().getTime(), "yyyyMMddHHmmss"));
			List<Banner> result = bannerMapper.selectBannerListMain(vo);
			return result;
		} catch (NullPointerException e) {
			return null;
		}
	}
	@Override
	public int setBannerDeleteProc(Banner vo) {
		try {
			
			Banner banner = bannerMapper.selectBannerMap(vo.getIdx());
			bannerMapper.deleteBannerMap(vo);
			
			FileUtil.delete("upload/web/banner/"+banner.getFileImgRenm());
	
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	@Override
	public int setBannerInvisible(Banner vo) {
		try {
			
			vo.setStatus(0);
			bannerMapper.updateBannerMapForStatus(vo);
	
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}
}

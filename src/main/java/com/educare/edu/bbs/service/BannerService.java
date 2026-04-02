
package com.educare.edu.bbs.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.service.model.Banner;
import com.educare.edu.bbs.service.model.Inqry;
import com.educare.edu.bbs.service.model.Popup;

public interface BannerService {

	List<Banner> getBannerList(Banner vo);

	int bannerWriteProc(Banner vo);

	Banner getBannerMap(Banner vo);

	int getBannerTotalCnt(Banner vo);

	int bannerImgProc(MultipartHttpServletRequest mhsr);

	List<Banner> getBannerListMain();

	int setBannerDeleteProc(Banner vo);

	int setBannerInvisible(Banner vo);
	
}

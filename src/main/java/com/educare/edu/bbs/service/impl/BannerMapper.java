package com.educare.edu.bbs.service.impl;

import java.util.List;

import com.educare.edu.bbs.service.model.Banner;
import com.educare.edu.bbs.service.model.Popup;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("BannerMapper")
public interface BannerMapper {

	List<Banner> selectBannerList(Banner vo);

	Banner selectBannerMap(Integer idx);

	void updateBannerMap(Banner vo);

	void insertBannerMap(Banner vo);

	int selectBannerTotalCnt(Banner vo);

	void updateBannerImg(Banner vo);

	List<Banner> selectBannerListMain(Banner vo);

	void deleteBannerMap(Banner vo);

	void updateBannerMapForStatus(Banner vo);

}

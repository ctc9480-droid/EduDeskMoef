package com.educare.edu.bbs.service.impl;

import java.util.List;

import com.educare.edu.bbs.service.model.Board;
import com.educare.edu.bbs.service.model.BoardMst;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("BoardMstMapper")
public interface BoardMstMapper {

	BoardMst selectBoardMstMap(String boardType);

}

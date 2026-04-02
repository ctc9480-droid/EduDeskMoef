package com.educare.edu.comn.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.educare.edu.comn.mapper.AttendStdntMapper;
import com.educare.edu.comn.mapper.LectureRoomExcptMapper;
import com.educare.edu.comn.mapper.LectureRoomMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.AttendStdnt;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.LectureRoomService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.LectureRoomExcptVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


/**
 *
 *
 */
@Service("LectureRoomService")
public class LectureRoomServiceImpl implements LectureRoomService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LectureRoomServiceImpl.class.getName());

	@Resource(name="LectureRoomMapper")
	private LectureRoomMapper lectureRoomMapper;
	@Resource(name="LectureRoomExcptMapper")
	private LectureRoomExcptMapper lectureRoomExcptMapper;
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;

	@Override
	public ResultVO getLectureRoomPageList(LectureRoomVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//전체 수
			int totalCnt = lectureRoomMapper.selectPageTotalCnt(vo);
			vo.setRowCnt(10);
			//페이징설정
			PaginationInfo page = new PaginationInfo();
			page.setTotalRecordCount(totalCnt);
			page.setCurrentPageNo(vo.getPage());
			page.setRecordCountPerPage(vo.getRowCnt());
			page.setPageSize(10);
			
	        vo.setFirstIndex(page.getFirstRecordIndex());
			
			//목록
			List<LectureRoomVO> list = lectureRoomMapper.selectPageList(vo);
			
			rstData.put("list", list);
			rstData.put("pageNavi", page);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public LectureRoomVO getLectureRoom(int roomSeq) {
		LectureRoomVO vo = new LectureRoomVO();
		vo.setRoomSeq(roomSeq);
		LectureRoomVO room = lectureRoomMapper.selectByPk(vo);
		return room;
	}

	@Override
	public ResultVO saveLectureRoom(LectureRoomVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String userId = SessionUserInfoHelper.getUserId();
			Date nowDt = DateUtil.getCalendar().getTime();
			vo.setRegId(userId);
			vo.setRegDt(nowDt);
			vo.setModId(userId);
			vo.setModDt(nowDt);
			
			//신규,수정 여부
			if(vo.getRoomSeq()>0){
				lectureRoomMapper.updateByPk(vo);
			}else{
				lectureRoomMapper.insertByPk(vo);
			}
			rstData.put("roomSeq", vo.getRoomSeq());
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	@Override
	public ResultVO deleteLectureRoom(int roomSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			LectureRoomVO vo = new LectureRoomVO();
			String userId = SessionUserInfoHelper.getUserId();
			Date nowDt = DateUtil.getCalendar().getTime();
			vo.setRegId(userId);
			vo.setRegDt(nowDt);
			vo.setModId(userId);
			vo.setModDt(nowDt);
			vo.setRoomSeq(roomSeq);
			
			vo.setUseYn("D");//삭제
			lectureRoomMapper.updateByPk(vo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Transactional
	@Override
	public ResultVO saveLectureRoomExcpt(int roomSeq, String dataJson) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> dataMap  = om.readValue(LncUtil.unescapeJson(dataJson), Map.class);
			List<Map<String, Object>> list = (List<Map<String, Object>>) dataMap.get("listData");
			
			//삭제
			LectureRoomExcptVO vo = new LectureRoomExcptVO();
			vo.setRoomSeq(roomSeq);
			lectureRoomExcptMapper.deleteByParam(vo);
			
			for(Map<String, Object> o : list){
				String startDt = LncUtil.replaceNull(o.get("startDt")).replaceAll("-", "");
				String endDt = LncUtil.replaceNull(o.get("endDt")).replaceAll("-", "");
				String memo = LncUtil.replaceNull(o.get("memo"));
				LectureRoomExcptVO vo2 = new LectureRoomExcptVO(roomSeq, startDt, endDt, memo);
				
				lectureRoomExcptMapper.insertByPk(vo2);
			}
			
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | JsonProcessingException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getLectureRoomExcpt(int roomSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//강의실 정보
			LectureRoomVO vo = new LectureRoomVO();
			vo.setRoomSeq(roomSeq);
			LectureRoomVO room = lectureRoomMapper.selectByPk(vo);
			rstData.put("room", room);
			
			LectureRoomExcptVO vo2 = new LectureRoomExcptVO();
			vo2.setRoomSeq(roomSeq);
			List<LectureRoomExcptVO> excptList = lectureRoomExcptMapper.selectByParam(vo2);
			rstData.put("list", excptList);
			
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getLectureRoomChoice(String[] eduDeStrArr) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			//eduDeStrArr교육예정시간 체크하여 가능한 강의실 찾기용 나중에 구현하자
			
			//강의실 조회
			LectureRoomVO vo = new LectureRoomVO();
			vo.setUseYn("Y");
			List<LectureRoomVO> list = lectureRoomMapper.selectByParam(vo);
			rstData.put("roomList", list);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO checkPossibleRoomByTimeList(List<LectureTime> timeList) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			if(ObjectUtils.isEmpty(timeList)){
				result.setMsg("교육시간정보에 오류가 발견되었습니다.");
				result.setResult(0);
				return result;
			}
			
			//저장대상의 일자를 조회하여 시작일 ,종료일을 세팅한다.
			String startDt = timeList.get(0).getEduDt();
			String endDt = timeList.get(0).getEduDt();
			if(timeList.size() > 0){
				endDt = timeList.get(timeList.size()-1).getEduDt();
			}
			
			//체크기간 예외일 조회
			LectureRoomExcptVO roomExcptVO = new LectureRoomExcptVO();
			roomExcptVO.setStartDt(startDt);
			roomExcptVO.setEndDt(endDt);
			List<LectureRoomExcptVO> excptList = lectureRoomExcptMapper.selectByDt(roomExcptVO);
			
			// 휴무일 체크
			for(LectureTime o: timeList){
				if(o.getRoomSeq() == 0 ){
					continue;
				}
				for(LectureRoomExcptVO o2 : excptList){
					if(o.getEduDt().compareTo(o2.getStartDt())>=0 
							&& o.getEduDt().compareTo(o2.getEndDt())<=0 
							&& o.getRoomSeq() == o2.getRoomSeq() 
							){
						LOG.info("no room2");
						//사용불가 강의실 발견
						result.setMsg(o2.getRoomNm()+"은(는) 휴무일로 설정되어 있어 사용하실 수 없습니다. (휴무일 : "+o2.getStartDt()+"~"+o2.getEndDt()+")");
						result.setResult(0);
						return result;
					}
				}
				
			}
			
			//사용일 조회
			LectureTime timeVO = new LectureTime();
			timeVO.setSrchStartDt(startDt);
			timeVO.setSrchEndDt(endDt);
			List<LectureTime> timeList2 = lectureTimeMapper.selectByDt(timeVO);
			//사용중체크
			for(LectureTime o: timeList){
				if(o.getRoomSeq() == 0 ){
					continue;
				}
				for(LectureTime o2 : timeList2){
					boolean isCheck1 = o.getEduDt().equals(o2.getEduDt());
					boolean isCheck2 = (o.getStartTm().compareTo(o2.getEndTm()) <= 0 && o.getEndTm().compareTo(o2.getStartTm()) >= 0);
					boolean isCheck3 = (o.getRoomSeq() == o2.getRoomSeq());
					boolean isCheck4 = !(o.getTimeSeq() == o2.getTimeSeq() && o.getEduSeq() == o2.getEduSeq());
					LOG.info(""+isCheck1);
					LOG.info(""+isCheck2);
					LOG.info(""+isCheck3);
					LOG.info(""+isCheck4);
					if(isCheck1 && (isCheck2) && isCheck3 && isCheck4){
						//강의실체크 작업 마무리 해야함
						LOG.info("no room2");
						//사용불가 강의실 발견
						result.setMsg(o2.getRoomNm()+"은(는) 이미 사용중입니다. \n(사용일 : "+o2.getEduDt()+" "+o2.getStartTmNm2()+"~"+o2.getEndTmNm2()+")");
						result.setResult(0);
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
	
}

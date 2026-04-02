package com.educare.edu.comn.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.educare.edu.comn.mapper.LectureDormiMapper;
import com.educare.edu.comn.mapper.LectureDormiPriceMapper;
import com.educare.edu.comn.mapper.LectureDormiStdntMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.model.LectureDormiPrice;
import com.educare.edu.comn.model.LectureDormiStdnt;
import com.educare.edu.comn.service.LectureDormiService;
import com.educare.edu.comn.vo.LectureDormiVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.DateUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


/**
 *
 *
 */
@Service("LectureDormiService")
public class LectureDormiServiceImpl implements LectureDormiService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LectureDormiServiceImpl.class.getName());

	@Resource(name="LectureDormiMapper")
	private LectureDormiMapper lectureDormiMapper;
	@Resource(name="LectureDormiStdntMapper")
	private LectureDormiStdntMapper lectureDormiStdntMapper;
	@Resource(name="LectureDormiPriceMapper")
	private LectureDormiPriceMapper lectureDormiPriceMapper;
	@Resource(name="LectureStdntMapper")
	private LectureStdntMapper lectureStdntMapper;
	
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	

	@Override
	public ResultVO getLectureDormiPageList(LectureDormiVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//전체 수
			int totalCnt = lectureDormiMapper.selectPageTotalCnt(vo);
			vo.setRowCnt(10);
			//페이징설정
			PaginationInfo page = new PaginationInfo();
			page.setTotalRecordCount(totalCnt);
			page.setCurrentPageNo(vo.getPage());
			page.setRecordCountPerPage(vo.getRowCnt());
			page.setPageSize(10);
			
	        vo.setFirstIndex(page.getFirstRecordIndex());
			
			//목록
			List<LectureDormiVO> list = lectureDormiMapper.selectPageList(vo);
			
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
	public LectureDormiVO getLectureDormi(int dormiSeq) {
		LectureDormiVO vo = new LectureDormiVO();
		vo.setDormiSeq(dormiSeq);
		LectureDormiVO room = lectureDormiMapper.selectByPk(vo);
		return room;
	}

	@Override
	public ResultVO saveLectureDormi(LectureDormiVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String userId = SessionUserInfoHelper.getUserId();
			Date nowDt = DateUtil.getCalendar().getTime();
			vo.setRegId(userId);
			vo.setRegDe(nowDt);
			vo.setUpdId(userId);
			vo.setUpdDe(nowDt);
			
			//신규,수정 여부
			if(vo.getDormiSeq()>0){
				lectureDormiMapper.updateByPk(vo);
			}else{
				lectureDormiMapper.insertByPk(vo);
			}
			rstData.put("dormiSeq", vo.getDormiSeq());
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getLectureDormiList(int eduSeq,String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//수업정보
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			if(ObjectUtils.isEmpty(lctre)) {
				result.setMsg("오류"); result.setResult(0); return result;
			}
			
			String eduPeriodBegin = lctre.getEduPeriodBegin();//숙소배정시작일시
			String eduPeriodEnd = lctre.getEduPeriodEnd();//숙소배정종료일시
			
			LectureDormiVO param = new LectureDormiVO();
			param.setSrchStartDt(eduPeriodBegin);
			param.setSrchEndDt(eduPeriodEnd);
			param.setEduSeq(eduSeq);
			param.setUserId(userId);
			
			List<LectureDormiVO> list = lectureDormiMapper.getLectureDormiListForPeriod(param);
			rstData.put("list", list);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Transactional
	@Override
	public ResultVO setClassDormi(int eduSeq, List<Integer> dormiSeqs,String userId, int dormiSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			boolean isEach = false;//개인 배정 설정 여부
			if(!ObjectUtils.isEmpty(userId) && dormiSeq > 0) {
				isEach = true;
			}
			
			//수업정보
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			if(ObjectUtils.isEmpty(lctre)) {
				result.setMsg("오류"); result.setResult(0); return result;
			}
			
			String eduPeriodBegin = lctre.getEduPeriodBegin();//숙소배정시작일시
			String eduPeriodEnd = lctre.getEduPeriodEnd();//숙소배정종료일시
			
			LectureDormiVO param = new LectureDormiVO();
			param.setRowCnt(0);
			param.setSrchStartDt(eduPeriodBegin);
			param.setSrchEndDt(eduPeriodEnd);
			param.setEduSeq(eduSeq);
			if(isEach) {
				param.setSrchUserId(userId);
				param.setSrchDormiSeq(dormiSeq);
			}else {
				param.setSrchDormiSeqArr(dormiSeqs);
				LectureDormiStdnt param2 = new LectureDormiStdnt();
				param2.setEduSeq(eduSeq);
				lectureDormiStdntMapper.deleteByParam(param2);//방배정 전체 삭제
			}
			
			//학생명단, 배정정보포함
			List<LectureDormiVO> stdntList = lectureDormiMapper.selectLectureStdntListForAssign(param);
			
			// 1. 빈 숙소 조회 (기간 기준)
			param.setEmptySt(1);
		    List<LectureDormiVO> dormiList = lectureDormiMapper.getLectureDormiListForPeriod(param);

		    // 학생목록 순서 랜덤 섞기
		    Collections.shuffle(stdntList);

		    // 숙소목록 순서 랜덤 섞기
		    Collections.shuffle(dormiList);
		    
		    // 2. 총 남은 정원 계산
		    int totalCapacity = dormiList.stream()
		                                 .mapToInt(d -> d.getCapaCnt() - d.getAssignedCnt())
		                                 .sum();

        	if(ObjectUtils.isEmpty(dormiList)) {
        		result.setMsg("정원이 모두 찼습니다. 배정할 수 없습니다.");
		    	result.setResult(0);//
		    	return result;
        	}
		    	
        	// 5. 학생 배정
        	List<LectureDormiVO> unassigned = new ArrayList<>();
        	for (LectureDormiVO stdnt : stdntList) {
        	    boolean assigned = false;

        	    LOG.info("[학생 배정 시작] 이름: {}, 성별: {}, 요청인원: {}, 요청장애인: {}", stdnt.getUserNm(), stdnt.getMfTypeNm(), stdnt.getRceptCapaCnt(), stdnt.getRceptAccessYn());

        	    LectureDormiVO assignDorm = assignDormi(stdnt,dormiList);//방구하기
        	    if (assignDorm != null) {
    	            insertAssign(stdnt, assignDorm, eduPeriodBegin, eduPeriodEnd, eduSeq, isEach);
    	            assignDorm.setAssignedCnt(assignDorm.getAssignedCnt() + 1);
    	            //assigned = true;
    	            //LOG.info("  [배정 완료] {} -> {} (잔여 자리: {})", stdnt.getUserNm(), assignDorm.getDormiNm(), assignDorm.getCapaCnt() - assignDorm.getAssignedCnt());
    	            //break;
    	        } else {
    	            //LOG.info("  [배정 불가] {} -> {} (장애인 여부 mismatch or 방 타입 mismatch)", stdnt.getUserNm(), assignDorm.getDormiNm());
    	        }
        	}

            // 6. 결과 처리
            if (!isEach && unassigned.size() > 0) {
                result.setMsg("정원이 모두 차서 일부만 배정되었습니다.");
                result.setResult(2); // 일부배정
                return result;
            }
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			e.printStackTrace();
			result.setResult(0);
			return result;
		}
	}
	
	/**
	 * 방배정 함수
	 * @param stdnt: 학생정보
	 * @param dormiList : 방목록
	 * @return 
	 */
	private LectureDormiVO assignDormi(LectureDormiVO stdnt,List<LectureDormiVO> dormiList){
		LOG.info("dormiListCnt : {}",dormiList.size());
		//장애인
		if("Y".equals(stdnt.getRceptAccessYn())){
			LOG.info("장애인원해요");
			if(stdnt.getRceptCapaCnt() <= 2){//2인실 원할때
				LOG.info("2인실원해요.");
				dormiList.sort(
						Comparator.comparing(LectureDormiVO::getAccessYn).reversed()
						.thenComparing(Comparator.comparingInt(LectureDormiVO::getCapaCnt))
						.thenComparing(Comparator.comparingInt(LectureDormiVO::getAssignedCnt).reversed())
				);
			}else{//4인실원할때
				LOG.info("4인실원해요");
				dormiList.sort(
						Comparator.comparing(LectureDormiVO::getAccessYn).reversed()
						.thenComparing(Comparator.comparingInt(LectureDormiVO::getCapaCnt).reversed())
						.thenComparing(Comparator.comparingInt(LectureDormiVO::getAssignedCnt).reversed())
				);
			}
		}else{
			if(stdnt.getRceptCapaCnt() <= 2){//2인실 원할때
				LOG.info("2인실원해요.");
				dormiList.sort(
						Comparator.comparing(LectureDormiVO::getAccessYn)
						.thenComparing(Comparator.comparingInt(LectureDormiVO::getCapaCnt))
						.thenComparing(Comparator.comparingInt(LectureDormiVO::getAssignedCnt).reversed())
				);
			}else{//4인실원할때
				LOG.info("4인실원해요");
				dormiList.sort(
						Comparator.comparing(LectureDormiVO::getAccessYn)
						.thenComparing(Comparator.comparingInt(LectureDormiVO::getCapaCnt).reversed())
						.thenComparing(Comparator.comparingInt(LectureDormiVO::getAssignedCnt).reversed())
				);
			}
		}
		LOG.info("여기로 오나");
		for (LectureDormiVO dorm : dormiList) {
			LOG.info("정렬확인2 : 몇인실:{},배정수:{},장애인여부:{}",dorm.getCapaCnt(),dorm.getAssignedCnt(),dorm.getAccessYn()); 
		}
		LOG.info("여기로 오나2");
		for (LectureDormiVO dorm : dormiList) {
			LOG.info("	방정보: 몇인실:{},배정수:{},장애인여부:{}",dorm.getCapaCnt(),dorm.getAssignedCnt(),dorm.getAccessYn()); 
			if(dorm.getAssignedCnt() >= dorm.getCapaCnt()){
				LOG.info("	배정실패1");
				continue;
			}
			
			
			// 방 성별 확인
	        String dormGender = dorm.getMfTypeNmTmp();
	        if (dormGender != null) {
	        	if(!dormGender.equals(stdnt.getMfTypeNm())){
	        		LOG.info("	배정실패3");
	        		continue;
	        	}
	        }
	        
	        //최종 배정방
	        LOG.info("	배정성공");
	        dorm.setMfTypeNmTmp(stdnt.getMfTypeNm());
	        return dorm;
	        
		}
		
		/*
	    // 5-1. 요청 인원수 + 장애인/일반 우선 배정
	    for (LectureDormiVO dorm : dormiList) {
	        boolean isAssign = false;
	    	int remaining = dorm.getCapaCnt() - dorm.getAssignedCnt();
	        if (remaining <= 0) continue;//정원초과

	        // 장애인 학생 우선 배정
	        if ("Y".equals(stdnt.getRceptAccessYn()) && "Y".equals(dorm.getAccessYn())) {
	        	isAssign = true;//배정
	        } 
	        
	        if (stdnt.getRceptCapaCnt() != 0 && stdnt.getRceptCapaCnt() != dorm.getCapaCnt()){
	        	isAssign = true;//배정
	        }
	        
	        // 방 성별 확인
	        String dormGender = dorm.getMfTypeNmTmp();
	        if (dormGender == null) {
	        	dormGender = stdnt.getMfTypeNm();
	        	//dorm.setMfTypeNmTmp(dormGender);
	        }

	        
	        // 요청인원수 체크

	        if (!dormGender.equals(stdnt.getMfTypeNm())) continue;

	        
	        String dormGender = dorm.getMfTypeNmTmp();
            if (dormGender == null) {
                dormGender = stdnt.getMfTypeNm();
                dorm.setMfTypeNmTmp(dormGender);
            } else if (!dormGender.equals(stdnt.getMfTypeNm()) && stdnt.getRceptCapaCnt() != 0) {
                continue;
            }
	    }
	    */
		return null;
	}
	// 공통 Insert 처리
	private void insertAssign(LectureDormiVO stdnt, LectureDormiVO dorm,
	                          String eduPeriodBegin, String eduPeriodEnd,
	                          int eduSeq, boolean isEach) {
	    LectureDormiStdnt assign = new LectureDormiStdnt();
	    assign.setDormiSeq(dorm.getDormiSeq());
	    assign.setUserId(stdnt.getUserId());
	    assign.setStartDt(eduPeriodBegin);
	    assign.setEndDt(eduPeriodEnd);
	    assign.setEduSeq(eduSeq);

	    if (isEach) {
	        lectureDormiStdntMapper.deleteByParam(assign);
	    }
	    lectureDormiStdntMapper.insertByPk(assign);
	}
	
	@Transactional
	@Override
	public ResultVO setFeeDormi(int eduSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			//수업정보
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			if(ObjectUtils.isEmpty(lctre)) {
				result.setMsg("오류"); result.setResult(0); return result;
			}
			
			String eduPeriodBegin = lctre.getEduPeriodBegin();//숙소배정시작일시
			String eduPeriodEnd = lctre.getEduPeriodEnd();//숙소배정종료일시
			
			LectureDormiVO param = new LectureDormiVO();
			param.setRowCnt(0);
			param.setSrchStartDt(eduPeriodBegin);
			param.setSrchEndDt(eduPeriodEnd);
			param.setEduSeq(eduSeq);
			
			//학생명단, 배정정보포함
			List<LectureDormiVO> stdntList = lectureDormiMapper.selectLectureStdntListForAssign(param);
			
			// 1. 해당교육에 연결된 숙소 조회 (기간 기준)
			List<LectureDormiVO> dormiList = lectureDormiMapper.getLectureDormiListForPeriod(param);
			
			//요금정보조회
			LectureDormiPrice priceVO = new LectureDormiPrice();
			List<LectureDormiPrice> priceList = lectureDormiPriceMapper.selectByAll(priceVO);
			LectureDormiPrice offPrice = new LectureDormiPrice(); 
			LectureDormiPrice peakPrice = new LectureDormiPrice(); 
			for(LectureDormiPrice o: priceList) {
				if("off".equals(o.getSeasonGb())) {
					offPrice = o;
				}else {
					peakPrice = o;
				}
			}
			
			for(LectureDormiVO stdnt : stdntList) {
				LOG.info("[학생정보] 학생명: {}, 방번호: {}",stdnt.getUserNm(),stdnt.getDormiSeq());
				for (LectureDormiVO dorm : dormiList) {
					if(stdnt.getDormiSeq()== dorm.getDormiSeq()) {
						LOG.info("	[방정보] 방이름: {}, 인원: {}",dorm.getDormiNm(),dorm.getAssignedCnt());
						int offFee = 0;
						int peakFee = 0;
						//요금 
						if(dorm.getCapaCnt() == 2) {
							offFee = offPrice.getFee2();
							peakFee = peakPrice.getFee2();
						}else {
							offFee = offPrice.getFee4();
							peakFee = peakPrice.getFee4();
						}
						
						//일반일수
						int offCnt = DateUtil.calDays(eduPeriodBegin.replaceAll("-", ""), eduPeriodEnd.replaceAll("-", ""));
						//성수기일수
						int peakCnt = DateUtil.getOverlapDays(eduPeriodBegin, eduPeriodEnd, peakPrice.getStartDt(), peakPrice.getEndDt());
						offCnt = offCnt - peakCnt;
						LOG.info("	offCnt : {}",offCnt);
						LOG.info("	peakCnt : {}",peakCnt);
						
						//전체 숙박요금
						int totalFee = (offCnt*offFee)+(peakCnt*peakFee);
						
						LOG.info("	totalFee : {}",totalFee);
						
						//입실인원으로 나눠서 개인 요금 산출
						int dormiFee = 0;
						if(totalFee > 0) {
							dormiFee = totalFee / dorm.getAssignedCnt() / 10 * 10;
						}
						
						//숙박요금 저장
						LectureStdnt stdntVO = new LectureStdnt();
						stdntVO.setUpdDe(new Date());
						stdntVO.setUpdId(sessId);
						stdntVO.setEduSeq(eduSeq);
						stdntVO.setUserId(stdnt.getUserId());
						stdntVO.setDormiFee(dormiFee);
						lectureStdntMapper.updateByPk(stdntVO);
						
						break;
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
	@Transactional
	@Override
	public ResultVO delClassDormi(int eduSeq,String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			 LectureDormiStdnt assign = new LectureDormiStdnt();
	            assign.setUserId(userId);
	            assign.setEduSeq(eduSeq);
			lectureDormiStdntMapper.deleteByParam(assign);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	

	@Override
	public ResultVO getClassDormiStdntByEdu(int eduSeq,int row) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//수업정보
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			if(ObjectUtils.isEmpty(lctre)) {
				result.setMsg("오류"); result.setResult(0); return result;
			}
			String eduPeriodBegin = lctre.getEduPeriodBegin();//숙소배정시작일시
			String eduPeriodEnd = lctre.getEduPeriodEnd();//숙소배정종료일시
			
			LectureDormiVO param = new LectureDormiVO();
			param.setSrchStartDt(eduPeriodBegin);
			param.setSrchEndDt(eduPeriodEnd);
			param.setEduSeq(eduSeq);
			
			int totalCnt = lectureDormiMapper.selectLectureStdntPageCnt(param);
			param.setRowCnt(row);
			//페이징설정
			PaginationInfo page = new PaginationInfo();
			page.setTotalRecordCount(totalCnt);
			page.setCurrentPageNo(param.getPage());
			page.setRecordCountPerPage(param.getRowCnt());
			page.setPageSize(10);
	        param.setFirstIndex(page.getFirstRecordIndex());
			
			//학생명단, 배정정보포함
			List<LectureDormiVO> stdntList = lectureDormiMapper.selectLectureStdntListForAssign(param);
			
			rstData.put("stdnt", stdntList);
			rstData.put("page", page);
			
			//요금정보조회
			LectureDormiPrice priceVO = new LectureDormiPrice();
			List<LectureDormiPrice> priceList = lectureDormiPriceMapper.selectByAll(priceVO);
			LectureDormiPrice offPrice = new LectureDormiPrice(); 
			LectureDormiPrice peakPrice = new LectureDormiPrice(); 
			for(LectureDormiPrice o: priceList) {
				if("off".equals(o.getSeasonGb())) {
					offPrice = o;
				}else {
					peakPrice = o;
				}
			}
			//일반일수
			int offCnt = DateUtil.calDays(eduPeriodBegin.replaceAll("-", ""), eduPeriodEnd.replaceAll("-", ""));
			//성수기일수
			int peakCnt = DateUtil.getOverlapDays(eduPeriodBegin, eduPeriodEnd, peakPrice.getStartDt(), peakPrice.getEndDt());
			offCnt = offCnt - peakCnt;
			Map<String, Object> feeInfo = new HashMap<String, Object>();
			feeInfo.put("offCnt", offCnt);
			feeInfo.put("peakCnt", peakCnt);
			feeInfo.put("offFee2", offPrice.getFee2());
			feeInfo.put("offFee4", offPrice.getFee4());
			feeInfo.put("peakFee2", peakPrice.getFee2());
			feeInfo.put("peakFee4", peakPrice.getFee4());
			rstData.put("feeInfo", feeInfo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	@Override
	public List<LectureDormiVO> getAssignLectureDormi(int dormiSeq,String srchYear,String srchMonth) {
		LectureDormiVO vo = new LectureDormiVO();
		vo.setDormiSeq(dormiSeq);
		vo.setSrchYear(srchYear);
		vo.setSrchMonth(srchMonth);
		List<LectureDormiVO> list = lectureDormiMapper.getAssignLectureDormi(vo);
		return list;
	}
	
	@Override
	public ResultVO getClassDormiInfoByEduDt(String eduDt) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//
			LectureDormiVO vo = new LectureDormiVO();
			vo.setSrchDt(eduDt);
			List<LectureDormiVO> list = lectureDormiMapper.getAssignLectureDormi(vo);
			rstData.put("list", list);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO deleteLectureDormi(int dormiSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String userId = SessionUserInfoHelper.getUserId();
			Date nowDt = DateUtil.getCalendar().getTime();
			
			LectureDormiVO vo = new LectureDormiVO();
			vo.setUpdId(userId);
			vo.setUpdDe(nowDt);
			vo.setDormiSeq(dormiSeq);
			
			vo.setUseYn("D");//삭제
			lectureDormiMapper.updateByPk(vo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO savePriceProc(int feeOff2, int feeOff4, int feePeak2, int feePeak4, String startDt, String endDt) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			LectureDormiPrice param = new LectureDormiPrice();
			param.setSeasonGb("off");
			param.setFee2(feeOff2);
			param.setFee4(feeOff4);
			LectureDormiPrice price = lectureDormiPriceMapper.selectByPk(param);
			if(!ObjectUtils.isEmpty(price)){
				lectureDormiPriceMapper.updateByPk(param);
			}else{
				lectureDormiPriceMapper.insertByPk(param);
			}
			param.setSeasonGb("peak");
			param.setFee2(feePeak2);
			param.setFee4(feePeak4);
			param.setStartDt(startDt);
			param.setEndDt(endDt);
			price = lectureDormiPriceMapper.selectByPk(param);
			if(!ObjectUtils.isEmpty(price)){
				lectureDormiPriceMapper.updateByPk(param);
			}else{
				lectureDormiPriceMapper.insertByPk(param);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public List<LectureDormiPrice> getLectureDormiPrice() {
		LectureDormiPrice vo = new LectureDormiPrice();
		List<LectureDormiPrice> price =lectureDormiPriceMapper.selectByAll(vo);
		return price;
	}
}

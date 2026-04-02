package com.educare.edu.comn.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.ExcelService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.web.AdminExcelController;
import com.educare.edu.member.service.MemberVO;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.edu.member.service.model.UserPrivt;
import com.educare.util.EhCacheUtil;
import com.educare.util.LncUtil;

@Service("ExcelService")
public class ExcelServiceImpl implements ExcelService {

	private static final Logger LOG = LoggerFactory.getLogger(AdminExcelController.class.getName());

	@Resource(name = "LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;

	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;

	// @Resource(name = "eccp")
	// private EhCacheComponent ehCacheComponent;

	@Override
	@Transactional
	public ResultVO getLectureScheduleByExcel(MultipartHttpServletRequest mr) {
		ResultVO result = new ResultVO();
		// String message = "";
		// String resultCode = "1";
		int errorRow = 0;

		int eduSeq = 2;
		// int subSeq = 0;
		int timeSeq = 1;

		try {
			// 파일추출
			MultipartFile mf = mr.getFile("fileObj");
			// LOG.debug("mr:{}", mr);
			// LOG.debug("file name:{}", mf.getOriginalFilename());
			// 엑셀 추출
			Workbook workbook = null;
			Sheet sheet = null;
			workbook = WorkbookFactory.create(mf.getInputStream());
			sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getLastRowNum();
			LOG.debug("rowCount:{}", rowCount);

			if (rowCount > 1) {
				// List<Agency> tempList = new ArrayList<Agency>();

				lectureTimeMapper.deleteByEduSeq(eduSeq);

				// 검증 단계
				String eduDt = "";
				String startTm = "";
				String endTm = "";
				String vacation = "";
				String timeTp = "";
				String instrNm = "";
				String checkLocate = "";
				String description = "";
				boolean dataOn = false;
				for (int i = 2; i <= rowCount; i++) {
					errorRow = (i + 1);
					Row row = sheet.getRow(i);
					String eduDtNew = LncUtil.getExcelToString(row.getCell(0));
					if (eduDt.equals(eduDtNew)) {
						endTm = LncUtil.getExcelToString(row.getCell(4));
					}

					if ("1".equals(timeTp)) {
						// insert LectureTime to DB
						LOG.debug("timeSeq:{}, eduDt:{}, startTm: {}, endTm:{}, instrNm:{}, description:{}", timeSeq,
								eduDt, startTm, endTm, instrNm, description);
						LectureTime ltVO = getLectureTime(eduSeq, eduDt, startTm, endTm, description);
						int result1 = lectureTimeMapper.insertByPk(ltVO);
						timeSeq++;
					}

					eduDt = eduDtNew;
					endTm = LncUtil.getExcelToString(row.getCell(2));
					vacation = LncUtil.getExcelToString(row.getCell(3));
					startTm = LncUtil.getExcelToString(row.getCell(4));
					timeTp = LncUtil.getExcelToString(row.getCell(5));
					instrNm = LncUtil.getExcelToString(row.getCell(6));
					checkLocate = LncUtil.getExcelToString(row.getCell(7));
					description = LncUtil.getExcelToString(row.getCell(8));

					instrNm = instrNm.split("_")[0];

					/*
					 * String addr2 = LncUtil.getExcelToString(row.getCell(2)); String addr3 =
					 * LncUtil.getExcelToString(row.getCell(3)); String typeNm =
					 * LncUtil.getExcelToString(row.getCell(4)); String ccNm =
					 * LncUtil.getExcelToString(row.getCell(5)); String addrDesc =
					 * LncUtil.getExcelToString(row.getCell(73)); String zip =
					 * LncUtil.getExcelToString(row.getCell(74)); String ccCd =
					 * LncUtil.getExcelToString(row.getCell(75));
					 */

					/*
					 * if((addr1+addr2+addr3+typeNm+ccNm+addrDesc+zip+ccCd).contains("ERROR")){
					 * result.setResult(0); result.setMsg(errorRow+ "행에서 오류가 발생하였습니다. 1"); break; }
					 * 
					 * if(addr3.length()>40){ resultCode="0"; message=(errorRow+
					 * "행에서 오류가 발생하였습니다. 2"); break; }
					 * 
					 * if(zip.length()>7){ resultCode="0"; message=(errorRow+ "행에서 오류가 발생하였습니다. 3");
					 * break; } System.out.println(ccCd); if(ObjectUtils.isEmpty(ccCd)){
					 * resultCode="0"; message=(errorRow+ "행에서 오류가 발생하였습니다. ccCd"); break; }
					 * 
					 * //System.out.println(addr1+"	"+addr2+"	"+addr3+"	"+typeNm+"	"
					 * +ccNm+"	"+addrDesc+"	"+zip+"	"+ccCd); Agency agency = new
					 * Agency(addr1,addr2,addr3,typeNm,ccNm,addrDesc,zip,ccCd);
					 * tempList.add(agency);
					 */
				}

				if ("1".equals(timeTp)) {
					// insert LectureTime to DB
					LOG.debug("timeSeq:{}, eduDt:{}, startTm: {}, endTm:{}, instrNm:{}, description:{}", timeSeq, eduDt,
							startTm, endTm, instrNm, description);
					LectureTime ltVO = getLectureTime(eduSeq, eduDt, startTm, endTm, description);
					int result1 = lectureTimeMapper.insertByPk(ltVO);
				}
			}
		} catch (NullPointerException | IOException e) {
			result.setResult(0);
			result.setMsg(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		result.setResult(1);
		result.setMsg("Success");
		return result;
	}

	@Override
	@Transactional
	public ResultVO getLectureStdntByExcel(MultipartHttpServletRequest mr) {
		ResultVO result = new ResultVO();
		// String message = "";
		// String resultCode = "1";
		int errorRow = 0;

		int eduSeq = 2;
		// int subSeq = 0;
		int timeSeq = 1;

		/*
		 * MemberVO vo = new MemberVO(); List<UserInfoStdnt> userList =
		 * memberMapper.selectStdntList(vo); LOG.debug("==cache 1=="+userList.size());
		 * 
		 * for(UserInfoStdnt user : userList){ LOG.debug("UserId:{}, UserNm:{}",
		 * user.getUserId(), user.getUserNm()); }
		 */

		UserPrivt userPrivt = EhCacheUtil.getUserPrvt("1234"); // userId

		try {
			// 파일추출
			MultipartFile mf = mr.getFile("fileObj");
			// LOG.debug("mr:{}", mr);
			// LOG.debug("file name:{}", mf.getOriginalFilename());

			// 엑셀 추출
			Workbook workbook = null;
			Sheet sheet = null;
			workbook = WorkbookFactory.create(mf.getInputStream());
			sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getLastRowNum();
			LOG.debug("rowCount:{}", rowCount);

			if (rowCount > 1) {
				// List<Agency> tempList = new ArrayList<Agency>();

				// lectureTimeMapper.deleteByEduSeq(eduSeq);

				// 검증 단계
				for (int i = 16; i <= rowCount; i++) {
					errorRow = (i + 1);
					Row row = sheet.getRow(i);
					String no = LncUtil.getExcelToString(row.getCell(0));
					String stdNm = LncUtil.getExcelToString(row.getCell(1));
					String krIdNm = LncUtil.getExcelToString(row.getCell(2));

					int colCnt = row.getLastCellNum();

					// instrNm = instrNm.split("_")[0];

					// insert LectureTime to DB
					LOG.debug("i:{}, colCnt:{}, no:{}, stdNm:{}, krIdNm:{}", i, colCnt, no, stdNm, krIdNm);
					// LectureTime ltVO = getLectureTime(eduSeq, eduDt, startTm, endTm,
					// description);
					// int result1 = lectureTimeMapper.insertByPk(ltVO);
					// timeSeq++;

				}
			}
		} catch (NullPointerException | IOException e) {
			result.setResult(0);
			result.setMsg(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		result.setResult(1);
		result.setMsg("Success");
		return result;
	}

	LectureTime getLectureTime(int eduSeq, String eduDt, String startTm, String endTm, String description) {
		LectureTime ltVO = new LectureTime();
		ltVO.setEduSeq(eduSeq);
		ltVO.setEduDt(eduDt);
		ltVO.setStartTm(startTm);
		ltVO.setEndTm(endTm);
		ltVO.setDescription(description);

		return ltVO;
	}



}

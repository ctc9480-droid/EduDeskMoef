package com.educare.edu.sample.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.mapper.CheckMapper;
import com.educare.edu.comn.mapper.EtcGradeCodeMapper;
import com.educare.edu.comn.mapper.EtcOrgCodeMapper;
import com.educare.edu.comn.mapper.ExamBankChoiceMapper;
import com.educare.edu.comn.mapper.ExamBankQuestionMapper;
import com.educare.edu.comn.mapper.FeedbackAnswerMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.ExamBankChoice;
import com.educare.edu.comn.model.ExamBankQuestion;
import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.EtcGradeCodeVO;
import com.educare.edu.comn.vo.EtcOrgCodeVO;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.exam.mapper.ExamMapper;
import com.educare.edu.exam.service.ExamService;
import com.educare.edu.exam.vo.ExamVO;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.sample.mapper.SampleMapper;
import com.educare.edu.sample.service.SampleService;
import com.educare.edu.sample.vo.SampleVO;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("SampleService")
public class SampleServiceImpl implements SampleService {

	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SampleServiceImpl.class.getName());

	@Resource(name = "SampleMapper")
	private SampleMapper sampleMapper;
	@Resource(name = "EtcOrgCodeMapper")
	private EtcOrgCodeMapper etcOrgCodeMapper;
	@Resource(name = "EtcGradeCodeMapper")
	private EtcGradeCodeMapper etcGradeCodeMapper;

	@Override
	public List<SampleVO> getSampleList(SampleVO vo) {
		List<SampleVO> list = sampleMapper.selectSampleList(vo);
		return list;
	}

	@Override
	public ResultVO setEtcOrgList(MultipartHttpServletRequest mr) {
		ResultVO result = new ResultVO();

		try {
			MultipartFile file = mr.getFile("orgFile");
			if (file == null || file.isEmpty()) {
				result.setResult(0);
				result.setMsg("파일이 존재하지 않습니다.");
				return result;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "MS949"));

			String line;
			boolean firstLine = true;

			// 1. DB에서 기존 기관코드 모두 조회
			List<String> existingCodes = etcOrgCodeMapper.selectByAll();
			Set<String> existSet = new HashSet<>(existingCodes);

			// 2. Batch용 리스트
			List<EtcOrgCodeVO> batchList = new ArrayList<>();
			int insertCount = 0;

			while ((line = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false; // 헤더 스킵
					continue;
				}

				String[] cols = line.split("\t");
				if (cols.length >= 3) {
					String code = cols[0].trim();
					if (!existSet.contains(code)) { // Java에서 중복 체크
						EtcOrgCodeVO vo = new EtcOrgCodeVO();
						vo.setOrgCd(code);
						vo.setFullNm(cols[1].trim());
						vo.setLowNm(cols[2].trim());

						batchList.add(vo);
						existSet.add(code); // Set에 추가

						// Batch 사이즈 500마다 insert
						if (batchList.size() >= 500) {
							etcOrgCodeMapper.insertBatch(batchList);
							insertCount += batchList.size();
							batchList.clear();
						}
					}
				}
			}

			// 남은 리스트 insert
			if (!batchList.isEmpty()) {
				etcOrgCodeMapper.insertBatch(batchList);
				insertCount += batchList.size();
			}

			result.setResult(1);
			result.setMsg(insertCount + "건이 등록되었습니다.");
			return result;
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			result.setResult(0);
			result.setMsg(e.getMessage());
			return result;
		}

	}

	@Override
	public ResultVO setEtcGradeList(MultipartHttpServletRequest mr) {
		ResultVO result = new ResultVO();

		try {
			MultipartFile file = mr.getFile("etcGradeFile");
			if (file == null || file.isEmpty()) {
				result.setResult(0);
				result.setMsg("파일이 존재하지 않습니다.");
				return result;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "MS949"));

			String line;
			boolean firstLine = true;

			// 1. DB에서 기존 기관코드 모두 조회
			List<String> existingCodes = etcGradeCodeMapper.selectByAll();
			Set<String> existSet = new HashSet<>(existingCodes);

			// 2. Batch용 리스트
			List<EtcGradeCodeVO> batchList = new ArrayList<>();
			int insertCount = 0;

			while ((line = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false; // 헤더 스킵
					continue;
				}

				String[] cols = line.split("\t");
				if (cols.length >= 3) {
					String code = cols[0].trim();
					if (!existSet.contains(code)) { // Java에서 중복 체크
						EtcGradeCodeVO vo = new EtcGradeCodeVO();
						vo.setGradeCd(code);
						vo.setGongGbCd(cols[1].trim());
						vo.setJikjongCd(cols[2].trim());
						vo.setJikgunCd(cols[4].trim());
						vo.setJikryulCd(cols[5].trim());
						vo.setJikryuCd(cols[6].trim());
						vo.setGradeNm(cols[8].trim());
						vo.setCloseSt(LncUtil.nvlInt(cols[9].trim()));

						batchList.add(vo);
						existSet.add(code); // Set에 추가

						// Batch 사이즈 500마다 insert
						if (batchList.size() >= 500) {
							etcGradeCodeMapper.insertBatch(batchList);
							insertCount += batchList.size();
							batchList.clear();
						}
					}
				}
			}

			// 남은 리스트 insert
			if (!batchList.isEmpty()) {
				etcGradeCodeMapper.insertBatch(batchList);
				insertCount += batchList.size();
			}

			result.setResult(1);
			result.setMsg(insertCount + "건이 등록되었습니다.");
			return result;
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			result.setResult(0);
			result.setMsg(e.getMessage());
			return result;
		}

	}

	@Override
	public ResultVO setEtcGradeList2(MultipartHttpServletRequest mr) {
		ResultVO result = new ResultVO();

		try {
			MultipartFile jikjongFile = mr.getFile("etcJikjongFile"); // 직종
			MultipartFile jikryulFile = mr.getFile("etcJikryulFile"); // 직렬
			MultipartFile jikryuFile = mr.getFile("etcJikryuFile"); // 직류

			// 직종 업데이트
			if (jikjongFile != null && !jikjongFile.isEmpty()) {
				List<Map<String, Object>> list = parseExcel(jikjongFile);
				for (Map<String, Object> row : list) {
					String code = (String) row.get("code");
					String name = (String) row.get("name");
					// DAO 호출
					EtcGradeCodeVO vo = new EtcGradeCodeVO();
					vo.setJikjongCd(code);
					vo.setJikjongNm(name);
					etcGradeCodeMapper.updateByParam(vo);
				}
			}

			// 직렬 업데이트
			if (jikryulFile != null && !jikryulFile.isEmpty()) {
				List<Map<String, Object>> list = parseExcel(jikryulFile);
				for (Map<String, Object> row : list) {
					String code = (String) row.get("code");
					String name = (String) row.get("name");
					EtcGradeCodeVO vo = new EtcGradeCodeVO();
					vo.setJikryulCd(code);
					vo.setJikryulNm(name);
					etcGradeCodeMapper.updateByParam(vo);
				}
			}

			// 직류 업데이트
			if (jikryuFile != null && !jikryuFile.isEmpty()) {
				List<Map<String, Object>> list = parseExcel(jikryuFile);
				for (Map<String, Object> row : list) {
					String code = (String) row.get("code");
					String name = (String) row.get("name");
					EtcGradeCodeVO vo = new EtcGradeCodeVO();
					vo.setJikryuCd(code);
					vo.setJikryuNm(name);
					etcGradeCodeMapper.updateByParam(vo);
				}
			}

			result.setResult(1);
			return result;
		} catch (NullPointerException|IOException e) {
			e.printStackTrace();
			result.setResult(0);
			result.setMsg(e.getMessage());
			return result;
		}

	}

	/**
	 * 엑셀 파싱 (첫 행은 헤더, 두 번째 행부터 데이터라고 가정)
	 */
	private List<Map<String, Object>> parseExcel(MultipartFile file) throws IOException {
		List<Map<String, Object>> list = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			for (int i = 1; i < rows; i++) { // 1행은 header라 가정
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				Map<String, Object> map = new HashMap<>();
				map.put("code", getCellValue(row.getCell(0)));
				map.put("name", getCellValue(row.getCell(1)));

				list.add(map);
			}
		}catch (IOException e) {
			
		}

		return list;
	}

	private String getCellValue(Cell cell) {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			return String.valueOf((long) cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

}

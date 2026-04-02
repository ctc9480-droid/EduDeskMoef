package com.educare.edu.quizBank.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.mapper.CheckMapper;
import com.educare.edu.comn.mapper.ExamBankChoiceMapper;
import com.educare.edu.comn.mapper.ExamBankQuestionMapper;
import com.educare.edu.comn.mapper.FeedbackAnswerMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.QuestionChoiceMapper;
import com.educare.edu.comn.mapper.QuestionMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.ExamBankChoice;
import com.educare.edu.comn.model.ExamBankQuestion;
import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.Question;
import com.educare.edu.comn.model.QuestionChoice;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.LectureRoomVO;
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
import com.educare.edu.quizBank.mapper.QuizBankMapper;
import com.educare.edu.quizBank.service.QuizBankService;
import com.educare.edu.quizBank.vo.QuizBankVO;
import com.educare.edu.quizBank.web.QuizBankComponent;
import com.educare.edu.quizCate.vo.QstnCategoryVO;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.mysql.fabric.xmlrpc.base.Array;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("QuizBankService")
public class QuizBankServiceImpl implements QuizBankService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(QuizBankServiceImpl.class.getName());

	@Resource(name = "QuizBankMapper")
	private QuizBankMapper quizBankMapper;
	@Resource(name = "QuestionChoiceMapper")
	private QuestionChoiceMapper questionChoiceMapper;
	@Resource(name = "QuestionMapper")
	private QuestionMapper questionMapper;
	
	

	@Override
	public ResultVO getQuizBankInfo(int qstnSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			QuizBankVO vo = new QuizBankVO();
			vo.setQstnSeq(qstnSeq);
			
			//문제가져오기
			Question qstn = questionMapper.selectByPk(vo);
			rstData.put("qstn", qstn);
			
			//보기가져오기
			List<QuestionChoice> chList = questionChoiceMapper.selectByQstn(vo);
			for(QuestionChoice o: chList){
				o.setChAns("Y".equals(o.getChAnsYn())?true:false);
			}
			rstData.put("chList", chList);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}


	@Override
	public ResultVO saveQuizBankFile(MultipartHttpServletRequest mhsr,int qstnSeq,String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String prefixQstn = "qstn_"+qstnSeq;//문제파일 프리픽스
			
			Iterator<String> itr = mhsr.getFileNames();
			while(itr.hasNext()){
				String fileName = itr.next();
				LOG.info(fileName);
				MultipartFile mf = mhsr.getFile(fileName);
				//if(ObjectUtils.isEmpty(mf)){
				//	continue;
				//}
				if(mf.isEmpty()){
					continue;
				}
				String uploadPath = QuizBankComponent.UPLOAD_PATH+qstnSeq+"/";
				String fileOrg = LncUtil.getEncode(mf.getOriginalFilename()); 
				LOG.info(fileOrg);
				ResultVO result2 = new ResultVO();
				if("qstnFn".equals(fileName)){
					String fileRename = FileUtil.getFileRename(fileOrg, prefixQstn);
					result2 = FileUtil.multiPartupload(mf, uploadPath, fileOrg, fileRename);
					if(result2.getResult() == 1){
						Question qstnVO = new Question();
						qstnVO.setQstnSeq(qstnSeq);
						qstnVO.setQstnFnOrg(fileOrg);
						qstnVO.setQstnFnRnm(fileRename);
						qstnVO.setUpdDe(new Date());
						qstnVO.setUpdId(userId);
						questionMapper.updateByFile(qstnVO);
					}
				}else{
					if(fileName.contains("chFn_")){
						String[] parts = fileName.split("_");
						int chSeq = Integer.parseInt(parts[1])+1;
						String fileRename = FileUtil.getFileRename(fileOrg, prefixQstn+"_ch_"+chSeq);
						result2 = FileUtil.multiPartupload(mf, uploadPath, fileOrg, fileRename);
						
						if(result2.getResult() == 1){
							QuestionChoice chVO = new QuestionChoice();
							chVO.setQstnSeq(qstnSeq);
							chVO.setChSeq(chSeq);
							chVO.setChFnOrg(fileOrg);
							chVO.setChFnRnm(fileRename);
							chVO.setUpdDe(new Date());
							chVO.setUpdId(userId);
							questionChoiceMapper.updateByFile(chVO);
						}
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
	public ResultVO saveQuizBank(String userId,int qstnSeq, String qstnNm, int ctg1Seq, int ctg2Seq, int ctg3Seq, int diffType, int qstnTp, String qstnStr, String qstnDesc, String ansDesc, String useYn,String chListJson,String fillBlank) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//나중에 삭제가능한 문제인지 유효성 체크해야함
			
			String regId = userId;
			String updId = userId;
			Date regDe = DateUtil.getCalendar().getTime();
			Date updDe = DateUtil.getCalendar().getTime();
			
			int optionCnt = 0;
			
			//문제저장
			Question qstnVO = new Question(qstnNm, qstnTp, ctg1Seq, ctg2Seq,ctg3Seq, diffType, qstnDesc, qstnStr, null, null, optionCnt, fillBlank, ansDesc, useYn, regId, updId, regDe, updDe);
			if(qstnSeq>0){
				//수정
				qstnVO.setQstnSeq(qstnSeq);
				questionMapper.updateByPk(qstnVO);
			}else{
				//저장
				questionMapper.insertByPk(qstnVO);
			}
			
			//보기json 파싱
			ObjectMapper om = new ObjectMapper();
			LOG.info(LncUtil.unescapeJson(chListJson));
			List<QuestionChoice> chList = om.readValue(LncUtil.unescapeJson(chListJson), new TypeReference<List<QuestionChoice>>() {});
			
			//일괄 대기상태
			questionChoiceMapper.updateUseNByQstnSeq(qstnSeq);
			int chSeq = 0;
			for(QuestionChoice o : chList){
				chSeq++;
				o.setChSeq(chSeq);
				o.setQstnSeq(qstnVO.getQstnSeq());
				o.setUpdDe(updDe);
				o.setUpdId(updId);
				o.setUseYn("Y");
				LOG.debug(">>"+o.isChAns());
				o.setChAnsYn(o.isChAns()?"Y":"N");
				
				QuestionChoice ch = questionChoiceMapper.selectByPk(o);
				if(ObjectUtils.isEmpty(ch)){
					//등록
					questionChoiceMapper.insertByPk(o);
				}else{
					//수정
					questionChoiceMapper.updateByPk(o);
				}
			}
			
			//임시삭제상태 삭제처리
			questionChoiceMapper.deleteByUseN(qstnSeq);
			
			rstData.put("qstnSeq", qstnVO.getQstnSeq());
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | IOException e) {
			result.setResult(0);
			return result;
		}
	}


	@Override
	public ResultVO getQuizBankPageList(int pageNo,int rowCnt,String srchWrd,int srchQstnTp,int srchCtg1Seq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			QuizBankVO vo = new QuizBankVO();
			vo.setSrchWrd(srchWrd);
			vo.setSrchCtg1Seq(srchCtg1Seq);
			vo.setSrchQstnTp(srchQstnTp);
			
			//전체 수
			int totalCnt = quizBankMapper.selectQuizBankPageCnt(vo);
			
			//페이징설정
			PaginationInfo page = new PaginationInfo();
			page.setTotalRecordCount(totalCnt);
			page.setCurrentPageNo(pageNo);
			page.setRecordCountPerPage(rowCnt);
			page.setPageSize(10);
	        vo.setRowCnt(rowCnt);
	        vo.setFirstIndex(page.getFirstRecordIndex());
			
			//목록
			List<QuizBankVO> list = quizBankMapper.selectQuizBankPageList(vo);
			
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
	public ResultVO getQuizBankList(int testTmplSeq,String srchWrd, int srchCtg1Seq, int srchCtg2Seq, int srchCtg3Seq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			QuizBankVO vo = new QuizBankVO();
			vo.setSrchWrd(srchWrd);
			vo.setTestTmplSeq(testTmplSeq);
			vo.setSrchCtg1Seq(srchCtg1Seq);
			vo.setSrchCtg2Seq(srchCtg2Seq);
			vo.setSrchCtg3Seq(srchCtg3Seq);
			
			//목록
			List<QuizBankVO> list = quizBankMapper.selectQuizBankList(vo);
			rstData.put("list", list);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}


	@Override
	public ResultVO delQuizBankFile(int qstnSeq, int chSeq,String fnRnm) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			if(qstnSeq < 1){
				result.setMsg("error");
				result.setResult(0);
				return result;
			}
			
			if(chSeq == -1 || chSeq > 0){
				String sessId = SessionUserInfoHelper.getUserId();
				
				String filepath = QuizBankComponent.UPLOAD_PATH+qstnSeq+"/"+fnRnm;
				//파일 삭제
				boolean isSucc = FileUtil.delete(filepath);
				
				if(!isSucc){
					result.setMsg("error");
					result.setResult(0);
					return result;
				}
				
				//db처리
				if(chSeq == -1){
					//문제파일 
					Question qstnVO = new Question();
					qstnVO.setQstnSeq(qstnSeq);
					qstnVO.setUpdDe(new Date());
					qstnVO.setUpdId(sessId);
					questionMapper.updateByFile(qstnVO);
				}else if(chSeq > 0){
					QuestionChoice chVO = new QuestionChoice();
					chVO.setQstnSeq(qstnSeq);
					chVO.setChSeq(chSeq);
					chVO.setUpdDe(new Date());
					chVO.setUpdId(sessId);
					questionChoiceMapper.updateByFile(chVO);
				}
			
				result.setMsg("파일을 삭제하였습니다.");
				result.setResult(1);
				return result;
			}
			
			result.setMsg("error");
			result.setResult(0);
			return result;
			
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}


	@Override
	public ResultVO uploadQuizBankExcel(MultipartHttpServletRequest mhsr,int ctg1Seq,int ctg2Seq,int ctg3Seq,int forceReg) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			MultipartFile mf = mhsr.getFile("uploadExcel");
			if(mf.isEmpty()){
				result.setMsg("파일이 없습니다.");
				result.setResult(0);
				return result;
			}
			
			LOG.debug("문제은행 업로드 엑셀파일명 : "+LncUtil.getEncode(mf.getOriginalFilename()) );
			//LOG.debug("ctg1Seq : "+ctg1Seq);
			//LOG.debug("ctg2Seq : "+ctg2Seq);
			//LOG.debug("ctg3Seq : "+ctg3Seq);
			
			if(ctg2Seq < 1){
				result.setMsg("최소한 2차카테고리까지 선택하셔야 합니다.");
				result.setResult(0);
				return result;
			}
			if(ctg1Seq < 1){
				result.setMsg("1차카테고리까지 선택하셔야 합니다.");
				result.setResult(0);
				return result;
			}
			
			Date nDate = new Date();
			
			//엑셀 추출
			Workbook workbook = null;
			Sheet sheet = null;
			workbook = WorkbookFactory.create(mf.getInputStream());
			sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getLastRowNum();   
			LOG.debug("rowCount:{}", rowCount);
			
			//업로드대상 리스트
			List<Map<String, Object>> uploadList = new ArrayList<Map<String,Object>>();
			
			String sessId = SessionUserInfoHelper.getUserId();
			int targetCnt = 0;
			StringBuffer msgSb = new StringBuffer();
			
			for(int i=2 ; i<= rowCount ; i++) {
				try {
					Row row = sheet.getRow(i);
					
					if(ObjectUtils.isEmpty(row.getCell(0))){
						continue;
					}
					
					String cellNo = LncUtil.getExcelToString(row.getCell(0));
					LOG.debug("cellNo : "+cellNo);
					boolean isDigit = LncUtil.isNumber(cellNo);
					//boolean isDigit = true;//2라인부터 무조건 검증
					if(isDigit){
						targetCnt++;
						int cellNoInt = LncUtil.nvlInt(cellNo);
						if(cellNoInt != targetCnt){
							msgSb.append(i+1+" 행에 번호가 순서대로 입력되지 않았습니다.");
							result.setMsg(msgSb.toString());
							result.setResult(0);
							return result;
						}
						
						
						if(cellNoInt > 0 && cellNoInt == targetCnt){
							//문제객체
							Map<String, Object> qt = new HashMap<String,Object>();
							uploadList.add(qt);
							String qstnNm = LncUtil.getExcelToString(row.getCell(1));	//문제명
							int diffType = LncUtil.nvlInt(LncUtil.getExcelToString(row.getCell(2)));				//난이도
							int qstnTp = LncUtil.nvlInt(LncUtil.getExcelToString(row.getCell(3)));				//문제유형
							String qstnStr = LncUtil.getExcelToString(row.getCell(4));	//문제문항
							String ansDesc = LncUtil.getExcelToString(row.getCell(5));	//정답설명
							String fillBlank = LncUtil.getExcelToString(row.getCell(6));//단답형정답
							Question qstn = new Question(); qstn.setRegId(sessId);qstn.setRegDe(nDate);qstn.setUpdId(sessId);qstn.setUpdDe(nDate);
							qstn.setQstnNm(qstnNm);
							qstn.setDiffType(diffType);
							qstn.setQstnTp(qstnTp);
							qstn.setQstnStr(qstnStr);
							qstn.setAnsDesc(ansDesc);
							qstn.setFillBlank(fillBlank);
							qstn.setUseYn("Y");
							
							qt.put("qstn", qstn);
							qt.put("cellNo", cellNo);
							LOG.debug("qstnNm : {}, qstnTp : {}, diffType : {}",qstn.getQstnNm(),qstn.getQstnTp(),qstn.getDiffType());
							
							if(!Arrays.asList(1,2).contains(qstnTp)){//주관식,서술형이라면 다음 문제로
								continue;
							}
							
							//객관식겍체
							List<QuestionChoice> chList = new ArrayList<QuestionChoice>();
							qt.put("chList", chList);
							
							QuestionChoice qsch1 = new QuestionChoice(); qsch1.setUseYn("Y"); qsch1.setUpdId(sessId); qsch1.setUpdDe(nDate);
							QuestionChoice qsch2 = new QuestionChoice();
							QuestionChoice qsch3 = new QuestionChoice();
							QuestionChoice qsch4 = new QuestionChoice();
							QuestionChoice qsch5 = new QuestionChoice();
							BeanUtils.copyProperties(qsch1, qsch2);
							BeanUtils.copyProperties(qsch1, qsch3);
							BeanUtils.copyProperties(qsch1, qsch4);
							BeanUtils.copyProperties(qsch1, qsch5);
							
							String ch1Str = LncUtil.getExcelToString(row.getCell(7));
							String ch1Yn = LncUtil.getExcelToString(row.getCell(8));
							ch1Yn = "Y".equals(ch1Yn)?"Y":"N";
							qsch1.setChStr(ch1Str);
							qsch1.setChAnsYn(ch1Yn);
							
							String ch2Str = LncUtil.getExcelToString(row.getCell(9));
							String ch2Yn = LncUtil.getExcelToString(row.getCell(10));
							ch2Yn = "Y".equals(ch2Yn)?"Y":"N";
							qsch2.setChStr(ch2Str);
							qsch2.setChAnsYn(ch2Yn);
							
							String ch3Str = LncUtil.getExcelToString(row.getCell(11));
							String ch3Yn = LncUtil.getExcelToString(row.getCell(12));
							ch3Yn = "Y".equals(ch3Yn)?"Y":"N";
							qsch3.setChStr(ch3Str);
							qsch3.setChAnsYn(ch3Yn);
							
							String ch4Str = LncUtil.getExcelToString(row.getCell(13));
							String ch4Yn = LncUtil.getExcelToString(row.getCell(14));
							ch4Yn = "Y".equals(ch4Yn)?"Y":"N";
							qsch4.setChStr(ch4Str);
							qsch4.setChAnsYn(ch4Yn);
							
							String ch5Str = LncUtil.getExcelToString(row.getCell(15));
							String ch5Yn = LncUtil.getExcelToString(row.getCell(16));
							ch5Yn = "Y".equals(ch5Yn)?"Y":"N";
							qsch5.setChStr(ch5Str);
							qsch5.setChAnsYn(ch5Yn);
							
							chList.add(qsch1);
							chList.add(qsch2);
							chList.add(qsch3);
							chList.add(qsch4);
							chList.add(qsch5);
						}
					}
				} catch (NumberFormatException|NullPointerException e) {
					e.printStackTrace();
					msgSb.append(i+1+" 행에 문제가 발생하였습니다.");
					result.setMsg(msgSb.toString());
					result.setResult(0);
					return result;
				}
				
			}
			LOG.debug("test {}",uploadList);
			if(forceReg != 1){
				//db문제목록 가져오기
				List<Question> qstnList = questionMapper.selectByAll();
				
				//검증처리
				for(Map<String, Object> o: uploadList){
					String cellNo = (String) o.get("cellNo");
					
					//문제검증
					Question qstn = (Question) o.get("qstn");
					if(ObjectUtils.isEmpty(qstn.getQstnNm()) || ObjectUtils.isEmpty(qstn.getQstnStr())){
						msgSb.append(cellNo+"번 문제명,문제설명에 공백이 존재합니다.\n");
					}
					if(!Arrays.asList(1,2,3).contains(qstn.getDiffType())){
						msgSb.append(cellNo+"번 문제 난이도 번호가 잘못 입력되었습니다.\n");
					}
					if(!Arrays.asList(1,2,3,4).contains(qstn.getQstnTp())){
						msgSb.append(cellNo+"번문제 문제유형 번호가 잘못 입력되었습니다.\n");
					}
					if(qstn.getQstnTp() == 3 && ObjectUtils.isEmpty(qstn.getFillBlank())){
						msgSb.append(cellNo+"번문제 단답형인 경우 정답을 입력하셔야 합니다.\n");
					}
					
					//엑셀에서 중복문제 체크
					for(Map<String, Object> o2: uploadList){
						String cellNo2 = (String) o2.get("cellNo");
						Question qstn2 = (Question) o2.get("qstn");
						if(!cellNo2.equals(cellNo) && qstn.getQstnNm().equals(qstn2.getQstnNm())){
							msgSb.append(cellNo+"번문제는 해당 엑셀파일에 중복문제가 존재합니다.\n");
							continue;
						}
					}
					
					//db에서 중복문제 체크
					for(Question qstn2: qstnList){
						if(qstn2.getQstnNm().equals(qstn.getQstnNm())){
							msgSb.append(cellNo+"번문제는 이미 등록된 문제입니다.\n");
							continue;
						}
					}
					
					//객관식이 아닌경우 여기까지
					if(!Arrays.asList(1,2).contains(qstn.getQstnTp())){
						continue;
					}
					
					//객관식 검증
					List<QuestionChoice> chList = (List<QuestionChoice>) o.get("chList");
					int chAnsCnt = 0;	//정답수, 1이상이 여야됨
					LOG.debug(qstn.getQstnNm());
					boolean validChTmp = false;//객관식 검증용
					boolean validCh = true;
					for(int k=0; k<chList.size(); k++){
						QuestionChoice o2 = chList.get(k);
						
						if (ObjectUtils.isEmpty(o2.getChStr())) {
							validChTmp = true; // 비정상 객관식 발견
			            } else {
			                if (validChTmp) {
			                    // 비정상이 발견된 이후에도 정상 객관식이 나타나면 오류
			                	validCh = false;
			                }
			                //정답체크 여부
							//LOG.debug(o2.getChAnsYn());
							if("Y".equals(o2.getChAnsYn())){
								chAnsCnt++;
							}
			            }
					}
					//OG.debug("chAnsCnt {}",chAnsCnt);
					if(!validCh){
						msgSb.append(cellNo+"번문제 객관식에 데이터가 잘못되었습니다.\n");
					}
					if(chAnsCnt < 1){
						msgSb.append(cellNo+"번 문제에 답안이 체크되지 않았습니다.\n");
					}
					if(qstn.getQstnTp() == 2 && chAnsCnt < 2){
						msgSb.append(cellNo+"번 문제에 다중답안이 체크되지 않았습니다.\n");
					}
					
				}
				
				//검증 미통과시 메시지 처리
				if(!ObjectUtils.isEmpty(msgSb.toString())){
					result.setMsg(msgSb.toString());
					result.setResult(0);
					return result;
				}
				
				if(targetCnt == 0){
					result.setMsg("등록할 문제가 없습니다.");
					result.setResult(0);
					return result;
				}
				
				result.setMsg(targetCnt+"건을 일괄 등록합니다. 계속 진행하시겠습니까?");
				result.setResult(1);
				return result;
			} 
			
			//DB처리
			for(Map<String, Object> o: uploadList){
				//문제등록
				Question qstn = (Question) o.get("qstn");
				LOG.debug("문제등록 {}",qstn);
				
				//문제저장
				int optionCnt = 0;
				String useYn = "Y";
				String regId = sessId;
				String updId = sessId;
				Date regDe = nDate;
				Date updDe = nDate;
				Question qstnVO = new Question(qstn.getQstnNm(), qstn.getQstnTp(), ctg1Seq, ctg2Seq,ctg3Seq, qstn.getDiffType(), qstn.getQstnDesc(), qstn.getQstnStr(), null, null, optionCnt, qstn.getFillBlank(), qstn.getAnsDesc(), useYn, regId, updId, regDe, updDe);
				questionMapper.insertByPk(qstnVO);
				
				if(qstn.getQstnTp() == 3 || qstn.getQstnTp() == 4){//단답,서술형이라면 중지
					continue;
				}
				
				//객관식 등록
				List<QuestionChoice> chList = (List<QuestionChoice>) o.get("chList");
				LOG.debug(qstn.getQstnNm());
				//일괄 대기상태
				int qstnSeq = qstnVO.getQstnSeq();
				questionChoiceMapper.updateUseNByQstnSeq(qstnSeq);
				int chSeq = 0;
				for(QuestionChoice o2: chList){
					LOG.debug("보기등록 {}",o2);
					
					chSeq++;
					o2.setChSeq(chSeq);
					o2.setQstnSeq(qstnVO.getQstnSeq());
					o2.setUpdDe(updDe);
					o2.setUpdId(updId);
					o2.setUseYn("Y");
					LOG.debug(">>"+o2.isChAns());
					
					QuestionChoice ch = questionChoiceMapper.selectByPk(o2);
					if(ObjectUtils.isEmpty(ch)){
						//등록
						questionChoiceMapper.insertByPk(o2);
					}else{
						//수정
						questionChoiceMapper.updateByPk(o2);
					}
					
				}
				//임시삭제상태 삭제처리
				questionChoiceMapper.deleteByUseN(qstnSeq);
				
			}
			LOG.debug("성공!!!");
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			result.setMsg("엑셀 업로드 중 에러가 발생하였습니다.");
			result.setResult(0);
			return result;
		}
	}

    /*
     * 문제은행 >삭제
     * (non-Javadoc)
     * @see com.educare.edu.quizBank.service.QuizBankService#quizBankDel(java.lang.String, int)
     */
	@Override
	public ResultVO quizBankDel(String userId, int qstnSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//삭제가능체크
			
			//삭제
			questionChoiceMapper.quizBankDel(qstnSeq);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

}

package com.educare.edu.member.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.educare.component.VarComponent;
import com.educare.edu.comn.mapper.CategoryAuthMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.mapper.UserCareerMapper;
import com.educare.edu.comn.mapper.UserCertifMapper;
import com.educare.edu.comn.mapper.UserCompMapper;
import com.educare.edu.comn.mapper.UserLangMapper;
import com.educare.edu.comn.mapper.UserSchoolMapper;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.UserCareer;
import com.educare.edu.comn.model.UserCertif;
import com.educare.edu.comn.model.UserComp;
import com.educare.edu.comn.model.UserLang;
import com.educare.edu.comn.model.UserSchool;
import com.educare.edu.comn.service.SmsService;
import com.educare.edu.comn.vo.CategoryAuthVO;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.MemberVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.InstrctrAttach;
import com.educare.edu.member.service.model.InstrctrRealm;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoInstrctr;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.edu.menu.service.MenuService;
import com.educare.edu.tmpFile.service.TempFileService;
import com.educare.util.CryptoSeedUtil;
import com.educare.util.CryptoUtil;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.SelfAuthUtil;
import com.educare.util.XmlBean;

import Kisinfo.Check.IPIN2Client;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @Class Name : MemberServiceImpl.java
 * @author SI개발팀 박용주
 * @since 2020. 5. 27.
 * @version 1.0
 * @see
 * @Description 회원 서비스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 5. 27.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Transactional
@Service("MemberService")
public class MemberServiceImpl implements MemberService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class.getName());
	
	/** 회원 Mapper */
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	
	/** 메뉴 서비스 */
	@Resource(name = "MenuService")
	private MenuService menuService;
	
	/** 임시파일 서비스 */
	@Resource(name = "TempFileService")
	private TempFileService tempFileService;
	
	/** table Mapper */
	@Resource(name = "TableMapper")
	private TableMapper tableMapper;
	
	@Resource(name = "SmsService")
	private SmsService smsService;
	@Resource(name = "CategoryAuthMapper")
	private CategoryAuthMapper categoryAuthMapper;
	@Resource(name = "UserCompMapper")
	private UserCompMapper userCompMapper;
	@Resource(name = "UserSchoolMapper")
	private UserSchoolMapper userSchoolMapper;
	@Resource(name = "UserCertifMapper")
	private UserCertifMapper userCertifMapper;
	@Resource(name = "UserLangMapper")
	private UserLangMapper userLangMapper;
	@Resource(name = "UserCareerMapper")
	private UserCareerMapper userCareerMapper;
	
	/**
	 * 강사 사진 저장
	 * @param request
	 * @return result : S/F, orgNm : 원본파일, fileRename : 파일 저장명
	 */
	private Map<String, String> saveInstrctrPhoto(MultipartHttpServletRequest request) {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		String isSuccess = "E";
		Iterator<?> itr =  request.getFileNames();
		File file = null;
		String fileRename = "";
		String orgNm = "";
		String fileType = "";
		
		File dir = new File(INSTRCTR_PHOTO_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		if(itr.hasNext()) {
			List<MultipartFile> list = request.getFiles((String) itr.next());
			for(MultipartFile multipartFile : list) {
				orgNm = multipartFile.getOriginalFilename();
				fileType = orgNm.substring(orgNm.lastIndexOf(".") + 1, orgNm.length());
				fileRename = System.currentTimeMillis() + "." + fileType;
				file = new File(INSTRCTR_PHOTO_PATH + fileRename);
				try {
					multipartFile.transferTo(file);
				} catch (IllegalStateException e) {
					LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
					isSuccess = "F";
				} catch (IOException e) {
					LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
					isSuccess = "F";
				}	
			}
			isSuccess = "S";
		}
		
		if("S".equals(isSuccess)){
			resultMap.put("orgNm", orgNm);
			resultMap.put("fileRename", fileRename);
		}
		
		resultMap.put("result", isSuccess);
		
		return resultMap;
	}
	
	/**
	 * 아이디대량등록 엑셀파일 리스트 추출
	 * @param excelfile
	 * @return
	 */
	private List<UserInfoStdnt> excelRead(File excelfile){
		List<UserInfoStdnt> list = null;
		UserInfoStdnt user = null;
		Workbook workbook = null;
		Sheet sheet = null;
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("Cp1252");
		
		try{
			workbook = Workbook.getWorkbook(excelfile, ws);
			sheet = workbook.getSheet(0);        
			
			int rowCount = sheet.getRows();    
 
			if(rowCount > 0) {
				list = new ArrayList<>();
				
				for(int i = 1 ; i < rowCount ; i++) {
					if(sheet.getCell(0, i).getContents() != null || "".equals(sheet.getCell(0, i).getContents())){
						user = new UserInfoStdnt();
						//user.setUserId(sheet.getCell(0, i).getContents());
						user.setEmail(sheet.getCell(0, i).getContents());
						user.setMobile(sheet.getCell(1, i).getContents());
						user.setUserNm(sheet.getCell(2, i).getContents());
						user.setUserNmEn(sheet.getCell(3, i).getContents());
						user.setBelong(sheet.getCell(4, i).getContents());
						user.setPosition(sheet.getCell(5, i).getContents());
						
						list.add(user);
					}	
				}
			}
		}catch(BiffException | IOException e){
			list = new ArrayList<>();
		}finally{
			if(workbook != null) workbook.close();
		}
		return list;
	}
	
	/**
	 * 관리자계정 리스트를 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public Map<String, Object> getMngrList(MemberVO vo) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int totalCnt = memberMapper.selectMngrListCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPage());
        paginationInfo.setRecordCountPerPage(vo.getRow());
        paginationInfo.setPageSize(PAGE_SIZE);
        
        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
        
        List<UserInfo> userList = memberMapper.selectMngrList(vo);
        
        resultMap.put("totalCnt", totalCnt);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("dataList", userList);
        
        return resultMap;
	}

	/**
	 * 관리자계정을 조회한다.
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfo getMngrDetail(String userId) {
		UserInfo userInfo = memberMapper.selectUserInfoByPk(userId);
		return userInfo;
	}

	/**
	 * 회원 리스트를 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public Map<String, Object> getStdntList(MemberVO vo) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setUserMemLvl(UserInfo.MEM_LVL_STDNT);
		int totalCnt = memberMapper.selectStdntListCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPage());
        paginationInfo.setRecordCountPerPage(vo.getRow());
        paginationInfo.setPageSize(PAGE_SIZE);
        
        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
        
        List<UserInfo> userList = memberMapper.selectStdntList(vo);
        
        resultMap.put("totalCnt", totalCnt);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("dataList", userList);
        
        return resultMap;
	}

	/**
	 * 사용 가능한 아이디인지 조회한다.
	 * @param userId
	 * @return true : 사용가능, false : 사용불가
	 */
	@Override
	public boolean isUseableId(String loginId) {
		boolean chk = true;
		/*
		UserInfo user = memberMapper.selectUserInfoByPk(userId);
		if(user != null && user.getUserId() != null) {
			chk = false;
		}
		*/
		String userId = memberMapper.getUserIdByLoginId(loginId);
		if(userId != null) {
			chk = false;
		}
		
		return chk;
	}

	/**
	 * 관리자 패스워드를 변경한다.
	 * @param loginId
	 * @param userPw
	 */
	@Override
	public String mngrPwUpd(String userId, String userPw) {
		
		if(userPw == null || "".equals(userPw)){
			return "비밀번호를 입력하세요.";
		}
		//else{
			//String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,12}$";
			//Matcher matcher = Pattern.compile(pwPattern).matcher(userPw);
			//if(!matcher.matches()){
			    //비번 자유롭게 ,체크안함,211105
				//return "비밀번호는 영문, 숫자, 특수문자를 조합하여 8~12자리로 입력하시기 바랍니다.";
			//}
		//}
		UserInfo param = new UserInfo();
		param.setUserId(userId);
		param.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(userPw));
		memberMapper.updateUserPw(param);
		
		return EXCUTE_SUCCESS;
	}

	/**
	 * 관리자 계정을 생성한다.
	 * @param vo
	 * @return 
	 */
	@Override
	public ResultVO saveMngr(UserInfo userParam ,String[] menuIds ,String[] authCtgrySeqs) {
		ResultVO result = new ResultVO();
		result.setResult(0);
		try {
			//내정보가져오기
			UserInfo user = memberMapper.selectUserInfoByPk(userParam.getUserId());
			
			if(userParam.getLoginId() == null || "".equals(userParam.getLoginId())){
				result.setMsg("아이디는 필수입력 입니다.");
				return result;
			}else{
				if(userParam.getLoginId().length() < 4){
					result.setMsg("아이디는 4자 이상 입니다.");
					return result;
				}
				if(user==null){
					if(!isUseableId(userParam.getLoginId())){
						result.setMsg("사용중인 아이디 입니다.");
						return result;
					}
				}
			}
			
			if(userParam.getUserNm() == null || "".equals(userParam.getUserNm())){
				result.setMsg("성명은 필수입력 입니다.");
				return result;
			}
		
			Date curDate = new Date();
			userParam.setRgsde(curDate);
			//userParam.setState(UserInfo.STATE_APPROVAL);//관리자 상태 변경하기위해서 주석처리,kbj
			userParam.setLstLoginDe(curDate);
			userParam.setLstPwDe(curDate);
			userParam.setStateUpdDe(curDate);
			if(user!=null){
				if(!ObjectUtils.isEmpty(userParam.getUserPw())){
					userParam.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(userParam.getUserPw()));
				}
				
				memberMapper.updateMngrUserInfo(userParam);
			}else{
				userParam.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(userParam.getUserPw()));
				userParam.setOrgCd("1");
				memberMapper.insertMngrUserInfo(userParam);
			}
			
			if(menuIds != null && menuIds.length > 0){
				menuService.setMenuAuth(menuIds, userParam.getUserId());
			}
			
			if(authCtgrySeqs != null && authCtgrySeqs.length > 0){
				CategoryAuthVO vo = new CategoryAuthVO();
				vo.setUserId(userParam.getUserId());
				categoryAuthMapper.deleteCategoryAuthList(vo);
				for(String ctgrySeq : authCtgrySeqs){
					vo.setCtgrySeq(Integer.parseInt(ctgrySeq));
					categoryAuthMapper.insertCategoryAuthList(vo);
				}
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("알 수 없는 오류가 발생하였습니다.");
			result.setResult(0);
			return result;
		}
		
	}

	/**
	 * 관리자 게정을 삭제한다.
	 * @param userId
	 * @return
	 */
	@Override
	public String mngrDelete(String userId) {
		
		if(userId == null || "".equals(userId)){
			return "잘못된 접근입니다.";
		}
		
		UserInfo user = memberMapper.selectUserInfoByPk(userId);
		if(user == null || user.getUserId() == null || !UserInfo.MEM_LVL_MID_ADM.equals(user.getUserMemLvl())){
			return "잘못된 접근입니다.";
		}
		
		menuService.deleteMenuAuthByUserId(userId);
		memberMapper.deleteMngrUserInfo(userId);
		
		return EXCUTE_SUCCESS;
	}

	@Override
	public String updMngr(String userId,String userNm, String tel, String orgNm, String orgEn, boolean jiginDel, String jiginFileNm,String[] menuIds,String orgCd,String userMemLvl) {
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		userInfo.setUserNm(userNm);
		userInfo.setTel(tel);
		
		Org orgParam = new Org();
		orgParam.setOrgNm(orgNm);
		orgParam.setOrgEn(orgEn);
		orgParam.setOrgCd(orgCd);
		orgParam.setJiginFileNm(jiginFileNm);
		orgParam.setJiginDel(jiginDel);
		
		if(userNm == null || "".equals(userNm)){
			return "성명은 필수입력 입니다.";
		}
		
		if(orgNm == null || "".equals(orgNm)){
			return "기관 이름은 필수입력 입니다.";
		}else{
			if(memberMapper.selectOrgNmCnt(orgParam)>0){
				return "중복된 기관 이름 입니다.";
			}
		}
		if(menuIds != null && menuIds.length > 0){
			menuService.setMenuAuth(menuIds, userInfo.getUserId());
		}
		
		memberMapper.updateMngrUserInfo(userInfo);
		if(userMemLvl.equals("2")){
			tableMapper.updateOrgByPk(orgParam);
		}
		
		return EXCUTE_SUCCESS;

	}

	/**
	 * 강사 리스트를 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public Map<String, Object> getInstrctrList(MemberVO vo) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setUserMemLvl(UserInfo.MEM_LVL_INSTRCTR);
		int totalCnt = memberMapper.selectInstrctrListCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPage());
        paginationInfo.setRecordCountPerPage(vo.getRow());
        paginationInfo.setPageSize(PAGE_SIZE);
        
        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
        
        List<UserInfo> userList = memberMapper.selectInstrctrList(vo);
        List<InstrctrRealm> moduleList = null;
        Map<String, String> moduleMap = new HashMap<String, String>();
        String moduleStr = "";
        int indexNo = 0;
        
        if(userList != null && !userList.isEmpty()){
        	for(UserInfo user : userList){
        		moduleList = memberMapper.seleteInstrctrRealmList(user.getUserId());
        		if(moduleList != null && !moduleList.isEmpty()){
        			for(InstrctrRealm module : moduleList){
        				if(indexNo > 0){
        					moduleStr += ", " + module.getCtgryNm();
        				}else{
        					moduleStr += module.getCtgryNm();
        				}
        				indexNo++;
        			}
        		}
        		moduleMap.put(user.getUserId(), moduleStr);
        		moduleStr = "";
        		indexNo = 0;
        	}
        }
        
        resultMap.put("totalCnt", totalCnt);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("dataList", userList);
        resultMap.put("moduleMap", moduleMap);
        
        return resultMap;
	}

	/**
	 * 강사정보를 저장한다.
	 * @param userInfoInstrctr
	 * @param vo
	 * @param request
	 * @return
	 */
	@Override
	public ResultVO saveMngrInstrctr(UserInfoInstrctr param) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			param.setLoginId(LncUtil.generateLoginId("prof_"));
			param.setUserPw("****");
			param.setUserMemLvl(UserInfo.MEM_LVL_INSTRCTR);
			param.setStateUpdDe(DateUtil.getCalendar().getTime());
			
			UserInfo userInfo = memberMapper.selectUserInfoByPk(param.getUserId());
			//기존유저가 아니라면
			if(ObjectUtils.isEmpty(userInfo)){
				
				if(existLoginId(param.getLoginId())){
					result.setResult(2);
					return result;
				}
				
				param.setRgsde(DateUtil.getCalendar().getTime());
				//param.setState(UserInfo.STATE_APPROVAL);
				param.setLstPwDe(DateUtil.getCalendar().getTime());
				param.setLstLoginDe(DateUtil.getCalendar().getTime());
				param.setStateUpdUserId(SessionUserInfoHelper.getUserId());
				param.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(param.getUserPw()));
				param.setOrgCd("0");
				memberMapper.insertMngrUserInfo(param);
				memberMapper.insertUserInfoInstrctr(param);
				
			}else{
				UserInfoInstrctr paramInstrctr = new UserInfoInstrctr();
				paramInstrctr.setUserId(param.getUserId());
				paramInstrctr.setArea(param.getArea());
				memberMapper.updateUserInfoInstrctr(paramInstrctr);
				
				memberMapper.updateUserInfo(param);
			}
			
			
			UserInfoInstrctr userInfoInstrctr = memberMapper.selectInstrctrByPk(param.getUserId());
			if(ObjectUtils.isEmpty(userInfoInstrctr)){
				UserInfoInstrctr paramInstrctr = new UserInfoInstrctr();
				paramInstrctr.setUserId(param.getUserId());
				//paramInstrctr.setArea(param.getArea());
				memberMapper.insertUserInfoInstrctr(paramInstrctr);
			}
			
			rstData.put("userId", param.getUserId());
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	/**
	 * 강사정보를 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public Map<String, Object> getInstrctrInfo(MemberVO vo) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		UserInfoInstrctr user = memberMapper.selectInstrctrByPk(vo.getUserId());
		List<InstrctrRealm> moduleList = memberMapper.seleteInstrctrRealmList(vo.getUserId());
		String module = "";
		
		if(moduleList != null && !moduleList.isEmpty()){
			for(int i = 0; i < moduleList.size(); i++){
				if(i > 0){
					module += ", ";
				}
				module += moduleList.get(i).getCtgryNm();
			}
		}
		
		vo.setFileSection(InstrctrAttach.FILE_SECTION_01);
		List<InstrctrAttach> attach01 = memberMapper.seleteInstrctrAttachList(vo);
		
		vo.setFileSection(InstrctrAttach.FILE_SECTION_02);
		List<InstrctrAttach> attach02 = memberMapper.seleteInstrctrAttachList(vo);
		
		vo.setFileSection(InstrctrAttach.FILE_SECTION_03);
		List<InstrctrAttach> attach03 = memberMapper.seleteInstrctrAttachList(vo);
		
		vo.setFileSection(InstrctrAttach.FILE_SECTION_09);
		List<InstrctrAttach> attach09 = memberMapper.seleteInstrctrAttachList(vo);
		
		result.put("user", user);
		result.put("module", module);
		result.put("moduleList", moduleList);
		result.put("attach01", attach01);
		result.put("attach02", attach02);
		result.put("attach03", attach03);
		result.put("attach09", attach09);
		
		return result;
	}

	/**
	 * 강사 첨부파일을 조회한다.
	 * @param fileSeq
	 * @return
	 */
	@Override
	public InstrctrAttach getInstrctrAttach(Integer fileSeq) {
		return memberMapper.selectInstrctrAttachByPk(fileSeq);
	}

	/**
	 * 강사정보를 수정한다.
	 * @param userInfoInstrctr
	 * @param vo
	 * @param request
	 * @return
	 */
	@Override
	public String updateInstrctr(UserInfoInstrctr userInfoInstrctr, MemberVO vo, MultipartHttpServletRequest request) {
		
		UserInfo user = vo.getUserInfo();
		
		Map<String, String> photoResult = this.saveInstrctrPhoto(request);

		if("F".equals(photoResult.get("result"))){
			return "오류가 발생하였습니다.";
		}else{
			if("E".equals(photoResult.get("result"))){
				if(userInfoInstrctr.getPhotoOrg() == null || "".equals(userInfoInstrctr)){
					userInfoInstrctr.setPhotoOrg("");
					userInfoInstrctr.setPhotoRename("");
				}
			}else{
				if(photoResult.get("orgNm") != null && !"".equals(photoResult.get("orgNm"))){
					userInfoInstrctr.setPhotoOrg(photoResult.get("orgNm"));
					userInfoInstrctr.setPhotoRename(photoResult.get("fileRename"));
				}
			}
		}
		
		if(vo.getAttachDelSeqs() != null && !"".equals(vo.getAttachDelSeqs())){
			String[] seqs = vo.getAttachDelSeqs().split("[|]");
			for(String seq : seqs){
				if(seq != null && !"".equals(seq)){
					memberMapper.deleteInstrctrAttach(Integer.parseInt(seq));
				}
			}
		}
		
		
		Date curDate = new Date();
		
		user.setStateUpdDe(curDate);
		user.setStateUpdUserId(SessionUserInfoHelper.getUserId());
		//user.setEncUserPw(CryptoUtil.encrypt Password(user.getUserPw(), user.getUserId()));
		
		memberMapper.updateUserInfo(user);
		memberMapper.updateUserInfoInstrctr(userInfoInstrctr);
		
		memberMapper.deleteInstrctrRealmUser(user.getUserId());
		if(vo.getCtgrySeqs() != null && vo.getCtgrySeqs().length > 0){
			for(Integer seq : vo.getCtgrySeqs()){
				memberMapper.insertInstrctrRealm(new InstrctrRealm(user.getUserId(), seq, ""));
			}
		}
		
		return EXCUTE_SUCCESS;
	}

	/**
	 * 강사정보를 삭제한다.
	 * @param userId
	 */
	@Override
	public void deleteInstrctr(String userId) {
		
		List<InstrctrAttach> attachList = memberMapper.seleteInstrctrAttachListAll(userId);
		if(attachList != null && !attachList.isEmpty()){
			for(InstrctrAttach attach : attachList) {
				FileUtil.delete(INSTRCTR_ATTACH_PATH + attach.getFileRename());
				memberMapper.deleteInstrctrAttach(attach.getFileSeq());
			}
		}
		
		memberMapper.deleteInstrctrRealmUser(userId);
		UserInfoInstrctr user = memberMapper.selectInstrctrByPk(userId);
		if(user != null){
			if(user.getPhotoRename() != null && !"".equals(user.getPhotoRename())){
				FileUtil.delete(INSTRCTR_PHOTO_PATH + user.getPhotoRename());
			}
			memberMapper.deleteInstrctrUserInfo(userId);
			memberMapper.deleteUserInfo(userId);
		}
	}

	/**
	 * 계정잠금 해제
	 * @param userId
	 */
	@Override
	public void lockRelease(String userId) {
		UserInfo user = new UserInfo();
		user.setUserId(userId);
		user.setLstLoginDe(new Date());
		memberMapper.updateLstLoginDe(user);
	}

	/**
	 * 휴면회원 리스트를 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public Map<String, Object> getDrmncyList(MemberVO vo) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setUserMemLvl(UserInfo.MEM_LVL_STDNT);
		
		Date curDate = new Date();	
		Date compareDe = DateUtil.dayCalForStr(Integer.parseInt(DateUtil.getDate2Str(curDate, "yyyy")), 
				Integer.parseInt(DateUtil.getDate2Str(curDate, "MM")), 
				Integer.parseInt(DateUtil.getDate2Str(curDate, "dd")), -730, "");

		vo.setCompareDe(DateUtil.getDate2Str(compareDe, "yyyyMMdd"));
		
		int totalCnt = memberMapper.selectDrmncyListCnt(vo);
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPage());
        paginationInfo.setRecordCountPerPage(vo.getRow());
        paginationInfo.setPageSize(PAGE_SIZE);
        
        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
        
        List<UserInfo> userList = memberMapper.selectDrmncyList(vo);
        
        resultMap.put("totalCnt", totalCnt);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("dataList", userList);
        
        return resultMap;
	}
	
	/**
	 * 회원 아이디를 생성한다.
	 * @param userInfoStdnt
	 * @param vo
	 * @return
	 */
	@Override
	public String saveStdnt(UserInfoStdnt userInfoStdnt, MemberVO vo) {
		
		UserInfo user = vo.getUserInfo();
		
		UserInfo sessUser = SessionUserInfoHelper.getUserInfo();
		if(sessUser == null){
			return "로그아웃상태 입니다.";
		}
		
		Date curDate = new Date();
		
		//이메일 중복체크
		if(existEmail(user.getEmail())){
			return "이메일 중복";
		}
		
		user.setUserMemLvl(UserInfo.MEM_LVL_STDNT);
		user.setRgsde(curDate);
		user.setLstLoginDe(null);
		user.setLstPwDe(null);
		user.setState(UserInfo.STATE_APPROVAL);
		user.setStateUpdDe(curDate);
		user.setStateUpdUserId(sessUser.getUserId());
		user.setCi("");
		user.setBirth("");
		user.setMfType(0);
		
		userInfoStdnt.setAdmRgsYn(UserInfoStdnt.STR_Y);
		userInfoStdnt.setSelfAuthYn(UserInfoStdnt.STR_N);
		userInfoStdnt.setTermsAcceptDe(null);
		
		//수정여부 체크
		
		if(ObjectUtils.isEmpty(user.getUserId())){
			//아이디 난수생성
			String userId = getRandomUserId();
			user.setUserId(userId);
			userInfoStdnt.setUserId(userId);
			user.setOrgCd(sessUser.getOrgCd());
			//user.setUserPw(CryptoUtil.encrypt Password(user.getEmail(), user.getEmail()));
			user.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(user.getEmail()));
			
			memberMapper.insertUserInfo(user);
			memberMapper.insertUserInfoStdnt(userInfoStdnt);
		}else{
			memberMapper.updateUserInfo(user);
			memberMapper.insertUserInfoStdnt(userInfoStdnt);
		}
		
		return EXCUTE_SUCCESS;
	}

	@Override
	public int saveStdntAdmin(UserInfo param) {
		try {
			Date curDate = new Date();
			param.setRgsde(curDate);
			param.setStateUpdDe(curDate);
			param.setStateUpdUserId(SessionUserInfoHelper.getUserId());
			param.setLstLoginDe(curDate);
			param.setLstPwDe(curDate);
			//param.setState("A"); 
			UserInfo userInfo = memberMapper.selectUserInfoByPk(param.getUserId());
			
			//수정여부 체크 
			if(ObjectUtils.isEmpty(userInfo)){
				
				//이메일 유효성 체크
				if(!VarComponent.checkRegExp(VarComponent.REGEX_EMAIL, param.getLoginId())){
					return 3;
				}
				
				//이메일 중복체크
				if(existLoginId(param.getLoginId())){
					return 2;
				}
				//user.setUserPw(CryptoUtil.encrypt Password(user.getEmail(), user.getEmail()));
				param.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(param.getUserPw()));
				
				memberMapper.insertUserInfo(param);
			}else{
				if(userInfo.getState().equals("S")){
					param.setState("S");//탈퇴회원은 상태 못바꿈
				}
				memberMapper.updateUserInfo(param);
			}
			
			UserInfo userInfoStdnt = memberMapper.selectStdntInfoByPk(param.getUserId());
			 
			//수정여부 체크
			if(ObjectUtils.isEmpty(userInfoStdnt)){
				UserInfoStdnt param2 = new UserInfoStdnt();
				param2.setUserId(param.getUserId());
				param2.setAdmRgsYn(UserInfoStdnt.STR_Y);
				param2.setSelfAuthYn(UserInfoStdnt.STR_N);
				memberMapper.insertUserInfoStdnt(param2);
			}
			
			//탈퇴인경우 로그인아이디뒤에 '_del'붙이기
			if(param.getState().equals("S")){
				if(!userInfo.getState().equals("S")){
					memberMapper.updateUserInfoForDel(param);
				}
			}
			
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	/**
	 * 회원 상세정보를 조회한다.
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfo getStdntInfo(String userId) {
		return memberMapper.selectStdntInfoByPk(userId);
	}

	@Override
	@Transactional
	public String saveStdntBulk(MultipartHttpServletRequest request) {
		String result = saveStdntBulk2(request);
		if(!result.equals(EXCUTE_SUCCESS)){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}
	/**
	 * 회원 아이디를 대량 등록한다.
	 * @param request
	 * @return
	 */
	
	public String saveStdntBulk2(MultipartHttpServletRequest request) {
		
		boolean isSuccess = false;
		
		Iterator<?> itr =  request.getFileNames();
		String fileRename = "";
		File file = null;
		
		File dir = new File(STDNT_BULK_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		if(itr.hasNext()) {
			List<MultipartFile> list = request.getFiles((String) itr.next());
			for(MultipartFile multipartFile : list) {
				String orgNm = multipartFile.getOriginalFilename();
				fileRename = "bulk_" + System.currentTimeMillis() + orgNm.substring(orgNm.lastIndexOf(".") + 1, orgNm.length());
				file = new File( STDNT_BULK_PATH + fileRename );
				try {
					multipartFile.transferTo(file);
				} catch (IllegalStateException e) {
					LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
				} catch (IOException e) {
					LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
				}	
			}
			isSuccess = true;
		}
		
		if(isSuccess){
			List<UserInfoStdnt> list = excelRead(file);
			if(list == null || list.isEmpty() || list.size() == 0){
				return "등록 할 정보가 없습니다.<br/>엑셀파일을 확인하세요.";
			}else{
				/*최종 등록 전에 검수 단계 */
				
				UserInfo sess = SessionUserInfoHelper.getUserInfo();
				String orgCd = "";
				if(sess!=null){
					orgCd = sess.getOrgCd();
				}
				//UserInfo userParam = new UserInfo();
				//userParam.setOrgCd(orgCd);
				//userParam.setUserMemLvl(UserInfo.MEM_LVL_STDNT);
				
				//마지막 숫자 계산
				//String lastOrgUserId = memberMapper.selectUserInfoForLastOrgUserId(userParam);
				//int lastOrgUserNo = Integer.parseInt( lastOrgUserId.substring(lastOrgUserId.length()-4) );
				
				String emails = "";
				String resultMsg = "";
				int i2=0;
				for(UserInfoStdnt stdnt : list){
					i2++;
					String lineMsg1 = ((i2+1)+"줄 : ");
					String lineMsg2 = "";
					
					//이메일 공백체크
					if(!LncUtil.checkEmail(stdnt.getEmail())){
						lineMsg2+="이메일 형식 아님,";
					}else{
						//회원 아이디 세팅
						//String yy = LncUtil.getDateToStr(Calendar.getInstance().getTime(), "yy");
						//String createdUserId = orgCd+"_"+yy+(String.format("%04d", lastOrgUserNo+i2));
						//stdnt.setUserId(createdUserId);
						
						
						
						//회원테이블에서 기관별 이메일 중복체크
						//userParam.setEmail(stdnt.getEmail());
						//if(existEmailByOrg(userParam)){
						//	lineMsg2+=("이미 가입된 이메일,");
						//}
						
						//회원테이블에서 이메일 중복체크
						if(existEmail(stdnt.getEmail())){
							lineMsg2+=("이미 가입된 이메일,");
						}
						
						//엑셀내 이메일  중복체크
						if(emails.contains(stdnt.getEmail())){
							lineMsg2+=("엑셀파일내 이메일 중복,");
						}
						//국문성명 공백 체크
						if(StringUtils.isEmpty(stdnt.getUserNm())){
							lineMsg2+=("국문성명 필수,");
						}
						//연락처 공백 체크
						if(StringUtils.isEmpty(stdnt.getMobile())){
							lineMsg2+=("연락처 필수,");
						}
						
						//회원아이디 난수로 생성
						stdnt.setUserId(getRandomUserId());
					}
					if(!StringUtils.isEmpty(lineMsg2)){
						resultMsg+=(lineMsg1+lineMsg2+"<br/>");
					}
					//엑셀상에서도 중복 체크하기위해 변수에 담아둔다
					emails+=(stdnt.getEmail()+",");
				}
				if(!StringUtils.isEmpty(resultMsg)){
					return resultMsg;
				}
				
				
				//최종 등록
				Date curDate = new Date();
				for(UserInfoStdnt stdnt : list){
					if(stdnt.getUserId() == null || "".equals(stdnt.getUserId())){
						return "아이디는 필수입니다.";
					}else{
						if(!isUseableId(stdnt.getUserId())){
							return "사용중인 아이디 입니다. [" + stdnt.getUserId() + "]";
						}
					}
					
					if(stdnt.getEmail() == null || "".equals(stdnt.getEmail())){
						return "이메일은 필수입니다. [" + stdnt.getUserId() + "]";
					}
					
					/*if(stdnt.getMobile() == null || "".equals(stdnt.getMobile())){
						return "연락처는 필수입니다. [" + stdnt.getUserId() + "]";
					}*/
					
					if(stdnt.getUserNm() == null || "".equals(stdnt.getUserNm())){
						return "국문 성명은 필수입니다. [" + stdnt.getUserId() + "]";
					}
					
					/*if(stdnt.getUserNmEn() == null || "".equals(stdnt.getUserNmEn())){
						return "영문 성명은 필수입니다. [" + stdnt.getUserId() + "]";
					}*/
					
					UserInfo user = new UserInfo();
					
					user.setUserId(stdnt.getUserId());
					user.setUserNm(stdnt.getUserNm());
					user.setEmail(stdnt.getEmail());
					user.setMobile(stdnt.getMobile());
					//user.setUserPw(CryptoUtil.encrypt Password(stdnt.getEmail(), stdnt.getEmail()));
					user.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(stdnt.getEmail()));
					user.setUserMemLvl(UserInfo.MEM_LVL_STDNT);
					user.setPostNo("");
					user.setAddr("");
					user.setAddrDetail("");
					user.setAddrEtc("");
					user.setRgsde(curDate);
					user.setLstLoginDe(null);
					user.setLstPwDe(null);
					user.setState(UserInfo.STATE_APPROVAL);
					user.setStateUpdDe(curDate);
					user.setStateUpdUserId(SessionUserInfoHelper.getUserId());
					user.setCi("");
					user.setBirth("");
					user.setMfType(0);
					user.setOrgCd(orgCd);
					
					stdnt.setAdmRgsYn(UserInfoStdnt.STR_Y);
					stdnt.setSelfAuthYn(UserInfoStdnt.STR_N);
					stdnt.setTermsAcceptDe(null);
					
					memberMapper.insertUserInfo(user);
					memberMapper.insertUserInfoStdnt(stdnt);
				}
			}
		}else{
			return "오류가 발생하였습니다.";
		}
		
		return EXCUTE_SUCCESS;
	}

	@Override
	public boolean existEmail(String email) {
		//int cnt = memberMapper.selectUserInfoForEmailCnt(userParam);
		UserInfo user = new UserInfo();
		user.setEmail(email);
		int cnt = memberMapper.selectUserInfoForEmailCnt(user);
		if(cnt>0){
			return true;
		}
		return false;
	}

	/**
	 * 아이디 난수 생성
	 * @param email
	 * @return
	 */
	private String getRandomUserId() {
		Calendar cal = Calendar.getInstance();
		String randomStr = cal.getTimeInMillis()+"_"+LncUtil.numberGen(8, 1);
		
		return randomStr;
	}

	/**
	 * 교육과정 카테고리에 해당하는 강사 목록조회
	 * @param ctgrySeq
	 * @return
	 */
	@Override
	public List<UserInfo> getInstrctrListByCategory(Integer ctgrySeq) {
		return memberMapper.selectInstrctrListByCategory(ctgrySeq);
	}
	
	/**
	 * 회원가입 처리
	 * @param userInfo
	 * @param vo
	 * @return
	 */
	@Override
	@Transactional
	public ResultVO joinUser(UserInfo user) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String, Object>();
		result.setData(rstData);
		try {
			result.setResult(0);
			
			//user.setEmail(user.getLoginId());
			
			Date curDate = new Date();
			
			//if(UtilComponent.checkPattern(user.getEmail(), VarComponent.REGEX_EMAIL)==0){
			//	return "이메일 형식이 잘못되었습니다.";
			//}
			
			if(existEmail(user.getEmail())){
				//result.setMsg("이메일이 중복되었습니다.");
				//return result;
			}
			
			LOG.info("userPw1: {}",user.getUserPw());
			user.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(user.getUserPw()));
			LOG.info("userPw2: {}",user.getUserPw());
			user.setState("W");//강사인경우 대기
			if(!"8".equals(user.getUserMemLvl())){//강사아닌경우
				user.setUserMemLvl("7");//회원가입시 준회원으로 가입
				user.setState("A");
			}
			user.setRgsde(curDate);
			user.setLstLoginDe(curDate);
			user.setLstPwDe(curDate);
			user.setStateUpdDe(curDate);
			user.setOrgCd("1");
			
			memberMapper.insertUserInfo(user);
			
			String userId = memberMapper.getUserIdByLoginId(user.getLoginId());
			
			if(!user.getUserMemLvl().equals("8")){//강사인경우
				UserInfoStdnt userInfoStdnt = new UserInfoStdnt();
				userInfoStdnt.setUserId(userId);
				userInfoStdnt.setAdmRgsYn(UserInfoStdnt.STR_N);
				userInfoStdnt.setSelfAuthYn(UserInfoStdnt.STR_Y);
				userInfoStdnt.setTermsAcceptDe(curDate);
				memberMapper.insertUserInfoStdnt(userInfoStdnt);
				
				//알림톡 발송
				//smsService.setSendSms("", user.getMobile(), "", null, 0, user.getUserId(), 3);
			}else{
				UserInfoInstrctr userInfoInstrctr = new UserInfoInstrctr();
				userInfoInstrctr.setUserId(userId);
				memberMapper.insertUserInfoInstrctr(userInfoInstrctr);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			LOG.info(e.getMessage());
			result.setMsg(VarComponent.MSG_ERROR2);
			result.setResult(0);
			return result;
		}
		
	}
	
	/** 
	 * 회원정보 수정
	 * @param userInfoStdnt
	 * @param vo
	 * @return
	 */
	@Override
	public ResultVO updateUserInfoStdnt(UserInfo user) {
		ResultVO result = new ResultVO();
		try {
			if(SessionUserInfoHelper.isLogined()){
				if(!"9".equals(SessionUserInfoHelper.getUserMemLvl())){
					result.setMsg("잘못된 경로로 접근하였습니다.");
					result.setResult(0);
					return result;
				}
				user.setUserId(SessionUserInfoHelper.getUserId());
			}
			
			user.setUserMemLvl(UserInfo.MEM_LVL_STDNT);
			UserInfoStdnt userInfoStdnt = new UserInfoStdnt();
			userInfoStdnt.setUserId(SessionUserInfoHelper.getUserId());
			//모바일 이메일은 모두 미입력해야됨
			memberMapper.updateUserInfoBase(user);
			memberMapper.updateUserInfoStdnt(userInfoStdnt);
			
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	/**
	 * 단체등록 가능한 수강생 목록을 조회한다.
	 * @param EduVO vo
	 * @return
	 */
	@Override
	public List<UserInfo> getStdntBulkList(EduVO vo) {
		return memberMapper.selectStdntListByEdu(vo);
	}

	/**
	 * 본인인증 중복검사
	 * @param model
	 * @return
	 */
	@Override
	public String selfAuthDuplChk() {
		
		if(!SelfAuthUtil.isCertified()){
			return "보인인증 오류입니다.";
		}
		
		UserInfo user = memberMapper.selectUserInfoByCi(SelfAuthUtil.getUserDi());
		if(user != null && user.getUserId() != null){
			String transId = user.getUserId().substring(0, 3);
			for(int i = 4 ; i <= user.getUserId().length(); i++) {
				transId = transId + "*";
			}	
			return "해당 본인인증 정보로 가입된 회원이 있습니다.<br/>" + transId + " (가입일자 : " + DateUtil.getDate2Str(user.getRgsde(), "yyyy-MM-dd") + ")";
		}
		
		return EXCUTE_SUCCESS;
	}

	/**
	 * 아이디 찾기
	 * @param vo
	 * @return
	 */
	@Override
	public String findId(String userNm, String mobile) {
		String result = "";
		
		MemberVO vo = new MemberVO();
		vo.setUserNm(userNm);
		LOG.info("mobile : {}",mobile);
		vo.setMobile(CryptoSeedUtil.encrypt(mobile));
		LOG.info("vo.getMobile() : {}",vo.getMobile());
		List<UserInfo> users = memberMapper.selectUserFindId(vo);
		
		for(UserInfo user:users){
			/*
			String transId = user.getUserId().substring(0, 4);
			for(int i = 5 ; i <= user.getUserId().length(); i++) {
				transId = transId + "*";
			}
			*/	
			result += LncUtil.maskId(user.getLoginId()) + " (" + DateUtil.getDate2Str(user.getRgsde(), "yyyy-MM-dd") + " 가입)<br/>";
		}
		if(result.equals("")){
			result = "일치하는 회원정보가 없습니다.";
		}
		
		return result;
	}

	/**
	 * 비밀번호 찾기
	 * @param vo
	 * @return
	 */
	@Override
	public String findPw(MemberVO vo) {
		UserInfo user = memberMapper.selectUserFindPw(vo);
		if(user == null){
			return "일치하는 회원정보가 없습니다.";
		}
		return EXCUTE_SUCCESS;
	}

	/**
	 * 비밀번호 찾기를 통한 변경
	 * @param userId
	 * @param userPw
	 * @return
	 */
	@Override
	public String updPwStdnt(String loginId, String userPw) {
		if(loginId == null || userPw == null){
			return "잘못된 접근입니다.";
		}
		
		boolean checkPw = VarComponent.checkRegExp(VarComponent.REGEX_USER_PW, userPw);
		if(!checkPw){
			return "비밀번호는 영문,숫자,특수문자,8자리 이상 입니다.";
		}
		
		Date curDate = new Date();
		UserInfo user = new UserInfo();
		user.setLoginId(loginId);
		//user.setUserPw(CryptoUtil.encrypt Password(userPw, userId));
		user.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(userPw));
		user.setLstLoginDe(curDate);
		user.setPwdErrorCnt(0);
		user.setLstPwDe(curDate);
		
		memberMapper.updateUserInfoStdntPw(user);
		
		return EXCUTE_SUCCESS;
	}

	/**
	 * 비밀번호를 확인한다.
	 * @param userId
	 * @param userPw
	 * @return
	 */
	@Override
	public String passWdChk(String loginId, String userPw) {
		//UserInfo user = memberMapper.selectUserInfoLogin(new UserInfo(userId, CryptoUtil.encrypt Password(userPw, userId)));
		UserInfo user = memberMapper.selectUserInfoLogin(new UserInfo(loginId, CryptoUtil.encodeSHA256CryptoNotDecode(userPw)));
		if(user == null){
			return "비밀번호가 일치하지 않습니다.";
		}
		return EXCUTE_SUCCESS;
	}

	/**
	 * 정보수정용 회원정보를 조회한다.
	 * @param userId
	 * @param userPw
	 * @return
	 */
	@Override
	public Map<String, String> getStdntInfo(String userId, String userPw) {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		UserInfo user = memberMapper.selectUserInfoLogin(new UserInfo(userId, CryptoUtil.encodeSHA256CryptoNotDecode(userPw)));
		if(user != null){
			UserInfo stdnt = memberMapper.selectStdntInfoByPk(user.getUserId());
			if(stdnt != null){
				resultMap.put("userId", stdnt.getUserId());
				//resultMap.put("email", stdnt.getEmail());
				//resultMap.put("email1", stdnt.getEmail().substring(0, stdnt.getEmail().indexOf("@")));
				//resultMap.put("email2", stdnt.getEmail().substring(stdnt.getEmail().indexOf("@") + 1));
				resultMap.put("mobile1", stdnt.getMobile().substring(0, 3));
				if(stdnt.getMobile().length() > 10){
					resultMap.put("mobile2", stdnt.getMobile().substring(3, 7));
					resultMap.put("mobile3", stdnt.getMobile().substring(7, 11));
				}else{
					resultMap.put("mobile2", stdnt.getMobile().substring(3, 6));
					resultMap.put("mobile3", stdnt.getMobile().substring(6, 10));
				}
				resultMap.put("userNm", stdnt.getUserNm());
				resultMap.put("postNo", stdnt.getPostNo());
				resultMap.put("addr", stdnt.getAddr());
				resultMap.put("addrDetail", stdnt.getAddrDetail());
				resultMap.put("addrEtc", stdnt.getAddrEtc());
				resultMap.put("birth", stdnt.getBirth());
				//resultMap.put("mfType", stdnt.getMfType());
				resultMap.put("loginId", stdnt.getLoginId());
			}
		}
		
		return resultMap;
	}

	/**
	 * 비밀번호 변경
	 * @param passOrg
	 * @param passNew
	 * @return
	 */
	@Override
	public String updPw(String passOrg, String passNew) {
		
		UserInfo sess = SessionUserInfoHelper.getUserInfo();
		if(sess == null){
			return "로그아웃 상태입니다.";
		}
		
		String loginId = sess.getLoginId();
		//UserInfo userInfo = memberMapper.selectUserInfoLogin(new UserInfo(userId, CryptoUtil.encrypt Password(passOrg, userId)));
		UserInfo userInfo = memberMapper.selectUserInfoLogin(new UserInfo(loginId, CryptoUtil.encodeSHA256CryptoNotDecode(passOrg)));
		
		
		if(userInfo == null){
			return "기존 비밀번호가 맞지 않습니다.";
		}
		
		UserInfo user = new UserInfo();
		user.setUserId(userInfo.getUserId());
		//user.setUserPw(CryptoUtil.encrypt Password(passNew, userId));
		user.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(passNew)); 
		user.setPwdErrorCnt(0);
		user.setLstPwDe(new Date());
		
		memberMapper.updateNewPw(user);
		
		return EXCUTE_SUCCESS;
	}

	/**
	 * 휴면회원 미니리스트를 조회한다.
	 * @param row
	 * @return
	 */
	@Override
	public List<UserInfoStdnt> getDrmncyMiniList(int row) {
		MemberVO vo = new MemberVO();
		
		Date curDate = new Date();	
		Date compareDe = DateUtil.dayCalForStr(Integer.parseInt(DateUtil.getDate2Str(curDate, "yyyy")), 
				Integer.parseInt(DateUtil.getDate2Str(curDate, "MM")), 
				Integer.parseInt(DateUtil.getDate2Str(curDate, "dd")), -365, "");

		vo.setCompareDe(DateUtil.getDate2Str(compareDe, "yyyyMMdd"));
		vo.setRow(row);
		
		return memberMapper.selectDrmncyMiniList(vo);
	}

	/**
	 * 회원정보 엑셀 파일을 생성한다.
	 * @param vo
	 * @return
	 */
	@Override
	public String createStdntListExcel(MemberVO vo) {
		
		vo.setUserMemLvl(UserInfo.MEM_LVL_STDNT);
		//List<UserInfoStdnt> userList = memberMapper.selectStdntListExcel(vo);
		List<UserInfo> userList = memberMapper.selectStdntList(vo);
		String filePath = MEMBER_EXCEL_PATH + "stdnt_" + System.currentTimeMillis() + ".xls";
		
		File dir = new File(MEMBER_EXCEL_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File file = new File(filePath); 
		WritableWorkbook workbook;
		
		try {
			workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("회원목록", 0);
			final WritableCellFormat FORMAT_TITLE = new WritableCellFormat();
			final WritableCellFormat FORMAT_DATA = new WritableCellFormat();
			
			FORMAT_TITLE.setFont( new WritableFont( WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE) );
			FORMAT_TITLE.setAlignment( Alignment.CENTRE );
			FORMAT_TITLE.setVerticalAlignment( VerticalAlignment.CENTRE );
			FORMAT_TITLE.setBackground(Colour.GREY_25_PERCENT);
			
			FORMAT_DATA.setFont( new WritableFont( WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE) );
			FORMAT_DATA.setAlignment( Alignment.CENTRE );
			FORMAT_DATA.setVerticalAlignment( VerticalAlignment.CENTRE );
			
			Label label;
			
			String[] header = {"성명", "아이디", "소속", "회원구분", "가입일자", "최종로그인"};
			int[] colWidth = {20, 20, 16, 16, 16, 16, 26, 32, 32, 14};
			int i = 0;
			
			for(String head : header){
				label = null;
				if(head != null && !"".equals(head)){
					label = new Label(i, 0, head, FORMAT_TITLE);
					sheet.addCell(label);
					sheet.setColumnView(i, colWidth[i]);
					i++;
				}
			}
			
			for(int j = 0; j < userList.size(); j++){
				UserInfo user = userList.get(j);
				label = new Label(0, (j+1), user.getUserNm(), FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(1, (j+1), user.getEmail(), FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(2, (j+1), "", FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(3, (j+1), user.getUserMemLvl().equals("9")?"정회원":"준회원", FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(4, (j+1), DateUtil.getDate2Str(user.getRgsde(), "yyyy-MM-dd HH:mm:ss"), FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(5, (j+1), DateUtil.getDate2Str(user.getLstLoginDe(), "yyyy-MM-dd HH:mm:ss"), FORMAT_DATA);
				sheet.addCell(label);
			}
			
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
		} catch (WriteException e) {
			LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
		}
		
		return filePath;
	}

	/**
	 * 강사정보 엑셀 파일을 생성한다.
	 * @param vo
	 * @return
	 */
	@Override
	public String createInstrctrListExcel(MemberVO vo) {
		
		vo.setUserMemLvl(UserInfo.MEM_LVL_INSTRCTR);
		List<UserInfoInstrctr> userList = memberMapper.selectInstrctrListExcel(vo);
		
		String filePath = MEMBER_EXCEL_PATH + "instrctr_" + System.currentTimeMillis() + ".xls";
		
		File dir = new File(MEMBER_EXCEL_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File file = new File(filePath);
		WritableWorkbook workbook;
		
		try {
			workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("강사목록", 0);
			final WritableCellFormat FORMAT_TITLE = new WritableCellFormat();
			final WritableCellFormat FORMAT_DATA = new WritableCellFormat();
			
			FORMAT_TITLE.setFont( new WritableFont( WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE) );
			FORMAT_TITLE.setAlignment( Alignment.CENTRE );
			FORMAT_TITLE.setVerticalAlignment( VerticalAlignment.CENTRE );
			FORMAT_TITLE.setBackground(Colour.GREY_25_PERCENT);
			
			FORMAT_DATA.setFont( new WritableFont( WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE) );
			FORMAT_DATA.setAlignment( Alignment.CENTRE );
			FORMAT_DATA.setVerticalAlignment( VerticalAlignment.CENTRE );
			
			Label label;
			
			String[] header = {"성명", "직위", "연락처", "소속", "전문분야", "등록일"};
			int[] colWidth = {20, 20, 16, 26, 40, 20};
			int i = 0;
			
			for(String head : header){
				label = null;
				if(head != null && !"".equals(head)){
					label = new Label(i, 0, head, FORMAT_TITLE);
					sheet.addCell(label);
					sheet.setColumnView(i, colWidth[i]);
					i++;
				}
			}
			
			List<InstrctrRealm> moduleList = null;
			String moduleStr = "";
		    int indexNo = 0;
			
			for(int j = 0; j < userList.size(); j++){
				UserInfoInstrctr user = userList.get(j);
				label = new Label(0, (j+1), user.getUserNm(), FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(1, (j+1), user.getPosition(), FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(2, (j+1), user.getMobile(), FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(3, (j+1), user.getBelong(), FORMAT_DATA);
				sheet.addCell(label);
				
				/*
				moduleList = memberMapper.seleteInstrctrRealmList(user.getUserId());
        		if(moduleList != null && !moduleList.isEmpty()){
        			String tmpModule = "";
        			for(InstrctrRealm module : moduleList){
        				if(module.getCtgryNm().toUpperCase().contains("AMS")){
        					tmpModule = "AMS";
        				}else if(module.getCtgryNm().toUpperCase().contains("QMS")){
        					tmpModule = "QMS";
        				}else if(module.getCtgryNm().toUpperCase().contains("ISMS")){
        					tmpModule = "ISMS";
        				}
        				if(indexNo > 0){
        					moduleStr += ", " + tmpModule;
        				}else{
        					moduleStr += tmpModule;
        				}
        				indexNo++;
        			}
        		}
        		
        		label = new Label(4, (j+1), moduleStr, FORMAT_DATA);
				sheet.addCell(label);
				*/
				label = new Label(4, (j+1), user.getArea(), FORMAT_DATA);
				sheet.addCell(label);
				
				label = new Label(5, (j+1), DateUtil.getDate2Str(user.getRgsde(), "yyyy-MM-dd"), FORMAT_DATA);
				sheet.addCell(label);
				
				moduleStr = "";
				indexNo = 0;
			}
			
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
		} catch (WriteException e) {
			LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
		}
		
		return filePath;
	}

	@Override
	public String checkOrgCd(String orgCd) {
		int cnt = memberMapper.selectOrgCdCnt(orgCd);
		if(cnt>0){
			return "이미 존재하는 코드입니다.";
		}
		return "";
	}
	@Override
	public String checkOrgNm(String orgCd,String orgNm) {
		Org userInfo = new Org();
		userInfo.setOrgCd(orgCd);
		userInfo.setOrgNm(orgNm);
		
		int cnt = memberMapper.selectOrgNmCnt(userInfo);
		if(cnt>0){
			return "이미 존재하는 기관명입니다.";
		}
		return "";
	}

	@Override
	public boolean existEmailByOrg(UserInfo userParam) {
		int cnt = memberMapper.selectUserInfoForEmailCnt(userParam);
		if(cnt>0){
			return true;
		}
		return false;
	}

	@Override
	public String checkInstrctrId(String userId) {
		String message="";
			
		//아이디규칙 체크 안함
		/*String regex = VarComponent.REGEX_USER_ID; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(userId); 
		
		if(!m.matches()) {
			message = "아이디는 영문 소문자로 시작하는<br/>영문 소문자, 숫자로 입력해주시기 바랍니다.<br/>(5 ~ 12자)";
		}else{
			if(!isUseableId(userId)){
				message = "사용중인 아이디 입니다.";
			}
		}*/
		if(!isUseableId(userId)){
			message = "사용중인 아이디 입니다.";
		}
		
		return message;
	}
	
	@Override
	public List<UserInfo> getInstrctrBulkList(EduVO vo) {
		return memberMapper.selectInstrctrListByEdu(vo);
	}

	@Override
	public Map<String, String> getInstrctrInfo(String userId, String userPw) {

		Map<String, String> resultMap = new HashMap<String, String>();
		
		//UserInfo user = memberMapper.selectUserInfoLogin(new UserInfo(userId, CryptoUtil.encrypt Password(userPw, userId)));
		UserInfo user = memberMapper.selectUserInfoLogin(new UserInfo(userId, CryptoUtil.encodeSHA256CryptoNotDecode(userPw)));
		if(user != null){
			UserInfoInstrctr stdnt = memberMapper.selectInstrctrByPk(user.getUserId());
			if(stdnt != null){
				resultMap.put("userId", stdnt.getUserId());
				resultMap.put("email", stdnt.getEmail());
				resultMap.put("email1", stdnt.getEmail().substring(0, stdnt.getEmail().indexOf("@")));
				resultMap.put("email2", stdnt.getEmail().substring(stdnt.getEmail().indexOf("@") + 1));
				
				resultMap.put("mobile1", stdnt.getMobile().substring(0, 3));
				if(stdnt.getMobile().length() > 10){
					resultMap.put("mobile2", stdnt.getMobile().substring(3, 7));
					resultMap.put("mobile3", stdnt.getMobile().substring(7, 11));
				}else{
					resultMap.put("mobile2", stdnt.getMobile().substring(3, 6));
					resultMap.put("mobile3", stdnt.getMobile().substring(6, 10));
				}
				
				resultMap.put("userNm", stdnt.getUserNm());
				resultMap.put("userNmEn", stdnt.getUserNmEn());
				resultMap.put("belong", stdnt.getBelong());
				resultMap.put("position", stdnt.getPosition());
				resultMap.put("postNo", stdnt.getPostNo());
				resultMap.put("addr", stdnt.getAddr());
				resultMap.put("addrDetail", stdnt.getAddrDetail());
				resultMap.put("addrEtc", stdnt.getAddrEtc());
				resultMap.put("photoOrg", stdnt.getPhotoOrg());
				resultMap.put("photoRename", stdnt.getPhotoRename());
				resultMap.put("memo", stdnt.getMemo());
				resultMap.put("loginId", stdnt.getLoginId());
			}
		}
		
		return resultMap;
	}

	@Override
	public String updateUserInfoInstrctr(UserInfo user) {
		user.setUserId(SessionUserInfoHelper.getUserId());
		
		UserInfoInstrctr userInfoInstrctr = new UserInfoInstrctr();
		userInfoInstrctr.setUserId(SessionUserInfoHelper.getUserId());
		memberMapper.updateUserInfoBase(user);
		memberMapper.updateUserInfoInstrctr(userInfoInstrctr);
		return EXCUTE_SUCCESS;
	}

	@Override
	public UserInfoInstrctr getInstrctrInfo(String userId) {
		return memberMapper.selectInstrctrByPk(userId);
	}

	@Override
	public String updOrgMngr(String userNm,String tel,String jiginFileNm,String orgNm,String orgEn,boolean jiginDel) {
		
		if(userNm == null || "".equals(userNm)){
			return "성명은 필수입력 입니다.";
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(SessionUserInfoHelper.getUserId());
		userInfo.setUserNm(userNm);
		userInfo.setTel(tel);
		memberMapper.updateMngrUserInfo(userInfo);
		
		return EXCUTE_SUCCESS;
	}

	@Override
	public boolean existLoginId(String userId) {
		int cnt = memberMapper.selectUserInfoForLoginIdCnt(userId);
		if(cnt>0){
			return true;
		}
		return false;
	}

	@Override
	public String saveMngrFile(String loginId, MultipartRequest mr,OrgVO orgParam) {
		String message = "";
		String folderName = "upload/org/";
		try {
			UserInfo user = memberMapper.selectUserInfoByLoginId(loginId);
			
			if(user == null){
				return "알수 없는 오류가 발생하였습니다.";
			}
			
			String userId = user.getUserId();
			
			//직인업로드
			//직인은 디비에 추가로 저장한다. html2canvas 동작가능하게 하기위해
			String jiginFileNm="";
			String jiginFileRenm="";
			byte[] jiginFileData=new byte[1024];
			MultipartFile mf = mr.getFile("jigin");
			String jiginOrg = "";
			if(!mf.isEmpty()){
				jiginOrg = new String(mf.getOriginalFilename().getBytes("8859_1"),"utf-8");
				String ext = jiginOrg.substring(jiginOrg.lastIndexOf(".") + 1);
				String fileRename = userId+"_jigin."+ext;
				//NaverObjectStorage.multiPartupload(mf, folderName, jiginOrg, fileRename);
				FileUtil.multiPartupload(mf, folderName, jiginOrg, fileRename);
				jiginFileNm=jiginOrg;
				jiginFileRenm=fileRename;
				jiginFileData=mf.getBytes();
			}
			
			//사업자등록증 업로드
			String bsnFileNm="";
			String bsnFileRenm="";
			mf = mr.getFile("bsnFile");
			String bsnOrg = "";
			if(!mf.isEmpty()){
				bsnOrg = new String(mf.getOriginalFilename().getBytes("8859_1"),"utf-8");
				String ext = bsnOrg.substring(bsnOrg.lastIndexOf(".") + 1);
				String fileRename = userId+"_bsn."+ext;
				//NaverObjectStorage.multiPartupload(mf, folderName, bsnOrg, fileRename);
				FileUtil.multiPartupload(mf, folderName, bsnOrg, fileRename);
				bsnFileNm=bsnOrg;
				bsnFileRenm=fileRename;
			}
			//통장사본 업로드
			String bankFileNm="";
			String bankFileRenm="";
			mf = mr.getFile("bankFile");
			String bankOrg = "";
			if(!mf.isEmpty()){
				bankOrg = new String(mf.getOriginalFilename().getBytes("8859_1"),"utf-8");
				String ext = bankOrg.substring(bankOrg.lastIndexOf(".") + 1);
				String fileRename = userId+"_bank."+ext;
				//NaverObjectStorage.multiPartupload(mf, folderName, bankOrg, fileRename);
				FileUtil.multiPartupload(mf, folderName, bankOrg, fileRename);
				bankFileNm=bankOrg;
				bankFileRenm=fileRename;
			}
			
			//파일정보 갱신
			orgParam.setOrgCd(user.getOrgCd());
			orgParam.setJiginFileNm(jiginFileNm);
			orgParam.setJiginFileRenm(jiginFileRenm);
			orgParam.setJiginFileData(jiginFileData);
			orgParam.setBsnFileNm(bsnFileNm);
			orgParam.setBankFileRenm(bankFileRenm);
			orgParam.setBankFileNm(bankFileNm);
			orgParam.setBsnFileRenm(bsnFileRenm);
			tableMapper.updateOrgByFileInfo(orgParam);
			
		} catch (NullPointerException | IOException e) {
			message = "알수 없는 오류가 발생하였습니다.";
		}
		return message;
	}

	@Override
	public CheckVO quitMemberProc(String loginId, String userPw) {
		CheckVO result = new CheckVO();
		try {
			UserInfo param = new UserInfo();
			param.setLoginId(loginId);
			param.setUserPw(CryptoUtil.encodeSHA256CryptoNotDecode(userPw));
			UserInfo user = memberMapper.selectUserInfoLogin(param);
			if(user==null){
				result.setResult(0);
				result.setMsg("사용자가 존재하지 않습니다.");
				return result;
			}
			
			//탈퇴처리
			param.setUserId(user.getUserId());
			memberMapper.updateUserInfoForDel(param);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			result.setMsg("알 수 없는 오류가 발생하였습니다. 관리자에게 문의하세요");
			return result;
		}
	}

	@Override
	public ResultVO joinUserAdd(String userId,int userTp
			,String compJsonp
			,String schoolListJsonp
			,String certifListJsonp
			,String languaListJsonp
			,String careerListJsonp) {
		ResultVO result = new ResultVO();
		try {
			String compJson = compJsonp;
			String schoolListJson = schoolListJsonp;
			String certifListJson = certifListJsonp;
			String languaListJson = languaListJsonp;
			String careerListJson = careerListJsonp;
			
			ObjectMapper om = new ObjectMapper();
			String updId = SessionUserInfoHelper.getUserId();
			Date updDe = new Date();
			if(userTp==1){
				//재직자 정보 저장
				compJson = LncUtil.unescapeJson(compJson);
				LOG.info("comp: "+compJson);
				UserComp compVO = om.readValue(LncUtil.unescapeJson(compJson), new TypeReference<UserComp>() {});
				compVO.setUpdDe(new Date());
				compVO.setUpdId(updId);
				
				//저장
				compVO.setUserId(userId);
				UserComp comp = userCompMapper.selectByPk(compVO);
				if(comp == null){
					userCompMapper.insertByPk(compVO);
				}else{
					userCompMapper.updateByPk(compVO);
				}
			}
			if(userTp == 2){
				//학력정보
				schoolListJson = LncUtil.unescapeJson(schoolListJson);
				LOG.info("schoolListJson: "+schoolListJson);
				List<UserSchool> schoolList = om.readValue(LncUtil.unescapeJson(schoolListJson), new TypeReference<List<UserSchool>>() {});
				int deleteResult1 = userSchoolMapper.deleteByUserId(userId);
				int schlSeq = 0;
				for(UserSchool o : schoolList){
					LOG.debug(o.toString());
					
					if(ObjectUtils.isEmpty(o.getSchoolNm())){
						break;
					}
					o.setBeginDt(DateUtil.getStr2Date(o.getBeginDtStr(), "yyyy-MM-dd"));
					o.setEndDt(DateUtil.getStr2Date(o.getEndDtStr(), "yyyy-MM-dd"));
					o.setUpdId(updId);
					o.setUpdDe(updDe);
					//저장
					schlSeq++;
					o.setUserId(userId);
					o.setSchlSeq(schlSeq);
					int saveResult = userSchoolMapper.insertByPk(o);
				}
				
				//자격정보
				certifListJson = LncUtil.unescapeJson(certifListJson);
				LOG.info("certifListJson: "+certifListJson);
				List<UserCertif> certifList = om.readValue(LncUtil.unescapeJson(certifListJson), new TypeReference<List<UserCertif>>() {});
				int deleteResult2 = userCertifMapper.deleteByUserId(userId);
				int crtfSeq = 0;
				for(UserCertif o : certifList){
					LOG.debug(o.toString());
					
					if(ObjectUtils.isEmpty(o.getCrtfNm())){
						break;
					}
					o.setCrtfDt(DateUtil.getStr2Date(o.getCrtfDtStr(), "yyyy-MM-dd"));
					o.setUpdId(updId);
					o.setUpdDe(updDe);
					//저장
					crtfSeq++;
					o.setUserId(userId);
					o.setCrtfSeq(crtfSeq);
					int saveResult = userCertifMapper.insertByPk(o);
				}
				
				//어학정보
				languaListJson = LncUtil.unescapeJson(languaListJson);
				LOG.info("languaListJson: "+languaListJson);
				List<UserLang> languaList = om.readValue(LncUtil.unescapeJson(languaListJson), new TypeReference<List<UserLang>>() {});
				int deleteResult3 = userLangMapper.deleteByUserId(userId);
				int langSeq = 0;
				for(UserLang o : languaList){
					LOG.debug(o.toString());
					
					if(ObjectUtils.isEmpty(o.getLangNm())){
						break;
					}
					o.setTestDt(DateUtil.getStr2Date(o.getTestDtStr(), "yyyy-MM-dd"));
					o.setUpdId(updId);
					o.setUpdDe(updDe);
					//저장
					langSeq++;
					o.setUserId(userId);
					o.setLangSeq(langSeq);
					int saveResult = userLangMapper.insertByPk(o);
				}
				
				//경력정보
				careerListJson = LncUtil.unescapeJson(careerListJson);
				LOG.info("careerListJson: "+careerListJson);
				List<UserCareer> careerList = om.readValue(LncUtil.unescapeJson(careerListJson), new TypeReference<List<UserCareer>>() {});
				int deleteResult4 = userCareerMapper.deleteByUserId(userId);
				int careerSeq = 0;
				for(UserCareer o : careerList){
					LOG.debug(o.toString());
					
					if(ObjectUtils.isEmpty(o.getCompNm())){
						break;
					}
					o.setBeginDt(DateUtil.getStr2Date(o.getBeginDtStr(), "yyyy-MM-dd"));
					o.setEndDt(DateUtil.getStr2Date(o.getEndDtStr(), "yyyy-MM-dd"));
					o.setUpdId(updId);
					o.setUpdDe(updDe);
					//저장
					careerSeq++;
					o.setUserId(userId);
					o.setCareerSeq(careerSeq);
					int saveResult = userCareerMapper.insertByPk(o);
				}
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | IOException e) {
			result.setResult(0);
			result.setMsg("알 수 없는 오류가 발생하였습니다. 관리자에게 문의하세요");
			return result;
		}
	}

	@Override
	public ResultVO saveJoinFile(String userId, MultipartFile mf1, MultipartFile mf2) {
		ResultVO result = new ResultVO();
		try {
			if(mf1!=null){
				String fileOrg = LncUtil.getEncode(mf1.getOriginalFilename());
				//String ext = FileUtil.getFileExt(fileOrg);
				String prefixStr = userId+"_LcnsFile";
				String fileRename = FileUtil.getFileRename(fileOrg, prefixStr);
				ResultVO result1 = FileUtil.multiPartupload(mf1, "upload/member/", fileOrg, fileRename);
				
				if(result1.getResult() != 1){
					result.setMsg("파일업로드 중 에러가 발생하였습니다. 다시 시도하시기 바랍니다.");
					result.setResult(0);
					return result;
				}
				
				//DB처리
				UserComp compParam = new UserComp();
				compParam.setUserId(userId);
				compParam.setBsnLcnsOrg(fileOrg);
				compParam.setBsnLcnsRnm(fileRename);
				compParam.setUpdDe(new Date());
				compParam.setUpdId(userId);
				userCompMapper.updateByPk(compParam);
			}
			if(mf2!=null){
				String fileOrg = LncUtil.getEncode(mf2.getOriginalFilename());
				//String ext = FileUtil.getFileExt(fileOrg);
				String prefixStr = userId+"_bankFile";
				String fileRename = FileUtil.getFileRename(fileOrg, prefixStr);
				ResultVO result1 = FileUtil.multiPartupload(mf2, "upload/member/", fileOrg, fileRename);
				
				if(result1.getResult() != 1){
					result.setMsg("파일업로드 중 에러가 발생하였습니다. 다시 시도하시기 바랍니다.");
					result.setResult(0);
					return result;
				}
				
				//DB처리
				UserComp compParam = new UserComp();
				compParam.setUserId(userId);
				compParam.setBsnBankOrg(fileOrg);
				compParam.setBsnBankRnm(fileRename);
				compParam.setUpdDe(new Date());
				compParam.setUpdId(userId);
				userCompMapper.updateByPk(compParam);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			result.setMsg("알 수 없는 오류가 발생하였습니다. 관리자에게 문의하세요");
			return result;
		}
	}

	@Override
	public ResultVO getMyUserInfoAdd(String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			UserComp compParam = new UserComp();
			compParam.setUserId(userId);
			UserComp comp = userCompMapper.selectByPk(compParam);
			rstData.put("comp", comp);
			
			//학력
			List<UserSchool> school = userSchoolMapper.selectByUserId(userId);
			rstData.put("school", school);
			for(UserSchool o: school){
				o.setBeginDtStr(DateUtil.getDate2Str(o.getBeginDt(), "yyyy-MM-dd"));
				o.setEndDtStr(DateUtil.getDate2Str(o.getEndDt(), "yyyy-MM-dd"));
			}
			
			//자격
			List<UserCertif> certif = userCertifMapper.selectByUserId(userId);
			rstData.put("certif", certif);
			for(UserCertif o: certif){
				o.setCrtfDtStr(DateUtil.getDate2Str(o.getCrtfDt(), "yyyy-MM-dd"));
			}
			
			//어학
			List<UserLang> langua = userLangMapper.selectByUserId(userId);
			rstData.put("langua", langua);
			for(UserLang o: langua){
				o.setTestDtStr(DateUtil.getDate2Str(o.getTestDt(), "yyyy-MM-dd"));
			}
			
			//경력
			List<UserCareer> career = userCareerMapper.selectByUserId(userId);
			rstData.put("career", career);
			for(UserCareer o: career){
				o.setBeginDtStr(DateUtil.getDate2Str(o.getBeginDt(), "yyyy-MM-dd"));
				o.setEndDtStr(DateUtil.getDate2Str(o.getEndDt(), "yyyy-MM-dd"));
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			result.setMsg("알 수 없는 오류가 발생하였습니다. 관리자에게 문의하세요");
			return result;
		}
	}

	@Override
	public ResultVO checkDupAuth(String authTypeNm, String authEncData) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			String authUserNm = "";
			String authBirth = "";
			String authGender = "";
			String authDupInfo = "";
			String authMobile = "";
			
			if("CHECK".equals(authTypeNm)){
				String sSiteCode = XmlBean.getConfigValue("nice.check.siteCd");				// NICE로부터 부여받은 사이트 코드
			    String sSitePassword = XmlBean.getConfigValue("nice.check.sitePw");			// NICE로부터 부여받은 사이트 패스워드
				NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
				int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, authEncData);
				
			    if( iReturn != 0 ) {
			        result.setResult(0);
			        return result;
			    }
			    
			    String sPlainData = niceCheck.getPlainData();
			    
			    // 데이타를 추출합니다.
			    java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
			    
			    authUserNm			= (String)mapresult.get("NAME");
			    authBirth		= (String)mapresult.get("BIRTHDATE");
			    authGender			= (String)mapresult.get("GENDER");
			    authDupInfo		= (String)mapresult.get("DI");
			    authMobile		= (String)mapresult.get("MOBILE_NO");
			    
			}else if("IPIN".equals(authTypeNm)){
				String sSiteCode	= XmlBean.getConfigValue("nice.ipin.siteCd");		//  NICE평가정보에서 발급한 IPIN 서비스 사이트코드
				String sSitePw		= XmlBean.getConfigValue("nice.ipin.sitePw");		//  NICE평가정보에서 발급한 IPIN 서비스 사이트패스워드
				// 모듈 객체 생성
			    IPIN2Client pClient = new IPIN2Client();
				
				// 인증결과 데이터 복호화
				// : CP요청번호 파라미터 추가 시 세션 추출값과 전송된 데이터 비교해 데이터 위변조 검사 가능
				// 예) int iRtn = pClient.fnResponse(sSiteCode, sSitePw, sResponseData, sCPRequest);	
				int iRtn = pClient.fnResponse(sSiteCode, sSitePw, authEncData);
				
				if (iRtn != 1) {
					result.setResult(0);
			        return result;
				}
				
				// 인증결과 추출
				authUserNm = pClient.getName();			// 성명 (EUC-KR)
				authGender				= pClient.getGenderCode();		// 성별 (0:여성, 1: 남성)
				authBirth				= pClient.getBirthDate();		// 생년월일 (YYYYMMDD)
				authDupInfo					= pClient.getDupInfo();			// 중복가입확인값 (64byte, 개인식별값, DI:Duplicate Info) 
		        
			}else{
				result.setResult(0);
		        return result;
			}
			
			rstData.put("authUserNm", authUserNm);
			rstData.put("authBirth", authBirth);
			rstData.put("authGender", authGender);
			rstData.put("authDupInfo", authDupInfo);
			rstData.put("authMobile", authMobile);
			
			if(ObjectUtils.isEmpty(authDupInfo)){
				result.setResult(0);
		        return result;
			}
			
			//중복회원 체크
			UserInfo user = memberMapper.selectUserInfoByCi(authDupInfo);
			if(user != null){
				rstData.put("loginId",user.getLoginId());
				result.setResult(0);
				result.setMsg("해당 인증정보로 가입된 계정이 존재합니다.");
		        return result;
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			result.setMsg("알 수 없는 오류가 발생하였습니다. 관리자에게 문의하세요");
			return result;
		}
	}
	
	@Override
	public ResultVO updateGpkiInfo(UserInfo userInfo) {
		ResultVO result = new ResultVO();
		
		try {

			memberMapper.updateGpkiInfo(userInfo);
			
			result.setResult(1);
			return result;
	
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	
	@Override
	public ResultVO updateMobileId(UserInfo userInfo) {
		ResultVO result = new ResultVO();
		
		try {

			memberMapper.updateMobileId(userInfo);
			
			result.setResult(1);
			return result;
	
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	
	@Override
	public ResultVO updateUserAllow(UserInfo userInfo) {
		ResultVO result = new ResultVO();
		
		try {
			List<Long> userIds = userInfo.getUserIds();
			List<Integer> userMemLvls = userInfo.getUserMemLvls();
			
			for (int i = 0; i < userIds.size(); i++) {
				userInfo.setUserId(""+userIds.get(i));
				userInfo.setUserMemLvl(""+userMemLvls.get(i));
				memberMapper.updateUserAllow(userInfo);
			}			
			
			result.setResult(1);
			return result;
	
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public int deleteUser(UserInfo userInfo) {
		return memberMapper.deleteUser(userInfo);
		
	}
}

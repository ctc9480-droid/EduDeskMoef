package com.educare.edu.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.member.service.model.InstrctrAttach;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoInstrctr;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.util.XmlBean;

/**
 * @Class Name : MemberService.java
 * @author SI개발팀 박용주
 * @since 2020. 5. 27.
 * @version 1.0
 * @see
 * @Description 회원 서비스 인터페이스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 5. 27.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public interface MemberService {
	
	public static final int PAGE_SIZE = 10;
	public static final String EXCUTE_SUCCESS = "SUCCESS";
	public static final String INSTRCTR_PHOTO_PATH = XmlBean.getServerContextRoot() + "/upload/member/photo/";
	public static final String INSTRCTR_ATTACH_PATH = XmlBean.getServerContextRoot() + "/upload/member/attach/";
	public static final String STDNT_BULK_PATH = XmlBean.getServerContextRoot() + "/upload/member/bulk/";
	public static final String MEMBER_EXCEL_PATH = XmlBean.getServerContextRoot() + "/upload/member/excel/";
	
	/**
	 * 관리자계정 리스트를 조회한다.
	 * @param vo
	 * @return
	 */
	public Map<String, Object> getMngrList(MemberVO vo);
	
	/**
	 * 관리자계정을 조회한다.
	 * @param userId
	 * @return
	 */
	public UserInfo getMngrDetail(String userId);
	
	/**
	 * 회원 리스트를 조회한다.
	 * @param vo
	 * @return
	 */
	public Map<String, Object> getStdntList(MemberVO vo);
	
	/**
	 * 회원 상세정보를 조회한다.
	 * @param userId
	 * @return
	 */
	public UserInfo getStdntInfo(String userId);
	
	/**
	 * 회원 아이디를 생성한다.
	 * @param userInfoStdnt
	 * @param vo
	 * @return
	 */
	public String saveStdnt(UserInfoStdnt userInfoStdnt, MemberVO vo);
	
	/**
	 * 회원 아이디를 대량 등록한다.
	 * @param request
	 * @return
	 */
	public String saveStdntBulk(MultipartHttpServletRequest request);
	
	/**
	 * 사용 가능한 아이디인지 조회한다.
	 * @param userId
	 * @return true : 사용가능, false : 사용불가
	 */
	public boolean isUseableId(String userId);
	
	/**
	 * 관리자 패스워드를 변경한다.
	 * @param userId
	 * @param userPw
	 * @return 
	 */
	public String mngrPwUpd(String userId, String userPw);
	
	/**
	 * 관리자 계정을 생성한다.
	 * @param vo
	 * @return 
	 */
	public ResultVO saveMngr(UserInfo userParam ,String[] menuIds,String[] authCtgrySeq);
	
	/**
	 * 관리자 계정을 삭제한다.
	 * @param userId
	 * @return
	 */
	public String mngrDelete(String userId);
	
	/**
	 * 최고관리자가 관리자 계정을 수정한다.
	 * @param vo
	 * @return
	 */
	public String updMngr(String userId,String userNm, String tel, String orgNm, String orgEn, boolean jiginDel, String jiginFileNm,String[] menuIds,String orgCd
			,String userMemLvl);
	
	/**
	 * 강사 리스트를 조회한다.
	 * @param vo
	 * @return
	 */
	public Map<String, Object> getInstrctrList(MemberVO vo);
	
	/**
	 * 강사정보를 저장한다.
	 * 관리자에서 사용하는 듯
	 * @param userInfoInstrctr
	 * @param vo
	 * @param request
	 * @return
	 */
	public ResultVO saveMngrInstrctr(UserInfoInstrctr param);
	
	/**
	 * 강사정보를 수정한다.
	 * @param userInfoInstrctr
	 * @param vo
	 * @param request
	 * @return
	 */
	public String updateInstrctr(UserInfoInstrctr userInfoInstrctr, MemberVO vo, MultipartHttpServletRequest request);
	
	/**
	 * 강사정보를 조회한다.
	 * @param vo
	 * @return
	 */
	public Map<String, Object> getInstrctrInfo(MemberVO vo);
	
	/**
	 * 강사 첨부파일을 조회한다.
	 * @param fileSeq
	 * @return
	 */
	public InstrctrAttach getInstrctrAttach(Integer fileSeq);
	
	/**
	 * 강사정보를 삭제한다.
	 * @param userId
	 */
	public void deleteInstrctr(String userId);
	
	/**
	 * 계정잠금 해제
	 * @param userId
	 */
	public void lockRelease(String userId);
	
	/**
	 * 휴면회원 리스트를 조회한다.
	 * @param vo
	 * @return
	 */
	public Map<String, Object> getDrmncyList(MemberVO vo);
	
	/**
	 * 교육과정 카테고리에 해당하는 강사 목록조회
	 * @param ctgrySeq
	 * @return
	 */
	public List<UserInfo> getInstrctrListByCategory(Integer ctgrySeq);
	
	/**
	 * 회원가입 처리
	 * @param careerListJson 
	 * @param languaListJson 
	 * @param certifListJson 
	 * @param schoolListJson 
	 * @param userInfoStdnt
	 * @param vo
	 * @return
	 */
	public ResultVO joinUser(UserInfo userInfo);
	
	/**
	 * 회원정보 수정
	 * @param userInfoStdnt
	 * @param vo
	 * @return
	 */
	public ResultVO updateUserInfoStdnt(UserInfo userInfo);
	
	/**
	 * 단체등록 가능한 수강생 목록을 조회한다.
	 * @param EduVO vo
	 * @return
	 */
	public List<UserInfo> getStdntBulkList(EduVO vo);
	
	/**
	 * 본인인증 중복검사
	 * @param model
	 * @return
	 */
	public String selfAuthDuplChk();
	
	/**
	 * 아이디 찾기
	 * @param vo
	 * @return
	 */
	public String findId(String userNm,String email);
	
	/**
	 * 비밀번호 찾기
	 * @param vo
	 * @return
	 */
	public String findPw(MemberVO vo);
	
	/**
	 * 비밀번호 찾기를 통한 변경 
	 * @param userId
	 * @param userPw
	 * @return
	 */
	public String updPwStdnt(String loginId, String userPw);
	
	/**
	 * 비밀번호 변경
	 * @param passOrg
	 * @param passNew
	 * @return
	 */
	public String updPw(String passOrg, String passNew);
	
	/**
	 * 비밀번호를 확인한다.
	 * @param userId
	 * @param userPw
	 * @return
	 */
	public String passWdChk(String userId, String userPw);
	
	/**
	 * 정보수정용 회원정보를 조회한다.
	 * @param userId
	 * @param userPw
	 * @return
	 */
	public Map<String, String> getStdntInfo(String userId, String userPw);
	
	/**
	 * 휴면회원 미니리스트를 조회한다.
	 * @param row
	 * @return
	 */
	public List<UserInfoStdnt> getDrmncyMiniList(int row);
	
	/**
	 * 회원정보 엑셀 파일을 생성한다.
	 * @param vo
	 * @return
	 */
	public String createStdntListExcel(MemberVO vo);
	
	/**
	 * 강사정보 엑셀 파일을 생성한다.
	 * @param vo
	 * @return
	 */
	public String createInstrctrListExcel(MemberVO vo);
	
	/**
	 * 기관관리자생성시 기관코드 중복 체크
	 * @param orgCd
	 * @return
	 */
	public String checkOrgCd(String orgCd);
	public String checkOrgNm(String orgCd,String orgNm);
	public boolean existEmailByOrg(UserInfo userParam);
	public String checkInstrctrId(String userId);

	public List<UserInfo> getInstrctrBulkList(EduVO vo);
	
	/**
	 * 강사 마이페이지 용
	 * @param userId
	 * @param userPw
	 * @return
	 */
	public Map<String, String> getInstrctrInfo(String userId, String userPw);
	/**
	 * 강사 마이페이지 수정 로직
	 * @param userInfoInstrctr
	 * @param vo
	 * @return
	 */
	public String updateUserInfoInstrctr(UserInfo userInfo);

	public boolean existEmail(String userId);

	public int saveStdntAdmin(UserInfo userInfo);

	public UserInfoInstrctr getInstrctrInfo(String userId);

	/**
	 * 기관관리자가 내정보 수정
	 * @param vo
	 * @return
	 */
	public String updOrgMngr(String userNm,String tel,String jiginFileNm,String orgNm,String orgEn,boolean jiginDel);

	public boolean existLoginId(String userId);

	public String saveMngrFile(String userId, MultipartRequest mr,OrgVO orgParam);

	/**
	 * 패스워드로 조회하여 회원이 존재하면 탈퇴 처리함
	 * @param loginId
	 * @param userPw
	 * @return
	 */
	public CheckVO quitMemberProc(String loginId, String userPw);

	public ResultVO joinUserAdd(String userId,int userTp, String compJson, String schoolListJson, String certifListJson, String languaListJson, String careerListJson);

	/**
	 * 회원가입 추가 파일저장
	 * @param mhsr
	 * @return
	 */
	public ResultVO saveJoinFile(String userId,MultipartFile mf,MultipartFile mf2);

	/**
	 * 재직자정보 조회
	 * @param userId
	 * @return
	 */
	public ResultVO getMyUserInfoAdd(String userId);

	/**
	 * 인증
	 * @param authCheckNm
	 * @param authEncData
	 * @return
	 */
	public ResultVO checkDupAuth(String authTypeNm, String authEncData);

	public ResultVO updateGpkiInfo(UserInfo userInfo);
	
	public ResultVO updateMobileId(UserInfo userInfo);

	/**
	 * 미승인 회원 승인처리
	 * @param dto
	 * @return
	 */
	public ResultVO updateUserAllow(UserInfo userInfo);

	public int deleteUser(UserInfo userInfo);

}

package com.educare.edu.member.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.educare.edu.comn.model.Org;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.member.service.MemberVO;
import com.educare.edu.member.service.model.InstrctrAttach;
import com.educare.edu.member.service.model.InstrctrRealm;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoInstrctr;
import com.educare.edu.member.service.model.UserInfoStdnt;

/**
 * @Class Name : MemberMapper.java
 * @author SI개발팀 박용주
 * @since 2020. 5. 27.
 * @version 1.0
 * @see
 * @Description 회원 Mapper
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 5. 27.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Mapper("MemberMapper")
public interface MemberMapper {
	
	UserInfo selectUserInfoByPk(String userId);
	
	UserInfo selectUserInfoLogin(UserInfo userInfo);
	
	void updateLoginSuccess(UserInfo userInfo);
	
	void updateLstLoginDe(UserInfo userInfo);
	
	int selectMngrListCnt(MemberVO vo);
	
	List<UserInfo> selectMngrList(MemberVO vo);
	
	int selectStdntListCnt(MemberVO vo);
	
	List<UserInfo> selectStdntList(MemberVO vo);
	
	List<UserInfoStdnt> selectStdntListExcel(MemberVO vo);
	
	UserInfo selectStdntInfoByPk(String userId);
	
	void updateUserPw(UserInfo userInfo);
	
	void updateUserInfoStdntPw(UserInfo userInfo);
	
	void updateNewPw(UserInfo userInfo);
	
	void insertUserInfo(UserInfo userInfo);
	
	void insertMngrUserInfo(UserInfo userInfo);
	
	void deleteMngrUserInfo(String userId);
	
	void updateMngrUserInfo(UserInfo userInfo);
	
	void updateUserInfo(UserInfo userInfo);
	
	void updateUserInfoBase(UserInfo userInfo);
	
	int selectInstrctrListCnt(MemberVO vo);
	
	List<UserInfo> selectInstrctrList(MemberVO vo);
	
	List<InstrctrRealm> seleteInstrctrRealmList(String userId);
	
	void deleteInstrctrRealm(Integer ctgrySeq);
	
	void deleteInstrctrRealmUser(String userId);
	
	List<InstrctrAttach> seleteInstrctrAttachList(MemberVO vo);
	
	List<InstrctrAttach> seleteInstrctrAttachListAll(String userId);
	
	void insertInstrctrAttach(InstrctrAttach instrctrAttach);
	
	void deleteInstrctrAttach(Integer fileSeq);
	
	void insertInstrctrRealm(InstrctrRealm instrctrRealm);
	
	void insertUserInfoInstrctr(UserInfoInstrctr userInfoInstrctr);
	
	void updateUserInfoInstrctr(UserInfoInstrctr userInfoInstrctr);
	
	UserInfoInstrctr selectInstrctrByPk(String userId);
	
	InstrctrAttach selectInstrctrAttachByPk(Integer fileSeq);
	
	void deleteInstrctrUserInfo(String userId);
	
	void deleteUserInfo(String userId);
	
	int selectDrmncyListCnt(MemberVO vo);
	
	List<UserInfo> selectDrmncyList(MemberVO vo);
	
	void insertUserInfoStdnt(UserInfoStdnt userInfoStdnt);
	
	List<UserInfo> selectInstrctrListByCategory(Integer ctgrySeq);
	
	List<UserInfo> selectStdntListByEdu(EduVO vo);
	
	UserInfo selectUserInfoByCi(String userId);
	
	List<UserInfo> selectUserFindId(MemberVO vo);
	
	UserInfo selectUserFindPw(MemberVO vo);
	
	void updateUserSelfAuth(UserInfo userInfo);
	
	void updateStdntSelfAuth(UserInfoStdnt userInfoStdnt);
	
	void updateUserInfoStdnt(UserInfoStdnt userInfoStdnt);
	
	List<UserInfoStdnt> selectDrmncyMiniList(MemberVO vo);
	
	List<UserInfoInstrctr> selectInstrctrListExcel(MemberVO vo);

	/**
	 * 기관별 이메일 존재 여부 체크용도
	 * @param user
	 * @return
	 */
	int selectUserInfoForEmailCnt(UserInfo user);

	String selectUserInfoForLastOrgUserId(UserInfo user);

	/**
	 * 관리자 계정중 기관코드 등록 건수
	 * @param orgCd
	 * @return
	 */
	int selectOrgCdCnt(String orgCd);
	int selectOrgNmCnt(Org org);

	List<UserInfo> selectInstrctrListByEdu(EduVO vo);

	UserInfo selectUserInfoLoginAdmin(UserInfo userInfo);

	int selectUserInfoForLoginIdCnt(String userId);

	String getUserIdByLoginId(String loginId);

	UserInfo selectUserInfoByLoginId(String loginId);

	void updateUserInfoForState(UserInfo userId);
	
	/**
	 * 탈퇴처리,로그인아이디뒤에 '_del'붙이기, state='S'
	 * @param param
	 */
	void updateUserInfoForDel(UserInfo param);
	

	UserInfo selectUserInfoByLoginIdAndUserMemLvl(String loginId);

	/**
	 * 연동아이디로 조회
	 * @param user
	 * @return
	 */
	UserInfo selectUserInfoByCompUserId(UserInfo user);

	void updatePwSync(UserInfo user);

	UserInfo selectUserInfoLoginAdminByLoginId(String userId);

	void updateLoginState(UserInfo user2);

	void updateLoginPwdErrorCntPlus(UserInfo user2);

	UserInfo selectUserInfoLoginBySubDn(UserInfo userInfo);
	
	void updateGpkiInfo(UserInfo userInfo);
	
	UserInfo selectUserInfoLoginBySubDn2(UserInfo userInfo);
	
	void updateMobileId(UserInfo userInfo);
	
	void updateUserAllow(UserInfo userInfo);

	int deleteUser(UserInfo userInfo);
}

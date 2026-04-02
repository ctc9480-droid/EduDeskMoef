package com.educare.component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserPrivt;

@Component("eccp")
public class EhCacheComponent {
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EhCacheComponent.class.getName());
	
	@Resource(name="MemberMapper")
	private MemberMapper memberMapper;
  
    @Cacheable(value = "globalMenuCache", key = "#userId")
    public UserPrivt getUserPrvt(String userId) {
    	LOG.info("caching...");
    	// 캐시에 없는 경우, 이 메소드가 호출됩니다.
        return fetchUserPrvtFromDb(userId);
    }
	
	// 실제 DB에서 사용자 정보를 가져오는 로직을 구현
	private UserPrivt fetchUserPrvtFromDb(String userId) {
		UserInfo user = memberMapper.selectUserInfoByPk(userId);
		//String rowId = user.getCompUserId();
		//ClientMember o2 = clientMemberMapper.selectByPk(rowId);
		 
		UserPrivt privt = new UserPrivt();
		privt.setUserId(userId);
		privt.setMobile(user.getDecMobile());
		privt.setEmail(user.getDecEmail());
		privt.setUserNm(user.getUserNm());
		privt.setLoginId(user.getLoginId());
		privt.setUserOrgNm(user.getUserOrgNm());
		privt.setUserGradeNm(user.getUserGradeNm());
		privt.setMfTypeNm(user.getMfTypeNm());
		privt.setBirth(user.getBirth());
		
		return privt;
    }
	
	//반환값을 캐시에 저장하기 때문에 void로 만들면 안됨
	@CachePut(value = "globalMenuCache", key = "#userId")
    public UserPrivt updateUserPrivt(String userId) {
        LOG.info("Updating user and cache...");
        // DB에 사용자 정보 업데이트 로직
        return fetchUserPrvtFromDb(userId);
    }
	
	@CacheEvict(value = "globalMenuCache", allEntries = true)
    public void refreshUserPrvtListCache() {
        // 메서드 내용은 비워도 됩니다. 캐시가 비워지기만 하면 됩니다.
    }

}

package com.educare.util;

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
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kcp.net.connection.util.Base64;

import org.apache.commons.httpclient.HttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.educare.cmm.schdul.SchdulService;
import com.educare.component.EhCacheComponent;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.member.service.model.UserPrivt;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class EhCacheUtil {
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EhCacheUtil.class.getName());
  
	public static UserPrivt getUserPrvt(String userId){
		if(userId == null){
			return null;
		}
		//LOG.info("call!!");
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = req.getSession();
		ServletContext conext = session.getServletContext();
		WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(conext);
		EhCacheComponent eccp = (EhCacheComponent)wContext.getBean("eccp");
		UserPrivt o = eccp.getUserPrvt(userId);
		return o;
    }

}

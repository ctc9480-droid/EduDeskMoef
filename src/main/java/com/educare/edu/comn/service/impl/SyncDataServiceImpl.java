package com.educare.edu.comn.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.sql.DataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

import com.educare.component.EhCacheComponent;
import com.educare.component.VarComponent;
import com.educare.edu.comn.mapper.LectureRceptMapper;
import com.educare.edu.comn.mapper.PayMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.Pay;
import com.educare.edu.comn.model.UserComp;
import com.educare.edu.comn.service.SyncDataService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.PayVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;

import com.educare.util.CryptoUtil;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;
import com.nhncorp.lucy.security.xss.CommonUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;


/**
 *
 *
 */
@Service("SyncDataService")
public class SyncDataServiceImpl implements SyncDataService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SyncDataServiceImpl.class.getName());
	
	@Resource(name="MemberMapper")
	private MemberMapper memberMapper;
	
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	@Resource(name="LectureRceptMapper")
	private LectureRceptMapper lectureRceptMapper;
	@Resource(name="PayMapper")
	private PayMapper payMapper;
	
	@Resource(name = "eccp")
	private EhCacheComponent eccp;
	
	 public static OkHttpClient createIgnoreSSLClient() {
	        try {
	            // Create a trust manager that does not validate certificate chains
	            TrustManager[] trustAllCerts = new TrustManager[] {
	                new X509TrustManager() {
	                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                        return null;
	                    }
	                    public void checkClientTrusted(
	                        java.security.cert.X509Certificate[] certs, String authType) {
	                    	//coment
	                    }
	                    public void checkServerTrusted(
	                        java.security.cert.X509Certificate[] certs, String authType) {
	                    	//coment
	                    }
	                }
	            };
	            
	            // Install the trust manager
	            SSLContext sc = SSLContext.getInstance("SSL");
	            sc.init(null, trustAllCerts, new java.security.SecureRandom());
	            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	           
	         // Install the all-trusting trust manager
	            SSLContext sslContext = SSLContext.getInstance("SSL");
	            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
	            HostnameVerifier hostnameVerifier = (hostname, session) -> true;

	            OkHttpClient client = new OkHttpClient();

	            // Set the SSL socket factory and hostname verifier
	            client.setSslSocketFactory(sslContext.getSocketFactory());
	            client.setHostnameVerifier(hostnameVerifier);

	            return client;
	        } catch (NullPointerException | KeyManagementException | NoSuchAlgorithmException e) {
	            return null;
	        }
	    }
	
}

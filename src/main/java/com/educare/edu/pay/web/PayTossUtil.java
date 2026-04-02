package com.educare.edu.pay.web;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.edu.comn.mapper.LectureRceptMapper;
import com.educare.edu.comn.mapper.PayMapper;
import com.educare.edu.comn.model.Pay;
import com.educare.edu.comn.service.PayService;
import com.educare.edu.comn.service.SyncDataService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.ConfigHandle;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletResponse;

public class PayTossUtil {
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(PayTossUserController.class.getName());
	private static final String API_SECRET_KEY = ConfigHandle.getProperties("pay.toss.secretKey");
	
	public static ResultVO paySelect(String orderId) throws Exception{
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String, Object>();
		result.setData(rstData);
		
		OutputStream outputStream = null;
		InputStream responseStream = null;
		Reader reader = null;
		try {
			
			String urlSTr = "https://api.tosspayments.com/v1/payments/orders/"+orderId;
			
			// TODO: 개발자센터에 로그인해서 내 결제위젯 연동 키 > 시크릿 키를 입력하세요. 시크릿 키는 외부에 공개되면 안돼요.
			// @docs https://docs.tosspayments.com/reference/using-api/api-keys
			String apiSecretKey = API_SECRET_KEY;

			// 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
			// 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
			// @docs
			// https://docs.tosspayments.com/reference/using-api/authorization#%EC%9D%B8%EC%A6%9D
			Base64.Encoder encoder = Base64.getEncoder();
			byte[] encodedBytes = encoder.encode((apiSecretKey + ":").getBytes(StandardCharsets.UTF_8));
			String authorizations = "Basic " + new String(encodedBytes);
			
			
			//add
			// SSL 인증서 검증을 무시하는 설정
	        TrustManager[] trustAllCertificates = new TrustManager[]{
	            new X509TrustManager() {
					
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						// TODO Auto-generated method stub
						
					}
				}
	        };
	        SSLContext sslContext = SSLContext.getInstance("TLS");
	        sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
	        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

			
			// 결제 승인 API를 호출하세요.
			// 결제를 승인하면 결제수단에서 금액이 차감돼요.
			// @docs
			// https://docs.tosspayments.com/guides/payment-widget/integration#3-결제-승인하기
			URL url = new URL(urlSTr);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestProperty("Authorization", authorizations);
			//connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestMethod("GET");
			//connection.setDoOutput(true);

			//outputStream = connection.getOutputStream();
			//outputStream.write(obj.toString().getBytes("UTF-8"));

			int code = connection.getResponseCode();
			boolean isSuccess = code == 200;

			responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

			JSONParser parser = new JSONParser();
			
			// TODO: 결제 성공 및 실패 비즈니스 로직을 구현하세요.
			reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			String resultJson = jsonObject.toJSONString();
			
			if(reader != null)reader.close();
			if(responseStream != null)responseStream.close();
			if(outputStream != null)outputStream.close();
			
			System.out.println(resultJson);
			
			if(code != 200){
				result.setMsg(jsonObject.get("message").toString());
				result.setResult(0);
				if("ALREADY_PROCESSED_PAYMENT".equals(jsonObject.get("code").toString())){
					result.setResult(2);//이미 처리된 결제
				}
				return result;
			}
				
			//결제성공시, crepas add,240920
			String status = jsonObject.get("status").toString();
			if("DONE".equals(status)){
				rstData.put("jsonObject",jsonObject);
				rstData.put("resultJson",resultJson);
				result.setResult(1);
				return result;
			}
			
			result.setResult(0);
			return result;
		} catch (NullPointerException | IOException | ParseException e) {
			result.setMsg(e.getMessage());
			result.setResult(0);
			return result;
		}finally{
			try {
				if(reader != null)reader.close();
				if(responseStream != null)responseStream.close();
				if(outputStream != null)outputStream.close();
			} catch (IOException e2) {
				LOG.error("finally 수행중 에러");
			}
		}
	}
}	

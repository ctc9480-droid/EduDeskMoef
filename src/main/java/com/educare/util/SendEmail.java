package com.educare.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.sun.mail.smtp.SMTPTransport;

/**
 * @Class Name : SendEmail.java
 * @author SI개발팀 박용주
 * @since 2020. 1. 22.
 * @version 1.0
 * @see
 * @Description 이메일 발송 관련 Utility Class
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 1. 22.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class SendEmail {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SendEmail.class.getName());
	
	/** 메일서버 호스트 */
	private static String 	host;
	/** 메일서버 포트 */
	private static Integer 	port;
	/** 메일서버 로그인 계정 */
	private static String 	loginId;
	/** 메일서버 로그인 비밀번호 */
	private static String 	loginPw;
	/** 메일서버 SSL 여부 */
	private static String 	sslYn;
	/** 메일서버 인코딩 타입 */
	private static String 	encode;
	/** 메일서버 보내는사람 메일주소 */
	private static String 	from;
	/** 메일서버 debug 여부 */
	private static String 	debug;
	
	/**
	 * <pre>
	 * SendEmail 초기화
	 * 멤버변수 SETTING
	 * </pre>
	 */
	private static void initConfig(){
		if(host == null){
			host = XmlBean.getConfigValue("mail.smtp.host");
			port = Integer.parseInt(XmlBean.getConfigValue("mail.smtp.port"));
			loginId = XmlBean.getConfigValue("mail.smtp.id");
			loginPw = XmlBean.getConfigValue("mail.smtp.pw");
			sslYn = XmlBean.getConfigValue("mail.smtp.sslYn");
			encode = XmlBean.getConfigValue("mail.smtp.encode");
			from = XmlBean.getConfigValue("mail.smtp.from");
			debug = XmlBean.getConfigValue("mail.smtp.debug");
		}
	}
	
	/**
	 * <pre>
	 * SendEmailVo 를 사용해 받는이메일, 제목, 내용 설정
	 * 받는 이메일은 ',' or ';' 로 구분하여 여러명 발송 가능
	 * 보내는 이메일은 계정과 동일하지 않을 경우 메일발송이 안될 수 있음.
	 * 필요 시에만 입력.
	 * </pre>
	 * @param vo k2web.com.util.SendEmailVo
	 * @return 발송 성공 : "", 실패 : "MAIL_ERROR"
	 */
	public static String sendEmail_bak(SendEmailVo vo){
		if(vo == null){
			return null;
		}
		
		
		initConfig();
		
		String result = ""; 
		if(vo.getToEmail() != null && !"".equals(vo.getToEmail())){
			try {
				
				//json생성
				HashMap<String, Object> dataMap0 = new HashMap<String, Object>();
				HashMap<String, String> dataMap = new HashMap<String, String>();
				dataMap0.put("data", dataMap);
				dataMap.put("title", vo.getSubject());
				dataMap.put("content", vo.getMessage());
				dataMap.put("sendInfo", "KTL ");
				dataMap.put("rcvInfo", vo.getToEmail());
				dataMap.put("sendDate", "");
				dataMap.put("sendType", "D");
				dataMap.put("categoryNm", "LMS");
				dataMap.put("linkNm", "LMS");
				dataMap.put("memo", "");
				ObjectMapper om = new ObjectMapper();
				String jsonData = om.writeValueAsString(dataMap0);
				OkHttpClient client = new OkHttpClient();

				MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
				RequestBody body = RequestBody.create(mediaType, jsonData);
				Request request = new Request.Builder()
				.url("http://ems.ncrc.or.kr/api/sendMail.do")
				.post(body)
				.addHeader("Content-Type", "application/json")
				//.addHeader("Authorization", "Basic "+Base64.encode((SMS_ID+":"+ACCESS_TOKEN).getBytes()))
				.addHeader("cache-control", "no-cache")
				.build();
				Response response = client.newCall(request).execute();
				String resultJson = response.body().string();
				LOG.info("result : "+resultJson);
				
				ObjectMapper om2 = new ObjectMapper();
				Map<String, Object> resultMap = om2.readValue(resultJson, Map.class);
				
				Map<String, String> resultMap2 = (Map<String, String>) resultMap.get("result");
				if(!"SUCCESS".equals(resultMap2.get("result"))){
					return "MAIL_ERROR";
				}
				
				result = "";
			} catch (NullPointerException | IOException e) {
				result = "MAIL_ERROR";
			}
			
		}else{
			result = "MAIL_ERROR";
		}
		return result;
	}
	
	/**
	 * <pre>
	 * SendEmailVo 를 사용해 받는이메일, 제목, 내용 설정
	 * 받는 이메일은 ',' or ';' 로 구분하여 여러명 발송 가능
	 * 보내는 이메일은 계정과 동일하지 않을 경우 메일발송이 안될 수 있음.
	 * 필요 시에만 입력.
	 * </pre>
	 * @param vo k2web.com.util.SendEmailVo
	 * @return 발송 성공 : "", 실패 : "MAIL_ERROR"
	 */
	public static String sendEmail_bak2(SendEmailVo vo){
		if(vo == null){
			return null;
		}
		
		
		initConfig();
		
		String result = ""; 
		if(vo.getToEmail() != null && !"".equals(vo.getToEmail())){
			try{
		        Properties propsTLS = new Properties();
		        propsTLS.put("mail.transport.protocol", "smtp");
		        if("Y".equals(sslYn)){
		        	propsTLS.put("mail.transport.protocol", "smtps");
		        }
		        
		        if("Y".equals(debug)){
		        	propsTLS.put("mail.debug", "true");
		        }
		        propsTLS.put("mail.smtp.host", host);
		        propsTLS.put("mail.smtp.auth", "true");
		        propsTLS.put("mail.smtp.starttls.enable", "true");
		        
		        Session sessionTLS = Session.getInstance(propsTLS);
		        sessionTLS.setDebug(false);
		        if("Y".equals(debug)){
		        	sessionTLS.setDebug(true);
		        }
		        
		        Message messageTLS = new MimeMessage(sessionTLS);
		        String fromName = "";
		        if(vo.getFromName() != null && !"".equals(vo.getFromName())){
		        	fromName = MimeUtility.encodeText(vo.getFromName(), encode, "B");	 
		        }
		        
		        if(vo.getFromEmail() != null && !"".equals(vo.getFromEmail())){
		        	messageTLS.setFrom(new InternetAddress(vo.getFromEmail(), fromName));
		        }else{
		        	messageTLS.setFrom(new InternetAddress(from, fromName)); 
		        }
		        
		        if(vo.getToEmail().indexOf(",") != -1 ||vo.getToEmail().indexOf(";") != -1){
		        	messageTLS.setRecipients(Message.RecipientType.TO, makerecipients(vo.getToEmail())); // real recipient
		        }else{
		        	messageTLS.setRecipients(Message.RecipientType.TO, InternetAddress.parse(vo.getToEmail())); // real recipient
		        	
		        }
		        
		        messageTLS.setSubject(MimeUtility.encodeText(vo.getSubject(), encode, "B"));
		        
		        MimeBodyPart mbpMailBody = new MimeBodyPart();
		        mbpMailBody.setText(vo.getMessage());
		        
		        messageTLS.setHeader("Content-type", "text/html; charset="+encode);
		        messageTLS.setSentDate(new Date());
		        
		        messageTLS.setContent(vo.getMessage(),"text/html; charset="+encode);
		        Transport transportTLS = sessionTLS.getTransport();
		        transportTLS.connect(host, port, loginId, loginPw); // account used
		        transportTLS.sendMessage(messageTLS, messageTLS.getAllRecipients());
		        transportTLS.close();
		        
			}catch(MessagingException me){
				LOG.error("ERROR:",me);
				result = "MAIL_ERROR";
			 }catch(UnsupportedEncodingException e){
				LOG.error("ERROR:",e);
				result = "MAIL_ERROR";
			 }
			
		}else{
			result = "MAIL_ERROR";
		}
		return result;
	}
	
	public static String sendEmail(SendEmailVo vo){
		if(vo == null){
			return null;
		}
		
		
		initConfig();
		
		String result = ""; 
		if(vo.getToEmail() != null && !"".equals(vo.getToEmail())){
			try{
				String protocol = "smtp";
		        Properties propsTLS = System.getProperties();
		        
		        propsTLS.put("mail.transport.protocol", protocol);
		        propsTLS.put("mail."+protocol+".host", host);
		        
		        if("Y".equals(sslYn)){
		        	protocol = "smtps";
		        	propsTLS.put("mail.transport.protocol", protocol);
		        	
		        	propsTLS.put("mail."+protocol+".auth", "true");
		        	propsTLS.put("mail."+protocol+".ssl.enable", "true");
			        propsTLS.put("mail."+protocol+".ssl.trust", host);
		        } else {
		        	propsTLS.put("mail."+protocol+".auth", "false");
		        	propsTLS.put("mail."+protocol+".starttls.enable", "false"); 
		        	propsTLS.put("mail."+protocol+".ssl.enable", "false");
		        }
		        
		        if("Y".equals(debug)){
		        	propsTLS.put("mail.debug", "true");
		        }
		        
		        
		        //propsTLS.put("mail.smtp.starttls.enable", "true");
		        
		        
		        Session sessionTLS = Session.getInstance(propsTLS, null);
		        sessionTLS.setDebug(false);
		        if("Y".equals(debug)){
		        	sessionTLS.setDebug(true);
		        }
		        
		        Message messageTLS = new MimeMessage(sessionTLS);
		        String fromName = "";
		        if(vo.getFromName() != null && !"".equals(vo.getFromName())){
		        	fromName = MimeUtility.encodeText(vo.getFromName(), encode, "B");	 
		        }
		        
		        if(vo.getFromEmail() != null && !"".equals(vo.getFromEmail())){
		        	messageTLS.setFrom(new InternetAddress(vo.getFromEmail(), fromName));
		        }else{
		        	messageTLS.setFrom(new InternetAddress(from, fromName)); 
		        }
		        
		        if(vo.getToEmail().indexOf(",") != -1 ||vo.getToEmail().indexOf(";") != -1){
		        	messageTLS.setRecipients(Message.RecipientType.TO, makerecipients(vo.getToEmail())); // real recipient
		        }else{
		        	messageTLS.setRecipients(Message.RecipientType.TO, InternetAddress.parse(vo.getToEmail())); // real recipient
		        	
		        }
		        
		        messageTLS.setSubject(MimeUtility.encodeText(vo.getSubject(), encode, "B"));
		        
		        MimeBodyPart mbpMailBody = new MimeBodyPart();
		        mbpMailBody.setText(vo.getMessage());
		        
		        messageTLS.setHeader("Content-type", "text/html; charset="+encode);
		        messageTLS.setSentDate(new Date());
		        
		        messageTLS.setContent(vo.getMessage(),"text/html; charset="+encode);
		        SMTPTransport transportTLS = (SMTPTransport) sessionTLS.getTransport(protocol);
		        if("".equals(loginId)){
		        	transportTLS.connect();
		        } else {
		        	transportTLS.connect(host, port, loginId, loginPw); // account used
		        }
		        //		        
		        transportTLS.sendMessage(messageTLS, messageTLS.getAllRecipients());
		        transportTLS.close();
		        
			}catch(MessagingException me){
				LOG.error("ERROR:",me);
				result = "MAIL_ERROR";
			 }catch(UnsupportedEncodingException e){
				LOG.error("ERROR:",e);
				result = "MAIL_ERROR";
			 }
			
		}else{
			result = "MAIL_ERROR";
		}
		return result;
	}
	
	/**
	 * 다중발송 메일의 경우 수신자 메일 주소를 InternetAddress[]로 변환한다.
	 * @param addrs 수신자 메일 주소
	 * @return javax.mail.internet.InternetAddress[]
	 * @throws javax.mail.internet.AddressException
	 */
	private static InternetAddress[] makerecipients(String addrs) throws AddressException{
		 StringTokenizer toker;
		 String delim = "";   //구분자 
		 InternetAddress[] addr = null;
		 
		 if(addrs != null){   //참조 주소가 있을때
			 if(addrs.indexOf(",") != - 1){   // 참조메일을 , 로 구분했으면...
				 delim = ",";
			 }else if(addrs.indexOf(";") != -1){  // 참조메일을 ; 로 구분했으면...
				 delim = ";";
			 }
			 
			 toker = new StringTokenizer(addrs ,delim);    // ,나 ;로  이메일주소 구분하여 토크나이져로 분리
			 int count  = toker.countTokens();      // 참조 이메일 카운트
			 addr = new InternetAddress[count];      
			 int cnt = 0;
			   
			 while(toker.hasMoreTokens()){
			 	addr[cnt++] = new InternetAddress(toker.nextToken().trim());
			 } //while   
		 }   
		 return addr;
	}
}

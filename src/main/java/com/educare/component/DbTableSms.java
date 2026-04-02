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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.cmm.schdul.SchdulService;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.util.DateUtil;
import com.mysql.fabric.xmlrpc.base.Array;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Component("dbTableSms")
public class DbTableSms {
	private final static String SENDER_KEY= "0a48d0f8ca00066fd5a6809edd533ff6838883f1";
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(DbTableSms.class.getName());
	
	
	public ResultVO sendAlimTalk(String toNum,String msg,String tplCd){
		ResultVO result = new ResultVO();
		try {
			if(ObjectUtils.isEmpty(toNum)){
				result.setMsg("수신번호형태가 잘못되었습니다 : "+toNum);
				result.setResult(0);
				return result;
			}
			if(ObjectUtils.isEmpty(msg) || ObjectUtils.isEmpty(tplCd)){
				result.setMsg("데이터가 제대로 입력되지 않았습니다.");
				result.setResult(0);
				return result;
			}
			
			if(!"ktl_edu_002,ktl_edu_004".contains(tplCd)){
				//result.setMsg("템플릿코드가 잘못되었습니다.");
				//result.setResult(0);
				//return result;
			}
			
			//db처리
			
			result.setResult(0);//임시 실패처리
			return result;
		} catch (NullPointerException  e) {
			result.setMsg(e.getMessage());
			result.setResult(0);
			return result;
		}
		
	}
	
}

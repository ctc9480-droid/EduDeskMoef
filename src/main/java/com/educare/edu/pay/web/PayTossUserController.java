package com.educare.edu.pay.web;

import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.util.ObjectUtils;
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
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/user/pay/toss/")
public class PayTossUserController {

	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(PayTossUserController.class.getName());

	@Resource(name="MemberService")
	private MemberService memberService;
	@Resource(name="EduService")
	private EduService eduService;
	@Resource(name = "PayMapper")
	private PayMapper payMapper;
	@Resource(name = "PayService")
	private PayService payService;
	@Resource(name = "LectureRceptMapper")
	private LectureRceptMapper lectureRceptMapper;
	@Resource(name = "SyncDataService")
	private SyncDataService syncDataService;
	
	private final String API_SECRET_KEY = ConfigHandle.getProperties("pay.toss.secretKey");
	
	@RequestMapping(value = "order.do")
	public String order(ModelMap model, HttpServletResponse res
			,int rceptSeq
			,int eduSeq
			) throws Exception {
		
		String userId = SessionUserInfoHelper.getUserId();
		
		LectureRcept rcept = eduService.getMyRceptDetail(rceptSeq, eduSeq, userId);
		if(rcept==null){
			LncUtil.alertBack(res, "잘못된 경로로 접근하였습니다.");
			return null;
		}
		
		Pay pay = payMapper.selectByPk(rcept.getPayNo());
		if(pay!=null){
			if(pay.getState()==2){
				LncUtil.alertBack(res, "이미 결제 완료되었습니다.","/user/mypage/myLcRceptList.do");
				return null;
			}
			if(pay.getState() == 1){
				LncUtil.alertBack(res, "이미 결제대기중에 있습니다.");
				return null;
			}
		}
		
		UserInfo user = memberService.getStdntInfo(userId);
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		
		String goodName = lctre.getEduNm();
		int goodMny = lctre.getLimsFee();
		String buyrName = user.getUserNm();
		
		String ordrIdxx = "E_"+eduSeq+"_"+userId+"_"+DateUtil.getDate2Str(new Date(), "yyyyMMddHHmmss");
		
		//신청서에 주문번호 저장
		LectureRcept vo = new LectureRcept();
		vo.setRceptSeq(rceptSeq);
		vo.setPayNo(ordrIdxx);
		lectureRceptMapper.updateByRceptSeq(vo);
		
		
		model.addAttribute("user",user);
		model.addAttribute("good_name",goodName);
		model.addAttribute("good_mny",goodMny);
		model.addAttribute("buyr_name",buyrName);
		model.addAttribute("ordr_idxx",ordrIdxx);
		
		return "/user/pay/toss/order";
	}

	@RequestMapping(value = "success.do")
	public String success(ModelMap model, HttpServletResponse res
			,@RequestParam Map<String, String> param
			) throws Exception {
		//로그인체크
		if(!SessionUserInfoHelper.isLogined()) {
			LncUtil.alertBack(res, "로그아웃 되었습니다. 다시 시도하시기 바랍니다.");
			return null;
		}
		
		LOG.info("param",param);
		String orderId = param.get("orderId");
		String amount = param.get("amount");
		String paymentKey = param.get("paymentKey");
		
		ResultVO result =  checkPayInfo(orderId,amount);
		if(result.getResult() == 0){
			LncUtil.alertBack(res, result.getMsg());
			return null;
		}
		
		//신청서조회
		LectureRcept lrVO = new LectureRcept();
		lrVO.setPayNo(orderId);
		LectureRcept rcept = lectureRceptMapper.selectByPayNo(lrVO);
		if(rcept == null){
			LncUtil.alertBack(res, "신청건에 문제가 있습니다. 관리자에게 문의하세요");
			return null;
		}
		
		//결과페이지로 이동위해 관련 파라미터값 호출
		int eduSeq = rcept.getEduSeq();
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		model.addAttribute("eduSeq",eduSeq);
		model.addAttribute("srchCtgry",lctre.getCtg1Seq());
		
		
		//승인
		ResultVO cnfmResult = payConfirm(orderId, paymentKey, amount);
		if(cnfmResult.getResult() < 1){//1:승인완료, 2:이미처리된결제
			LncUtil.alertBack(res, cnfmResult.getMsg());
			return null;
		}
		Map<String, Object> rstData = (Map<String, Object>) cnfmResult.getData();
		
		
		//결제성공로직
		String payNo = orderId;
		String userId = SessionUserInfoHelper.getUserId();
		int payType = 1;//무조건 카드
		String pgPayNo = paymentKey;
		String pgNm = "toss";
		int amount2 = LncUtil.nvlInt(amount);
		String pgResultData = rstData.get("resultJson").toString();
		int result2 = payService.setSuccessPay(payNo, userId, amount2, payType, pgPayNo, pgNm,pgResultData);
		
		
		return "/user/pay/toss/success";
	}
	
	public ResultVO checkPayInfo(String orderId,String amount){
		ResultVO result = new ResultVO();
		try {
			LOG.info("orderId",orderId);
			LOG.info("amount",amount);
			
			//주문번호 유효성 체크
			LectureRcept lrVO = new LectureRcept();
			lrVO.setPayNo(orderId);
			LectureRcept rcept = lectureRceptMapper.selectByPayNo(lrVO);
			if(rcept == null){
				result.setMsg("주문번호가 잘못되었습니다.");
				result.setResult(0);
				return result;
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	
	@RequestMapping(value = "fail.do")
	public String fail(ModelMap model, HttpServletResponse res
			,String message
			,String orderId
			) throws Exception {
		
		
		String[] parts = orderId.split("_");
		int eduSeq = Integer.parseInt(parts[1]);
		model.addAttribute("eduSeq",eduSeq);
		return "/user/pay/toss/fail";
	}
	public ResultVO payConfirm(String orderId, String paymentKey,String amount) throws Exception{
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String, Object>();
		result.setData(rstData);
		
		OutputStream outputStream = null;
		InputStream responseStream = null;
		Reader reader = null;
		try {
			JSONObject obj = new JSONObject();
			obj.put("orderId", orderId);
			obj.put("amount", amount);
			obj.put("paymentKey", paymentKey);

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
			URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Authorization", authorizations);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			outputStream = connection.getOutputStream();
			outputStream.write(obj.toString().getBytes("UTF-8"));

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
	
	@ResponseBody
	@RequestMapping(value = "sync.do")
	public ResultVO sync(ModelMap model, HttpServletResponse res
			,String orderId
			) throws Exception {
		LOG.info("==sync start==");
		ResultVO result = new ResultVO();
		
		//토스승인건 조회
		ResultVO result1 = PayTossUtil.paySelect(orderId);
		if(result1.getResult() != 1) {
			return result1;
		}
		Map<String, Object> rstData = (Map<String, Object>) result1.getData();
		String resultJson = (String) rstData.get("resultJson");
		LOG.info("resultJson : {}",resultJson);
		
		//파싱
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> resultMap = om.readValue(resultJson, Map.class);
		Map<String, Object> pgCardMap = (Map<String, Object>) resultMap.get("card");
		String paymentKey = (String) resultMap.get("paymentKey");
		int amount = LncUtil.nvlInt(resultMap.get("totalAmount"));	//결제총금액
		String approvedAt = (String) resultMap.get("approvedAt");
		Date approvedDt =  Date.from(OffsetDateTime.parse(approvedAt).toInstant());
		LOG.info("approvedDt : {}",approvedDt);
		
		//주문번호 분리
		String[] orderIdArr = orderId.split("_");
		int eduSeq = LncUtil.nvlInt(orderIdArr[1]);
		String userId = orderIdArr[2];
		LOG.info("1. 주문번호분리 eduSeq : {} | userId : {}",eduSeq,userId);
		
		//해당 주문번호와 관련된 신청서조회
		LectureRcept lrVO = new LectureRcept();
		lrVO.setEduSeq(eduSeq);
		lrVO.setUserId(userId);
		List<LectureRcept> rceptList = lectureRceptMapper.selectByEduSeqNUserId(lrVO);
		if(ObjectUtils.isEmpty(rceptList)){
			result.setResult(0);result.setMsg("해당 주문번호로 조회되는 신청서가 없습니다.");;
			return result;
		}
		
		LectureRcept rcept = null;
		for(LectureRcept o: rceptList) {
			if(o.getState() == 1 || o.getState() == 2) {
				if(orderId.equals(o.getPayNo())) {
					rcept = o;
					break;
				}else if(ObjectUtils.isEmpty(o.getPayNo())){
					rcept = o;
					//주문번호비어있으면 갱신
					LectureRcept param = new LectureRcept();
					param.setRceptSeq(o.getRceptSeq());
					param.setPayNo(orderId);
					lectureRceptMapper.updateAnyByPk(param);
					break;
				}
			}
		}
		if(rcept == null) {
			result.setResult(0);result.setMsg("이미 다른주문번호가 저장되어 있습니다. 관리자에게 문의하세요");
			return result;
		}
		
		
		//결제성공로직
		String payNo = orderId;
		int payType = 1;//무조건 카드
		String pgPayNo = paymentKey;
		String pgNm = "toss";
		int amount2 = LncUtil.nvlInt(amount);
		String pgResultData = rstData.get("resultJson").toString();
		int result2 = payService.setSuccessPay(payNo, userId, amount2, payType, pgPayNo, pgNm,pgResultData);
		
		LOG.info("payService.setSuccessPay : {}",result2);
		
		result.setMsg(result.getMsg());
		result.setResult(1);
		return result;
	}
}	

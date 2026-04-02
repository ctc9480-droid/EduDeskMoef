package com.educare.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.educare.edu.comn.vo.ResultVO;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * @Class Name : LncUtil.java
 * @author SI개발팀 박용주
 * @since 2019. 10. 15.
 * @version 1.0
 * @see
 * @Description
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2019. 10. 15.	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class LncUtil {
	
	public static final String TILES = ".tiles";
	private static final SecureRandom random = new SecureRandom();
	
	/**
	 * Cross Site Script 해당 문자열을 치환한다.
	 * @param str1 Cross Site Script 치환할 문자열
	 * @return Cross Site Script 치환된 문자열
	 */
	public static String replaceXSS(String str1){
		String str = str1;
		if(str != null && !"".equals(str)){
			str = getTrans(str);
		}
		return str;
	}
	
	/**
	 * script 만 replace 처리한다.
	 * @param str script 치환할 문자열
	 * @return script 치환된 문자열
	 */
	public static String replaceScriptOnly(String str1){
		String str =str1;
		if(str != null && !"".equals(str)){
			str = str.replaceAll("<+[S,s][C,c][R,r][I,i][P,p][T,t]", "&lt;script")
				.replaceAll("<+/+[S,s][C,c][R,r][I,i][P,p][T,t]", "&lt;/script");
		}
		return str;
	}
	
	/**
	 * Cross Site Script를 치환한다.
	 * @param str1 Cross Site Script 치환할 문자열
	 * @return Cross Site Script 치환된 문자열
	 */
	public static String getTrans(String str1){
		String str = str1;
		if(str == null || ("").equals(str)){
			return str;
		}else{
			str = str.replaceAll("<!--StartFragment-->","");
			
			//s = s.replaceAll("--","");
			
			str = str
			   .replaceAll("<+[I,i][F,f][R,r][A,a][M,m][E,e]", "&lt;frame")
			   .replaceAll("<+/+[I,i][F,f][R,r][A,a][M,m][E,e]", "&lt;/frame")
			   .replaceAll("<+[O,o][B,b][J,j][E,e][C,c][T,t]", "&lt;object")
               .replaceAll("<+/+[O,o][B,b][J,j][E,e][C,c][T,t]", "&lt;/object")
			   .replaceAll("<+[S,s][C,c][R,r][I,i][P,p][T,t]", "&lt;script")
			   .replaceAll("<+/+[S,s][C,c][R,r][I,i][P,p][T,t]", "&lt;/script")
			   .replaceAll("<+[S,s][T,t][Y,y][L,l][E,e]", "&lt;style")
			   .replaceAll("<+/+[S,s][T,t][Y,y][L,l][E,e]", "&lt;/style")
			   .replaceAll("<+[M,m][E,e][T,t][A,a]", "&lt;meta")
			   .replaceAll("<+/+[M,m][E,e][T,t][A,a]", "&lt;/meta")
			   .replaceAll("&#x09;", "")
			   .replaceAll("&#x0A;", "")
			   .replaceAll("&#x0D;", "")
			   .replaceAll("&#x6A&#x61&#x76&#x61&#x73&#x63&#x72&#x69&#x70&#x74&#x3A&#x61&#x6C&#x65&#x72&#x74&#x28&#x27&#x58&#x53&#x53&#x27&#x29", "")
			   .replaceAll("&#106;&#97;&#118;&#97;&#115;&#99;&#114;&#105;&#112;&#116;&#58;&#97;&#108;&#101;&#114;&#116;&#40;&#39;&#88;&#83;&#83;&#39;&#41;", "")
			   .replaceAll("&#0000106&#0000097&#0000118&#0000097&#0000115&#0000099&#0000114&#0000105&#0000112&#0000116&#0000058&#0000097&#0000108&#0000101&#0000114&#0000116&#0000040&#0000039&#0000088&#0000083&#0000083&#0000039&#0000041", "")
			   .replaceAll("&#x6A", "")    
			   .replaceAll("&#x61", "")
			   .replaceAll("&#0000106", "")
			   .replaceAll("&#0000097", "")
			   .replaceAll("&#106;", "")
			   .replaceAll("&#97;", "")
			   .replaceAll("[\t]", "")
			   .replaceAll("/\\*.*\\*/", "")
			   .replaceAll("(?i)expression", "e-x-p-r-e-s-s-i-o-n")
			   .replaceAll("(?i)javascript", "j-v-a-s-c-r-i-p-t")
			   .replaceAll("(?i)vbscript", "v-b-s-c-r-i-p-t")
			   .replaceAll("(?i)onabort", "0nabort")
			   .replaceAll("(?i)onactivate", "0nactivate")
			   .replaceAll("(?i)onafter", "0nafter")
			   .replaceAll("(?i)onbefore", "0nbefore")
			   .replaceAll("(?i)onblur", "0nblur")
			   .replaceAll("(?i)onbounce", "0nbounce")
			   .replaceAll("(?i)oncellchange", "0ncellchange")
			   .replaceAll("(?i)onchange", "0nchange")
			   .replaceAll("(?i)onclick", "0nclick")
			   .replaceAll("(?i)oncontextmenu", "0ncontextmenu")
			   .replaceAll("(?i)oncontrolselect", "0ncontrolselect")
			   .replaceAll("(?i)oncopy", "0ncopy")
			   .replaceAll("(?i)oncut", "0ncut")
			   .replaceAll("(?i)ondataavailable", "0ndataavailable")
			   .replaceAll("(?i)ondataset", "0ndataset")
			   .replaceAll("(?i)ondblclick", "0ndblclick")
			   .replaceAll("(?i)ondeactivate", "0ndeactivate")
			   .replaceAll("(?i)ondrag", "0ndrag")
			   .replaceAll("(?i)ondrop", "0ndrop")
			   .replaceAll("(?i)onerror", "0nerror")
			   .replaceAll("(?i)onfilterchange", "0nfilterchange")
			   .replaceAll("(?i)onfinish", "0nfinish")
			   .replaceAll("(?i)onfocus", "0nfocus")
			   .replaceAll("(?i)onhelp", "0nhelp")
			   .replaceAll("(?i)onkeydown", "0nkeydown")
			   .replaceAll("(?i)onkeypress", "0nkeypress")
			   .replaceAll("(?i)onkeyup", "0nkeyup")
			   .replaceAll("(?i)onload", "0nload")
			   .replaceAll("(?i)onlosecapture", "0nlosecapture")
			   .replaceAll("(?i)onmouse", "0nmouse")
			   .replaceAll("(?i)onmove", "0nmove")
			   .replaceAll("(?i)onpaste", "0npaste")
			   .replaceAll("(?i)onpropertychange", "0npropertychange")
			   .replaceAll("(?i)onreadystatechange", "0nreadystatechange")
			   .replaceAll("(?i)onreset", "0nreset")
			   .replaceAll("(?i)onresize", "0nresize")
			   .replaceAll("(?i)onrow", "0nrow")
			   .replaceAll("(?i)onscroll", "0nscroll")
			   .replaceAll("(?i)onselect", "0nselect")
			   .replaceAll("(?i)onselectionchange", "0nselectionchange")
			   .replaceAll("(?i)onselectstart", "0nselectstart")
			   .replaceAll("(?i)onstart", "0nstart")
			   .replaceAll("(?i)onstop", "0nstop")
			   .replaceAll("(?i)onsubmit", "0nsubmit")
			   .replaceAll("(?i)onunload", "0nunload");
		}
		return str;
	}
	
	/**
 	 * 파일경로 관련 행안부 보안취약점 조치.
 	 * @param value 파일경로
 	 * @return 조치된 파일경로
 	 */
 	public static String filePathReplaceAll(String value) {
 		String returnValue = value;
 		if (returnValue == null || returnValue.trim().equals("")) {
 			return "";
 		}

 		//returnValue = returnValue.replaceAll("/", "");
 		returnValue = returnValue.replaceAll("[.][.]/", "");
 		returnValue = returnValue.replaceAll("[.]/", "");
 		returnValue = returnValue.replaceAll("[.]\\\\\\\\", "");
 		returnValue = returnValue.replaceAll("[.][.]\\\\\\\\", "");

 		//네트워크 경로로 인해 주석처리
 		//returnValue = returnValue.replaceAll("\\\\\\\\", ""); 
 		
 		returnValue = returnValue.replaceAll("\\\\[.]\\\\[.]", ""); // ..
 		returnValue = returnValue.replaceAll("&", "");

 		String fileSeperator = File.separator;
 		if( !returnValue.contains( fileSeperator ) ) {
 			returnValue = returnValue.replace( "/", fileSeperator )
 									 .replace( "\\", fileSeperator );
 		}
 		
 		return returnValue;
 	}
 	
 	/**
     * NULL 문자를 대체한다.
     * @param str 원본 스트링
     * @param str2 대체 스트링
     * @return str이 NULL문자의 경우 str2, 아닐경우 str
     */
    public final static String replaceNull(String str, String str2){
        if ( str==null ||"".equals(str)) {
        	return str2;
        }
        return str;
    }
    
    /**
     * NULL 문자를 대체한다.
     * @param obj 원본 객체
     * @return obj가 NULL일 경우 "", 아닐경우 obj
     */
    public final static String replaceNull(Object obj){
    	String str = "";
        if (obj != null) {
        	str = obj.toString();
        }
        return str;
    } 
	
	/**
	 * Request로 부터 IP를 가져온다.
	 * @param request javax.servlet.http.HttpServletRequest
	 * @return IP
	 */
	public static String getIp(HttpServletRequest request){
		
		String ip = null;
		
		if(request != null){
			String[] headerList = { 
			    "X-FORWARDED-FOR",
			    "Proxy-Client-IP",
			    "WL-Proxy-Client-IP",
			    "HTTP_X_FORWARDED_FOR",
			    "HTTP_X_FORWARDED",
			    "HTTP_X_CLUSTER_CLIENT_IP",
			    "HTTP_CLIENT_IP",
			    "HTTP_FORWARDED_FOR",
			    "HTTP_FORWARDED",
			    "HTTP_VIA",
			    "REMOTE_ADDR" 
			};
	
			for (String header : headerList) {
		        ip = request.getHeader(header);
		        if( ip!=null && !"".equals(ip) )	break;
		    }
			if (ip == null || "".equals(ip)){
				return request.getRemoteAddr();
			}	
			
			//
			if(ip.contains(",")){
				ip = ip.split(",")[0].trim();
			}
		}
		return ip;
	}
	
	/**
	 * 숫자타입 여부를 판단한다.
	 * @param str 판단할 스트링
	 * @return 숫자타입 : true
	 */
	public static boolean isNumber(String str){
	    if(str.equals("")) return false;
        char[] ch = str.toCharArray();
        int len = ch.length;	    
	    for(int i = 0 ; i < len ; i++){
	        if(ch[i] < 48 || ch[i] > 59){
	            return false;
	        }
	    }
	    return true;
	}
	
	/**
	 * <pre>
	 * 요청 브라우저가 PC인지 체크
	 * request.setAttribute( "isPcBrowser", [true/false] );
	 * </pre>
	 * @param request javax.servlet.http.HttpServletRequest
	 * @return PC 브라우저 : true, ETC : false
	 */
	public static boolean isPcBrowser(HttpServletRequest request) {
		String headerStr = replaceNull( request.getHeader( "User-Agent" ) );
		boolean isPcBrowser = ( !"".equals( headerStr ) && !headerStr.toUpperCase().contains( "MOBILE" ) );
		request.setAttribute( "isPcBrowser", isPcBrowser );
		
		return isPcBrowser;
	}
	
	/**
     * 전달된 파라미터에 맞게 난수를 생성한다
     * @param len : 생성할 난수의 길이
     * @param dupCd : 중복 허용 여부 (1: 중복허용, 2:중복제거)
     */
    public static String numberGen(int len, int dupCd ) {
        
		try {
			Random rand = SecureRandom.getInstance("SHA1PRNG");
			String numStr = ""; //난수가 저장될 변수
			
			for(int i=0;i<len;i++) {
				
				//0~9 까지 난수 생성
				String ran = Integer.toString(rand.nextInt(10));
				
				if(dupCd==1) {
					//중복 허용시 numStr에 append
					numStr += ran;
				}else if(dupCd==2) {
					//중복을 허용하지 않을시 중복된 값이 있는지 검사한다
					if(!numStr.contains(ran)) {
						//중복된 값이 없으면 numStr에 append
						numStr += ran;
					}else {
						//생성된 난수가 중복되면 루틴을 다시 실행한다
						i-=1;
					}
				}
			}
			return numStr;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

    }
    
    /**
	 * submit방식이 ajax인지 확인
	 * @param request
	 * @return ajax submit : true, form submit : false
	 */
	public static boolean isAjaxSubmit(HttpServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase( request.getHeader( "x-requested-with" ) );
	}
	
	/**
	 * <pre>
	 * 파일크기 단위 변경
	 * 크기에 따라 자동 변경됨
	 * </pre>
	 * @param fileSize
	 * @return
	 */
	public static String replaceFileSize(Integer fileSize) {
		String bytes = String.valueOf(fileSize);
		String retFormat = "0";
		Double size = Double.parseDouble(bytes);

		String[] s = { "bytes", "KB", "MB", "GB", "TB", "PB" };

		if (bytes != "0") {
			int idx = (int) Math.floor(Math.log(size) / Math.log(1024));
			DecimalFormat df = new DecimalFormat("#,###.##");
			double ret = ((size / Math.pow(1024, Math.floor(idx))));
			retFormat = df.format(ret) + " " + s[idx];
		} else {
			retFormat += " " + s[0];
		}

		return retFormat;
	}
	
	/**
	 * 이메일 정규식
	 */
	public static boolean checkEmail(String email){
		if(StringUtils.isEmpty(email)){
			return false;
		}
		//String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; 
		String regex = "^[_a-zA-Z0-9._%+-]+@[_a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(email);
		if(!m.matches()) {
			return false;
		}
		
		return true;
	}

	/**
	 * 날짜 포맷 유틸
	 */
	public static String getDateToStr(Date dt,String fm){
		SimpleDateFormat sdf = new SimpleDateFormat(fm, Locale.KOREA);
		String r = sdf.format(dt);
		return r;
	}
	
	public static int nvlInt(Object obj){
		try {
			int result = Integer.parseInt(obj.toString());
			return result;
		} catch (NullPointerException | NumberFormatException e) {
			return 0;
		}
	}
	
	public static String getEncode(String target){
		//try {
			return target;//new String(target.getBytes("8859_1"),"utf-8");
		//} catch (NullPointerException | UnsupportedEncodingException e) {
		//	return null;
		//}
	}
	public static String getExcelToString(Cell cell){
		String result = "";
		try {
			switch (cell.getCellType()) {
            case NUMERIC:
                // 숫자 셀을 텍스트로 변환
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    result = cell.getDateCellValue().toString();  // 날짜 형식일 경우
                } else {
                    result = NumberToTextConverter.toText(cell.getNumericCellValue());  // 숫자 셀 텍스트 변환
                }
                break;
            case STRING:
                result = cell.getStringCellValue();  // 텍스트 셀의 값
                break;
            case BOOLEAN:
                result = Boolean.toString(cell.getBooleanCellValue());  // 불리언 셀의 값
                break;
            case FORMULA:
                result = cell.getCellFormula();  // 수식 셀의 값
                break;
            default:
                result = "ERROR";  // 그 외의 타입 처리
                break;
        }
		} catch (NullPointerException e) {
			result = "ERROR";
		}
		return result;
	}
	
	public static String getReadFile(String path) {
		StringBuffer sb = new StringBuffer();
		String filePath = path;
		FileInputStream fileStream=null;
		try {
			fileStream = new FileInputStream(filePath);
			byte[] readBuffer = new byte[fileStream.available()];
			while(fileStream.read(readBuffer)>0){
				sb.append(new String(readBuffer));
			}
			return sb.toString();
		} catch (NullPointerException | IOException e) {
			return "ERROR";
		}finally{
			try {
				fileStream.close();
			} catch (IOException e2) {
				return "ERROR";
			}
		}
	}
	public String nvl(Object val){
		if ( val == null ){
			return "";
		}
        return  val.toString();
	}

	public static String getServerIp() {
		String result = null;
		try {
			result = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			result = "";
		}
		return result;
	}
	
	public static void alertBack(HttpServletResponse response, String msg) throws Exception {
		response.setContentType("text/html;charset=utf-8"); // 어떤 타입으로 출력할것인지 명시하였다.

		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		out.println("<html lang=\"ko\">");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>title</title>");
		
		out.println("<script type=\"text/javascript\">");
		out.println("alert('" + msg + "');");
		out.println("history.back();");
		out.println("</script>");
		
		out.println("</head>");
		out.println("<body>");
		out.println("</body>");
		out.println("</html>");
		
		out.flush();
        out.close();
	}
	public static void alertBack(HttpServletResponse response, String msg,String url) throws Exception {
		response.setContentType("text/html;charset=utf-8"); // 어떤 타입으로 출력할것인지 명시하였다.
		
		PrintWriter out = response.getWriter();
		String ctxPath = ConfigHandle.getProperties("lnc.app.contextpath");
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		out.println("<html lang=\"ko\">");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>title</title>");
		
		out.println("<script type=\"text/javascript\">");
		out.println("alert('" + msg + "');");
		out.println("location.href='"+ctxPath+url+"';");
		out.println("</script>");
		
		out.println("</head>");
		out.println("<body>");
		out.println("</body>");
		out.println("</html>");
		
		out.flush();
		out.close();
	}
	public static void alertClose(HttpServletResponse response, String msg) throws Exception {
		response.setContentType("text/html;charset=utf-8"); // 어떤 타입으로 출력할것인지 명시하였다.
		
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		out.println("<html lang=\"ko\">");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>title</title>");
		
		out.println("<script type=\"text/javascript\">");
		out.println("alert('" + msg + "');");
		out.println("window.close();");
		out.println("</script>");
		
		out.println("</head>");
		out.println("<body>");
		out.println("</body>");
		out.println("</html>");
		
		out.flush();
		out.close();
	}
	
    
    public static String escapeQuotes(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '\'') {
                sb.append("\\'");
            } else if (c == '\"') {
                sb.append("\\\"");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    /**
     * html > 원복
     * @param escapedJson
     * @return
     */
    public static String unescapeJson(String escapedJson) {
        String unescapedJson = StringEscapeUtils.unescapeHtml4(escapedJson);
        return unescapedJson;
    }
    
    /**
	 * 달력화면 반복을 위한 맵을 반환한다.
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static Map<Integer, String> getCalMap(String startDay, String endDay){
		
		if(startDay == null || endDay == null){
			return null;
		}
		
		Map<Integer, String> result = new HashMap<Integer, String>();
		
		int calDays = 0;
		
		int sy = Integer.parseInt(startDay.substring(0, 4));
		int sm = Integer.parseInt(startDay.substring(4, 6));
		int sd = Integer.parseInt(startDay.substring(6, 8));
		
		calDays = calDays(startDay, endDay);
		
		for(int i = 0; i <= calDays; i++){
			result.put(i, DateUtil.dayCalForStr(sy, sm, sd, i, "", "yyyyMMdd"));
		}
		
		return result;
	}
	public static int calDays(String startDay, String endDay){
		
		int sy = Integer.parseInt(startDay.substring(0, 4));
		int sm = Integer.parseInt(startDay.substring(4, 6));
		int sd = Integer.parseInt(startDay.substring(6, 8));
		
		int ey = Integer.parseInt(endDay.substring(0, 4));
		int em = Integer.parseInt(endDay.substring(4, 6));
		int ed = Integer.parseInt(endDay.substring(6, 8));
		
		Calendar sCal = Calendar.getInstance();
		Calendar eCal = Calendar.getInstance();
		
		sCal.set(sy, sm - 1, sd);
		eCal.set(ey, em - 1, ed);
		
		long diffSec = (eCal.getTimeInMillis() - sCal.getTimeInMillis()) / 1000; 
		long diffDay = diffSec/(60 * 60 * 24);
		
		return (int) diffDay;
	}
	
	public static ResultVO callHttpApi(String url,String datas){
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			OkHttpClient client = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
			RequestBody body = RequestBody.create(mediaType, datas);
			Request request = new Request.Builder()
			.url(url)
			.post(body)
			.addHeader("Content-Type", "application/json")
			//.addHeader("Authorization", "Basic "+Base64.encode((SMS_ID+":"+ACCESS_TOKEN).getBytes()))
			.addHeader("cache-control", "no-cache")
			.build();
			Response response = client.newCall(request).execute();
			String resultBody = response.body().string();
			rstData.put("result", resultBody);
			result.setResult(1);
			return result;
		} catch (NullPointerException | IOException e) {
			result.setResult(0);
			return result;
		}
	}
	public static String getDevice(HttpServletRequest req) {
		
	    String userAgent = req.getHeader("User-Agent").toUpperCase();
	    String device = "E";
	    
	    if( userAgent.indexOf( "MOBILE" ) > -1 || userAgent.indexOf( "IPHONE" ) > -1 ) {
	        device = "M";
	    } else {
	    	if( userAgent.indexOf( "IPAD" ) > -1 || ( userAgent.indexOf( "ANDROID" ) > -1 && userAgent.indexOf( "MOBILE" ) < 0 ) ) {
	    		device = "T";
	    	} else {
	    		device = "P";
	    	}
	    }
	    
	    return device;
	}
	public static boolean hasDuplicateValue(List<Map<String, Object>> rceptList,String key) {
		Set<Object> userIdSet = new HashSet<>();
		for (Map<String, Object> map : rceptList) {
			Object userId = map.get(key);
	     	if(ObjectUtils.isEmpty(userId)){//공백 패스
	     		continue;
	     	}
			
			if ( userIdSet.contains(userId)) {
	       		return true; // Found a duplicate userId
	    	}
	     	userIdSet.add(userId);
		}
		return false; // No duplicate userId found
	}
	
	public static boolean isHTML(String input) {
	    // 정규 표현식을 사용하여 HTML 태그 여부 확인
	    String htmlPattern = "<[^>]*>";
	    Pattern pattern = Pattern.compile(htmlPattern);
	    Matcher matcher = pattern.matcher(input);

	    return matcher.find();
	}
	
	// 백분율을 계산하는 함수
    public static String calculatePercentage(int numerator, int denominator, int decimalPlaces) {
        // 분모가 0인 경우 0으로 나누는 것이 불가능하므로 예외 처리
        if (denominator == 0) {
            //throw new ArithmeticException("분모는 0이 될 수 없습니다.");
        	return "0%";
        }

        // 백분율 계산
        double percentage = ((double) numerator / denominator) * 100;

        // 결과를 소수점 아래 자릿수만큼 버림하여 문자열로 반환
        String result = String.format("%." + decimalPlaces + "f",percentage);

        // '%'를 추가
        return result + "%";
    }
    
    public static String filterFileNm(String input) {
        // 정규식 패턴: 한글, 숫자, 영문자만을 포함하는 문자열을 찾음
        String regex = "[^가-힣0-9a-zA-Z]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 찾은 패턴을 제거하여 리턴
        return matcher.replaceAll("");
    }
    
	public static int checkEmptyStrs(String... args) {
		try {
			for(String o : args){
				if(!StringUtils.hasText(o)){
					return 0;
				}
			}
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	/**
	 * 랜덤 정렬 배열 리턴
	 * @param size
	 * @return
	 */
	public static int[] getRandomNumList(int size) {
        // 1부터 size까지의 숫자를 리스트에 추가
        List<Integer> numList = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            numList.add(i);
        }
        
        // 리스트를 랜덤하게 섞음
        Collections.shuffle(numList);
        
        // 리스트를 배열로 변환
        int[] randomArray = new int[size];
        for (int i = 0; i < size; i++) {
            randomArray[i] = numList.get(i);
        }
        
        return randomArray;
    }

	public static int[] generateRandomArray(int n) {
		if(n < 0){//시큐어코딩 때문에...
			return null;
		}
		int[] array = new int[n];
        
        // 1부터 n까지 배열 채우기
        for (int i = 0; i < n; i++) {
            array[i] = i + 1;
        }
        
        // 배열 섞기 (Fisher-Yates Shuffle 알고리즘 사용)
        Random random = new Random();
        for (int i = n - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Swap array[i] with array[index]
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
        
        return array;
    }
	
	public static String formatPhoneNumber(String phoneNumber) {
        // 이미 올바른 형식인 경우
        if (phoneNumber.matches("\\d{3}-\\d{3,4}-\\d{4}") || phoneNumber.matches("\\d{4}-\\d{3,4}-\\d{4}")) {
            return phoneNumber;
        }

        // 숫자로만 이루어진 경우
        if (phoneNumber.matches("\\d{10}")) {
            // 예: 000-000-0000
            return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
        } else if (phoneNumber.matches("\\d{11}")) {
            // 예: 000-0000-0000
            return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 7) + "-" + phoneNumber.substring(7);
        } else if (phoneNumber.matches("\\d{12}")) {
            // 예: 0000-0000-0000
            return phoneNumber.substring(0, 4) + "-" + phoneNumber.substring(4, 8) + "-" + phoneNumber.substring(8);
        }

        // 유효하지 않은 경우
        return "Invalid phone number format";
    }

	public static boolean isNumeric(String str) {
		if (str == null || str.isEmpty()) {
			return false; // null 또는 빈 문자열은 숫자가 아님
		}
		return str.matches("-?\\d+(\\.\\d+)?");
		// 정수와 소수점 포함 숫자 확인 (e.g., -123, 456, 78.9)
	}
	
	public static String getMaskedName(String regNm){
		try {
			
			return regNm.substring(0,1)+"OO";
			
		} catch (NullPointerException e) {
			return "OOO";
		}
	}
    public static String unescapeHtml(String input) {
        if (input == null) return null;

        return input
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&#39;", "'")
                .replace("&#x2F;", "/")
                .replace("&#x5C;", "\\");
    }
    
    public static String generateLoginId(String prefix) {
    	// 1. 현재 시간 (yyyyMMddHHmmss)
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 2. 난수 (6자리 숫자)
        int randNum = 100000 + random.nextInt(900000);

        return prefix + timestamp + randNum;
    }
    /**
     * 아이디 가운데 부분 마스킹
     * @param id 원본 아이디
     * @return 마스킹된 아이디
     */
    public static String maskId(String id) {
        if (id == null || id.length() <= 4) {
            // 길이가 너무 짧으면 그대로 리턴
            return id;
        }

        int prefixLen = 3; // 앞 3글자
        int suffixLen = 1; // 뒤 1글자

        String prefix = id.substring(0, prefixLen);
        String suffix = id.substring(id.length() - suffixLen);
        StringBuilder masked = new StringBuilder();

        // 가운데 부분을 * 로 채움
        for (int i = 0; i < id.length() - (prefixLen + suffixLen); i++) {
            masked.append("*");
        }

        return prefix + masked + suffix;
    }
}

package com.educare.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @Class Name : StringReplace.java
 * @author SI개발팀 박용주
 * @since 2019. 12. 2.
 * @version 1.0
 * @see
 * @Description
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2019. 12. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class StringReplace {
	
private static final Logger LOG = Logger.getLogger(StringReplace.class.getName());
	
	private String sFilename ;
	private String sTarget 	;
	private String sReplacement ;
	
	private String sMessage ;
	
	public StringReplace(){
	}
	
	public StringReplace(String sFilename, String sTarget, String sReplacement){
		this.sFilename = sFilename;
		this.sTarget = sTarget;
		this.sReplacement = sReplacement;
	}
	
	public String getMessage() {
		return sMessage;
	}

	private void setMessage(String message) {
		sMessage = message;
	}

	public void processStringReplace(){
		if(sFilename==null || sFilename.equals("")) {
			setMessage("#1 파일이 선택되지 않았습니다.");
			return;
		}
		if(sTarget==null || sTarget.equals("")){
			setMessage("#2 대상 문자열이 선택되지 않았습니다.");
			return;
		}
		if(sReplacement==null){
			setMessage("#3 치환할 문자가 선택되지 않았습니다.");
			return;
		}
		
		if(this.setStringReplace() == 1){
			setMessage("#4 정상적으로 치환되지 않았습니다.");
			return;
		}else{
			setMessage("#5 정상적으로 처리되었습니다.");
			return;
		}
	}
	
	private int setStringReplace(){
		BufferedReader brReader = null;
		BufferedWriter bwWriter = null;
		int iReturnValue 		= 0;
		
		//*** 시큐어코딩 디렉토리 경로조작 ****************************//
		sFilename= LncUtil.filePathReplaceAll(sFilename);
		//*** 시큐어코딩 디렉토리 경로조작 ****************************//
		
		try{
			brReader 	 = new BufferedReader(new InputStreamReader(new FileInputStream(sFilename),"UTF-8"));
			ArrayList al = new ArrayList();
			
			
			//String sTmp = brReader.readLine();
			//while(sTmp != null) {
			//	al.add(sTmp.replaceAll(sTarget, sReplacement));
			//}
			
			String sTmp;
			while ((sTmp = brReader.readLine()) != null) { // sTmp를 갱신
			    al.add(sTmp.replaceAll(sTarget, sReplacement));
			}
			
			bwWriter 	 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sFilename ),"UTF-8"));
			
			for(int i = 0 ; i < al.size(); i++){
				bwWriter.write((String)al.get(i));
				bwWriter.newLine();
			}
		}catch(IOException e){
			LOG.error("IGNORE:",e);
			iReturnValue = 1;
		}finally{
			if(bwWriter != null)	try {	bwWriter.flush();	} catch (IOException e) {LOG.error("IGNORE:",e);}
			if(bwWriter != null)	try {	bwWriter.close();	} catch (IOException e) {LOG.error("IGNORE:",e);}
			if(brReader != null)	try {	brReader.close();	} catch (IOException e) {LOG.error("IGNORE:",e);}
		}
		
		return iReturnValue;
	}

}

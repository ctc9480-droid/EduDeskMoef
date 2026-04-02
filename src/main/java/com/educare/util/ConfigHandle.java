package com.educare.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @Class Name : ConfigHandle.java
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
 * 2019. 12. 2.	    SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class ConfigHandle {
	
	private static final Logger LOG = Logger.getLogger(ConfigHandle.class.getName());
	
	private static Properties pConfigProperties = null;
	private static String sConfPath = null;
	private static File fConfigFile = null;
	private static long lConfLastLoadTime = 0l;
	
	public ConfigHandle(){
	}
	
	/** 
	 *	lnc.properties 을 메모리에 로딩한다.<br>
	 *  @param
	 *	@return 
	 */
	private static void init(){
		try{
			String sPath = null;
			
			//WEB-INF 위치 
			sPath = ConfigHandle.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			
			URL fileUrl = ConfigHandle.class.getClassLoader().getResource("/egovframework/properties/"+System.getProperty("server.mode")+"/lnc2.properties");
			
			int nFindInx = sPath.indexOf("WEB-INF");
			if( nFindInx > 0 ) sPath = sPath.substring(0, nFindInx + 7);
			sConfPath = sPath + File.separator + "conf" + File.separator + "lnc.properties";
			
			//fConfigFile = new File(sConfPath);
			if(fileUrl != null){
				fConfigFile = new File(fileUrl.getFile());
				loadFile();
			}
		}catch(IndexOutOfBoundsException e){
			LOG.error("IGNORE:",e);
		}	
	}
	
	/**
	 * 
	 * @return if success, return true. else return false
	 */
	private static boolean loadFile(){
		boolean bReturnValue = false;

		// Fail Loading
		if (fConfigFile == null || !fConfigFile.isFile()){
			//System.out.println("ConfHandle loadFile() : cannot load config file");
			return false;
		}
		
		FileInputStream oFileInput=null;

		try{
			oFileInput = new FileInputStream(fConfigFile);
			
			Properties tempProperties = new Properties();
			tempProperties.load(oFileInput);
			// 동기화가 필요한 코드 블록
	        synchronized (ConfigHandle.class) {
	            pConfigProperties = tempProperties;
	            lConfLastLoadTime = fConfigFile.lastModified();
	        }
			
			
			bReturnValue = true;
		}catch(FileNotFoundException e){	
			LOG.error("IGNORE:",e);
		}catch(IOException e){
			LOG.error("IGNORE:",e);
		}finally{
			if (oFileInput!=null) try{oFileInput.close();}catch(IOException e){LOG.error("IGNORE:",e);}
			oFileInput = null;
		}
		return bReturnValue;
	}
	
	public static String getProperties(String sKeyName){
		
		if(initCheck()){
			init();
		}
		
		return pConfigProperties.getProperty(sKeyName);
	}
	
	private static boolean initCheck(){
		
		//if(fConfigFile == null ){
		if(fConfigFile == null || lConfLastLoadTime < fConfigFile.lastModified()){
			return true;
		}else{
			return false;
		}
	}
	
	
	//메모리상 처리
	public static void setProperties(String sKey, String sValue){
		pConfigProperties.setProperty(sKey, sValue);
	}
	
	//파일 내부 처리
	public static String setReplaceConfFile(String sKey, String sValue){
		
		String sTarget 		= sKey+"="+pConfigProperties.getProperty(sKey);
		String sReplacement = sKey+"="+sValue;
		
		StringReplace sr = new StringReplace(sConfPath, sTarget, sReplacement);
		sr.processStringReplace();
		
		return sr.getMessage();
	}
}

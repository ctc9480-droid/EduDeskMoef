package com.educare.util;

/**
 * @Class Name : XmlBean.java
 * @author SI개발팀 박용주
 * @since 2019. 12. 2.
 * @version 1.0
 * @see
 * @Description lnc.properties
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2019. 12. 2.	    SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class XmlBean {
	
	public static String getConfigServerType() {
		return getConfigValue("lnc.config.serverType");
	}
	public static String getConfigOs() {
		return getConfigValue("lnc.config.os");
	}
	public static String getConfigDatabase() {
		return getConfigValue("lnc.config.database");
	}
	public static String getConfigDatabaseVersion() {
		return getConfigValue("lnc.config.database.version");
	}
	public static String getConfigDatabaseCharset() {
		return getConfigValue("lnc.config.database.charset");
	}
	public static String getConfigWas() {
		return getConfigValue("lnc.config.was");
	}
	public static String getConfigWasVersion() {
		return getConfigValue("lnc.config.was.version");
	}
	public static String getConfigLanguage() {
		return getConfigValue("lnc.config.lang");
	}
	public static String getConfigDebug() {
		return getConfigValue("lnc.config.debug");
	}
	
	public static String getConfigHanEncoding() {
		return getConfigValue("lnc.config.han.encoding");
	}
	public static String getConfigProjectName() {
		return getConfigValue("lnc.config.projectname");
	}
	public static String getConfigCookieDomain() {
		return getConfigValue("lnc.config.cookie.domain");
	}

	
	public static String getServerUrl() {
		return getConfigValue("lnc.server.url");
	}

	public static String getServerPort() {
		return getConfigValue("lnc.server.port");
	}
	
	public static String getServerDomain() {
		return getConfigValue("lnc.server.domain");
	}
	//URL / FILE PATH  대체 메소드
	public static String getServerSubpath() {
		return getConfigValue("lnc.server.subpath");
	}
	
	public static String getServerContextRoot() {
		return getConfigValue("lnc.server.contextroot");
	}
	public static String getServerDataRoot() {
		return getConfigValue("lnc.server.dataroot");
	}
	public static String getRootPathWEBINF(){
		return getConfigValue("lnc.rootpath.webinf");
	}
	public static String getRootPathAdmin(){
		return getConfigValue("lnc.rootpath.admin");
	}
	public static String getRootPathUser(){
		return getConfigValue("lnc.rootpath.user");
	}
	
	public static String getConfigValue(String sKeyName){
		return ConfigHandle.getProperties(sKeyName);
	}
	
	public static String setConfigValue(String sKey, String sValue){

		return ConfigHandle.setReplaceConfFile(sKey, sValue);
	}

	//*** 파일 업로드 불가 확장자 *******************************//
	public static String getFileUploadDisabledExtension(){
		return getConfigValue("lnc.file.upload.disabled.extension");
	}
	
	/**
	 * 게시판 업로드 디폴트 파일타입
	 * @return
	 */
	public static String getBoardUploadDefaultFileType(){
		return getConfigValue("lnc.board.upload.default.fileType");
	}
	public static String getAlbumUploadDefaultFileType(){
		return getConfigValue("lnc.album.upload.default.fileType");
	}
	public static String getMovieUploadDefaultFileType(){
		return getConfigValue("lnc.movie.upload.default.fileType");
	}
	public static String getImageUploadDefaultFileSize(){
		return getConfigValue("lnc.image.upload.default.fileSize");
	}
	
	public static String getJWizardSourceType(){
		return getConfigValue("lnc.source.type");
	}
	
	public static String getDiscountServerIp(){
		return getConfigValue("discount.server.ip");
	}
	public static String getDiscountServerPort(){
		return getConfigValue("discount.server.port");
	}
	
	public static String getSmartcheckUrl(){
		return getConfigValue("lnc.smartcheck.url");
	}
	public static String getServiceName(){
		return getConfigValue("service.name");
	}
}

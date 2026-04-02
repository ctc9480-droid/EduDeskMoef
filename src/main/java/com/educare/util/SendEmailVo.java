package com.educare.util;

/**
 * @Class Name : SendEmailVo.java
 * @author SI개발팀 박용주
 * @since 2020. 1. 22.
 * @version 1.0
 * @see
 * @Description 이메일 발송 관련 Vo Class
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 1. 22.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class SendEmailVo {
	
	/** 발신자 메일주소 */
	private String	fromEmail;
	/** 발신자 이름 */
	private String	fromName;
	/** 수신자 메일주소 */
	private String	toEmail;
	/** 메일제목 */
	private String	subject;
	/** 메일내용 */
	private String	message;
	
	/**
	 * 발신자 메일주소를 가져온다.
	 * @return 발신자 메일주소
	 */
	public String getFromEmail() {
		return fromEmail;
	}
	/**
	 * 발신자 메일주소를 설정한다.
	 * @param fromEmail 발신자 메일주소
	 */
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	/**
	 * 발신자 이름을 가져온다.
	 * @return 발신자 이름
	 */
	public String getFromName() {
		return fromName;
	}
	/**
	 * 발신자 이름을 설정한다.
	 * @param fromName 발신자 이름
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	/**
	 * 수신자 메일주소를 가져온다.
	 * @return 수신자 메일주소
	 */
	public String getToEmail() {
		return toEmail;
	}
	/**
	 * 수신자 메일주소를 설정한다.
	 * @param toEmail 수신자 메일주소
	 */
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	/**
	 * 메일제목을 가져온다.
	 * @return 메일제목
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 메일제목을 설정한다.
	 * @param subject 메일제목
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * 메일내용을 가져온다.
	 * @return 메일내용
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 메일내용을 설정한다.
	 * @param message 메일내용
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	/**
	 * 메일의 폰트 가져온다
	 * @param lang 사이트 언어
	 * @return font-family
	 */
	public String getFontFamily(String lang) {
		String font = null;
		if(lang.equals("ja")) {
			font = "font-family: verdana, 'ヒラギノ丸ゴ ProN W4', 'Hiragino Maru Gothic ProN', 'メイリオ', 'Meiryo', 'ＭＳ Ｐゴシック', 'MS PGothic', Sans-Serif !important;";
		}else {
			font = "font-family: arial,'Nanum Gothic',nanumgothic,tahoma,sans-serif";
		}
		
		return font;
	}
	
}

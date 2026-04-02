package com.educare.edu.bbs;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.educare.util.XmlBean;

public class BbsConstant {
	final static public String MSG_ERR = 			"알 수 없는 에러가 발생하였습니다. 관리자에게 문의하세요";
	final static public String MSG_SUC = 			"정상적으로 처리되었습니다.";
	final static public String MSG_ERR_REG = 		"글 작성중 에러가 발생하였습니다.";
	final static public String MSG_ERR_VIEW = 		"게시글이 존재하지 않습니다.";
	final static public String MSG_ERR_REG_FILE = 	"파일첨부 중 에러가 발생하였습니다.";
	final static public String MSG_ERR_DEL_FILE = 	"파일삭제 중 에러가 발생하였습니다.";
	final static public String MSG_SUC_REG = 		"정상적으로 저장되었습니다.";
	final public static String MSG_ERR_WRONG_PATH=	"잘못 된 주소입니다.";
	final public static String MSG_ERR_REG_SELF=	"본인이 작성한 글이 아닙니다.";
	final public static String MSG_ERR_PW=			"비밀번호가 일치하지 않습니다.";
	final public static String MSG_ERR_INQRY_DEL=	"답변완료는 삭제 할 수 없습니다.";
	final public static String MSG_ERR_LOGIN=		"로그인을 하셔야 합니다.";
	
	final static public String[] MSG_RESULT_CODE={
		/*0*/MSG_ERR,			
		/*1*/MSG_SUC,			
		/*2*/MSG_SUC_REG,
		/*3*/MSG_ERR_VIEW,
		/*4*/MSG_ERR_REG_FILE,
		/*5*/MSG_ERR_DEL_FILE,
		/*6*/MSG_ERR_REG_SELF,
		/*7*/MSG_ERR_PW,
		/*8*/MSG_ERR_INQRY_DEL
		
	};
	
	final public static String URL_ADMIN_MAIN="/admin/main/index.do";
	//final public static String PATH_ATTACH = XmlBean.getServerContextRoot() + "/upload/board/attach/";
	final public static String PATH_ATTACH = "upload/board/";
	
	/**
	 * <pre>
	 * 게시판 존재 여부 체크
	 * 공지사항 : bbs/notice
	 * 자유 : bbs/free
	 * 자료실 : bbs/recs
	 * 교육자료실 : recs/eduRecs
	 * </pre>
	 * @param boardType
	 * @return
	 */
	public static boolean isAdminBoard(String boardType){
		if( "notice,free,recs,faq,etc1,meal,hope,edyear".contains(boardType) ){
			return true;
		}
		return false;
	}
	
	/**
	 * <pre>
	 * 사용자 게시판 주소,존재 여부 체크
	 * 공지사항 : cs/notice
	 * 자유 : cmmnty/free
	 * 자료실 : cmmnty/recs
	 * </pre>
	 * @param boardType
	 * @return
	 */
	public static boolean isUserBoard(String boardType,String parent){
		if("notice,free,recs,faq,etc1,meal,hope,edyear".contains(boardType)&&boardType.length()>0 && parent.equals("bbs")){
			return true;
		}
		return false;
	}
	
	/**
	 * 이미지파일 > 바이트 변환
	 * @param mf
	 * @param width
	 * @param height
	 * @return
	 */
	public static byte[] getByteImageResizeByUpload(MultipartFile mf, int width, int height) {
		InputStream in = null;
		ByteArrayOutputStream baos = null;
		byte[] imageInByte;
		try {
			//리사이즈 하자
			in = mf.getInputStream();
			BufferedImage bufImg = ImageIO.read(in);
			
			/*
			//크롭
			int width1 = Math.min(bImageFromConvert.getHeight(),  bImageFromConvert.getWidth());
			int height1 = width1;
			int width2 = (bImageFromConvert.getWidth() - width1)/2;
			int height2 = (bImageFromConvert.getHeight() - height1)/2;
			bufImg = Scalr.crop(bImageFromConvert
					, width2, height2
					, width1, height1
					, null);
			
			//리사이징
			if(height==0){
				float w=scaledImage.getWidth();
				float h=scaledImage.getHeight();
				float w2=width;
				float h2=0;
				h2=(w2/w)*h;
				height=(int) h2;
			}
			
			bufImg = Scalr.resize(scaledImage,
					Scalr.Method.SPEED,
					Scalr.Mode.FIT_EXACT,
					width,
					height,
					Scalr.OP_ANTIALIAS);
			*/
			
			//바이트로 바꾸자
			baos = new ByteArrayOutputStream();
			ImageIO.write( bufImg, "jpg", baos );
			baos.flush();
			imageInByte = baos.toByteArray();
		} catch (IOException e) {
			imageInByte=null;
		}finally{
			try {
				if(baos!=null)baos.close();
				if(in!=null)in.close();
			} catch (IOException e) {
				imageInByte=null;
			}
		}
	     
		return imageInByte;
	}
}

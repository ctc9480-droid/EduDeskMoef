package com.educare.edu.bbs.service.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.imageio.ImageIO;

import com.educare.util.DateUtil;

/**
 * @Class Name : Popup.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 Model
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
public class Popup {
	//테이블컬럼
	private int idx;
	private String title;
	private String content;
	private int status=1;
	public byte[] img;
	private String startDtime;
	private String endDtime;
	private String regId;
	private String regNm;
	private String regDtime;
	private Integer height=0;
	private Integer width=0;
	private Integer posX=0;
	private Integer posY=0;
	
	//추가 컬럼
	private int rNum;
	private String startDt;
	private String endDt;
	private String startHh;
	private String endHh;
	public Integer[] idxs={};
	
	//검색컬럼
	private int pageNo=1;
	private int firstRecordIndex=0;
	private int recordCountPerPage=10;
	private String searchSelect;
	private String searchWord;
	private int searchStatus;
	private String searchNowDtime;
	
	//성공여부컬럼
	private int result=1;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getTitle() {
		if(title!=null){
			if(title.equals("")){
				return null;
			}
			return title;
		}
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public String getContent2() {
		if(content!=null){
			return content.replaceAll("(\r\n|\r|\n|\n\r)","<br/>");
		}else{
			return "ERROR";
		}
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStartDtime() {
		if(startDtime==null&&getStartDt()!=null&&getStartHh()!=null){
			return getStartDt().replaceAll("-", "")+getStartHh()+"0000000";
		}
		return startDtime;
	}
	public String replaceStartDtime(String pattern) {
		String result=startDtime;
		try {
			if(startDtime.length()==17){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS",Locale.KOREA);
				result=DateUtil.getDate2Str(sdf.parse(startDtime),pattern);
			}
		} catch (NullPointerException | ParseException e) {
			return result;
		}
		return result;
	}
	public void setStartDtime(String startDtime) {
		this.startDtime = startDtime;
	}
	public String getEndDtime() {
		if(endDtime==null&&getEndDt()!=null&&getEndHh()!=null){
			return getEndDt().replaceAll("-", "")+getEndHh()+"0000000";
		}
		return endDtime;
	}
	public String replaceEndDtime(String pattern) {
		String result=endDtime;
		try {
			if(endDtime.length()==17){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS",Locale.KOREA);
				result=DateUtil.getDate2Str(sdf.parse(endDtime),pattern);
			}
		} catch (NullPointerException | ParseException e) {
			return result;
		}
		return result;
	}
	public void setEndDtime(String endDtime) {
		this.endDtime = endDtime;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getrNum() {
		return rNum;
	}
	public void setrNum(int rNum) {
		this.rNum = rNum;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getFirstRecordIndex() {
		return firstRecordIndex;
	}
	public void setFirstRecordIndex(int firstRecordIndex) {
		this.firstRecordIndex = firstRecordIndex;
	}
	public String getSearchSelect() {
		return searchSelect;
	}
	public void setSearchSelect(String searchSelect) {
		this.searchSelect = searchSelect;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDtime() {
		if(regDtime==null){
			return DateUtil.getDate2Str(Calendar.getInstance().getTime(),"yyyyMMddHHmmssSSS");
		}
		return regDtime;
	}
	public void setRegDtime(String regDtime) {
		this.regDtime = regDtime;
	}
	public String replaceRegDtime(String pattern) {
		String result=regDtime;
		try {
			if(regDtime.length()==17){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS",Locale.KOREA);
				result=DateUtil.getDate2Str(sdf.parse(regDtime),pattern);
			}
		} catch (NullPointerException | ParseException e) {
			return result;
		}
		return result;
	}
	public byte[] getImg() {
		return img;
	}
	public String getImg2() {
		String result="";
		ByteArrayOutputStream baos=null;
		InputStream in=null;
		try {
			if(img == null) {
				return "";
			}
			in =new ByteArrayInputStream(img);
			BufferedImage bimg = ImageIO.read(in);
			in.close();
			baos = new ByteArrayOutputStream();
			ImageIO.write( bimg, "png", baos );
			baos.flush();
			byte[] imageInByteArray = baos.toByteArray();
			baos.close();
			return javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
		} catch (NullPointerException| IOException| IllegalStateException|IllegalArgumentException e) {
			result="";
		}finally{
			try {
				if(baos!=null)baos.close();
				if(in!=null)in.close();
			} catch (IOException e2) {
				result="";
			}
		}
		return result;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getPosX() {
		return posX;
	}
	public void setPosX(Integer posX) {
		this.posX = posX;
	}
	public Integer getPosY() {
		return posY;
	}
	public void setPosY(Integer posY) {
		this.posY = posY;
	}
	public String getStartDt() {
		if(startDtime!=null){
			return startDtime.substring(0,4)+"-"+startDtime.substring(4,6)+"-"+startDtime.substring(6,8);
		}
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		if(endDtime!=null){
			return endDtime.substring(0,4)+"-"+endDtime.substring(4,6)+"-"+endDtime.substring(6,8);
		}
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getStartHh() {
		if(startDtime!=null){
			return startDtime.substring(8,10);
		}
		return startHh;
	}
	public void setStartHh(String startHh) {
		this.startHh = startHh;
	}
	public String getEndHh() {
		if(endDtime!=null){
			return endDtime.substring(8,10);
		}
		return endHh;
	}
	public void setEndHh(String endHh) {
		this.endHh = endHh;
	}
	public int getSearchStatus() {
		return searchStatus;
	}
	public void setSearchStatus(int searchStatus) {
		this.searchStatus = searchStatus;
	}
	public Integer[] getIdxs() {
		return idxs;
	}
	public void setIdxs(Integer[] idxs) {
		this.idxs = idxs;
	}
	public String getSearchNowDtime() {
		return searchNowDtime;
	}
	public void setSearchNowDtime(String searchNowDtime) {
		this.searchNowDtime = searchNowDtime;
	}
}

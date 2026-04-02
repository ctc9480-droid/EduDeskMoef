package com.educare.edu.bbs.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.edu.bbs.BbsConstant;
import com.educare.edu.bbs.service.BoardAttachService;
import com.educare.edu.bbs.service.PopupService;
import com.educare.edu.bbs.service.model.BoardAttach;
import com.educare.edu.bbs.service.model.Popup;
import com.educare.edu.tmpFile.service.TempFileService;
import com.educare.util.ConfigHandle;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.google.gson.JsonObject;

/**
 * @Class Name : BbsCommController.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 공통 컨트롤러
 * 
 * <pre>
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성 
 * </pre>
 */
@Controller
@RequestMapping(value="/bbs/comm/")
public class BbsCommController {
	final HttpHeaders HEADERS = new HttpHeaders();
	@Resource(name = "PopupService")
	private PopupService popupService;
	@Resource(name = "BoardAttachService")
	private BoardAttachService boardAttachService;
	
	/**
	 * 팝업 프리뷰 
	 * jsp파일 공통 경로로 바꿔야함
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("popup/open.do")
	public String popupPreview(
			ModelMap model,@ModelAttribute Popup vo) {
		Popup map = popupService.getPopupMap(vo);
		model.addAttribute("popupMap",map);
        return "user/bbs/popupOpen";
	}

	@RequestMapping("popup/list.json")
	public String popupList2Json(
			ModelMap model) {
		Popup vo = new Popup();
		vo.setSearchNowDtime(DateUtil.getDate2Str(Calendar.getInstance().getTime(), "yyyyMMddHHmmssSSS"));
		vo.setSearchStatus(1);
		
		List<Popup> popupList = popupService.getPopupList(vo);
		
		model.addAttribute("result",popupList);
		return "jsonView";
	}
	
	/**
	 * 첨부파일 사이즈 
	 * @param model
	 * @param fileSeq
	 */
	@RequestMapping("attach/size.json")
	public String size(
			ModelMap model,
			Integer fileSeq) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuccess = false;
		int fileSize = 0;
		
		BoardAttach attach = boardAttachService.getBoardAttachMap(fileSeq);
		if(attach != null){
			isSuccess = true;
			fileSize = attach.getFileSize();
		}
		
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("fileSize", fileSize);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
	}
	@RequestMapping("download/{fileSeq}.do")
	public void download(
			@PathVariable( "fileSeq" ) Integer fileSeq,
			HttpServletRequest request,
			HttpServletResponse response
			) {
		
		BoardAttach attach = boardAttachService.getBoardAttachMap(fileSeq);
		
		String tmpPath = TempFileService.TEMP_DOWNLOAD_PATH;//다운로드에 사용할 임시 폴더
		String subPath = BbsConstant.PATH_ATTACH;//스토리지에 경로
		String fileSave = attach.getFileRename();//저장파일이름
		String fileOrg = attach.getFileOrg();//원본파일이름
		//String filePath = tmpPath+BbsConstant.PATH_ATTACH+fileSave;//로컬 파일 경로
		
		//NaverObjectStorage.download(subPath,fileSave );
		//FileUtil.download(filePath, fileOrg, request, response);
		//FileUtil.delete(filePath);
		
		String filePath = "/"+subPath+fileSave;//로컬 파일 경로
		FileUtil.download(filePath, fileOrg, request, response);
	}
	
	@RequestMapping(value = "image/{idx}.do")
	public void func01getajax2(HttpServletRequest req,HttpServletResponse res, Model model,@ModelAttribute Popup vo
			) throws Exception {

    	InputStream in = null;
		ByteArrayOutputStream baos = null;
		byte[] imageInByte=null;
    	try {
    		Popup popupMap = popupService.getPopupMap(vo);
    		String contentType = "image/png";
    		res.setContentType(contentType);  // Content Type Set
			
			in = new ByteArrayInputStream(popupMap.getImg());
					
			BufferedImage bImageFromConvert = ImageIO.read(in);
			
			//바이트로 바꾸자
			baos = new ByteArrayOutputStream();
			ImageIO.write( bImageFromConvert, "png", baos );
			baos.flush();
			
	    	imageInByte = baos.toByteArray();
		} catch (NullPointerException e) {
			imageInByte = javax.xml.bind.DatatypeConverter.parseBase64Binary(BLANK_IMG);
		}finally{
			if(baos!=null)baos.close();
			if(in!=null)in.close();
		}
    	
    	
	    res.getOutputStream().write(imageInByte);
    }
	
	private static final String BLANK_IMG="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";
}

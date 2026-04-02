package com.educare.edu.tmpFile.web;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.tmpFile.service.TempFileService;
import com.educare.edu.tmpFile.service.model.TempFile;
import com.google.gson.JsonObject;

/**
 * @Class Name : TempFileController.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 25.
 * @version 1.0
 * @see
 * @Description 임시파일 컨트롤러
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 25.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Controller
public class TempFileController {
	
	/** 임시파일 서비스 */
	@Resource(name="TempFileService")
	private TempFileService tempFileService;
	
	private static final String SUCCESS = "success";
	
	/**
	 * 업로드 가능여부 체크
	 * @param request
	 * @param totalCnt
	 * @param maxCnt
	 * @param totalSize
	 * @param maxSize
	 * @return
	 */
	private String uploadAbleChk(
			MultipartHttpServletRequest request, 
			String totalCnt,
			String maxCnt,
			String totalSize,
			String maxSize){
		
		Iterator<?> itr =  request.getFileNames();
		long fileSize = 0L; 
		
		if(itr.hasNext()) {
			List<MultipartFile> list = request.getFiles((String) itr.next());
			for(MultipartFile multipartFile : list) {
				fileSize = multipartFile.getSize();
			}
		}
		
		int totalFileCnt = Integer.parseInt(totalCnt) + 1;
		int totalFileSize = (int) (Integer.parseInt(totalSize) + fileSize);
		
		if(maxCnt != null && !"".equals(maxCnt) && !"0".equals(maxCnt)){
			if(totalFileCnt > Integer.parseInt(maxCnt)){
				return "최대 " + maxCnt + "개의 파일이 업로드 가능합니다.";
			}
		}

		if(totalFileSize > Integer.parseInt(maxSize)){
			return "첨부파일 제한용량 " + (Integer.parseInt(maxSize) / 1000000.00) + "MB를 초과하였습니다.";
		}
		
		return SUCCESS;
		
	}
	
	/**
	 * 임시파일 업로드 ajax 호출
	 * @param model
	 * @param org.springframework.web.multipart.MultipartHttpServletRequest request
	 * @param totalCnt : 첨부된 파일 수
	 * @param maxCnt : 첨부파일 수 제한(없으면 0)
	 * @param totalSize : 첨부된 파일 용량  
	 * @param maxSize : 첨부파일 용량 제한(바이트)
	 * @return
	 */
	@RequestMapping("/tempFile/upload.json")
	public String upload(
			ModelMap model,
			MultipartHttpServletRequest request,
			String totalCnt,
			String maxCnt,
			String totalSize,
			String maxSize) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuccess = false;
		String message = "파일 업로드 중 오류가 발생하였습니다.";
		String chk = "";
		
		chk = uploadAbleChk(request, totalCnt, maxCnt, totalSize, maxSize);
		
		if(!SUCCESS.equals(chk)){
			jsonObj.addProperty("isSuccess", false);
			jsonObj.addProperty("message", chk);
			model.addAttribute("obj", jsonObj);
			return "obj";
		}
		
		TempFile file = tempFileService.setTempFile(request);
		if(file != null && file.getFileSeq() != null){
			isSuccess = true;
		}
		
		jsonObj.addProperty("fileSeq", file.getFileSeq());
		jsonObj.addProperty("fileRename", file.getFileRename());
		jsonObj.addProperty("fileOrg", file.getFileOrg());
		jsonObj.addProperty("fileType", file.getFileType());
		jsonObj.addProperty("fileSize", file.getFileSize());
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	
	/**
	 * 임시파일 삭제
	 * @param model
	 * @param tmpfileSeq
	 * @return
	 */
	@RequestMapping("/tempFile/delete.json")
	public String delete(ModelMap model, String tmpfileSeq){
		
		JsonObject jsonObj = new JsonObject();
		boolean isSuccess = false;
		
		Integer fileSeq = Integer.parseInt(tmpfileSeq.replaceAll("T_", ""));
		
		TempFile file = tempFileService.getTempFile(fileSeq);
		
		if(file != null && file.getFileSeq() != null){
			jsonObj.addProperty("fileSize", file.getFileSize());
			tempFileService.deleteTempFile(fileSeq);
			isSuccess = true;
		}
		
		jsonObj.addProperty("message", "파일삭제 오류");
		jsonObj.addProperty("isSuccess", isSuccess);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
	}
}

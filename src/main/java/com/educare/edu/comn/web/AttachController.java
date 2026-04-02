package com.educare.edu.comn.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.util.ConfigHandle;
import com.educare.util.FileUtil;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

/**
 * @Class Name : AttachController.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 30.
 * @version 1.0
 * @see
 * @Description 첨부파일 관련 컨트롤러
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 30.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Controller
public class AttachController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(AttachController.class.getName());
	
	/**
	 * CKEDITOR 첨부파일 업로드
	 * @param commandMap
	 * @param model
	 * @return null
	 * @throws Exception e
	 */
	@RequestMapping(value="/editot/popupUpload.json", method=RequestMethod.POST)
	public String fileUpload(HttpServletRequest req, HttpServletResponse resp, Model model,
                 MultipartHttpServletRequest multiFile) throws Exception {
		String gubun = multiFile.getParameter("gubun");
		String prefixStr = multiFile.getParameter("prefixStr");
		
		MultipartFile file = multiFile.getFile("upload");
		if(file != null){
			if(file.getSize() > 0 && StringUtils.isNotBlank(file.getName())){
				if(file.getContentType().toLowerCase().startsWith("image/") ){
					String fileOrg = file.getOriginalFilename();
					String fileRename = FileUtil.getFileRename(fileOrg, prefixStr+"_");
					String folderName = "upload/web/editor/"+gubun+"/";
					String cdnUrl = ConfigHandle.getProperties("cloud.cdn.url");
					//NaverObjectStorage.multiPartupload(file, folderName, fileOrg, fileRename);
					FileUtil.multiPartupload(file, folderName, fileOrg, fileRename);
					
                    model.addAttribute("uploaded",1);
                    model.addAttribute("fileName",fileRename);
                    model.addAttribute("url",cdnUrl+"/"+folderName+fileRename);
				}
			}
		}
		return "jsonView";
	}
}

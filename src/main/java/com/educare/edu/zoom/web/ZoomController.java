package com.educare.edu.zoom.web;

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
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
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
@RequestMapping(value="/zoom/")
public class ZoomController {
	
	/**
	 * 팝업 프리뷰 
	 * jsp파일 공통 경로로 바꿔야함
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("zoom_create")
	public String zoomCreated(ModelMap model) {
        return "/zoom/zoom_create" + LncUtil.TILES;
	}

}

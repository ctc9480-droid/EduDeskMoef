package com.educare.edu.eduMyPopup.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.educare.component.UtilComponent;

@Controller
@RequestMapping(value="/admin/sample/")
public class EduMyPopupAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EduMyPopupAdminController.class.getName());
	
	@Resource(name = "utcp")
	private UtilComponent utcp;
	 

}

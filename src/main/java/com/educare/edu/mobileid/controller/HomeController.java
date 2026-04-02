package com.educare.edu.mobileid.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * 로그인 화면
	 */
	@RequestMapping(value = "/signIn.do", method = RequestMethod.GET)
	public String signIn(Locale locale, Model model) {
		logger.info("Welcome signin! The client locale is {}.", locale);
		
		return "signIn";
	}

	/**
	 * 로그인 완료 후
	 */
	@RequestMapping(value = "/myPage.do", method = RequestMethod.GET)
	public String myPage(Locale locale, Model model) {
		logger.info("you sign in our serivce.");
		
		String signInInfo = "안녕하십니까. 로그인 되었습니다.";
		model.addAttribute("signInInfo", signInInfo);
		
		return "myPage";
	}
}

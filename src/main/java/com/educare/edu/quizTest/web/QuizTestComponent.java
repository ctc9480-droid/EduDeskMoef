package com.educare.edu.quizTest.web;

import org.springframework.stereotype.Component;

@Component("qtcp")
public class QuizTestComponent {
	public static final String UPLOAD_PATH = "upload/web/quizBank/";
	private static final String[] DIFF_TYPE={"-","1단계","2단계","3단계"};
	private static final String[] QSTN_TP={"-","객관식","객관식다중선택","단답형","서술형"};
	
	public static String[] getDiffTypeArr(){
		try {
			String[] diffTypeArr = DIFF_TYPE;
			return diffTypeArr;
		} catch (NullPointerException e) {
			return null;
		}
	}
	public static String[] getQstnTpArr(){
		try {
			String[] qstnTpArr = QSTN_TP;
			return qstnTpArr;
		} catch (NullPointerException e) {
			return null;
		}
	}
}

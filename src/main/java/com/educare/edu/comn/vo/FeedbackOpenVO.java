package com.educare.edu.comn.vo;

import java.util.Date;

import com.educare.edu.comn.model.FeedbackOpen;
import com.educare.util.DateUtil;

public class FeedbackOpenVO extends FeedbackOpen{
	public String getAddNowDtime(){
		try {
			return DateUtil.getDate2Str(new Date(), "yyyyMMddHHmmssSSS");
		} catch (NullPointerException e) {
			return null;
		}
	}
}

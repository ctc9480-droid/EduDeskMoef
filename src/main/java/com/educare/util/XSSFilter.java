package com.educare.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XSSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String contentType = req.getContentType();

        if (contentType != null && contentType.startsWith("multipart/form-data")) {
            // multipart 요청 → multipart 전용 Wrapper 적용
            chain.doFilter(new XSSMultipartRequestWrapper(req), response);
        }else { // 일반 요청 → 기존 Wrapper 적용 
        	request.setCharacterEncoding("UTF-8");
        	chain.doFilter(req, response);
        }
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}

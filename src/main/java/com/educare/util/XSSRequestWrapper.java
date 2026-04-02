package com.educare.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return cleanXSS(value);
    }

    private String cleanXSS(String value) {
        if (value == null) {
            return null;
        }

        // 아주 단순한 XSS 치환
        value = value.replaceAll("<", "&lt;");
        value = value.replaceAll(">", "&gt;");
        value = value.replaceAll("\"", "&quot;");
        value = value.replaceAll("'", "&#39;");

        return value;
    }
}

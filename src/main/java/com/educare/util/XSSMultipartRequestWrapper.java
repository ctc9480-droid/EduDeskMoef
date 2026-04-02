package com.educare.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class XSSMultipartRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> sanitizedParams = new HashMap<>();

    public XSSMultipartRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
        	request.setCharacterEncoding("UTF-8");
            // multipart 여부 확인
            if (ServletFileUpload.isMultipartContent(request)) {
                // 파일은 건드리지 않고, 파라미터만 치환
                Map<String, String[]> params = request.getParameterMap();
                for (Map.Entry<String, String[]> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String[] values = entry.getValue();
                    String[] sanitizedValues = new String[values.length];

                    for (int i = 0; i < values.length; i++) {
                        sanitizedValues[i] = cleanXSS(values[i]);
                    }
                    sanitizedParams.put(key, sanitizedValues);
                }
            } else {
                // 일반 요청도 동일하게 처리
                Map<String, String[]> params = request.getParameterMap();
                for (Map.Entry<String, String[]> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String[] values = entry.getValue();
                    String[] sanitizedValues = new String[values.length];

                    for (int i = 0; i < values.length; i++) {
                        sanitizedValues[i] = cleanXSS(values[i]);
                    }
                    sanitizedParams.put(key, sanitizedValues);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getParameter(String name) {
        String[] values = sanitizedParams.get(name);
        return (values != null && values.length > 0) ? values[0] : null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return sanitizedParams.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return sanitizedParams;
    }

    private String cleanXSS(String value) {
        if (value == null) return null;
        // 간단한 XSS 치환 예시
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        //value = StringEscapeUtils.unescapeHtml4(value);
        return value;
    }
}
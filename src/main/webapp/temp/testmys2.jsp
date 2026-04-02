<%@ page import="java.sql.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%!
	public String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		System.out.println("> X-FORWARDED-FOR : " + ip);

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
			System.out.println("> Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			System.out.println(">  WL-Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			System.out.println("> HTTP_CLIENT_IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			System.out.println("> HTTP_X_FORWARDED_FOR : " + ip);
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
			System.out.println("> getRemoteAddr : "+ip);
		}
		System.out.println("> Result : IP Address : "+ip);

		return ip;
	}
%>

<%
response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/edu; Secure; SameSite=None");

//String ip = LncUtil.getIp(request);
String ip = getClientIP(request);
out.println("RemoteAddress - " + ip);

if(!"106.244.74.221,127.0.0.1,0:0:0:0:0:0:0:1".contains(ip)){
	return;
}

%>

<!DOCTYPE html>
<html>
<head>
    <title>MySQL Query Executor</title>
    <style>
    	input { font-size: 18px; }
		textarea { font-size: 20px;	}
	</style>
</head>
<body>
    <h2>MySQL Query Executor</h2>
    <form method="post">
    	check: <input name="checkstr" type = "text"> <br>
        query: <textarea name="query" rows="10" cols="200"><%= request.getParameter("query") != null ? request.getParameter("query") : "" %></textarea><br>
        <input type="submit" value="Execute">
    </form>
    <hr>
    <%
    // Mysql local test DB 접속 정보
    String jdbcUrl = "jdbc:mysql://106.244.74.221:13306/suhyup_lms"; // DB URL (포트와 SID)
    String jdbcUser = "suhyup_lms"; // Mysql 사용자명
    String jdbcPassword = "suhyup_lms"; // Mysql 비밀번호
    String driverNm = "com.mysql.jdbc.Driver";
    
    // Mysql Suhyup LMS  DB
    //String jdbcUrl = "jdbc:mysql://138.252.11.61:3306/suhyup"; // DB URL (포트와 SID)
    //String jdbcUser = "suhyup"; // Mysql 사용자명
    //String jdbcPassword = "Suhyup!@#456"; // Mysql 비밀번호
    
    	String checkstr = request.getParameter("checkstr");
    	if (!"1234qwer!".equals(checkstr)) {
    		out.println("checkstr 불일치");
    		return;
    	}
    	
        String query = request.getParameter("query");
        if (query != null && !query.trim().isEmpty()) {
            String url = jdbcUrl;
            String user = jdbcUser;
            String password = jdbcPassword;
            
            try {
                Class.forName(driverNm);
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                boolean isResultSet = stmt.execute(query);
                
                if (isResultSet) {
                    ResultSet rs = stmt.getResultSet();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
    %>
    <table border="1">
        <tr>
            <% for (int i = 1; i <= columnCount; i++) { %>
                <th><%= metaData.getColumnName(i) %></th>
            <% } %>
        </tr>
        <% while (rs.next()) { %>
        <tr>
            <% for (int i = 1; i <= columnCount; i++) { %>
                <td><%= rs.getString(i) %></td>
            <% } %>
        </tr>
        <% } %>
    </table>
    <%
                    rs.close();
                } else {
                    out.println("<p>Query executed successfully.</p>");
                }
                stmt.close();
                conn.close();
            } catch (Exception e) {
                out.println("<p>Error: " + e.getMessage() + "</p>");
            }
        }
    %>
</body>
</html>
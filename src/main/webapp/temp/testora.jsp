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
    <h2>Oracle Query Executor</h2>
    <form method="post">
    	check: <input name="checkstr" type = "text"> <br>
    	table: <input name="tablenm" type = "text"> <br>
        where: <textarea name="wherestr" rows="5" cols="200"><%= request.getParameter("query") != null ? request.getParameter("query") : "" %></textarea><br>
        row: <input name="rownum" type = "number" value="5"> <br>
        <input type="submit" value="Execute">
    </form>
    <hr>
    <%
    // Oracle local test DB 접속 정보
    String jdbcUrl = "jdbc:oracle:thin:@106.244.74.221:21521/orcl"; // DB URL (포트와 SID)
    String jdbcUser = "scott"; // 오라클 사용자명
    String jdbcPassword = "scott1234"; // 오라클 비밀번호
    String driverNm = "oracle.jdbc.OracleDriver";
    
    // Oracle Suhyup 조업관리  DB
    //String jdbcUrl = "jdbc:oracle:thin:@138.252.11.100:1521/USMDB"; // DB URL (포트와 SID)
    //String jdbcUser = "REAL_HOME"; // 오라클 사용자명
    //String jdbcPassword = "edu_lms22"; // 오라클 비밀번호
    
    	String checkstr = request.getParameter("checkstr");
    	if (!"1234qwer!".equals(checkstr)) {
    		out.println("checkstr 불일치");
    		return;
    	}
    	
    	String tablenm = request.getParameter("tablenm");
    	String wherestr = request.getParameter("wherestr");
    	String rownum = request.getParameter("rownum");
    	
        //String query = request.getParameter("query");
        String query = "select * from " + tablenm + " where " + wherestr + " and rownum <= " + rownum;
        out.println("<p>Query String : " + query + "</p>");
        
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
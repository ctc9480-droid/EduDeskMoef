<%@page import="org.springframework.util.ObjectUtils"%>
<%@page import="com.educare.util.LncUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<%
response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/edu; Secure; SameSite=None");

String ip = LncUtil.getIp(request);
out.println(ip);
if(!"106.244.74.221,127.0.0.1,0:0:0:0:0:0:0:1,192.168.0.164,192.168.0.226,192.168.245.100".contains(ip)){
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
    <title>DB 데이터 출력</title>
    <style>
        table {
        	
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0 20px;
            font-size: 18px;
            text-align: left;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:hover {background-color: #f5f5f5;}
        td input[type=text] {
        	width: 100%;
        }
    </style>
    

</head>
<body>
<form id="form-db" method="post">
</form>
<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/jquery-3.5.1.min.js" type="text/javascript"></script>
<script>
	
	
	$('textarea').keydown(function (event) {
	      // Enter key has the key code 13
	      if (event.key === "Enter") {
	        event.preventDefault(); // Prevents default action (e.g., new line)
	        $('#form-db').submit(); // Submits the form
	      }
	    });
</script>

<%
/* 
if(this.value == 'oracle1'){
	$url.val('jdbc:oracle:thin:@//172.16.1.128:1521/ktlis');
	$driverNm.val('oracle.jdbc.OracleDriver');
	$id.val('REAL_HOME2');
	$pw.val('REAL_HOME2');
}else if(this.value == 'mssql2'){
	$url.val('jdbc:sqlserver://172.16.1.60:1433;databaseName=EON;encrypt=false;trustServerCertificate=true;loginTimeout=30;');
	$driverNm.val('com.microsoft.sqlserver.jdbc.SQLServerDriver');
	$id.val('ktleondb');
	$pw.val('ktleon12#');
}else if(this.value == 'mssql1'){
	$url.val('jdbc:sqlserver://172.16.1.185:1433;databaseName=ktl_d4you;encrypt=false;trustServerCertificate=true;loginTimeout=30;');
	$driverNm.val('com.microsoft.sqlserver.jdbc.SQLServerDriver');
	$id.val('ktl_d4you');
	$pw.val('mssqlpwdktl_d4you!@#');
}
 */

    // Oracle DB 접속 정보
    String jdbcUrl = "jdbc:oracle:thin:@//172.16.1.128:1521/ktlis"; // DB URL (포트와 SID)
    String jdbcUser = "REAL_HOME"; // 오라클 사용자명
    String jdbcPassword = "REAL_HOME"; // 오라클 비밀번호
    String driverNm = "oracle.jdbc.OracleDriver";
    
    jdbcUrl = "jdbc:oracle:thin:@//106.244.74.221:21521/orcl"; // DB URL (포트와 SID)
    jdbcUser = "scott"; // 오라클 사용자명
    jdbcPassword = "scott1234"; // 오라클 비밀번호
    driverNm = "oracle.jdbc.OracleDriver";
    
    
    String sql = "select * from wbill_outm_test where bill_no = ?";
    //sql = "update wbill_outm_test set KYEL_TY = 1 where bill_no = ?";
    
    out.println(sql);
    out.println("<br/>");
    
    if(ObjectUtils.isEmpty(jdbcUrl) || ObjectUtils.isEmpty(jdbcUser) || ObjectUtils.isEmpty(jdbcPassword) || ObjectUtils.isEmpty(driverNm) || ObjectUtils.isEmpty(sql)){
    	out.println("공백");
    	return;
    }
    
    String regex = "(?i)^\\s*SELECT\\s+.+\\s+FROM\\s+.+;?\\s*$";
    if(!sql.matches(regex)){
    	//out.println("select 구문 아님");
    	//return;
    }
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Map<String, Object>> resultList = new ArrayList<>();
    List<String> columnNames = new ArrayList<>();  // 컬럼명을 저장할 리스트

    try {
        // 1. JDBC 드라이버 로드 (Oracle)
        Class.forName(driverNm);

        // 2. 데이터베이스 연결
        conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

        // 3. SQL 쿼리 준비
        pstmt = conn.prepareStatement(sql);
		
        //pstmt.setString(1, "24-123123");
        pstmt.setString(1, "1202501400001");
        
        // 4. 쿼리 실행
        rs = pstmt.executeQuery();
        //pstmt.executeUpdate();
		
        if(rs == null){
        	return;
        }
        
        // 5. 결과 메타데이터를 이용하여 컬럼명을 동적으로 처리
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();  // 컬럼 수

        // 컬럼명 저장
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(rsmd.getColumnName(i));
        }

        // 데이터 저장
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);  // 컬럼명 가져오기
                Object columnValue = rs.getObject(i);  // 컬럼 값 가져오기
                row.put(columnName, columnValue);  // Map에 컬럼명과 값 저장
            }
            resultList.add(row);  // 리스트에 추가
        }

%>

<h2>DB 데이터 결과</h2>

<!-- 테이블 시작 -->
<table>
    <!-- 테이블 헤더 -->
    <tr>
        <%
            for (String columnName : columnNames) {
                out.println("<th>" + columnName + "</th>");
            }
        %>
    </tr>

    <!-- 테이블 데이터 -->
    <%
        for (Map<String, Object> row : resultList) {
            out.println("<tr>");
            for (String columnName : columnNames) {
                out.println("<td>" + row.get(columnName) + "</td>");
            }
            out.println("</tr>");
        }
    %>
</table>

<%
    } catch (Exception e) {
        e.printStackTrace();
        out.println("DB 오류 발생: " + e.getMessage());
    } finally {
        // 자원 해제
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
%>

</body>
</html>

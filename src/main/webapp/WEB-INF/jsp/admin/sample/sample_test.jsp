<%@page import="com.educare.util.LncUtil"%>
<%@page import="org.springframework.web.multipart.MultipartHttpServletRequest"%>
<%@page import="org.springframework.web.multipart.MultipartFile"%>
<%@page import="com.educare.util.FileUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>




<form enctype="multipart/form-data" method="post" id="form-reg">
<input type="file" id="filetest"/>
<button type="button" onclick="upload();">upload</button>
</form>

        
<script>
function upload(){
	var formData2 = new FormData();
	formData2.append("filetest", $("#filetest")[0].files[0]);
$.ajax({
	contentType : false,
	processData : false,
	url : "testUpload.ajax",
	type : "post",
	data : formData2,
	cache : false,
	async : true,
	success : function(r2) {
		if(r2.result ==1){
		}
	}
});
}
</script>


<form enctype="multipart/form-data" method="post" id="form-reg">
<input type="file" id="orgFile"/>
<button type="button" onclick="upload2();">org upload</button>
</form>
<script>
function upload2(){
	var formData2 = new FormData();
	formData2.append("orgFile", $("#orgFile")[0].files[0]);
$.ajax({
	contentType : false,
	processData : false,
	url : "orgUpload.ajax",
	type : "post",
	data : formData2,
	cache : false,
	async : true,
	success : function(r2) {
		if(r2.result ==1){
		}
	}
});
}
</script>

<form enctype="multipart/form-data" method="post" >
<input type="file" id="etcGradeFile"/>
<button type="button" onclick="upload3();">grade upload</button>
</form>
<script>
function upload3(){
	var formData2 = new FormData();
	formData2.append("etcGradeFile", $("#etcGradeFile")[0].files[0]);
$.ajax({
	contentType : false,
	processData : false,
	url : "etcGradeUpload.ajax",
	type : "post",
	data : formData2,
	cache : false,
	async : true,
	success : function(r2) {
		if(r2.result ==1){
		}
	}
});
}
</script>

<form enctype="multipart/form-data" method="post" >
<input type="file" id="etcJikjongFile"/>
<button type="button" onclick="upload4();">직종 upload</button>
</form>
<form enctype="multipart/form-data" method="post" >
<input type="file" id="etcJikryulFile"/>
<button type="button" onclick="upload4();">직렬 upload</button>
</form>
<form enctype="multipart/form-data" method="post" >
<input type="file" id="etcJikryuFile"/>
<button type="button" onclick="upload4();">직류 upload</button>
</form>
<script>
function upload4(){
	var formData4 = new FormData();
	formData4.append("etcJikjongFile", $("#etcJikjongFile")[0].files[0]);
	formData4.append("etcJikryulFile", $("#etcJikryulFile")[0].files[0]);
	formData4.append("etcJikryuFile", $("#etcJikryuFile")[0].files[0]);
$.ajax({
	contentType : false,
	processData : false,
	url : "etcGradeUpload2.ajax",
	type : "post",
	data : formData4,
	cache : false,
	async : true,
	success : function(r2) {
		if(r2.result ==1){
		}
	}
});
}
</script>
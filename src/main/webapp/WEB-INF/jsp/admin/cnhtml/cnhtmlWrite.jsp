<%@page import="com.educare.component.VarComponent"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>
<%-- 
<script src="${utcp.ctxPath}/resources/plugins/ckeditor5-build-classic/ckeditor.js"></script>
 --%>
<script>
	$(function() {
	
		CKEDITOR.config.allowedContent = true;
		CKEDITOR.replace('ckeditCn', {
			filebrowserUploadUrl : '${utcp.ctxPath}/editot/popupUpload.json?gubun=board&prefixStr=${vo.boardType}_${vo.idx}',
			height : 400,
		});
		
	});

	function fn_SaveCnhtml() {
	
		$('#content').val(CKEDITOR.instances.ckeditCn.getData());

		var formData = $('#reg-form').serialize();
		$.ajax({
			url:'saveCnhtmlProc.ajax',
			data:formData,
			method:'post',
			success:function(r){
				if(r.result == 1){
					location.href='cnhtmlList.do';
					return;
				}else{
					alert('시스템 에러가 발생하였습니다.');
				}
			}
		});
	}
	
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form name="reg_form" id="reg-form" method="post">
			<input type="hidden" id="cnhtSeq" name="cnhtSeq" value="${param.cnhtSeq }" /> 

			<table width="100%" class="tb02 tc">
				<caption class="sound_only">게시판 등록 테이블</caption>
				<tbody>
					<%-- 게시여부 사용하려면 주석 해제
					--%>
					<tr>
						<th class="tdbg3 tc"><label for="mn_id1">컨텐츠구분</label></th>
						<td class="tl">
						<%
						    request.setAttribute("cnhtTypeMap", VarComponent.getCnhtTypeList());
						%>
						<select name="cnhtType">
						    <c:forEach var="entry" items="${cnhtTypeMap}">
						        <option value="${entry.key}">${entry.value}</option>
						    </c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="mn_id1">게시여부</label></th>
						<td class="tl">
								<select name="state">
								<c:forEach items="<%=VarComponent.getCnhtStateArr() %>" var="nm" varStatus="s">
									<c:if test="${s.index > 0 }">
									<option value="${s.index }" ${s.index==data.cnht.state?'selected':'' }> ${nm }
									</c:if>
								</c:forEach>
								</select>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="wr_name">제목</label></th>
						<td class="tl"><input type="text" id="title" name="title" value="${data.cnht.title }" maxlength="50" class="ip4" style="${boardMap.status}" placeholder="제목" /></td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="mn_id1">내용</label></th>
						<td class="tl">
						<textarea id="ckeditCn" name="ckeditCn" placeholder="내용" class="w100 h500" style="height: 100%">${data.cnht.content }</textarea> 
						<input type="hidden" id="content" name="content" />
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<div class="fl tc">
			<button onclick="location.href='cnhtmlList.do'" class="btn02 btn_grayl">목록</button>
		</div>

		<div class="fr tc">
			<button type="button" class="btn01 btn_greenl" onclick="fn_SaveCnhtml();">저장</button>
		</div>
	</div>
</section>



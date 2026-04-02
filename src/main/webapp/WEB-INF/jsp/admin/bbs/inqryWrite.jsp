<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script>
	function fn_reg() {
		$('#form_reg').attr('action','inqryWriteProc.do')
		$('#form_reg').submit();
	}	
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form name="form_reg" id="form_reg" method="post">
			<input type="hidden" name="idx" value="${vo.idx }" />
			<table width="100%" class="tb02 tc">
				<caption class="sound_only">온라인문의 등록 테이블</caption>
				<tbody>
					<tr>
						<th class="tdbg3 tc">성명
						</td>
						<td class="tl" colspan="">${inqryMap.regNm }</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">휴대폰번호 
						</td>
						<td class="tl" colspan="">${inqryMap.contact}</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">이메일
						</td>
						<td class="tl" colspan="">${inqryMap.email}</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">문의 제목</th>
						<td class="tl">
							<%-- <input type="text" name="title" value="${inqryMap.title }" style="width: 100%" /> --%> ${inqryMap.title }
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">문의 내용</th>
						<td class="tl">
							<%-- <textarea name="content" style="width: 500px; height: 300px;">${inqryMap.content }</textarea> --%> ${inqryMap.content2 }
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">답변</th>
						<td class="tl"><textarea name="answer" style="width: 100%; height: 300px;">${inqryMap.answer }</textarea></td>
					</tr>
					<tr>
						<th class="tdbg3 tc">상태</th>
						<td class="tl"><select name="status">
								<c:forEach items="${vo.statusArr }" var="nm" varStatus="status">
									<option value="${status.index }" ${status.index==inqryMap.status?'selected':'' }>${nm }</option>
								</c:forEach>
						</select></td>
					</tr>
				</tbody>
			</table>
			<div class="fl tc">
				<button onclick="location.href='${utcp.ctxPath}/admin/bbs/inqryList.do';" class="btn02 btn_grayl" type="button">목록</button>
			</div>

			<div class="fr tc">
				<button onclick="fn_reg();" class="btn01 btn_green" type="button">수정</button>
				
			</div>
		</form>
	</div>
</section>

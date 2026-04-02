<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<input type="hidden" id="idx" value="${param.idx }" />
<section class="pd025 pd200 po_re" id="vm-feedback">
	<div class="po_re br5 bs_box cf">
		<div class="fbWrap">

			<table width="100%" class="tb05">
				<tr>
					<th>제목</th>
					<td class="mcqtd"><input value="${passTpl.title }" name="title" type="text" class="ip2 mgl10" /></td>
				</tr>
				<tr>
					<th>내용</th>
					<td class="mcqtd"><input value="${passTpl.content }" name="content" type="text" class="ip2 mgl10" /></td>
				</tr>
			</table>
		</div>
	</div>
</section>


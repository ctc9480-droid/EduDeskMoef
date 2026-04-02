<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script>
	function fn_del() {
		if (!confirm('다시 복구 할 수 없습니다. 삭제하시겠습니까?')){return;}
		$('#form_view').attr('action', 'inqryDeleteProc.do')
		$('#form_view').submit();
	}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<table width="100%" class="tb02 tc">
			<caption class="sound_only">온라인문의테이블</caption>
			<tbody>
				<tr>
					<th class="tdbg3 tc">성명
					</td>
					<td class="tl" colspan="3">${inqryMap.regNm }</td>
				</tr>
				<tr>
					<th class="tdbg3 tc">휴대폰번호 </th>
					<td class="tl" colspan="3">${inqryMap.contact }</td>
				</tr>
				<tr>
					<th class="tdbg3 tc">이메일</th>
					<td class="tl" colspan="3">${inqryMap.email }</td>
				</tr>
				<tr>
					<th class="tdbg3 tc">문의 제목</th>
					<td class="tl" colspan="3">${inqryMap.title }</td>
				</tr>
				<tr>
					<th class="tdbg3 tc">문의 내용</th>
					<td class="tl h300" colspan="3">${inqryMap.content }</td>
				</tr>
				<tr>
					<th class="tdbg3 tc"><label for="iq_content">답변 내용</label></th>
					<td class="tl h300" colspan="3">${utcp.convNewLine(inqryMap.answer) }</td>
				</tr>
				<tr>
					<th class="tdbg3 tc">상태</th>
					<td class="tl" colspan="3">${inqryMap.statusNm }</td>
				</tr>
			</tbody>
		</table>
		<div class="fl tc">
			<a href="inqryList.do" class="btn02 btn_grayl">목록</a>
		</div>
		<div class="fr tc">
			<button onclick="location.href='inqryWrite.do?idx=${inqryMap.idx }'" class="btn01 btn_green">답변달기</button>
			<button onclick="fn_del();" class="btn01 btn_gray" type="button">삭제</button>
		</div>
	</div>
</section>

<form id="form_view">
<input type="hidden" name="idx" value="${inqryMap.idx }"/>
</form>

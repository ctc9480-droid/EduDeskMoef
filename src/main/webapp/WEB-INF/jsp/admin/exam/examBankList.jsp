<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>


<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<div class="tr fbTxt">
			<button class="btn01 btn_green fbBtn" onclick="location.href='examBankReg.do';">
				<i class="fas fa-file-signature"></i> 문제 만들기
			</button>
		</div>
		<div class="fbCon">
			<table width="100%" class="tb03 tc">
				<c:forEach items="${data.boardList}" var="o" varStatus="s">
				<tr>
					<td>${data.boardCnt-o.rNum+1 }</td>
					<td class="tl"><a href="examBankReg.do?ebqIdx=${o.ebqIdx }">
						<span class="dp_ib fl font_22 fw_500 pdl15 pdr15">${o.fncQuestion }</span></a> 
					</td>
					<td>${utcp.convDateToStr(o.regTime,'yyyy-MM-dd HH:mm') }</td>
				</tr>
				</c:forEach>
			</table>
			<jsp:include page="/WEB-INF/jsp/admin/bbs/pageNavi.jsp"/>
		</div>
	</div>
</section>

<script>

</script>

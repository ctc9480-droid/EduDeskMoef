<%@page import="com.educare.edu.education.service.EduVO"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%@page import="com.educare.util.DateUtil"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.educare.edu.member.service.MemberService"%>
<%@page import="com.educare.edu.member.service.model.UserInfo"%>
<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@page import="com.educare.edu.education.service.model.Lecture"%>
<%@page import="com.educare.edu.education.service.EduService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%
response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/; Secure; SameSite=None");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>ktl 결제 페이지</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=yes, target-densitydpi=medium-dpi">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<link href="${utcp.ctxPath}/pay/easypay/static/css/style.css" rel="stylesheet" type="text/css" id="cssLink" />
<script src="${utcp.ctxPath}/resources/user/js/jquery-3.5.1.min.js"></script>
 <script src="https://js.tosspayments.com/v2/standard"></script>
</head>

<body>

	<div class="wrap">

		<!-- 주문정보 입력 form : order_info -->
		<form id="payForm" method="post" action="">
			<!-- header -->
			<div class="header">
				<a href="javascript:fn_moveCancel();" class="btn-back"><span>뒤로가기</span></a>
				<h1 class="title">
					주문/결제
					<!-- SAMPLE -->
				</h1>
			</div>
			<!-- //header -->
			<!-- contents -->
			<div id="skipCont" class="contents">
				<p class="txt-type-1">
					<!-- 이 페이지는 결제를 요청하는 샘플 페이지입니다. -->
				</p>
				<!-- <p class="txt-type-2">소스 수정 시 [※ 필수] 또는 [※ 옵션] 표시가 포함된 문장은 가맹점의 상황에 맞게 적절히 수정 적용하시기 바랍니다.</p> -->
				<!-- 주문내역 -->
				<h2 class="title-type-3">주문내역</h2>
				<ul class="list-type-1">
					<!-- 주문번호(ordr_idxx) -->
					<li>
						<div class="left">
							<p class="title">주문번호</p>
						</div>
						<div class="right">
							<div class="ipt-type-1 pc-wd-2">
								<input type="text" id="shopOrderNo" value="${ordr_idxx}" maxlength="40" readonly="readonly" />
								<a href="#none" class="btn-clear"></a>
							</div>
						</div>
					</li>
					<!-- 상품명(good_name) -->
					<li>
						<div class="left">
							<p class="title">교육명</p>
						</div>
						<div class="right">
							<div class="ipt-type-1 pc-wd-2">
								<input type="text" id="goodsName" value="${good_name}" readonly="readonly" />
								<a href="#none" class="btn-clear"></a>
							</div>
						</div>
					</li>
					<!-- 결제금액(good_mny) - ※ 필수 : 값 설정시 ,(콤마)를 제외한 숫자만 입력하여 주십시오. -->
					<li>
						<div class="left">
							<p class="title">교육비</p>
						</div>
						<div class="right">
							<div class="ipt-type-1 gap-2 pc-wd-2">
								<fmt:formatNumber type="number" maxFractionDigits="3" value="${good_mny}" var="good_mny2" />
								<input type="text" value="${good_mny2 }" maxlength="9" readonly="readonly" />
								<input type="hidden" id="amount" value="${good_mny}" maxlength="9" readonly="readonly" />
								<a href="#none" class="btn-clear"></a> <span class="txt-price">원</span>
							</div>
						</div>
					</li>
				</ul>
				<div class="line-type-1"></div>
				<!-- 주문정보 -->
				<h2 class="title-type-3">주문정보</h2>
				<ul class="list-type-1">
					<!-- 주문자명(buyr_name) -->
					<li>
						<div class="left">
							<p class="title">주문자명</p>
						</div>
						<div class="right">
							<div class="ipt-type-1 pc-wd-2">
								<input type="text" id="buyrName" name="buyr_name" value="${buyr_name}" readonly="readonly" />
								<a href="#none" class="btn-clear"></a>
							</div>
						</div>
					</li>
					<!-- 주문자 연락처1(buyr_tel1) 
                    <li>
                        <div class="left"><p class="title">전화번호</p></div>
                        <div class="right">
                            <div class="ipt-type-1 pc-wd-2">
                                <input type="text" name="buyr_tel1" value="${buyr_tel2}" readonly="readonly"/>
                                <a href="#none" class="btn-clear"></a>
                            </div>
                        </div>
                    </li>
                    -->
					<!-- 휴대폰번호(buyr_tel2) -->
					<li>
						<div class="left">
							<p class="title">휴대폰번호</p>
						</div>
						<div class="right">
							<div class="ipt-type-1 pc-wd-2">
								<input type="text" id="buyrTel2" name="buyr_tel2" value="${user.mobile}" readonly="readonly" />
								<a href="#none" class="btn-clear"></a>
							</div>
						</div>
					</li>
					<!-- 주문자 E-mail(buyr_mail) -->
					<li>
						<div class="left">
							<p class="title">이메일</p>
						</div>
						<div class="right">
							<div class="ipt-type-1 pc-wd-2">
								<input type="text" id="buyrMail" name="buyr_mail" value="${user.email}" readonly="readonly" />
								<a href="#none" class="btn-clear"></a>
							</div>
						</div>
					</li>
				</ul>
				<div class="line-type-1"></div>

				<div Class="Line-Type-1"></div>
				<ul class="list-btn-2">
					<!-- D : 버튼 비활성시 a태그에 class disable 추가 -->
					<li class="pc-only-show">
						<a href="javascript:fn_moveCancel();" class="btn-type-3 pc-wd-2">뒤로</a>
					</li>
					<li>
						<a href="#none" onclick="requestPayment();" class="btn-type-2 pc-wd-3">결제요청</a>
					</li>
				</ul>
			</div>
			<!-- //contents -->

			<div class="grid-footer">
				<div class="inner">
					<!-- footer -->
					<div class="footer">
						<!--              ⓒ Gnsm . -->
					</div>
					<!-- //footer -->
				</div>
			</div>

		</form>
	</div>
	<!--//wrap-->
	<script>

//------  SDK 초기화 ------
// @docs https://docs.tosspayments.com/sdk/v2/js#토스페이먼츠-초기화
const clientKey = "<spring:eval expression="@prop['pay.toss.clientKey']"/>";
const customerKey = "11bc3VlKhkCwU1TFog8nF";
const tossPayments = TossPayments(clientKey);
// 회원 결제
// @docs https://docs.tosspayments.com/sdk/v2/js#tosspaymentspayment
//const payment = tossPayments.payment({ customerKey });
// 비회원 결제
const payment = tossPayments.payment({customerKey: TossPayments.ANONYMOUS});
const customerMobile = '${user.mobile}'.replace(/-/g,'');
console.log(customerMobile);
// ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
// @docs https://docs.tosspayments.com/sdk/v2/js#paymentrequestpayment
async function requestPayment() {
  // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
  // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
  await payment.requestPayment({
    method: "CARD", // 카드 결제
    amount: {
      currency: "KRW",
      value: ('${good_mny}'*1),
    },
    orderId: "${ordr_idxx}", // 고유 주분번호
    orderName: "${good_name}",
    //successUrl: window.location.origin + "/success", // 결제 요청이 성공하면 리다이렉트되는 URL
    successUrl: window.location.origin + "${utcp.ctxPath}/user/pay/toss/success.do",
    //failUrl: window.location.origin + "/fail", // 결제 요청이 실패하면 리다이렉트되는 URL
    failUrl: window.location.origin + "${utcp.ctxPath}/user/pay/toss/fail.do",
    customerEmail: "${user.email}",
    customerName: "${buyr_name}",
    customerMobilePhone: customerMobile,
    // 카드 결제에 필요한 정보
    card: {
      useEscrow: false,
      flowMode: "DEFAULT", // 통합결제창 여는 옵션
      useCardPoint: false,
      useAppCardOnly: false,
    },
  });
}

	function fn_moveCancel() {
		//history.back();
		location.href='${utcp.ctxPath}/user/mypage/myLcRceptList.do';
	}

</script>
</body>
</html>
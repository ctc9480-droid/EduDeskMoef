<%@page import="com.educare.edu.menu.service.MenuUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	$(function() {
		<c:if test='${menuAuthList != null && fn:length(menuAuthList) > 0}'>
	    	<c:forEach var="auth" items="${menuAuthList}" varStatus="stat">
	    		$("input:checkbox[name=menuIds][value=${auth.menuId}]").prop("checked", true);
	    	</c:forEach>
		</c:if>
		
		
		$('input[id^=menu-').click(function() {
			var menuIdArr = this.id.split('-');
			if(menuIdArr.length==2){
				if($(this).is(':checked')){
					$('input[id^='+this.id+']').prop('checked',true);
				}else{
					$('input[id^='+this.id+']').prop('checked',false);
				}
			}
			if(menuIdArr.length==3){
				if($(this).is(':checked')){
					$('input[id='+menuIdArr[0]+'-'+menuIdArr[1]+']').prop('checked',true);
				}
			}
		});

		$('#email3').change(function() {
			$('#email2').val(this.value);
		});
	
			
		$('input:radio[name=userMemLvl][value=${not empty user.userMemLvl?user.userMemLvl:3}]').prop('checked',true);
		var tel = '${user.tel}'.split('-');
		var mobile = '${user.mobile}'.split('-');
		var email = '${user.email}'.split('@');
		$('#tel1').val(tel[0]);
		$('#tel2').val(tel[1]);
		$('#tel3').val(tel[2]);
		$('#mobile1').val(mobile[0]);
		$('#mobile2').val(mobile[1]);
		$('#mobile3').val(mobile[2]);
		$('#email1').val(email[0]);
		$('#email2').val(email[1]);
	});

	function fn_list() {
		location.href = "${utcp.ctxPath}/admin/member/oprtList.do";
	}

	function fn_rgs() {
		$('#mobile').val(
				$('#mobile1').val() + '-' + $('#mobile2').val() + '-'
						+ $('#mobile3').val());
		/* $('#tel').val(
				$('#tel1').val() + '-' + $('#tel2').val() + '-'
						+ $('#tel3').val()); 
		$('#email').val(
				$('#email1').val() + '@' + $('#email2').val());
		*/
		
		//비밀번호체크
		if($('#userPw').val() && $('#userPw').val() != $('#userPw2').val()){
			alert('비밀번호가 일치하지 않습니다.');
			return;
		}
		
		//권한 중복체크
		var unList = new Set(vm_eduAuth.authList);
		if(vm_eduAuth.authList.length != unList.size){
			alert('중복');
			return;
		}
		
		var formData = $('#mngrRgsFrm').serializeArray();
		
		//권한 중복체크
		var combinedSet = new Set();
		var combinedSet2 = new Set();
	    for (var i in vm_eduAuth.authList) {
	    	var item = vm_eduAuth.authList[i];
	    	if(vm_eduAuth.authList.length>1 && item.ctgrySeq1 == 0){
	    		alert('전체권한은 하나만 등록 가능합니다.');
	    		return;
	    	}
	    	
	    	var combinedKey = item.ctgrySeq1+'_'+item.ctgrySeq2;
	        if (combinedSet.has(combinedKey)) {
	            alert('교육운영권한이 중복되었습니다.');
	        	return true;
	        } else {
	            combinedSet.add(combinedKey);
	        }
	    	
	        if(item.ctgrySeq2==0){
	        	 combinedSet2.add(item.ctgrySeq1);
	        }
	        if (item.ctgrySeq2>0 && combinedSet2.has(item.ctgrySeq1)) {
	            alert('2차 카테고리 전체 권한이 존재합니다.');
	        	return true;
	        } 
	    	
	        
	        var result = 0;
	        if(item.ctgrySeq2 > 0){
	        	result = item.ctgrySeq2;
	        }else{
	        	result = item.ctgrySeq1;
	        }
	        formData.push({name: 'authCtgrySeq', value: result});
	    }
		$.ajax({
			url : "${utcp.ctxPath}/admin/member/oprtRgsProc.ajax",
			type : "post",
			data : formData,
			cache : false,
			async : true,
			success : function(r) {
				if (r.result != 1) {
					$("#messageStr").html(r.msg);
					location.href = "#message";
				} else {
					location.href='oprtList.do';
				}
			}
		});
	}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form method="post" id="mngrRgsFrm">
			<input type="hidden" name="userId" value="${user.userId }" />
			<input type="hidden" name="orgCd" value="${org.orgCd }" />
			<input type="hidden" name="tel" id="tel" />
			<input type="hidden" name="mobile" id="mobile" />
			<input type="hidden" name="email" id="email" />
			<input type="hidden" name="userMemLvl" value="3" />
			<table class="w100 tb02 tc">
				<caption class="sound_only">관리자등록테이블</caption>
				<colgroup>
					<col style="width: 10%;">
					<col style="width: ;">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" colspan="" class="tdbg3 tc"><label for="userId">
								아이디 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl" colspan=""><c:choose>
								<c:when test="${empty user }">
									<input type="text" name="loginId" value="${user.loginId }" id="loginId" class="ip1">
								</c:when>
								<c:otherwise>
						${user.loginId }
						<input type="hidden" name="loginId" value="${user.loginId }" id="loginId" />
								</c:otherwise>
							</c:choose> 
							<!-- 
							<input type="radio" name="userMemLvl" value="3" />내부운영자 <input type="radio" name="userMemLvl" value="4" />외주운영자
							 -->
							</td>
					</tr>
					<tr>
						<th scope="row" colspan="" class="tdbg3 tc"><label for="userPw">
								비밀번호 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl"><input type="password" name="userPw" id="userPw" class="ip1" maxlength="20"/></td>
						
					</tr>
					<tr>
						<th scope="row" class="tdbg3 tc"><label for="userPw2">
								비밀번호 확인 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl"><input type="password" name="userPw2" id="userPw2" class="ip1" maxlength="20"></td>
					</tr>
					<tr>
						<th scope="row" colspan="" class="tdbg3 tc"><label for="userNm">
								성명 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl" colspan="">
							<input type="text" name="userNm" value="${user.userNm }" id="userNm" class="ip1"></td>
					</tr>
					
					<tr>
						<th scope="row" colspan="" class="tdbg3 tc"><label for="mobile1">
								휴대폰번호  
							</label></th>
						<td class="tl" colspan="">
						<select id="mobile1" name="mobile1" title="휴대폰번호 선택항목" required="">
								<option value="" selected>선택</option>
								<option value="010" >010</option>
								<option value="011">011</option>
								<option value="016">016</option>
								<option value="017">017</option>
								<option value="018">018</option>
								<option value="019">019</option>
						</select> - <input type="text" id="mobile2" name="" class="ip5 onlyNumber" maxlength="4" required=""> - <input type="text" id="mobile3" name="" class="ip5 onlyNumber" maxlength="4" required=""></td>
					</tr>
					
					<tr style="display:none;">
						<th colspan=""><label>교육운영권한 <span class="required">&nbsp;&nbsp;&nbsp;</span></label></th>
						<td class="tl">
							<div id="vm-eduAuth">
							    <div v-for="(item, index) in authList" :key="index">
							        <select v-model="item.ctgrySeq1" @change="updateSecondCategory(index)">
							            <option value="0">전체</option>
							            <option v-for="category in getSubCategories(0)" :value="category.ctgrySeq">{{ category.ctgryNm }}</option>
							        </select>
							        <select v-if="item.ctgrySeq1 > 0" v-model="item.ctgrySeq2">
							            <option value="0">전체</option>
							            <option v-for="subCategory in getSubCategories(item.ctgrySeq1)" :value="subCategory.ctgrySeq">{{ subCategory.ctgryNm }}</option>
							        </select>
							        <button type="button" class="btn04 btn_greenl" @click="deleteItem(index)">삭제</button>
							        <button v-if="index === authList.length - 1" type="button" class="btn04 btn_greenl" @click="addItem()">추가</button>
							    </div>
							</div>
							<script>
							var authObj = { ctgrySeq1: 0, ctgrySeq2: 0 };
							var vm_eduAuth = new Vue({
								el:'#vm-eduAuth',
								data:{
							        authList: [],
							        categories: [], // 카테고리 목록을 저장할 데이터 추가
							    },
								created: function() {
							        this.fetchData();
							    },
								methods:{
									fetchData: function() {
							            // 데이터 가져오기
							            $.ajax({
							                url: 'callAuthList.ajax',
							                data: { userId: '${userId}' },
							                success: function(r) {
							                	vm_eduAuth.categories = r.data.cateList;
							                	if(r.data.authList && r.data.authList[0].userId){
								                	vm_eduAuth.authList = r.data.authList.map(function(item) {
								                        var newItem = Object.assign({}, authObj, item);
								                        // ctgrySeq 값이 부모 카테고리인지 자식 카테고리인지 확인
								                        var isParentCategory = r.data.cateList.some(function(category) {
								                            return category.parentSeq === 0 && category.ctgrySeq === item.ctgrySeq;
								                        });
								                        // 부모 카테고리인 경우
								                        if (isParentCategory) {
								                            newItem.ctgrySeq1 = item.ctgrySeq; // 부모 카테고리 자신의 ctgrySeq를 할당
								                        } else {
								                            // 부모 카테고리가 아닌 경우, 해당 자식 카테고리의 부모 카테고리를 찾아 할당
								                            var parentCategory = r.data.cateList.find(function(category) {
								                                return category.ctgrySeq === item.ctgrySeq;
								                            });
								                            newItem.ctgrySeq1 = parentCategory.parentSeq;
								                            newItem.ctgrySeq2 = parentCategory.ctgrySeq;
								                        }
								                        return newItem;
								                    });
							                	}else{
							                		vm_eduAuth.authList.push(authObj);
							                	}
							                    
							                    
							                }
							            });
							        },
							        addItem: function(index){
										var tmpObj2 = $.extend(true, {}, authObj);//true를빼면 얕은복사
										this.authList.push(tmpObj2);
									},
									deleteItem:function(index){
										if(this.authList.length==1){
											alert('삭제 안됨');
											return;
										}
										this.authList.splice(index,1);
									},
									getSubCategories: function(parentSeq) {
								        return this.categories.filter(function(category) {
								            return category.parentSeq == parentSeq;
								        });
								    },
							        updateSecondCategory: function(index) {
							            var selectedFirstCategory = this.authList[index].ctgrySeq1;
							            if (selectedFirstCategory > 0) {
							               // this.authList[index].ctgry2Options = this.getSubCategories(selectedFirstCategory);
							                this.authList[index].ctgrySeq2 = 0;
							            } else {
							                //this.authList[index].ctgry2Options = [];
							                this.authList[index].ctgrySeq2 = 0;
							            }
							        }
								},
							});
							</script>
						</td>
					</tr>
					<tr>
						<th colspan="">상태</th>
						<td class="tl">
							<select name="state">
							<option value="A" ${user.state eq 'A' ? 'selected':'' }>승인</option>
							<option value="W" ${user.state eq 'W' ? 'selected':'' }>대기</option>
						</select>
						</td>
					</tr>
					<c:if test="${user.userMemLvl ne 1 }">
						<tr>
							<th class="tdbg3 tc" colspan="">메뉴권한</th>
							<td class="tl menu_td" colspan="">
								<%
								pageContext.setAttribute("baseMenuList", MenuUtil.getAdminBaseMenuList());
								%>
								<c:forEach items="${baseMenuList }" var="o" varStatus="s">
								<c:if test="${o.cd ne 'index' and o.st == 1 }">
								<label for="menu-${s.index }" class="cb font_16">
									<input type="checkbox" id="menu-${s.index }" name="menuIds" class="depth01" value="${o.cd }"/>${o.nm }
								</label>
								<c:if test="${empty o.sub }">
								<br/>
								</c:if>
								<c:if test="${not empty o.sub }">
								<div class="sub_menuIp mgb15">
									ㄴ
									<c:forEach items="${o.sub }" var="o2" varStatus="s2">
									<c:if test="${o2.st == 1 }">
									<label for="menu-${s.index }-${s2.index}">
										<input type="checkbox" class="m0100" id="menu-${s.index }-${s2.index}" name="menuIds" value="${o2.cd }" />
										${o2.nm }
									</label>
									</c:if>
									</c:forEach>
								</div> 
								</c:if>
								</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:if>
					<tr>
						<th class="tdbg3 tc" colspan=""><label for="wr_name">접속이력</label></th>
						<td class="tl" colspan="">
							<div class="accessList">
								<table width="100%" class="tb01 tc">
									<thead bgcolor="#f7f8fa">
										<tr>
											<th>NO</th>
											<th>접속일시</th>
											<th>접속 IP</th>
											<th>접근메뉴</th>
										</tr>
									</thead>
									<tbody class="accessList">
										<c:choose>
											<c:when test='${connLogList != null && fn:length(connLogList) > 0}'>
												<c:forEach var="log" items="${connLogList}" varStatus="stat">
													<tr>
														<td>${20 - stat.index}</td>
														<td><fmt:formatDate value="${log.connDe}" pattern="yyyy-MM-dd / HH:mm" /></td>
														<td>${log.ip}</td>
														<td>${log.menuNm}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="4" style="text-align: center; height: 80px;">접속이력이 없습니다.</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>

								</table>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="fl tc">
				<button class="btn02 btn_grayl" onclick="fn_list(); return false;" type="button">목록</button>
			</div>
			<div class="fr tc">
				<button class="btn01 btn_greenl" onclick="fn_rgs(); return false;" type="button">등록하기</button>
			</div>
		</form>
	</div>
</section>

<div class="remodal messagePop messagePop2" data-remodal-id="message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop1" data-remodal-id="success" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">등록되었습니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_list(); return false;" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>
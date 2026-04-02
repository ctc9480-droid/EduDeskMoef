<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-etc-iem">
<input type="radio" name="etcIemYn" value="N" id="etcIemN" @click="fn_use(2)"/><label for="etcIemN" class="pdl5 mgr15 cp">사용안함</label>
<input type="radio" name="etcIemYn" value="Y" id="etcIemY" @click="fn_use(1)"/><label for="etcIemY" class="pdl5 mgr15 cp">사용함</label>

<div v-if="isUse">
* 이름, 휴대폰번호 , 이메일, 소속, 직책 외에 추가로 받고 싶은 신청폼을 추가하세요
<br />
<div v-for="(o,i) in listEtc">
<select v-model="o.dataInputTy" class="vb">
	<option value="text">텍스트</option>
	<option value="radio">체크박스(단일 선택)</option>
	<option value="checkbox">체크박스(중복선택가능)</option>
	<option value="select">보기 선택</option>
</select>
<input v-model="o.etcIemNm" :id="'etcIemNm-'+i" type="text"  class="ip1 mgr10" maxlength="100" autocomplete="off" />
<input v-model="o.etcIemEx" :id="'etcIemEx-'+i" type="text"  class="ip1 mgr10" maxlength="1000" placeholder="선택1,선택2" autocomplete="off" />
<input v-model="o.essntlInputYn" v-bind:true-value="'Y'" v-bind:false-value="'N'" type="checkbox" />필수여부
<button @click="delEtcs(listEtc,i)" type="button" class="btn04 btn_orange" @click="addPlans(listEtc)">삭제</button>
<button @click="addEtcs(listEtc)" v-if="listEtc.length==(i+1)" type="button" class="btn04 btn_orange">추가</button>
</div>
</div>
</div>
<script>
//
$(document).ready(function(){
	$('input:radio[name=etcIemYn]:input[value=${not empty lctre.etcIemYn?lctre.etcIemYn:'N'}]').prop('checked',true);
	
	if($('input:radio[name=etcIemYn]:checked').val() == 'Y'){
		$('#vm-etc-iem').show();
	}
	
});

var init_iem_etc = {
		etcIemSeq : 1,
		dataInputTy : 'text',
		etcIemNm : '항목명',
		essntlInputYn : 'Y',
		useYn : 'Y',
	};
var vm_etc_iem = new Vue({
	el:'#vm-etc-iem',
	data:{
		listEtc : [init_iem_etc],
		openEtc : init_iem_etc,
		isUse: false,
	},
	mounted: function(){
		if('${lctre.etcIemYn}' == 'Y'){
			this.isUse = true;
		}
		if('${lctre.etcIemJson}' != ''){
			var _etcIemData = JSON.parse('${lctre.etcIemJson}');
			if(_etcIemData.etcIemList.length>0){
				this.listEtc = _etcIemData.etcIemList;
			}
		}
	},
	methods:{
		fn_use: function(o){
			if(o == 1){
				this.isUse = true;
			}else{
				this.isUse = false;
			}
		},
		openPlans:function(a){
			this.openPlan=a;
			location.href='#md-tm-plan';
		},
		delEtcs:function(plans,i){
			if(plans.length==1){
				alert('삭제 할 수 없습니다.');return;
			}
			plans.splice(i,1);
		},
		addEtcs:function(a){
			if(a.length==10){
				alert('10개까지만 생성 가능합니다.');
				return;
			}
			var obj = a[a.length-1];
			var obj2 = $.extend(true,{}, obj);
			obj2.etcIemSeq++;
			a.push(obj2);
		},
		
	},
});

</script>
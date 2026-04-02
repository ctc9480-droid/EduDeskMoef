<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-qstn-ch">
	<div class="mcqWrap">
		<div v-for="(o,i) in chList" class="mcqWrap">
			<div class="mgb5">
			<span>({{i+1}}).</span>
			<input type="text" v-model="o.chStr" class="ip2" />
			<input type="checkbox" class="" v-model="o.chAns" :id="'chAnsYn-'+i" value="1" />
			<label :for="'chAnsYn-'+i">정답</label>
			
			<button v-if="chList.length == (i+1)" type="button" @click="delCh(i)" class="delBtn btn04 btn_grayl vt">
				<i class="fas fa-trash-alt fc_red"></i>
			</button>
			</div>
			<div class="mgb5">
			    <template v-if="o.chFnRnm">
			    <img  width="100px" :src="'${utcp.ctxPath }/DATA/upload/web/quizBank/${param.qstnSeq}/'+o.chFnRnm"/>
			    <a href="#none" @click=delFile(o.chSeq,o.chFnRnm)>[삭제]</a>
			    </template>
			    <input type="file" :name="'chFn_'+i" class="frm_file ip1 mgl30">
			</div>
		</div>
		<button type="button" @click="addCh" class="btn04 btn_gray w50">
			<i class="fas fa-plus fl vm pdt10"></i> 보기 추가
		</button>
	</div>
</div>

<script>
var var_qstn_ch = {chStr: '',chAns: false};
var vm_qstn_ch = new Vue({
	el : '#vm-qstn-ch',
	data : {
		chList:[$.extend(true, {}, var_qstn_ch)],
	},
	mounted : function() {
        // 페이지가 로드될 때 AJAX 호출
        this.loadData();
    },
	methods : {
		loadData : function(){
			 $.ajax({
				 data : {qstnSeq: $('#i-qstnSeq').val()},
                 url: 'quizBankInfo.ajax',
                 method: 'GET',
                 success: function(r) {
                     // 받아온 데이터를 Vue 데이터에 할당
                     this.chList = r.data.chList;
                 }.bind(this), // 이 부분이 중요함. 함수 내에서 this가 Vue 인스턴스를 가리키도록 함
                 error: function(xhr, status, error) {
                     console.error(error);
                 }
             });
		},
		addCh : function(){
			var chObj = $.extend(true, {}, var_qstn_ch);
			this.chList.push(chObj);
		},
		delCh : function(i){
			this.chList.splice(i, 1);
		},
		delFile: function(chSeq,fnRnm){
			fn_delQstnFile(chSeq,fnRnm);
		},
	},
});
</script>
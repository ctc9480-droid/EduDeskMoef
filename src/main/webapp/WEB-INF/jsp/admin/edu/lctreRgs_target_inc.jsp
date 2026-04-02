<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<div id="vm-edu-target">
	 <input type="checkbox" v-model="targetArr" name="targetArr" value="1" id="vm-targetArr1"/> <label for="vm-targetArr1">재정경제부</label>
	<input type="checkbox" v-model="targetArr" name="targetArr" value="2" id="vm-targetArr2"/> <label for="vm-targetArr2">기획예산처</label>
	<input type="checkbox" v-model="targetArr" name="targetArr" value="5" id="vm-targetArr5"/> <label for="vm-targetArr5">중앙행정기관</label>
	<input type="checkbox" v-model="targetArr" name="targetArr" value="3" id="vm-targetArr3"/> <label for="vm-targetArr3">지자체</label>
	<input type="checkbox" v-model="targetArr" name="targetArr" value="4" id="vm-targetArr4"/> <label for="vm-targetArr4">공공기관</label>
</div>

<script>
var targetArr = [];

var targets = '${lctre.targets}';
if(targets != ''){
	targetArr = targets.split(',');
}

var vm_tmTarget = new Vue({
	el:'#vm-edu-target',
	data:{
		targetArr:targetArr,
	},
	methods: {
	    
	},
});


</script>
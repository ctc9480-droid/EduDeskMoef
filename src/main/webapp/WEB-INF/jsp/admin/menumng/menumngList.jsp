<%@page import="com.educare.component.VarComponent"%>
<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<style>
    ul {
      list-style: none;
      padding-left: 20px;
    }
    .node {
      margin: 4px 0;
    }
    .actions {
      margin-left: 10px;
    }
    button {
      margin-left: 4px;
    }
  </style>

<section class="pd025 pd200 po_re" id="vm-menumng">
	<div class="po_re br5 bs_box" style="float:left;width:200px;">
	<h2>메뉴 관리</h2>
    <ul>
      <tree-node
        v-for="(node, index) in tree"
        :key="index"
        :node="node"
        @delete-node="deleteNode(tree, $event)"
      ></tree-node>
    </ul>
    <button @click="addRoot">최상위 메뉴 추가</button>

	</div>
	<div class="po_re br5 bs_box" style="float:left; width:calc(100% - 300px); margin-left: 100px;">
		수정영역
		
	</div>
</section>

<script>
Vue.component('tree-node', {
  props: ['node'],
  template: `
    <li class="node">
      <span>{{ node.name }}</span>
      <span class="actions">
        <button @click="addChild">하위 추가</button>
        <button @click="deleteSelf">삭제</button>
      </span>
      <ul v-if="node.children.length">
        <tree-node
          v-for="(child, idx) in node.children"
          :key="idx"
          :node="child"
          @delete-node="deleteNode(node.children, $event)"
        ></tree-node>
      </ul>
    </li>
  `,
  methods: {
    addChild() {
      const name = prompt('하위 메뉴 이름을 입력하세요:')
      if (name) {
        this.node.children.push({ name, children: [] })
      }
    },
    deleteSelf() {
      this.$emit('delete-node', this.node)
    },
    deleteNode(list, target) {
      const index = list.indexOf(target)
      if (index !== -1) {
        list.splice(index, 1)
      }
    }
  }
})

new Vue({
  el: '#vm-menumng',
  data: {
    tree: [
      { name: '메뉴1', children: [] },
      { name: '메뉴2', children: [] }
    ]
  },
  methods: {
    addRoot() {
      const name = prompt('새 최상위 메뉴 이름을 입력하세요:')
      if (name) {
        this.tree.push({ name, children: [] })
      }
    },
    deleteNode(list, target) {
      const index = list.indexOf(target)
      if (index !== -1) {
        list.splice(index, 1)
      }
    }
  }
})
</script>
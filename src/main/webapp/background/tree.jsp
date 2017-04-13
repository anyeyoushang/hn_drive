<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
#backTree{
    -webkit-user-select:none;
    -moz-user-select:none;
    -ms-user-select:none;
    user-select:none;
}
</style>
<script type="text/javascript">
$(function(){
	$('#backTree').tree({
		url :'${ctx}/sys/tree/treeDate',
		lines:'true',
		onClick:function(node){
			var url;
			url = '${pageContext.request.contextPath}' + '/background/datagrid/' + node.attributes.url;
			addTab({
				title : node.text,
				closable : true,
				iconCls : node.iconCls,
				href : url
			});
		},
		onDblClick:function(node){
			if(node.state == 'closed'){
				$(this).tree('expand', node.target);
			}else{
				$(this).tree('collapse', node.target);
			}
		}
	});
	
	// 禁用右键
	$('#backTree').tree({
		onContextMenu:function(e,node){
			e.preventDefault();
		}
	});
});	
</script>
<ul id="backTree"></ul>




<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var sjwz_className = 'model.dao.DriverPosition';
	var sjwz_datagrid;
	var sjwz_editRow = undefined; //定义全局变量：当前编辑的行
	$(function() {
		sjwz_datagrid = $('#sjwz_datagrid').datagrid({
			url : '${ctx}/sys/position/showDataList',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [10, 20, 30, 40, 50],
			fit : true,
			fitColumns : true,
			idField : 'positionId',// 翻页会选中其他列选中的数据 
			// 初始化
			sortName : 'positionTime',
			sortOrder : 'desc',
			columns : [[
			{
				field:'positionId', 
				checkbox:true
			},{
				title:'司机姓名', 
				field:'username', 
				width:100,
				align:'center',
			},{
				title:'手机号码', 
				field:'userphone', 
				width:100,
				align:'center',
			},{
				title:'司机位置', 
				field:'address',
				width:100,
				align:'center',
			},{
				title:'时间', 
				field:'positionTime',
				width:150,
				align:'center',
				sortable : true
			}]],
			// 数据加载之前
			onBeforeLoad:function(param){
				param['className'] = sjwz_className;
				return true;
			}
		});
	});
	
	// 添加数据
	function sjwz_appendFun() {
		// 取消选中
		sjwz_datagrid.datagrid('uncheckAll').datagrid('clearSelections');
		$('#sjwz_add').dialog({
		    title: '添加用户',
		    width: 300,
		    height: 200,
		    closed: false,
		    cache: false,
		    modal: true,
		    buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#admin_yhglAdd_addForm').form('submit', {
						url : '${ctx}/userController/add.action',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_sjwz_datagrid').datagrid('insertRow', {
										index : 0,
										row : r.obj
									});
									d.dialog('destroy');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							} catch (e) {
								$.messager.alert('提示', result);
							}
						}
					});
				}
			}],
			/* onClose : function() {
				$(this).dialog('destroy');
			} */
		}); 
	}
	
	// 删除数据
	function sjwz_del(){
		var arr = sjwz_datagrid.datagrid('getSelections');
		if(arr.length == 0){
			$.messager.alert('提示', '您还没有选择数据!', 'info');
			return;
		}
		$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
			if (r){
				$.post('${ctx}/sys/user/deleteUser', {"arr":arr}, function(data){
					console.log(data);
					$.messager.alert('提示', data.message, 'info');
					if(data.success == true){// 删除成功
						datagrid.datagrid('load', {});
						datagrid.datagrid('clearSelections');
					}
				});
			}
		});
	}
	
	// 清空搜索内容
	function sjwz_clear(){
		$('#sjwz_searchForm').form('clear');
		sjwz_datagrid.datagrid('load', {});
	}
	
	// 搜索数据
	function sjwz_search(){
		sjwz_datagrid.datagrid('load',serializeObject($('#sjwz_searchForm').form()));
	}
</script>
<style type="text/css">
	*{
		font-family:微软雅黑;
	}
</style>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',title:'搜索数据',split:false,border:false,collapsed:false" style="height:94px;" class="datagrid-toolbar">
		<form id="sjwz_searchForm" method="post">
			<table style="margin-top:4px;">
				<tr align="right">
					<th>手机号:</th>
					<td><input class="easyui-numberbox" type="text" name="userPhone&%lk%" data-options="min:1,"/></td>
					
					<td><a id="btn" href="javascript:sjwz_search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a></td>
					<td><a id="btn" href="javascript:sjwz_clear();" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',split:true,border:false">
		<table id="sjwz_datagrid"></table>
	</div>
</div>

<div id="sjwz_add">
	<table style="margin-top:4px;">
		<tr align="right">
			<th>手机号:</th>
			<td><input type="text" class="easyui-textbox" name="userPhone" /></td>
		</tr>
		<tr align="right">
			<th>用户名:</th>
			<td><input class="easyui-numberbox" type="text" name="userName" data-options="min:1,"/></td>
		</tr>
		<tr align="right">
			<th>身份证号码:</th>
			<td><input type="text" class="easyui-textbox" name="IDcard" /></td>
		</tr>
	</table>
</div> 



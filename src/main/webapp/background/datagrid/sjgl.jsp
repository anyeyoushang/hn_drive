<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var sjgl_className = 'model.dao.User';
	var sjgl_datagrid;
	var sjgl_editRow = undefined; //定义全局变量：当前编辑的行
	$(function() {
		sjgl_datagrid = $('#sjgl_datagrid').datagrid({
			url : '${ctx}/showDataList',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [10, 20, 30, 40, 50],
			fit : true,
			fitColumns : true,
			idField : 'userId',// 翻页会选中其他列选中的数据 
			// 初始化
			sortName : 'userId',
			sortOrder : 'asc',
			columns : [[
			{
				field:'userId', 
				checkbox:true
			},{// 一定要给宽
				title:'用户名', 
				field:'userName', 
				width:100,
				align:'center',
				editor: { type: 'validatebox', options: { required: true} }
			},{
				title:'手机号码', 
				field:'userPhone', 
				width:100,
				align:'center',
				editor: { type: 'validatebox', options: { required: true} }
			},{
				title:'用户角色', 
				field:'userRole', 
				width:100,
				align:'center',
				formatter : function(value, row, index) {
					if(value == 1){
						return '用户';
					}else if(value == 2){
						return '司机';
					}
				}
			},{
				title:'头像',
				field:'headImg', 
				width:100,
				align:'center',
				formatter : function(value, row, index) {
					if(value == null){
						return '<img style="width:30px;height:30px;" src="${ctx}/static/images/logo.png"/>';
					}else{
						var headImg = '<img style="width:30px;height:30px;" src="/' + value + '"/>';
						return headImg;
					}
				}
			},{
				title:'身份证',
				field:'IDcard', 
				width:200,
				align:'center',
				editor: { type: 'text', options: { required: true} }
			},{
				title:'用户余额', 
				field:'userMoney',
				width:120,
				align:'center',
			},{
				title:'接单状态', 
				field:'AcceptState',
				width:120,
				align:'center',
				formatter : function(value, row, index) {
					if(value == 0){
						return '已开启';
					}else{
						return '未开启';
					}
				}
			},{
				title:'注册时间', 
				field:'addTime',
				width:150,
				align:'center',
				sortable : true
			}]],
			toolbar:[{
				text:'添加数据',
				iconCls:'icon-add',
				handler:function(){
					sjgl_appendFun();
				}
			},'-',{
				text:'删除数据',
				iconCls:'icon-remove',
				handler:function(){
					sjgl_del();
				}
			},'-',{ text: '提交修改', iconCls: 'icon-save', handler: function () {
                //保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
                sjgl_datagrid.datagrid("endEdit", sjgl_editRow);
            }
            }, '-',
            { text: '取消编辑', iconCls: 'icon-redo', handler: function () {
                //取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
                sjgl_editRow = undefined;
                sjgl_datagrid.datagrid("rejectChanges");
                sjgl_datagrid.datagrid("unselectAll");
            }
            }],
            onDblClickRow: function (rowIndex, rowData) {
            	console.log(rowIndex);
            	console.log(rowData);
            	//双击开启编辑行
                if (sjgl_editRow != undefined) {
                	// 取消当前行的选中
                    sjgl_editRow = undefined;
                    sjgl_datagrid.datagrid("rejectChanges");
                    sjgl_datagrid.datagrid("unselectAll");
                }
                if (sjgl_editRow == undefined) {
                	console.log('开始编辑');
                	sjgl_datagrid.datagrid("beginEdit", rowIndex);
                    sjgl_editRow = rowIndex;
                }
            },
            onAfterEdit: function (rowIndex, rowData, changes) {
                // endEdit该方法触发此事件,修改数据库
                $.post('${ctx}/sys/user/updateUser', rowData, function(data){
					$.messager.alert('提示', data.message, 'info');
				});
                sjgl_editRow = undefined;
            },
			// 数据加载之前
			onBeforeLoad:function(param){
				param['className'] = sjgl_className;
				param['userRole&='] = 2;
				return true;
			}
		});
	});
	
	// 添加数据
	function sjgl_appendFun() {
		// 取消选中
		sjgl_datagrid.datagrid('uncheckAll').datagrid('clearSelections');
		$('#sjgl_add').dialog({
		    title: '添加司机',
		    width: 300,
		    height: 180,
		    closed: false,
		    cache: false,
		    modal: true,
		    buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					$.post('${ctx}/sys/user/addDrive', serializeObject($('#sjgl_addForm')), function(data){
						if(data.success){
							$('#sjgl_addForm').form('clear');
							sjgl_datagrid.datagrid('load', {});
							sjgl_datagrid.datagrid('clearSelections');
							$('#sjgl_add').dialog('close');
							$.messager.show({
								title : '提示',
								msg : data.message,
								timeout:2000
							});
						}else{
							$.messager.alert('提示',data.message,'info');
						}
					});
				}
			}]
		}); 
	}
	
	// 删除数据
	function sjgl_del(){
		var arr = sjgl_datagrid.datagrid('getSelections');
		if(arr.length == 0){
			$.messager.alert('提示', '您还没有选择数据!', 'info');
			return;
		}
		var arr = sjgl_datagrid.datagrid('getSelections');
		var ids = '';
		for(var i = 0; i < arr.length; i++){
			ids += arr[i].userId + ',';
		}
		$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
			if (r){
				$.post('${ctx}/sys/user/deleteUser', {"ids":ids}, function(data){
					$.messager.alert('提示', data.message, 'info');
					if(data.success == true){// 删除成功
						sjgl_datagrid.datagrid('load', {});
						sjgl_datagrid.datagrid('clearSelections');
					}
				});
			}
		});
	}
	
	// 清空搜索内容
	function sjgl_clear(){
		$('#sjgl_searchForm').form('clear');
		sjgl_datagrid.datagrid('load', {});
	}
	
	// 搜索数据
	function sjgl_search(){
		sjgl_datagrid.datagrid('load',serializeObject($('#sjgl_searchForm').form()));
	}
</script>
<style type="text/css">
	*{
		font-family:微软雅黑;
	}
</style>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',title:'搜索数据',split:false,border:false,collapsed:false" style="height:94px;" class="datagrid-toolbar">
		<form id="sjgl_searchForm" method="post">
			<table style="margin-top:4px;">
				<tr align="right">
					<th>姓名:</th>
					<td><input type="text" class="easyui-textbox" name="userName&%lk%" /></td>
					<th>手机号:</th>
					<td><input class="easyui-numberbox" type="text" name="userPhone&%lk%" data-options="min:1,"/></td>
				</tr>
				<tr align="right">
					<th>注册时间:</th>
					<td><input type="text" class="easyui-datebox" editable="false" name="addTime&>=" /></td>
					<th>到:</th>
					<td><input type="text" class="easyui-datebox" editable="false" name="addTime&<=" /></td>
					
					<td><a id="btn" href="javascript:sjgl_search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a></td>
					<td><a id="btn" href="javascript:sjgl_clear();" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',split:true,border:false">
		<table id="sjgl_datagrid"></table>
	</div>
</div>

<div id="sjgl_add">
	<form id="sjgl_addForm" action="post">
		<table style="margin-top:4px;">
			<tr align="right">
				<th>用户名:</th>
				<td><input class="easyui-textbox" type="text" name="userName" data-options="required:'true',missingMessage:'请填写用户名'"/></td>
			</tr>
			<tr align="right">
				<th>手机号:</th>
				<td><input type="text" class="easyui-numberbox" name="userPhone" data-options="min:1,required:'true',missingMessage:'请填写手机号'"/></td>
			</tr>
			<tr align="right">
				<th>身份证:</th>
				<td><input type="text" class="easyui-textbox" name="IDcard" data-options="required:'true',missingMessage:'请填写身份证号码'" /></td>
			</tr>
		</table>
	</form>
</div>



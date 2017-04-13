<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>后台管理系统</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/jquery-easyui-1.4.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" href="${ctx}/static/tool/self.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/tool/syUtils.js"></script>
<script type="text/javascript">
	var ctx = '${pageContext.request.contextPath}';
	var datagrid;
	var editRow = undefined; //定义全局变量：当前编辑的行
	$(function() {
		datagrid = $('#datagrid').datagrid({
			url : ctx + '/showDataList',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 15,
			pageList : [15, 20, 30, 40, 50],
			fit : true,
			fitColumns : true,
			idField : 'incomeId',// 翻页会选中其他列选中的数据 
			// 初始化
			sortName : 'animalIncomeTime',
			sortOrder : 'asc',
			columns : [[
			{
				field:'incomeId', 
				checkbox:true
			},{// 一定要给宽
				title:'手机号', 
				field:'userPhone', 
				width:100,
				align:'center',
			},{
				title:'微信号', 
				field:'weixinId', 
				width:100,
				align:'center',
			},{
				title:'银行卡号', 
				field:'bankId', 
				width:100,
				align:'center',
			},{
				title:'收益份数', 
				field:'animalIncomeNum', 
				width:100,
				align:'center',
			},{
				title : '收益积分',
				field : 'animalIncomeCount',
				width : 70,
				align : 'center',
			},{
				title:'收益时间',
				field:'animalIncomeTime', 
				width:100,
				align:'center',
			}]],
			toolbar:[],
			onAfterEdit: function (rowIndex, rowData, changes) {
                //endEdit该方法触发此事件
                console.info(rowData);
                // 修改数据库
				$.post(ctx + '/mobile/user/confirmCash', rowData, function(data){
					$.messager.alert('提示', '打款成功!', 'info');
				});
                editRow = undefined;
            },
            onDblClickRow: function (rowIndex, rowData) {
            	//双击开启编辑行
                if (editRow != undefined) {
                    datagrid.datagrid("endEdit", editRow);
                }
                if (editRow == undefined) {
                    datagrid.datagrid("beginEdit", rowIndex);
                    editRow = rowIndex;
                }
            },
			// 数据加载之前
			onBeforeLoad:function(param){
				param['className'] = 'AnimalService';
				return true;
			}
		});
	});
	
	
	// 添加数据
	function add(){
		var arr = datagrid.datagrid('getSelections');
		if(arr.length == 0){
			$.messager.alert('提示', '您还没有选择数据!', 'info');
			return;
		}else if(arr.length > 1){
			$.messager.alert('提示', '请只选择一条数据!', 'info');
			return;
		}
		var obj = {};
		var userId = arr[0].userId;
		obj['userId'] = userId;
		var addDialog;
		addDialog = $('#add').dialog({
			title: '余额充值',
			width: 306,
			height: 150,
			collapsible: true,
		    resizable: true,
		    closed: false,
		    cache: false,    
		    modal: true,
		    buttons:[{
				text:'重置',
				handler:function(){
					$('#addForm').form('reset');
				}
			},{
				text:'充值',
				handler:function(){
					obj['userMoney'] = $('#userMoney').val();
					$.post(ctx + '/sys/user/addUserMoney', obj, function(data){
						if(data.success){
				    		$.messager.show({
					    		title:'系统消息',
					    		msg:data.message,
					    		timeout:2000,
					    		showType:'slide'
					    	});
				    		$('#addForm').form('reset');
				    		addDialog.dialog('close');
					    	datagrid.datagrid('load');
				    	}else{
				    		$.messager.alert('提示','添加失败!', 'info');
				    	}
					});
				}
			}]
		});
	}
	
	// 删除数据
	function del(){
		var arr = datagrid.datagrid('getSelections');
		if(arr.length == 0){
			$.messager.alert('提示', '您还没有选择数据!', 'info');
			return;
		}
		if(arr.length > 1){
			$.messager.alert('提示', '一次只能选择一条数据!', 'info');
			return;
		}
		$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
			if (r){
				$.post(ctx + '/sys/user/deleteUser', {"arr":arr[0].userId}, function(data){
					console.log(data);
					$.messager.alert('提示', data.message, 'info');
					if(data.success == true){// 删除成功
						datagrid.datagrid('load', {});
					}
				});
			}
		});
	}
	
	// 清空搜索内容
	function clear(){
		$('#searchForm').form('clear');
		datagrid.datagrid('load', {});
	}
	
	// 搜索数据
	function search(){
		datagrid.datagrid('load', serializeObject($('#searchForm').form()));
	}
	
</script>
<style type="text/css">
	*{
		font-family:微软雅黑;
	}
</style>
</head>
<body class="easyui-layout" oncontextmenu="return false">
	<!-- 显示数据部分 -->
 	<div data-options="region:'center',split:true,border:false">
 		<%-- datagrid表格 --%>
		<table id="datagrid"></table>
 	</div>
 	<!-- 添加数据的dialog -->
	<div id="add" style="display:none;">
	<!--  enctype="multipart/form-data" -->
		<form id="addForm" method="post" style="width:230px;margin:0 auto;">
			<table>
				<tr>
	    			<td></td>
	    			<td>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>金额:</td>
	    			<td>
	    				<input id="userMoney" class="easyui-textbox" type="text" name="name" data-options="required:false" style="width:150px;"/>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
</body>
</html>
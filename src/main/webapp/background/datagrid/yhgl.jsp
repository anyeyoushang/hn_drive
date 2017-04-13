<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var yhgl_className = 'model.dao.User';
	var yhgl_datagrid;
	var yhgl_editRow = undefined; //定义全局变量：当前编辑的行
	$(function() {
		yhgl_datagrid = $('#yhgl_datagrid').datagrid({
			//需要把这个回调函数放在datagrid的初始化方法里
			/* onLoadSuccess : function(){
			  //解决一个样式bug
			  $(".borderdiv").remove();
			  var height = $(".datagrid-view2 .datagrid-body").outerHeight() - $(".datagrid-view2 .datagrid-btable").outerHeight();
			  if(height > 0){
			    $(".datagrid-view2 .datagrid-body").css("position", "relative").append("<div class='borderdiv'></div>");
			    $(".borderdiv").css({
			      height : height,
			      borderLeft : "1px solid #ccc",
			      position : "absolute",
			      right : "18px"
			    });
			  }
			}, */
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
				title:'注册时间', 
				field:'addTime',
				width:150,
				align:'center',
				sortable : true
			}]],
			toolbar:[
         	/* {
				text:'添加数据',
				iconCls:'icon-add',
				handler:function(){
					yhgl_appendFun();
				}
			},'-', */{
				text:'删除数据',
				iconCls:'icon-remove',
				handler:function(){
					yhgl_del();
				}
			},'-',{ text: '提交修改', iconCls: 'icon-save', handler: function () {
                //保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
                yhgl_datagrid.datagrid("endEdit", yhgl_editRow);
            }
            }, '-',
            { text: '取消编辑', iconCls: 'icon-redo', handler: function () {
                //取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
                yhgl_editRow = undefined;
                yhgl_datagrid.datagrid("rejectChanges");
                yhgl_datagrid.datagrid("unselectAll");
            }
            }],
            onDblClickRow: function (rowIndex, rowData) {
            	//双击开启编辑行
                if (yhgl_editRow != undefined) {
                	// 取消当前行的选中
                    yhgl_editRow = undefined;
                    yhgl_datagrid.datagrid("rejectChanges");
                    yhgl_datagrid.datagrid("unselectAll");
                }
                if (yhgl_editRow == undefined) {
                	yhgl_datagrid.datagrid("beginEdit", rowIndex);
                    yhgl_editRow = rowIndex;
                }
            },
            onAfterEdit: function (rowIndex, rowData, changes) {
                // endEdit该方法触发此事件,修改数据库
                $.post('${ctx}/sys/user/updateUser', rowData, function(data){
					$.messager.alert('提示', data.message, 'info');
				});
                yhgl_editRow = undefined;
            },
			// 数据加载之前
			onBeforeLoad:function(param){
				param['className'] = yhgl_className;
				param['userRole&='] = 1;
				return true;
			}
		});
	});
	
	// 添加数据
	function yhgl_appendFun() {
		// 取消选中
		yhgl_datagrid.datagrid('uncheckAll').datagrid('clearSelections');
		$('#user_add').dialog({
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
					alert(1);
					var d = $(this).closest('.window-body');
					$('#admin_yhglAdd_addForm').form('submit', {
						url : '${pageContext.request.contextPath}/userController/add.action',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.success) {
									$('#admin_yhgl_datagrid').datagrid('insertRow', {
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
					$.post('${ctx}/sys/user/addUserMoney', obj, function(data){
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
	function yhgl_del(){
		var arr = yhgl_datagrid.datagrid('getSelections');
		var ids = '';
		for(var i = 0; i < arr.length; i++){
			ids += arr[i].userId + ',';
		}
		$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
			if (r){
				$.post('${ctx}/sys/user/deleteUser', {"ids":ids}, function(data){
					$.messager.alert('提示', data.message, 'info');
					if(data.success == true){// 删除成功
						yhgl_datagrid.datagrid('load', {});
						yhgl_datagrid.datagrid('clearSelections');
					}
				});
			}
		});
	}
	
	// 清空搜索内容
	function yhgl_clear(){
		$('#yhgl_searchForm').form('clear');
		yhgl_datagrid.datagrid('load', {});
	}
	
	// 搜索数据
	function yhgl_search(){
		yhgl_datagrid.datagrid('load',serializeObject($('#yhgl_searchForm').form()));
	}
</script>
<style type="text/css">
	*{
		font-family:微软雅黑;
	}
</style>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',title:'搜索数据',split:false,border:false,collapsed:false" style="height:94px;" class="datagrid-toolbar">
		<div style="float:left;">
			<form id="yhgl_searchForm" method="post">
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
						
						<td><a id="btn" href="javascript:yhgl_search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a></td>
						<td><a id="btn" href="javascript:yhgl_clear();" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">重置</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div>
			<h1 style="color:red;">今日新增用户:20</h1>
		</div>
	</div>
	
	
	<div data-options="region:'center',split:true,border:false">
		<table id="yhgl_datagrid"></table>
	</div>
</div>

<div id="user_add">
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



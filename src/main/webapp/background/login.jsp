<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/base.jsp"/>
<script type="text/javascript">
	$(function(){
		$('#loginFrame').dialog({
			title:'登陆',
			width:280,
			height:180,
			closable:false,
			modal:true,
			resizable:true,
			buttons:[{
				text:'重置',
				handler:function(){
					$('#loginForm').form('reset');
				}
			},{
				text:'登陆',
				handler:function(){
					var userName = $('#userName').val();
					var passWord = $('#passWord').val();
					$.post('${ctx}/sys/user/backLogin', {'userName':userName, 'passWord':passWord}, function(data){
						if(data.success){
							$('#loginFrame').dialog('close');
							$.messager.show({
								title:'系统消息',
								msg:'登陆成功!',
								timeout:3000
							});
						}else{
							$.messager.alert('提示','您的用户名或密码不正确!', 'info');
						}
					});
				}
			}]
		});
	});
</script>
<div id="loginFrame">
	<form id="loginForm" method="post">
		<table>
    		<tr>
    			<td>用户名:</td><!-- value="admin" -->
    			<td><input id="userName" class="easyui-textbox" type="text" name="name" data-options="required:true"/></td>
    		</tr>
    		<tr>
    			<td>密码:</td>
    			<td><input id="passWord" class="easyui-textbox" type="password" name="password" data-options="required:true"/></td>
    		</tr>
    	</table>
    </form>
</div>
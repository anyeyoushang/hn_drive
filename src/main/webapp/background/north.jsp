<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	function logoutFun() {
		$.messager.confirm('确认对话框', '您想要退出该系统吗？', function(r){
			if (r){
				$.getJSON('${ctx}/sys/user/quitLogin', function(result) {
					location.replace('${ctx}/background/backLogin.jsp');
				});
			}
		});
	}
	
	// 显示管理员信息
	function userInfoFun() {
		$('#modify_admin_info').dialog({
			width : 300,
			height : 220,
			modal : true,
			title : '管理员信息',
			buttons : [ {
				text : '修改密码',
				iconCls : 'icon-edit',
				handler : function() {
					$('#user_userInfo_form').form('submit', {
						url : '${ctx}/sys/user/modifyAdminInfo',
						success : function(result) {
							console.log(result);
							try {
								var r = $.parseJSON(result);
								console.log(r);
								if (r.success) {
									$('#modify_admin_info').dialog('close');
								}
								$.messager.show({
									title : '提示',
									msg : r.message
								});
							} catch (e) {
								$.messager.alert('提示', result);
							}
						}
					});
				}
			} ],
		});
	}
</script>
<div id="sessionInfoDiv" style="position: absolute;right: 5px;top:10px;">
	<%-- ${adminInfo.id}${adminInfo.userName} --%>
	<c:if test="${adminInfo.id != null}">
		[<strong>${adminInfo.userName}</strong>]，欢迎你！
	</c:if>
</div>
<div style="position: absolute; right: 0px; bottom: 0px; ">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
	<button onclick="logoutFun();" class="easyui-menubutton" data-options="iconCls:'icon-back'">注销</button>  
	<!-- <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'icon-back'">注销</a> -->
</div>
<!-- <div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div onclick="logoutFun();">锁定窗口</div>
	<div class="menu-sep"></div>
	<div onclick="logoutFun();">重新登录</div>
	<div onclick="logoutFun(true);">退出系统</div>
</div> -->
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="changeTheme('default');">default</div>
	<div onclick="changeTheme('ui-sunny');">ui-sunny</div>
	<div onclick="changeTheme('black');">black</div>
	<div onclick="changeTheme('bootstrap');">bootstrap</div>
	<div onclick="changeTheme('gray');">gray</div>
	<div onclick="changeTheme('metro');">metro</div>
	<div onclick="changeTheme('ui-cupertino');">ui-cupertino</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div onclick="userInfoFun();">个人信息</div>
	<div class="menu-sep"></div>
	<div>
		<span>更换主题</span>
		<div style="width: 120px;">
			<div onclick="changeTheme('default');">default</div>
			<div onclick="changeTheme('ui-sunny');">ui-sunny</div>
			<div onclick="changeTheme('black');">black</div>
			<div onclick="changeTheme('bootstrap');">bootstrap</div>
			<div onclick="changeTheme('gray');">gray</div>
			<div onclick="changeTheme('metro');">metro</div>
			<div onclick="changeTheme('ui-cupertino');">ui-cupertino</div>
		</div>
	</div>
</div>

<div id="modify_admin_info" style="display: none;padding-top:10px;" align="center">
	<form id="user_userInfo_form" method="post">
		<input name="id" type="hidden" value="${adminInfo.id}" />
		<table class="tableForm">
			<tr>
				<th style="width: 55px;">登录名</th>
				<td><input name="userName" value="${adminInfo.userName}" /></td>
			</tr>
			<tr>
				<th>修改密码</th>
				<td><input name="password" type="password" class="easyui-validatebox" data-options="required:'true',missingMessage:'请填写登录密码'" /></td>
			</tr>
			<tr>
				<th>所属角色</th>
				<td><input readonly="readonly" value="系统管理员" /></td>
			</tr>
		</table>
	</form>
</div>
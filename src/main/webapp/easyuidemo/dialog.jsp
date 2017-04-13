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
	<jsp:include page="/base.jsp"/>
	<link rel="stylesheet" type="text/css"
		href="${ctx}/static/jquery-easyui-1.4.5/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css"
		href="${ctx}/static/jquery-easyui-1.4.5/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/static/tool/syUtils.js"></script>

<script type="text/javascript">
	$(function(){
		$('#cc').dialog({
		    title: 'My Dialog',    
		    width: 400,
		    height: 200,
		    closed: false,
		    cache: false,
		    href: 'uploadTest.jsp',
		    modal: true,
		    toolbar:[{
				text:'编辑',
				iconCls:'icon-edit',
				handler:function(){alert('edit')}
			},{
				text:'帮助',
				iconCls:'icon-help',
				handler:function(){alert('help')}
			}],
			buttons:[{
				text:'保存',
				handler:function(){alert('save')}
			},{
				text:'关闭',
				handler:function(){
					alert('close')
					$('#cc').window('close');
				}
			}]
		});
	});
	
	
	
</script>

</head>
<body>
	<div id="dd" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:false,modal:true">   
    	Dialog Content.    
	</div>
	
	<div id="cc">Dialog Content.</div>
</body>
</html>

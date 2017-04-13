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
		$('#pp').progressbar({ 
		value: 60 
		});
	});
	
	function progress(){
		var win = $.messager.progress({
		title:'Please waiting',
		msg:'Loading data...',
		text:''
		});
		setTimeout(function(){
		$.messager.progress('close');
		},5000)
	}
	
</script>

</head>
<body>
	<div id="p" class="easyui-progressbar" data-options="value:70" style="width:400px;"></div>
	
	<div id="pp" style="width:400px;"></div>
	
	<button class="easyui-linkbutton" onclick="progress()">Progress</button>
	
	<div id="rr" class="easyui-resizable" data-options="maxWidth:800,maxHeight:600,handles='s'" style="width:100px;height:100px;border:1px solid #ccc;"></div>
</body>
</html>

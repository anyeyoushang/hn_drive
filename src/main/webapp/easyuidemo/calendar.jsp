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
		$('#dd').calendar({
		    current:new Date(),
		    formatter: function(date){
	    		return date.getDate();
	    	}
		});
	});
</script>

</head>
<body>
	<div id="cc" class="easyui-calendar" style="width:180px;height:180px;"></div>
	<div style="height:10px;"></div>
	<div id="dd" style="width:180px;height:180px;"></div>
	
	<input type="text" class="easyui-datebox" editable="false" name="startTime" />
</body>
</html>

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
		$('#dd').combogrid({    
		    panelWidth:450,    
		    value:'006',
		    idField:'id',
		    textField:'text',
		    url:'${ctx }/user/combobox',    
		    columns:[[
		        {field:'id',title:'Code',width:60,sortable:true},    
		        {field:'text',title:'Name',width:100,sortable:true},    
		        {field:'addr',title:'Address',width:120,sortable:true},    
		        {field:'col4',title:'Col41',width:100,sortable:true}
		    ]]
		});
	});
	
	function fn1(){
		$('#dd').combogrid('clear');
	}

</script>

</head>
<body>
	<select id="cc" class="easyui-combogrid" name="dept" style="width:250px;"   
	data-options="    
	    panelWidth:450,    
	    value:'006',    
	    idField:'code',    
	    textField:'name',    
	    url:'${ctx }/user/combobox',
	    columns:[[
	        {field:'id',title:'Code',width:60},    
	        {field:'text',title:'Name',width:100},    
	        {field:'addr',title:'Address',width:120},    
	        {field:'col4',title:'Col41',width:100}    
	    ]]
	">
	</select>
	
	<input id="dd" name="dept" value="01" />
	
	<button onclick="fn1()">aa</button>
</body>
</html>

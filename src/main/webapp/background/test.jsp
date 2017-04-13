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
<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/tool/syUtils.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<div id="tabs" class="easyui-tabs" fit="true" data-options="tools:'#tab-tools',border:false">
  　　 <div title="Tab1" style="border:2px solid red;width:100%;height:100%;">  
         tab1
      </div>  
      <div title="Tab2" data-options="closable:true">  
         tab2   
      </div>  
      <div title="Tab3" data-options="iconCls:'icon-reload',closable:true">  
         tab3   
      </div> 
11</did>
<h1>测试页面</h1>
</body>
</html>
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
		$('#dt').datetimebox({    
		    required: true,    
		    showSeconds: true   
		});
		
		$('#dd').datetimespinner({    
		    //...       
		});
	});
	
	
	
</script>

</head>
<body>
	<input class="easyui-datetimebox" name="birthday"     
        data-options="required:true,showSeconds:false" editable="false" style="width:150px">
        
    <input id="dt" type="text" name="birthday" editable="false"></input>
    
    <input class="easyui-datetimespinner" style="width:300px">
    
    <input id="dd" type="text" style="width:300px">
</body>
</html>

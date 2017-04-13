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
		$('#p').panel({
		  width:500,
		  height:150,
		  title: 'My Panel',
		  collapsible:true,
		  tools: [{
		    iconCls:'icon-add',    
		    handler:function(){alert('new')}    
		  },{
		    iconCls:'icon-save',    
		    handler:function(){alert('save')}
		  }],
		  
		});
		
		$('#e').panel({
		    href:'uploadTest.jsp',
		    width:100,
		    left:900,
		    top:900,
		    fit:true,
		    border:true,
		    onLoad:function(){    
		        console.log('loaded successfully');    
		    }    
		});
	});
	
	function fn1(){
		alert(1);
		$('#p').panel('move',{
		  left:1000,    
		  top:100
		});
	}
	
</script>

</head>
<body>
	<div class="easyui-panel" title="My Panel"     
        style="width:500px;height:150px;padding:10px;background:#fafafa;"   
        data-options="iconCls:'icon-save',closable:true,    
                collapsible:true,minimizable:true,maximizable:true">   
	    <p>panel content.</p>   
	    <p>panel content.</p>   
	</div>
	
	<div id="p" style="padding:10px;">    
	    <p>panel content.</p>
	    <p>panel content.</p>
	</div>
	
	<div id="e" style="padding:10px;">    
	    <p>panel content.</p>
	    <p>panel content.</p>
	</div>
	
	<button onclick="fn1()">点击</button>
</body>
</html>

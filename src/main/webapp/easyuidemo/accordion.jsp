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
		$('#aa').accordion({
			// 定义在展开和折叠的时候是否显示动画效果。
		    animate:true,
		    onSelect:function(title,index){
		    	console.log(title + "  " + index);
		    }
		});
		
		console.log($('#aa').accordion('getSelected'));
		
		$('#aa').accordion('add', {
			title: '新标题',
			content: '新内容',
			selected: false
		});
	});
	
	function fn1(){
		$('#aa').accordion('remove', '新标题');
	}
</script>

</head>
<body>
	<div id="aa" class="easyui-accordion" style="width:300px;height:200px;">
	    <div title="Title1" data-options="iconCls:'icon-save'" style="overflow:auto;padding:10px;">   
	        <h3 style="color:#0099FF;">Accordion for jQuery</h3>   
	        <p>Accordion is a part of easyui framework for jQuery.     
	        It lets you define your accordion component on web page more easily.</p>   
	    </div>
	    
	    <div title="Title2" data-options="iconCls:'icon-reload',selected:true" style="padding:10px;">   
	        content2    
	    </div>
	     
	    <div title="Title3">
	        content3
	    </div>
	</div>  
	
    <button onclick="fn1()">点击</button>
</body>
</html>

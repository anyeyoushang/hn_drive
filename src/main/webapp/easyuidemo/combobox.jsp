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
		$('#dd').combobox({
		    url:'${ctx }/user/combobox',    
		    valueField:'id',
		    textField:'text',
		    formatter: function(row){
				var opts = $(this).combobox('options');
				console.log(opts.textField);
				console.log(row);
				return row[opts.textField];
			}
		});
		
		$('#cc1').combobox({
		    url:'${ctx }/user/combobox',    
		    valueField:'id',
		    textField:'text',
		    onSelect: function(rec){
		    	console.log(rec);
	            var url = 'www.baidu.com?id='+rec.id;
	            $('#cc2').combobox('reload', url);  
	        }
		});
		
		$('#cc2').combobox({
		    valueField:'id',
		    textField:'text'
		});
	});

</script>

</head>
<body>
<form>
	<select id="cc" class="easyui-combobox" name="dept" style="width:200px;">   
	    <option value="aa">aitem1</option>   
	    <option>bitem2</option>   
	    <option>bitem3</option>   
	    <option>ditem4</option>   
	    <option>eitem5</option>   
	</select>
	
	<input id="cc" class="easyui-combobox" name="dept"   value="请选择"
    data-options="valueField:'id',textField:'text',url:'${ctx }/user/combobox'" />  
	<input id="dd" name="dept" value="aa">
	
	<input id="cc1" class="easyui-combobox" />
	<input id="cc2" class="easyui-combobox" />
	
	<input type="submit" value="提交" />
	
</form>
</body>
</html>

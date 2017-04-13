<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<title>佳伟代驾后台管理系统</title>
<jsp:include page="/base.jsp"/>
<script type="text/javascript">
$(function(){
	if('${adminInfo }' == ''){// 说明没有登陆
		location.replace('${ctx}/background/backLogin.jsp');
	}
});
</script>
<style type="text/css">
	*{
		font-size:13px;
		font-family:微软雅黑;
	}
	#nt{
		margin:18px 30px;
		float:right;
	}
	#nd{
		margin:15px 26px;
		float:left;
		font-size:28px;
	}
	#nt td{
		font-size:18px;
	}
</style>
</head>
<body class="easyui-layout" fit="true" oncontextmenu="return false">
	<%-- 北部 --%>
	<div data-options="region:'north',href:'${pageContext.request.contextPath}/background/north.jsp'" style="height: 60px;overflow: hidden;" class="logo"></div>
    <%-- 南部 --%>
    <div data-options="region:'south'" style="height:30px;text-align:center;padding-top:5px;">
    	<a style="text-decoration:none;margin:0 auto;" href="http://www.hfwxkf.com/"  target="_blank">
    		安徽好牛软件&copy;版权所有
   		</a>
    </div>
    <%--西部 split:是否可以改变大小--%>
    <div data-options="region:'west',title:'导航菜单',split:true,border:false" style="width:180px;" href="${ctx }/background/tree.jsp">
    </div>
    <%-- 中间部分 --%>
    <div data-options="region:'center',title:'欢迎使用佳伟代驾后台管理系统',href:'${ctx }/background/center.jsp'" style="overflow: hidden;">
    </div>
</body>
</html>

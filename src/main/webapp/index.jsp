<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>SyPro示例项目(弹窗修改版v20121119)</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<jsp:include page="/inc.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<!-- 北部 -->
	<div data-options="region:'north',href:'${pageContext.request.contextPath}/layout/north.jsp'" style="height: 60px;overflow: hidden;" class="logo"></div>
	<!-- 南部 -->
	<div data-options="region:'south',href:'${pageContext.request.contextPath}/layout/south.jsp'" style="height: 27px;overflow: hidden;"></div>
	<!-- 西部 -->
	<div data-options="region:'west',title:'功能导航',href:'${pageContext.request.contextPath}/layout/west.jsp'" style="width: 200px;overflow: hidden;"></div>
	<!-- 东部 -->
	<%-- <div data-options="region:'east',title:'日历',split:true" style="width: 200px;overflow: hidden;">
		<jsp:include page="layout/east.jsp"></jsp:include>
	</div> --%>
	<!-- 中间部分 -->
	<div data-options="region:'center',title:'欢迎使用SyPro示例系统',href:'${pageContext.request.contextPath}/layout/center.jsp'" style="overflow: hidden;"></div>
	<!-- 登陆 -->
	<%-- <jsp:include page="user/login.jsp"></jsp:include> --%>
	<!-- 注册 -->
	<jsp:include page="user/reg.jsp"></jsp:include>
</body>
</html>
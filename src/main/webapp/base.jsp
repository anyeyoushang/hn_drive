<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="model.dao.*"%>

<c:set var="ctx" value="${pageContext.request.contextPath }" scope="application"/>

<!-- easyui控件 -->
<!-- 更换皮肤 -->
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/static/jquery-easyui-1.4.5/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui-1.4.5/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>

<!-- cookie插件 -->
<script type="text/javascript" src="${ctx}/static/tool/jquery.cookie.js" charset="utf-8"></script>

<!-- 自己定义的样式和JS扩展 -->
<link rel="stylesheet" href="${ctx}/static/tool/syCss.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/static/tool/syUtil.js" charset="utf-8"></script>

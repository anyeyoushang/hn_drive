<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head> 
<meta charset="utf-8" />
<title>登录页面 - 项目</title>
<jsp:include page="/base.jsp"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/file/css/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/file/css/font-awesome.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/file/css/family.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/file/css/ace-rtl.min.css" />
<link rel="stylesheet" href="${ctx}/static/file/css/ace.min.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/file/js/ace-extra.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/file/js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/file/js/jquery.cookie.js"></script>
	
<script type="text/javascript">
	function admin_login(){
		$.post('${ctx}/sys/user/backLogin', serializeObject($("#adminLoginForm")), function(data){
			if(data.success){
				location ="${ctx}/background/index.jsp";
			}else{
				$("#d1").css("display","none");
				$("#d2").css("display","block");
			}
		});
	}
	
</script>
</head>
	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="icon-leaf green"></i>
									<span class="red">代驾</span>
									<span class="white">后台系统</span>
								</h1>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 style="border-bottom:1px solid #ccc;margin:0px;padding:0px;padding-bottom:10px;" class="blue lighter bigger">
												<i class="icon-coffee green"></i>
												请输入您的登录名和密码
											</h4>
											<div id="d1" style="display:block;height:25px;"></div>
											<div id="d2" style="border:1px solid white;display:none;margin:0px;padding:3px 0px;color:red;">
												<span>您输入的用户名或密码有误!</span>
											</div>
											<form action="javascript:admin_login();" id="adminLoginForm">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" name="userName" placeholder="用户名" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" name="password" placeholder="密码" />
															<i class="icon-lock"></i>
														</span>
													</label>
													<div class="space"></div>
													<div class="clearfix">
														<!-- 
														<label class="inline">
															<input type="checkbox" id="aceCheck" class="ace" />
															<span class="lbl"> 记住我</span>
														</label>
														 -->
														<button type="submit" id="login" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="icon-key"></i>
															登录
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
</body>
</html>

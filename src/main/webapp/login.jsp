<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery.min.js"></script>
<script>
	function login(){
		$.post("mobile/comment/login", {"name":"admin","password":"admin"}, function(data){
			if(data.success == true){
				location = "mobile/comment/index";
			}
		});
	}
</script>
</head>
<body>
	<button onclick="login();">登录</button>
</body>
</html>
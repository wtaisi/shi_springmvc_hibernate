<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML >

<html>
<head>
<title>Login</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<%@ page isELIgnored="false"%>
</head>

<body onload="document.getElementById('userName').focus();">
						<h1>${message}</h1>
			<form id=loginform method=get name=loginform action="login/main">
				用户名：<input type=text id=userName name=username class="input_border" maxlength=16 /> <br />
				密码：<input  type=password id=password name=password class="input_border" /> <br />
				<input name="submit" type="submit" value="登录" /> 
			</form>
</body>
</html>

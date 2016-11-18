<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>消息提示</title>
</head>
<body>
	<div>
		<div id="user_success" class="Area bgfff">
			<h4 class="titleH">注册成功</h4>
			<div class="user_successCon">
				<p>恭喜，${username} 注册成功！</p>
				<a href="/">返回首页</a>
			</div>
		</div>
	</div>
</body>
</html>
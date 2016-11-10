<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>我的资料</title>
</head>
<body>
<div data-options="region:'center',title:'我的资料'">
	<span>帐号：</span>
	<span>${user.username}</span>
	<a id="signout" href="#">退出</a>
</div>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#signout").click(function() {
			userFn.logout();
		});
	});
	</script>
</body>
</html>
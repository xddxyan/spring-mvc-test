<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<script type="text/javascript" src="/js/all.js"></script>
	<link href="/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/common.css" rel="stylesheet">
	<link href="/images/favicon.ico" rel="icon" type="image/png" />
	<title><sitemesh:title /></title>
	<sitemesh:head />
</head>
<body>
	<div class="container">
		<%@ include file="header.jsp"%>
		<sitemesh:body />
		<%@ include file="footer.jsp"%>
	</div>
</body>
</html>
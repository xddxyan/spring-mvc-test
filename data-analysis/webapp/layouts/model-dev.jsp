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
	<script type="text/javascript" src="/js/sha1.js"></script>
	<script type="text/javascript" src="/plugins/jquery-1.12.4.js"></script><!-- for ie6~8 support -->
	<script type="text/javascript" src="/plugins/vue.js"></script>
	<script type="text/javascript" src="/plugins/vue-router.min.js"></script>
	<script type="text/javascript" src="/plugins/jstree/jstree.min.js"></script>
	<script type="text/javascript" src="/plugins/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript" src="/plugins/bootstrap-datepicker/locales/zh-CN.min.js"></script>
	<script type="text/javascript" src="/plugins/lodash.min.js"></script>
	
	<script type="text/javascript" src="/js/meta-global.js"></script>
	<script type="text/javascript" src="/js/meta-jstree.js"></script>
	<script type="text/javascript" src="/js/meta-user.js"></script>
	<script type="text/javascript" src="/js/meta-data.js"></script>
	
	<script type="text/javascript" src="/js/vue-pagination.js"></script>
	<script type="text/javascript" src="/js/vue-alert.js"></script>
	<script type="text/javascript" src="/js/vue-common.js"></script>
	<script type="text/javascript" src="/js/vue-table.js"></script>
	
	<script type="text/javascript" src="/script/meta-index.js"></script>
	<script type="text/javascript" src="/script/meta-subject.js"></script>
	
	<link rel="stylesheet" href="/plugins/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css">
	<link rel="stylesheet" href="/plugins/jstree/themes/default/style.min.css" />
	<link rel="stylesheet" href="/css/common.css">
	<link rel="icon" href="/image/favicon.ico" type="image/png" />
	<title><sitemesh:title /></title>
	<sitemesh:head />
</head>
<body>
	<div id="vue-alert"></div>
	<div class="container-fluid">
		<%@ include file="header.jsp"%>
		<sitemesh:body />
		<%@ include file="footer.jsp"%>
	</div>
</body>
</html>
<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="panel_body" fragment="true" %>
<%@ attribute name="panel_head" fragment="true" %>
<div class="panel panel-default">
	<div class="panel-heading">
		<jsp:invoke fragment="panel_head"/>
		<div class="clear-both"></div>
	</div>
	<div class="panel-body">
		<jsp:invoke fragment="panel_body"/>
	</div>
</div>

<!-- panel -->
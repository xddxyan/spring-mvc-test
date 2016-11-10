<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="title" fragment="true" %>
<%@ attribute name="body" fragment="true" %>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<div class="modal fade" id="<%=id%>" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<jsp:invoke fragment="title"/>
				</h4>
			</div>
			<div class="modal-body">
				<jsp:invoke fragment="body"/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary">确定</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
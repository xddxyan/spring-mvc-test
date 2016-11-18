<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="tt" type="java.lang.String" required="true"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title"><%=tt%></h3>
	</div>
	<div class="panel-body">
		<div>
			<div class="pull-left operations" style="min-width: 150px">
				<button type="button" class="btn btn-success btn-sm"
					onclick="tree_create();">
					<i class="glyphicon glyphicon-asterisk"></i>
				</button>
				<button type="button" class="btn btn-warning btn-sm"
					onclick="tree_rename();">
					<i class="glyphicon glyphicon-pencil"></i>
				</button>
				<button type="button" class="btn btn-danger btn-sm"
					onclick="tree_delete();">
					<i class="glyphicon glyphicon-remove"></i>
				</button>
			</div>
			<div class="pull-right">
				<input type="text" value="" id="search_input" placeholder="Search" />
			</div>
		</div>
		<div class="row" id="subject-tree"></div>
	</div>
</div>
<!-- panel -->
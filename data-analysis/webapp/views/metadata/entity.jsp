<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="copycat" uri="http://www.copycat.org/tags"%>

<tags:panel_table>
<jsp:attribute name="panel_head">
	<h3 class="panel-title pull-left">参数实体列表</h3>
	<div class="pull-right btn-group-sm">
		<button class="btn btn-default create_elem" type="button">
			<i class="glyphicon glyphicon-asterisk"></i>新增实体
		</button>
	</div>
	<div class="pull-right btn-group-sm">
		<button class="btn btn-default full-btn" type="button">
			<i class="glyphicon glyphicon-asterisk"></i>
		</button>
	</div>
	<div class="pull-right dropdown btn-group-sm">
		<button class="btn btn-default" type="button" data-toggle="dropdown" >
			<i class="glyphicon glyphicon-asterisk"></i>列选择
		</button>
		<ul class="dropdown-menu"> 
			<c:forEach items="${fieldList}" var="item">
				<li><input type="checkbox" value=true />${item}</li>
			</c:forEach>
		</ul>
	</div>
</jsp:attribute>
<jsp:attribute name="panel_body">
	<div class="table-responsive">
		<table class="table table-striped load-table fixed-table">
			<thead>
				<tr class="field-list">
				<c:forEach items="${fieldList}" var="item">
					<th>${item}</th>
				</c:forEach>
					<th width=90>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${itemList}" var="item" varStatus="status">
					<tr>
						<td>${item.entity_id}</td>
						<td>${item.entity_name}</td>
						<td>${item.database_id}</td>
						<td>${item.schema}</td>
						<td>${item.table_name}</td>
						<td>${item.table_desc}</td>
						<td><copycat:format pattern="yyyy年MM月dd日 HH:mm:ss"
								value="${item.create_time}" /></td>
						<td>${item.create_user}</td>
						<td><copycat:format pattern="yyyy年MM月dd日 HH:mm:ss"
								value="${item.last_modify_time}" /></td>
						<td>${item.sequence}</td>
						<td class="td-oper">
							<button type="button" class="btn btn-default btn-xs find_elem" entity_id="${item.entity_id}">
								<i class="glyphicon glyphicon-search blue-glass"></i>
							</button>
							<button type="button" class="btn btn-default btn-xs change_elem">
								<i class="glyphicon glyphicon-pencil yellow-pencil"></i>
							</button>
							<button type="button" class="btn btn-danger btn-xs del_elem" entity_id="${item.entity_id}">
								<i class="glyphicon glyphicon-remove"></i>
							</button>
						</td>
					</tr>
				</c:forEach>
	
			</tbody>
		</table>
	</div>
</jsp:attribute>
</tags:panel_table>

<tags:bootstrap_pagination page="${page}" url="${pageurl}" />

<script>
$('.table-responsive table td').addClass('auto-td');
$('.table-responsive').on('click', 'table tr', function() {
	$(this).find("td:not(.td-oper)").toggleClass( "auto-td" );
	$(this).find("td:not(.td-oper)").toggleClass( "full-td" );
});
$('.full-btn').click(function() {
	$(".load-table").toggleClass( "fixed-table" );
});
//隐藏列
$(".dropdown-menu input").change( function(){
	var n = $(this).index("input");
	var col = $(".load-content tr th:nth-child("+n+"),td:nth-child("+n+")");
	if($(this).is(":checked")){
		col.hide();
	}else{
		col.show();
	}
});
</script>

<!-- panel -->
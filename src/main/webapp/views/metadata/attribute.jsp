<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="copycat" uri="http://www.copycat.org/tags"%>

<div class="table-responsive">
	<table class="table table-striped load-table ">
		<thead>
			<tr>
				<c:forEach items="${fieldList}" var="field" varStatus="status">
					<th>${field}</th>
				</c:forEach>

				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="status">
				<tr>
					<td>${item.attribute_id}</td>
					<td>${item.attribute_name}</td>
					<td>${item.column_name}</td>
					<td>${item.column_desc}</td>
					<td>${item.data_type}</td>
					<td>${item.length}</td>
					<td>${item.precision}</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</div>

<tags:bootstrap_pagination page="${page}" url="${pageurl}" />

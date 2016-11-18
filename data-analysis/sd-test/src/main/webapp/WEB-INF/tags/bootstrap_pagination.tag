<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.copycat.framework.Page" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="text-center" aria-label="Page navigation">
	<ul class="pagination">
		<c:if test="${page.size > 1}">
			<c:if test="${page.number > 1}">
				<li><a href="<%=url%>1" aria-label="Previous">首页</a></li>
				<li><a href="<%=url%>${page.number - 1}"><span aria-hidden="true">&laquo;</span></a></li>
			</c:if>
			<c:forEach begin="${page.begin}" end="${page.end}" var="i">
				<c:if test="${page.number == i }">
					<li class="active"><span>${i}</span></li>
				</c:if>
				<c:if test="${page.number != i }">
					<li><a href="<%=url%>${i}">${i}</a></li>
				</c:if>
			</c:forEach>
			<c:if test="${page.number < page.size}">
				<li><a href="<%=url%>${page.number + 1}"><span aria-hidden="true">&raquo;</span></a></li>
				<li><a href="<%=url%>${page.size}" aria-label="Next">尾页</a></li>
			</c:if>
		</c:if>
	</ul>
</nav>

<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.copycat.framework.Page" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="pagination">
	<c:if test="${page.size > 1}">
		<c:if test="${page.number > 1}">
			<a href="<%=url%>/1">首页</a>
			<a href="<%=url%>/${page.number - 1}">上一页</a>
		</c:if>
		<c:forEach begin="${page.begin}" end="${page.end}" var="i">
			<c:if test="${page.number == i }">
				<span class="active">${i}</span>
			</c:if>
			<c:if test="${page.number != i }">
				<a href="<%=url%>/${i}">${i}</a>
			</c:if>
		</c:forEach>
		<c:if test="${page.number < page.size}">
			<a href="<%=url%>/${page.number + 1}">下一页</a>
			<a href="<%=url%>/${page.size}">尾页</a>
		</c:if>
	</c:if>
</div>

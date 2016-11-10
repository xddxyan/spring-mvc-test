<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.metasoft.model.Constant" %>
<script type="text/javascript">
var a = document.createElement('a');
a.href = window.location.href;
var login = "<%=Constant.SIGNIN_URL%>";
console.log(a.pathname);
login+=("?referer="+a.pathname);
window.location.href = login;
</script>

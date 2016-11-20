<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>经营分析系统</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

</head>
<body>
	<div id="vue-router"></div>
	<script type="text/javascript">
	var username = "${session.username}"
	var FooBar = {template:'<div>foobar</div>'}
	Vue.use(VueRouter)
	const router = new VueRouter({
	  mode: 'history',
	  base: '/metadata/',
	  routes: [
		{ path: '/', redirect: '/index' },
	    { path: '/index', component: VueIndex },
	    { path: "/subject", component: VueSubject },
	    { path: "/database", component: FooBar },
	    { path: "/dimension", component: FooBar },
	    { path: "/indicator", component: FooBar},
	    { path: '/statement', component: FooBar}
	  ]
	})

	new Vue({
		router,
		template:`
		<div id="vue-router">
		<ul class="nav nav-tabs">
			<router-link tag="li" active-class="active" to="/index" ><a>首页</a></router-link>
			<router-link tag="li" active-class="active" to="/subject"><a>主题实体</a></router-link>
			<router-link tag="li" active-class="active" to="/database"><a>数据源</a></router-link>
			<router-link tag="li" active-class="active" to="/dimension"><a>维度</a></router-link>
			<router-link tag="li" active-class="active" to="/indicator"><a>指标</a></router-link>
			<router-link tag="li" active-class="active" to="/statement"><a>报表查询</a></router-link>
			<router-link tag="li" to="" v-if="CheckSysAdmin()"><a>admin test</a></router-link>
			<router-link tag="li" to="" v-if="CheckGuest()"><a>guest</a></router-link>
		</ul>
			<router-view ></router-view>
		</div>
		`,
		mixins:[gMixin]
	}).$mount('#vue-router')
	</script>
</body>
</html>
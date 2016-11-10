<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.metasoft.model.Constant" %>
<!DOCTYPE html>
<html>
<head>
<title>登录</title>
<style type="text/css"></style>
<link href="/css/signin.css" rel="stylesheet">
</head>
<body>
	<div class="col-lg-12">
		<div class="well center-block " style="max-width: 500px">
			<form class="form-signin" name="form" action="./" method="post">
				<h2 class="form-signin-heading">请登录</h2>
				<input type="text" name="username" id="username" class="form-control" placeholder="用户名" required autofocus /> 
				<input type="password" name="password" id="password" class="form-control" placeholder="密码" required />
				<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
			</form>
		</div>
	</div>
	<!-- /container -->

	<script type="text/javascript" src="/js/jsencrypt.min.js"></script>
	<script type="text/javascript" src="/js/sha1.js"></script>
  	<script type="text/javascript">
  		var captchaOk = false;
	  	$(document).ready(function(){
	  		$("form[name='form']").submit(function(e){
	  			e.preventDefault();
	  			var user = {
	  				username: $("#username").val(), 
	  				password: $("#password").val()
	  			};
				user.password = GetSha1(user.password);
				// Encrypt with the public key...
				var pubkey = "${publicKey}";
				var encrypt = new JSEncrypt();
				encrypt.setPublicKey(pubkey);
				user.password = encrypt.encrypt(user.password);
	  			User.Signin(user, "${referer}");
	  		});
	  	});
	</script>
</body></html>
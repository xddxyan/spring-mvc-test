<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.codechiev.model.Constant" %>
<!DOCTYPE html>
<html>
<head>
<title>登录</title>
<style type="text/css"></style>
</head>
<body>
	<div class="col-lg-12">
		
		<div class="well center-block " style="max-width: 500px">
			<form class="form-signin" name="form" >
				<div class="row">
				<div class="col-md-12 text-center">
				<h3 class="form-signin-heading text-center">数据中心</h3>
				<input type="text" name="username" id="username" class="form-control input-lg margin-between15" placeholder="用户名" required autofocus /> 
				<input type="password" name="password" id="password" class="form-control input-lg margin-between15" placeholder="密码" required />
				
				<button class="btn btn-lg btn-primary margin-between15" type="submit">登录</button>
				</div>
				</div>
			</form>
		</div>
	</div>
	<!-- /container -->

	<script type="text/javascript" src="/js/jsencrypt.min.js"></script>

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
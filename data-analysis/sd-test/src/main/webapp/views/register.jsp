<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE>
<html>
<head>
<title>注册</title>
<link href="/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/signin.css" rel="stylesheet">
<style type="text/css"></style>
</head>
<body>
	<div class="col-lg-12">
		<div class="well center-block" style="max-width: 500px">
			<form class="form-signin" name="form" method="POST">
				<h2 class="form-signin-heading">新用户</h2>
					<input type="text" id="username" name="username" placeholder="用户名" class="form-control" required autofocus > 
					<input type="text" id="email" name="email" placeholder="电子邮件" class="form-control" required > 
					<input type="password" id="password" name="password" placeholder="密码" class="form-control" required> 
					<input type="password" id="password_confirm" name="password_confirm" placeholder="重复密码" class="form-control" required>
				<button class="btn btn-lg btn-primary btn-block">Register</button>
			</form>
		</div>
	</div>

	<script type="text/javascript" src="/js/jsencrypt.min.js"></script>
	<script type="text/javascript" src="/js/sha1.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
  		$("form[name='form']").submit(function(e){
  			e.preventDefault();
  			var user = {
  				username: $("#username").val(), 
  				password: $("#password").val(), 
  				email   : $("#email").val()
  			};
			var password_confirm = $("#password_confirm").val();			
			if(password_confirm != user.password){
				VueAlert.Warning("请确认密码",null);
				return;
			}
			user.password = GetSha1(user.password);
			// Encrypt with the public key...
			var pubkey = "${publicKey}";
			var encrypt = new JSEncrypt();
			encrypt.setPublicKey(pubkey);
			user.password = encrypt.encrypt(user.password);
  			User.Signup(user, "${referer}");
  		});
	});
	</script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>消息服务</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="/js/angular.min.js"></script>
<style type="text/css">
#msg{
	min-height:200px;
	max-height:500px;
	overflow-y: scroll;
}
</style>
</head>
<body>
	<div class="col-lg-12">
			<c:if test="${not empty session}">
			  	<h3>当前登录用户:<span class="label label-info">${session.username}</span></h3>
			</c:if>

			<div class="panel panel-info">
				<div class="panel-heading">消息服务</div>
				<div class="panel-body">
					<div id="msg" class="well">
						
					</div>
					<div class="input-group input-group-lg">
						<input id="msg_input" type="text" class="form-control" placeholder="说点啥..."> 
						<span class="input-group-btn">
							<button class="btn btn-default btn-primary" type="button" onclick=send()>发送</button>
						</span>
					</div>
					<!-- /input-group -->
				</div>
			</div>
		</div>

<div ng-app="myApp" ng-controller="customersCtrl">
<table>
  <tr ng-repeat="x in names">
    <td>{{ x.Name }}</td>
    <td>{{ x.Country }}</td>
    <td>
    <button type="button" class="btn btn-danger btn-xs del_elem" ng-click="Edit(x)">
		<i class="glyphicon glyphicon-remove"></i>
	</button>
    </td>
  </tr>
</table>
</div>
	<script type="text/javascript">
		function send(){
			$.post("/test/chat-send",{'touser':'-1','msg':$('#msg_input').val()}, function(data){console.log(data)});
		}
		function onMsg(from, msg){
			$("#msg").append('<p><span class="label label-info">'+from+':</span>'+msg+'</p>');
			if($("#msg p").length>100){
				$("#msg p:first-child").remove();
			}
		}
		var seq = 0;
		function poll(){
			$.ajax({method: "GET",
				url: "/test/chat-get",
				data: {"seq":seq} }).
				done(function(data) {
					if(data=="async-timeout"){
						console.log(data);
					}else{
						console.log(data);
						var obj = jQuery.parseJSON( data );
						for(var i=0; i<obj.length; i++){
							if(obj[i].seq>seq||seq-obj[i].seq>100)
								seq=obj[i].seq;
							onMsg(obj[i].from, obj[i].msg);
						}
					}
					//poll();
					CountDown(5, function(){poll();});//测试是否收到缓存消息
				  }).
				fail(function(jqXHR, textStatus, errorThrown) {
					//try again later
					CountDown(5, function(){poll();});
					console.log(textStatus);
				  });
		}
		$(document).ready(function() {
			poll();
		});
		
		var app = angular.module('myApp', []);
		app.controller('customersCtrl', function($scope) {
			$scope.names = [ {"Name":"Alfreds Futterkiste","City":"Berlin","Country":"Germany"}, {"Name":"Ana Trujillo Emparedados y helados","City":"México D.F.","Country":"Mexico"}, {"Name":"Antonio Moreno Taquería","City":"México D.F.","Country":"Mexico"}, {"Name":"Around the Horn","City":"London","Country":"UK"}, {"Name":"B's Beverages","City":"London","Country":"UK"}, {"Name":"Berglunds snabbköp","City":"Luleå","Country":"Sweden"}, {"Name":"Blauer See Delikatessen","City":"Mannheim","Country":"Germany"}, {"Name":"Blondel père et fils","City":"Strasbourg","Country":"France"}, {"Name":"Bólido Comidas preparadas","City":"Madrid","Country":"Spain"}, {"Name":"Bon app'","City":"Marseille","Country":"France"}, {"Name":"Bottom-Dollar Marketse","City":"Tsawassen","Country":"Canada"}, {"Name":"Cactus Comidas para llevar","City":"Buenos Aires","Country":"Argentina"}, {"Name":"Centro comercial Moctezuma","City":"México D.F.","Country":"Mexico"}, {"Name":"Chop-suey Chinese","City":"Bern","Country":"Switzerland"}, {"Name":"Comércio Mineiro","City":"São Paulo","Country":"Brazil"} ];
			$scope.Edit = function(data) {
				  alert(data);
				};
		});
		</script>
</body>
</html>
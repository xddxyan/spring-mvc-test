<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="copycat" uri="http://www.copycat.org/tags"%>
<html>
<head>
	<title>参数标题</title>
</head>
<body>
<div class="col-lg-12">
	<div class="panel panel-default">
		<div class="panel-heading text-right">
			<h3 class="panel-title pull-left">参数面板</h3>
			<button class="btn btn-default create_elem" type="button" >
				<i class="glyphicon glyphicon-asterisk"></i>新增数据源
			</button>
		</div>
		<div class="panel-body">
			<div class="table-responsive">
				<table class="table load-table ">
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
								<td>${item.database_id}</td>
								<td>${item.database_name}</td>
								<td>${item.database_username}</td>
								<td>${item.database_password}</td>
								<td>${item.database_connection}</td>
								
								<td>
									<button type="button" class="btn btn-default btn-xs change_elem" >
										<i class="glyphicon glyphicon-pencil yellow-pencil"></i>
									</button>
									<button type="button" class="btn btn-danger btn-xs delete_elem" db_id="${item.database_id}">
										<i class="glyphicon glyphicon-remove"></i>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div><!-- table-responsive -->
		</div><!-- panel-body -->
	</div><!-- panel -->
	
	<div id="msgdlg" class="alert-fixed"></div>
	
	<tags:bootstrap_form id="formmodal">
		<jsp:attribute name="title">修改数据源</jsp:attribute>
		<jsp:attribute name="body"></jsp:attribute>
	</tags:bootstrap_form>
</div><!-- column -->

<script type="text/javascript">
	$(function() {
		var alertDlg = new BootstrapAlert($("#msgdlg"));		
		var formmodal = $("#formmodal");
		var theform = formmodal.find("form");
		var bootstrapForm = new BootstrapForm(theform);
		bootstrapForm.init([{"label":"数据库id", "name":"database_id"},
					                                {"label":"数据库驱动名", "name":"database_name"},
					                                {"label":"用户名", "name":"database_username"},
					                                {"label":"密码", "name":"database_password"},
					                                {"label":"jdbc连接地址", "name":"database_connection"}]);
		bootstrapForm.onSubmit(function(data){
        	   if("ok"==data) 
        		   alertDlg.alert("操作成功", function(){Reload();});
        	   else {
        		   alert(data);
	        	   Reload();
        	   }
        });
		$(".create_elem").click(function(){
			formmodal.find(".modal-title").html("新增数据源");
			formmodal.modal('show',{backdrop: true});
			bootstrapForm.setAction("database/add");
			bootstrapForm.setIput('database_id', '');
		});
		$(".change_elem").click(function(){
			formmodal.find(".modal-title").html("修改数据源");
			formmodal.modal('show',{backdrop: 'static'});
			bootstrapForm.setAction("database/change");
			var tds = $(this).parent().siblings();
			bootstrapForm.setInputByPos(0, tds.eq(0).text(), true);
			bootstrapForm.setInputByPos(1, tds.eq(1).text());
			bootstrapForm.setInputByPos(2, tds.eq(2).text());
			bootstrapForm.setInputByPos(3, tds.eq(3).text());
			bootstrapForm.setInputByPos(4, tds.eq(4).text());
			bootstrapForm.setInputByPos(5, tds.eq(5).text());
		});
		$(".delete_elem").click(function(){
			$.post("database/delete", {database_id: $(this).attr('db_id')}, function(data){
				if(data=='ok'){
					alertDlg.alert("操作成功", function(){Reload();});
				}else{
					alert(data);
				}
			});
		});
	});
</script>
</body>
</html>
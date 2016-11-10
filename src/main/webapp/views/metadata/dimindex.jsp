<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title>维度管理</title>
	<link rel="stylesheet" href="/plugins/jstree/themes/default/style.min.css" />
</head>
<body>
	<div class="col-md-3" >
		<tags:panel_jstree tt="维度目录"></tags:panel_jstree>
	</div><!-- col -->

	<div class="col-md-9 load-content" >
	</div><!-- col -->
	
	<div id="msgdlg" class="alert-fixed"></div>
	
	<tags:bootstrap_form id="formmodal">
		<jsp:attribute name="title"></jsp:attribute>
		<jsp:attribute name="body">
		<div class="form-group">
		    <label>维度id</label> 
		    <input type="text" class="form-control" name="dimension_id">
		</div>
		<div class="form-group">
		    <label>维度名称</label> 
		    <input type="text" class="form-control" name="dimension_name">
		</div>
				<div class="form-group">
		    <label>维度描述</label> 
		    <input type="text" class="form-control" name="dimension_desc">
		</div>
				<div class="form-group">
		    <label>维度类型</label> 
		    <input type="text" class="form-control" name="dimemsion_type">
		</div>
		</jsp:attribute>
	</tags:bootstrap_form>

	<script type="text/javascript">
		var alertDlg = new BootstrapAlert($("#msgdlg"));	
		jstreeNode 	= $('#subject-tree');
		search 		= $('#search_input');
		delete_func = function (sel, jstreeRef){
			$.post("/metadata/dim-index/delete",
					{ids: JSON.stringify(sel)},
					function(d){
						if(d=="ok"){
							jstreeRef.delete_node(sel);
							alertDlg.alert("删除目录成功");
						}else{
							alert(d);
						}
					});
		}
		rename_func = function (node, status){
			if(status)
				$.post("/metadata/dim-index/change",
					{parent_dim_index_id:node.parent, dim_index_id:node.id, dim_index_name:node.text},
					function(d){
						if(d=="ok"){
							alertDlg.alert("修改目录成功");
						}else{
							alert(d);
						}
					});
		}
		create_func = function (node, status){
			$.post("/metadata/dim-index/add",
					{parent_dim_index_id:node.parent, dim_index_id:node.id, dim_index_name:node.text},
					function(d){
						if(d=="ok"){
							alertDlg.alert("增加目录成功");
						}else{
							alert(d);
						}
					});
		}
		
		$(function(){
			//树
			var jsontreeData = ${treelist};
			InitJstree(jsontreeData);
			//load实体列表
			var loading = new LoadBootstrapTable($(".load-content"));
			loading.loading("/metadata/dimension.get");			
			//编辑	
			var formmodal = $("#formmodal");
			var theform = formmodal.find("form");
			var bootstrapForm = new BootstrapForm(theform);
			bootstrapForm.onSubmit(function(data){
	        	   if("ok"==data) 
	        		   alertDlg.alert("操作成功", function(){Reload();});
	        	   else {
	        		   alert(data);
		        	   Reload();
	        	   }
	        });
			$(".create_elem").click( function(){
				formmodal.find(".modal-title").html("新增实体");
				formmodal.modal('show',{backdrop: true});
				bootstrapForm.setAction("dimension/add");
				bootstrapForm.setInputByName('dimension_id', "");
			});
			$(".load-content").on( "click", ".change_elem", function(){
				formmodal.find(".modal-title").html("修改实体");
				formmodal.modal('show',{backdrop: 'static'});
				bootstrapForm.setAction("dimension/change");
				var tds = $(this).parents("td").siblings();
				bootstrapForm.setInputByPos(0, tds.eq(0).text(), true);
				bootstrapForm.setInputByPos(1, tds.eq(1).text());
				bootstrapForm.setInputByPos(2, tds.eq(2).text());
				bootstrapForm.setInputByPos(3, tds.eq(3).text());
				bootstrapForm.setInputByPos(4, tds.eq(4).text());
				bootstrapForm.setInputByPos(5, tds.eq(5).text());
			});
		});
	</script>
</body>
</html>
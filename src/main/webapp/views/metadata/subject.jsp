<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title>主题管理</title>
	<link rel="stylesheet" href="/plugins/jstree/themes/default/style.min.css" />
</head>
<body>

	<div class="col-md-3" >
		<tags:panel_jstree tt="主题域"></tags:panel_jstree>
	</div><!-- col -->

	<div class="col-md-9 load-content" >
	</div><!-- col -->
	
	<div id="msgdlg" class="alert-fixed"></div>
	
	<tags:bootstrap_form id="formmodal">
		<jsp:attribute name="title"></jsp:attribute>
		<jsp:attribute name="body">
		<div class="form-group">
		    <label>实体id</label> 
		    <input type="text" class="form-control" name="entity_id">
		</div>
		<div class="form-group">
		    <label>实体名称</label> 
		    <input type="text" class="form-control" name="entity_name">
		</div>
		<div class="form-group">
		    <label>数据库id</label> 
		    <input type="text" class="form-control" name="database_id">
		</div>
		<div class="form-group">
		    <label>数据库模式</label> 
		    <input type="text" class="form-control" name="schema">
		</div>
		<div class="form-group">
		    <label>表名</label> 
		    <input type="text" class="form-control" name="table_name">
		</div>
		<div class="form-group">
		    <label>表描述</label> 
		    <input type="text" class="form-control" name="table_desc">
		</div>
		<div class="form-group">
		    <label>序列号</label> 
		    <input type="text" class="form-control" name="sequence">
		</div>
		</jsp:attribute>
	</tags:bootstrap_form>
	<tags:bootstrap_modal id="attrmodal">
		<jsp:attribute name="title">实体属性</jsp:attribute>
		<jsp:attribute name="body"></jsp:attribute>
	</tags:bootstrap_modal>

	<script type="text/javascript">
		var alertDlg = new BootstrapAlert($("#msgdlg"));	
		jstreeNode 	= $('#subject-tree');
		search 		= $('#search_input');
		delete_func = function (sel, jstreeRef){
			$.post("/metadata/subject/delete",
					{ids: JSON.stringify(sel)},
					function(d){
						if(d=="ok"){
							jstreeRef.delete_node(sel);
							alertDlg.alert("删除主题成功");
						}else{
							alert(d);
						}
					});
		}
		rename_func = function (node, status){
			if(status)
				$.post("/metadata/subject/change",
					{parent_subject_area_id:node.parent, subject_area_id:node.id, subject_area_name:node.text},
					function(d){
						if(d=="ok"){
							alertDlg.alert("修改主题成功");
						}else{
							alert(d);
						}
					});
		}
		create_func = function (node, status){
			$.post("/metadata/subject/add",
					{parent_subject_area_id:node.parent, subject_area_id:node.id, subject_area_name:node.text},
					function(d){
						if(d=="ok"){
							alertDlg.alert("增加主题成功");
						}else{
							alert(d);
						}
					});
		}
		
		//on doc loaded
		$(function(){
			//树
			var subjectJson = ${treelist};
			InitJstree(subjectJson);
			//load实体列表
			var loadEntity = new LoadBootstrapTable($(".load-content"));
			loadEntity.loading("/metadata/entity.get");			
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
			$(".create_elem").click(function(){
				formmodal.find(".modal-title").html("新增实体");
				formmodal.modal('show',{backdrop: true});
				bootstrapForm.setAction("entity/add");
				bootstrapForm.setIput('database_id', '');
			});
			$(".load-content").on( "click", ".change_elem", function(){
				formmodal.find(".modal-title").html("修改实体");
				formmodal.modal('show',{backdrop: 'static'});
				bootstrapForm.setAction("entity/change");
				var tds = $(this).parents("td").siblings();
				bootstrapForm.setInputByPos(0, tds.eq(0).text(), true);
				bootstrapForm.setInputByPos(1, tds.eq(1).text());
				bootstrapForm.setInputByPos(2, tds.eq(2).text());
				bootstrapForm.setInputByPos(3, tds.eq(3).text());
				bootstrapForm.setInputByPos(4, tds.eq(4).text());
				bootstrapForm.setInputByPos(5, tds.eq(5).text());
			});

			//load entity attribute
			var attrmodal = $("#attrmodal");
			attrmodal.find(".modal-dialog").addClass("modal-lg");
			var loadAttr = new LoadBootstrapTable($("#attrmodal .modal-body"));			
			$(".load-content").on( "click", ".find_elem", function(){
				attrmodal.modal('show',{backdrop: 'static'});
				loadAttr.loading("/metadata/attribute.get?entity_id="+$(this).attr("entity_id"));	
			});
			
			//auto adapt column width
		});
	</script>
</body>
</html>
function subject_data(){
	return{"username":username, jstree:null
	}
}
function entity_data(){
	return {items:[], attributes:[], 
		entity_id:"", entity_name:"", database_id:"", schema:"", table_name:"", table_desc:""}
}
Vue.component('entity-form', {
	template : `
<form class="form-horizontal" v-on:submit.prevent="onSubmit">
	<div class="form-group">
	<label class="col-md-2 control-label">实体id：</label>
	<div class="col-md-4">
		<input type="text" class="form-control" v-model="entity_id" readonly>
	</div>
	<label class="col-md-2 control-label">实体名称：</label>
	<div class="col-md-4">
		<input type="text" required="required" class="form-control" v-model="entity_name">
	</div>
	</div>
	
	<div class="form-group">
	<label class="col-md-2 control-label">数据库id：</label>
	<div class="col-md-4">
		<select class="form-control" v-model="database_id" >
		  <option v-for="db in dbs" v-bind:value="db.id">{{db.name}}</option>
		</select>
	</div>
	<label class="col-md-2 control-label">数据库schema：</label>
	<div class="col-md-4">
		<select class="form-control" v-model="schema" >
		  <option v-for="schema in schemas" v-bind:value="schema.id">{{schema.name}}</option>
		</select>
	</div>
	</div>
	
	<div class="form-group">
		<label class="col-md-2 control-label">表名：</label>
		<div class="col-md-4">
		<input type="text" required="required" class="form-control" v-model="table_name">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label">表描述：</label>
		<div class="col-md-10">
		<textarea class="form-control" placeholder="..." rows="2" v-model="table_desc" ></textarea>
		</div>
	</div>

	<div class="form-group">
	<div class=" col-md-12 text-center">
		<button type="submit" class="btn btn-default" >保存</button>
		<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	</div>
	</div>
</form>
	`,
	props : { onSubmit:Function, schemas:Array, dbs:Array},
	data : entity_data,
	methods : {
		update : function(data){
			for ( var key in data) {
				this[key] = data[key]; 
			}
		}
	}
});
Vue.component('entity-tb', {
template : `
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title pull-left">参数实体列表</h3>
		<div class="pull-right btn-group-sm">
			<button class="btn btn-default" type="button" @click="new_entity">
				<i class="glyphicon glyphicon-asterisk"></i>新增实体
			</button>
		</div>
		<div class="pull-right btn-group-sm">
			<button class="btn btn-default full-btn" type="button">
				<i class="glyphicon glyphicon-asterisk"></i>
			</button>
		</div>
		<div class="pull-right dropdown btn-group-sm">
			<button class="btn btn-default" type="button" data-toggle="dropdown" >
				<i class="glyphicon glyphicon-asterisk"></i>列选择
			</button>
			<ul class="dropdown-menu"> 
				<li><input type="checkbox" value=true />{item}</li>
			</ul>
		</div>
		<div class="clear-both"></div>
	</div>
	<div class="panel-body auto-td">
		<vue-table :comp-items="items" :comp-attributes="attributes"></vue-table>
		<vue-page ref="entity_page" v-bind:is-sm=true :get-data="get_entity"></vue-page>
	</div>
	
	<vue-modal lg=true footer-hidden=true modal-id='new-entity' >
		<h4 slot="title">新增实体：</h4>
		<div slot="body">
			<entity-form :on-submit="new_entity_post"></entity-form>
		</div>
	</vue-modal>
</div>
`,
	data : entity_data ,
	mounted : function(){
		this.get_entity(1)
	},
	methods : {
		get_entity:function(pageNum){
			var thiscomp = this;
			$.get('/meta-data/entity.get', {'pageNum':pageNum}, GetCallback(function(data){
				thiscomp.attributes = data.attributes
				thiscomp.items = data.items
				thiscomp.$refs.entity_page.total=data.page.total;
				thiscomp.$refs.entity_page.pageSize=data.page.limit;
				thiscomp.$refs.entity_page.setPage(data.page.number);
			}))
		},
		new_entity:function(){
			$("#new-entity").modal('show')
		},
		new_entity_post:function(event){
			var data = $(event.target).serialize();
			$.post("/meta-data/entity.add", data, function(data){
				if('ok'==data){
					VueAlert.Success('添加成功');
					Reload();
				}else{
					VueAlert.Warning(data);
				}
			});
		}
	}
})

const VueSubject = {
	template: `
<div class="row">
	<div class="col-md-3">
	<div class="operations margin-between15 " >
		<button type="button" class="btn btn-success btn-sm" @click="create_node">
			<i class="glyphicon glyphicon-asterisk"></i>
		</button>
		<button type="button" class="btn btn-warning btn-sm" @click="rename_node">
			<i class="glyphicon glyphicon-pencil"></i>
		</button>
		<button type="button" class="btn btn-danger btn-sm" @click="delete_node">
			<i class="glyphicon glyphicon-remove"></i>
		</button>
	</div>
	<div class=" margin-between15 ">
		<input type="text" value="" id="search_input" placeholder="Search" />
	</div>
	<div class=" margin-between15" id="js-tree"></div>
	</div>
	
	<div class="col-md-9">
	<entity-tb></entity-tb>
	</div>

</div>
`,
	data : index_data ,
	created : function() {
		VueDirective()
	},
	mounted : function(){
		var thiscomp = this;
		$.get('/meta-data/subject.get', GetCallback(function(data){
			var treediv = $('#js-tree');
			thiscomp.jstree = new JsTree(treediv, data);
			thiscomp.jstree.init([ "state", "types", "wholerow", "dnd"]);
			thiscomp.jstree.on_select_node = function(node) {}
		}))
	},
	methods : {
		delete_node : function (){
			this.jstree.delete_node(function(sel, jstreeRef){
				$.post("/meta-data/subject.delete",
						{ids: JSON.stringify(sel)},
						function(d){
							if(d=="ok"){
								jstreeRef.delete_node(sel);
								VueAlert.Success("删除主题成功");
							}else{
								VueAlert.Warning(d);
							}
						});
			})
		},
		rename_node : function (){
			this.jstree.rename_node(function(node){
				$.post("/meta-data/subject.change",
						{parent_subject_area_id:node.parent, subject_area_id:node.id, subject_area_name:node.text},
						function(d){
							if(d=="ok"){
								VueAlert.Success("修改主题成功");
							}else{
								VueAlert.Warning(d);
							}
						});
			})
		},
		create_node : function (){
			this.jstree.create_node(null,function(node){
				$.post("/meta-data/subject.add",
						{parent_subject_area_id:node.parent, subject_area_id:node.id, subject_area_name:node.text},
						function(d){
							if(d=="ok"){
								VueAlert.Success("增加主题成功");
							}else{
								VueAlert.Warning(d);
							}
						});
			})
			
		}
	}
};
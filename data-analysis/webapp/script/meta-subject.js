function subject_data(){
	return{"username":username, jstree:null
	}
}
function entity_data(){
	return {items:[], attributes:[], isFull:1, 
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
		<button type="submit" class="btn btn-default">保存</button>
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
<div >
	<div>
	<vue-page ref="entity_page" class="pull-left pg-no-margin"
		v-bind:is-sm=true :get-data="get_entity"></vue-page>
	<div class="pull-right form-inline">
		<vue-filter :sm="true"></vue-filter>
		<vue-datepicker :sm="true" :search="getby_date"></vue-datepicker>&nbsp
		<div class="btn-group btn-group-sm">
			<button class="btn btn-default" @click="new_entity">新增实体</button>	
			<button class="btn btn-default " @click="isFull^=1">
				<i class="glyphicon" v-bind:class="isFull ? 'glyphicon-resize-full' : 'glyphicon-resize-small'"></i>
			</button>
			<button class="btn btn-default dropdown-toggle" data-toggle="dropdown" ><i class="glyphicon glyphicon-th icon-th"></i><span class="caret"></span></button>
			<ul class="dropdown-menu"> 
				<li v-for="attr in attributes" @click.stop="toggleColumn"><label><input type="checkbox" checked/>{{attr.desc}}</label></li>
			</ul>
		</div>
	</div>
	<div class="clear-both"></div>
	</div>

	<vue-table :is-full="isFull" :comp-items="items" :comp-attributes="attributes"
		:comp-update=modify_entity :comp-delete=delete_entity
		oper-title="操作" v-bind:operation="6"></vue-table>
	
	<vue-modal :lg="true" :footer-hidden="true" ref='new_entity' >
		<h4 slot="title">新增实体：</h4>
		<div slot="body">
			<entity-form :on-submit="new_entity_post"></entity-form>
		</div>
	</vue-modal>
	<vue-modal :lg="true" :footer-hidden="true" ref='modify_entity' >
		<h4 slot="title">修改实体：</h4>
		<div slot="body">
			<entity-form :on-submit="modify_entity_post" ref="modify_entity_form"></entity-form>
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
				thiscomp.$refs.entity_page.total=data.page.total
				thiscomp.$refs.entity_page.pageSize=data.page.limit
				thiscomp.$refs.entity_page.setPage(data.page.number)
			}))
		},
		getby_date:function(date){
			alert(date)
		},
		new_entity:function(){
			this.$refs.new_entity.show()
		},
		modify_entity:function(item){
			this.$refs.modify_entity.show()
			this.$refs.modify_entity_form.update(item)
		},
		delete_entity:function(item){
			var thiscomp = this
			$.post("/meta-data/entity.del", {"entity_id":item.entity_id}, function(data){
				if('ok'==data){
					VueAlert.Success('删除成功')
					thiscomp.$refs.entity_page.refreshPage()
				}else{
					VueAlert.Warning(data)
				}
			})
		},
		new_entity_post:function(event){
			var thiscomp = this
			var data = $(event.target).serialize()
			$.post("/meta-data/entity.add", data, function(data){
				if('ok'==data){
					VueAlert.Success('添加成功')
					thiscomp.$refs.entity_page.refreshPage()
				}else{
					VueAlert.Warning(data)
				}
			})
			this.$refs.new_entity.hide()
		},
		modify_entity_post:function(event){
			var thiscomp = this
			var data = $(event.target).serialize()
			$.post("/meta-data/entity.change", data, function(data){
				if('ok'==data){
					VueAlert.Success('修改成功');
					thiscomp.$refs.entity_page.refreshPage()
				}else{
					VueAlert.Warning(data);
				}
			})
			this.$refs.modify_entity.hide()
		},
		toggleColumn:function(e){
		}
	}
})

const VueSubject = {
	template: `
<div class="row padding-top15">
	<div class="padding-right-clear left-panel col-md-3">
		<div class="table-cell panel-inner" >
		<div class="margin-between15 input-group input-group-sm">
	      	<span class="input-group-btn">
			<button type="button" class="btn btn-success " @click="create_node">
				<i class="glyphicon glyphicon-plus"></i>
			</button>
			<button type="button" class="btn btn-warning " @click="rename_node">
				<i class="glyphicon glyphicon-pencil"></i>
			</button>
			<button type="button" class="btn btn-danger " @click="delete_node">
				<i class="glyphicon glyphicon-remove"></i>
			</button>
			</span>
			<input type="text" value="" id="search_input" placeholder="Search" class="form-control"/>
		</div>
		<div class=" margin-between15" id="js-tree"></div>
		</div>
		<div class="table-cell vertical-middle switch-btn">
			<i class="glyphicon glyphicon-chevron-left"></i>
		</div>
	</div>
	
	<div class="right-panel col-md-9 padding-left-clear">
	<vue-tabs ref="tabs" ></vue-tabs>
	</div>

</div>
`,
	mixins:[gMixin],
	data : index_data ,
	created : function() {
		this.VueDirective()
	},
	mounted : function(){
		var thiscomp = this;
		$.get('/meta-data/subject.get', GetCallback(function(data){
			var treediv = $('#js-tree')
			var jstree = new JsTree(treediv, data)
			jstree.search = $("#search_input")
			jstree.on_select_node = function(node) {
				thiscomp.$refs.tabs.addTab("实体列表-"+node.text,"entity"+node.id,"<entity-tb></entity-tb>")
			}
			jstree.init([ "state", "types", "wholerow", "dnd", "search"])
			thiscomp.jstree = jstree
		}))
		
		var el = this.$el
		var rp = el.querySelector(".right-panel")
		var sb = el.querySelector(".switch-btn")
		var lp = el.querySelector(".left-panel")
		$(sb).click(function(){
			$(rp).toggleClass('col-md-9 col-md-12')
			$(lp).toggleClass('col-md-3')
			$(rp).toggleClass('padding-left-clear right-panel-padding')
			$(lp).toggleClass('left-panel-hidden')
			$(this).find('i').toggleClass('glyphicon-chevron-left glyphicon-chevron-right')
		})
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
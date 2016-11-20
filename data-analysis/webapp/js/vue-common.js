Vue.component('vue-tabs', {
	template : `
<div>
	<ul class="nav nav-tabs nav-tabs-sm">
        <li v-for="(tab, index) in tabs">
        	<a v-bind:href="'#'+tab.id" data-toggle="tab">
        		{{tab.tab}}<button class="close" type="button" @click="removetab_(index, $event)">x</button>
        	</a>
        </li>
	</ul>
	<div class="tab-content padding-top15">
		<div v-if="withTag" class="tab-pane" v-bind:id="tab.id" v-for="(tab, index) in tabs">
			<div v-bind:is="tab.comp"></div>
		</div>
	</div>
</div>
`,
	props: {withTag:Boolean},
	data : function(){
		return {tabs : [], tabMap : {}}
	},
	methods : {
		addTab:function(tab,id,comp){
			var tab = {'tab':tab,'id':id, 'comp':comp}
			this.addTab_(tab)
		},
		addTab_:function(tab){
			if(this.tabMap[tab.id]){
				return
			}
			this.tabMap[tab.id]=tab
			this.tabs.push(tab)
			
			var Comp = Vue.extend({template: "<div class='tab-pane' id='"+tab.id+"' >"+tab.comp+"</div>"})
			var comp = new Comp().$mount()
			var content = this.$el.querySelector(".tab-content")
			$(content).append(comp.$el)
			
			var thiscomp = this
			this.$nextTick(function () {
				var a = thiscomp.$el.querySelector(".nav a[href='#"+tab.id+"']")
				$(a).tab('show')
			})
		},
		removetab_:function(index, event){
			var tabs = this.tabs
			var id = tabs[index].id
			delete this.tabMap[id]
			tabs.splice(index,1)
			
			var li = $(event.target).parent().parent()
			var neighbor = li.prev().length?li.prev():li.next()
			var thiscomp = this
			this.$nextTick(function () {
				var content = thiscomp.$el.querySelector("#"+id)
				if($(content).hasClass("active") && neighbor.length){
					neighbor.find("a").tab('show')
				}
				$(content).remove()
			})
		}
	}
})

Vue.component('vue-itemlist', {
	template : `
	<span>
		<span v-for="(item, index) in itemList" >{{index>0&&itemList.length>1?'，':''}}{{getAlias(item)}}</span>
	</span>`,
	props : {
		itemList : Array,
		aliasMap : Object
	},
	methods : {
		getAlias:function(item){
			var alias = null!=this.aliasMap&&this.aliasMap[item]?this.aliasMap[item]:item
			return alias
		}
	}
})

Vue.component('vue-well', {
  template: `
<div class="well">
	<slot name="title">default title</slot>
	<div class="row">
	<slot name="body">
      default body
    </slot>
	</div>
</div>
<!-- /.well -->`
})

Vue.component('vue-modal', {
  template: `<div class="modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document" v-bind:class="{ 'modal-sm': sm, 'modal-lg': lg }">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
            	<slot name="title">
              		default title
            	</slot>
				</h4>
			</div>
			<div class="modal-body">
            <slot name="body">
              default body
            </slot>
			</div>
			<div class="modal-footer" v-bind:class="{ 'hidden': footerHidden }">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary">确定</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->`,
	props : {
		sm:{ type: Boolean, default: false},
		lg:{ type: Boolean, default: false},
		footerHidden:{ type: Boolean, default: false}, 
		modalTitle:String, modalId:String},
	data : function(){
		return {};
	},
	methods:{
		show:function(){
			$(this.$el).modal('show')
		},
		hide:function(){
			$(this.$el).modal('hide')
		}
	}
})

Vue.component('vue-datepicker', {
	template: 
`<div class="input-group date" v-bind:class="{'input-group-sm':sm}">
	<input type="text" class="form-control" >
	<span class="input-group-addon" v-if="search" @click="search(date)"><i class="glyphicon glyphicon-search"></i></span>
</div>`,
	mounted(){
		var thiscomp = this
		$(this.$el.querySelector("input")).datepicker({language: "zh-CN"}).on("changeDate", function(e) {
			thiscomp.date = e.timeStamp
	    });
	},
	props:{sm:{ type: Boolean, default: false}, search:Function},
	data:function(){return {date:""}}
})

Vue.component('vue-filter',{
	template:`
	<div class="form-group">
    <label ></label>
    <input class="form-control" v-bind:class="{'input-sm':sm}" type="text" v-model:ignore="filter">
	</div>`,
	props:{ onSubmit:Function, debounce:{ type: Number, default: 1000}, sm:{ type: Boolean, default: false}},
	data: function(){return {filter:''}},
	methods: {
		do_filter: _.debounce(function () {
    		console.log("filtering")
    	},1000)
	},
	watch:{
		filter: function(data) {
			this.filter = data;
			this.do_filter();
		}
	}
})

var gMixin = {
  methods: {
    AbsUrl: function (url) {
      return GetAbsUrl(url)
    },
    VueDirective : function(){
    	Vue.directive('model', function (el, binding) {
    		if(binding.arg&&binding.arg=="ignore"){console.log(binding.arg);return}
    		el.name = binding.expression; 
    	});
    },
	CheckSysAdmin : function(){return 1&gRoles},
	CheckGuest : function(){return 2&gRoles},
	CheckSysAdminOrTenant(){return gRoles & (1|2)} 
  }
}
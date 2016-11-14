Vue.component('vue-tabs', {
	template : `
<div>
	<ul class="nav nav-tabs nav-tabs-sm">
        <li v-bind:class="{active:tab.active}" v-for="(tab, index) in tabs">
        	<a v-bind:href="'#'+tab.id" data-toggle="tab">
        		{{tab.tab}}<button class="close" type="button" @click="tabs.splice(index,1)">x</button>
        	</a>
        </li>
	</ul>
	<div class="tab-content padding-top15">
		<div v-bind:class="{active:tab.active}" class="tab-pane" v-bind:id="tab.id" v-for="(tab, index) in tabs">
			<div v-bind:is="tab.comp"></div>
		</div>
	</div>
</div>
`,
	created : function(){ 
		for(index in this.tabList)
			this.addTab(this.tabList[index].tab,this.tabList[index].id,null)
	},
	props : { tabList : Array },
	data : function(){
		return {tabs : []}
	},
	methods : {
		addTab:function(tab,id,comp){
			for(index in this.tabs){
				if(this.tabs[index].active)
					this.tabs[index].active=false
			}
			var tab = {'tab':tab,active:true,'id':id, 'comp':comp}
			this.addTab_(tab)
		},
		addTab_:function(tab){
			this.tabs.push(tab)
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
  template: `<div v-bind:id="modalId" class="modal fade" tabindex="-1" role="dialog">
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
	props : {sm:false,lg:false,footerHidden:false, modalTitle:String, modalId:String},
	data : function(){
		return {};
	}
})

function VueDirective(){
	Vue.directive('model', function (el, binding) {
		if(binding.arg&&binding.arg=="ignore"){console.log(binding.arg);return}
		el.name = binding.expression; 
	});
}

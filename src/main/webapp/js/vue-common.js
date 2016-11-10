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

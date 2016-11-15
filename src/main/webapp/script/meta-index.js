function index_data(){
	return{'username':username
	}
}

const VueIndex = {
	template: `
<div class="well" >
  	<h3>当前登录用户:<span class="label label-info">{{username}}</span></h3>
	<a href="/signout.get" class="btn btn-default btn-lg btn-block" role="button">退出</a>
</div>
`,
		data : index_data ,
		created : function() {
			Vue.directive('model', function (el, binding) {
				  el.name = binding.expression; 
			});
		},
		mounted : function(){
			var thiscomp = this;
		},
		methods : {
		}
};
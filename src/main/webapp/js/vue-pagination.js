Vue.component('vue-page', {
	template : `
<nav class="text-center" aria-label="Page navigation">
	<ul v-if="pageSize" class="pagination" v-bind:class="{'pagination-sm' : isSm}">
		<li v-if="isFirstPage"><a href="javascript:void(0)" @click="gotoPage(1)" aria-label="Previous">首页</a></li>
		<li v-if="isFirstPage"><a href="javascript:void(0)" @click="gotoPage(curPage-1)" ><span aria-hidden="true">&laquo;</span></a></li>
		<li v-for="page in pages" v-bind:class="{ 'active' : page.active}">
			<span v-if="page.active" >{{page.page}}</span>
			<a v-else href="javascript:void(0)" @click="gotoPage(page.page)" >{{page.page}}</a>
		</li>
		<li><a href="javascript:void(0)" @click="gotoPage(curPage)" ><span aria-hidden="true">跳至:</span></a></li>
		<li><span class="pg-sp-input"><input type='text' maxlength="2" size="2" v-model="curPage"></span></li>
		<li v-if="isEndPage"><a href="javascript:void(0)" @click="gotoPage(curPage+1)" ><span aria-hidden="true">&raquo;</span></a></li>
		<li v-if="isEndPage"><a href="javascript:void(0)" @click="gotoPage(getTotalPageNum())" aria-label="Next">尾页</a></li>
		
	</ul>
</nav>
`,
	props : {
		pageSize:Number,
		isSm:Boolean
	},
	data:function(){return {
		isFirstPage:false,
		isEndPage:false,
		pages:[],
		total:0,
		curPage:0
	}},
	methods : {
		gotoPage:function(pg){
			var reg = /^-?\d+$/;
			if(reg.test(pg))
				this.update(pg);
		},
		getPages:function(){
			this.pages = [];
			for(var i=0; i<5; i++){
				var page = this.curPage - 2 + i;
				if (page > 0 && page <= this.getTotalPageNum()) {
					this.pages.push({'page':page,'active':this.curPage==page?true:false});
				}
			}
		},
		getTotalPageNum:function(){
			return Math.ceil(this.total / this.pageSize);
		},
		update:function(curpage){
			this.curPage = curpage;
			this.isFirstPage = this.curPage>1;
			this.isEndPage = this.curPage<this.getTotalPageNum();
			this.getPages();
		}
	}
})
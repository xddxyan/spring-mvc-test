Vue.component('vue-table', {
	template : `
	<table class="table table-bordered table-striped table-condensed">
	<thead>
		<tr>
			<th v-if="null!=compAttributes" v-for="attribute in compAttributes">{{attribute.desc}}</th>
			<th v-if="operation" class="text-center oper-th">{{operTitle}}</th>
		</tr>
	</thead>
	<tbody>
		<tr v-for="item in compItems">
			<td v-for="attribute in compAttributes">
				<span v-if="null!=attribute.html" v-html="getHtml(item.data[attribute.attr], attribute.html)"></span>
				<span v-else>{{getContent(item,attribute.attr)}}</span>
			</td>
			
			<td v-if="operation" class="text-center">
				<a class="glyphicon glyphicon-search font-md" href="javascript:void(0)" v-if="operation&1" @click="compView(item)"></a>
				<a class="glyphicon glyphicon-pencil font-md" href="javascript:void(0)" v-if="operation&2" @click="on_update(item)"></a>
				<a class="glyphicon glyphicon-remove font-md" href="javascript:void(0)" v-if="operation&4" @click="on_delete(item)"></a>
				<a class="glyphicon glyphicon-ok font-md" href="javascript:void(0)" v-if="operation&8" @click="compOk(item)"></a>
			</td>
			
		</tr>
	</tbody>
</table>`,
	props : {
		compItems : Array,
		compAttributes : Array,
		compView : Function,
		compUpdate : Function,
		compDelete : Function,
		compOk : Function,
		operation:Number,
		operTitle:String
	},
	methods : {
		getHtml : function(data, html){
			if('glyphicon'==html)
				return '<span class="'+data+'"></span>';
			return data;
		},
		getContent(item, attr){
			var data = item.data?item.data:item;
			if(data && data[attr])
				return data[attr];
			return '';
		},
		on_update : function(item){
			if(this.compUpdate)
				this.compUpdate(item);
		},
		on_delete : function(item){
			if(this.compDelete)
				this.compDelete(item);
		}
		
	}
})
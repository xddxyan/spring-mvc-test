Vue.component('vue-table', {
	template : `
<div v-bind:class="isFull ? 'full-td' : 'auto-td'">
	<table class="table table-bordered table-striped table-condensed">
	<thead>
		<tr v-if="compAttributes.length">
			<th v-for="attribute in compAttributes">{{attribute.desc}}</th>
			
			<th v-if="operation" class="text-center oper-th">{{operTitle}}</th>
		</tr>
		<tr v-else>
			<th v-for="column in getRawColumns()">{{column}}</th>
		</tr>
	</thead>
	<tbody v-if="compAttributes.length">
		<tr v-for="item in compItems">
			<td v-for="attribute in compAttributes">
				<span v-if="null!=attribute.html" v-html="getHtml(item.data[attribute.attr], attribute.html)"></span>
				<span v-else>{{getContent(item,attribute.attr)}}</span>
			</td>
			
			<td v-if="operation" class="text-center">
				<a class="glyphicon glyphicon-search font-md" href="javascript:void(0)" v-if="operation&1" @click="compView(item)"></a>
				<a class="glyphicon glyphicon-pencil font-md" href="javascript:void(0)" v-if="operation&2" @click="compUpdate(item)"></a>
				<a class="glyphicon glyphicon-remove font-md" href="javascript:void(0)" v-if="operation&4" @click="compDelete(item)"></a>
				<a class="glyphicon glyphicon-ok font-md" href="javascript:void(0)" v-if="operation&8" @click="compOk(item)"></a>
			</td>
		</tr>
	</tbody>
	<tbody v-else>
		<tr v-for="item in compItems">
			<td v-for="(value,key) in item">{{value}}</td>
		</tr>
	</tbody>
	</table>
</div>`,
	props : {
		compItems : Array,
		compAttributes : Array,
		compView : Function,
		compUpdate : Function,
		compDelete : Function,
		compOk : Function,
		operation:Number,
		operTitle:String,
		isFull:Number
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
		getRawColumns(){
			var columns = []
			if(this.compItems&&this.compItems.length){
				for ( var col in this.compItems[0]) { columns.push(col) }
			}
			return columns
		}
	}
})
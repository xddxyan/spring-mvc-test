Vue.component('vue-alert', {
	template : `<div id="root">
		<div class="alert-fixed" >
		<div class="alert alert-dismissible fade in" role="alert" v-bind:class="elem.style" v-for="(elem, index) in elems">
			<span class="msgbox">
	            {{elem.msg}}
			</span>
			<button type="button" class="close" aria-label="Close" v-on:click="alertRemove(index)">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</div>
	</div>`,
	props :{elems : Array, removeAlert : Function},
	methods : {
		alertRemove:function(index){
			if(this.removeAlert)
				this.removeAlert(index);
	}}
});

var VueAlert = null; 
$(function(){
	VueAlert = {
		view : new Vue({
				template : '<vue-alert :elems="elems" :remove-alert="alertRemove" ></comp-alert>',
				el : '#vue-alert',
				data : {elems : []},
				methods : {
					alertRemove : function(index) {
						this.elems.splice(index, 1);
					},
					alertAdd : function(message, style){
						this.elems.push({ 'msg' :message, 'style' : style});
						window.setTimeout(function(elems){elems.shift();}, 3000, this.elems);
					}
				}
		}),
		Success : function(message){
			VueAlert.view.alertAdd(message, {'alert-success': true});
		},
		Warning : function(message){
			this.view.alertAdd(message, {'alert-warning': true});
		},
		Danger : function(message){
			this.view.alertAdd(message, {'alert-danger': true});
		}
	}
});

//ajax 加表表格,分页
function LoadBootstrapTable(div){
	this.div = div;
	this.div.on( "click", ".pagination a", {loadobj: this}, function(e){
	    e.preventDefault();
	    e.data.loadobj.loading($(this).attr("href"));
	});
}
LoadBootstrapTable.prototype.loading = function(url){ 
	this.div.load(url);
}
//bootstrap 风格alert
function BootstrapAlert(parent){
	this.parent = parent;
	this.alertDlg = $('<div class="alert alert-success alert-dismissible fade in hidden" role="alert">\
	<span class="msgbox"></span>\
	<button type="button" class="close" data-dismiss="alert" aria-label="Close">\
	<span aria-hidden="true">&times;</span>\
	</button>\
	</div>');
}
BootstrapAlert.prototype.alert = function(msg, onclose){
	var clone = this.alertDlg.clone().removeClass("hidden");
	clone.find(".msgbox").html(msg);
	clone.prependTo(this.parent).alert();
	CountDown(3,function(){clone.alert('close');});
	if(onclose)
		clone.on('closed.bs.alert', onclose);
}
//bootstrap 风格表单
function BootstrapForm(theform ){
	this.theform = theform;
}
BootstrapForm.prototype.init = function(fields){
	var body = this.theform.find(".modal-body");
	for(var i=0; i<fields.length; i++){
		var field = fields[i];
		body.append('<div class="form-group"> \
			    <label >'+field.label+'</label> \
			    <input type="text" class="form-control" name="'+field.name+'" > \
			  	</div>');
	}
}
BootstrapForm.prototype.onSubmit = function(callback){
	this.theform.submit({theform: this.theform}, function(e) {
		e.preventDefault(); // avoid to execute the actual submit of the form.
		$.ajax({
			type	: "POST",
			url		: e.data.theform.attr("action"),
			data	: e.data.theform.serialize(), // serializes the form's elements.
			success : callback
		});
	});
}
BootstrapForm.prototype.setInputByName = function(name, value, readonly){
	var input = this.theform.find("input[name='"+name+"']");
	this.setInput(input, value, readonly);
}
BootstrapForm.prototype.setInputByPos = function(pos, value, readonly){
	var inputs = this.theform.find("input");
	var input = inputs.eq(pos);
	this.setInput(input, value, readonly);
}
BootstrapForm.prototype.setInput = function(input, value, readonly){
	if(value)
		input.val(value);
	else
		input.val("");
	
	if(readonly)
		input.attr('readonly', readonly);
	else
		input.removeAttr('readonly');
}
BootstrapForm.prototype.setAction = function(url){
	this.theform.attr("action", url);
}




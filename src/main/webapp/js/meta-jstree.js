/* you provide variables below
 var jstreeNode = $('#subject-tree');
 var search = $('#search_input');
 var delete_func = function (sel, jstreeRef)
 var rename_func = function (node, status)
 var create_func = function (node, status)
 var select_func = function (node, status)
 */
 
function JsTree(div, jstreedata) {
	this.jstreeDiv = div;
	this.jstreedata = jstreedata; 
	this.search = null;
	this.on_select_node;
	this.on_change_node;
	this.create_node_check;
	this.move_node_check;
}

JsTree.prototype.init = function(plugins, isOnReady) {
	var thisjstree = this;
	if (null == this.jstreeDiv)
		return;
	if (null == plugins)
		plugins = [ "dnd", "search", "state", "types", "wholerow" ];
	this.jstreeDiv.jstree({
		"plugins" : plugins,
		"types" : {
	        "file" : {
	            "icon" : "jstree-file"
	        },
	        "logic-category" :{
	        	"icon" : "glyphicon glyphicon-th"
	        }
	    },
	    "dnd" : {
            "is_draggable" : function(node) {
                //if (node[0].type == 'file')
                return true
            },
            check_while_dragging: true
        },
		"core" : {
			'data' : this.jstreedata,
			'check_callback' : function(operation, node, node_parent, node_position, more) {
                // operation can be 'create_node', 'rename_node', 'delete_node', 'move_node' or 'copy_node'
                // in case of 'rename_node' node_position is filled with the new node name
				if (operation === "create_node") {
					return thisjstree.create_node_check?thisjstree.create_node_check(node_parent):true;
				}
				else if (operation === "move_node") {
					return thisjstree.move_node_check?thisjstree.move_node_check(node_parent):true;
                }
                return true;
            }
		}
	});
	
	if(isOnReady)
		this.jstreeDiv.on('ready.jstree', this._OnReady(this)).jstree();
	else
		this._OnReady(this)()

	if (null == this.search)
		return;
	var to = false;
	this.search.keyup(function() {
		if (to) {
			clearTimeout(to);
		}
		to = setTimeout(function() {
			var v = search.val();
			this.jstreeDiv.jstree(true).search(v);
		}, 250);
	});
}
JsTree.prototype._OnReady = function(jstree) {
	console.log('ready.jstree');
	var lastSelected = null;
	return function(e, data) {
		jstree.jstreeDiv.on('select_node.jstree', function( e, data ) {
			if(lastSelected && lastSelected == data.selected[0]){
				jstree.jstreeDiv.jstree(true).deselect_all(true)
				lastSelected = null
			}else
				lastSelected = data.selected[0]
			return jstree.on_select_node?jstree.on_select_node(data.node):null
		}).jstree();
	}
}
JsTree.prototype.get_instance = function(){
	return this.jstreeDiv.jstree(true)
}
JsTree.prototype.get_selected_nodes = function(){
	return this.get_instance().get_selected(true)
}
JsTree.prototype.get_selected_node = function(){
	var nodes = this.get_selected_nodes()
	if(nodes.length > 0)
		return nodes[0]
	return null
}
JsTree.prototype.create_node = function(type, create_func) {
	var ref = this.jstreeDiv.jstree(true), sel = ref.get_selected();
	if (!sel.length) {
		sel = ref.create_node("#", {
			"type" : type?type:"folder",
			"text" : "new root"
		});
		if (sel) {
			ref.edit(sel, null, create_func);
		}
	}
	sel = sel[0];
	sel = ref.create_node(sel, {
		"type" : type?type:"file",
		"text" : "new child"
	});
	if (sel) {
		ref.edit(sel, null, create_func);
	}
};
JsTree.prototype.rename_node = function(rename_func) {
	var ref = this.get_instance(), sel = ref.get_selected();
	if (!sel.length) {
		return false;
	}
	sel = sel[0];
	ref.edit(sel, null, rename_func);
};
JsTree.prototype.delete_node = function(delete_func) {
	var ref = this.get_instance(), sel = ref.get_selected();
	if (!sel.length) {
		return false;
	}
	delete_func(sel, ref);
};
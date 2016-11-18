package com.codechiev.model;

import java.util.List;

public class JsTreeNode {
	
	public String text;
	public String icon;
	public String state;
	public Object data;
	public List<JsTreeNode> children;
	public JsTreeNode( String text, Object data, List<JsTreeNode> children) {
		super();
		this.text = text;
		this.data = data;
		this.children = children;
	}
}
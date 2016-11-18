package com.codechiev.model;

public class JsTreeSimpleNode {
	/* Alternative format of the node (id & parent are required)
	{
	  id          : "string" // required
	  parent      : "string" // required
	  text        : "string" // node text
	  icon        : "string" // string for custom
	  state       : {
	    opened    : boolean  // is the node open
	    disabled  : boolean  // is the node disabled
	    selected  : boolean  // is the node selected
	  },
	  li_attr     : {}  // attributes for the generated LI node
	  a_attr      : {}  // attributes for the generated A node
	}*/
	
	public static final String ICON_FLASH = "glyphicon glyphicon-flash";
	public static final String ICON_HOUSE = "glyphicon glyphicon-home";
	public static final String ICON_USER = "glyphicon glyphicon-user";
	public static final String ICON_DBADDRESS = "glyphicon glyphicon-hdd";
	public static final String ICON_TABLE = "glyphicon glyphicon-th";
	public static final String ICON_CENTER = "glyphicon glyphicon-book";
	public static final String ICON_CATALOG ="glyphicon glyphicon-list-alt";
	
	public String id;
	public String parent = "#";
	public String text;
	public String icon;
	public String state;
	public Object data;
	public JsTreeSimpleNode(String id, String parent, String text, Object data) {
		super();
		this.id = id;
		this.parent = parent;
		this.text = text;
		this.data = data;
	}
	
}

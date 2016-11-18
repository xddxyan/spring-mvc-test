package org.copycat.framework.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.copycat.framework.annotation.AutoId;
import org.copycat.framework.annotation.Column;
import org.copycat.framework.annotation.Id;
import org.copycat.framework.annotation.Table;

public class DbUtil {
    
	public static String GetTable(Class<?> clazz){
		Table tb = clazz.getAnnotation(Table.class);
		if(null==tb)
			return "none_table";
		return tb.value();
	}
	
	public static String GetIdField(Class<?> clazz){
		Field[] fileds = clazz.getDeclaredFields();
		for(Field f:fileds){
			Column col = f.getAnnotation(Column.class);
			Id id = f.getAnnotation(Id.class);
			if(null!=id)
				return col.value();
			AutoId autoid = f.getAnnotation(AutoId.class);
			if(null!=autoid)
				return col.value();
		}
		return "none_id";
	}
	public static String GetIdName(Class<?> clazz){
		Field[] fileds = clazz.getDeclaredFields();
		for(Field f:fileds){
			Id id = f.getAnnotation(Id.class);
			if(null!=id)
				return f.getName();
			AutoId autoid = f.getAnnotation(AutoId.class);
			if(null!=autoid)
				return f.getName();
		}
		return "none_name";
	}
	
	public static List<Attr> GetAttrList(Class<?> clazz){
		List<Attr> list = new ArrayList<Attr>();
		Field[] fileds = clazz.getDeclaredFields();
		for(Field f:fileds){
			Column col = f.getAnnotation(Column.class);
			
			list.add(new Attr(f.getName(), col.name()));
		}
		return list;
	}
}
class Attr {
	public String attr;
	public String desc;

	public Attr(String attr, String desc) {
		super();
		this.attr = attr;
		this.desc = desc;
	}
}

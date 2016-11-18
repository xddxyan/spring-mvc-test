package com.codechiev.model.mybatis;

import java.lang.reflect.Field;
import java.util.Map;

import org.copycat.framework.annotation.Column;
import org.copycat.framework.annotation.Id;
import org.apache.ibatis.jdbc.SQL;
import org.copycat.framework.annotation.AutoId;
import org.copycat.framework.annotation.Table;

public class SqlProvider {
	static public String SelectByIdSql(Map<String, Object> params){
		String table = (String) params.get("table");
		String idField = (String) params.get("idField");
		StringBuilder sql = new StringBuilder().append("SELECT * FROM ");
		if(null!=table)sql.append(table).append(" WHERE ").append(idField).append(" = #{id}");
		return sql.toString();
	}
	static public String SelectByAutoIdSql(Map<String, Object> params){
		final String table = (String) params.get("table");
		return new SQL() {{
		    SELECT("*");
		    FROM(table);
		    WHERE("id = #{id}");
		}}.toString();

	}
	static public String SelectSql(Map<String, Object> params){
		String table = (String) params.get("table");
		String condition = (String) params.get("condition");
		StringBuilder sql = new StringBuilder().append("SELECT * FROM ");
		if(null!=table)sql.append(table);
		if(null!=condition)sql.append(condition);
		return sql.toString();
	}
	static public String CountSql(Map<String, Object> params){
		String table = (String) params.get("table");
		String condition = (String) params.get("condition");
		StringBuilder sql = new StringBuilder().append("SELECT count(*) FROM ");
		if(null!=table)sql.append(table);
		if(null!=condition)sql.append(condition);
		return sql.toString();
	}

	static public String DeleteByAutoIdSql(Map<String, Object> params){
		String table = (String) params.get("table");
		StringBuilder sql = new StringBuilder().append("DELETE FROM ");
		if(null!=table)sql.append(table).append(" WHERE id = #{id}");
		return sql.toString();
	}
	
	static public String DeleteByIdSql(Map<String, Object> params){
		String table = (String) params.get("table");
		String idField = (String) params.get("idField");
		StringBuilder sql = new StringBuilder().append("DELETE FROM ");
		if(null!=table)sql.append(table).append(" WHERE ").append(idField).append(" = #{id}");
		return sql.toString();
	}
	
	static public String UpdateSql(Object obj){
		Table tb = obj.getClass().getAnnotation(Table.class);
		Column column = null;
		String idField = null;
		StringBuilder fieldSb = new StringBuilder();
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field f:fields){
			column = f.getAnnotation(Column.class);
			if(null!=column ){
				if(!f.isAnnotationPresent(Id.class) && !f.isAnnotationPresent(AutoId.class)){
					fieldSb.append(f.getName()).
					append("= #{").append(f.getName()).append("},");
				}else{
					idField = f.getName();
				}
			}
		}
		fieldSb.setCharAt(fieldSb.length()-1, ' ');
		StringBuilder sql = new StringBuilder().append("UPDATE ");
		if(null!=tb)sql.append(tb.value()).append(" set ").append(fieldSb.toString()).
			append(" WHERE ").append(idField).append("=#{").append(idField).append("}");
		return sql.toString();
	}
	
	static public String InsertSql(Object obj){
		Table tb = obj.getClass().getAnnotation(Table.class);
		AutoId id = null;
		Column column = null;
		StringBuilder fieldSb = new StringBuilder();
		StringBuilder valueSb = new StringBuilder();
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field f:fields){
			column = f.getAnnotation(Column.class);
			if(null!=column ){
				if(!f.isAnnotationPresent(AutoId.class)){
					fieldSb.append(f.getName()).append(",");
					valueSb.append("#{").append(f.getName()).append("},");
				}else{
					id = f.getAnnotation(AutoId.class);
				}
			}
		}
		fieldSb.setCharAt(fieldSb.length()-1, ' ');
		valueSb.setCharAt(valueSb.length()-1, ' ');
		if(id!=null)
			return "INSERT into "+
			tb.value()+"(id,"+fieldSb.toString()+") VALUES(nextval('"+
			id.value()+"') ,"+valueSb.toString()+")";
		else
			return "INSERT into "+
			tb.value()+"("+fieldSb.toString()+") VALUES("+valueSb.toString()+")";
	}
	
	static public String ForeignKeySql(Map<String, Object> params){
			String table = (String) params.get("table");
			return "	SELECT "
					+ "tc.constraint_name, tc.table_name, kcu.column_name, "
					+ "ccu.table_name AS foreign_table_name, "
					+ "ccu.column_name AS foreign_column_name "
					+ "FROM "
					+ "information_schema.table_constraints AS tc "
					+ "JOIN information_schema.key_column_usage AS kcu "
					+ "ON tc.constraint_name = kcu.constraint_name "
					+ "JOIN information_schema.constraint_column_usage AS ccu "
					+ "ON ccu.constraint_name = tc.constraint_name "
					+ "WHERE constraint_type = 'FOREIGN KEY' AND tc.table_name='"+table+"'";
	}

}

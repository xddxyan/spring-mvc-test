package com.metasoft.model.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.metasoft.model.ForeignKey;

public interface GenericMapper<T> {
	
	@SelectProvider(method = "SelectByAutoIdSql", type = SqlProvider.class)
	public T selectByAutoId(@Param("id") long id, @Param("table") String table);
	
	@SelectProvider(method = "SelectSql", type = SqlProvider.class)
	public List<T> select(@Param("condition") String condition, @Param("table") String table);
	
	@SelectProvider(method = "CountSql", type = SqlProvider.class)
	public Integer count(@Param("condition") String condition, @Param("table") String table);
	
	@DeleteProvider(method = "DeleteByAutoIdSql", type = SqlProvider.class)
	public void deleteByAutoId(@Param("id") long id, @Param("table") String table);
	
	@DeleteProvider(method = "DeleteByIdSql", type = SqlProvider.class)
	public void deleteById(@Param("id") String id, @Param("idField") String idField, @Param("table") String table);
	
	@SelectProvider(method = "SelectByIdSql", type = SqlProvider.class)
	public T selectById(@Param("id") String id, @Param("idField") String idField, @Param("table") String table);
	
	@InsertProvider(method = "InsertSql", type = SqlProvider.class)
	public long insert(T t);
	@UpdateProvider(method = "UpdateSql", type = SqlProvider.class)
	public void update(T t); 

	@SelectProvider(method = "ForeignKeySql", type = SqlProvider.class)
	public List<ForeignKey> foreignKey(@Param("table") String table);
}

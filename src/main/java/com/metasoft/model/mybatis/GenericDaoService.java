package com.metasoft.model.mybatis;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.copycat.framework.Page;
import org.copycat.framework.util.DbUtil;

import com.metasoft.model.ForeignKey;

/**
 * @author metasoft
 * Generic data accessing object
 * 通用数据访问对象
 * @param <T>
 */
public abstract class GenericDaoService<T> {
	
	private final Class<?> TypeOfTable;

    public GenericDaoService() {
        Type superclass = getClass().getGenericSuperclass(); 
        ParameterizedType parameterized = (ParameterizedType) superclass;
        // with nested generic types, this becomes a little more complicated
        TypeOfTable = (Class<?>) parameterized.getActualTypeArguments()[0];
    }
	
	public abstract GenericMapper<T> getMapper();
	
	public String getTable() {
		return DbUtil.GetTable(TypeOfTable);
	}

	public String getIdField() {
		return DbUtil.GetIdName(TypeOfTable);
	}

	public void insert(T t) {
		getMapper().insert(t);
	}

	public void update(T t) {
		getMapper().update(t);
	}

	public T selectById(long id) {
		return getMapper().selectByAutoId(id, getTable());
	}	

	public List<T> selectByIdAsc( int offset, int size) {
		String condition = " order by "+getIdField()+" asc limit "+size+" offset "+offset;
		return getMapper().select( condition, getTable());
	}
	public List<T> selectPageByIdAsc( Page page) {
		String condition = " order by "+getIdField()+" asc limit "+page.getLimit()+" offset "+page.getOffset();
		page.setTotal(getMapper().count(null, getTable()));
		return getMapper().select( condition, getTable());
	}
	public List<T> selectPageByIdDesc( Page page, String where) {
		StringBuilder condition = new StringBuilder().append(where==null?"":where).
				append(" order by ").append(getIdField()).
				append(" desc limit ").append(page.getLimit()).
				append(" offset ").append(page.getOffset());
		page.setTotal(getMapper().count(where, getTable()));
		return getMapper().select( condition.toString(), getTable());
	}

	public void deleteByAutoId(long id) {
		getMapper().deleteByAutoId(id, getTable());
	}
	public void deleteById(String id) {
		getMapper().deleteById(id, getIdField(), getTable());
	}
	
	public List<ForeignKey> foreignKey() {
		return getMapper().foreignKey(getTable());
	}
}
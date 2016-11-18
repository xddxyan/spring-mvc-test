package com.codechiev.model.mybatis;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.copycat.framework.Page;
import org.copycat.framework.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.codechiev.model.ForeignKey;

/**
 * @author metasoft
 * Generic data accessing object
 * 通用数据访问对象
 * make sure spring version is greater than 4.0 while Autowired in superclass
 * @param <T>
 */
public abstract class GenericDaoService<T_DAO, T_MAPPER extends GenericMapper<T_DAO>> {
	
	@Autowired
	protected T_MAPPER mapper;
	private final Class<?> kDaoClass;

    public GenericDaoService() {
        Type superclass = getClass().getGenericSuperclass(); 
        ParameterizedType parameterized = (ParameterizedType) superclass;
        kDaoClass = (Class<?>) parameterized.getActualTypeArguments()[0];
    }
	
	public String getTable() {
		return DbUtil.GetTable(kDaoClass);
	}

	public String getIdField() {
		return DbUtil.GetIdName(kDaoClass);
	}

	public void insert(T_DAO t) {
		mapper.insert(t);
	}

	public void update(T_DAO t) {
		mapper.update(t);
	}

	public T_DAO selectById(long id) {
		return mapper.selectByAutoId(id, getTable());
	}	

	public List<T_DAO> selectByIdAsc( int offset, int size) {
		String condition = " order by "+getIdField()+" asc limit "+size+" offset "+offset;
		return mapper.select( condition, getTable());
	}
	public List<T_DAO> selectPageByIdAsc( Page page) {
		String condition = " order by "+getIdField()+" asc limit "+page.getLimit()+" offset "+page.getOffset();
		page.setTotal(mapper.count(null, getTable()));
		return mapper.select( condition, getTable());
	}
	public List<T_DAO> selectPageByIdDesc( Page page, String where) {
		StringBuilder condition = new StringBuilder().append(where==null?"":where).
				append(" order by ").append(getIdField()).
				append(" desc limit ").append(page.getLimit()).
				append(" offset ").append(page.getOffset());
		page.setTotal(mapper.count(where, getTable()));
		return mapper.select( condition.toString(), getTable());
	}

	public void deleteByAutoId(long id) {
		mapper.deleteByAutoId(id, getTable());
	}
	public void deleteById(String id) {
		mapper.deleteById(id, getIdField(), getTable());
	}
	
	public List<ForeignKey> foreignKey() {
		return mapper.foreignKey(getTable());
	}
}
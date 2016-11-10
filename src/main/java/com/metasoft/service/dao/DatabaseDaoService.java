package com.metasoft.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metasoft.model.mapper.DatabaseMapper;
import com.metasoft.model.metadata.Database;
import com.metasoft.model.mybatis.GenericDaoService;

@Service
public class DatabaseDaoService extends GenericDaoService<Database> {

	@Autowired
	private DatabaseMapper mapper;
	@Override
	public DatabaseMapper getMapper() {
		return mapper;
	}

	public void setMapper(DatabaseMapper mapper) {
		this.mapper = mapper;
	}

}
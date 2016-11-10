package com.metasoft.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metasoft.model.mapper.SchemaMapper;
import com.metasoft.model.metadata.Schema;
import com.metasoft.model.mybatis.GenericDaoService;

@Service
public class SchemaDaoService extends GenericDaoService<Schema> {

	@Autowired
	private SchemaMapper mapper;
	@Override
	public SchemaMapper getMapper() {
		return mapper;
	}

	public void setMapper(SchemaMapper mapper) {
		this.mapper = mapper;
	}

}
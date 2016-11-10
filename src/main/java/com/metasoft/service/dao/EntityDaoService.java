package com.metasoft.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metasoft.model.mapper.EntityMapper;
import com.metasoft.model.metadata.Entity;
import com.metasoft.model.mybatis.GenericDaoService;

@Service
public class EntityDaoService extends GenericDaoService<Entity> {

	@Autowired
	private EntityMapper mapper;
	@Override
	public EntityMapper getMapper() {
		return mapper;
	}

	public void setMapper(EntityMapper mapper) {
		this.mapper = mapper;
	}

}
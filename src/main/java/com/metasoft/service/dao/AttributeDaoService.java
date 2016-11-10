package com.metasoft.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metasoft.model.mapper.AttributeMapper;
import com.metasoft.model.metadata.Attribute;
import com.metasoft.model.mybatis.GenericDaoService;

@Service
public class AttributeDaoService extends GenericDaoService<Attribute> {

	@Autowired
	private AttributeMapper mapper;
	@Override
	public AttributeMapper getMapper() {
		return mapper;
	}

	public void setMapper(AttributeMapper mapper) {
		this.mapper = mapper;
	}

}
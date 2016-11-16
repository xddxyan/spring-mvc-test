package com.metasoft.service.dao;

import org.springframework.stereotype.Service;

import com.metasoft.model.mapper.AttributeMapper;
import com.metasoft.model.metadata.Attribute;
import com.metasoft.model.mybatis.GenericDaoService;

@Service
public class AttributeDaoService extends GenericDaoService<Attribute, AttributeMapper> {

}
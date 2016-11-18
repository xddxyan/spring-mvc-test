package com.codechiev.service.dao;

import org.springframework.stereotype.Service;

import com.codechiev.model.mapper.AttributeMapper;
import com.codechiev.model.metadata.Attribute;
import com.codechiev.model.mybatis.GenericDaoService;

@Service
public class AttributeDaoService extends GenericDaoService<Attribute, AttributeMapper> {

}
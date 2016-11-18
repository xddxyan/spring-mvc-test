package com.codechiev.service.dao;

import org.springframework.stereotype.Service;

import com.codechiev.model.mapper.SchemaMapper;
import com.codechiev.model.metadata.Schema;
import com.codechiev.model.mybatis.GenericDaoService;

@Service
public class SchemaDaoService extends GenericDaoService<Schema,SchemaMapper> {

}
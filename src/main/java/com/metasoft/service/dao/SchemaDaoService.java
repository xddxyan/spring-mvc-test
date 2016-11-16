package com.metasoft.service.dao;

import org.springframework.stereotype.Service;

import com.metasoft.model.mapper.SchemaMapper;
import com.metasoft.model.metadata.Schema;
import com.metasoft.model.mybatis.GenericDaoService;

@Service
public class SchemaDaoService extends GenericDaoService<Schema,SchemaMapper> {

}
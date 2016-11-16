package com.metasoft.service.dao;

import org.springframework.stereotype.Service;

import com.metasoft.model.mapper.DatabaseMapper;
import com.metasoft.model.metadata.Database;
import com.metasoft.model.mybatis.GenericDaoService;

@Service
public class DatabaseDaoService extends GenericDaoService<Database,DatabaseMapper> {

}
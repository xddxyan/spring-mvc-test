package com.codechiev.service.dao;

import org.springframework.stereotype.Service;

import com.codechiev.model.mapper.DatabaseMapper;
import com.codechiev.model.metadata.Database;
import com.codechiev.model.mybatis.GenericDaoService;

@Service
public class DatabaseDaoService extends GenericDaoService<Database,DatabaseMapper> {

}
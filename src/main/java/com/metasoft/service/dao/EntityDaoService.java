package com.metasoft.service.dao;

import org.springframework.stereotype.Service;

import com.metasoft.model.mapper.EntityMapper;
import com.metasoft.model.metadata.Entity;
import com.metasoft.model.mybatis.GenericDaoService;

@Service
public class EntityDaoService extends GenericDaoService<Entity,EntityMapper> {

}
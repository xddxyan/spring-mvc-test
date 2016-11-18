package com.codechiev.service.dao;

import org.springframework.stereotype.Service;

import com.codechiev.model.mapper.EntityMapper;
import com.codechiev.model.metadata.Entity;
import com.codechiev.model.mybatis.GenericDaoService;

@Service
public class EntityDaoService extends GenericDaoService<Entity,EntityMapper> {

}
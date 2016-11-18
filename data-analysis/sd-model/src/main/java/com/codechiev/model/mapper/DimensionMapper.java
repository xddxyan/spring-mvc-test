package com.codechiev.model.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

import com.codechiev.model.metadata.Dimension;
import com.codechiev.model.mybatis.GenericMapper;
import com.codechiev.service.dao.SubjectDaoService;

public interface DimensionMapper extends GenericMapper<Dimension>{
	@DeleteProvider(method = "DeleteCascadeSql", type = SubjectDaoService.class)
	void deleteCascade(@Param("rootid") String rootid);
}

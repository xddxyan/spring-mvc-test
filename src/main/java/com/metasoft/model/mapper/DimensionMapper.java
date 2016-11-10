package com.metasoft.model.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

import com.metasoft.model.metadata.Dimension;
import com.metasoft.model.mybatis.GenericMapper;
import com.metasoft.service.dao.SubjectDaoService;

public interface DimensionMapper extends GenericMapper<Dimension>{
	@DeleteProvider(method = "DeleteCascadeSql", type = SubjectDaoService.class)
	void deleteCascade(@Param("rootid") String rootid);
}

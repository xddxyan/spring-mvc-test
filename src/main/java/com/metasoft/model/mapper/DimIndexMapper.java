package com.metasoft.model.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

import com.metasoft.model.metadata.DimIndex;
import com.metasoft.model.mybatis.GenericMapper;
import com.metasoft.service.dao.SubjectDaoService;

public interface DimIndexMapper extends GenericMapper<DimIndex>{
	@DeleteProvider(method = "DeleteCascadeSql", type = SubjectDaoService.class)
	void deleteCascade(@Param("rootid") String rootid);
	
	
}

package com.metasoft.model.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

import com.metasoft.model.metadata.Subject;
import com.metasoft.model.mybatis.GenericMapper;
import com.metasoft.service.dao.SubjectDaoService;

public interface DimInfoMapper extends GenericMapper<Subject>{
	@DeleteProvider(method = "DeleteCascadeSql", type = SubjectDaoService.class)
	void deleteCascade(@Param("rootid") String rootid);
}

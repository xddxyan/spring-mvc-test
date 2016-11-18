package com.codechiev.model.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

import com.codechiev.model.metadata.Subject;
import com.codechiev.model.mybatis.GenericMapper;
import com.codechiev.service.dao.SubjectDaoService;

public interface SubjectMapper extends GenericMapper<Subject>{
	@DeleteProvider(method = "DeleteCascadeSql", type = SubjectDaoService.class)
	void deleteCascade(@Param("rootid") String rootid);
}

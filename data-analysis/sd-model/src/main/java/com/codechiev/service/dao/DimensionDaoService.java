package com.codechiev.service.dao;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codechiev.model.mapper.DimensionMapper;
import com.codechiev.model.metadata.Dimension;
import com.codechiev.model.mybatis.GenericDaoService;

@Service
public class DimensionDaoService extends GenericDaoService<Dimension,DimensionMapper> {

	@Transactional
	public void update(Dimension s) {
		super.update(s);
	}
	
	/**
	 * Using RECURSIVE, a WITH query can refer to its own output.
	 */
	public static String DeleteCascadeSql(Map<String, Object> params){
		String rootid = (String) params.get("rootid");
		if(null == rootid )
			return "";
		return "WITH RECURSIVE AllChildren(subject_area_id, parent_subject_area_id, root_id) AS( "
				+ "select t1.subject_area_id::varchar, t1.parent_subject_area_id::varchar, t1.subject_area_id as root_id "
				+ "from i_metadata_subject_area as t1 where t1.subject_area_id = '"+rootid+"' "
				+ "union all "
				+ "select t2.subject_area_id::varchar, t2.parent_subject_area_id::varchar, ac.subject_area_id as root_id "
				+ "from i_metadata_subject_area as t2 inner join AllChildren as ac on t2.parent_subject_area_id = ac.subject_area_id"
				+ ") "
				+ "delete from i_metadata_subject_area where subject_area_id in (SELECT subject_area_id from AllChildren);";
	}

	@Transactional
	public void deleteCascade(String id) {
		mapper.deleteCascade(id);
	}
}
package com.metasoft.service.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metasoft.model.mapper.DimIndexMapper;
import com.metasoft.model.metadata.DimIndex;
import com.metasoft.model.mybatis.GenericDaoService;

@Service
public class DimIndexDaoService extends GenericDaoService<DimIndex,DimIndexMapper> {

	@Transactional
	public void update(DimIndex s) {
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

	public List<DimIndex> selectByIdAsc( int offset, int size) {
		String condition = " order by "+super.getIdField()+" asc limit "+size+" offset "+offset;
		return mapper.select( condition, getTable());
	}
}
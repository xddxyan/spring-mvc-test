package com.metasoft.model.metadata;

import org.copycat.framework.annotation.Column;
import org.copycat.framework.annotation.Id;
import org.copycat.framework.annotation.Table;

@Table("i_metadata_dim_index")
public class DimIndex {
	@Id
	@Column(name="维度目录id")
	private String dim_index_id = "";
	@Column("目录名称")
	private String dim_index_name = "";
	@Column("父级目录id")
	private String parent_dim_index_id="";
	public String getDim_index_id() {
		return dim_index_id;
	}
	public void setDim_index_id(String dim_index_id) {
		this.dim_index_id = dim_index_id;
	}
	public String getDim_index_name() {
		return dim_index_name;
	}
	public void setDim_index_name(String dim_index_name) {
		this.dim_index_name = dim_index_name;
	}
	public String getParent_dim_index_id() {
		return parent_dim_index_id;
	}
	public void setParent_dim_index_id(String parent_dim_index_id) {
		this.parent_dim_index_id = parent_dim_index_id;
	}

}

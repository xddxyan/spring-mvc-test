package com.metasoft.model.metadata;

import org.copycat.framework.annotation.Column;
import org.copycat.framework.annotation.Id;
import org.copycat.framework.annotation.Table;

@Table("i_metadata_subject_area")
public class Subject {
	@Id
	@Column("subject_area_id")
	private String subject_area_id = "";
	@Column("subject_area_name")
	private String subject_area_name = "";
	@Column("parent_subject_area_id")
	private String parent_subject_area_id="";

	public String getSubject_area_id() {
		return subject_area_id;
	}

	public void setSubject_area_id(String subject_area_id) {
		this.subject_area_id = subject_area_id;
	}

	public String getSubject_area_name() {
		return subject_area_name;
	}

	public void setSubject_area_name(String subject_area_name) {
		this.subject_area_name = subject_area_name;
	}

	public String getParent_subject_area_id() {
		return parent_subject_area_id;
	}

	public void setParent_subject_area_id(String parent_subject_area_id) {
		this.parent_subject_area_id = parent_subject_area_id;
	}

}

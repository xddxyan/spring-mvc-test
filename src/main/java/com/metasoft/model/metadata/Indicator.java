package com.metasoft.model.metadata;

import org.copycat.framework.annotation.Column;
import org.copycat.framework.annotation.Table;

@Table("i_metadata_index")
public class Indicator {
		@Column("metadata_index")
		private String attribute_id;
		@Column( name = "实体名称")
		private String attribute_name;
		@Column( name = "实体id")
		private String entity_id;
		@Column( name = "栏位")
		private String column_name;
		@Column( name = "栏位描述")
		private String column_desc;
		@Column( name = "数据类型")
		private String data_type;
		@Column( name = "长度")
		private int length;
		@Column( name = "精度")
		private int precision;
		@Column( name = "序列")
		private int attribute_seq;
		public String getAttribute_id() {
			return attribute_id;
		}
		public void setAttribute_id(String attribute_id) {
			this.attribute_id = attribute_id;
		}
		public String getAttribute_name() {
			return attribute_name;
		}
		public void setAttribute_name(String attribute_name) {
			this.attribute_name = attribute_name;
		}
		public String getEntity_id() {
			return entity_id;
		}
		public void setEntity_id(String entity_id) {
			this.entity_id = entity_id;
		}
		public String getColumn_name() {
			return column_name;
		}
		public void setColumn_name(String column_name) {
			this.column_name = column_name;
		}
		public String getColumn_desc() {
			return column_desc;
		}
		public void setColumn_desc(String column_desc) {
			this.column_desc = column_desc;
		}
		public String getData_type() {
			return data_type;
		}
		public void setData_type(String data_type) {
			this.data_type = data_type;
		}
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
		public int getPrecision() {
			return precision;
		}
		public void setPrecision(int precision) {
			this.precision = precision;
		}
		public int getAttribute_seq() {
			return attribute_seq;
		}
		public void setAttribute_seq(int attribute_seq) {
			this.attribute_seq = attribute_seq;
		}
}

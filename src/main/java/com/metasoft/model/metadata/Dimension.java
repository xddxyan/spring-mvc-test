package com.metasoft.model.metadata;

import org.copycat.framework.annotation.Column;
import org.copycat.framework.annotation.Id;
import org.copycat.framework.annotation.Table;

@Table("i_metadata_dimension")
public class Dimension {
		@Id
		@Column( name="维度id")
		private String dimension_id;
		@Column( name= "维度名称")
		private String dimension_name = "";
		@Column( name="维度描述")
		private String dimension_desc = "";
		@Column( name="维度类型")
		private int dimemsion_type;
		@Column( name= "创建时间")
		private long create_time;
		@Column( name= "创建用户")
		private String create_user = "";
		@Column( name="上次修改时间")
		private long last_modify_time;
		
		public String getDimension_id() {
			return dimension_id;
		}
		public void setDimension_id(String dimension_id) {
			this.dimension_id = dimension_id;
		}
		public String getDimension_name() {
			return dimension_name;
		}
		public void setDimension_name(String dimension_name) {
			this.dimension_name = dimension_name;
		}
		public String getDimension_desc() {
			return dimension_desc;
		}
		public void setDimension_desc(String dimension_desc) {
			this.dimension_desc = dimension_desc;
		}
		public int getDimemsion_type() {
			return dimemsion_type;
		}
		public void setDimemsion_type(int dimemsion_type) {
			this.dimemsion_type = dimemsion_type;
		}
		public long getCreate_time() {
			return create_time;
		}
		public void setCreate_time(long create_time) {
			this.create_time = create_time;
		}
		public String getCreate_user() {
			return create_user;
		}
		public void setCreate_user(String create_user) {
			this.create_user = create_user;
		}
		public long getLast_modify_time() {
			return last_modify_time;
		}
		public void setLast_modify_time(long last_modify_time) {
			this.last_modify_time = last_modify_time;
		}

}

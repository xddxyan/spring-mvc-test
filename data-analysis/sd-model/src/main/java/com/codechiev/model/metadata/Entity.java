package com.codechiev.model.metadata;

import org.copycat.framework.annotation.Column;
import org.copycat.framework.annotation.Id;
import org.copycat.framework.annotation.Table;

@Table("i_metadata_entity")
public class Entity {
		@Id
		@Column( name="实体id")
		private String entity_id;
		@Column( name= "实体名称")
		private String entity_name = "";
		@Column( name="数据库id")
		private String database_id = "";
		@Column( name="数据库模式")
		private String schema = "";
		@Column( name="表名")
		private String table_name = "";
		@Column( name="表描述")
		private String table_desc = "";
		@Column( name= "创建时间")
		private long create_time;
		@Column( name= "创建用户")
		private String create_user = "";
		@Column( name="上次修改时间")
		private long last_modify_time;
		@Column( name="序列号")
		private int sequence;
		public String getEntity_id() {
			return entity_id;
		}
		public void setEntity_id(String entity_id) {
			this.entity_id = entity_id;
		}
		public String getEntity_name() {
			return entity_name;
		}
		public void setEntity_name(String entity_name) {
			this.entity_name = entity_name;
		}
		public String getDatabase_id() {
			return database_id;
		}
		public void setDatabase_id(String database_id) {
			this.database_id = database_id;
		}
		public String getSchema() {
			return schema;
		}
		public void setSchema(String schema) {
			this.schema = schema;
		}
		public String getTable_name() {
			return table_name;
		}
		public void setTable_name(String table_name) {
			this.table_name = table_name;
		}
		public String getTable_desc() {
			return table_desc;
		}
		public void setTable_desc(String table_desc) {
			this.table_desc = table_desc;
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
		public int getSequence() {
			return sequence;
		}
		public void setSequence(int sequence) {
			this.sequence = sequence;
		}
		

}

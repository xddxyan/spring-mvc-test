package com.metasoft.model.metadata;

import org.copycat.framework.annotation.Column;
import org.copycat.framework.annotation.Table;

@Table("i_metadata_schema")
public class Schema {
		@Column("database_id")
		private String database_id="";
		@Column("schema")
		private String schema="";

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
}

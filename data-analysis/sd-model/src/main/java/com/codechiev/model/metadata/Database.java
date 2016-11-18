package com.codechiev.model.metadata;

import org.copycat.framework.annotation.Column;
import org.copycat.framework.annotation.Id;
import org.copycat.framework.annotation.Table;

@Table("i_metadata_database")
public class Database {
		@Id
		@Column(name = "id")
		private String database_id="";
		@Column(name = "驱动名")
		private String database_name="";
		@Column( name = "用户名")
		private String database_username="";
		@Column( name = "密码")
		private String database_password="";
		@Column( name = "jdbc连接地址")
		private String database_connection="";
		public String getDatabase_id() {
			return database_id;
		}
		public void setDatabase_id(String database_id) {
			this.database_id = database_id;
		}
		public String getDatabase_name() {
			return database_name;
		}
		public void setDatabase_name(String database_name) {
			this.database_name = database_name;
		}
		public String getDatabase_username() {
			return database_username;
		}
		public void setDatabase_username(String database_username) {
			this.database_username = database_username;
		}
		public String getDatabase_password() {
			return database_password;
		}
		public void setDatabase_password(String database_password) {
			this.database_password = database_password;
		}
		public String getDatabase_connection() {
			return database_connection;
		}
		public void setDatabase_connection(String database_connection) {
			this.database_connection = database_connection;
		}
}

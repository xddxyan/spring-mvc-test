package com.codechiev.model;

import org.copycat.framework.annotation.Column;

public class ForeignKey {
	@Column("constraint_name")
	private String constraint_name;
	@Column("table_name")
	private String table_name;
	@Column("column_name")
	private String column_name;
	@Column("foreign_table_name")
	private String foreign_table_name;
	@Column("foreign_column_name")
	private String foreign_column_name;
	public String getConstraint_name() {
		return constraint_name;
	}
	public void setConstraint_name(String constraint_name) {
		this.constraint_name = constraint_name;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getForeign_table_name() {
		return foreign_table_name;
	}
	public void setForeign_table_name(String foreign_table_name) {
		this.foreign_table_name = foreign_table_name;
	}
	public String getForeign_column_name() {
		return foreign_column_name;
	}
	public void setForeign_column_name(String foreign_column_name) {
		this.foreign_column_name = foreign_column_name;
	}


}
新增普通维度:  
维度名称(品牌),
维度类型(普通维度),
维度描述,

create table i_metadata_dim_index(
	dim_index_id varchar(32) not null,
	dim_index_name varchar(64) not null,
	parent_dim_index_id varchar(32) not null
	);
	
create table i_metadata_dim_index_dimension(
	dim_index_id varchar(32) not null,
	dimension_id varchar(32) not null
	);

create table i_metadata_dimension(   
	dimension_id varchar(32) not null,
	primary key (dimension_id),
	dimension_name varchar(256) not null,--名称
	dimension_desc varchar(4096) not null,--描述
	dimemsion_type int not null,--维度类型,普通,层次
	create_user varchar(64) not null,
	create_time bigint not null,
	last_modify_time bigint not null
);
--维度信息
create table i_metadata_dim_info(
	dimension_id varchar(32) not null,
	dimension_table varchar(64) not null,--维表名称
	layer_id integer not null,
	layer_name varchar(64) not null,
	code_field 
	order_field
	name_field
	order_type
);

alter table i_metadata_dim_index
   add constraint foreign key (database_id)
      references i_metadata_dimension (database_id)
      on delete restrict on update restrict;
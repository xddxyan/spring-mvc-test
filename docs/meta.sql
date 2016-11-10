--主题域
create sequence i_metadata_subject_area_seq start 1 increment by 1;
create table i_metadata_subject_area(
	id bigint not null ,
	primary key ( id ),
	subject_area_id varchar(32) not null unique,
	subject_area_name varchar(256) not null,
	parent_subject_area_id varchar(32) not null
);

--数据库表
create sequence i_metadata_database_seq start 1 increment by 1;
create table i_metadata_database(
	id bigint not null ,
	primary key ( id ),
	database_id varchar(32) not null unique,
	database_name varchar(64) not null,
	database_username varchar(64) not null,
	database_password varchar(1024) not null,
	database_connection varchar(256) not null
);

--数据库用户表
create sequence i_metadata_schema_seq start 1 increment by 1;
create table i_metadata_schema(
	id bigint not null ,
	primary key ( id ),
	schema varchar(64) not null unique,
	database_id varchar(32) not null,
	foreign key ( database_id ) references i_metadata_database ( database_id )
);

--实体表
create sequence i_metadata_entity_seq start 1 increment by 1;
create table i_metadata_entity(
	id bigint not null ,
	primary key ( id ) ,
	entity_id varchar(32) not null unique,--实体编号
	entity_name varchar(256) not null,--实体名称
	database_id varchar(32) not null,--数据库编号
	foreign key ( database_id ) references i_metadata_database ( database_id ),
	schema varchar(64) not null,--用户名
	foreign key ( schema ) references i_metadata_schema ( schema ),
	table_name varchar(64) not null,--表名
	table_desc varchar(4096) not null,--实体表描述
	create_time bigint not null,--创建时间
	create_user varchar(64) not null,--创建人
	last_modify_time bigint not null,--最后修改时间
	sequence int not null--顺序号
);

--实体与主题关系
create sequence i_metadata_entity_subject_seq start 1 increment by 1;
create table i_metadata_entity_subject(
	id bigint not null ,
	primary key ( id ) ,
	entity_id varchar(32) not null,
	foreign key ( entity_id ) references i_metadata_entity ( entity_id ),
	subject_area_id varchar(32) not null,
	foreign key ( subject_area_id ) references i_metadata_subject_area ( subject_area_id )
);

--实体访问统计
create sequence i_metadata_entity_access_statics_seq start 1 increment by 1;
create table i_metadata_entity_access_statics(
	id bigint not null,
	primary key ( id ) ,
	entity_id varchar(32) not null,
	foreign key ( entity_id ) references i_metadata_entity ( entity_id ),
	total_count integer not null,--总访问次数
	latest_count integer not null--近三个月访问次数
);

--实体属性表
create sequence i_metadata_attribute_seq start 1 increment by 1;
create table i_metadata_attribute(
	id bigint not null,
	primary key (id),
	attribute_id varchar(32) not null unique,
	attribute_name varchar(256) not null,
	entity_id varchar(32) not null,
	foreign key ( entity_id ) references i_metadata_entity ( entity_id ),
	column_name varchar(64) not null,
	column_desc varchar(4096) not null,
	data_type varchar(64) not null,
	length integer not null,
	precision integer not null,
	attribute_seq integer not null
);

--实体约束表
create sequence i_metadata_constraint_seq start 1 increment by 1;
create table i_metadata_constraint(
	id bigint not null,
	primary key (id) ,
	constraint_id varchar(32) not null unique,
	ref_constraint_id varchar(32) not null,
	foreign key ( ref_constraint_id ) references i_metadata_constraint ( constraint_id ),
	constraint_type varchar(1) not null,--约束类型
	entity_id varchar(32) not null,
	foreign key ( entity_id ) references i_metadata_entity ( entity_id )
);

--实体约束属性
create sequence i_metadata_constraint_attr_seq start 1 increment by 1;
create table i_metadata_constraint_attr(
	id bigint not null,
	constraint_id varchar(32) not null,
	foreign key ( constraint_id ) references i_metadata_constraint ( constraint_id ),
	attribute_id varchar(32) not null,
	foreign key ( attribute_id ) references i_metadata_attribute ( attribute_id ),
	primary key (id)
);

--实体对应物理表,仅限于维表分表时使用
create sequence i_metadata_entity_tables_seq start 1 increment by 1;
create table i_metadata_entity_tables(
	id bigint not null,
	primary key (id),
	entity_id varchar(32) not null,
	foreign key ( entity_id ) references i_metadata_entity ( entity_id ),
	database_id varchar(32) not null,
	foreign key ( database_id ) references i_metadata_database ( database_id ),
	schema varchar(64) not null,
	foreign key ( schema ) references i_metadata_schema ( schema ),
	table_name varchar(64) not null,
	validate_time bigint not null
);

--属性访问统计
create sequence i_metadata_attr_access_statics_seq start 1 increment by 1;
create table i_metadata_attr_access_statics(
	id bigint not null,
	attribute_id varchar(32) not null,
	foreign key ( attribute_id ) references i_metadata_attribute ( attribute_id ),
	total_count integer not null,--总访问次数
	latest_count integer not null--近三个月访问次数
);

--drop table if exists i_metadata_subject_area;
--drop table if exists i_metadata_entity_subject;

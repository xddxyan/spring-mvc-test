--Terminology
--指标->index
--口径->specification

--指标
create sequence metadata_ind_type_seq start 1 increment by 1;
create table metadata_ind_type(
	id bigint not null ,
	primary key ( id ) ,
	index_type_code varchar(2) not null unique,
	index_type_name varchar(32) not null
)

--指标
create sequence metadata_index_seq start 1 increment by 1;
create table metadata_index(
	id bigint not null ,
	primary key ( id ) ,
	index_id varchar(32) not null unique,
	index_type_code varchar(2) not null,--
	foreign key index_type_code references metadata_ind_type ( index_type_code ),
	unit varchar(32) not null,--
	format_type_code varchar(2) not null,
	--foreign key 
	precsion integer not null,
	create_time bigint not null,
	create_user varchar(64) not null,
	sequence integer not null,
	latest_ind_version_id varchar(32) not null
)

--指标口径
create sequence metadata_ind_spec_seq start 1 increment by 1;
create table metadata_ind_spec(
	id bigint not null ,
	primary key ( id ) ,
	index_id varchar(32) not null,
	business_spec varchar(4096) not null,--业务口径
	technology_spec varchar(4096) not null,--技术口径
	attachment_name varchar(256) not null,
	attachment blob
)
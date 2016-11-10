create table i_metadata_index(
	index_id varchar(32) not null,
	primary key ( index_id ) ,
	index_type_code varchar(2) not null,--
	unit varchar(32) not null,--
	format_type_code varchar(2) not null,
	precsion integer not null,
	create_time bigint not null,
	create_user varchar(64) not null,
	seq integer not null,
	latest_ind_version_id varchar(32) not null
)

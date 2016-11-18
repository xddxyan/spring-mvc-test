/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2016/9/19 12:45:21                           */
/*==============================================================*/


drop table i_metadata_attr_access_statics;

drop table i_metadata_attribute;

drop table i_metadata_database;

drop table i_metadata_entity;

drop table i_metadata_entity_access_static;

drop table i_metadata_entity_subject;

drop table i_metadata_entity_tables;

drop table i_metadata_schema;

drop table i_metadata_subject_area;

/*==============================================================*/
/* Table: i_metadata_attr_access_statics                        */
/*==============================================================*/
create table i_metadata_attr_access_statics (
   attribute_id         VARCHAR(32)          not null,
   total_count          INT4                 not null,
   latest_count         INT4                 not null
);

comment on column i_metadata_attr_access_statics.total_count is
'�ܷ��ʴ���';

comment on column i_metadata_attr_access_statics.latest_count is
'�������·��ʴ���';

/*==============================================================*/
/* Table: i_metadata_attribute                                  */
/*==============================================================*/
create table i_metadata_attribute (
   attribute_id         VARCHAR(32)          not null,
   attribute_name       VARCHAR(256)         not null,
   entity_id            VARCHAR(32)          not null,
   column_name          VARCHAR(64)          not null,
   column_desc          VARCHAR(4096)        not null,
   data_type            VARCHAR(64)          not null,
   length               INT4                 not null,
   precision            INT4                 not null,
   attribute_seq        INT4                 not null,
   constraint PK_I_METADATA_ATTRIBUTE primary key (attribute_id)
);

comment on table i_metadata_attribute is
'ʵ�����Ա�';

/*==============================================================*/
/* Table: i_metadata_database                                   */
/*==============================================================*/
create table i_metadata_database (
   database_id          VARCHAR(32)          not null,
   database_name        VARCHAR(64)          not null,
   database_username    VARCHAR(64)          not null,
   database_password    VARCHAR(1024)        not null,
   database_connection  VARCHAR(256)         not null,
   constraint PK_I_METADATA_DATABASE primary key (database_id)
);

/*==============================================================*/
/* Table: i_metadata_entity                                     */
/*==============================================================*/
create table i_metadata_entity (
   entity_id            VARCHAR(32)          not null,
   entity_name          VARCHAR(256)         not null,
   database_id          VARCHAR(32)          not null,
   schema               VARCHAR(64)          not null,
   table_name           VARCHAR(64)          not null,
   table_desc           VARCHAR(4096)        not null,
   create_time          bigint               not null,
   create_user          VARCHAR(64)          not null,
   last_modify_time     bigint               not null,
   sequence             INT4                 not null,
   constraint PK_I_METADATA_ENTITY primary key (entity_id)
);

/*==============================================================*/
/* Table: i_metadata_entity_access_static                       */
/*==============================================================*/
create table i_metadata_entity_access_static (
   entity_id            VARCHAR(32)          not null,
   total_count          INT4                 not null,
   latest_count         VARCHAR(254)         not null
);

/*==============================================================*/
/* Table: i_metadata_entity_subject                             */
/*==============================================================*/
create table i_metadata_entity_subject (
   entity_id            VARCHAR(32)          not null,
   subject_area_id      VARCHAR(32)          not null
);

/*==============================================================*/
/* Table: i_metadata_entity_tables                              */
/*==============================================================*/
create table i_metadata_entity_tables (
   entity_id            VARCHAR(32)          not null,
   database_id          VARCHAR(32)          not null,
   schema               VARCHAR(64)          not null,
   table_name           VARCHAR(64)          not null,
   validate_time        bigint               not null
);

/*==============================================================*/
/* Table: i_metadata_schema                                     */
/*==============================================================*/
create table i_metadata_schema (
   schema               VARCHAR(64)          not null,
   database_id          VARCHAR(32)          not null,
   constraint PK_I_METADATA_SCHEMA primary key (schema)
);

comment on table i_metadata_schema is
'���ݿ���ٱ�,����table,sequence������';

/*==============================================================*/
/* Table: i_metadata_subject_area                               */
/*==============================================================*/
create table i_metadata_subject_area (
   subject_area_id      varchar(32)          not null,
   subject_area_name    varchar(256)         not null,
   parent_subject_area_id VARCHAR(32)          null,
   constraint PK_I_METADATA_SUBJECT_AREA primary key (subject_area_id)
);
/*
alter table i_metadata_attr_access_statics
   add constraint fk_attraccess_attr foreign key (attribute_id)
      references i_metadata_attribute (attribute_id)
      on delete restrict on update restrict;

alter table i_metadata_attribute
   add constraint fk_attr_entity foreign key (entity_id)
      references i_metadata_entity (entity_id)
      on delete restrict on update restrict;

alter table i_metadata_entity
   add constraint fk_entity_database foreign key (database_id)
      references i_metadata_database (database_id)
      on delete restrict on update restrict;

alter table i_metadata_entity
   add constraint fk_entity_schema foreign key (schema)
      references i_metadata_schema (schema)
      on delete restrict on update restrict;

alter table i_metadata_entity_access_static
   add constraint FK_I_METADA_REFERENCE_I_METADA foreign key (entity_id)
      references i_metadata_entity (entity_id)
      on delete restrict on update restrict;

alter table i_metadata_entity_subject
   add constraint fk_entitysubject_entity foreign key (entity_id)
      references i_metadata_entity (entity_id)
      on delete restrict on update restrict;

alter table i_metadata_entity_subject
   add constraint fk_entitysubject_subjectarea foreign key (subject_area_id)
      references i_metadata_subject_area (subject_area_id)
      on delete restrict on update restrict;

alter table i_metadata_entity_tables
   add constraint fk_entitytables_schema foreign key (schema)
      references i_metadata_schema (schema)
      on delete restrict on update restrict;

alter table i_metadata_entity_tables
   add constraint fk_entitytables_entity foreign key (entity_id)
      references i_metadata_entity (entity_id)
      on delete restrict on update restrict;

alter table i_metadata_entity_tables
   add constraint fk_entitytables_database foreign key (database_id)
      references i_metadata_database (database_id)
      on delete restrict on update restrict;

alter table i_metadata_schema
   add constraint fk_schema_database foreign key (database_id)
      references i_metadata_database (database_id)
      on delete restrict on update restrict;

alter table i_metadata_subject_area
   add constraint fk_subject_area foreign key (parent_subject_area_id)
      references i_metadata_subject_area (subject_area_id)
      on delete restrict on update restrict;
*/

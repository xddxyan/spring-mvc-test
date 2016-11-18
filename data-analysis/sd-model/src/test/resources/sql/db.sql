create sequence p_users_seq start 1 increment by 1;
--DROP TABLE IF EXISTS p_users;
CREATE TABLE p_users (
	id bigint NOT NULL ,
	username VARCHAR( 256 ) not null unique ,
	password VARCHAR( 256 ) not null default '',
	email VARCHAR( 256 ) not null default '',
	power bigint NOT NULL default 0 ,
	createdate bigint NOT NULL ,
	PRIMARY KEY ( id ) 
);

--Ȩ���б� �û���power&�˱��power>0��ʾ�������
CREATE SEQUENCE p_authority_seq start 1 increment by 1;
--DROP TABLE IF EXISTS p_authority;
CREATE TABLE p_authority(
	id bigint NOT NULL ,
	name VARCHAR( 64 ) not null default '',
	power bigint NOT NULL ,
	url VARCHAR( 256 ) not null default '',
	PRIMARY KEY ( id ) 
);
CREATE INDEX p_authority_url ON p_authority USING btree (url);

--�û����б� ֻ��ʾ����
CREATE SEQUENCE p_group_seq start 10001 increment by 1;
--DROP TABLE IF EXISTS p_group;
CREATE TABLE p_group (
	id bigint NOT NULL ,
	name character varying(64) NOT NULL,
	PRIMARY KEY ( id ) 
);

--��¼��¼
CREATE SEQUENCE p_signin_seq start 10001 increment by 1;
--DROP TABLE IF EXISTS p_contact;
CREATE TABLE p_signin (
	id bigint NOT NULL ,
	uid bigint NOT NULL ,
	date_ bigint NOT NULL ,
	ip character varying(64) NOT NULL,
	PRIMARY KEY ( id ) 
);
CREATE INDEX p_signin_uid ON p_signin USING btree (uid);
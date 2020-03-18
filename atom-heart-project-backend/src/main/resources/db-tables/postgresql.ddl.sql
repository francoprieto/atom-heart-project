
CREATE TABLE [schema].menu(
	id varchar(50) NOT NULL
,	parent varchar(50) NOT NULL
,	title varchar(50) NOT NULL
,	url varchar(500) NOT NULL
,   index number(4) NOT NULL
, 	PRIMARY KEY (id)
);
CREATE TABLE [schema].feature(
	id varchar(100) NOT NULL
, 	description varchar(1000)
, 	inactive number(1) NOT NULL
, 	PRIMARY KEY (id)
);
CREATE TABLE [schema].role(
	id numeric(17) NOT NULL
, 	nombre varchar(50) NOT NULL
, 	comentarios varchar(1000)
, 	user_insert varchar(50) NOT NULL
, 	date_insert timestamp NOT NULL
, 	user_update varchar(50)
, 	date_update timestamp
, 	user_disable varchar(50)
, 	date_disable timestamp
, 	PRIMARY KEY (id)
);
CREATE TABLE [schema].role_feature(
	id numeric(17) NOT NULL
,	role_id numeric(17) NOT NULL
,	feature_id varchar(100) NOT NULL
, 	user_insert varchar(50) NOT NULL
, 	date_insert timestamp NOT NULL
, 	PRIMARY KEY (id)
,	FOREIGN KEY (role_id) REFERENCES [schema].rol (id)
,	FOREIGN KEY (feature_id) REFERENCES [schema].feature (id)
);
CREATE TABLE [schema].profile(
	id varchar(100) NOT NULL	
, 	user_insert varchar(50) NOT NULL
, 	date_insert timestamp NOT NULL
, 	user_update varchar(50)
, 	date_update timestamp
, 	user_disable varchar(50)
, 	date_disable timestamp
, 	PRIMARY KEY (id)
);
CREATE TABLE [schema].profile_role(
	id numeric(17) NOT NULL
,	profile_id varchar(100) NOT NULL
,	role_id	numeric(17) NOT NULL
, 	user_insert varchar(50) NOT NULL
, 	date_insert timestamp NOT NULL
, 	PRIMARY KEY (id)
,	FOREIGN KEY (profile_id) REFERENCES [schema].profile (id)
,	FOREIGN KEY (role_id) REFERENCES [schema].role (id)
);
CREATE TABLE [schema].user(
	id varchar(50) NOT NULL
, 	user_insert varchar(50) NOT NULL
, 	date_insert timestamp NOT NULL
, 	user_update varchar(50)
, 	date_update timestamp
, 	user_disable varchar(50)
, 	date_disable timestamp
, 	profile_id varchar(50)
,	name varchar(200) NOT NULL
, 	email varchar(200) NOT NULL
, 	password varchar(200) NOT NULL
,	su numeric(1) NOT NULL
, 	PRIMARY KEY (id)
,	FOREIGN KEY (profile_id) REFERENCES [schema].profile (id)
);
CREATE TABLE [schema].user_role(
	id numeric(17) NOT NULL
,	role_id numeric(17) NOT NULL
,	user_id varchar(100) NOT NULL
, 	user_insert varchar(50) NOT NULL
, 	date_insert timestamp NOT NULL
, 	PRIMARY KEY (id)
,	FOREIGN KEY (role_id) REFERENCES [schema].role (id)
,	FOREIGN KEY (user_id) REFERENCES [schema].user (id)
);

-- Usuario admin con database login
INSERT INTO [schema].usuario(id,user_insert,date_insert,name,email,su,password)
VALUES('francoprieto@gmail.com','admin',now(),'Franco Prieto','id',1,'8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');
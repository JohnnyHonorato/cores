CREATE TABLE core.role (
	id serial NOT NULL,
	name character varying(255) NOT NULL,
	deleted boolean DEFAULT false,
	created_on timestamp without time zone,
  	updated_on timestamp without time zone,
  	created_by integer,
  	updated_by integer,
	CONSTRAINT role_pkey PRIMARY KEY (id)
);


CREATE TABLE core.role_permission (
	role_id INTEGER NOT NULL,
	permission_id INTEGER NOT NULL,
	PRIMARY KEY (role_id, permission_id),
	CONSTRAINT role_permission_role_FK FOREIGN KEY (role_id) REFERENCES role (id),
	CONSTRAINT role_permission_permission_FK FOREIGN KEY (permission_id) REFERENCES permission (id)
);

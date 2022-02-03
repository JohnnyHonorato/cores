CREATE TABLE core.user_account (
	id serial NOT NULL,
	name character varying(255) NOT NULL,
	username character varying(255) NOT NULL,
        password character varying(255),
	deleted boolean DEFAULT false,
        external boolean DEFAULT false,
        created_on timestamp without time zone,
	updated_on timestamp without time zone,
	created_by integer,
	updated_by integer,
	CONSTRAINT user_account_pkey PRIMARY KEY (id)
);


CREATE TABLE core.user_role (
	user_account_id INTEGER NOT NULL,
	role_id INTEGER NOT NULL
);

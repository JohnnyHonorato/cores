CREATE TABLE core.password_definition_token(
    id serial NOT NULL PRIMARY KEY,
    deleted boolean DEFAULT false,
    token character varying(128),
    used boolean DEFAULT false,
    user_id integer,
    validity timestamp without time zone NOT NULL
);

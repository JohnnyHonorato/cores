CREATE TABLE core.module(
  id serial NOT NULL PRIMARY KEY,
  icon character varying(30),
  name character varying(255),
  link character varying(255),
  open_mode character varying(15),
  deleted boolean DEFAULT false,
  path_name character varying(255),
  visible boolean DEFAULT true,
  created_on timestamp without time zone,
  updated_on timestamp without time zone,
  created_by integer,
  updated_by integer
);

CREATE TABLE core.permission (
  id serial NOT NULL PRIMARY KEY,
  name character varying(255),
  label character varying(255) ,
  description character varying(255),
  module_id INTEGER NOT NULL,
  CONSTRAINT permission_module_FK FOREIGN KEY (module_id) REFERENCES module (id)
);

INSERT INTO core.module (name, link, icon, open_mode, path_name, visible) VALUES ('Core', '${core-link}', 'fas fa-cog', 'SPA', 'core', true);
INSERT INTO core.module (name, link, icon, open_mode, path_name, visible) VALUES ('Business', '${business-link}', 'fas fa-business-time', 'SPA', 'business', true);
INSERT INTO core.module (name, link, icon, open_mode, path_name, visible) VALUES ('Tracker', '${tracker-link}', 'fas fa-cog', 'SPA', 'tracker', false);

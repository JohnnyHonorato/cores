CREATE TABLE core.filepath (
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(255),
    original_name VARCHAR(255),
    file_size INTEGER,
	directory VARCHAR(255)
);


CREATE TABLE core.organization (
  id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  fantasy_name VARCHAR(150),
  government_code VARCHAR(50),
  description VARCHAR(5000),
  img_id INTEGER,
  deleted BOOLEAN NOT NULL DEFAULT FALSE,
  created_on timestamp without time zone,
  updated_on timestamp without time zone,
  created_by integer,
  updated_by integer,
  CONSTRAINT organization_img_FK FOREIGN KEY (img_id) REFERENCES filepath (id)
);

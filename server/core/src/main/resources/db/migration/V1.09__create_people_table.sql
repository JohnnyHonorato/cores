CREATE TABLE core.people (
  id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  government_code VARCHAR(50),
  nickname varchar(50),
  people_type VARCHAR(50),
  deleted BOOLEAN NOT NULL DEFAULT FALSE,
  user_id INTEGER,
  created_on timestamp without time zone,
  updated_on timestamp without time zone,
  created_by integer,
  updated_by integer,
  CONSTRAINT member_user_FK FOREIGN KEY (user_id) REFERENCES user_account (id)
);


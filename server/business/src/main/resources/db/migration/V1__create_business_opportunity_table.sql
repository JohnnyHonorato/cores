CREATE TABLE business.business_opportunity (
  id SERIAL NOT NULL PRIMARY KEY,
  title VARCHAR(255),
  description VARCHAR(255),
  organization_id integer NOT NULL,
  deleted boolean DEFAULT false
);

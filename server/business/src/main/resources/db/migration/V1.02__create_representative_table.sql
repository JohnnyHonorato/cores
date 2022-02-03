CREATE TABLE business.representative (
  id SERIAL NOT NULL PRIMARY KEY,
  office VARCHAR(255),
  signatory boolean DEFAULT FALSE,
  company_representative boolean DEFAULT FALSE,
  technical_representative boolean DEFAULT FALSE,
  people_id integer NOT NULL,
  business_opportunity_id integer NOT NULL,
  deleted boolean DEFAULT false,
  CONSTRAINT people_representative_people_FK FOREIGN KEY (people_id) REFERENCES core.people (id)
);

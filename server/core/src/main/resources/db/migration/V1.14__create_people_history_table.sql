CREATE TABLE core.people_history (
  id SERIAL NOT NULL PRIMARY KEY,
  type VARCHAR(50),
  module_source VARCHAR(255),
  created_at timestamp,
  people_id INTEGER,
  CONSTRAINT people_history_people_FK FOREIGN KEY (people_id) REFERENCES people (id)
);

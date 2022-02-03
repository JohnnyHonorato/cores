CREATE TABLE core.people_organization (
	id serial NOT NULL,
	department character varying(255),
	position character varying(255),
	people_id INTEGER NOT NULL,
	organization_id INTEGER NOT NULL,
	technical_manager boolean DEFAULT FALSE,
	CONSTRAINT people_organization_pkey PRIMARY KEY (id),
	CONSTRAINT people_organization_member_FK FOREIGN KEY (people_id) REFERENCES people (id),
	CONSTRAINT people_organization_organization_FK FOREIGN KEY (organization_id) REFERENCES organization (id)
);

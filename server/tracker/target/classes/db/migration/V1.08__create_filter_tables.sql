CREATE TABLE tracker.filter
(
    id serial NOT NULL PRIMARY KEY,
    created_by integer,
    created_on timestamp without time zone,
    updated_by integer,
    updated_on timestamp without time zone,
    favourite boolean,
    name character varying(255),
    number_of_attributes integer,
    tracker_model_id integer NOT NULL,
    CONSTRAINT fkkfukninr8kek3rmmii1pn8dpp FOREIGN KEY (tracker_model_id)
        REFERENCES tracker.tracker_model (id) MATCH SIMPLE
);

CREATE TABLE tracker.filter_assignee (
	filter_id integer NOT NULL,
	assignee_id integer NOT NULL,
	CONSTRAINT fki23xnkk20euej47d4pok98xbi FOREIGN KEY (assignee_id) REFERENCES assignee(id),
	CONSTRAINT fkq4ooue6txx00lt4nf2xmbutyr FOREIGN KEY (filter_id) REFERENCES "filter"(id)
);

CREATE TABLE tracker.filter_tag (
	filter_id integer NOT NULL,
	tag_id integer NOT NULL,
	CONSTRAINT fknd89fsq6vyhwst7mggp5bo34t FOREIGN KEY (tag_id) REFERENCES tag(id),
	CONSTRAINT fkniw71r1ptjap5e0reuymvvlfm FOREIGN KEY (filter_id) REFERENCES "filter"(id)
);

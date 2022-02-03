CREATE TABLE tracker.tracker_model(
    id serial NOT NULL PRIMARY KEY ,
    name character varying(255) NOT NULL,
    module_id INTEGER NOT NULL,
    description character varying(5000),
    deleted boolean DEFAULT false,
    created_on timestamp without time zone,
    updated_on timestamp without time zone,
    created_by INTEGER,
    updated_by INTEGER
);

CREATE TABLE tracker.status(
    id serial NOT NULL PRIMARY KEY ,
    name character varying(255) NOT NULL,
    position INTEGER NOT NULL,
    deleted boolean DEFAULT false,
    tracker_model_id INTEGER NOT NULL,
    CONSTRAINT status_tracker_model_FK FOREIGN KEY (tracker_model_id) REFERENCES tracker_model (id)
);

CREATE TABLE tracker.attribute(
    id serial NOT NULL PRIMARY KEY ,
    title character varying(255),
    type character varying(255),
    related_attribute_id INTEGER CHECK (related_attribute_id <> id),
    required BOOLEAN NOT NULL DEFAULT FALSE,
    position INTEGER,
    x_axis INTEGER,
    y_axis INTEGER,
    number_of_columns INTEGER,
    min_value double precision,
    max_value double precision,
    max_length INTEGER,
    list_values TEXT,
    show_on_card boolean DEFAULT false,
    needs_value_complement boolean DEFAULT false,
    currency character varying(255),
    min_date timestamp without time zone,
    max_date timestamp without time zone,
    deleted boolean DEFAULT false,
    tracker_model_id INTEGER,
    CONSTRAINT attribute_attribute_FK FOREIGN KEY (related_attribute_id) REFERENCES attribute (id),
    CONSTRAINT attribute_tracker_model_FK FOREIGN KEY (tracker_model_id) REFERENCES tracker_model (id)
);

CREATE TABLE tracker.tag(
    id serial NOT NULL PRIMARY KEY,
    name character varying(255),
    color character varying(255),
    tracker_model_id INTEGER,
    CONSTRAINT tracker_tag_FK FOREIGN KEY (tracker_model_id) REFERENCES tracker_model (id)
);

CREATE TABLE tracker.assignee(
    id serial NOT NULL PRIMARY KEY,
    created_by integer,
    created_on timestamp without time zone,
    updated_by integer,
    updated_on timestamp without time zone,
    people_id integer NOT NULL,
    tracker_model_id integer,
    CONSTRAINT tracker_model_assignee_FK FOREIGN KEY (tracker_model_id)
        REFERENCES tracker_model (id)
);

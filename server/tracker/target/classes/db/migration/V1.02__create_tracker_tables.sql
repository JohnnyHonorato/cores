CREATE TABLE tracker.tracker(
    id serial NOT NULL PRIMARY KEY ,
    title character varying(255) NOT NULL,
    description character varying(255),
    due_date timestamp without time zone,
    created_on timestamp without time zone,
    updated_on timestamp without time zone,
    created_by INTEGER,
    updated_by INTEGER,
    status_id INTEGER,
    tracker_model_id INTEGER,
    CONSTRAINT tracker_status_FK FOREIGN KEY (status_id) REFERENCES status (id),
    CONSTRAINT tracker_tracker_model_FK FOREIGN KEY (tracker_model_id) REFERENCES tracker_model (id)
);

CREATE TABLE tracker.attribute_value(
    id serial NOT NULL PRIMARY KEY ,
    value character varying(255),
    value_complement character varying(255),
    tracker_id INTEGER,
    attribute_id INTEGER,
    CONSTRAINT attribute_value_tracker_FK FOREIGN KEY (tracker_id) REFERENCES tracker (id),
    CONSTRAINT attribute_value_attribute_FK FOREIGN KEY (attribute_id) REFERENCES attribute (id)
);

CREATE TABLE tracker.tracker_tag(
    id serial NOT NULL PRIMARY KEY,
    tracker_id INTEGER,
    tag_id INTEGER,
    CONSTRAINT fksi9nm7utj2elpo69fg6cb69xv FOREIGN KEY (tracker_id) REFERENCES tracker (id),
    CONSTRAINT fksi9998utjselbg69fg6cb69xv FOREIGN KEY (tag_id) REFERENCES tag (id)
);

CREATE TABLE tracker.checklist(
    id serial NOT NULL PRIMARY KEY,
    name character varying(255),
    tracker_id INTEGER,
    CONSTRAINT checklist_tracker_FK FOREIGN KEY (tracker_id) REFERENCES tracker (id)
);

CREATE TABLE tracker.checklist_item(
    id serial NOT NULL PRIMARY KEY,
    name character varying(255),
    checklist_id INTEGER,
    done boolean,
    CONSTRAINT checklist_tracker_item_FK FOREIGN KEY (checklist_id) REFERENCES checklist (id)
);

CREATE TABLE tracker.tracker_assignee(
    tracker_id integer NOT NULL,
    assignee_id integer NOT NULL,
    CONSTRAINT fkn2o68le0x8mlxocjcbt3i8hcy FOREIGN KEY (assignee_id)
        REFERENCES assignee (id),
    CONSTRAINT fkoyh7infvtxd3t80b3bycdsn3j FOREIGN KEY (tracker_id)
        REFERENCES tracker (id)
);

CREATE TABLE tracker.filepath(
    id serial NOT NULL PRIMARY KEY,
    name character varying(255),
    original_name character varying(255),
    file_size INTEGER,
    directory character varying(255),
    tracker_id INTEGER,
    CONSTRAINT attachments_tracker_FK FOREIGN KEY (tracker_id) REFERENCES tracker (id)
);


CREATE TABLE tracker.comment(
    id serial NOT NULL PRIMARY KEY ,
    text character varying(5000),
    tracker_id integer NOT NULL,
    auto_generated boolean DEFAULT false,
    system_log boolean DEFAULT false,
    created_on timestamp without time zone,
    updated_on timestamp without time zone,
    created_by INTEGER,
    updated_by INTEGER,
    CONSTRAINT tracker_comment_FK FOREIGN KEY (tracker_id) REFERENCES tracker (id)
);


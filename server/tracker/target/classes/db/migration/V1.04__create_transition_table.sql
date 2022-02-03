CREATE TABLE tracker.transition(
    id serial NOT NULL PRIMARY KEY,
    attributes character varying(5000),
    source_id integer NOT NULL,
    target_id integer NOT NULL,
    deleted boolean DEFAULT false,
    tracker_model_id integer NOT NULL,
    CONSTRAINT status_source_transition_FK FOREIGN KEY (source_id) REFERENCES status (id) ON DELETE CASCADE,
    CONSTRAINT status_target_transition_FK FOREIGN KEY (target_id) REFERENCES status (id) ON DELETE CASCADE,
    CONSTRAINT tracker_model_transition_FK FOREIGN KEY (tracker_model_id) REFERENCES tracker_model (id)
);


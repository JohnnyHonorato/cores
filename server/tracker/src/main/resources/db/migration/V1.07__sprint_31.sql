-- 2021-09-14

ALTER TABLE tracker.tracker_model
    ADD file_types_restrictions character varying(35000);
ALTER TABLE tracker.filepath
    ADD file_type character varying(255);
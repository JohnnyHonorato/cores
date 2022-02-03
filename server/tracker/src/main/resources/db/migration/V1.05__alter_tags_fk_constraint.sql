ALTER TABLE tracker.tag
DROP CONSTRAINT IF EXISTS tracker_tag_FK,
ADD CONSTRAINT tracker_tag_FK
    FOREIGN KEY (tracker_model_id) REFERENCES tracker_model (id)
    ON DELETE CASCADE;

ALTER TABLE tracker.tracker_tag
DROP CONSTRAINT IF EXISTS fksi9998utjselbg69fg6cb69xv,
ADD CONSTRAINT tracker_tag__tag_FK
    FOREIGN KEY (tag_id) REFERENCES tag (id)
    ON DELETE CASCADE;

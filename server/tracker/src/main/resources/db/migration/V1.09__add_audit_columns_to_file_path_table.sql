ALTER TABLE tracker.filepath ADD created_on timestamp without time zone;
ALTER TABLE tracker.filepath ADD updated_on timestamp without time zone;
ALTER TABLE tracker.filepath ADD created_by INTEGER;
ALTER TABLE tracker.filepath ADD updated_by INTEGER;

ALTER TABLE tracker.filter ADD COLUMN deleted boolean;
UPDATE tracker.filter SET deleted = false;
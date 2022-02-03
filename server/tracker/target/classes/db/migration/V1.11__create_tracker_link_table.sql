CREATE TABLE tracker.tracker_link (
    id serial NOT NULL PRIMARY KEY,
    first_link integer NOT NULL,
    second_link integer NOT NULL,
    CONSTRAINT fknd89fsq6vlo98t7mggp5bo34t FOREIGN KEY (first_link) REFERENCES tracker(id),
    CONSTRAINT fkniw7189rtjap5e0reuymvvlfm FOREIGN KEY (second_link) REFERENCES tracker(id)
);

CREATE TABLE events(
    id BIGSERIAL PRIMARY KEY,
    link TEXT NOT NULL
);

INSERT INTO events (link) VALUES ('2015');

ALTER TABLE rsvps ADD COLUMN event_id BIGINT REFERENCES events(id);

UPDATE rsvps SET event_id = (SELECT id FROM events WHERE link = '2015');
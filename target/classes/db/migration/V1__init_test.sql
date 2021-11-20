CREATE TABLE IF NOT EXISTS test
(
    id   UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255)     NOT NULL
);

CREATE UNIQUE INDEX name ON test (name);
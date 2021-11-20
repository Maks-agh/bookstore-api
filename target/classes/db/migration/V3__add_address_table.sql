CREATE TABLE IF NOT EXISTS addresses
(
    id           UUID PRIMARY KEY NOT NULL,
    first_line   VARCHAR(255) NOT NULL,
    second_line  VARCHAR(255) NOT NULL,
    city         VARCHAR(255) NOT NULL,
    postal_code  VARCHAR(255) NOT NULL
);

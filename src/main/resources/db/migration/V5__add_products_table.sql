CREATE TABLE IF NOT EXISTS products
(
    id          UUID PRIMARY KEY NOT NULL,
    name        VARCHAR(255)     NOT NULL,
    description VARCHAR(255)     NOT NULL,
    in_stock    FLOAT            NOT NULL,
    price       FLOAT            NOT NULL
);

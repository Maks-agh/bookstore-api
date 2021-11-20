CREATE TABLE IF NOT EXISTS products
(
    id          UUID PRIMARY KEY NOT NULL,
    name        VARCHAR(255)     NOT NULL,
    description VARCHAR(255)     NOT NULL,
    in_stock    int              NOT NULL,
    price       FLOAT            NOT NULL
);
ALTER TABLE products ADD CONSTRAINT in_stock_not_negative CHECK (in_stock >= 0)

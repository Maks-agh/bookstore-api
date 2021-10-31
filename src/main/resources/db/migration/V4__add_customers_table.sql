CREATE TABLE IF NOT EXISTS customers
(
    id         UUID PRIMARY KEY          NOT NULL,
    name       VARCHAR(255)              NOT NULL,
    email      VARCHAR(255) UNIQUE       NOT NULL,
    phone      VARCHAR(255) UNIQUE       NOT NULL,
    password   VARCHAR(255)              NOT NULL,
    address_id UUID REFERENCES addresses NOT NULL
);

CREATE UNIQUE INDEX email ON customers (email);
CREATE UNIQUE INDEX phone ON customers (phone);

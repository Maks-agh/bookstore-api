CREATE TABLE IF NOT EXISTS orders
(
    id          UUID PRIMARY KEY NOT NULL,
    created_by  UUID             NOT NULL,
    received_at timestamp        NOT NULL,
    packed_at   timestamp,
    sent_at     timestamp,
    status      VARCHAR(255)     NOT NULL
);

CREATE TABLE IF NOT EXISTS order_details
(
    order_id   UUID REFERENCES orders   NOT NULL,
    product_id UUID REFERENCES products NOT NULL,
    quantity   int                      NOT NULL,
    price      float8                   NOT NULL,
    PRIMARY KEY (order_id, product_id)
);

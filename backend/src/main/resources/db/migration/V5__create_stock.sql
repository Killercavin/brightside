-- stock table
CREATE TABLE stock (
                       id SERIAL PRIMARY KEY,
                       variant_id INTEGER NOT NULL REFERENCES product_variants(id) ON DELETE CASCADE,
                       quantity INTEGER DEFAULT 0,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
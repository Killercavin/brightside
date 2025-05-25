-- product_images table
CREATE TABLE product_images (
                                id SERIAL PRIMARY KEY,
                                product_id INTEGER REFERENCES products(id) ON DELETE CASCADE,
                                variant_id INTEGER REFERENCES product_variants(id) ON DELETE CASCADE,
                                url VARCHAR(500) NOT NULL,
                                alt_text VARCHAR(255),
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
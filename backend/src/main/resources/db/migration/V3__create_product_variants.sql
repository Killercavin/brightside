-- product_variants table
CREATE TABLE product_variants (
                                  id SERIAL PRIMARY KEY,
                                  product_id INTEGER NOT NULL REFERENCES products(id) ON DELETE CASCADE,
                                  sku VARCHAR(64) UNIQUE NOT NULL,
                                  color VARCHAR(100),
                                  size VARCHAR(100),
                                  stock_quantity INTEGER DEFAULT 0,
                                  price NUMERIC(10, 2),
                                  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
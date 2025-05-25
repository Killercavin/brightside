-- products table
CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(250) NOT NULL,
                          description TEXT NOT NULL,
                          category_id INTEGER NOT NULL REFERENCES categories(id) ON DELETE CASCADE ON UPDATE RESTRICT,
                          price NUMERIC(10, 2) NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE drivers (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         name VARCHAR(255) NOT NULL,
                         surname VARCHAR(255),
                         email VARCHAR(255),
                         phone VARCHAR(50),
                         deleted BOOLEAN DEFAULT FALSE,
                         created_date int8  NOT NULL,
                         modified_date int8 NOT NULL,
                         created_by VARCHAR(255)  NOT NULL,
                         modified_by VARCHAR(255)  NOT NULL
);

CREATE TABLE loads (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       load_number VARCHAR(255),
                       pickup_address VARCHAR(255) NOT NULL,
                       delivery_address VARCHAR(255) NOT NULL,
                       status VARCHAR(50) DEFAULT 'UNSIGNED',
                       driver_id UUID,
                       deleted BOOLEAN DEFAULT FALSE,
                       created_date int8  NOT NULL,
                       modified_date int8 NOT NULL,
                       created_by VARCHAR(255)  NOT NULL,
                       modified_by VARCHAR(255)  NOT NULL,
                       FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE SET NULL
);

CREATE UNIQUE INDEX  idx_loads_driver_id ON loads USING btree(driver_id);

CREATE UNIQUE INDEX  unique_email_on_drivers ON drivers USING btree (email,deleted);
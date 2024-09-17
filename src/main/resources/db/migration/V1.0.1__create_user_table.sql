CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
                       "id" UUID DEFAULT uuid_generate_v4(),
                       "username" varchar(64) NOT NULL,
                       "password" varchar(255) NOT NULL,
                       "enabled" bool NOT NULL,
                       "role" varchar (64) NOT NULL,
                       "last_name" varchar(255),
                       "first_name" varchar(255),
                       "created_date" int8 NOT NULL,
                       "modified_date" int8 NOT NULL,
                       PRIMARY KEY ("id")
)
;
CREATE UNIQUE INDEX  idx_users_username ON users USING btree(username);

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cities;
DROP TYPE IF EXISTS gender_enum;

CREATE TYPE gender_enum AS ENUM ('f', 'm');

CREATE TABLE cities
(
    city_id SERIAL PRIMARY KEY,
    name    VARCHAR(100)
);

CREATE TABLE users
(
    user_id    SERIAL PRIMARY KEY,
    first_name VARCHAR(100)        NOT NULL,
    last_name  VARCHAR(100),
    birth_date DATE,
    gender     gender_enum,
    interests  TEXT,
    city_id    INT,
    username   VARCHAR(100) UNIQUE NOT NULL,
    password   VARCHAR(100)        NOT NULL,

    CONSTRAINT fk_city FOREIGN KEY (city_id) REFERENCES cities (city_id)
);

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
    first_name VARCHAR(100)        NOT NULL,
    last_name  VARCHAR(100),
    birth_date DATE,
    gender     gender_enum,
    interests  TEXT,
    city_id    INT,
    username   VARCHAR(100) UNIQUE NOT NULL,
    password   VARCHAR(100)        NOT NULL,
    user_id    uuid PRIMARY KEY DEFAULT uuid_generate_v7(),

    CONSTRAINT fk_city FOREIGN KEY (city_id) REFERENCES cities (city_id)
);

CREATE TABLE user_friends
(
    user_id   uuid      NOT NULL,
    friend_id uuid      NOT NULL,
    created   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (user_id, friend_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_friend FOREIGN KEY (friend_id) REFERENCES users (user_id) ON DELETE CASCADE,

    CONSTRAINT chk_different_users CHECK (user_id != friend_id)
);

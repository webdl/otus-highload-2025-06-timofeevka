CREATE EXTENSION IF NOT EXISTS pg_uuidv7;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS posts;
DROP TYPE IF EXISTS user_status;

CREATE TYPE user_status AS ENUM ('active', 'suspended', 'deactivated');
CREATE TABLE users
(
    user_id      UUID PRIMARY KEY,
    display_name VARCHAR(100),
    total_posts  INTEGER     NOT NULL DEFAULT 0,
    created      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status       user_status NOT NULL DEFAULT 'active'
);
CREATE INDEX idx_users_created ON users(created);
CREATE INDEX idx_users_status ON users(status);

CREATE TABLE posts
(
    post_id UUID PRIMARY KEY   DEFAULT uuid_generate_v7(),
    user_id UUID      NOT NULL,
    content TEXT      NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);
CREATE INDEX idx_post_user_id ON posts (user_id);
CREATE INDEX idx_post_created ON posts (created);

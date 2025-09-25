CREATE EXTENSION IF NOT EXISTS pg_uuidv7;

DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;
DROP TYPE IF EXISTS user_status;

CREATE TYPE user_status AS ENUM ('active', 'suspended', 'deactivated');
CREATE TABLE users
(
    user_id      UUID PRIMARY KEY,
    display_name VARCHAR(200),
    total_posts  INTEGER     NOT NULL DEFAULT 0,
    created      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status       user_status NOT NULL DEFAULT 'active'
);
CREATE INDEX idx_users_created ON users (created);
CREATE INDEX idx_users_status ON users (status);

CREATE TABLE posts
(
    post_id   UUID PRIMARY KEY     DEFAULT uuid_generate_v7(),
    author_id UUID        NOT NULL,
    content   TEXT        NOT NULL,
    created   TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user FOREIGN KEY (author_id) REFERENCES users (user_id) ON DELETE CASCADE
);
CREATE INDEX idx_post_user_id ON posts (author_id);
CREATE INDEX idx_post_created ON posts (created);

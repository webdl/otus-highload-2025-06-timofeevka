CREATE EXTENSION IF NOT EXISTS pg_uuidv7;

DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS authors;
DROP TYPE IF EXISTS author_status;

CREATE TYPE author_status AS ENUM ('active', 'suspended', 'deactivated');
CREATE TABLE authors
(
    user_id      UUID PRIMARY KEY,
    display_name VARCHAR(200),
    total_posts  INTEGER       NOT NULL DEFAULT 0,
    created      TIMESTAMPTZ   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status       author_status NOT NULL DEFAULT 'active'
);
CREATE INDEX idx_authors_created ON authors (created);
CREATE INDEX idx_authors_status ON authors (status);

CREATE TABLE posts
(
    post_id UUID PRIMARY KEY     DEFAULT uuid_generate_v7(),
    user_id UUID        NOT NULL,
    content TEXT        NOT NULL,
    created TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES authors (user_id) ON DELETE CASCADE
);
CREATE INDEX idx_post_user_id ON posts (user_id);
CREATE INDEX idx_post_created ON posts (created);

package ru.webdl.otus.socialnetwork.infra.post.entities;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.post.entities.Post;
import ru.webdl.otus.socialnetwork.core.post.entities.PostRepository;
import ru.webdl.otus.socialnetwork.core.post.entities.impl.PostImpl;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;

    public PostRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Post> postRowMapper = (rs, rowNum) -> new PostImpl(
            rs.getObject("post_id", UUID.class),
            rs.getObject("author_id", UUID.class),
            rs.getString("content"),
            rs.getObject("created", OffsetDateTime.class)
    );

    @Override
    public UUID create(Post post) {
        String sql = "INSERT INTO posts (author_id, content) VALUES (?, ?) RETURNING post_id;";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, post.getAuthorId());
            ps.setString(2, post.getContent());
            return ps;
        }, keyHolder);
        return keyHolder.getKeyAs(UUID.class);
    }

    @Override
    public void update(Post post) {
        String sql = "UPDATE posts SET author_id = ?, content = ? WHERE post_id = ?";
        jdbcTemplate.update(sql, post.getAuthorId(), post.getContent(), post.getPostId());
    }

    @Override
    public void delete(Post post) {
        String sql = "DELETE FROM posts WHERE post_id = ?";
        jdbcTemplate.update(sql, post.getPostId());
    }

    @Override
    @DS("slave_1")
    public Optional<Post> getPost(UUID postId) {
        String sql = "SELECT * FROM posts WHERE post_id = ?";
        return jdbcTemplate.query(sql, postRowMapper, postId).stream().findFirst();
    }

    @Override
    @DS("slave_1")
    @Transactional(readOnly = true)
    public List<Post> getPosts(List<User> users) {
        return List.of();
    }
}

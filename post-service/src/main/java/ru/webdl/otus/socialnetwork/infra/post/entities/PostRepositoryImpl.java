package ru.webdl.otus.socialnetwork.infra.post.entities;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.post.entities.Post;
import ru.webdl.otus.socialnetwork.core.post.entities.PostRepository;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;

    public PostRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID create(Post post) {
        String sql = "INSERT INTO posts (author_id, content) VALUES (?, ?) RETURNING post_id;";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, post.getAuthor().getId());
            ps.setString(2, post.getContent());
            return ps;
        }, keyHolder);
        return keyHolder.getKeyAs(UUID.class);
    }

    @Override
    @DS("slave_1")
    @Transactional(readOnly = true)
    public List<Post> getPosts(List<User> users) {
        return List.of();
    }
}

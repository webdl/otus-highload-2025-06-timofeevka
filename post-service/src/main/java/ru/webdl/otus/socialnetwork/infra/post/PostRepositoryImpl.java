package ru.webdl.otus.socialnetwork.infra.post;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.PostImpl;
import ru.webdl.otus.socialnetwork.core.post.PostRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Post> postRowMapper = (rs, rowNum) -> new PostImpl(
            rs.getObject("post_id", UUID.class),
            rs.getObject("user_id", UUID.class),
            rs.getString("content"),
            rs.getObject("created", OffsetDateTime.class)
    );

    @Override
    public Post create(Post post) {
        String sql = "INSERT INTO posts (user_id, content) VALUES (?, ?) RETURNING *;";
        return jdbcTemplate.queryForObject(sql, postRowMapper, post.getUserId(), post.getContent());
    }

    @Override
    public void update(Post post) {
        String sql = "UPDATE posts SET content = ? WHERE post_id = ?";
        jdbcTemplate.update(sql, post.getContent(), post.getPostId());
    }

    @Override
    public void delete(UUID postId) {
        String sql = "DELETE FROM posts WHERE post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    @Override
    @DS("slave_1")
    public Optional<Post> getPost(UUID postId) {
        String sql = "SELECT * FROM posts WHERE post_id = ?";
        return jdbcTemplate.query(sql, postRowMapper, postId).stream().findFirst();
    }

    @Override
    @DS("slave_1")
    public List<Post> getPosts(List<Author> authors) {
        String sql = "SELECT * FROM posts WHERE user_id in (?) ORDER BY created DESC";
        return jdbcTemplate.query(sql, postRowMapper, authors.stream().map(Author::getAuthorId).toList());
    }
}

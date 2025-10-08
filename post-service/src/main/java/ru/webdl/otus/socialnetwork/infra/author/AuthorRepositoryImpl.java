package ru.webdl.otus.socialnetwork.infra.author;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.author.AuthorImpl;
import ru.webdl.otus.socialnetwork.core.author.AuthorRepository;

import java.time.OffsetDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Author> authorRowMapper = (rs, rowNum) -> new AuthorImpl(
            rs.getObject("user_id", UUID.class),
            rs.getString("display_name"),
            rs.getInt("total_posts"),
            rs.getObject("created", OffsetDateTime.class),
            rs.getString("status")
    );

    @Override
    public Author create(Author author) {
        String sql = "INSERT INTO authors (user_id, display_name) VALUES (?, ?) RETURNING *";
        return jdbcTemplate.queryForObject(sql, authorRowMapper, author.getAuthorId(), author.getDisplayName());
    }

    @Override
    public void save(Author author) {
        String sql = "UPDATE authors SET display_name = ?, total_posts = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, author.getDisplayName(), author.getTotalPosts(), author.getAuthorId());
    }

    @Override
    @DS("slave_1")
    public Optional<Author> findById(UUID userId) {
        String sql = "SELECT * FROM authors WHERE user_id = ?";
        return jdbcTemplate.query(sql, authorRowMapper, userId).stream().findFirst();
    }

    @Override
    public Optional<Author> findByIdWithLock(UUID userId) {
        String sql = "SELECT * FROM authors WHERE user_id = ? FOR UPDATE";
        return jdbcTemplate.query(sql, authorRowMapper, userId).stream().findFirst();
    }

    @Override
    public List<Author> getAuthors(List<UUID> userIds) {
        String sql = "SELECT * FROM authors WHERE user_id IN (:authorIds)";
        Map<String, Object> params = Collections.singletonMap("authorIds", userIds);
        return namedParameterJdbcTemplate.query(sql, params, authorRowMapper);
    }
}

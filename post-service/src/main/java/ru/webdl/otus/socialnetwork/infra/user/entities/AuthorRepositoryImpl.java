package ru.webdl.otus.socialnetwork.infra.user.entities;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.author.entities.Author;
import ru.webdl.otus.socialnetwork.core.author.entities.AuthorImpl;
import ru.webdl.otus.socialnetwork.core.author.repositories.AuthorRepository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
    @DS("slave_1")
    @Transactional(readOnly = true)
    public Optional<Author> findById(UUID authorId) {
        String sql = "SELECT * FROM authors WHERE user_id = ?";
        return jdbcTemplate.query(sql, authorRowMapper, authorId).stream().findFirst();
    }
}

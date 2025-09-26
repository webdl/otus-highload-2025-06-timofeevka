package ru.webdl.otus.socialnetwork.infra.user.entities;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.author.entities.User;
import ru.webdl.otus.socialnetwork.core.author.entities.UserImpl;
import ru.webdl.otus.socialnetwork.core.author.repositories.UserRepository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new UserImpl(
            rs.getObject("user_id", UUID.class),
            rs.getString("display_name"),
            rs.getInt("total_posts"),
            rs.getObject("created", OffsetDateTime.class),
            rs.getString("status")
    );

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (user_id, display_name) VALUES (?, ?) RETURNING *";
        return jdbcTemplate.queryForObject(sql, userRowMapper, user.getUserId(), user.getDisplayName());
    }

    @Override
    @DS("slave_1")
    @Transactional(readOnly = true)
    public Optional<User> findById(UUID id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.query(sql, userRowMapper, id).stream().findFirst();
    }
}

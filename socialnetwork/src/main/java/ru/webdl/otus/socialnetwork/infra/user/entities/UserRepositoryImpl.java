package ru.webdl.otus.socialnetwork.infra.user.entities;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.UserImpl;
import ru.webdl.otus.socialnetwork.core.user.entities.UserRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        return new UserImpl(
                rs.getLong("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getObject("birth_date", LocalDate.class),
                rs.getString("gender"),
                rs.getString("interests"),
                rs.getInt("city_id"),
                rs.getString("username"),
                rs.getString("password")
        );
    };

    @Override
    @DS("slave_1")
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.query(sql, userRowMapper, id).stream().findFirst();
    }

    @Override
    @DS("slave_1")
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.query(sql, userRowMapper, username).stream().findFirst();
    }

    @Override
    @DS("slave_2")
    @Transactional(readOnly = true)
    public List<User> findByFirstLastName(String firstName, String lastName) {
        firstName += "%";
        lastName += "%";
        String sql = "SELECT * FROM users WHERE first_name LIKE ? AND last_name LIKE ?";
        return jdbcTemplate.query(sql, userRowMapper, firstName, lastName);
    }

    @Override
    public int create(User user) {
        String sql = """
                INSERT INTO users (first_name, last_name, birth_date, gender, interests, city_id, username, password)
                                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                                RETURNING user_id;""";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setObject(3, user.getBirthDate());
            ps.setObject(4, user.getGender(), java.sql.Types.OTHER);
            ps.setString(5, user.getInterests());
            ps.setInt(6, user.getCityId());
            ps.setString(7, user.getUsername());
            ps.setString(8, user.getPassword());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}

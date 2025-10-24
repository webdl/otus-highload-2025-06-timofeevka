package ru.webdl.otus.socialnetwork.infra.user;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.core.user.UserImpl;
import ru.webdl.otus.socialnetwork.core.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final BeanPropertyRowMapper<UserEntity> userRowMapper = new BeanPropertyRowMapper<>(UserEntity.class);

    @Override
    @DS("slave_1")
    public Optional<User> findById(UUID id) {
        String sql = "SELECT user_id, first_name, last_name, birth_date, gender, interests, city_id, username, password, last_login " +
                "FROM users WHERE user_id = ?";
        UserEntity record = jdbcTemplate.queryForObject(sql, userRowMapper, id);
        return Optional.ofNullable(record).map(this::toDomain);
    }

    @Override
    @DS("slave_1")
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT user_id, first_name, last_name, birth_date, gender, interests, city_id, username, password, last_login " +
                "FROM users WHERE username = ?";
        UserEntity record = jdbcTemplate.queryForObject(sql, userRowMapper, username);
        return Optional.ofNullable(record).map(this::toDomain);
    }

    @Override
    @DS("slave_2")
    public List<User> findByFirstLastName(String firstName, String lastName) {
        firstName += "%";
        lastName += "%";
        String sql = "SELECT user_id, first_name, last_name, birth_date, gender, interests, city_id, username, password, last_login " +
                "FROM users " +
                "WHERE first_name LIKE ? AND last_name LIKE ?";
        List<UserEntity> records = jdbcTemplate.query(sql, userRowMapper, firstName, lastName);
        return records.stream().map(this::toDomain).toList();
    }

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (first_name, last_name, birth_date, gender, interests, city_id, username, password) " +
                "VALUES (:firstName, :lastName, :birthDate, :gender, :interests, :cityId, :username, :password) " +
                "RETURNING *;";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(toEntity(user));
        UserEntity record = namedParameterJdbcTemplate.queryForObject(sql, params, userRowMapper);
        return toDomain(record);
    }

    @Override
    public void addFriend(User user, User friend) {
        String sql = "INSERT INTO user_friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getId(), friend.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        String sql = "DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, user.getId(), friend.getId());
    }

    @Override
    @DS("slave_1")
    public List<User> getFriends(User user) {
        String sql = """
                SELECT u.*
                FROM users u
                WHERE u.user_id IN (
                    SELECT friend_id
                    FROM user_friends
                    WHERE user_id = ?
                    );""";
        List<UserEntity> records = jdbcTemplate.query(sql, userRowMapper, user.getId());
        return records.stream().map(this::toDomain).toList();
    }

    @Override
    public void updateLastLogin(User user) {
        String sql = "UPDATE users SET last_login = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getLastLogin(), user.getId());
    }


    private User toDomain(UserEntity r) {
        return UserImpl.builder()
                .id(r.getUserId())
                .firstName(r.getFirstName())
                .lastName(r.getLastName())
                .birthDate(r.getBirthDate())
                .gender(r.getGender())
                .interests(r.getInterests())
                .cityId(r.getCityId())
                .username(r.getUsername())
                .password(r.getPassword())
                .lastLogin(r.getLastLogin())
                .build();
    }

    private UserEntity toEntity(User r) {
        return UserEntity.builder()
                .userId(r.getId())
                .firstName(r.getFirstName())
                .lastName(r.getLastName())
                .birthDate(r.getBirthDate())
                .gender(r.getGender())
                .interests(r.getInterests())
                .cityId(r.getCityId())
                .username(r.getUsername())
                .password(r.getPassword())
                .lastLogin(r.getLastLogin())
                .build();
    }
}

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
    private static final BeanPropertyRowMapper<UserRecord> userRowMapper = new BeanPropertyRowMapper<>(UserRecord.class);

    @Override
    @DS("slave_1")
    public Optional<User> findById(UUID id) {
        String sql = "SELECT user_id, first_name, last_name, birth_date, gender, interests, city_id, username, password " +
                "FROM users WHERE user_id = ?";
        UserRecord record = jdbcTemplate.queryForObject(sql, userRowMapper, id);
        return Optional.ofNullable(record).map(this::toDomainEntity);
    }

    @Override
    @DS("slave_1")
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT user_id, first_name, last_name, birth_date, gender, interests, city_id, username, password " +
                "FROM users WHERE username = ?";
        UserRecord record = jdbcTemplate.queryForObject(sql, userRowMapper, username);
        return Optional.ofNullable(record).map(this::toDomainEntity);
    }

    @Override
    @DS("slave_2")
    public List<User> findByFirstLastName(String firstName, String lastName) {
        firstName += "%";
        lastName += "%";
        String sql = "SELECT user_id, first_name, last_name, birth_date, gender, interests, city_id, username, password " +
                "FROM users " +
                "WHERE first_name LIKE ? AND last_name LIKE ?";
        List<UserRecord> records = jdbcTemplate.query(sql, userRowMapper, firstName, lastName);
        return records.stream().map(this::toDomainEntity).toList();
    }

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (first_name, last_name, birth_date, gender, interests, city_id, username, password) " +
                "VALUES (:firstName, :lastName, :birthDate, :gender, :interests, :cityId, :username, :password) " +
                "RETURNING *;";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(toDbEntity(user));
        UserRecord record = namedParameterJdbcTemplate.queryForObject(sql, params, userRowMapper);
        return toDomainEntity(record);
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
        List<UserRecord> records = jdbcTemplate.query(sql, userRowMapper, user.getId());
        return records.stream().map(this::toDomainEntity).toList();
    }


    private User toDomainEntity(UserRecord r) {
        return UserImpl.builder()
                .firstName(r.firstName())
                .lastName(r.lastName())
                .birthDate(r.birthDate())
                .gender(r.gender())
                .interests(r.interests())
                .cityId(r.cityId())
                .username(r.username())
                .password(r.password())
                .build();
    }

    private UserRecord toDbEntity(User r) {
        return new UserRecord(r.getId(),
                r.getFirstName(),
                r.getLastName(),
                r.getBirthDate(),
                r.getGender(),
                r.getInterests(),
                r.getCityId(),
                r.getUsername(),
                r.getPassword()
        );
    }
}

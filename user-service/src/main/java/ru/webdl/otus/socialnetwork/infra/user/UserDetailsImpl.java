package ru.webdl.otus.socialnetwork.infra.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final String username;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}

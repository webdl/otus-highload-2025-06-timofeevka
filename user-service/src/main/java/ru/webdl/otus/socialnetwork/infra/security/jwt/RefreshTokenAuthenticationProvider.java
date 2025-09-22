package ru.webdl.otus.socialnetwork.infra.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFindUseCase;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.infra.user.UserDetailsImpl;

@Component
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {
    private final UserFindUseCase userFindUseCase;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public RefreshTokenAuthenticationProvider(UserFindUseCase userFindUseCase,
                                              JwtTokenProvider tokenProvider) {
        this.userFindUseCase = userFindUseCase;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        String username = tokenProvider.getUserNameFromJwtToken(token);
        UserDetailsImpl userDetails = getUserDetails(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private UserDetailsImpl getUserDetails(String username) {
        User user = userFindUseCase.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new UserDetailsImpl(user);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (RefreshJwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

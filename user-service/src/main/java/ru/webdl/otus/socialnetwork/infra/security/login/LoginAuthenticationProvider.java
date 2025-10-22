package ru.webdl.otus.socialnetwork.infra.security.login;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.webdl.otus.socialnetwork.core.user.UserSignInUseCase;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.infra.user.UserDetailsImpl;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {
    private final UserSignInUseCase userSignInUseCase;

    @Autowired
    public LoginAuthenticationProvider(UserSignInUseCase userSignInUseCase) {
        this.userSignInUseCase = userSignInUseCase;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        String username = (String) authentication.getPrincipal();
        String password = authentication.getCredentials().toString();
        User user = userSignInUseCase.signin(username, password);
        UserDetails securityUser = new UserDetailsImpl(user);
        return new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}

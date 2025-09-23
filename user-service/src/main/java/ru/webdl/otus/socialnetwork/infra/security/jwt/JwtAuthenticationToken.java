package ru.webdl.otus.socialnetwork.infra.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String rawAccessToken;
    private String principal;

    public JwtAuthenticationToken(final String rawAccessToken) {
        super(null);
        this.rawAccessToken = rawAccessToken;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(final UserDetails userDetails) {
        super(userDetails.getAuthorities());
        super.setAuthenticated(true);
        super.eraseCredentials();
        this.principal = userDetails.getUsername();
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public String getPrincipal() {
        return principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessToken = null;
    }
}

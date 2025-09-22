package ru.webdl.otus.socialnetwork.infra.security.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.webdl.otus.socialnetwork.infra.security.exception.ExpiredTokenException;

import java.util.Date;

@Log4j2
@Component
public class JwtTokenProvider {
    public static final String HEADER = "Authorization";
    public static final String JWT_TOKEN_HEADER_PARAM = HEADER;
    public static final String HEADER_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;
    @Value("${security.jwt.secret}")
    private String jwtSecret;
    @Value("${security.jwt.tokenExpirationTime}")
    private int tokenExpirationInSec;
    @Value("${security.jwt.refreshTokenExpirationTime}")
    private int refreshTokenExpirationInSec;

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public JwtPair generateTokenPair(final UserDetails user) {
        String token = createToken(user);
        String refreshToken = createRefreshToken(user);
        return new JwtPair(token, refreshToken);
    }

    private String createRefreshToken(final UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(getExpiryDate(refreshTokenExpirationInSec))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private String createToken(final UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(getExpiryDate(tokenExpirationInSec))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private Date getExpiryDate(final int tokenExpirationInSec) {
        Date now = new Date();
        return new Date(now.getTime() + tokenExpirationInSec * 1000L);
    }

    public UserDetails parseJwtToken(final String accessToken) {
        UserDetails userDetails = null;
        if (StringUtils.hasText(accessToken) && validateToken(accessToken)) {
            String username = getUserNameFromJwtToken(accessToken);
            userDetails = userDetailsService.loadUserByUsername(username);
        }
        return userDetails;
    }

    public String getUserNameFromJwtToken(final String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(final String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException ex) {
            log.debug("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (SignatureException | ExpiredJwtException expiredEx) {
            log.debug("JWT Token is expired", expiredEx);
            throw new ExpiredTokenException(authToken, "JWT Token expired", expiredEx);
        }
    }

    public String getTokenFromRequest(final HttpServletRequest request) {
        String header = request.getHeader(JWT_TOKEN_HEADER_PARAM);
        if (org.apache.commons.lang3.StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }
        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }
        return header.substring(HEADER_PREFIX.length());
    }
}
package ru.webdl.otus.socialnetwork.infra.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.webdl.otus.socialnetwork.infra.security.jwt.*;
import ru.webdl.otus.socialnetwork.infra.security.login.LoginAuthenticationFilter;
import ru.webdl.otus.socialnetwork.infra.security.login.LoginAuthenticationProvider;
import ru.webdl.otus.socialnetwork.infra.security.matcher.SkipPathRequestMatcher;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig {
    private static final String SIGNIN_ENTRY_POINT = "/login";
    private static final String SIGNUP_ENTRY_POINT = "/api/v1/user/register";
    private static final String PUBLIC_ENTRY_POINT = "/api/v1/public/**";
    public static final String SWAGGER_ENTRY_POINT = "/swagger-ui/**";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/auth/refreshToken";
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationFailureHandler failureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;


    private final TokenAuthenticationProvider tokenAuthenticationProvider;
    private final LoginAuthenticationProvider loginAuthenticationProvider;
    private final RefreshTokenAuthenticationProvider refreshTokenAuthenticationProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider,
                          AuthenticationFailureHandler failureHandler,
                          @Qualifier("loginAuthenticationSuccessHandler") AuthenticationSuccessHandler authenticationSuccessHandler,
                          TokenAuthenticationProvider tokenAuthenticationProvider,
                          LoginAuthenticationProvider loginAuthenticationProvider,
                          RefreshTokenAuthenticationProvider refreshTokenAuthenticationProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.failureHandler = failureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.loginAuthenticationProvider = loginAuthenticationProvider;
        this.refreshTokenAuthenticationProvider = refreshTokenAuthenticationProvider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:8080"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "X-Requested-With"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENTRY_POINT).permitAll()
                        .requestMatchers(SIGNIN_ENTRY_POINT).permitAll()
                        .requestMatchers(SIGNUP_ENTRY_POINT).permitAll()
                        .requestMatchers(SWAGGER_ENTRY_POINT).permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager)
                .addFilterBefore(buildLoginProcessingFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildTokenAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildRefreshTokenProcessingFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    protected LoginAuthenticationFilter buildLoginProcessingFilter(AuthenticationManager authenticationManager) {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter(SIGNIN_ENTRY_POINT, authenticationSuccessHandler, failureHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    protected TokenAuthenticationFilter buildTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        List<String> pathsToSkip = Arrays.asList(PUBLIC_ENTRY_POINT, SIGNIN_ENTRY_POINT, SIGNUP_ENTRY_POINT, SWAGGER_ENTRY_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip);
        TokenAuthenticationFilter filter = new TokenAuthenticationFilter(jwtTokenProvider, matcher, failureHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    protected RefreshTokenAuthenticationFilter buildRefreshTokenProcessingFilter(AuthenticationManager authenticationManager) {
        RefreshTokenAuthenticationFilter filter = new RefreshTokenAuthenticationFilter(TOKEN_REFRESH_ENTRY_POINT,
                authenticationSuccessHandler, failureHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(
                loginAuthenticationProvider,
                tokenAuthenticationProvider,
                refreshTokenAuthenticationProvider
        ));
    }
}

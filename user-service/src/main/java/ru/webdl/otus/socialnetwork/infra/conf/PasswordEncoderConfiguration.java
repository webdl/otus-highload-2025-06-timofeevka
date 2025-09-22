package ru.webdl.otus.socialnetwork.infra.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {

    @Bean
    @Profile("prod")
    public PasswordEncoder prodPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    @Profile("dev")
    public PasswordEncoder devPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

}

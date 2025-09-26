package ru.webdl.otus.socialnetwork.infra.user.externals;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.UserImpl;
import ru.webdl.otus.socialnetwork.core.user.externals.UserExternalService;
import ru.webdl.otus.socialnetwork.infra.user.externals.dto.ExternalUserRequest;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExternalUserServiceImpl implements UserExternalService {
    private final RestTemplate restTemplate;
    @Value("${externals.user-service.url}")
    private String userServiceBaseUrl;
    @Value("${externals.user-service.get-user-path}")
    private String userServiceGetUserPath;

    @Override
    public Optional<User> findById(UUID userId) {
        try {
            String url = buildUserUrl(userId);
            HttpHeaders headers = createHeaders();

            ResponseEntity<ExternalUserRequest> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    ExternalUserRequest.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                User domainUser = convertToDomainUser(response.getBody());
                return Optional.of(domainUser);
            } else {
                return Optional.empty();
            }

        } catch (RestClientException e) {
            return Optional.empty();
        }
    }

    private String buildUserUrl(UUID userId) {
        return UriComponentsBuilder.fromUriString(userServiceBaseUrl)
                .path(userServiceGetUserPath)
                .buildAndExpand(userId)
                .toUriString();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", "PostService/1.0");
        return headers;
    }

    private User convertToDomainUser(ExternalUserRequest externalUser) {
        return new UserImpl(externalUser.getId(), externalUser.getFirstName() + externalUser.getLastName());
    }

}

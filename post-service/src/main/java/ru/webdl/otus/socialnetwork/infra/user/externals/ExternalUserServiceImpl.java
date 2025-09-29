package ru.webdl.otus.socialnetwork.infra.user.externals;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.webdl.otus.socialnetwork.core.user.ExternalUser;
import ru.webdl.otus.socialnetwork.core.user.ExternalUserImpl;
import ru.webdl.otus.socialnetwork.core.user.ExternalUserService;
import ru.webdl.otus.socialnetwork.core.user.UserNotFoundException;
import ru.webdl.otus.socialnetwork.infra.user.externals.dto.ExternalUserRequest;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExternalUserServiceImpl implements ExternalUserService {
    private final RestTemplate restTemplate;
    @Value("${externals.user-service.url}")
    private String userServiceBaseUrl;
    @Value("${externals.user-service.get-user-path}")
    private String userServiceGetUserPath;

    @Override
    public ExternalUser findById(UUID userId) {
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
                return convertToExternalUser(response.getBody());
            } else {
                throw new UserNotFoundException(userId, "User service didn't return a result. Status: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            throw new UserNotFoundException(userId, e.getMessage());
        }
    }

    @Override
    public List<ExternalUser> findUserFriends(UUID userId) {
        return List.of();
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

    private ExternalUser convertToExternalUser(ExternalUserRequest externalUser) {
        return new ExternalUserImpl(externalUser.getId(), externalUser.getFirstName(), externalUser.getLastName());
    }
}

package ru.webdl.otus.socialnetwork.infra.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.core.user.UserImpl;
import ru.webdl.otus.socialnetwork.core.user.UserNotFoundException;
import ru.webdl.otus.socialnetwork.core.user.UserRepository;
import ru.webdl.otus.socialnetwork.infra.user.dto.RemoteUserRequest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RemoteUserServiceImpl implements UserRepository {
    private final RestTemplate restTemplate;
    @Value("${externals.user-service.url}")
    private String userServiceBaseUrl;
    @Value("${externals.user-service.get-user-path}")
    private String userServiceGetUserPath;
    @Value("${externals.user-service.get-friends-path}")
    private String userServiceGetFriendsPath;

    @Override
    public User getBy(UUID userId) {
        try {
            String url = buildUserUrl(userServiceGetUserPath, userId);
            HttpHeaders headers = createHeaders();
            ResponseEntity<RemoteUserRequest> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    RemoteUserRequest.class
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
    public List<User> getFriendsFor(User user) {
        try {
            String url = buildUserUrl(userServiceGetFriendsPath, user.userId());
            HttpHeaders headers = createHeaders();
            ResponseEntity<RemoteUserRequest[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    RemoteUserRequest[].class
            );
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return convertToExternalUser(Arrays.asList(response.getBody()));
            } else {
                throw new UserNotFoundException(user.userId(), "User service didn't return a result. Status: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            throw new UserNotFoundException(user.userId(), e.getMessage());
        }
    }

    private String buildUserUrl(String path, UUID userId) {
        return UriComponentsBuilder.fromUriString(userServiceBaseUrl)
                .path(path)
                .buildAndExpand(userId)
                .toUriString();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", "PostService/1.0");
        return headers;
    }

    private List<User> convertToExternalUser(List<RemoteUserRequest> externalUsers) {
        return externalUsers.stream().map(this::convertToExternalUser).toList();
    }

    private User convertToExternalUser(RemoteUserRequest externalUser) {
        return new UserImpl(externalUser.getId(), externalUser.getFirstName(), externalUser.getLastName());
    }
}

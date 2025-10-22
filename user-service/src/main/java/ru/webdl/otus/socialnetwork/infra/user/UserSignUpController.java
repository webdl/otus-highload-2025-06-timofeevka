package ru.webdl.otus.socialnetwork.infra.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.webdl.otus.socialnetwork.core.user.UserCreateParameters;
import ru.webdl.otus.socialnetwork.core.user.UserSignUpUseCase;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.infra.user.dto.UserIdResponse;
import ru.webdl.otus.socialnetwork.infra.user.dto.UserSignUpResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
class UserSignUpController {
    private final UserSignUpUseCase userSignUpUseCase;

    @PostMapping("/register")
    ResponseEntity<UserIdResponse> register(@RequestBody UserSignUpResponse data) {
        UserCreateParameters parameters = UserCreateParameters.builder()
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .birthDate(data.getBirthDate())
                .gender(data.getGender())
                .interests(data.getInterests())
                .cityId(data.getCityId())
                .username(data.getUsername())
                .password(data.getPassword())
                .build();
        User user = userSignUpUseCase.signup(parameters);
        return ResponseEntity.ok(new UserIdResponse(user.getId()));
    }

}

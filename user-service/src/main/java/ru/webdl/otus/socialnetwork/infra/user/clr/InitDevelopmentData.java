package ru.webdl.otus.socialnetwork.infra.user.clr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.user.UserCreateParameters;
import ru.webdl.otus.socialnetwork.core.user.UserFindUseCase;
import ru.webdl.otus.socialnetwork.core.user.UserSignUpUseCase;

@Component
@Profile("dev")
public class InitDevelopmentData implements CommandLineRunner {
    private final UserFindUseCase userFindUseCase;
    private final UserSignUpUseCase userSignUpUseCase;

    public InitDevelopmentData(UserFindUseCase userFindUseCase, UserSignUpUseCase userSignUpUseCase) {
        this.userFindUseCase = userFindUseCase;
        this.userSignUpUseCase = userSignUpUseCase;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userFindUseCase.findByUsername("admin").isEmpty()) {
            createAdminUser();
        }
    }

    private void createAdminUser() {
        UserCreateParameters parameters = UserCreateParameters.create("Админ", "Админов", "admin", "admin")
                .cityId(171)
                .build();
        userSignUpUseCase.signup(parameters);
    }
}

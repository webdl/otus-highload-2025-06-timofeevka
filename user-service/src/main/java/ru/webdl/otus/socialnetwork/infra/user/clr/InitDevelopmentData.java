package ru.webdl.otus.socialnetwork.infra.user.clr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFindUseCase;
import ru.webdl.otus.socialnetwork.core.user.cases.UserSignUpUseCase;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.impl.UserImpl;

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
        User user = new UserImpl(null, "Админ", "Админов", null, null, null,
                171, "admin", "admin");
        userSignUpUseCase.signup(user);
    }
}

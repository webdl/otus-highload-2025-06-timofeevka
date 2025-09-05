package ru.webdl.otus.socialnetwork.infra.user.clr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.user.UserService;
import ru.webdl.otus.socialnetwork.core.user.cases.UserRegistrationUseCase;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.UserImpl;

@Component
public class CreateAdminUser implements CommandLineRunner {
    private final UserService userService;
    private final UserRegistrationUseCase userRegistrationUseCase;

    public CreateAdminUser(UserService userService, UserRegistrationUseCase userRegistrationUseCase) {
        this.userService = userService;
        this.userRegistrationUseCase = userRegistrationUseCase;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userService.findByUsername("admin").isEmpty()) {
            User user = new UserImpl(null, "Админ", "Админов", null, null, null,
                    171, "admin", "admin");
            userRegistrationUseCase.register(user);
        }
    }
}

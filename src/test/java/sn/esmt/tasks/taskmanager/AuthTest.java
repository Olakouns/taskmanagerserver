package sn.esmt.tasks.taskmanager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sn.esmt.tasks.taskmanager.dto.converters.ApiResponse;
import sn.esmt.tasks.taskmanager.dto.converters.RegisterUser;
import sn.esmt.tasks.taskmanager.services.AuthService;

@SpringBootTest
@ActiveProfiles("test")
public class AuthTest {

    @Autowired
    private AuthService authService;


    @Test
    public void createUser() {
        RegisterUser registerUser = new RegisterUser();
        registerUser.setUsername("olakouns");
        registerUser.setFirstName("Razacki");
        registerUser.setLastName("KOUNASSO");
        registerUser.setEmail("kounassolazare@gmail.com");
        registerUser.setPassword("mot2P@ss");
        registerUser.setConfirmPassword("mot2P@ss");

        ApiResponse apiResponse = this.authService.createUser(registerUser);
        Assertions.assertThat(apiResponse.getSuccess()).isEqualTo(true);
    }
}

package sn.esmt.tasks.taskmanager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sn.esmt.tasks.taskmanager.dto.converters.ApiResponse;
import sn.esmt.tasks.taskmanager.entities.enums.EmailType;
import sn.esmt.tasks.taskmanager.services.EmailService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
public class SendEmailTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void senEmail() {
        try {
            ApiResponse apiResponse = this.emailService.senEmail(EmailType.ACTIVATE_ACCOUNT, "kounassolazare@gmail.com", UUID.randomUUID().toString(), "Razacki KOUNASSO");
            Assertions.assertThat(apiResponse.getSuccess()).isEqualTo(true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

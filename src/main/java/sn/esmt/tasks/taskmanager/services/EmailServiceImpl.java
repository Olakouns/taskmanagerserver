package sn.esmt.tasks.taskmanager.services;

import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.expression.ThymeleafEvaluationContext;
import sn.esmt.tasks.taskmanager.dto.converters.ApiResponse;
import sn.esmt.tasks.taskmanager.entities.enums.EmailType;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private final ApplicationContext applicationContext;

    private final SpringTemplateEngine templateEngine;


    public EmailServiceImpl(JavaMailSender emailSender, ApplicationContext applicationContext, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.applicationContext = applicationContext;
        this.templateEngine = templateEngine;
    }

    @Override
    public ApiResponse senEmail(EmailType emailType, String email, String token, String name) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();
        Map<String, Object> variables = new HashMap<>();
        context.setVariable(ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                new ThymeleafEvaluationContext(applicationContext, null));
        variables.put("token", token);
        variables.put("name", name);
        context.setVariables(variables);

        String html = templateEngine.process("login", context);

        helper.setTo(email);
        helper.setText(html, true);
        helper.setSubject("Confirmation d'inscription");
        helper.setFrom("sen.discover@gmail.com", "Sen Discover");

        emailSender.send(message);

        return new ApiResponse(true, "Mail sent");
    }
}

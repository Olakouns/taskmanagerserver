package sn.esmt.tasks.taskmanager.services;

import sn.esmt.tasks.taskmanager.dto.converters.ApiResponse;
import sn.esmt.tasks.taskmanager.entities.enums.EmailType;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    ApiResponse senEmail(EmailType emailType, String email, String token, String name) throws MessagingException, UnsupportedEncodingException;
}

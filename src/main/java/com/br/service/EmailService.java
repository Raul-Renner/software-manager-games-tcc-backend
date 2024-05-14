package com.br.service;

import com.br.entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final String TEMPLATE_NAME = "registerOrganization";

//    private static final String SPRING_LOGO_IMAGE = "registration";

    private static final String MAIN_SUBJECT = "Seja bem vindo(a)!";

    private final Environment environment;

    private final JavaMailSender javaMailSender;

    private final TemplateEngine htmlTemplateEngine;

//    public EmailService(Environment environment, JavaMailSender javaMailSender, TemplateEngine htmlTemplateEngine) {
//        this.environment = environment;
//        this.javaMailSender = javaMailSender;
//        this.htmlTemplateEngine = htmlTemplateEngine;
//    }

    public void sendMail(User user) throws MessagingException, UnsupportedEncodingException {

        String confirmationUrl = "generated_confirmation_url";
        String mailFrom = environment.getProperty("spring.mail.properties.mail.smtp.from");
        String mailFromName = environment.getProperty("mail.from.name", "identify");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Context context = new Context();
        context.setVariable("email", user.getUserInformation().getEmail());
        context.setVariable("name", user.getUserInformation().getName());
        String htmlContent = htmlTemplateEngine.process("registerOrganization", context);

        helper.setTo(user.getUserInformation().getEmail());
        helper.setFrom(new InternetAddress(mailFrom, mailFromName));
        helper.setSubject(MAIN_SUBJECT);
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);

    }
}

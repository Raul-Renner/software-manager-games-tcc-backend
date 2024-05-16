package com.br.service;

import com.br.entities.Mail;
import com.br.entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    private final TemplateEngine htmlTemplateEngine;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String emailFrom;

    //    public EmailService(Environment environment, JavaMailSender javaMailSender, TemplateEngine htmlTemplateEngine) {
    //        this.environment = environment;
    //        this.javaMailSender = javaMailSender;
    //        this.htmlTemplateEngine = htmlTemplateEngine;
    //    }

    public void sendMail(Mail mail) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String htmlContent = getHtmlContent(mail);

        helper.setTo(mail.getTo());
        helper.setFrom(mail.getFrom());
        helper.setSubject(mail.getSubject());
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);

    }

    private String getHtmlContent(Mail mail){
        var context = new Context();
        context.setVariables(mail.getHtmlTemplate().getProps());
        return htmlTemplateEngine.process(mail.getHtmlTemplate().getTemplate(), context);
    }

    public void buildTemplateEmailToSendUser(User user, String password, Boolean flag) throws MessagingException, UnsupportedEncodingException {
        if(!flag){
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put("name", user.getUserInformation().getName());
            properties.put("password", password);
            properties.put("login", user.getLogin());


            this.sendMail(Mail.builder()
                    .to(user.getUserInformation().getEmail())
                    .from(emailFrom)
                    .htmlTemplate(new Mail.HtmlTemplate("registerOrganization", properties))
                    .subject("Organização Cadastrado com sucesso!")
                    .build());
        }else {
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put("name", user.getUserInformation().getName());
            properties.put("profile", user.getProfile().name());
            properties.put("organization", user.getOrganization().getName());
            properties.put("password", password);
            properties.put("login", user.getLogin());


            sendMail(Mail.builder()
                    .to(user.getUserInformation().getEmail())
                    .from(emailFrom)
                    .htmlTemplate(new Mail.HtmlTemplate("ColaboratorRegister", properties))
                    .subject("Seja bem vindo a equipe " + user.getOrganization().getName())
                    .build());
        }
    }
}

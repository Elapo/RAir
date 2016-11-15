package com.realdolmen.rair.domain;

import com.realdolmen.rair.domain.builder.MailBuilder;
import com.realdolmen.rair.domain.jsf.SessionBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

@Named
@ApplicationScoped
public class MailService {
    private static final String username = "noreply.rair@gmail.com", password = "Realdolmen123";

    private Properties props = new Properties();

    private Session session;

    @PostConstruct
    private void init() {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public void sendMail(MailBuilder mailBuilder) {
        try {
            sendMail(mailBuilder.build(session));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public MailBuilder newMail() {
        return new MailBuilder();
    }

    public void sendMail(Message mail) throws MessagingException {
        if (SessionBean.DEBUG_MODE) {
            mail.setRecipient(Message.RecipientType.TO, new InternetAddress("noreply.rair@gmail.com"));
        }
        Transport.send(mail);
    }
}

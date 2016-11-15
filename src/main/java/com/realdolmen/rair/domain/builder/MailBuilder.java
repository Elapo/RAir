package com.realdolmen.rair.domain.builder;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class MailBuilder {

    private String from, subject, body;

    private Map<Message.RecipientType, List<String>> recipientTypeListMap = new HashMap<>();

    public MailBuilder() {
        recipientTypeListMap.put(Message.RecipientType.TO, new ArrayList<>());
        recipientTypeListMap.put(Message.RecipientType.CC, new ArrayList<>());
        recipientTypeListMap.put(Message.RecipientType.BCC, new ArrayList<>());
    }

    public MailBuilder from(String email) {
        from = email;
        return this;
    }

    public MailBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public MailBuilder body(String body) {
        this.body = body;
        return this;
    }

    public MailBuilder to(String mail) {
        recipientTypeListMap.get(Message.RecipientType.TO).add(mail);
        return this;
    }

    public MailBuilder cc(String mail) {
        recipientTypeListMap.get(Message.RecipientType.CC).add(mail);
        return this;
    }

    public MailBuilder bcc(String mail) {
        recipientTypeListMap.get(Message.RecipientType.BCC).add(mail);
        return this;
    }

    public Message build(Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        recipientTypeListMap.forEach((type, list) -> {
            list.forEach(recipient -> {
                try {
                    message.addRecipient(type, new InternetAddress(recipient));
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        });
        message.setSubject(subject);
        message.setText(body);
        message.setSentDate(new Date());
        return message;
    }
}

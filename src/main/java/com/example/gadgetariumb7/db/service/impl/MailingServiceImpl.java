package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Mailing;
import com.example.gadgetariumb7.db.entity.Subscription;
import com.example.gadgetariumb7.db.repository.MailingRepository;
import com.example.gadgetariumb7.db.service.MailingService;
import com.example.gadgetariumb7.db.service.SubscriptionService;
import com.example.gadgetariumb7.dto.request.MailingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {

    private final SubscriptionService subscriptionService;
    private final JavaMailSender emailSender;
    private final MailingRepository mailingRepository;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void saveMailing(Mailing mailing) {
        mailingRepository.save(mailing);
    }

    @Override
    public void sendMailing(MailingRequest mailingRequest) {
        Mailing mailing = new Mailing();
        BeanUtils.copyProperties(mailingRequest, mailing);
        List<Subscription> subscribers = subscriptionService.findAll();
        for (Subscription subscriber : subscribers) {
            sendEmail(subscriber.getEmail(), mailing);
        }
        saveMailing(mailing);
    }

    @Override
    public void sendEmail(String email, Mailing mailing) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject(mailing.getMailingName());
        message.setText(mailing.getDescription() + "\n" + mailing.getImage()
                + "\n" + "Дата начала акции: " + mailing.getMailingDateOfStart() + "\n" + "Дата окончании акции: " + mailing.getMailingDateOfEnd());
        message.setBcc();
        emailSender.send(message);
    }
}

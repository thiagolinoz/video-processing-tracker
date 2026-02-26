package br.com.fiap_postech.video_processing_tracker.adapters.notification;

import br.com.fiap_postech.video_processing_tracker.domain.ports.out.NotificationPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class NotificationEmail implements NotificationPort {

    private final MailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;

    public NotificationEmail(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(email);
        message.setText("Error in processing your video, try again");

        mailSender.send(message);

    }
}

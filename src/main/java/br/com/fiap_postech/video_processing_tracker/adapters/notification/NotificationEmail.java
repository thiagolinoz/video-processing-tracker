package br.com.fiap_postech.video_processing_tracker.adapters.notification;

import br.com.fiap_postech.video_processing_tracker.domain.ports.out.NotificationPort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationEmail implements NotificationPort {

    private final JavaMailSender mailSender;

    public NotificationEmail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String email) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("videopostech@gmail.com");
        message.setTo(email);
        message.setText("Error in processing your video, try again");

        mailSender.send(message);
    }
}

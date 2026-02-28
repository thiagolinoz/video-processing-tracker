package br.com.fiap_postech.video_processing_tracker.adapters.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationEmailTest {

    @Mock
    private MailSender mailSender;

    @InjectMocks
    private NotificationEmail notificationEmail;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(notificationEmail, "emailFrom", "adicionar-email-valido@email.com");
    }

    @Test
    @DisplayName("Deve montar e enviar o email corretamente")
    void send_deveMontarEmailEEnviar() {
        String destinatario = "user@email.com";

        notificationEmail.send(destinatario);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage mensagem = captor.getValue();
        assertThat(mensagem.getTo()).containsExactly(destinatario);
        assertThat(mensagem.getFrom()).isEqualTo("adicionar-email-valido@email.com");
        assertThat(mensagem.getText()).isEqualTo("Error in processing your video, try again");
    }

    @Test
    @DisplayName("Deve chamar mailSender exatamente uma vez por chamada")
    void send_deveInvocarMailSenderUmaVez() {
        notificationEmail.send("outro@email.com");

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}

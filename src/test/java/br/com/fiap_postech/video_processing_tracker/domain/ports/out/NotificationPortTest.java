package br.com.fiap_postech.video_processing_tracker.domain.ports.out;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationPortTest {

    @Mock
    private NotificationPort notificationPort;

    @Test
    @DisplayName("send deve ser invocado com o email correto")
    void send_deveSerInvocadoComEmailCorreto() {
        notificationPort.send("user@email.com");

        verify(notificationPort, times(1)).send("user@email.com");
    }

    @Test
    @DisplayName("send não deve ser invocado sem chamada explícita")
    void send_semChamada_naoDeveExecutar() {
        verifyNoInteractions(notificationPort);
    }

    @Test
    @DisplayName("send pode ser chamado múltiplas vezes com emails diferentes")
    void send_comEmailsDiferentes_deveRegistrarCadaInvocacao() {
        notificationPort.send("a@email.com");
        notificationPort.send("b@email.com");

        verify(notificationPort, times(1)).send("a@email.com");
        verify(notificationPort, times(1)).send("b@email.com");
    }
}

package br.com.fiap_postech.video_processing_tracker.adapters.event.listener;

import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileEventListenerTest {

    @Mock
    private VideoMetadataServicePort videoMetadataServicePort;

    @Mock
    private Acknowledgment acknowledgment;

    @InjectMocks
    private FileEventListener fileEventListener;

    @Test
    @DisplayName("Deve desserializar a mensagem Kafka e chamar o serviço com o VideoModel correto")
    void consume_comMensagemValida_deveMapearEChamarServico() throws Exception {
        String message = """
                {
                  "nmPersonEmail": "user@email.com",
                  "idVideoSend": "video-id-123",
                  "cdVideoStatus": "PROCESSING",
                  "nmVideo": "meu-video.mp4",
                  "nmPersonName": "User Name",
                  "nmVideoPathZip": "/zip/path"
                }
                """;

        fileEventListener.consume(message, acknowledgment);

        ArgumentCaptor<VideoModel> captor = ArgumentCaptor.forClass(VideoModel.class);
        verify(videoMetadataServicePort, times(1)).handleVideo(captor.capture());
        verify(acknowledgment, times(1)).acknowledge();

        VideoModel captured = captor.getValue();
        assertThat(captured.getNmPessoaEmail()).isEqualTo("user@email.com");
        assertThat(captured.getIdVideoSend()).isEqualTo("video-id-123");
        assertThat(captured.getCdVideoStatus()).isEqualTo("PROCESSING");
        assertThat(captured.getNmVideo()).isEqualTo("meu-video.mp4");
        assertThat(captured.getNmPersonName()).isEqualTo("User Name");
        assertThat(captured.getNmVideoPathZip()).isEqualTo("/zip/path");
    }

    @Test
    @DisplayName("Deve dar acknowledge e ignorar mensagem inválida (sem email e sem idVideoSend)")
    void consume_comMensagemInvalida_deveIgnorarEAcknowledge() {
        String message = "{\"nmVideo\": \"video.mp4\"}";

        fileEventListener.consume(message, acknowledgment);

        verify(videoMetadataServicePort, never()).handleVideo(any());
        verify(acknowledgment, times(1)).acknowledge();
    }

    @Test
    @DisplayName("Não deve dar acknowledge quando JSON for inválido")
    void consume_comJsonInvalido_naoDeveAcknowledge() {
        String message = "nao-e-um-json-valido";

        fileEventListener.consume(message, acknowledgment);

        verify(videoMetadataServicePort, never()).handleVideo(any());
        verify(acknowledgment, never()).acknowledge();
    }
}

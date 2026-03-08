package br.com.fiap_postech.video_processing_tracker.domain.ports.in;

import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoMetadataServicePortTest {

    @Mock
    private VideoMetadataServicePort videoMetadataServicePort;

    @Test
    @DisplayName("handleVideo deve ser invocado com o VideoModel correto")
    void handleVideo_deveSerInvocadoComVideoModelCorreto() {
        VideoModel model = VideoModel.builder()
                .nmPessoaEmail("user@email.com")
                .idVideoSend("vid-1")
                .cdVideoStatus("RECEIVED")
                .build();

        videoMetadataServicePort.handleVideo(model);

        verify(videoMetadataServicePort, times(1)).handleVideo(model);
    }

    @Test
    @DisplayName("handleVideo não deve ser invocado caso não seja chamado")
    void handleVideo_semChamada_naoDeveExecutar() {
        verifyNoInteractions(videoMetadataServicePort);
    }

    @Test
    @DisplayName("handleVideo pode ser chamado múltiplas vezes")
    void handleVideo_multiplasChamadas_deveRegistrarTodasAsInvocacoes() {
        VideoModel model = VideoModel.builder().nmPessoaEmail("a@b.com").build();

        videoMetadataServicePort.handleVideo(model);
        videoMetadataServicePort.handleVideo(model);

        verify(videoMetadataServicePort, times(2)).handleVideo(model);
    }
}

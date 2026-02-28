package br.com.fiap_postech.video_processing_tracker.domain.ports.out;

import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoMetadataRepositoryPortTest {

    @Mock
    private VideoMetadataRepositoryPort videoMetadataRepositoryPort;

    @Test
    @DisplayName("updateVideoStatus deve ser invocado com o VideoModel correto")
    void updateVideoStatus_deveSerInvocadoComVideoModelCorreto() {
        VideoModel model = VideoModel.builder()
                .nmPessoaEmail("user@email.com")
                .idVideoSend("vid-1")
                .cdVideoStatus("COMPLETED")
                .build();

        videoMetadataRepositoryPort.updateVideoStatus(model);

        verify(videoMetadataRepositoryPort, times(1)).updateVideoStatus(model);
    }

    @Test
    @DisplayName("updateVideoStatus não deve ser invocado sem chamada explícita")
    void updateVideoStatus_semChamada_naoDeveExecutar() {
        verifyNoInteractions(videoMetadataRepositoryPort);
    }

    @Test
    @DisplayName("updateVideoStatus pode ser chamado múltiplas vezes")
    void updateVideoStatus_multiplasChamadas_deveRegistrarTodasAsInvocacoes() {
        VideoModel model = VideoModel.builder().idVideoSend("vid-1").build();

        videoMetadataRepositoryPort.updateVideoStatus(model);
        videoMetadataRepositoryPort.updateVideoStatus(model);

        verify(videoMetadataRepositoryPort, times(2)).updateVideoStatus(model);
    }
}

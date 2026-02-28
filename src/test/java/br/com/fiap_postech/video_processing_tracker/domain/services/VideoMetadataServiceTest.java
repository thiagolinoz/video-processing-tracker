package br.com.fiap_postech.video_processing_tracker.domain.services;

import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.NotificationPort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.VideoMetadataRepositoryPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoMetadataServiceTest {

    @Mock
    private VideoMetadataRepositoryPort videoMetadataRepositoryPort;

    @Mock
    private NotificationPort notificationPort;

    @InjectMocks
    private VideoMetadataService videoMetadataService;

    private VideoModel videoModel;

    @BeforeEach
    void setUp() {
        videoModel = VideoModel.builder()
                .nmPessoaEmail("user@email.com")
                .idVideoSend("video-id-123")
                .nmVideo("meu-video.mp4")
                .nmPersonName("User Name")
                .nmVideoPathZip("/zip/path")
                .build();
    }

    @Test
    @DisplayName("Deve atualizar o status do vídeo quando status for RECEIVED")
    void handleVideo_quandoStatusRECEIVED_deveAtualizarStatus() {
        videoModel.setCdVideoStatus("RECEIVED");

        videoMetadataService.handleVideo(videoModel);

        verify(videoMetadataRepositoryPort, times(1)).updateVideoStatus(videoModel);
        verify(notificationPort, never()).send(any());
    }

    @Test
    @DisplayName("Deve atualizar status e enviar notificação quando status for PROCESS_ERROR")
    void handleVideo_quandoStatusPROCESS_ERROR_deveAtualizarENotificar() {
        videoModel.setCdVideoStatus("PROCESS_ERROR");

        videoMetadataService.handleVideo(videoModel);

        verify(videoMetadataRepositoryPort, times(1)).updateVideoStatus(videoModel);
        verify(notificationPort, times(1)).send("user@email.com");
    }

    @Test
    @DisplayName("Deve atualizar o status do vídeo quando status for PROCESSING")
    void handleVideo_quandoStatusPROCESSING_deveAtualizarStatus() {
        videoModel.setCdVideoStatus("PROCESSING");

        videoMetadataService.handleVideo(videoModel);

        verify(videoMetadataRepositoryPort, times(1)).updateVideoStatus(videoModel);
        verify(notificationPort, never()).send(any());
    }

    @Test
    @DisplayName("Deve atualizar o status do vídeo quando status for COMPLETED")
    void handleVideo_quandoStatusCOMPLETED_deveAtualizarStatus() {
        videoModel.setCdVideoStatus("COMPLETED");

        videoMetadataService.handleVideo(videoModel);

        verify(videoMetadataRepositoryPort, times(1)).updateVideoStatus(videoModel);
        verify(notificationPort, never()).send(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando status for inválido")
    void handleVideo_quandoStatusInvalido_deveLancarExcecao() {
        videoModel.setCdVideoStatus("STATUS_INVALIDO");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> videoMetadataService.handleVideo(videoModel));

        verify(videoMetadataRepositoryPort, never()).updateVideoStatus(any());
        verify(notificationPort, never()).send(any());
    }
}

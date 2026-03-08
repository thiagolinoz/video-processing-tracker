package br.com.fiap_postech.video_processing_tracker.adapters.event.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class VideoUploadedMessageTest {

    @Test
    @DisplayName("isValidForProcessing deve retornar true quando email e idVideoSend estão preenchidos")
    void isValidForProcessing_comCamposObrigatoriosPreenchidos_deveRetornarTrue() {
        VideoUploadedMessage message = VideoUploadedMessage.builder()
                .nmPersonEmail("user@email.com")
                .idVideoSend("video-id-123")
                .cdVideoStatus("PROCESSING")
                .nmVideo("video.mp4")
                .build();

        assertThat(message.isValidForProcessing()).isTrue();
    }

    @Test
    @DisplayName("isValidForProcessing deve retornar false quando email é nulo")
    void isValidForProcessing_comEmailNulo_deveRetornarFalse() {
        VideoUploadedMessage message = VideoUploadedMessage.builder()
                .nmPersonEmail(null)
                .idVideoSend("video-id-123")
                .build();

        assertThat(message.isValidForProcessing()).isFalse();
    }

    @Test
    @DisplayName("isValidForProcessing deve retornar false quando idVideoSend é nulo")
    void isValidForProcessing_comIdVideoSendNulo_deveRetornarFalse() {
        VideoUploadedMessage message = VideoUploadedMessage.builder()
                .nmPersonEmail("user@email.com")
                .idVideoSend(null)
                .build();

        assertThat(message.isValidForProcessing()).isFalse();
    }

    @Test
    @DisplayName("isValidForProcessing deve retornar false quando ambos os campos obrigatórios são nulos")
    void isValidForProcessing_comAmbosCamposNulos_deveRetornarFalse() {
        VideoUploadedMessage message = new VideoUploadedMessage();

        assertThat(message.isValidForProcessing()).isFalse();
    }

    @Test
    @DisplayName("Builder deve criar objeto com todos os campos corretamente")
    void builder_deveCriarObjetoComTodosOsCampos() {
        Date dateCompleted = new Date(1740736800000L);

        VideoUploadedMessage message = VideoUploadedMessage.builder()
                .nmPersonEmail("user@email.com")
                .idVideoSend("vid-01")
                .cdVideoStatus("COMPLETED")
                .nmVideo("video.mp4")
                .nmPersonName("User Name")
                .dateTimeVideoProcessCompleted(dateCompleted)
                .nmVideoPathZip("/zip/path")
                .errorMessage("erro")
                .build();

        assertThat(message.getNmPersonEmail()).isEqualTo("user@email.com");
        assertThat(message.getIdVideoSend()).isEqualTo("vid-01");
        assertThat(message.getCdVideoStatus()).isEqualTo("COMPLETED");
        assertThat(message.getNmVideo()).isEqualTo("video.mp4");
        assertThat(message.getNmPersonName()).isEqualTo("User Name");
        assertThat(message.getDateTimeVideoProcessCompleted()).isEqualTo(dateCompleted);
        assertThat(message.getNmVideoPathZip()).isEqualTo("/zip/path");
        assertThat(message.getErrorMessage()).isEqualTo("erro");
    }

    @Test
    @DisplayName("Construtor padrão deve inicializar errorMessage e dateTimeVideoProcessCompleted como null")
    void construtorPadrao_deveInicializarCamposDefaultsComoNull() {
        VideoUploadedMessage message = new VideoUploadedMessage();

        assertThat(message.getErrorMessage()).isNull();
        assertThat(message.getDateTimeVideoProcessCompleted()).isNull();
    }
}

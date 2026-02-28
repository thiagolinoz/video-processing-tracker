package br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers;

import br.com.fiap_postech.video_processing_tracker.adapters.event.message.VideoUploadedMessage;
import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.infra.db.entities.VideoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class VideoMapperTest {

    private static final Date DATE_COMPLETED = new Date(1740736800000L); // 2026-02-28T10:00:00Z

    private VideoUploadedMessage buildMessage(String email, String videoId, String status) {
        return VideoUploadedMessage.builder()
                .nmPersonEmail(email)
                .idVideoSend(videoId)
                .cdVideoStatus(status)
                .nmVideo("meu-video.mp4")
                .nmPersonName("User Name")
                .dateTimeVideoProcessCompleted(DATE_COMPLETED)
                .nmVideoPathZip("/zip/path")
                .errorMessage(null)
                .build();
    }

    @Test
    @DisplayName("Deve converter VideoUploadedMessage para VideoModel corretamente")
    void toVideoModelMessage_deveMappearTodosOsCampos() {
        VideoUploadedMessage message = buildMessage("user@email.com", "video-id-123", "RECEIVED");

        VideoModel result = VideoMapper.toVideoModelMessage(message);

        assertThat(result).isNotNull();
        assertThat(result.getNmPessoaEmail()).isEqualTo("user@email.com");
        assertThat(result.getIdVideoSend()).isEqualTo("video-id-123");
        assertThat(result.getCdVideoStatus()).isEqualTo("RECEIVED");
        assertThat(result.getNmVideo()).isEqualTo("meu-video.mp4");
        assertThat(result.getNmPersonName()).isEqualTo("User Name");
        assertThat(result.getNmVideoPathZip()).isEqualTo("/zip/path");
        assertThat(result.getDateTimeVideoProcessCompleted()).isEqualTo(DATE_COMPLETED);
        assertThat(result.getErrorMessage()).isNull();
    }

    @Test
    @DisplayName("Deve converter VideoModel para VideoEntity corretamente")
    void toEntityMessage_deveMappearTodosOsCampos() {
        VideoModel videoModel = VideoModel.builder()
                .nmPessoaEmail("user@email.com")
                .idVideoSend("video-id-123")
                .cdVideoStatus("PROCESSING")
                .nmVideo("meu-video.mp4")
                .nmPersonName("User Name")
                .nmVideoPathZip("/zip/path")
                .dateTimeVideoProcessCompleted(DATE_COMPLETED)
                .errorMessage(null)
                .build();

        VideoEntity result = VideoMapper.toEntityMessage(videoModel);

        assertThat(result).isNotNull();
        assertThat(result.getNmPessoaEmail()).isEqualTo("user@email.com");
        assertThat(result.getIdVideoSend()).isEqualTo("video-id-123");
        assertThat(result.getCdVideoStatus()).isEqualTo("PROCESSING");
        assertThat(result.getNmVideo()).isEqualTo("meu-video.mp4");
        assertThat(result.getNmPersonName()).isEqualTo("User Name");
        assertThat(result.getNmVideoPathZip()).isEqualTo("/zip/path");
        assertThat(result.getDateTimeVideoProcessCompleted()).isEqualTo(DATE_COMPLETED.toInstant());
        assertThat(result.getErrorMessage()).isNull();
    }

    @Test
    @DisplayName("Deve converter VideoModel para VideoEntity com dateTimeVideoProcessCompleted nulo")
    void toEntityMessage_comDataNula_deveMappearSemErro() {
        VideoModel videoModel = VideoModel.builder()
                .nmPessoaEmail("user@email.com")
                .idVideoSend("vid-1")
                .cdVideoStatus("COMPLETED")
                .dateTimeVideoProcessCompleted(null)
                .build();

        VideoEntity result = VideoMapper.toEntityMessage(videoModel);

        assertThat(result.getDateTimeVideoProcessCompleted()).isNull();
        assertThat(result.getNmPessoaEmail()).isEqualTo("user@email.com");
    }

    @Test
    @DisplayName("Deve mapear errorMessage quando presente")
    void toVideoModelMessage_comErrorMessage_deveMappear() {
        VideoUploadedMessage message = VideoUploadedMessage.builder()
                .nmPersonEmail("user@email.com")
                .idVideoSend("vid-err")
                .cdVideoStatus("PROCESS_ERROR")
                .errorMessage("Falha ao processar")
                .build();

        VideoModel result = VideoMapper.toVideoModelMessage(message);

        assertThat(result.getErrorMessage()).isEqualTo("Falha ao processar");
    }
}

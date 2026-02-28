package br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers;

import br.com.fiap_postech.video_processing_tracker.adapters.dto.VideoUploadedCloudEvent;
import br.com.fiap_postech.video_processing_tracker.domain.models.events.VideoUploadedModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class CloudEventMapperTest {

    @Test
@DisplayName("Given um VideoUploadedCloudEvent válido, When mapear para Model, Then deve mapear todos os campos corretamente")
void toModel_deveMapearTodosOsCampos_bdd() {
    // Given
    Instant created = Instant.parse("2026-02-28T10:00:00Z");
    Instant completed = Instant.parse("2026-02-28T11:00:00Z");

    VideoUploadedModel data = new VideoUploadedModel(
            "user@email.com",
            "video-id-123",
            "RECEIVED",
            "meu-video.mp4",
            "/origin/path",
            "/zip/path",
            created,
            completed,
            "User Name"
    );

    VideoUploadedCloudEvent event = new VideoUploadedCloudEvent(
            "1.0",
            "video.uploaded",
            "video-service",
            "video-id-123",
            "event-id-abc",
            "2026-02-28T10:00:00Z",
            "application/json",
            data
    );

    // When
    VideoUploadedModel result = CloudEventMapper.toModel(event);

    // Then
    assertThat(result).isNotNull();
    
    assertThat(result.nmPessoaEmail()).isEqualTo(data.nmPersonName());
    assertThat(result.idVideoSend()).isEqualTo("video-id-123");
    assertThat(result.cdVideoStatus()).isEqualTo("RECEIVED");
    assertThat(result.nmVideo()).isEqualTo("meu-video.mp4");
    assertThat(result.nmVideoPathOrigin()).isEqualTo("/origin/path");
    assertThat(result.nmVideoPathZip()).isEqualTo("/zip/path");
    assertThat(result.dateTimeVideoCreated()).isEqualTo(created);
    assertThat(result.dateTimeVideoProcessCompleted()).isEqualTo(completed);
    assertThat(result.nmPersonName()).isEqualTo("User Name");
}

    @Test
    @DisplayName("Deve mapear corretamente com campos de data nulos")
    void toModel_comDatasNulas_deveMappear() {
        VideoUploadedModel data = new VideoUploadedModel(
                "user@email.com", "vid-456", "PROCESSING",
                "video.mp4", "/src", "/zip",
                null, null, "Outro Nome"
        );

        VideoUploadedCloudEvent event = new VideoUploadedCloudEvent(
                "1.0", "video.processing", "svc", "vid-456",
                "eid-2", "2026-02-28T12:00:00Z", "application/json", data
        );

        VideoUploadedModel result = CloudEventMapper.toModel(event);

        assertThat(result.dateTimeVideoCreated()).isNull();
        assertThat(result.dateTimeVideoProcessCompleted()).isNull();
        assertThat(result.idVideoSend()).isEqualTo("vid-456");
    }
}

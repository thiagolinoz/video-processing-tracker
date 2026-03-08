package br.com.fiap_postech.video_processing_tracker.adapters.dto;

import br.com.fiap_postech.video_processing_tracker.domain.models.events.VideoUploadedModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class VideoUploadedCloudEventTest {

    private VideoUploadedModel buildData() {
        return new VideoUploadedModel(
                "user@email.com",
                "video-id-123",
                "RECEIVED",
                "meu-video.mp4",
                "/origin/path",
                "/zip/path",
                Instant.parse("2026-02-28T10:00:00Z"),
                null,
                "User Name"
        );
    }

    @Test
    @DisplayName("Deve criar o record com todos os campos corretamente")
    void record_deveCriarComTodosOsCampos() {
        VideoUploadedModel data = buildData();

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

        assertThat(event.specversion()).isEqualTo("1.0");
        assertThat(event.type()).isEqualTo("video.uploaded");
        assertThat(event.source()).isEqualTo("video-service");
        assertThat(event.subject()).isEqualTo("video-id-123");
        assertThat(event.id()).isEqualTo("event-id-abc");
        assertThat(event.time()).isEqualTo("2026-02-28T10:00:00Z");
        assertThat(event.datacontenttype()).isEqualTo("application/json");
        assertThat(event.data()).isEqualTo(data);
    }

    @Test
    @DisplayName("Dois records com mesmos campos devem ser iguais (equals)")
    void equals_comMesmosCampos_deveSerIgual() {
        VideoUploadedModel data = buildData();

        VideoUploadedCloudEvent event1 = new VideoUploadedCloudEvent(
                "1.0", "video.uploaded", "svc", "vid-1",
                "eid-1", "2026-02-28T10:00:00Z", "application/json", data
        );
        VideoUploadedCloudEvent event2 = new VideoUploadedCloudEvent(
                "1.0", "video.uploaded", "svc", "vid-1",
                "eid-1", "2026-02-28T10:00:00Z", "application/json", data
        );

        assertThat(event1).isEqualTo(event2);
        assertThat(event1.hashCode()).isEqualTo(event2.hashCode());
    }

    @Test
    @DisplayName("Records com campos diferentes devem ser diferentes")
    void equals_comCamposDiferentes_deveSerDiferente() {
        VideoUploadedModel data = buildData();

        VideoUploadedCloudEvent event1 = new VideoUploadedCloudEvent(
                "1.0", "video.uploaded", "svc", "vid-1",
                "eid-1", "2026-02-28T10:00:00Z", "application/json", data
        );
        VideoUploadedCloudEvent event2 = new VideoUploadedCloudEvent(
                "1.0", "video.uploaded", "svc", "vid-2",
                "eid-2", "2026-02-28T10:00:00Z", "application/json", data
        );

        assertThat(event1).isNotEqualTo(event2);
    }

    @Test
    @DisplayName("Deve aceitar data nula sem lançar exceção")
    void record_comDataNula_deveCriarSemErro() {
        VideoUploadedCloudEvent event = new VideoUploadedCloudEvent(
                "1.0", "video.uploaded", "svc", "vid-1",
                "eid-1", "2026-02-28T10:00:00Z", "application/json", null
        );

        assertThat(event.data()).isNull();
        assertThat(event.id()).isEqualTo("eid-1");
    }

    @Test
    @DisplayName("toString deve conter os valores do record")
    void toString_deveConterValoresDoRecord() {
        VideoUploadedCloudEvent event = new VideoUploadedCloudEvent(
                "1.0", "video.uploaded", "svc", "vid-1",
                "eid-abc", "2026-02-28T10:00:00Z", "application/json", null
        );

        assertThat(event.toString()).contains("eid-abc");
    }
}

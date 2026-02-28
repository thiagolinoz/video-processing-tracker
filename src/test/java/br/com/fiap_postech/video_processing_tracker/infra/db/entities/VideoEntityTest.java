package br.com.fiap_postech.video_processing_tracker.infra.db.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class VideoEntityTest {

    private static final Instant CREATED = Instant.parse("2026-02-28T10:00:00Z");
    private static final Instant COMPLETED = Instant.parse("2026-02-28T11:00:00Z");

    @Test
    @DisplayName("Setters e getters devem funcionar corretamente")
    void settersGetters_devemFuncionarCorretamente() {
        VideoEntity entity = new VideoEntity();

        entity.setNmPessoaEmail("user@email.com");
        entity.setIdVideoSend("vid-1");
        entity.setCdVideoStatus("PROCESSING");
        entity.setNmVideo("video.mp4");
        entity.setNmVideoPathOrigin("/origin");
        entity.setNmVideoPathZip("/zip");
        entity.setDateTimeVideoCreated(CREATED);
        entity.setDateTimeVideoProcessCompleted(COMPLETED);
        entity.setNmPersonName("User Name");
        entity.setErrorMessage("erro");

        assertThat(entity.getNmPessoaEmail()).isEqualTo("user@email.com");
        assertThat(entity.getIdVideoSend()).isEqualTo("vid-1");
        assertThat(entity.getCdVideoStatus()).isEqualTo("PROCESSING");
        assertThat(entity.getNmVideo()).isEqualTo("video.mp4");
        assertThat(entity.getNmVideoPathOrigin()).isEqualTo("/origin");
        assertThat(entity.getNmVideoPathZip()).isEqualTo("/zip");
        assertThat(entity.getDateTimeVideoCreated()).isEqualTo(CREATED);
        assertThat(entity.getDateTimeVideoProcessCompleted()).isEqualTo(COMPLETED);
        assertThat(entity.getNmPersonName()).isEqualTo("User Name");
        assertThat(entity.getErrorMessage()).isEqualTo("erro");
    }

    @Test
    @DisplayName("Construtor AllArgs deve preencher todos os campos")
    void construtorAllArgs_devePreencherTodosOsCampos() {
        VideoEntity entity = new VideoEntity(
                "user@email.com", "vid-1", "COMPLETED",
                "video.mp4", "/origin", "/zip",
                CREATED, COMPLETED, "User Name", "erro"
        );

        assertThat(entity.getNmPessoaEmail()).isEqualTo("user@email.com");
        assertThat(entity.getCdVideoStatus()).isEqualTo("COMPLETED");
        assertThat(entity.getDateTimeVideoCreated()).isEqualTo(CREATED);
        assertThat(entity.getDateTimeVideoProcessCompleted()).isEqualTo(COMPLETED);
        assertThat(entity.getErrorMessage()).isEqualTo("erro");
    }

    @Test
    @DisplayName("Construtor padrão deve criar objeto com campos nulos")
    void construtorPadrao_deveCriarObjetoComCamposNulos() {
        VideoEntity entity = new VideoEntity();

        assertThat(entity.getNmPessoaEmail()).isNull();
        assertThat(entity.getIdVideoSend()).isNull();
        assertThat(entity.getCdVideoStatus()).isNull();
    }

    @Test
    @DisplayName("Dois VideoEntity com mesmos campos devem ser iguais (equals/hashCode do Lombok)")
    void equals_comMesmosCampos_deveSerIgual() {
        VideoEntity e1 = new VideoEntity("a@b.com", "vid-1", "RECEIVED",
                "v.mp4", null, "/zip", null, null, "Nome", null);
        VideoEntity e2 = new VideoEntity("a@b.com", "vid-1", "RECEIVED",
                "v.mp4", null, "/zip", null, null, "Nome", null);

        assertThat(e1).isEqualTo(e2);
        assertThat(e1.hashCode()).isEqualTo(e2.hashCode());
    }

    @Test
    @DisplayName("toString deve conter os campos principais")
    void toString_deveConterCamposPrincipais() {
        VideoEntity entity = new VideoEntity();
        entity.setNmPessoaEmail("user@email.com");
        entity.setIdVideoSend("vid-1");

        String result = entity.toString();

        assertThat(result).contains("user@email.com");
        assertThat(result).contains("vid-1");
    }
}

package br.com.fiap_postech.video_processing_tracker.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class VideoModelTest {

    @Test
    @DisplayName("Builder deve criar VideoModel com todos os campos preenchidos")
    void builder_deveCriarVideoModelComTodosOsCampos() {
        Date dateCompleted = new Date(1740736800000L);

        VideoModel model = VideoModel.builder()
                .nmPessoaEmail("user@email.com")
                .idVideoSend("video-id-123")
                .cdVideoStatus("PROCESSING")
                .nmVideo("video.mp4")
                .nmPersonName("User Name")
                .nmVideoPathZip("/zip/path")
                .dateTimeVideoProcessCompleted(dateCompleted)
                .errorMessage("erro")
                .build();

        assertThat(model.getNmPessoaEmail()).isEqualTo("user@email.com");
        assertThat(model.getIdVideoSend()).isEqualTo("video-id-123");
        assertThat(model.getCdVideoStatus()).isEqualTo("PROCESSING");
        assertThat(model.getNmVideo()).isEqualTo("video.mp4");
        assertThat(model.getNmPersonName()).isEqualTo("User Name");
        assertThat(model.getNmVideoPathZip()).isEqualTo("/zip/path");
        assertThat(model.getDateTimeVideoProcessCompleted()).isEqualTo(dateCompleted);
        assertThat(model.getErrorMessage()).isEqualTo("erro");
    }

    @Test
    @DisplayName("Setters devem atualizar os campos corretamente")
    void setters_devemAtualizarCampos() {
        VideoModel model = new VideoModel();
        Date date = new Date();

        model.setNmPessoaEmail("novo@email.com");
        model.setIdVideoSend("novo-id");
        model.setCdVideoStatus("COMPLETED");
        model.setNmVideo("novo-video.mp4");
        model.setNmPersonName("Novo Nome");
        model.setNmVideoPathZip("/novo/zip");
        model.setDateTimeVideoProcessCompleted(date);
        model.setErrorMessage("nova mensagem");

        assertThat(model.getNmPessoaEmail()).isEqualTo("novo@email.com");
        assertThat(model.getIdVideoSend()).isEqualTo("novo-id");
        assertThat(model.getCdVideoStatus()).isEqualTo("COMPLETED");
        assertThat(model.getNmVideo()).isEqualTo("novo-video.mp4");
        assertThat(model.getNmPersonName()).isEqualTo("Novo Nome");
        assertThat(model.getNmVideoPathZip()).isEqualTo("/novo/zip");
        assertThat(model.getDateTimeVideoProcessCompleted()).isEqualTo(date);
        assertThat(model.getErrorMessage()).isEqualTo("nova mensagem");
    }

    @Test
    @DisplayName("Dois VideoModel com mesmos campos devem ser iguais (equals/hashCode do Lombok)")
    void equals_comMesmosCampos_deveSerIgual() {
        VideoModel model1 = VideoModel.builder()
                .nmPessoaEmail("user@email.com")
                .idVideoSend("vid-1")
                .cdVideoStatus("RECEIVED")
                .build();

        VideoModel model2 = VideoModel.builder()
                .nmPessoaEmail("user@email.com")
                .idVideoSend("vid-1")
                .cdVideoStatus("RECEIVED")
                .build();

        assertThat(model1).isEqualTo(model2);
        assertThat(model1.hashCode()).isEqualTo(model2.hashCode());
    }

    @Test
    @DisplayName("Construtor padrão deve criar objeto com campos nulos")
    void construtorPadrao_deveCriarObjetoComCamposNulos() {
        VideoModel model = new VideoModel();

        assertThat(model.getNmPessoaEmail()).isNull();
        assertThat(model.getIdVideoSend()).isNull();
        assertThat(model.getCdVideoStatus()).isNull();
    }
}

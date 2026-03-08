package br.com.fiap_postech.video_processing_tracker.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VideoStatusEnumTest {

    @Test
    @DisplayName("Deve conter todos os status esperados")
    void values_deveConterTodosOsStatus() {
        VideoStatusEnum[] values = VideoStatusEnum.values();

        assertThat(values).containsExactlyInAnyOrder(
                VideoStatusEnum.RECEIVED,
                VideoStatusEnum.PROCESSING,
                VideoStatusEnum.COMPLETED,
                VideoStatusEnum.PROCESS_ERROR
        );
    }

    @Test
    @DisplayName("valueOf deve retornar o enum correto para cada nome")
    void valueOf_deveRetornarEnumCorreto() {
        assertThat(VideoStatusEnum.valueOf("RECEIVED")).isEqualTo(VideoStatusEnum.RECEIVED);
        assertThat(VideoStatusEnum.valueOf("PROCESSING")).isEqualTo(VideoStatusEnum.PROCESSING);
        assertThat(VideoStatusEnum.valueOf("COMPLETED")).isEqualTo(VideoStatusEnum.COMPLETED);
        assertThat(VideoStatusEnum.valueOf("PROCESS_ERROR")).isEqualTo(VideoStatusEnum.PROCESS_ERROR);
    }

    @Test
    @DisplayName("valueOf deve lançar IllegalArgumentException para valor inválido")
    void valueOf_comValorInvalido_deveLancarExcecao() {
        assertThatThrownBy(() -> VideoStatusEnum.valueOf("STATUS_INVALIDO"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Enum deve ter exatamente 4 valores")
    void values_deveConterExatamente4Valores() {
        assertThat(VideoStatusEnum.values()).hasSize(4);
    }
}

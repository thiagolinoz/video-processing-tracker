package br.com.fiap_postech.video_processing_tracker.adapters.web.api.controller;

import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VideoController.class)
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoMetadataServicePort videoMetadataServicePort;

    @Test
    @DisplayName("Deve retornar 201 Created ao criar vídeo")
    void createVideo_deveRetornar201() throws Exception {
        mockMvc.perform(post("/api/v1/user/user@email.com/videos/create/RECEIVED"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/user/videos/create/status"));
    }

    @Test
    @DisplayName("Deve retornar 201 com qualquer status informado na URL")
    void createVideo_comStatusDiferente_deveRetornar201() throws Exception {
        mockMvc.perform(post("/api/v1/user/outro@email.com/videos/create/PROCESS_ERROR"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar 404 para endpoint inexistente")
    void createVideo_comEndpointInvalido_deveRetornar404() throws Exception {
        mockMvc.perform(post("/api/v1/videos"))
                .andExpect(status().isNotFound());
    }
}

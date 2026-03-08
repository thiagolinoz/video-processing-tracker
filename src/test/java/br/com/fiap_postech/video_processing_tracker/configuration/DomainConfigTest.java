package br.com.fiap_postech.video_processing_tracker.configuration;

import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.NotificationPort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.VideoMetadataRepositoryPort;
import br.com.fiap_postech.video_processing_tracker.domain.services.VideoMetadataService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DomainConfigTest {

    @Mock
    private VideoMetadataRepositoryPort videoMetadataRepositoryPort;

    @Mock
    private NotificationPort notificationPort;

    @Test
    @DisplayName("videoMetadataServicePort deve retornar uma instância de VideoMetadataService")
    void videoMetadataServicePort_deveRetornarInstanciaDeVideoMetadataService() {
        DomainConfig domainConfig = new DomainConfig();

        VideoMetadataServicePort servicePort = domainConfig.videoMetadataServicePort(
                videoMetadataRepositoryPort, notificationPort);

        assertThat(servicePort).isNotNull();
        assertThat(servicePort).isInstanceOf(VideoMetadataService.class);
    }

    @Test
    @DisplayName("Chamadas distintas devem retornar instâncias diferentes")
    void videoMetadataServicePort_chamadaDistinta_deveRetornarNovaInstancia() {
        DomainConfig domainConfig = new DomainConfig();

        VideoMetadataServicePort port1 = domainConfig.videoMetadataServicePort(
                videoMetadataRepositoryPort, notificationPort);
        VideoMetadataServicePort port2 = domainConfig.videoMetadataServicePort(
                videoMetadataRepositoryPort, notificationPort);

        assertThat(port1).isNotSameAs(port2);
    }
}

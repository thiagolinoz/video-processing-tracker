package br.com.fiap_postech.video_processing_tracker.configuration;

import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.NotificationPort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.VideoMetadataRepositoryPort;
import br.com.fiap_postech.video_processing_tracker.domain.services.VideoMetadataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public VideoMetadataServicePort videoMetadataServicePort(VideoMetadataRepositoryPort videoMetadataRepositoryPort,
                                                             NotificationPort notificationPort) {
        return new VideoMetadataService(videoMetadataRepositoryPort, notificationPort);
    }

}

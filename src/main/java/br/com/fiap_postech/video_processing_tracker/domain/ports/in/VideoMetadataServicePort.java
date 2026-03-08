package br.com.fiap_postech.video_processing_tracker.domain.ports.in;

import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import org.springframework.stereotype.Component;

@Component
public interface VideoMetadataServicePort {
    void handleVideo(VideoModel videoModel);
}

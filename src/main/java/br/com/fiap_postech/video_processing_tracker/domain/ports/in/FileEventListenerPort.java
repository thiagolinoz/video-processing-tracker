package br.com.fiap_postech.video_processing_tracker.domain.ports.in;

import br.com.fiap_postech.video_processing_tracker.adapters.dto.VideoUploadedCloudEvent;
import br.com.fiap_postech.video_processing_tracker.domain.models.events.VideoUploadedModel;

public interface FileEventListenerPort {

    VideoUploadedModel consume(VideoUploadedCloudEvent event);
}

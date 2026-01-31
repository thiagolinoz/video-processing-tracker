package br.com.fiap_postech.video_processing_tracker.adapters.event.listener;

import br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers.CloudEventMapper;
import br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers.VideoMapper;
import br.com.fiap_postech.video_processing_tracker.adapters.dto.VideoUploadedCloudEvent;
import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.models.events.VideoUploadedModel;
import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;

public class FileEventListener {

    private final VideoMetadataServicePort videoMetadataServicePort;

    public FileEventListener(VideoMetadataServicePort videoMetadataServicePort) {
        this.videoMetadataServicePort = videoMetadataServicePort;
    }

    public VideoModel consume(VideoUploadedCloudEvent event) {
        VideoUploadedModel videoUploadedModel = CloudEventMapper.toModel(event);
        return videoMetadataServicePort.handleVideo(VideoMapper.toVideoModel(videoUploadedModel));
    }
}

package br.com.fiap_postech.video_processing_tracker.adapters.event.listener;

import br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers.CloudEventMapper;
import br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers.VideoMapper;
import br.com.fiap_postech.video_processing_tracker.adapters.dto.VideoUploadedCloudEvent;
import br.com.fiap_postech.video_processing_tracker.domain.models.events.VideoUploadedModel;
import br.com.fiap_postech.video_processing_tracker.domain.ports.in.FileEventListenerPort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.VideoMetadataRepositoryPort;

public class FileEventListener implements FileEventListenerPort {

    private final VideoMetadataServicePort videoMetadataServicePort;

    public FileEventListener(VideoMetadataServicePort videoMetadataServicePort) {
        this.videoMetadataServicePort = videoMetadataServicePort;
    }


    @Override
    public VideoUploadedModel consume(VideoUploadedCloudEvent event) {
        VideoUploadedModel videoUploadedModel = CloudEventMapper.toModel(event);
        videoMetadataServicePort.insertVideo(VideoMapper.toVideoModel(videoUploadedModel));
        return null;
    }
}

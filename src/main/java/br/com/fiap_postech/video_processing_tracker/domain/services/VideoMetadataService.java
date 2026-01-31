package br.com.fiap_postech.video_processing_tracker.domain.services;

import br.com.fiap_postech.video_processing_tracker.domain.enums.VideoStatusEnum;
import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.VideoMetadataRepositoryPort;

public class VideoMetadataService implements VideoMetadataServicePort {

    private final VideoMetadataRepositoryPort videoMetadataRepositoryPort;

    public VideoMetadataService(VideoMetadataRepositoryPort videoMetadataRepositoryPort) {
        this.videoMetadataRepositoryPort = videoMetadataRepositoryPort;
    }

    @Override
    public VideoModel handleVideo(VideoModel videoModel) {
        VideoModel model;

        if (videoModel.getCdVideoStatus().equals(VideoStatusEnum.RECEIVED.name())) {
            model = persistVideo(videoModel);
        } else {
            model = updateVideoStatus(videoModel);
        }

        return model;
    }

    public VideoModel persistVideo(VideoModel videoModel) {
        return videoMetadataRepositoryPort.insertVideo(videoModel);
    }

    public VideoModel updateVideoStatus(VideoModel videoModel) {
        return videoMetadataRepositoryPort.updateVideoStatus(videoModel);
    }
}

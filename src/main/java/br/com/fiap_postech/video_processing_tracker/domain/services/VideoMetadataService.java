package br.com.fiap_postech.video_processing_tracker.domain.services;

import br.com.fiap_postech.video_processing_tracker.domain.enums.VideoStatusEnum;
import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.NotificationPort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.VideoMetadataRepositoryPort;

public class VideoMetadataService implements VideoMetadataServicePort {

    private final VideoMetadataRepositoryPort videoMetadataRepositoryPort;
    private final NotificationPort notificationPort;

    public VideoMetadataService(VideoMetadataRepositoryPort videoMetadataRepositoryPort,
                                NotificationPort notificationPort) {
        this.videoMetadataRepositoryPort = videoMetadataRepositoryPort;
        this.notificationPort = notificationPort;
    }

    @Override
    public void handleVideo(VideoModel videoModel) {
        VideoStatusEnum status = VideoStatusEnum.valueOf(videoModel.getCdVideoStatus());

        switch (status) {
            case RECEIVED -> persistVideo(videoModel);
            case PROCESS_ERROR -> sendNotification(videoModel.getNmPessoaEmail());
            default -> updateVideoStatus(videoModel);
        };
    }

    private void persistVideo(VideoModel videoModel) {
        videoMetadataRepositoryPort.insertVideo(videoModel);
    }

    private void updateVideoStatus(VideoModel videoModel) {
        videoMetadataRepositoryPort.updateVideoStatus(videoModel);
    }

    private void sendNotification(String email) {
        notificationPort.send(email);
    }
}

package br.com.fiap_postech.video_processing_tracker.domain.services;

import br.com.fiap_postech.video_processing_tracker.adapters.event.listener.FileEventListener;
import br.com.fiap_postech.video_processing_tracker.domain.enums.VideoStatusEnum;
import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.NotificationPort;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.VideoMetadataRepositoryPort;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VideoMetadataService implements VideoMetadataServicePort {

    private final VideoMetadataRepositoryPort videoMetadataRepositoryPort;
    private final NotificationPort notificationPort;
    private static final Logger logger = Logger.getLogger(FileEventListener.class.getName());

    public VideoMetadataService(VideoMetadataRepositoryPort videoMetadataRepositoryPort,
                                NotificationPort notificationPort) {
        this.videoMetadataRepositoryPort = videoMetadataRepositoryPort;
        this.notificationPort = notificationPort;
    }

    @Override
    public void handleVideo(VideoModel videoModel) {
        VideoStatusEnum status = VideoStatusEnum.valueOf(videoModel.getCdVideoStatus());

        switch (status) {
            case PROCESS_ERROR -> updateVideoError(videoModel.getNmPessoaEmail(), videoModel);
            default -> updateVideoStatus(videoModel);
        };
    }

    private void updateVideoError(String email, VideoModel videoModel) {
        updateVideoStatus(videoModel);
        sendNotification(email);
    }

    private void updateVideoStatus(VideoModel videoModel) {
        logger.log(Level.INFO, "Atualizando status do video no dynamo com status: {}", videoModel.getCdVideoStatus());
        videoMetadataRepositoryPort.updateVideoStatus(videoModel);
    }

    private void sendNotification(String email) {
        notificationPort.send(email);
    }
}

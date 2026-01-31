package br.com.fiap_postech.video_processing_tracker.domain.ports.out;

import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;

public interface VideoMetadataRepositoryPort {
    void insertVideo(VideoModel videoModel);
    void updateVideoStatus(VideoModel videoModel);
}

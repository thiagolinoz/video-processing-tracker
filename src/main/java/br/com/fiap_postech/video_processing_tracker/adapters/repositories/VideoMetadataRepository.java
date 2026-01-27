package br.com.fiap_postech.video_processing_tracker.adapters.repositories;

import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.VideoMetadataRepositoryPort;

public class VideoMetadataRepository implements VideoMetadataRepositoryPort {

    @Override
    public VideoModel insertVideo(VideoModel videoModel) {
        return null;
    }

    @Override
    public VideoModel updateVideoStatus(VideoModel videoModel) {
        return null;
    }
}

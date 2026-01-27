package br.com.fiap_postech.video_processing_tracker.domain.services;

import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.ports.in.VideoMetadataServicePort;

public class VideoMetadataService implements VideoMetadataServicePort {

    @Override
    public VideoModel insertVideo(VideoModel videoModel) {
        //TODO regras para salvar ou atualizar
        return null;
    }

    @Override
    public VideoModel updateVideoStatus(VideoModel videoModel) {
        return null;
    }
}

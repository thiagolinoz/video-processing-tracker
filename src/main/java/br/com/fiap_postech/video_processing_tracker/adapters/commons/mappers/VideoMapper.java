package br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers;

import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.models.events.VideoUploadedModel;

public class VideoMapper {

    public static VideoModel toVideoModel(VideoUploadedModel videoUploadedModel) {
        return new VideoModel(
                videoUploadedModel.nmPessoaEmail(),
                videoUploadedModel.idVideoSend(),
                videoUploadedModel.cdVideoStatus(),
                videoUploadedModel.nmVideo(),
                videoUploadedModel.nmVideoPathOrigin(),
                videoUploadedModel.nmVideoPathZip(),
                videoUploadedModel.dateTimeVideoCreated(),
                videoUploadedModel.dateTimeVideoProcessCompleted(),
                videoUploadedModel.nmPersonName()
        );
    }
}

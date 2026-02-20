package br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers;

import br.com.fiap_postech.video_processing_tracker.adapters.event.message.VideoUploadedMessage;
import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.infra.db.entities.VideoEntity;

public class VideoMapper {

    public static VideoModel toVideoModel(VideoUploadedMessage videoUploadedMessage) {
        return new VideoModel(
                videoUploadedMessage.nmPessoaEmail(),
                videoUploadedMessage.idVideoSend(),
                videoUploadedMessage.cdVideoStatus(),
                videoUploadedMessage.nmVideo(),
                videoUploadedMessage.nmVideoPathOrigin(),
                videoUploadedMessage.nmVideoPathZip(),
                videoUploadedMessage.dateTimeVideoCreated().toInstant(),
                videoUploadedMessage.dateTimeVideoProcessCompleted().toInstant(),
                videoUploadedMessage.nmPersonName()
        );
    }

    public static VideoEntity toEntity(VideoModel videoModel) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setNmPessoaEmail(videoModel.getNmPessoaEmail());
        videoEntity.setIdVideoSend(videoModel.getIdVideoSend());
        videoEntity.setCdVideoStatus(videoModel.getCdVideoStatus());
        videoEntity.setNmVideo(videoModel.getNmVideo());
        videoEntity.setNmVideoPathOrigin(videoModel.getNmVideoPathOrigin());
        videoEntity.setNmVideoPathZip(videoModel.getNmVideoPathZip());
        videoEntity.setDateTimeVideoCreated(videoModel.getDateTimeVideoCreated());
        videoEntity.setDateTimeVideoProcessCompleted(videoModel.getDateTimeVideoProcessCompleted());
        videoEntity.setNmPersonName(videoModel.getNmPersonName());
        return videoEntity;
    }
}

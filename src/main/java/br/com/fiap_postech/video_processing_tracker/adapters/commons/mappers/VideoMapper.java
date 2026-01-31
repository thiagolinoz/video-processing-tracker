package br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers;

import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.models.events.VideoUploadedModel;
import br.com.fiap_postech.video_processing_tracker.infra.db.entities.VideoEntity;

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

    public static VideoModel toModel(VideoEntity videoEntity) {
        return new VideoModel(
                videoEntity.getNmPessoaEmail(),
                videoEntity.getIdVideoSend(),
                videoEntity.getCdVideoStatus(),
                videoEntity.getNmVideo(),
                videoEntity.getNmVideoPathOrigin(),
                videoEntity.getNmVideoPathZip(),
                videoEntity.getDateTimeVideoCreated(),
                videoEntity.getDateTimeVideoProcessCompleted(),
                videoEntity.getNmPersonName()
        );
    }
}

package br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers;

import br.com.fiap_postech.video_processing_tracker.adapters.event.message.VideoUploadedMessage;
import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.infra.db.entities.VideoEntity;

public class VideoMapper {

    public static VideoModel toVideoModelMessage(VideoUploadedMessage videoUploadedMessage) {
        return new VideoModel(
                videoUploadedMessage.getNmPersonEmail(),
                videoUploadedMessage.getIdVideoSend(),
                videoUploadedMessage.getCdVideoStatus(),
                videoUploadedMessage.getNmVideo(),
                videoUploadedMessage.getNmPersonName(),
                videoUploadedMessage.getDateTimeVideoProcessCompleted(),
                videoUploadedMessage.getNmVideoPathZip(),
                videoUploadedMessage.getErrorMessage() != null ? videoUploadedMessage.getErrorMessage() : null
        );
    }

    public static VideoEntity toEntityMessage(VideoModel videoModel) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setNmPessoaEmail(videoModel.getNmPessoaEmail());
        videoEntity.setIdVideoSend(videoModel.getIdVideoSend());
        videoEntity.setCdVideoStatus(videoModel.getCdVideoStatus());
        videoEntity.setNmVideo(videoModel.getNmVideo());
        videoEntity.setNmPersonName(videoModel.getNmPersonName());
        if (videoModel.getDateTimeVideoProcessCompleted() != null){
            videoEntity.setDateTimeVideoProcessCompleted(videoModel.getDateTimeVideoProcessCompleted().toInstant());
        }else{
            videoEntity.setDateTimeVideoProcessCompleted(null);
        }
        videoEntity.setNmVideoPathZip(videoModel.getNmVideoPathZip());
        videoEntity.setErrorMessage(videoModel.getErrorMessage() != null ? videoModel.getErrorMessage() : null);
        return videoEntity;
    }
}

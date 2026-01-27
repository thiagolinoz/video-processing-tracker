package br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers;

import br.com.fiap_postech.video_processing_tracker.adapters.dto.VideoUploadedCloudEvent;
import br.com.fiap_postech.video_processing_tracker.domain.models.events.VideoUploadedModel;

public class CloudEventMapper {
    public static VideoUploadedModel toModel(VideoUploadedCloudEvent event) {
        return new VideoUploadedModel(event.data().nmPersonName(),
                event.data().idVideoSend(),
                event.data().cdVideoStatus(),
                event.data().nmVideo(),
                event.data().nmVideoPathOrigin(),
                event.data().nmVideoPathZip(),
                event.data().dateTimeVideoCreated(),
                event.data().dateTimeVideoProcessCompleted(),
                event.data().nmPersonName());
    }
}

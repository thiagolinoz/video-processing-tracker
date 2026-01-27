package br.com.fiap_postech.video_processing_tracker.adapters.dto;

import br.com.fiap_postech.video_processing_tracker.domain.models.events.VideoUploadedModel;

public record VideoUploadedCloudEvent(
        String specversion,
        String type,
        String source,
        String subject,
        String id,
        String time,
        String datacontenttype,
        VideoUploadedModel data
) {
}

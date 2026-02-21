package br.com.fiap_postech.video_processing_tracker.adapters.repositories;

import br.com.fiap_postech.video_processing_tracker.adapters.commons.mappers.VideoMapper;
import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.domain.ports.out.VideoMetadataRepositoryPort;
import br.com.fiap_postech.video_processing_tracker.infra.db.entities.VideoEntity;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Component
public class VideoMetadataRepository implements VideoMetadataRepositoryPort {

    private final DynamoDbTable<VideoEntity> tableVideo;

    public VideoMetadataRepository(DynamoDbEnhancedClient enhancedClient) {
        TableSchema<VideoEntity> schema = TableSchema.fromBean(VideoEntity.class);
        this.tableVideo = enhancedClient.table("Videos", schema);
    }

    @Override
    public void updateVideoStatus(VideoModel videoModel) {
        VideoEntity videoEntity = VideoMapper.toEntityMessage(videoModel);
        tableVideo.updateItem(videoEntity);
    }
}

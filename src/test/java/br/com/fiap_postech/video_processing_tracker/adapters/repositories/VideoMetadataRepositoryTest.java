package br.com.fiap_postech.video_processing_tracker.adapters.repositories;

import br.com.fiap_postech.video_processing_tracker.domain.models.VideoModel;
import br.com.fiap_postech.video_processing_tracker.infra.db.entities.VideoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoMetadataRepositoryTest {

    @Mock
    private DynamoDbEnhancedClient enhancedClient;

    @Mock
    private DynamoDbTable<VideoEntity> tableVideo;

    private VideoMetadataRepository repository;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        when(enhancedClient.table(eq("Videos"), any(TableSchema.class))).thenReturn((DynamoDbTable) tableVideo);
        repository = new VideoMetadataRepository(enhancedClient);
    }

    private VideoModel buildVideoModel(String status) {
        return VideoModel.builder()
                .nmPessoaEmail("user@email.com")
                .idVideoSend("video-id-123")
                .cdVideoStatus(status)
                .nmVideo("meu-video.mp4")
                .nmPersonName("User Name")
                .nmVideoPathZip("/zip/path")
                .dateTimeVideoProcessCompleted(new Date())
                .build();
    }

    @Test
    @DisplayName("Deve chamar updateItem ao atualizar status do vídeo")
    @SuppressWarnings("unchecked")
    void updateVideoStatus_deveChamarUpdateItem() {
        VideoModel videoModel = buildVideoModel("COMPLETED");

        repository.updateVideoStatus(videoModel);

        ArgumentCaptor<UpdateItemEnhancedRequest<VideoEntity>> captor =
                ArgumentCaptor.forClass(UpdateItemEnhancedRequest.class);
        verify(tableVideo, times(1)).updateItem(captor.capture());

        VideoEntity entity = captor.getValue().item();
        assertThat(entity.getCdVideoStatus()).isEqualTo("COMPLETED");
        assertThat(entity.getIdVideoSend()).isEqualTo("video-id-123");
        assertThat(entity.getNmPessoaEmail()).isEqualTo("user@email.com");
        assertThat(entity.getNmPersonName()).isEqualTo("User Name");
    }

    @Test
    @DisplayName("Deve chamar updateItem ao atualizar status de vídeo com erro")
    void updateVideoStatus_comStatusPROCESS_ERROR_deveChamarUpdateItem() {
        VideoModel videoModel = buildVideoModel("PROCESS_ERROR");
        videoModel.setErrorMessage("Falha ao processar");

        repository.updateVideoStatus(videoModel);

        verify(tableVideo, times(1)).updateItem(any(UpdateItemEnhancedRequest.class));
    }

    @Test
    @DisplayName("Não deve chamar putItem ao atualizar status")
    void updateVideoStatus_naoDeveChamarPutItem() {
        repository.updateVideoStatus(buildVideoModel("PROCESSING"));

        verify(tableVideo, never()).putItem(any(VideoEntity.class));
    }
}

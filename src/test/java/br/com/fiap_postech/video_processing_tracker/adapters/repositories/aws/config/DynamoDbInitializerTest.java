package br.com.fiap_postech.video_processing_tracker.adapters.repositories.aws.config;

import br.com.fiap_postech.video_processing_tracker.infra.db.entities.VideoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamoDbInitializerTest {

    @Mock
    private DynamoDbEnhancedClient enhancedClient;

    @Mock
    @SuppressWarnings("rawtypes")
    private DynamoDbTable tableVideo;

    private DynamoDbInitializer initializer;

    @BeforeEach
    void setUp() {
        initializer = new DynamoDbInitializer(enhancedClient);
    }

    @Test
    @DisplayName("init não deve criar tabelas quando shouldCreate é false")
    void init_comShouldCreateFalse_naoDeveCriarTabelas() {
        ReflectionTestUtils.setField(initializer, "shouldCreate", false);

        initializer.init();

        verifyNoInteractions(enhancedClient);
    }

    @Test
    @DisplayName("init deve chamar setupTables quando shouldCreate é true")
    @SuppressWarnings("unchecked")
    void init_comShouldCreateTrue_deveChamarSetupTables() {
        ReflectionTestUtils.setField(initializer, "shouldCreate", true);
        when(enhancedClient.table(eq("Videos"), any(TableSchema.class))).thenReturn(tableVideo);

        initializer.init();

        verify(enhancedClient, times(1)).table(eq("Videos"), any(TableSchema.class));
        verify(tableVideo, times(1)).createTable();
    }

    @Test
    @DisplayName("setupTables deve ignorar ResourceInUseException (tabela já existe)")
    @SuppressWarnings("unchecked")
    void setupTables_comResourceInUseException_naoDeveLancarExcecao() {
        when(enhancedClient.table(eq("Videos"), any(TableSchema.class))).thenReturn(tableVideo);
        doThrow(ResourceInUseException.builder().message("Table already exists").build())
                .when(tableVideo).createTable();

        initializer.setupTables(VideoEntity.class, "Videos");

        verify(tableVideo, times(1)).createTable();
    }

    @Test
    @DisplayName("setupTables deve lançar RuntimeException para erros inesperados")
    @SuppressWarnings("unchecked")
    void setupTables_comExcecaoGenerica_deveLancarRuntimeException() {
        when(enhancedClient.table(eq("Videos"), any(TableSchema.class))).thenReturn(tableVideo);
        doThrow(new RuntimeException("Erro inesperado")).when(tableVideo).createTable();

        assertThatThrownBy(() -> initializer.setupTables(VideoEntity.class, "Videos"))
                .isInstanceOf(RuntimeException.class);
    }
}

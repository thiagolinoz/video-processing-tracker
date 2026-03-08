package br.com.fiap_postech.video_processing_tracker.adapters.repositories.aws.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DynamoDbConfigTest {

    @Mock
    private DynamoDbClient dynamoDbClient;

    @Test
    @DisplayName("enhancedClient deve retornar instância não nula a partir do DynamoDbClient")
    void enhancedClient_deveRetornarInstanciaValida() {
        DynamoDbConfig config = new DynamoDbConfig();

        DynamoDbEnhancedClient result = config.enhancedClient(dynamoDbClient);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("dynamoDbClient deve retornar instância não nula com endpoint local")
    void dynamoDbClient_comEndpointLocal_deveRetornarInstanciaValida() {
        DynamoDbConfig config = new DynamoDbConfig();
        ReflectionTestUtils.setField(config, "endpoint", "http://localhost:8000");
        ReflectionTestUtils.setField(config, "region", "us-east-1");

        DynamoDbClient result = config.dynamoDbClient();

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("dynamoDbClient deve retornar instância não nula com endpoint amazonaws")
    void dynamoDbClient_comEndpointAmazonaws_deveRetornarInstanciaValida() {
        DynamoDbConfig config = new DynamoDbConfig();
        ReflectionTestUtils.setField(config, "endpoint", "https://dynamodb.us-east-1.amazonaws.com");
        ReflectionTestUtils.setField(config, "region", "us-east-1");

        DynamoDbClient result = config.dynamoDbClient();

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("enhancedClient chamadas distintas devem retornar instâncias diferentes")
    void enhancedClient_chamadaDistinta_deveRetornarNovaInstancia() {
        DynamoDbConfig config = new DynamoDbConfig();

        DynamoDbEnhancedClient c1 = config.enhancedClient(dynamoDbClient);
        DynamoDbEnhancedClient c2 = config.enhancedClient(dynamoDbClient);

        assertThat(c1).isNotSameAs(c2);
    }
}

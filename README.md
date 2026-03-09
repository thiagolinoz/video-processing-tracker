# Video Processing Tracker

Microserviço responsável por rastrear o status do processamento de vídeos. Consome eventos do Kafka publicados por um serviço de processamento, persiste o estado atualizado no DynamoDB (AWS) e envia notificações por e-mail ao usuário em caso de erro no processamento.

---

## Sumário

- [Visão Geral](#visão-geral)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Fluxo de Negócio](#fluxo-de-negócio)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Configuração](#configuração)
- [Como Executar](#como-executar)
  - [Localmente com Docker Compose](#localmente-com-docker-compose)
  - [Kubernetes (K8s)](#kubernetes-k8s)
- [Tópico Kafka](#tópico-kafka)
- [DynamoDB — Tabela `Videos`](#dynamodb--tabela-videos)
- [API REST](#api-rest)
- [Testes](#testes)

---

## Visão Geral

O **Video Processing Tracker** é um microserviço da stack FIAP Pós-Tech que:

1. **Consome mensagens Kafka** do tópico `process-status-videos`, produzidas pelo serviço de processamento de vídeo.
2. **Atualiza o status do vídeo** no DynamoDB com os dados recebidos no evento.
3. **Envia e-mail de notificação** ao usuário quando o status é `PROCESS_ERROR`.

---

## Arquitetura

O projeto segue a **Arquitetura Hexagonal (Ports & Adapters)**, separando claramente o núcleo de domínio das integrações externas.

```
┌──────────────────────────────────────────────────────┐
│                    ADAPTERS (entrada)                 │
│  Kafka Listener (FileEventListener)                  │
│  REST Controller (VideoController) — somente teste   │
└────────────────────┬─────────────────────────────────┘
                     │ VideoModel
┌────────────────────▼─────────────────────────────────┐
│                    DOMÍNIO                            │
│  VideoMetadataService (implementa VideoMetadataServicePort)│
│  VideoStatusEnum: RECEIVED | PROCESSING | COMPLETED | PROCESS_ERROR│
└────┬───────────────────────────────────────┬─────────┘
     │ updateVideoStatus()                   │ send()
┌────▼───────────────┐          ┌────────────▼─────────┐
│  ADAPTER (saída)   │          │  ADAPTER (saída)      │
│  VideoMetadataRepository     │  NotificationEmail    │
│  (DynamoDB)        │          │  (Gmail SMTP)         │
└────────────────────┘          └──────────────────────┘
```

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.10 |
| Spring Kafka | gerenciado pelo BOM |
| Spring Mail | gerenciado pelo BOM |
| AWS SDK for Java | 2.29.20 |
| DynamoDB Enhanced Client | 2.29.20 |
| SpringDoc OpenAPI (Swagger) | 2.8.8 |
| Lombok | gerenciado pelo BOM |
| Docker Base Image | `amazoncorretto:21-al2023-jdk` |

**Infraestrutura local (Docker Compose):**

| Serviço | Imagem | Porta |
|---|---|---|
| DynamoDB Local | `amazon/dynamodb-local:latest` | `8000` |
| DynamoDB Admin UI | `aaronshaf/dynamodb-admin` | `8001` |
| Zookeeper | `confluentinc/cp-zookeeper:latest` | `2181` |
| Kafka | `confluentinc/cp-kafka:7.4.0` | `9092` |
| Kafka UI | `provectuslabs/kafka-ui:latest` | `8088` |

---

## Fluxo de Negócio

```
Kafka Topic: "process-status-videos"
        │
        ▼
FileEventListener.consume()
        │  deserializa JSON → VideoUploadedMessage
        │  valida campos obrigatórios (nmPersonEmail, idVideoSend)
        ▼
VideoMapper.toVideoModelMessage() → VideoModel
        │
        ▼
VideoMetadataService.handleVideo(VideoModel)
        │
        ├─ RECEIVED / PROCESSING / COMPLETED
        │       └─▶ updateVideoStatus() → DynamoDB
        │
        └─ PROCESS_ERROR
                ├─▶ updateVideoStatus() → DynamoDB
                └─▶ sendNotification()  → Gmail SMTP
```

---

## Estrutura do Projeto

```
src/main/java/br/com/fiap_postech/video_processing_tracker/
│
├── VideoProcessingTrackerApplication.java
│
├── domain/
│   ├── enums/
│   │   └── VideoStatusEnum              # RECEIVED | PROCESSING | COMPLETED | PROCESS_ERROR
│   ├── models/
│   │   ├── VideoModel                   # Modelo central de domínio
│   │   └── events/VideoUploadedModel    # Record imutável para eventos
│   ├── ports/
│   │   ├── in/VideoMetadataServicePort  # handleVideo(VideoModel)
│   │   └── out/
│   │       ├── VideoMetadataRepositoryPort  # updateVideoStatus(VideoModel)
│   │       └── NotificationPort            # send(String address)
│   └── services/
│       └── VideoMetadataService         # Orquestra atualização + notificação
│
├── adapters/
│   ├── commons/mappers/
│   │   ├── VideoMapper                  # Message → Model → Entity
│   │   └── CloudEventMapper            # CloudEvent DTO → Model
│   ├── dto/
│   │   └── VideoUploadedCloudEvent     # Record CloudEvent
│   ├── event/
│   │   ├── listener/FileEventListener  # @KafkaListener
│   │   └── message/VideoUploadedMessage# DTO da mensagem Kafka
│   ├── notification/
│   │   └── NotificationEmail           # Implementa NotificationPort (Gmail SMTP)
│   ├── repositories/
│   │   ├── VideoMetadataRepository     # Implementa VideoMetadataRepositoryPort (DynamoDB)
│   │   └── aws/config/
│   │       ├── DynamoDbConfig          # DynamoDbClient + DynamoDbEnhancedClient
│   │       └── DynamoDbInitializer     # Cria tabela "Videos" no startup (configurável)
│   └── web/api/controller/
│       └── VideoController             # REST stub (somente para teste de persistência)
│
├── configuration/
│   ├── DomainConfig                    # Wiring dos beans de domínio
│   └── kafka/KafkaConfig              # @EnableKafka
│
└── infra/db/entities/
    └── VideoEntity                     # @DynamoDbBean — tabela "Videos"
```

---

## Configuração

As configurações principais ficam em `src/main/resources/application.properties`.

| Propriedade | Descrição | Padrão (produção) |
|---|---|---|
| `aws.dynamodb.endpoint` | Endpoint do DynamoDB | `https://dynamodb.us-east-1.amazonaws.com` |
| `aws.dynamodb.create-tables` | Cria tabela no startup | `false` |
| `spring.kafka.bootstrap-servers` | Broker Kafka | `10.0.14.140:9092` |
| `spring.kafka.consumer.group-id` | Consumer group | `seu-grupo-v2` |
| `spring.mail.username` | E-mail remetente (Gmail) | configurado no properties |
| `MAIL_PASSWORD` | Senha do Gmail (variável de ambiente) | — |

**Para desenvolvimento local**, substitua:
```properties
# DynamoDB local
aws.dynamodb.endpoint=http://localhost:8000
aws.dynamodb.create-tables=true

# Kafka local
spring.kafka.bootstrap-servers=localhost:9092
```

---

## Como Executar

### Localmente com Docker Compose

**Pré-requisitos:** Docker, Java 21, Maven

```bash
# 1. Sobe a infraestrutura local (DynamoDB, Kafka, UIs)
docker compose up -d

# 2. Compila o projeto (sem testes)
./mvnw package -DskipTests

# 3. Define a senha do e-mail e inicia a aplicação
export MAIL_PASSWORD=sua_senha_de_app_gmail
java -jar target/video-processing-tracker-*.jar
```

Após subir, acesse:
- **DynamoDB Admin UI:** http://localhost:8001
- **Kafka UI:** http://localhost:8088
- **Swagger UI:** http://localhost:8080/swagger-ui.html

### Kubernetes (K8s)

Os manifests estão em `k8s/`. O CI/CD substitui a variável `REPO_ECR` pela URI da imagem no Amazon ECR.

```bash
# Aplica todos os manifests
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/application/application-deployment.yaml
kubectl apply -f k8s/application/application-service.yaml
```

As credenciais AWS são injetadas via Secret `aws-academy-credentials` (access-key-id, secret-access-key, session-token).

**Recursos do Deployment:**

| Recurso | Request | Limit |
|---|---|---|
| CPU | 100m | 500m |
| Memória | 128Mi | 256Mi |

---

## Tópico Kafka

| Parâmetro | Valor |
|---|---|
| Tópico | `process-status-videos` |
| Consumer group | `seu-grupo-v2` |
| ACK mode | `MANUAL_IMMEDIATE` |
| Offset reset | `earliest` |
| Deserializer | `StringDeserializer` (JSON via `ObjectMapper`) |

**Exemplo de payload esperado:**
```json
{
  "nmPersonEmail": "usuario@email.com",
  "idVideoSend": "uuid-do-video",
  "nmPersonName": "Nome do Usuário",
  "nmVideo": "meu-video.mp4",
  "cdVideoStatus": "COMPLETED",
  "nmVideoPathOrigin": "s3://bucket/origem/meu-video.mp4",
  "nmVideoPathZip": "s3://bucket/zips/meu-video.zip",
  "errorMessage": null
}
```

---

## DynamoDB — Tabela `Videos`

| Campo | Tipo | Papel |
|---|---|---|
| `nmPessoaEmail` | `String` | Partition Key |
| `idVideoSend` | `String` | Sort Key |
| `cdVideoStatus` | `String` | Sort Key do índice `cdVideoStatusIndex` |
| `nmVideo` | `String` | Nome do arquivo de vídeo |
| `nmVideoPathOrigin` | `String` | Caminho S3 de origem |
| `nmVideoPathZip` | `String` | Caminho S3 do ZIP gerado |
| `dateTimeVideoCreated` | `Instant` | Data/hora de criação |
| `dateTimeVideoProcessCompleted` | `Instant` | Data/hora de conclusão |
| `nmPersonName` | `String` | Nome do usuário |
| `errorMessage` | `String` | Mensagem de erro (se houver) |

> Os campos `null` **não são sobrescritos** nas atualizações (`IgnoreNullsMode.SCALAR_ONLY`), preservando dados já persistidos.

---

## API REST

> **Atenção:** o controller existe apenas para testes de persistência e deve ser removido em produção.

| Método | Endpoint | Resposta |
|---|---|---|
| `POST` | `/api/v1/user/{email}/videos/create/{status}` | `201 Created` |

Documentação interativa disponível em: `http://localhost:8080/swagger-ui.html`

---

## Testes

O projeto conta com testes unitários cobrindo todas as camadas da arquitetura hexagonal.

```bash
# Executa todos os testes
./mvnw test
```

| Camada testada | Classes de teste |
|---|---|
| Mappers | `CloudEventMapperTest`, `VideoMapperTest` |
| DTOs | `VideoUploadedCloudEventTest`, `VideoUploadedMessageTest` |
| Listener Kafka | `FileEventListenerTest` |
| Notificação | `NotificationEmailTest` |
| Repositório | `VideoMetadataRepositoryTest` |
| Config DynamoDB | `DynamoDbConfigTest`, `DynamoDbInitializerTest` |
| Controller | `VideoControllerTest` |
| Config domínio | `DomainConfigTest` |
| Enums | `VideoStatusEnumTest` |
| Modelos | `VideoModelTest` |
| Ports (in/out) | `VideoMetadataServicePortTest`, `NotificationPortTest`, `VideoMetadataRepositoryPortTest` |
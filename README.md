# Spring AI: Employee Onboarding Assistant (Cloud Version)

#### A Retrieval-Augmented Generation (RAG)** application built with **Spring Boot** and **Spring AI**. This assistant intelligently processes an internal employee handbook to provide context-aware answers and personalized roadmaps using **Google Gemini** and **PostgreSQL (PGVector)**.

## 🛠 Tech Stack

* **Framework:** Spring Boot 3.x
* **AI Framework:** Spring AI 1.1.2 (Milestone)
* **LLM:** Google Gemini 1.5 Flash (via Gemini Developer API)
* **Embeddings:** Google `text-embedding-004` (768 dimensions)
* **Vector Store:** PostgreSQL with `pgvector` extension
* **Containerization:** Docker & Docker Compose

### Prerequisites
1.  **Google AI Studio Key:** Obtain your API key from [aistudio.google.com](https://aistudio.google.com/).
2.  **Java 21+:** The project leverages modern Java features.
3.  **Docker:** For running the PostgreSQL + PGVector instance.

## Setup & Installation

### 1. Clone the Repository
```bash
    git clone <your-repo-url> 
    cd onboarding-assistant
```

### 2. Launch the Database
Use Docker Compose to start the PostgreSQL instance with the pgvector extension pre-installed:

```bash
  docker-compose up -d
```


### 3. Database Initialization (Manual if not using auto-init)
Ensure the vector extension is enabled in your database:

```sql
    CREATE EXTENSION IF NOT EXISTS vector;
```

### 4. Configure Environment Variables
Add your Google API Key to your environment or application.properties:

#### Properties
```declarative
    spring.ai.google.genai.api-key=YOUR_GEMINI_API_KEY
    spring.ai.google.genai.project-id=unused
```

#### Testing the Endpoints
##### 1. Ingest the Handbook
Vectorize the internal documents and store them in the PostgreSQL database.
```bash
    curl -X POST http://localhost:8080/api/v1/onboarding/ingest
```
##### 2. Generate a Personalized Roadmap
Retrieve a role-specific and department-specific onboarding roadmap grounded in the handbook context.
```bash
    curl "http://localhost:8080/api/v1/onboarding/roadmap?role=JavaDev&dept=Payments"
```
#### Architecture

* **Ingestion Phase:** Reads resource files → Splits text via TokenTextSplitter → Generates 768-dim embeddings → Stores in PGVector.
* **Retrieval Phase:** User query is vectorized → Similarity search finds the most relevant handbook chunks.
* **Generation Phase:** Context + User Prompt are sent to Gemini 1.5 Flash to produce a grounded response.

#### Next Steps: Local Agent Migration
To move away from cloud dependencies and quotas, the next phase involves:

* **Ollama Installation:** Running models locally on macOS.
* **Model Setup:** Pulling gemma3:4b (LLM) and nomic-embed-text (Embeddings).
* **Dependency Swap:** Transitioning from spring-ai-starter-model-google-genai to spring-ai-ollama-spring-boot-starter.
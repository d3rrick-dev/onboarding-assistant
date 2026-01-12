package com.onboarding.smoketest;


import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class VectorStoreTest {
    @Autowired
    private VectorStore vectorStore;

    @Test
    void testVectorStoreConnectivityAndSearch() {
        var testDoc = new Document(
                "The secret onboarding code is BLUE-LION-2026",
                Map.of("category", "test"));
        vectorStore.add(List.of(testDoc));
        var results = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query("What is the secret code?")
                        .topK(1)
                        .build()
        );
        assertThat(results).isNotEmpty();
        assertThat(results.getFirst().getText()).contains("BLUE-LION-2026");
    }
}

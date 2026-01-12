package com.onboarding;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngestionService {
    private final VectorStore vectorStore;
    public void ingestHandbook(Resource resource) {
        var textReader = new TextReader(resource);
        var splitter = new TokenTextSplitter();
        vectorStore.accept(splitter.apply(textReader.get()));
    }
}
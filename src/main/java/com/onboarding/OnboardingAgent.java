package com.onboarding;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

@Service
public class OnboardingAgent {
    private final ChatClient chatClient;

    public OnboardingAgent(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder
                .defaultAdvisors(
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest.builder().build())
                                .build()
                ).defaultSystem("""
                You are the 'Senior Onboarding Architect'.
                Use the provided context to build a 30-day plan for a {role} in the {dept} team.
                Be concise and categorize tasks into: 
                1. Immediate (Day 1) 
                2. Short-term (Week 1)
                3. Cultural Nuances.
                Provide actionable items only based on the handbook.
                """)
                .build();
    }

    public String generatePlan(String role, String dept) {
        return chatClient.prompt()
                .system(s -> s.param("role", role).param("dept", dept))
                .user("Based on the handbook, what should my first month look like?")
                .call()
                .content();
    }
}

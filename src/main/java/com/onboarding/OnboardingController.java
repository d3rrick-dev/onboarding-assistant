package com.onboarding;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/onboarding")
@RequiredArgsConstructor
public class OnboardingController {
    private final OnboardingAgent agent;
    private final IngestionService ingestion;
    @PostMapping("/ingest")
    public String load() {
        ingestion.ingestHandbook(new ClassPathResource("handbook.txt"));
        return "Knowledge base vectorized into PostgreSQL!";
    }

    @GetMapping("/roadmap")
    public String getRoadmap(@RequestParam String role, @RequestParam String dept) {
        return agent.generatePlan(role, dept);
    }
}